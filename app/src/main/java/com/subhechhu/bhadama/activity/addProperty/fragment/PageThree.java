package com.subhechhu.bhadama.activity.addProperty.fragment;

import android.Manifest;
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

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.subhechhu.bhadama.BuildConfig;
import com.subhechhu.bhadama.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class PageThree extends Fragment {

    final private int CAMERA_PERMISSION = 1221;
    final private int GALLERY_PERMISSION = 1222;

    final private int REQUEST_IMAGE_CAPTURE = 4367;

    int imageViewSelected = 0;

    LinearLayout linearlayout;
    View parentView;
    ImageView image_first, image_second, image_third, image_fourth, image_fifth, image_sixth;

    String currentPhotoPath;


    public static PageThree newInstance() {
        return new PageThree();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_pagethree, container, false);
        linearlayout = parentView.findViewById(R.id.linearlayout);

        CardView cardview_first = parentView.findViewById(R.id.cardview_first);
        CardView cardview_second = parentView.findViewById(R.id.cardview_second);
        CardView cardview_third = parentView.findViewById(R.id.cardview_third);
        CardView cardview_fourth = parentView.findViewById(R.id.cardview_fourth);
        CardView cardview_fifth = parentView.findViewById(R.id.cardview_fifth);
        CardView cardview_sixth = parentView.findViewById(R.id.cardview_sixth);

        image_first = parentView.findViewById(R.id.imageView_first);
        image_second = parentView.findViewById(R.id.imageView_second);
        image_third = parentView.findViewById(R.id.imageview_third);
        image_fourth = parentView.findViewById(R.id.imageView_fourth);
        image_fifth = parentView.findViewById(R.id.imageView_fifth);
        image_sixth = parentView.findViewById(R.id.imageView_sixth);

        cardview_first.setOnClickListener(view -> {
            renderPickerDialog();
            imageViewSelected = 1;
        });

        cardview_second.setOnClickListener(view -> {
            renderPickerDialog();
            imageViewSelected = 2;
        });

        cardview_third.setOnClickListener(view -> {
            renderPickerDialog();
            imageViewSelected = 3;
        });

        cardview_fourth.setOnClickListener(view -> {
            renderPickerDialog();
            imageViewSelected = 4;
        });

        cardview_fifth.setOnClickListener(view -> {
            renderPickerDialog();
            imageViewSelected = 5;
        });

        cardview_sixth.setOnClickListener(view -> {
            renderPickerDialog();
            imageViewSelected = 6;
        });

        return parentView;
    }

    public void renderPickerDialog() {
        View view = getLayoutInflater().inflate(R.layout.imagepicker_dialog, (ViewGroup) parentView, false);
        BottomSheetDialog dialog = new BottomSheetDialog(Objects.requireNonNull(getActivity()), R.style.dialogStyle);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        dialog.show();

        AppCompatButton button_camera = view.findViewById(R.id.button_camera);
        AppCompatButton button_gallery = view.findViewById(R.id.button_gallery);

        button_camera.setOnClickListener(viewCamera -> {
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
            if (checkPermission(GALLERY_PERMISSION)) {
                Toast.makeText(getActivity(), "Gallery Permission granted", Toast.LENGTH_SHORT).show();
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

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            switch (imageViewSelected) {
                case 1:
                    try {
//                        compressImage(currentPhotoPath);
//                        Bitmap bitmap_1 = BitmapFactory.decodeFile(currentPhotoPath);
////                        Log.e("TAG")
//                        bitmap_1.compress(Bitmap.CompressFormat.JPEG, 2, new FileOutputStream(currentPhotoPath));
                        image_first.setImageBitmap(compressImage(currentPhotoPath));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    try {
//                        Bitmap bitmap_2 = BitmapFactory.decodeFile(currentPhotoPath);
//                        bitmap_2.compress(Bitmap.CompressFormat.JPEG, 2, new FileOutputStream(currentPhotoPath));
//                        image_second.setImageBitmap(bitmap_2);
                        image_second.setImageBitmap(compressImage(currentPhotoPath));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    try {
//                        Bitmap bitmap_3 = BitmapFactory.decodeFile(currentPhotoPath);
//                        bitmap_3.compress(Bitmap.CompressFormat.JPEG, 2, new FileOutputStream(currentPhotoPath));
//                        image_third.setImageBitmap(bitmap_3);
                        image_third.setImageBitmap(compressImage(currentPhotoPath));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case 4:
                    try {
//                        Bitmap bitmap_4 = BitmapFactory.decodeFile(currentPhotoPath);
//                        bitmap_4 = getResizedBitmap(bitmap_4, 100);
//                        bitmap_4.compress(Bitmap.CompressFormat.JPEG, 2, new FileOutputStream(currentPhotoPath));
//                        image_fourth.setImageBitmap(bitmap_4);
                        image_fourth.setImageBitmap(compressImage(currentPhotoPath));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case 5:
                    try {
//                        Bitmap bitmap_5 = BitmapFactory.decodeFile(currentPhotoPath);
//                        bitmap_5 = getResizedBitmap(bitmap_5, 100);
//                        bitmap_5.compress(Bitmap.CompressFormat.JPEG, 2, new FileOutputStream(currentPhotoPath));
//                        image_fifth.setImageBitmap(bitmap_5);
                        image_fifth.setImageBitmap(compressImage(currentPhotoPath));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case 6:
                    try {
//                        Bitmap bitmap_6 = BitmapFactory.decodeFile(currentPhotoPath);
//                        bitmap_6 = getResizedBitmap(bitmap_6, 100);
//                        bitmap_6.compress(Bitmap.CompressFormat.JPEG, 2, new FileOutputStream(currentPhotoPath));
//                        image_sixth.setImageBitmap(bitmap_6);
                        image_sixth.setImageBitmap(compressImage(currentPhotoPath));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
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
        View view = getLayoutInflater().inflate(R.layout.imagepicker_nopermission_dialog, (ViewGroup) parentView, false);
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
                            Log.e("TAG", "177 Camera Permission Denied");
                        } else {
                            showNoPermissionDialog("Camera Permission");
                        }
                    }
                }
                break;

            case GALLERY_PERMISSION:
                if (grantResults.length > 0 && permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(getActivity(), "Opening Gallery", Toast.LENGTH_SHORT).show();
                    } else {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(Objects.requireNonNull(getActivity()),
                                Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            Log.e("TAG", "195 Gallery Permission Denied");
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

    public Bitmap compressImage(String imageUri) {

        String filePath = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = (float) actualWidth / (float) actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

        options.inJustDecodeBounds = false;
        options.inTempStorage = new byte[16 * 1024];

        try {
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2.0f, middleY - bmp.getHeight() / 2.0f, new Paint(Paint.FILTER_BITMAP_FLAG));

        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scaledBitmap;
    }

    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = Objects.requireNonNull(getActivity()).getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = Math.min(heightRatio, widthRatio);
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }
        return inSampleSize;
    }
}