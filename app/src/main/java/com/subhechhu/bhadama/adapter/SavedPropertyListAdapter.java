package com.subhechhu.bhadama.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.subhechhu.bhadama.activity.location.LocationModel;
import com.subhechhu.bhadama.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class SavedPropertyListAdapter extends RecyclerView.Adapter<SavedPropertyListAdapter.MyViewHolder> {
    private List<LocationModel> propertyModel = new ArrayList<>();

    private final Context context;
    private final ItemClick itemClick;
    Integer[] house;

    public SavedPropertyListAdapter(Context context, ItemClick itemClick, Integer[] house) {
        this.house = house;
        this.itemClick = itemClick;
        this.context = context;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView_background, imageView_fav, imageView_call;

        MyViewHolder(View view) {
            super(view);
            imageView_background = view.findViewById(R.id.imageView_property_image);
            imageView_fav = view.findViewById(R.id.imageView_fav);
            imageView_call = view.findViewById(R.id.imageView_call);
        }
    }

    @Override
    public SavedPropertyListAdapter.@NotNull MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_savedproperty, parent, false);
        return new MyViewHolder(itemView);
    }

    public void showList(List<LocationModel> displayedList) {
        this.propertyModel = displayedList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(SavedPropertyListAdapter.MyViewHolder holder, int position) {
        final LocationModel modelWord = propertyModel.get(position);
        holder.imageView_background.setImageResource(this.house[position]);

        holder.imageView_call.setOnClickListener(view -> Toast.makeText(context,"Phone a friend? ",Toast.LENGTH_SHORT).show());

        holder.imageView_fav.setOnClickListener(view -> holder.imageView_fav.setImageResource(R.drawable.ic_baseline_favorite_24));

        holder.imageView_background.setOnClickListener(v -> itemClick.onClick(modelWord, holder.imageView_background, house[position]));
    }

    @Override
    public int getItemCount() {
        return propertyModel.size();
    }

    public interface ItemClick {
        void onClick(LocationModel modelWord, ImageView imageView, int house);
    }
}
