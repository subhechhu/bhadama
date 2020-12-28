package com.subhechhu.bhadama.activity.addProperty.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.subhechhu.bhadama.R;
import com.subhechhu.bhadama.activity.MapActivity;

import org.json.JSONException;

import java.util.Objects;

public class PageFour extends Fragment {

    private static final String TAG = PageFour.class.getSimpleName();

    TextView textView_rooms_count, textView_rooms_rent, textView_rooms_furnishing, textView_availabledate,
            textView_rooms_location, textView_rooms_parking, textView_rooms_tenant, textView_rooms_water,
            textView_rooms_type, textView_viewinmaps;

    String latitude = "", longitude = "", location = "", city = "", roomsize = "", rent = "", availableDate = "", roomType = "";
    String furnishing = "", tenants = "";

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

        } catch (Exception e) {
            e.printStackTrace();
        }
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

        textView_viewinmaps.setOnClickListener(view12 -> {
//            Log.e(TAG, "viewinmaps pressed, longitude: " + longitude + "\t latitude: " + latitude);
            if (longitude.isEmpty() && latitude.isEmpty()) {
                Toast.makeText(getActivity(), "Location Not Entered", Toast.LENGTH_SHORT).show();
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
}