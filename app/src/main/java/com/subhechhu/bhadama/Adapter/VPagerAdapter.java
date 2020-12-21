package com.subhechhu.bhadama.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.subhechhu.bhadama.Fragment.PageFour;
import com.subhechhu.bhadama.Fragment.PageOne;
import com.subhechhu.bhadama.Fragment.PageThree;
import com.subhechhu.bhadama.Fragment.PageTwo;

import org.jetbrains.annotations.NotNull;

public class VPagerAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 4;

    public VPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @NotNull
    @Override
    public Fragment getItem(int position) {
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
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }
}