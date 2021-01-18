package com.subhechhu.bhadama.activity.propertyDetailsBuyer.map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.subhechhu.bhadama.R;

import java.util.List;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder> {

    private final List<MapModel> mData;
    private final LayoutInflater mInflater;
    private final ViewPager2 viewPager2;
    private final Context context;

    ViewPagerAdapter(Context context, List<MapModel> data, ViewPager2 viewPager2) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.viewPager2 = viewPager2;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_item_poi_map, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String animal = mData.get(position).getName();
        holder.myTextView.setText(animal);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView myTextView;
        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.textView_place);
        }
    }
}