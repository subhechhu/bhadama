package com.subhechhu.bhadama.activity.addProperty;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.subhechhu.bhadama.activity.addProperty.fragment.PageFour;
import com.subhechhu.bhadama.activity.addProperty.fragment.PageOne;
import com.subhechhu.bhadama.activity.addProperty.fragment.PageThree;
import com.subhechhu.bhadama.activity.addProperty.fragment.PageTwo;

public class addPropertyAdapter extends FragmentStateAdapter {
    private static final int NUM_ITEMS = 4;

    public addPropertyAdapter(FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return PageOne.newInstance();
        } else if (position == 1) {
            return PageTwo.newInstance();
        } else if (position == 2) {
            return PageThree.newInstance();
        } else if (position == 3) {
            return PageFour.newInstance();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return NUM_ITEMS;
    }
}