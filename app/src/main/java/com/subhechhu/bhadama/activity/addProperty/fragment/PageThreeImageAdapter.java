package com.subhechhu.bhadama.activity.addProperty.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.subhechhu.bhadama.activity.addProperty.AddPropertyActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class PageThreeImageAdapter extends RecyclerView.Adapter<PageThreeImageAdapter.MyViewHolder> {
    private static final String TAG = PageThreeImageAdapter.class.getSimpleName();
    //    private List<String> imageList = new ArrayList<>();
    private JSONArray imageList = new JSONArray();

    private final Context context;
    ClickListener clickListener;

    public interface ClickListener {
        //        void onClick(List<String> imageList);
        void onClick(JSONArray imageList);
    }


    public PageThreeImageAdapter(Context context, ClickListener clickListener) {
        this.clickListener = clickListener;
        this.context = context;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView_background, imageView_delete;
        ProgressBar progressBar_image;
        CardView cardview_parent;

        MyViewHolder(View view) {
            super(view);
            imageView_background = view.findViewById(R.id.imageview_background);
            imageView_delete = view.findViewById(R.id.imageview_delete);
            cardview_parent = view.findViewById(R.id.cardview_parent);
            progressBar_image = view.findViewById(R.id.progressBar_image);
        }
    }

    @Override
    public PageThreeImageAdapter.@NotNull MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_addimage, parent, false);
        return new MyViewHolder(itemView);
    }

    public void showList(JSONArray displayedList) {
//        this.imageList = displayedList;
        this.imageList = displayedList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(PageThreeImageAdapter.MyViewHolder holder, int position) {
        try {
            holder.imageView_delete.setOnClickListener(view ->
                    Toast.makeText(context, "Delete ", Toast.LENGTH_SHORT).show());

            String imagePath = imageList.getString(position);
            Glide
                    .with(context)
                    .asBitmap()
                    .load(imagePath)
                    .error(R.drawable.background_image_2)
                    .listener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            holder.progressBar_image.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                            holder.progressBar_image.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(holder.imageView_background);

            holder.imageView_delete.setOnClickListener(view -> {
                imageList.remove(position);
                clickListener.onClick(imageList);
                showList(imageList);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return imageList.length();
    }
}
