package com.example.neostore2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ActivityOrderList extends AppCompatActivity {
    private RetroViewModel retroViewModel;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        retroViewModel = new ViewModelProvider(this).get(RetroViewModel.class);

        mRecyclerView = findViewById(R.id.orderRecycler);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        String token = HelperShared.Helper.getInstance(getApplicationContext()).fetchUser().getToken();

        retroViewModel.getOrderList(token).observe(this, new Observer<List<DataOrderList>>() {
            @Override
            public void onChanged(List<DataOrderList> dataOrderLists) {

                List<DataOrderList> exampleList = dataOrderLists;

                mAdapter = new AdapterOrderList(exampleList, ActivityOrderList.this);
                mRecyclerView.setAdapter(mAdapter);

            }
        });

    }


}