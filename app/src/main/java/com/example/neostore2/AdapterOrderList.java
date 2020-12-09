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
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AdapterOrderList extends RecyclerView.Adapter<AdapterOrderList.ExampleViewHolder> {
    public List<DataOrderList> mExampleList;
    Activity context;

    public class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView oID;
        public TextView oDate;
        public TextView oPrice;

        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);

            oID = itemView.findViewById(R.id.tvOrderId);
            oDate = itemView.findViewById(R.id.tvOrderDate);
            oPrice = itemView.findViewById(R.id.tvOrderPrice);

        }
    }

    public AdapterOrderList(List<DataOrderList> exampleList,Activity context) {
        mExampleList = exampleList;
        this.context = context;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.frame_orderlist, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {

        final DataOrderList currentItem = mExampleList.get(position);

        holder.oID.setText(currentItem.getId());
        holder.oDate.setText(currentItem.getDate());
        holder.oPrice.setText("Rs. "+format(currentItem.getCost()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent orderdetail = new Intent(context, ActivityOrderDetails.class);
                orderdetail.putExtra("id", currentItem.getId());
                context.startActivity(orderdetail);
            }
        });

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
