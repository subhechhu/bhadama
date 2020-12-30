package com.subhechhu.bhadama.activity.addProperty.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.subhechhu.bhadama.R;
import com.subhechhu.bhadama.activity.MapActivity;

import org.json.JSONException;

import java.io.IOException;
import java.util.Objects;

public class PageFour extends Fragment {

    private static final String TAG = PageFour.class.getSimpleName();

    TextView textView_rooms_count, textView_rooms_rent, textView_rooms_furnishing, textView_availabledate,
            textView_rooms_location, textView_rooms_parking, textView_rooms_tenant, textView_rooms_water,
            textView_rooms_type, textView_viewinmaps;

    ImageView imageView_first, imageView_second, imageView_third, imageView_fourth, imageView_fifth,
            imageView_sixth;

    CardView cardView_first, cardView_second, cardView_third, cardView_fourth, cardView_fifth,
            cardView_sixth;

    ProgressBar progressbar_summary;

    String latitude = "", longitude = "", location = "", city = "", roomsize = "", rent = "",
            availableDate = "", roomType = "";
    String furnishing = "", tenants = "";
    String image_1 = "", image_2 = "", image_3 = "", image_4 = "", image_5 = "", image_6 = "";

    boolean wheel2, wheel4, nwsc, underground, others;

    FragmentViewModel fragmentViewModel;

    public static PageFour newInstance() {
        return new PageFour();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentViewModel = ViewModelProviders.of(requireActivity()).get(FragmentViewModel.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            progressbar_summary.setVisibility(View.VISIBLE);

            if (roomsize.equalsIgnoreCase("1"))
                textView_rooms_count.setText(getString(R.string.total_room, roomsize, "Room"));
            else
                textView_rooms_count.setText(getString(R.string.total_room, roomsize, "Rooms"));
            textView_rooms_rent.setText(getString(R.string.rent_amount, rent));
            textView_availabledate.setText(availableDate);
            textView_rooms_location.setText(city);
            textView_rooms_type.setText(roomType);

            textView_rooms_furnishing.setText(furnishing);
            if (wheel2 && wheel4)
                textView_rooms_parking.setText(getString(R.string.bothParking));
            else if (wheel4)
                textView_rooms_parking.setText(getString(R.string.parking_4));
            else if (wheel2)
                textView_rooms_parking.setText(getString(R.string.parking_2));
            else
                textView_rooms_parking.setText(getString(R.string.noparking));

            textView_rooms_tenant.setText(getString(R.string.tenants, tenants));

            if (nwsc && underground || nwsc && others || others && underground)
                textView_rooms_water.setText(getString(R.string.differentSources));
            else if (nwsc) {
                textView_rooms_water.setText(getString(R.string.nwsc_plain));
            } else if (underground) {
                textView_rooms_water.setText(getString(R.string.underground));
            } else
                textView_rooms_water.setText(getString(R.string.nowater));

            if (!image_1.isEmpty()) {
                Log.e(TAG, "image path: " + image_1);
                loadImage(image_1, imageView_first);
                cardView_first.setVisibility(View.VISIBLE);
                imageView_first.setVisibility(View.VISIBLE);
            } else {
                progressbar_summary.setVisibility(View.INVISIBLE);
                cardView_first.setVisibility(View.GONE);
                imageView_first.setVisibility(View.GONE);
            }

            if (!image_2.isEmpty()) {
                Log.e(TAG, "image path: " + image_2);
                loadImage(image_2, imageView_second);
                cardView_second.setVisibility(View.VISIBLE);
                imageView_second.setVisibility(View.VISIBLE);
            } else {
                cardView_second.setVisibility(View.GONE);
                imageView_second.setVisibility(View.GONE);
            }

            if (!image_3.isEmpty()) {
                Log.e(TAG, "image path: " + image_3);
                loadImage(image_3, imageView_third);
                cardView_third.setVisibility(View.VISIBLE);
                imageView_third.setVisibility(View.VISIBLE);
            } else {
                cardView_third.setVisibility(View.GONE);
                imageView_third.setVisibility(View.GONE);
            }

            if (!image_4.isEmpty()) {
                Log.e(TAG, "image path: " + image_4);
                loadImage(image_4, imageView_fourth);
                cardView_fourth.setVisibility(View.VISIBLE);
                imageView_fourth.setVisibility(View.VISIBLE);
            } else {
                cardView_fourth.setVisibility(View.GONE);
                imageView_fourth.setVisibility(View.GONE);
            }

            if (!image_5.isEmpty()) {
                Log.e(TAG, "image path: " + image_5);
                loadImage(image_5, imageView_fifth);
                cardView_fifth.setVisibility(View.VISIBLE);
                imageView_fifth.setVisibility(View.VISIBLE);
            } else {
                cardView_fifth.setVisibility(View.GONE);
                imageView_fifth.setVisibility(View.GONE);
            }

            if (!image_6.isEmpty()) {
                Log.e(TAG, "image path: " + image_6);
                loadImage(image_6, imageView_sixth);
                cardView_sixth.setVisibility(View.VISIBLE);
                cardView_sixth.setVisibility(View.VISIBLE);
            } else {
                cardView_sixth.setVisibility(View.GONE);
                imageView_sixth.setVisibility(View.GONE);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadImage(String path, ImageView imageView) {
        Bitmap thumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(path),
                300, 200);
        Glide
                .with(this)
                .asBitmap()
                .load(thumbImage)
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        progressbar_summary.setVisibility(View.INVISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        progressbar_summary.setVisibility(View.INVISIBLE);
                        return false;
                    }
                })
                .into(imageView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pagefour, container, false);
        textView_rooms_count = view.findViewById(R.id.textView_rooms_count);
        textView_rooms_rent = view.findViewById(R.id.textView_rooms_rent);
        textView_rooms_type = view.findViewById(R.id.textView_type);
        textView_rooms_furnishing = view.findViewById(R.id.textView_rooms_furnishing);
        textView_availabledate = view.findViewById(R.id.textView_availabledate);
        textView_rooms_location = view.findViewById(R.id.textView_rooms_location);
        textView_viewinmaps = view.findViewById(R.id.textView_viewinmap);
        textView_rooms_parking = view.findViewById(R.id.textView_rooms_parking);
        textView_rooms_tenant = view.findViewById(R.id.textView_rooms_tenant);
        textView_rooms_water = view.findViewById(R.id.textView_rooms_water);

        imageView_first = view.findViewById(R.id.imageView_first);
        imageView_second = view.findViewById(R.id.imageView_second);
        imageView_third = view.findViewById(R.id.imageView_third);
        imageView_fourth = view.findViewById(R.id.imageView_fourth);
        imageView_fifth = view.findViewById(R.id.imageView_fifth);
        imageView_sixth = view.findViewById(R.id.imageView_sixth);

        cardView_first = view.findViewById(R.id.cv_first);
        cardView_second = view.findViewById(R.id.cv_second);
        cardView_third = view.findViewById(R.id.cv_third);
        cardView_fourth = view.findViewById(R.id.cv_fourth);
        cardView_fifth = view.findViewById(R.id.cv_fifth);
        cardView_sixth = view.findViewById(R.id.cv_sixth);

        progressbar_summary = view.findViewById(R.id.progressbar_summary);

        AppCompatButton button_verify_property = view.findViewById(R.id.button_verify_property);
        button_verify_property.setOnClickListener(view1 -> Objects.requireNonNull(getActivity()).finish());

        fragmentViewModel.fragmentOneResponse().observe(getViewLifecycleOwner(), response -> {
            try {
                rent = response.getString("rent");
                roomType = response.getString("roomType");
                roomsize = response.getString("roomsize");
                availableDate = response.getString("date");
                city = response.getString("city");
                location = response.getString("location");
                latitude = response.getString("latitude");
                longitude = response.getString("longitude");

            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        });

        fragmentViewModel.fragmentTwoResponse().observe(getViewLifecycleOwner(), response -> {
            Log.e(TAG, "Fragment Two: " + response);
            try {
                wheel2 = response.getBoolean("twoWheeler");
                wheel4 = response.getBoolean("fourWheeler");
                nwsc = response.getBoolean("watersupply_nwsc");
                underground = response.getBoolean("watersupply_underground");
                others = response.getBoolean("watersupply_other");
                furnishing = response.getString("furnishing");
                tenants = response.getString("tenants");
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        fragmentViewModel.fragmentThreeResponse().observe(getViewLifecycleOwner(), response -> {
            Log.e(TAG, "Fragment Three: " + response);
            try {
                image_1 = response.getString("img_1");
                image_2 = response.getString("img_2");
                image_3 = response.getString("img_3");
                image_4 = response.getString("img_4");
                image_5 = response.getString("img_5");
                image_6 = response.getString("img_6");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        textView_viewinmaps.setOnClickListener(view12 -> {
            if (longitude.isEmpty() && latitude.isEmpty()) {
                Toast.makeText(getActivity(), "Location Not Added", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(getActivity(), MapActivity.class);
                intent.putExtra("lat", latitude);
                intent.putExtra("lon", longitude);
                intent.putExtra("location", location);
                startActivity(intent);
            }
        });
        return view;
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
        String cursorString;

        Cursor cursor = Objects.requireNonNull(getActivity()).getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            cursorString = cursor.getString(index);
            cursor.close();
            return cursorString;
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