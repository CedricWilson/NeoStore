package com.example.neostore2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityProductTable extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onStart() {
        super.onStart();
        if(!HelperShared.Helper.getInstance(this).isLoggedIn()){
            Intent home = new Intent(getApplicationContext(), ActivityLogin.class);
            home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(home);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_table);



        mRecyclerView = findViewById(R.id.recycler);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        jsonparse();


        findViewById(R.id.ivBack).setOnClickListener(v -> { finish(); });

    }

    private void jsonparse() {

        TextView title = findViewById(R.id.tbProduct);
        Intent ptable = getIntent();
        String val = ptable.getStringExtra("id");

        switch (val){
            case "1":title.setText("Table");;break;
            case "2":title.setText("Chair");;break;
            case "3":title.setText("Sofa");;break;
            case "4":title.setText("Bed");;break;
            default:
                throw new IllegalStateException("Unexpected value: ");
        }

        Call<ResponseProductList> call = RetrofitClient.getInstance().getApi()
                .getList(val);
        call.enqueue(new Callback<ResponseProductList>() {
            @Override
            public void onResponse(Call<ResponseProductList> call, Response<ResponseProductList> response) {

                List<DataProduct> exampleList = response.body().getData();

                mAdapter = new AdapterProduct(exampleList, ActivityProductTable.this);
                mRecyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onFailure(Call<ResponseProductList> call, Throwable t) {

            }
        });

    }






}