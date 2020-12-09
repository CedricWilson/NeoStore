package com.example.neostore2;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ExampleViewHolder> {
    public List<DataProduct> mExampleList;
    Activity context;

    public class ExampleViewHolder extends RecyclerView.ViewHolder {
        public ImageView pImage;
        public TextView pName;
        public TextView pPrice;
        public TextView pProducer;
        public RatingBar pRating;



        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            pImage = itemView.findViewById(R.id.ivPImage);
            pName = itemView.findViewById(R.id.tvPName);
            pPrice = itemView.findViewById(R.id.tvPrice);
            pProducer = itemView.findViewById(R.id.tvProducer);
            pRating = itemView.findViewById(R.id.ratingBar);
        }
    }



    public AdapterProduct(List<DataProduct> exampleList, Activity context) {
        mExampleList = exampleList;
        this.context = context;

    }



    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.frame_productlist, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {

        final DataProduct currentItem = mExampleList.get(position);
        Glide.with(context).load(currentItem.getProductImage()).into(holder.pImage);


        holder.pName.setText(currentItem.getName());
        holder.pProducer.setText(currentItem.getProducer());
        holder.pPrice.setText("Rs. "+format(currentItem.getCost()));
        holder.pRating.setRating(currentItem.getRating());


        holder.itemView.setOnClickListener(v -> {
            Intent details = new Intent(context, ActivityDetails.class);
            details.putExtra("id", currentItem.getId());
            context.startActivity(details);
        });


//        holder.itemView.setOnClickListener(v -> {
//
//            SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString(fname, currentItem.getFirst_name());
//            editor.putString(sname, currentItem.getLast_name());
//            editor.putString(mail, currentItem.getEmail());
//            editor.putString(avatar, currentItem.getAvatar());
//            editor.apply();
//
//            Intent i = new Intent(context , Details.class);
//            context.startActivity(i);
//
//        });


    }


    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    private String format(String amount) {
        int number = Integer.valueOf(amount);
        return NumberFormat.getNumberInstance(new Locale("en", "in")).format(number);
    }
}
