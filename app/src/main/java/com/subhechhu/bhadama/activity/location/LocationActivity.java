package com.subhechhu.bhadama.activity.location;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.subhechhu.bhadama.R;
import com.subhechhu.bhadama.activity.addProperty.fragment.PageOne;
import com.subhechhu.bhadama.util.GetConstants;
import com.subhechhu.bhadama.util.Network;

import java.util.Timer;
import java.util.TimerTask;

public class LocationActivity extends AppCompatActivity implements LocationAdapter.ItemClick {
    private static final String TAG = LocationActivity.class.getSimpleName();

    AppCompatEditText edittext_location_search;
    ImageView button_clear;

    ProgressBar progressBar_search;
    RecyclerView recyclerview_places;

    LocationViewModel locationViewModel;
    LocationAdapter locationAdapter;

    Timer timer;

    @RequiresApi(api = Build.VERSION_CODES.M)
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
//        progressBar_search.setVisibility(View.VISIBLE);

        edittext_location_search.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(edittext_location_search, InputMethodManager.SHOW_IMPLICIT);

        recyclerview_places.setLayoutManager(new LinearLayoutManager(this));
        recyclerview_places.setHasFixedSize(true);

        recyclerview_places.setAdapter(locationAdapter);

        locationViewModel = ViewModelProviders.of(this).get(LocationViewModel.class);
        locationViewModel.getLocationRepository().observe(this, locationResponse -> {
            Log.d(TAG, "===name in main activity: " + locationResponse);
            progressBar_search.setVisibility(View.INVISIBLE);
            if (locationResponse != null)
                locationAdapter.showList(locationResponse);
            else
                Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show();
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
                            Log.e(TAG, "text found: " + editable.toString());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (Network.getConnection(LocationActivity.this))
                                        locationViewModel.makeGetRequest(editable.toString(), GetConstants.LOGIN_REQUESTCODE);
                                }
                            });
                        }
                    }, 2000);
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
        Intent intent = new Intent();
        intent.putExtra("locationObject", modelWord);
        intent.putExtra("city", modelWord.getAddress().getCity());
        setResult(RESULT_OK, intent);
        finish();
    }
}