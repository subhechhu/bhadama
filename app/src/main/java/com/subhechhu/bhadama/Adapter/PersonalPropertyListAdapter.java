package com.subhechhu.bhadama.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.subhechhu.bhadama.Model.LocationModel;
import com.subhechhu.bhadama.R;

import java.util.ArrayList;
import java.util.List;


public class PersonalPropertyListAdapter extends RecyclerView.Adapter<PersonalPropertyListAdapter.MyViewHolder> {
    private List<LocationModel> propertyModel = new ArrayList<>();

    private Context context;
    //    private final ItemClick itemClick;
    Integer house[];

    public PersonalPropertyListAdapter(Integer house[]) {
        this.house = house;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView_location;
        LinearLayout linear_locationCard;

        ImageView imageView_background;

        MyViewHolder(View view) {
            super(view);
            imageView_background = view.findViewById(R.id.imageView_property_image);
//            textView_location = view.findViewById(R.id.address);
//            linear_locationCard = view.findViewById(R.id.linear_locationCard);
        }
    }

    @Override
    public PersonalPropertyListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.property_card_personal, parent, false);
        return new MyViewHolder(itemView);
    }

    public void showList(List<LocationModel> displayedList) {
        this.propertyModel = displayedList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(PersonalPropertyListAdapter.MyViewHolder holder, int position) {
        final LocationModel modelWord = propertyModel.get(position);
        holder.imageView_background.setImageResource(this.house[position]);
//        holder.textView_location.setText(modelWord.getDisplayName());
//        holder.linear_locationCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return propertyModel.size();
    }

//    public interface ItemClick {
//        void onClick(LocationModel modelWord);
//    }
}
