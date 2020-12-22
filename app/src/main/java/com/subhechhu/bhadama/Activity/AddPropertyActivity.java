package com.subhechhu.bhadama.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.subhechhu.bhadama.Adapter.VPagerAdapter;
import com.subhechhu.bhadama.R;

import static androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class AddPropertyActivity extends AppCompatActivity {

    FloatingActionButton floating_personalprop_next, floating_personalprop_prev;
    VPagerAdapter adapterViewPager;

    int currentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property);

        floating_personalprop_next = findViewById(R.id.floating_personalprop_next);
        floating_personalprop_prev = findViewById(R.id.floating_personalprop_previous);

        ViewPager vpPager = findViewById(R.id.vpPager);
        adapterViewPager = new VPagerAdapter(getSupportFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpPager.setAdapter(adapterViewPager);

        floating_personalprop_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vpPager.setCurrentItem(currentPosition + 1,true);
            }
        });

        floating_personalprop_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vpPager.setCurrentItem(currentPosition - 1,true);
            }
        });

        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.e("TAG", "position: " + position);
                currentPosition = position;
                if (position == 0) {
                    floating_personalprop_prev.setVisibility(View.INVISIBLE);
                }
                if (position != 0 && position != 3) {
                    floating_personalprop_prev.setVisibility(View.VISIBLE);
                    floating_personalprop_next.setVisibility(View.VISIBLE);
                }
                if (position == 3) {
                    floating_personalprop_next.setVisibility(View.INVISIBLE);
                    floating_personalprop_prev.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}