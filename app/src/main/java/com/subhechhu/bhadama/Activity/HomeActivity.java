package com.subhechhu.bhadama.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mohammedalaa.seekbar.DoubleValueSeekBarView;
import com.mohammedalaa.seekbar.OnDoubleValueSeekBarChangeListener;
import com.subhechhu.bhadama.R;

import java.util.Arrays;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = HomeActivity.class.getName();

    AppCompatButton button_home_search, button_home_location;
    LinearLayout linearButton_first, linearButton_second;

    TextView textView_range_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        textView_range_value = findViewById(R.id.textView_range_value);
        button_home_search = findViewById(R.id.button_home_search);
        button_home_location = findViewById(R.id.button_home_location);

        linearButton_first = findViewById(R.id.linearButton_first);
        linearButton_second = findViewById(R.id.linearButton_second);

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

        button_home_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


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
                Log.e(TAG, "value changed: first: " + i + " second: " + i1 + " boolean: " + b);
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
}