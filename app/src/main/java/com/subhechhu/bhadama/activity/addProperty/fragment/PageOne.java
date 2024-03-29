package com.subhechhu.bhadama.activity.addProperty.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.subhechhu.bhadama.activity.addProperty.AddPropertyActivity;
import com.subhechhu.bhadama.activity.location.LocationActivity;
import com.subhechhu.bhadama.activity.location.LocationModel;
import com.subhechhu.bhadama.R;
import com.subhechhu.bhadama.activity.personalProperty.ModelPersonalProperty;
import com.subhechhu.bhadama.util.Network;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import static android.app.Activity.RESULT_OK;

public class PageOne extends Fragment {

    private static final String TAG = PageOne.class.getSimpleName();
    private static final int LOCATION_ACTIVITY = 45543;

    DatePickerDialog datepicker;
    AppCompatButton button_addprop_date, button_addprop_location;

    EditText edittext_addprop_rent;

    RadioGroup radio_group_rooms, radio_group_rent_type;

    String rent = "", latitude = "", longitude = "", location = "", city = "", roomSize = "", roomType = "", availableDate = "";
    Timer timer;

    JSONObject fieldObject;

    ModelPersonalProperty personalProperty;
    FragmentViewModel fragmentViewModel;

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    Calendar cal;
    int posessionDay, posessionMonth, posessionYear;

    public static PageOne newInstance() {
        return new PageOne();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentViewModel = ViewModelProviders.of(requireActivity()).get(FragmentViewModel.class);
        if (getActivity() instanceof AddPropertyActivity) {
            personalProperty = ((AddPropertyActivity) Objects.requireNonNull(getActivity())).getDataToEdit();
        }
        cal = Calendar.getInstance();
        fieldObject = new JSONObject();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pageone, container, false);
        button_addprop_date = view.findViewById(R.id.button_addprop_date);
        button_addprop_location = view.findViewById(R.id.button_addprop_location);
        radio_group_rent_type = view.findViewById(R.id.radio_group_rent_type);

        radio_group_rooms = view.findViewById(R.id.radio_group_rooms);

        edittext_addprop_rent = view.findViewById(R.id.edittext_addprop_rent);
        edittext_addprop_rent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (timer != null) {
                    timer.cancel();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                rent = edittext_addprop_rent.getText().toString();
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Log.e(TAG, "text changed");
                        try {
                            fieldObject.put("rent", rent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, 1500);
            }
        });

        radio_group_rooms.setOnCheckedChangeListener((radioGroup, i) -> {
            if (edittext_addprop_rent.hasFocus())
                edittext_addprop_rent.clearFocus();
            if (i == R.id.radio_room1)
                roomSize = "1";
            else if (i == R.id.radio_room2)
                roomSize = "2";
            else
                roomSize = "2+";
            try {
                fieldObject.put("roomsize", roomSize);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        radio_group_rent_type.setOnCheckedChangeListener((radioGroup, i) -> {
            if (edittext_addprop_rent.hasFocus())
                edittext_addprop_rent.clearFocus();

            if (i == R.id.radio_flat)
                roomType = "Flat";
            else if (i == R.id.radio_house)
                roomType = "House";

            if (fieldObject != null) {
                try {
                    fieldObject.put("roomType", roomType);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        button_addprop_date.setOnClickListener(view12 -> {
            if (edittext_addprop_rent.hasFocus())
                edittext_addprop_rent.clearFocus();
            final Calendar cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);
            datepicker = new DatePickerDialog(getActivity(),
                    (view1, year1, monthOfYear, dayOfMonth) -> {
                        availableDate = year1 + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        button_addprop_date.setText(getString(R.string.date, year1, (monthOfYear + 1), dayOfMonth));
                        if (fieldObject != null) {
                            try {
                                fieldObject.put("date", availableDate);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, year, month, day);
            datepicker.show();
        });

        button_addprop_location.setOnClickListener(view13 -> {
            if (edittext_addprop_rent.hasFocus())
                edittext_addprop_rent.clearFocus();
            if (Network.getConnection(getActivity()))
                startActivityForResult(new Intent(getActivity(), LocationActivity.class), LOCATION_ACTIVITY);
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (personalProperty != null) {

            //Selecting Room
            if (personalProperty.getRoomSize().equalsIgnoreCase("1"))
                radio_group_rooms.check(R.id.radio_room1);
            else if (personalProperty.getRoomSize().equalsIgnoreCase("2"))
                radio_group_rooms.check(R.id.radio_room2);
            else
                radio_group_rooms.check(R.id.radio_room3);

            //Selecting Room Type
            if (personalProperty.getRoomType().equalsIgnoreCase("Flat"))
                radio_group_rent_type.check(R.id.radio_flat);
            else if (personalProperty.getRoomType().equalsIgnoreCase("House"))
                radio_group_rent_type.check(R.id.radio_house);

            //Adding rent
            edittext_addprop_rent.setText(getString(R.string.rent_amt, personalProperty.getRent()));

            //Adding date
            try {
                Log.e(TAG, "personalProperty.getAvailableFrom(): " + personalProperty.getAvailableFrom());
                Date date = inputFormat.parse(personalProperty.getAvailableFrom());
                cal.setTime(date);

                posessionDay = cal.get(Calendar.DAY_OF_MONTH);
                posessionMonth = cal.get(Calendar.MONTH);
                posessionYear = cal.get(Calendar.YEAR);
                button_addprop_date.setText(getString(R.string.date, posessionYear, posessionMonth + 1, posessionDay));

            } catch (Exception e) {
                e.printStackTrace();
            }

            //Adding Location
            button_addprop_location.setText(personalProperty.getPlace());

            try {
                latitude = personalProperty.getLocation().getCoordinates().get(0);
                longitude = personalProperty.getLocation().getCoordinates().get(1);
                city = personalProperty.getCity();
                availableDate = posessionYear + "-" + (posessionMonth + 1) + "-" + posessionDay;
                location = personalProperty.getPlace();
                rent = "" + personalProperty.getRent();
                roomSize = personalProperty.getRoomSize();
                roomType = personalProperty.getRoomType();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == LOCATION_ACTIVITY) {
            LocationModel locationObject = data.getExtras().getParcelable("locationObject");
//            Log.e(TAG, "homemodel word: " + locationObject.toString());
            city = data.getStringExtra("city");
            Log.e(TAG, "city: " + city);
            if (city == null)
                city = "Nepal";
            location = getString(R.string.searched_location, locationObject.getDisplayPlace(), city);
            latitude = locationObject.getLat();
            longitude = locationObject.getLon();
            if (fieldObject != null) {
                try {
                    fieldObject.put("latitude", latitude);
                    fieldObject.put("longitude", longitude);
                    fieldObject.put("city", city);
                    fieldObject.put("location", location);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            button_addprop_location.setText(location);
        }
    }

    public boolean nextClickListener() {
        if (edittext_addprop_rent.hasFocus())
            edittext_addprop_rent.clearFocus();
        if (rent.isEmpty() || availableDate.isEmpty() || location.isEmpty()) {
            Toast.makeText(getActivity(), "Fields cannot be selected", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            try {
                Log.e(TAG, rent + "\n" + availableDate + "\n" + roomSize + "\n" + location + "\n" + city + "\n" + latitude + "\n" + longitude + "\n" + roomType);
                fieldObject.put("rent", rent);
                fieldObject.put("date", availableDate);
                fieldObject.put("roomsize", roomSize);
                fieldObject.put("location", location);
                fieldObject.put("city", city);
                fieldObject.put("latitude", latitude);
                fieldObject.put("longitude", longitude);
                fieldObject.put("roomType", roomType);
            } catch (JSONException e) {
                Log.e(TAG, "exception: " + e.getMessage());
                e.printStackTrace();
            }
            Log.d(TAG, "fragment one on pause data" + fieldObject);
            fragmentViewModel.postFragmentOneData(fieldObject);
            return true;
        }
    }
}