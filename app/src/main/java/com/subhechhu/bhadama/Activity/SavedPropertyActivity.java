package com.subhechhu.bhadama.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.subhechhu.bhadama.R;

public class SavedPropertyActivity extends AppCompatActivity {

    FloatingActionButton floating_personalprop_add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_property);

        TextView textView_personalprop_empty = findViewById(R.id.textView_personalprop_empty);
        textView_personalprop_empty.setText(getIntent().getStringExtra("Message"));

        if (getIntent().getStringExtra("from").equalsIgnoreCase("Saved")) {
            ImageView imageView_arrow = findViewById(R.id.imageView_arrow);
            imageView_arrow.setVisibility(View.INVISIBLE);
        }

        findViewById(R.id.floating_personalprop_add).setVisibility(View.INVISIBLE);

    }
}