package com.subhechhu.bhadama.activity.addProperty.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.subhechhu.bhadama.BuildConfig;
import com.subhechhu.bhadama.R;
import com.subhechhu.bhadama.activity.addProperty.AddPropertyActivity;
import com.subhechhu.bhadama.activity.personalProperty.ModelPersonalProperty;
import com.subhechhu.bhadama.util.ImageUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static android.app.Activity.RESULT_OK;

public class PageThree extends Fragment implements PageThreeImageAdapter.ClickListener {

    private static final String TAG = PageThree.class.getSimpleName();


    final private int CAMERA_PERMISSION = 1221;
    final private int GALLERY_PERMISSION = 1222;

    final private int REQUEST_IMAGE_CAPTURE = 4367;
    final private int REQUEST_IMAGE_GALLERY = 5447;

    View parentView;

    RecyclerView recyclerview_images;
    FloatingActionButton button_addImage;

    ExecutorService executor;
    Handler handler;

    String currentPhotoPath;
    boolean canAdd = true;

    PageThreeImageAdapter pageThreeImageAdapter;
    JSONArray imageList;
//    JSONArray imageListJsonArray;

    boolean galleryPicker, cameraPicker;

    FragmentViewModel fragmentViewModel;
    ModelPersonalProperty personalProperty;
    JSONObject fieldObject;
    JSONArray imageJsonArray;
    Dialog progressDialog;

    public static PageThree newInstance() {
        return new PageThree();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imageList = new JSONArray();
        fragmentViewModel = ViewModelProviders.of(requireActivity()).get(FragmentViewModel.class);
        if (getActivity() instanceof AddPropertyActivity) {
            personalProperty = ((AddPropertyActivity) Objects.requireNonNull(getActivity())).getDataToEdit();
        }
        pageThreeImageAdapter = new PageThreeImageAdapter(getActivity(), this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_pagethree, container, false);
        button_addImage = parentView.findViewById(R.id.button_addImage);
        recyclerview_images = parentView.findViewById(R.id.recyclerview_images);

        return parentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        button_addImage.setOnClickListener(view -> {
            renderPickerDialog();
        });

        recyclerview_images.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview_images.setHasFixedSize(true);
        recyclerview_images.setAdapter(pageThreeImageAdapter);

        if (personalProperty != null) {
            for (int i = 0; i < personalProperty.getImages().size(); i++) {
                imageList.put(personalProperty.getImages().get(i));
            }
            pageThreeImageAdapter.showList(imageList);
        }
    }

    public void renderPickerDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_imagepicker, (ViewGroup) parentView, false);
        BottomSheetDialog dialog = new BottomSheetDialog(Objects.requireNonNull(getActivity()), R.style.dialogStyle);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        dialog.show();

        AppCompatButton button_camera = view.findViewById(R.id.button_camera);
        AppCompatButton button_gallery = view.findViewById(R.id.button_gallery);
        AppCompatButton button_whatsapp = view.findViewById(R.id.button_whatsapp);

        button_camera.setOnClickListener(viewCamera -> {
            cameraPicker = true;
            dialog.dismiss();
            if (checkPermission(CAMERA_PERMISSION)) {
                openCamera();
            } else {
                try {
                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                            CAMERA_PERMISSION);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        });

        button_gallery.setOnClickListener(viewGallery -> {
            dialog.dismiss();
            galleryPicker = true;
            if (checkPermission(GALLERY_PERMISSION)) {
                openGallery();
            } else {
                try {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            GALLERY_PERMISSION);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        });

        button_whatsapp.setOnClickListener(view1 -> {
            if (appInstalledOrNot()) {
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=+918892096825"));
//                startActivity(intent);
                makeToast("We will reach you via Whatsapp");
                dialog.dismiss();
            } else {
                makeToast("Whatsapp not available");
            }
        });
    }

    private boolean appInstalledOrNot() {
        PackageManager pm = requireActivity().getPackageManager();
        try {
            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(Objects.requireNonNull(getActivity()).getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                makeToast("Check your camera and try again");
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(), "com.subhechhu.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private void openGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, REQUEST_IMAGE_GALLERY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        galleryPicker = false;
        cameraPicker = false;

        if ((requestCode == REQUEST_IMAGE_CAPTURE || requestCode == REQUEST_IMAGE_GALLERY) && resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {

                renderProgressDialog();

                Log.e(TAG, "current photo path from camera: " + currentPhotoPath);

                File file = new File(currentPhotoPath);
                int file_size = Integer.parseInt(String.valueOf(file.length() / 1024)); //in kb

                Log.e(TAG, "image size from the camera: " + file_size);

                executor = Executors.newSingleThreadExecutor();
                handler = new Handler(Looper.getMainLooper());

                executor.execute(() -> {
                    currentPhotoPath = new ImageUtils().getCompressedBitmap(getActivity(), currentPhotoPath);
                    handler.post(() -> {
                        addImageToJsonArray(0, currentPhotoPath, imageList);
                    });
                });
            }
            if (requestCode == REQUEST_IMAGE_GALLERY) {

                renderProgressDialog();

                currentPhotoPath = getImagePath(data);
                Log.e(TAG, "current photo path from gallery: " + getImagePath(data));

                File file = new File(getImagePath(data));
                int file_size = Integer.parseInt(String.valueOf(file.length() / 1024)); //in kb

                Log.e(TAG, "image size from the gallery: " + file_size);

                executor = Executors.newSingleThreadExecutor();
                handler = new Handler(Looper.getMainLooper());

                executor.execute(() -> {
                    currentPhotoPath = new ImageUtils().getCompressedBitmap(getActivity(), currentPhotoPath);
                    handler.post(() -> {
                        addImageToJsonArray(0, currentPhotoPath, imageList);
                    });
                });

            }
        }
    }

    public void addImageToJsonArray(int pos, String imagePath, JSONArray jsonArr) {
        try {
            for (int i = jsonArr.length(); i > pos; i--) {
                jsonArr.put(i, jsonArr.get(i - 1));
            }
            jsonArr.put(pos, imagePath);
            Log.e(TAG, "imageList on adding: " + jsonArr);
            if (jsonArr.length() == 6)
                button_addImage.setVisibility(View.INVISIBLE);
            pageThreeImageAdapter.showList(jsonArr);
            progressDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String getImagePath(Intent data) {
        if (data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            if (selectedImage != null) {
                Cursor cursor = Objects.requireNonNull(getActivity()).getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    return picturePath;
                }
            }
        }
        return "";
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CANADA).format(new Date());
        String imageFileName = "Bhadama_" + timeStamp + "_";
        File storageDir = Objects.requireNonNull(getActivity()).getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[]
            permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_PERMISSION:
                if (grantResults.length > 0 && permissions[0].equals(Manifest.permission.CAMERA)) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        openCamera();
                    } else {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(Objects.requireNonNull(getActivity()),
                                Manifest.permission.CAMERA)) {
                            Log.e(TAG, "177 Camera Permission Denied");
                        } else {
                            showNoPermissionDialog("Camera Permission");
                        }
                    }
                }
                break;

            case GALLERY_PERMISSION:
                if (grantResults.length > 0 && permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        openGallery();
                    } else {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(Objects.requireNonNull(getActivity()),
                                Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            Log.e(TAG, "195 Gallery Permission Denied");
                        } else {
                            showNoPermissionDialog("Gallery Permission");
                        }
                    }
                }
                break;

            default:
                break;
        }
    }

    public boolean checkPermission(int permissionCode) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return false;

        if (permissionCode == CAMERA_PERMISSION) {
            int result = Objects.requireNonNull(getActivity()).checkSelfPermission(Manifest.permission.CAMERA);
            return result == PackageManager.PERMISSION_GRANTED;
        } else if (permissionCode == GALLERY_PERMISSION) {
            int result = Objects.requireNonNull(getActivity()).checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED;
        }

        return false;
    }

    private void showNoPermissionDialog(String permission) {
        View view = getLayoutInflater().inflate(R.layout.dialog_imagepicker_nopermission, (ViewGroup) parentView, false);
        BottomSheetDialog dialog = new BottomSheetDialog(Objects.requireNonNull(getActivity()), R.style.dialogStyle);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        dialog.show();

        AppCompatButton button_permission_close = view.findViewById(R.id.button_permission_close);
        AppCompatButton button_permission_settings = view.findViewById(R.id.button_permission_settings);

        TextView textView_permission_message = view.findViewById(R.id.textView_permission_message);
        textView_permission_message.setText(getString(R.string.nopermission, permission));

        button_permission_close.setOnClickListener(viewClose -> dialog.dismiss());

        button_permission_settings.setOnClickListener(viewPermission -> {
            startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + BuildConfig.APPLICATION_ID)));
            dialog.dismiss();

        });
    }

    private void renderProgressDialog() {
        progressDialog = new Dialog(getActivity());
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setCancelable(false);
        progressDialog.setContentView(R.layout.dialog_progress);

        TextView textViewProgressDialogMessage = progressDialog.findViewById(R.id.textView_message);
        textViewProgressDialogMessage.setText(R.string.processing_image);

        progressDialog.show();
    }

    @Override
    public void onClick(JSONArray imageList) {
        this.imageList = imageList;
        if (imageList.length() <= 6)
            button_addImage.setVisibility(View.VISIBLE);
    }

    public void makeToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    public void nextClickListener() {
        if (!cameraPicker && !galleryPicker) {
            imageJsonArray = new JSONArray();
            try {
                for (int i = 0; i < imageList.length(); i++)
                    imageJsonArray.put(imageList.getString(i));
                fieldObject = new JSONObject();
                fieldObject.put("imageArray", imageJsonArray);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d(TAG, "fragment three on pause data" + fieldObject);
            fragmentViewModel.postFragmentThreeData(fieldObject);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"onDestroy() PageThree.java");
    }
}