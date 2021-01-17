package com.subhechhu.bhadama.activity.propertyDetailsBuyer;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.subhechhu.bhadama.activity.propertyDetailsBuyer.fragment.PageFuel;
import com.subhechhu.bhadama.activity.propertyDetailsBuyer.fragment.PageHospital;
import com.subhechhu.bhadama.activity.propertyDetailsBuyer.fragment.PagePharmacy;
import com.subhechhu.bhadama.activity.propertyDetailsBuyer.fragment.PageShoppingCenter;

public class PoiAdapter extends FragmentStateAdapter {
    private static final int NUM_ITEMS = 4;

    public PoiAdapter(FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return PageHospital.newInstance();
        } else if (position == 1) {
            return PagePharmacy.newInstance();
        } else if (position == 2) {
            return PageShoppingCenter.newInstance();
        } else if (position == 3) {
            return PageFuel.newInstance();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return NUM_ITEMS;
    }
}