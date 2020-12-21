package com.subhechhu.bhadama.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.subhechhu.bhadama.R;

public class PageFour extends Fragment {

    private static final int LOCATION_ACTIVITY = 45543;

    private int page;

    DatePickerDialog datepicker;
    AppCompatButton button_addprop_date, button_addprop_location;

    String latitude, longitude, location, city;

    public static PageFour newInstance() {
//        Bundle args = new Bundle();
//        args.putInt("someInt", page);
//        args.putString("someTitle", title);
//        fragmentFirst.setArguments(args);
        return new PageFour();
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        page = getArguments().getInt("someInt", 0);
//        title = getArguments().getString("someTitle");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pagefour, container, false);

        AppCompatButton button_verify_property = view.findViewById(R.id.button_verify_property);
        button_verify_property.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        return view;
    }
}