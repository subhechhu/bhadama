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

public class PageOne extends Fragment {

    private static final int LOCATION_ACTIVITY = 45543;

    private int page;

    DatePickerDialog datepicker;
    AppCompatButton button_addprop_date, button_addprop_location;

    String latitude, longitude, location, city;

    public static PageOne newInstance() {
//        Bundle args = new Bundle();
//        args.putInt("someInt", page);
//        args.putString("someTitle", title);
//        fragmentFirst.setArguments(args);
        return new PageOne();
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

////        page = getArguments().getInt("someInt", 0);
//        title = getArguments().getString("someTitle");
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pageone, container, false);
        button_addprop_date = view.findViewById(R.id.button_addprop_date);
        button_addprop_location = view.findViewById(R.id.button_addprop_location);

        button_addprop_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                datepicker = new DatePickerDialog(getActivity(),
                        (view1, year1, monthOfYear, dayOfMonth) -> button_addprop_date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1), year, month, day);
                datepicker.show();
            }
        });
        button_addprop_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(), LocationActivity.class), LOCATION_ACTIVITY);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == LOCATION_ACTIVITY) {
            LocationModel locationObject = (LocationModel) data.getExtras().getParcelable("locationObject");
            Log.e("TAG", "homemodel word: " + locationObject.toString());
//            Log.e("TAG","homemodel city: "+data.getStringExtra("city"));
            city = data.getStringExtra("city");
            location = getString(R.string.searched_location, locationObject.getDisplayPlace(), data.getStringExtra("city"));
            latitude = locationObject.getLat();
            longitude = locationObject.getLon();
            button_addprop_location.setText(location);
        }
    }
}