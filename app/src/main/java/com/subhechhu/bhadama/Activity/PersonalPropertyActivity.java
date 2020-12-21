package com.subhechhu.bhadama.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.subhechhu.bhadama.Adapter.VPagerAdapter;
import com.subhechhu.bhadama.R;

import static androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class PersonalPropertyActivity extends AppCompatActivity {

    FloatingActionButton floating_personalprop_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_property);

        TextView textView_personalprop_empty = findViewById(R.id.textView_personalprop_empty);
        textView_personalprop_empty.setText(getIntent().getStringExtra("Message"));


        floating_personalprop_add = findViewById(R.id.floating_personalprop_add);

        floating_personalprop_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(PersonalPropertyActivity.this, AddPropertyActivity.class), 654);
            }
        });
    }
}