package com.subhechhu.bhadama.activity.addProperty.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.subhechhu.bhadama.BuildConfig;
import com.subhechhu.bhadama.R;
import com.subhechhu.bhadama.activity.addProperty.AddPropertyActivity;
import com.subhechhu.bhadama.activity.personalProperty.ModelPersonalProperty;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class PageThreeNew extends Fragment implements PageThreeImageAdapter.ClickListener {

    private static final String TAG = PageThreeNew.class.getSimpleName();


    final private int CAMERA_PERMISSION = 1221;
    final private int GALLERY_PERMISSION = 1222;

    final private int REQUEST_IMAGE_CAPTURE = 4367;
    final private int REQUEST_IMAGE_GALLERY = 5447;

    View parentView;

    RecyclerView recyclerview_images;
    AppCompatButton button_addImage;
    int imageCount = 0;

    String currentPhotoPath;

    PageThreeImageAdapter pageThreeImageAdapter;
    List<String> imageList;

    boolean galleryPicker, cameraPicker;

    FragmentViewModel fragmentViewModel;
    ModelPersonalProperty personalProperty;
    JSONObject fieldObject;
    JSONArray imageJsonArray;

    public static PageThreeNew newInstance() {
        return new PageThreeNew();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentViewModel = ViewModelProviders.of(requireActivity()).get(FragmentViewModel.class);
        if (getActivity() instanceof AddPropertyActivity) {
            personalProperty = ((AddPropertyActivity) Objects.requireNonNull(getActivity())).getDataToEdit();
        }
        pageThreeImageAdapter = new PageThreeImageAdapter(getActivity(), this);
        imageList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_pagethree_new, container, false);
        button_addImage = parentView.findViewById(R.id.button_addImage);
        recyclerview_images = parentView.findViewById(R.id.recyclerview_images);

        return parentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        button_addImage.setOnClickListener(view -> renderPickerDialog());

        recyclerview_images.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview_images.setHasFixedSize(true);
        recyclerview_images.setAdapter(pageThreeImageAdapter);
    }

    public void renderPickerDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_imagepicker, (ViewGroup) parentView, false);
        BottomSheetDialog dialog = new BottomSheetDialog(Objects.requireNonNull(getActivity()), R.style.dialogStyle);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        dialog.show();

        AppCompatButton button_camera = view.findViewById(R.id.button_camera);
        AppCompatButton button_gallery = view.findViewById(R.id.button_gallery);

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
    }

    @SuppressLint("QueryPermissionsNeeded")
    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(Objects.requireNonNull(getActivity()).getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(getActivity(), "Check your camera and try again", Toast.LENGTH_SHORT).show();
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
                Log.e(TAG, "current photo path from camera: " + currentPhotoPath);
            }
            if (requestCode == REQUEST_IMAGE_GALLERY) {
                currentPhotoPath = getImagePath(data);
                Log.e(TAG, "current photo path from gallery: " + getImagePath(data));
            }

            imageList.add(currentPhotoPath);
            Log.e(TAG, "imageList on adding: " + imageList);
            pageThreeImageAdapter.showList(imageList);
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
        String imageFileName = "JPEG_" + timeStamp + "_";
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

    @Override
    public void onPause() {
        super.onPause();
        if (!cameraPicker && !galleryPicker) {
            imageJsonArray = new JSONArray();
            for (int i = 0; i < imageList.size(); i++)
                imageJsonArray.put(imageList.get(i));
            try {
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
    public void onClick(List<String> imageList) {
        this.imageList = imageList;
        Log.e(TAG, "imageList on deleting: " + imageList);

    }
}