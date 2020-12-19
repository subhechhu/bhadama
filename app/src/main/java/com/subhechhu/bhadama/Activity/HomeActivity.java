package com.subhechhu.bhadama.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.ui.PlaceAutocompleteFragment;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.ui.PlaceSelectionListener;
import com.mohammedalaa.seekbar.DoubleValueSeekBarView;
import com.mohammedalaa.seekbar.OnDoubleValueSeekBarChangeListener;
import com.subhechhu.bhadama.Adapter.LocationAdapter;
import com.subhechhu.bhadama.R;
import com.subhechhu.bhadama.model.LocationModel;
import com.subhechhu.bhadama.viewmodels.LocationViewModel;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = HomeActivity.class.getName();
    private static final int LOCATION_ACTIVITY = 4534;

    AppCompatButton button_home_search, button_home_location;
    LinearLayout linearButton_first, linearButton_second;

    TextView textView_range_value, textView_view_in_map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        textView_range_value = findViewById(R.id.textView_range_value);
        textView_view_in_map = findViewById(R.id.textView_view_in_map);

        button_home_search = findViewById(R.id.button_home_search);
        button_home_location = findViewById(R.id.button_home_location);

        linearButton_first = findViewById(R.id.linearButton_first);
        linearButton_second = findViewById(R.id.linearButton_second);


        textView_view_in_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(button_home_location.getText().equals("Add Location")){
                    Toast.makeText(HomeActivity.this,"Add location to view in maps",Toast.LENGTH_SHORT).show();
                }
            }
        });

        button_home_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(HomeActivity.this, LocationActivity.class), LOCATION_ACTIVITY);
            }
        });


        linearButton_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Your property", Toast.LENGTH_SHORT).show();
            }
        });

        linearButton_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Saved property", Toast.LENGTH_SHORT).show();
            }
        });

        button_home_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        DoubleValueSeekBarView rangeSeekbar = findViewById(R.id.home_range_seekbar);
        textView_range_value.setText(getString(R.string.range, rangeSeekbar.getCurrentMinValue(), rangeSeekbar.getCurrentMaxValue()));

        rangeSeekbar.setOnRangeSeekBarViewChangeListener(new OnDoubleValueSeekBarChangeListener() {
            @Override
            public void onValueChanged(DoubleValueSeekBarView doubleValueSeekBarView, int i, int i1, boolean b) {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == LOCATION_ACTIVITY) {
            LocationModel locationObject = (LocationModel) data.getExtras().getParcelable("locationObject");
//            Log.e("TAG","homemodel word: "+locationObject.toString());
//            Log.e("TAG","homemodel city: "+data.getStringExtra("city"));
            button_home_location.setText(getString(R.string.searched_location,locationObject.getDisplayPlace(),data.getStringExtra("city")));
        }
    }
}