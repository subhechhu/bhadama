package com.subhechhu.bhadama.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import com.subhechhu.bhadama.Adapter.LocationAdapter;
import com.subhechhu.bhadama.R;
import com.subhechhu.bhadama.model.LocationModel;
import com.subhechhu.bhadama.viewmodels.LocationViewModel;

public class LocationActivity extends AppCompatActivity implements LocationAdapter.ItemClick {

    LocationViewModel locationViewModel;
    LocationAdapter locationAdapter;

    RecyclerView recyclerview_places;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        locationAdapter = new LocationAdapter(this);

        recyclerview_places = findViewById(R.id.recyclerview_places);
        recyclerview_places.setLayoutManager(new LinearLayoutManager(this));
        recyclerview_places.setHasFixedSize(true);

        recyclerview_places.setAdapter(locationAdapter);

        locationViewModel = ViewModelProviders.of(this).get(LocationViewModel.class);
        locationViewModel.init("Dantakali");
        locationViewModel.getLocationRepository().observe(this, newsResponse -> {
            Log.d("TAG", "name in main activity: " + newsResponse);
            locationAdapter.showList(newsResponse);
        });
    }

    @Override
    public void onClick(LocationModel modelWord) {
        Toast.makeText(LocationActivity.this,modelWord.getDisplayPlace(),Toast.LENGTH_SHORT).show();
        Intent intent=new Intent();
        intent.putExtra("locationObject", modelWord);
        intent.putExtra("city",modelWord.getAddress().getCity());
        setResult(RESULT_OK,intent);
        finish();
    }
}