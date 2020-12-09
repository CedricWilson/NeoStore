package com.example.neostore2;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class AdapterImage extends RecyclerView.Adapter<AdapterImage.ImageViewHolder> {
    private final ImageView main;
    public List<DataProductImages> mExampleList;
    Activity context;
    int selectedPosition=0;




    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public ImageView mini;


        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            mini = itemView.findViewById(R.id.ivMiniImage);

        }
    }

    public AdapterImage(List<DataProductImages> exampleList, Activity context, ImageView main) {
        mExampleList = exampleList;
        this.context = context;
        this.main= main;

    }


    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.frame_imagerow, parent, false);
        ImageViewHolder evh = new ImageViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        final DataProductImages currentItem = mExampleList.get(position);

        if(selectedPosition==position)
            holder.mini.setBackgroundColor(Color.parseColor("#30B1F2"));
        else
            holder.mini.setBackgroundColor(Color.parseColor("White"));


        Glide.with(context).load(currentItem.getImage()).into(holder.mini);

        holder.itemView.setOnClickListener(v ->{
            selectedPosition=position;
            notifyDataSetChanged();

            Glide.with(context).load(currentItem.getImage()).into(main);

        });

    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

}
