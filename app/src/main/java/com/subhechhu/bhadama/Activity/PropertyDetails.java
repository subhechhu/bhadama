package com.subhechhu.bhadama.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.subhechhu.bhadama.R;

public class PropertyDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_details);

        ImageView imageView_property_image = findViewById(R.id.imageView_property_image);
        imageView_property_image.setImageResource(getIntent().getIntExtra("img",0));
    }
}