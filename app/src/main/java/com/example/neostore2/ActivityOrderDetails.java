package com.example.neostore2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ActivityOrderDetails extends AppCompatActivity {
    private RetroViewModel model;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        model = new ViewModelProvider(this).get(RetroViewModel.class);
        findViewById(R.id.ivBack).setOnClickListener(v -> { finish(); });


        mRecyclerView = findViewById(R.id.orderDetailRecycler);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));

        Intent i = getIntent();
        String id = i.getStringExtra("id");
        String token = HelperShared.Helper.getInstance(getApplicationContext()).fetchUser().getToken();

        TextView tvId = findViewById(R.id.tvOrderDetailID);

        TextView tvPrice = findViewById(R.id.tvOrderDetailPrice);

        model.getOrderDetail(token, id).observe(this, new Observer<DataOrderDetail>() {
            @Override
            public void onChanged(DataOrderDetail dataOrderDetail) {

                List<DataSubOrderDetail> examplelist = dataOrderDetail.getOrderdetails();
                mAdapter = new AdapterOrderDetail(examplelist, ActivityOrderDetails.this);
                mRecyclerView.setAdapter(mAdapter);
                String id = dataOrderDetail.getId();
                String cost = dataOrderDetail.getCost();

                tvId.setText(id);

                tvPrice.setText("Rs. "+format(cost));


            }
        });

    }

    private String format(String amount) {
        int number = Integer.valueOf(amount);
        return NumberFormat.getNumberInstance(new Locale("en", "in")).format(number);
    }

}