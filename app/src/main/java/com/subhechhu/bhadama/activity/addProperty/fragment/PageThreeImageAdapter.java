package com.subhechhu.bhadama.activity.addProperty.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.subhechhu.bhadama.R;
import com.subhechhu.bhadama.activity.addProperty.AddPropertyActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class PageThreeImageAdapter extends RecyclerView.Adapter<PageThreeImageAdapter.MyViewHolder> {
    private static final String TAG = PageThreeImageAdapter.class.getSimpleName();
    private List<String> imageList = new ArrayList<>();

    private final Context context;
    ClickListener clickListener;

    public interface ClickListener {
        void onClick(List<String> imageList);
    }


    public PageThreeImageAdapter(Context context, ClickListener clickListener) {
        this.clickListener = clickListener;
        this.context = context;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView_background, imageView_delete;
        CardView cardview_parent;

        MyViewHolder(View view) {
            super(view);
            imageView_background = view.findViewById(R.id.imageview_background);
            imageView_delete = view.findViewById(R.id.imageview_delete);
            cardview_parent = view.findViewById(R.id.cardview_parent);
        }
    }

    @Override
    public PageThreeImageAdapter.@NotNull MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_addimage, parent, false);
        return new MyViewHolder(itemView);
    }

    public void showList(List<String> displayedList) {
        Collections.reverse(displayedList);
        this.imageList = displayedList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(PageThreeImageAdapter.MyViewHolder holder, int position) {
        holder.imageView_delete.setOnClickListener(view ->
                Toast.makeText(context, "Delete ", Toast.LENGTH_SHORT).show());

        String imagePath = imageList.get(position);
        Glide
                .with(context)
                .asBitmap()
                .load(imagePath)
                .into(holder.imageView_background);

        holder.imageView_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageList.remove(position);
                clickListener.onClick(imageList);
                showList(imageList);
            }
        });

    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }
}
