package com.example.neostore2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.neostore2.Adapters.AdapterOrderList;
import com.example.neostore2.Data.DataOrderList;
import com.example.neostore2.Helpers.HelperShared;
import com.example.neostore2.Helpers.RetroViewModel;

import java.util.List;

public class ActivityOrderList extends ActivityBase {
    private RetroViewModel retroViewModel;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        retroViewModel = new ViewModelProvider(this).get(RetroViewModel.class);

        BackPressed(R.anim.slide_in_left, R.anim.slide_out_right);



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

    @Override
    public void onBackPressed() {
        Intent home = new Intent(ActivityOrderList.this, ActivityHomepage.class);
        startActivity(home);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


}