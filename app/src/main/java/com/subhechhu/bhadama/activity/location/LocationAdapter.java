package com.subhechhu.bhadama.activity.location;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.subhechhu.bhadama.R;

import java.util.ArrayList;
import java.util.List;


public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.MyViewHolder> {
    private List<LocationModel> locationModel = new ArrayList<>();

    private Context context;
    private final ItemClick itemClick;

    public LocationAdapter(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView_location;
        LinearLayout linear_locationCard;

        MyViewHolder(View view) {
            super(view);
            textView_location = view.findViewById(R.id.address);
            linear_locationCard = view.findViewById(R.id.linear_locationCard);
        }
    }

    @Override
    public LocationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_card, parent, false);
        return new MyViewHolder(itemView);
    }

    public void showList(List<LocationModel> displayedList) {
        this.locationModel = displayedList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(LocationAdapter.MyViewHolder holder, int position) {
        final LocationModel modelWord = locationModel.get(position);
        holder.textView_location.setText(modelWord.getDisplayName());
        holder.linear_locationCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClick.onClick(modelWord);
            }
        });
    }

    @Override
    public int getItemCount() {
        return locationModel.size();
    }

    public interface ItemClick {
        void onClick(LocationModel modelWord);
    }
}
