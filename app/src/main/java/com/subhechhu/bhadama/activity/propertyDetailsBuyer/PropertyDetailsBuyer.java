package com.subhechhu.bhadama.activity.propertyDetailsBuyer;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.mapbox.mapboxsdk.geometry.LatLng;
import com.subhechhu.bhadama.R;

import me.relex.circleindicator.CircleIndicator3;

public class PropertyDetailsBuyer extends AppCompatActivity {
    ViewPager2 vpPager;
    PoiAdapter adapterViewPager;
    CircleIndicator3 indicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_details);

        ImageView imageView_property_image = findViewById(R.id.imageView_property_image);
        imageView_property_image.setImageResource(getIntent().getIntExtra("img", 0));

        vpPager = findViewById(R.id.vpPager);

        adapterViewPager = new PoiAdapter(this);
        vpPager.setAdapter(adapterViewPager);

        indicator = findViewById(R.id.indicator);
        indicator.setViewPager(vpPager);

        adapterViewPager.registerAdapterDataObserver(indicator.getAdapterDataObserver());

    }

    public LatLng getLatLong() {
        return new LatLng(Double.parseDouble("27.6620568"),
                Double.parseDouble("85.3352306"));
    }
}