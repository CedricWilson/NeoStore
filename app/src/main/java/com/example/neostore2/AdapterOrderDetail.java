package com.example.neostore2;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AdapterOrderDetail extends RecyclerView.Adapter<AdapterOrderDetail.ExampleViewHolder> {
    public List<DataSubOrderDetail> mExampleList;
    Activity context;

    public class ExampleViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView category;
        public TextView name;
        public TextView price;
        public TextView quantity;

        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.ivOrderPic);
            category = itemView.findViewById(R.id.tvOrderDCategory);
            name = itemView.findViewById(R.id.tvOrderDName);
            price = itemView.findViewById(R.id.tvOrderDPrice);
            quantity = itemView.findViewById(R.id.tvOrderDQuantity);

        }
    }

    public AdapterOrderDetail(List<DataSubOrderDetail> exampleList,Activity context) {
        mExampleList = exampleList;
        this.context = context;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.frame_orderdetail, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {

        final DataSubOrderDetail currentItem = mExampleList.get(position);

        holder.name.setText(currentItem.getName());
        holder.quantity.setText(currentItem.getQuantity());
        holder.price.setText("Rs. "+format(currentItem.getTotal()));
        holder.category.setText("("+currentItem.getCategory()+")");

        Glide.with(context).load(currentItem.getImage()).into(holder.image);


    }

    private String format(String amount) {
        int number = Integer.valueOf(amount);
        return NumberFormat.getNumberInstance(new Locale("en", "in")).format(number);
    }


    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

}
