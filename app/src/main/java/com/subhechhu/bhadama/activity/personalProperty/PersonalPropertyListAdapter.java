package com.subhechhu.bhadama.activity.personalProperty;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.subhechhu.bhadama.R;

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
        ProgressBar progressBar_image;
        CardView parentlayout;

        String imageUrl;

        MyViewHolder(View view) {
            super(view);
            imageUrl = "";
            parentlayout = view.findViewById(R.id.parentlayout);
            imageView_background = view.findViewById(R.id.imageView_property_image);
            imageView_verified = view.findViewById(R.id.imageView_verified);
            textView_rent = view.findViewById(R.id.textView_rent);
            textView_location = view.findViewById(R.id.textView_location);
            progressBar_image = view.findViewById(R.id.progressBar_image);
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

        if (property.getImages().size() > 0) {
            holder.imageUrl = property.getImages().get(0);
        } else {
            holder.imageUrl = "";
        }

        Glide
                .with(context)
                .asBitmap()
                .load(holder.imageUrl)
                .error(R.drawable.background_image_2)
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        holder.progressBar_image.setVisibility(View.INVISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar_image.setVisibility(View.INVISIBLE);
                        return false;
                    }
                })
                .into(holder.imageView_background);

        holder.parentlayout.setOnClickListener(view -> {
            ((PersonalPropertyActivity)context).propertyDetailView(propertyList.get(position),holder.imageView_background);
        });
    }

    @Override
    public int getItemCount() {
        return propertyList.size();
    }
}
