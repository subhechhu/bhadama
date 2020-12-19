package com.subhechhu.bhadama.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.subhechhu.bhadama.Adapter.LocationAdapter;
import com.subhechhu.bhadama.R;
import com.subhechhu.bhadama.Model.LocationModel;
import com.subhechhu.bhadama.ViewModel.LocationViewModel;

import java.util.Timer;
import java.util.TimerTask;

public class LocationActivity extends AppCompatActivity implements LocationAdapter.ItemClick {

    LocationViewModel locationViewModel;
    LocationAdapter locationAdapter;

    AppCompatEditText edittext_location_search;
    ImageView button_clear;

    ProgressBar progressBar_search;

    RecyclerView recyclerview_places;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        locationAdapter = new LocationAdapter(this);

        recyclerview_places = findViewById(R.id.recyclerview_places);
        edittext_location_search = findViewById(R.id.edittext_location_search);
        button_clear = findViewById(R.id.button_clear);
        button_clear.setVisibility(View.INVISIBLE);

        progressBar_search = findViewById(R.id.progressBar_location);
        progressBar_search.setVisibility(View.VISIBLE);

        recyclerview_places.setLayoutManager(new LinearLayoutManager(this));
        recyclerview_places.setHasFixedSize(true);

        recyclerview_places.setAdapter(locationAdapter);

        locationViewModel = ViewModelProviders.of(this).get(LocationViewModel.class);
        locationViewModel.init();
        locationViewModel.getLocation("Nepal");
        locationViewModel.getLocationRepository().observe(this, newsResponse -> {
            Log.d("TAG", "===name in main activity: " + newsResponse);
            progressBar_search.setVisibility(View.INVISIBLE);
            locationAdapter.showList(newsResponse);
        });

        button_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edittext_location_search.setText("");
            }
        });


        edittext_location_search.addTextChangedListener(new TextWatcher() {
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
                if (editable.length() == 0) {
                    progressBar_search.setVisibility(View.INVISIBLE);
                    button_clear.setVisibility(View.INVISIBLE);
                } else {
                    button_clear.setVisibility(View.VISIBLE);
                }

                if (editable.length() >= 3) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Log.e("TAG", "text found: " + editable.toString());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    locationViewModel.getLocation(editable.toString());
                                }
                            });
                        }
                    }, 3000);
                    progressBar_search.setVisibility(View.VISIBLE);
                } else {
                    if (timer != null) {
                        progressBar_search.setVisibility(View.INVISIBLE);
                        timer.cancel();
                    }
                }
            }
        });
    }

    @Override
    public void onClick(LocationModel modelWord) {
        Toast.makeText(LocationActivity.this, modelWord.getDisplayPlace(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra("locationObject", modelWord);
        intent.putExtra("city", modelWord.getAddress().getCity());
        setResult(RESULT_OK, intent);
        finish();
    }
}