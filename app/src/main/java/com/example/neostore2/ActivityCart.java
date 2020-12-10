package com.example.neostore2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityCart extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        mRecyclerView = findViewById(R.id.cartRecycler);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        int check = 0;

        findViewById(R.id.ivBack).setOnClickListener(v -> { finish(); });

        Button submit = findViewById(R.id.btRSubmit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent address = new Intent(ActivityCart.this, ActivityAddress.class);
                startActivity(address);
            }
        });

        TextView status = findViewById(R.id.CurrentCartStatus);
        TextView totalbox = findViewById(R.id.tvCTotal);
        counter();

        String s = HelperShared.Helper.getInstance(getApplicationContext()).fetchCount();

        if (!s.equals("0")) {
            check = Integer.valueOf(s);
        }
        if (check > 0) {
            status.setVisibility(View.GONE);

            String token = HelperShared.Helper.getInstance(getApplicationContext()).fetchUser().getToken();

            Call<ResponseCart> call = RetrofitClient.getInstance().getApi()
                    .listcart(token);
            call.enqueue(new Callback<ResponseCart>() {
                @Override
                public void onResponse(Call<ResponseCart> call, Response<ResponseCart> response) {

                    String tot = response.body().getTotal();
                    if (tot != null) {
                        String total = format(Integer.valueOf(tot));
                        totalbox.setText("Rs. " + total);
                    } else {
                        totalbox.setText("Rs. ");
                    }

                    List<DataCart> exampleList = response.body().getData();
                    mAdapter = new AdapterCart(exampleList, ActivityCart.this, token, totalbox);
                    mRecyclerView.setAdapter(mAdapter);

                    HelperSwiper HelperSwiper = new HelperSwiper(getApplicationContext(), mRecyclerView) {
                        @Override
                        public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                            underlayButtons.add(new HelperSwiper.UnderlayButton(
                                    "",

                                    R.drawable.delete,
                                    Color.parseColor("#ffffff"),
                                    new HelperSwiper.UnderlayButtonClickListener() {
                                        @Override
                                        public void onClick(int pos) {

                                            deleteItem(exampleList.get(pos).getProduct().getId(),
                                                    token, exampleList, pos);

                                        }
                                    }, ActivityCart.this
                            ));
                        }
                    };
                }

                @Override
                public void onFailure(Call<ResponseCart> call, Throwable t) {

                }
            });
        } else {
            mRecyclerView.setVisibility(View.GONE);
            status.setText("Cart Is Empty");
            status.setVisibility(View.VISIBLE);
            totalbox.setText("Rs. 0");
        }


    }


    private void deleteItem(String id, String token, List<DataCart> exampleList, int pos) {
        mRecyclerView = findViewById(R.id.cartRecycler);
        TextView status = findViewById(R.id.CurrentCartStatus);

        Call<ResponseDeleteCart> call = RetrofitClient.getInstance().getApi()
                .deleteCart(token, id);

        exampleList.remove(pos);
        mAdapter.notifyItemRemoved(pos);


        call.enqueue(new Callback<ResponseDeleteCart>() {
            @Override
            public void onResponse(Call<ResponseDeleteCart> call, Response<ResponseDeleteCart> response) {
                Toast.makeText(ActivityCart.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseDeleteCart> call, Throwable t) {

            }
        });
        Call<ResponseCart> call1 = RetrofitClient.getInstance().getApi()
                .listcart(token);
        call1.enqueue(new Callback<ResponseCart>() {
            @Override
            public void onResponse(Call<ResponseCart> call, Response<ResponseCart> response) {
                TextView totalbox = findViewById(R.id.tvCTotal);
                String tot = response.body().getTotal();
                if (tot != null) {
                    String total = format(Integer.valueOf(tot));
                    totalbox.setText("Rs. " + total);
                } else {
                    totalbox.setText("Rs. 0");
                    mRecyclerView.setVisibility(View.GONE);
                    status.setText("Cart Is Empty");
                    status.setVisibility(View.VISIBLE);
                    totalbox.setText("Rs. 0");
                }

            }

            @Override
            public void onFailure(Call<ResponseCart> call, Throwable t) {

            }
        });

    }

    private String format(int amount) {
        return NumberFormat.getNumberInstance(new Locale("en", "in")).format(amount);
    }

    private void counter() {
        String tok = HelperShared.Helper.getInstance(getApplicationContext()).fetchUser().getToken();
        Call<ResponseCart> call2 = RetrofitClient.getInstance().getApi()
                .listcart(tok);

        call2.enqueue(new Callback<ResponseCart>() {
            @Override
            public void onResponse(Call<ResponseCart> call, Response<ResponseCart> response) {
                String f = response.body().getCount();
                HelperShared.Helper.getInstance(getApplicationContext()).updateCount(f);


            }

            @Override
            public void onFailure(Call<ResponseCart> call, Throwable t) {

            }
        });
    }
}