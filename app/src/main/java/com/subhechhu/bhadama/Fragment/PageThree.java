package com.subhechhu.bhadama.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.subhechhu.bhadama.R;

public class PageThree extends Fragment {

    private static final int LOCATION_ACTIVITY = 45543;

    private int page;

    DatePickerDialog datepicker;
    AppCompatButton button_addprop_date, button_addprop_location;

    String latitude, longitude, location, city;

    public static PageThree newInstance() {
//        Bundle args = new Bundle();
//        args.putInt("someInt", page);
//        args.putString("someTitle", title);
//        fragmentFirst.setArguments(args);
        return new PageThree();
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
        View view = inflater.inflate(R.layout.fragment_pagethree, container, false);
        return view;
    }
}