package com.subhechhu.bhadama.activity.addProperty;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.subhechhu.bhadama.activity.addProperty.fragment.PageFour;
import com.subhechhu.bhadama.activity.addProperty.fragment.PageOne;
import com.subhechhu.bhadama.activity.addProperty.fragment.PageThree;
import com.subhechhu.bhadama.activity.addProperty.fragment.PageTwo;

public class AddPropertyAdapter extends FragmentStateAdapter {
    private static final int NUM_ITEMS = 4;

    PageOne pageOne;
    PageTwo pageTwo;
    PageThree pageThree;
    PageFour pageFour;

    public AddPropertyAdapter(FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            pageOne = PageOne.newInstance();
            return pageOne;
        } else if (position == 1) {
            pageTwo = PageTwo.newInstance();
            return pageTwo;
        } else if (position == 2) {
            pageThree = PageThree.newInstance();
            return pageThree;
        } else if (position == 3) {
            pageFour = PageFour.newInstance();
            return pageFour;
        }
        return null;
    }

    public Fragment getFragment(int position) {
        if (position == 0)
            return pageOne;
        else if (position == 1)
            return pageTwo;
        else if (position == 2)
            return pageThree;
        else
            return pageFour;
    }

    @Override
    public int getItemCount() {
        return NUM_ITEMS;
    }
}