package com.subhechhu.bhadama.activity.addProperty.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.subhechhu.bhadama.R;
import com.subhechhu.bhadama.activity.MapActivity;
import com.subhechhu.bhadama.activity.addProperty.AddPropertyActivity;
import com.subhechhu.bhadama.activity.personalProperty.ModelPersonalProperty;
import com.subhechhu.bhadama.util.Network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class PageFour extends Fragment {

    private static final String TAG = PageFour.class.getSimpleName();

    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    TextView textView_rooms_count, textView_rooms_rent, textView_rooms_furnishing, textView_availabledate,
            textView_rooms_location, textView_rooms_parking, textView_rooms_tenant, textView_rooms_water,
            textView_rooms_type, textView_viewinmaps;

    ProgressBar progressbar_summary;

    LinearLayout cardview_holder;

    String latitude = "", longitude = "", location = "", city = "", roomsize = "", rent = "",
            availableDate = "", roomType = "";
    String dateCal;
    String tenants = "";
    boolean furnishing;

    boolean wheel2, wheel4, nwsc, underground, others;
    JSONArray imageArray;

    FragmentViewModel fragmentViewModel;
    ModelPersonalProperty personalProperty;

    public static PageFour newInstance() {
        return new PageFour();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentViewModel = ViewModelProviders.of(requireActivity()).get(FragmentViewModel.class);
        if (getActivity() instanceof AddPropertyActivity) {
            personalProperty = ((AddPropertyActivity) Objects.requireNonNull(getActivity())).getDataToEdit();
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
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
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

        progressbar_summary = view.findViewById(R.id.progressbar_summary);

        cardview_holder = view.findViewById(R.id.cardview_holder);

        AppCompatButton button_verify_property = view.findViewById(R.id.button_verify_property);

        if (personalProperty != null) {
            button_verify_property.setText(R.string.updateProperty);
        } else {
            button_verify_property.setText(R.string.publishproperty);
        }

        button_verify_property.setOnClickListener(view1 ->
        {
            if (Network.getConnection(getActivity())) {
                try {
                    if (personalProperty != null) {
                        JSONObject mainObject = new JSONObject();
                        if (!personalProperty.getRoomSize().equalsIgnoreCase(roomsize))
                            mainObject.put("roomSize", roomsize);
                        if (!personalProperty.getRoomType().equalsIgnoreCase(roomType))
                            mainObject.put("roomtype", roomType);
                        if (!String.valueOf(personalProperty.getRent()).equalsIgnoreCase(rent))
                            mainObject.put("rent", rent);
                        if (!personalProperty.getPlace().equalsIgnoreCase(location))
                            mainObject.put("place", location);
                        if (!personalProperty.getCity().equalsIgnoreCase(city))
                            mainObject.put("city", city);
                        if (!personalProperty.getFurnishing().equalsIgnoreCase("" + furnishing))
                            mainObject.put("furnishing", furnishing);
                        if (!personalProperty.getTenants().equalsIgnoreCase(tenants))
                            mainObject.put("tenants", tenants);
                        if (personalProperty.isWaterSupplyNwscc() != nwsc)
                            mainObject.put("waterSupplyNwscc", nwsc);
                        if (personalProperty.isWaterSupplyOther() != others)
                            mainObject.put("waterSupplyOther", others);
                        if (personalProperty.isWaterSupplyUnderground() != underground)
                            mainObject.put("waterSupplyUnderground", underground);
                        if (personalProperty.isTwoWheeler() != wheel2)
                            mainObject.put("twoWheeler", wheel2);
                        if (personalProperty.isFourWheeler() != wheel4)
                            mainObject.put("fourWheeler", wheel4);

                        if (personalProperty.getImages().size() != imageArray.length()) {
                            for (int i = 0; i < imageArray.length(); i++) {
                                if (!imageArray.getString(i).startsWith("https://")) {
                                    mainObject.put("images", imageArray);
                                    break;
                                }
                            }
                            mainObject.put("images", imageArray);
                        }


                        Date date = inputFormat.parse(personalProperty.getAvailableFrom());
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(date);

                        int year = cal.get(Calendar.YEAR);
                        int month = cal.get(Calendar.MONTH);
                        int day = cal.get(Calendar.DAY_OF_MONTH);

                        dateCal = year + "-" + (month + 1) + "-" + day;
                        if (!dateCal.equalsIgnoreCase(availableDate))
                            mainObject.put("availableFrom", availableDate);

                        try {
                            if (!personalProperty.getLocation().getCoordinates().get(0).equalsIgnoreCase(latitude) &&
                                    !personalProperty.getLocation().getCoordinates().get(1).equalsIgnoreCase(longitude)) {
                                JSONArray locationArray = new JSONArray();
                                locationArray.put(latitude);
                                locationArray.put(longitude);
                                mainObject.put("location", new JSONObject().put("coordinates", locationArray));
                            }

                        } catch (Exception e) {
                            JSONArray locationArray = new JSONArray();
                            locationArray.put(latitude);
                            locationArray.put(longitude);
                            mainObject.put("location", new JSONObject().put("coordinates", locationArray));
                        }
                        Log.e(TAG, "final request body edit: " + mainObject.toString());
                        ((AddPropertyActivity) Objects.requireNonNull(getActivity())).uploadData(mainObject);
                    } else {
                        JSONObject mainObject = new JSONObject();
                        mainObject.put("roomSize", roomsize);
                        mainObject.put("roomtype", roomType);
                        mainObject.put("rent", rent);
                        mainObject.put("availableFrom", availableDate);
                        mainObject.put("place", location);
                        mainObject.put("city", city);
                        mainObject.put("furnishing", furnishing);
                        mainObject.put("tenants", tenants);
                        mainObject.put("waterSupplyOther", others);
                        mainObject.put("waterSupplyNwscc", nwsc);
                        mainObject.put("waterSupplyUnderground", underground);
                        mainObject.put("twoWheeler", wheel2);
                        mainObject.put("fourWheeler", wheel4);

                        JSONArray locationArray = new JSONArray();
                        locationArray.put(latitude);
                        locationArray.put(longitude);
                        mainObject.put("location", new JSONObject().put("coordinates", locationArray));

                        if (imageArray.length() > 0)
                            mainObject.put("images", imageArray);

                        Log.e(TAG, "final request body new: " + mainObject.toString());
                        ((AddPropertyActivity) Objects.requireNonNull(getActivity())).uploadData(mainObject);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        fragmentViewModel.fragmentOneResponse().observe(getViewLifecycleOwner(), response -> {
            try {
                rent = response.getString("rent");
                roomType = response.getString("roomType");
                roomsize = response.getString("roomsize");
                availableDate = response.getString("date");
                city = response.getString("location");
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
                furnishing = response.getBoolean("furnishing");
                tenants = response.getString("tenants");
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        fragmentViewModel.fragmentThreeResponse().observe(getViewLifecycleOwner(), response -> {
            Log.e(TAG, "Fragment Three: " + response);
            try {
                imageArray = response.getJSONArray("imageArray");
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

    @Override
    public void onResume() {
        super.onResume();
        try {
            progressbar_summary.setVisibility(View.INVISIBLE);

            if (roomsize.equalsIgnoreCase("1"))
                textView_rooms_count.setText(getString(R.string.total_room, roomsize, "Room"));
            else
                textView_rooms_count.setText(getString(R.string.total_room, roomsize, "Rooms"));
            textView_rooms_rent.setText(getString(R.string.rent_amount, rent));
            textView_availabledate.setText(availableDate);
            textView_rooms_location.setText(location);
            textView_rooms_type.setText(roomType);

            if (furnishing)
                textView_rooms_furnishing.setText(R.string.furnished);
            else
                textView_rooms_furnishing.setText(R.string.unfurnished);
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
            } else if (others) {
                textView_rooms_water.setText(getString(R.string.other));
            } else
                textView_rooms_water.setText(getString(R.string.nowater));

            cardview_holder.removeAllViews();
            renderImage();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void renderImage() {
        try {
            Log.e(TAG, "render image length: " + imageArray.length());
            for (int i = 0; i < imageArray.length(); i++) {
                Log.e(TAG, "render image called: " + imageArray.get(i));
                View row = getLayoutInflater().inflate(R.layout.row_item_addimage_fourth, null);
                ImageView imageview_background = row.findViewById(R.id.imageview_background);
                ProgressBar progressBar_image = row.findViewById(R.id.progressBar_image);
                Glide
                        .with(Objects.requireNonNull(getActivity()))
                        .asBitmap()
                        .load(imageArray.getString(i))
                        .centerCrop()
                        .error(R.drawable.background_image_2)
                        .listener(new RequestListener<Bitmap>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                progressBar_image.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                progressBar_image.setVisibility(View.GONE);
                                return false;
                            }
                        })
                        .into(imageview_background);

                cardview_holder.addView(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"onDestroy() PageFour.java");
    }
}