package com.subhechhu.bhadama.Fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.subhechhu.bhadama.Activity.LocationActivity;
import com.subhechhu.bhadama.Model.LocationModel;
import com.subhechhu.bhadama.R;

import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

public class PageTwo extends Fragment {

    private static final int LOCATION_ACTIVITY = 45543;

    private int page;

    DatePickerDialog datepicker;
    AppCompatButton button_addprop_date, button_addprop_location;

    String latitude, longitude, location, city;

    public static PageTwo newInstance() {
//        Bundle args = new Bundle();
//        args.putInt("someInt", page);
//        args.putString("someTitle", title);
//        fragmentFirst.setArguments(args);
        return new PageTwo();
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
        View view = inflater.inflate(R.layout.fragment_pagetwo, container, false);
        return view;
    }
}