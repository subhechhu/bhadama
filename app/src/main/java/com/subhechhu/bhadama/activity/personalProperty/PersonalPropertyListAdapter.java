package com.subhechhu.bhadama.activity.personalProperty;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.subhechhu.bhadama.R;
import com.subhechhu.bhadama.activity.propertyDetailsSeller.PropertyDetailsSeller;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class PersonalPropertyListAdapter extends RecyclerView.Adapter<PersonalPropertyListAdapter.MyViewHolder> {
    private static final String TAG = PersonalPropertyListAdapter.class.getSimpleName();
    private List<ModelPersonalProperty> propertyList = new ArrayList<>();

    private final Context context;

    public PersonalPropertyListAdapter(Context context) {
        this.context = context;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView_background, imageView_verified;
        TextView textView_rent, textView_location;
        CardView parentlayout;

        MyViewHolder(View view) {
            super(view);
            parentlayout = view.findViewById(R.id.parentlayout);
            imageView_background = view.findViewById(R.id.imageView_property_image);
            imageView_verified = view.findViewById(R.id.imageView_verified);
            textView_rent = view.findViewById(R.id.textView_rent);
            textView_location = view.findViewById(R.id.textView_location);
        }
    }

    @Override
    public PersonalPropertyListAdapter.@NotNull MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_personalproperty, parent, false);
        return new MyViewHolder(itemView);
    }

    public void showList(List<ModelPersonalProperty> displayedList) {
        this.propertyList = displayedList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(PersonalPropertyListAdapter.MyViewHolder holder, int position) {
        holder.imageView_verified.setOnClickListener(view -> {
            if (propertyList.get(position).isApproved())
                Toast.makeText(context, "Property Verified", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(context, "Property Verification Pending", Toast.LENGTH_SHORT).show();
        });

        final ModelPersonalProperty property = propertyList.get(position);
        holder.textView_rent.setText(context.getString(R.string.rent_amount_int, propertyList.get(position).getRent()));
        holder.textView_location.setText(propertyList.get(position).getPlace());
        if (propertyList.get(position).isApproved()) {
            holder.imageView_verified.setImageResource(R.drawable.ic_baseline_check_circle_24);
        } else {
            holder.imageView_verified.setImageResource(R.drawable.ic_baseline_report_24);
        }
        if (propertyList.get(position).getImages().size() != 0) {
            Glide
                    .with(context)
                    .asBitmap()
                    .load(property.getImages().get(0))
                    .error(R.drawable.background_image_2)
                    .into(holder.imageView_background);
            holder.imageView_background.setImageAlpha(100);
        }
        else
            holder.imageView_background.setImageAlpha(80);

        holder.parentlayout.setOnClickListener(view -> {
            Gson gson = new Gson();
            String propertyJson = gson.toJson(propertyList.get(position));
            Intent intent = new Intent(context, PropertyDetailsSeller.class);
//            if (propertyList.get(position).getImages().size() != 0)
            intent.putExtra("img", R.drawable.icon_main);
//            else
//                intent.putExtra("img", propertyList.get(position).getImages().get(0));
            intent.putExtra("data", propertyJson);
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation((Activity) context, holder.imageView_background, "backgroundImage");
            context.startActivity(intent, options.toBundle());
        });
    }

    @Override
    public int getItemCount() {
        return propertyList.size();
    }
}
