package com.subhechhu.bhadama.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.mohammedalaa.seekbar.DoubleValueSeekBarView;
import com.mohammedalaa.seekbar.OnDoubleValueSeekBarChangeListener;
import com.subhechhu.bhadama.activity.location.LocationActivity;
import com.subhechhu.bhadama.activity.location.LocationModel;
import com.subhechhu.bhadama.R;
import com.subhechhu.bhadama.activity.personalProperty.PersonalPropertyActivity;
import com.subhechhu.bhadama.util.Network;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = HomeActivity.class.getName();
    private static final int LOCATION_ACTIVITY = 4534;

    AppCompatButton button_home_search, button_home_location;

    FloatingActionButton floating_icon_personal, floating_icon_saved;
    FloatingActionMenu floating_icon;

    TextView textView_range_value, textView_view_in_map;

    CheckBox checkbox_room1, checkbox_room2, checkbox_room3, checkbox_entireFlat, checkbox_entireHouse;

    String latitude, longitude, location, city;
    int maxAmount, minAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        textView_range_value = findViewById(R.id.textView_range_value);
        textView_view_in_map = findViewById(R.id.textView_view_in_map);

        button_home_search = findViewById(R.id.button_home_search);
        button_home_location = findViewById(R.id.button_home_location);

        floating_icon_personal = findViewById(R.id.floating_icon_personal);
        floating_icon_saved = findViewById(R.id.floating_icon_saved);
        floating_icon = findViewById(R.id.floating_icon);

        checkbox_room1 = findViewById(R.id.checkbox_room1);
        checkbox_room2 = findViewById(R.id.checkbox_room2);
        checkbox_room3 = findViewById(R.id.checkbox_room3);
        checkbox_entireFlat = findViewById(R.id.checkbox_flat);
        checkbox_entireHouse = findViewById(R.id.checkbox_house);

        textView_view_in_map.setOnClickListener(view -> {
            if (hasInternet()) {
                if (button_home_location.getText().equals("Add Location")) {
                    Toast.makeText(HomeActivity.this, "Add location to view in maps", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(HomeActivity.this, MapActivity.class);
                    intent.putExtra("lat", latitude);
                    intent.putExtra("lon", longitude);
                    intent.putExtra("location", location);
                    startActivity(intent);
                }
            }
        });

        button_home_location.setOnClickListener(view -> {
            if (hasInternet()) {
                startActivityForResult(new Intent(HomeActivity.this, LocationActivity.class), LOCATION_ACTIVITY);
            }
        });


        floating_icon_personal.setOnClickListener(view -> {
            floating_icon.close(true);
            if (hasInternet()) {
                Intent intent = new Intent(HomeActivity.this, PersonalPropertyActivity.class);
                startActivity(intent);
            }
        });

        floating_icon_saved.setOnClickListener(view -> {
            floating_icon.close(true);
            if (hasInternet()) {
                Intent intent = new Intent(HomeActivity.this, SavedPropertyActivity.class);
                intent.putExtra("Message", "");
                intent.putExtra("from", "saved");
                startActivity(intent);
            }
        });

        button_home_search.setOnClickListener(view -> {
//                if (button_home_location.getText().equals("Add Location")) {
//                    Toast.makeText(HomeActivity.this, "Add location to search", Toast.LENGTH_SHORT).show();
//                } else {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("lat", latitude);
                jsonObject.put("long", longitude);
                jsonObject.put("city", city);
                jsonObject.put("maxAmount", minAmount);
                jsonObject.put("minAmount", minAmount);
                jsonObject.put("room1", checkbox_room1.isChecked());
                jsonObject.put("room2", checkbox_room2.isChecked());
                jsonObject.put("room3", checkbox_room3.isChecked());
                jsonObject.put("entireFlat", checkbox_entireFlat.isChecked());
                jsonObject.put("entireHouse", checkbox_entireHouse.isChecked());

                Log.e(TAG, "json to server: " + jsonObject.toString());

                Intent intent = new Intent(HomeActivity.this, SavedPropertyActivity.class);
                intent.putExtra("Message", "");
                intent.putExtra("from", "saved");
                startActivity(intent);

            } catch (JSONException e) {
                e.printStackTrace();
            }
//                }
        });

        DoubleValueSeekBarView rangeSeekbar = findViewById(R.id.home_range_seekbar);
        textView_range_value.setText(getString(R.string.range, rangeSeekbar.getCurrentMinValue(), rangeSeekbar.getCurrentMaxValue()));

        rangeSeekbar.setOnRangeSeekBarViewChangeListener(new OnDoubleValueSeekBarChangeListener() {
            @Override
            public void onValueChanged(DoubleValueSeekBarView doubleValueSeekBarView, int i, int i1, boolean b) {
                maxAmount = i1;
                minAmount = i;
                textView_range_value.setText(getString(R.string.range, i, i1));
            }

            @Override
            public void onStopTrackingTouch(DoubleValueSeekBarView doubleValueSeekBarView, int i, int i1) {

            }

            @Override
            public void onStartTrackingTouch(DoubleValueSeekBarView doubleValueSeekBarView, int i, int i1) {

            }
        });
    }

    public boolean hasInternet() {
        return Network.getConnection(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == LOCATION_ACTIVITY) {
            LocationModel locationObject = data.getExtras().getParcelable("locationObject");
            Log.e(TAG, "homemodel word: " + locationObject.toString());
            city = data.getStringExtra("city");
            if (data.getStringExtra("city") == null) {
                city = "Nepal";
            }
            location = getString(R.string.searched_location, locationObject.getDisplayPlace(), city);
            latitude = locationObject.getLat();
            longitude = locationObject.getLon();
            button_home_location.setText(location);
        }
    }

    @Override
    public void onBackPressed() {
        if (floating_icon.isOpened())
            floating_icon.close(true);
        else
            super.onBackPressed();
    }
}