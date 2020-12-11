package com.example.neostore2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neostore2.Adapters.AdapterCart;
import com.example.neostore2.Data.DataCart;
import com.example.neostore2.Helpers.HelperShared;
import com.example.neostore2.Helpers.HelperSwiper;
import com.example.neostore2.Helpers.RetroViewModel;
import com.example.neostore2.Helpers.RetrofitClient;
import com.example.neostore2.Response.ResponseCart;

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
    private RetroViewModel model;
    boolean cartIsEmpty = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        mRecyclerView = findViewById(R.id.cartRecycler);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        findViewById(R.id.ivBack).setOnClickListener(v -> {
            Intent home =new Intent(ActivityCart.this, ActivityHomepage.class);
            startActivity(home);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

        model = new ViewModelProvider(this).get(RetroViewModel.class);
        String token = HelperShared.Helper.getInstance(getApplicationContext()).fetchUser().getToken();

        TextView status = findViewById(R.id.CurrentCartStatus);
        TextView totalbox = findViewById(R.id.tvCTotal);

        model.cartcount(token).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                int check = 0;

                if (s != null) {
                    if (!s.equals("0")) {
                        check = Integer.valueOf(s);
                    }
                }

                if (check > 0) {
                    cartIsEmpty = false;
                    status.setVisibility(View.GONE);

                    model.listcart(token).observe(ActivityCart.this, new Observer<ResponseCart>() {
                        @Override
                        public void onChanged(ResponseCart responseCart) {

                            if (responseCart != null) {
                                String tot = responseCart.getTotal();
                                if (tot != null) {
                                    totalbox.setText("Rs. " + format(tot));
                                } else {
                                    totalbox.setText("Rs. ");
                                }

                                List<DataCart> exampleList = responseCart.getData();
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
                                                        deleteItem(exampleList.get(pos).getProduct().getId(), token, exampleList, pos);
                                                    }
                                                }, ActivityCart.this
                                        ));
                                    }
                                };
                            }
                        }
                    });
                } else {
                    mRecyclerView.setVisibility(View.GONE);
                    status.setText("Cart Is Empty");
                    status.setVisibility(View.VISIBLE);
                    totalbox.setText("Rs. 0");
                }
            }
        });

        findViewById(R.id.btRSubmit).setOnClickListener(v -> {
            if (cartIsEmpty == true) {
                Toast.makeText(this, "Cart is Empty", Toast.LENGTH_SHORT).show();
            } else {
                Intent address = new Intent(ActivityCart.this, ActivityAddress.class);
                startActivity(address);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }


    private void deleteItem(String id, String token, List<DataCart> exampleList, int pos) {
        mRecyclerView = findViewById(R.id.cartRecycler);
        TextView status = findViewById(R.id.CurrentCartStatus);
        TextView totalbox = findViewById(R.id.tvCTotal);

        model.deletecart(token, id);

        exampleList.remove(pos);
        mAdapter.notifyItemRemoved(pos);

        if (exampleList.size() == 0) {

            totalbox.setText("Rs. 0");
            mRecyclerView.setVisibility(View.GONE);
            status.setText("Cart Is Empty");
            status.setVisibility(View.VISIBLE);
        }

//        model.listcart(token).observe(this, new Observer<ResponseCart>() {
//            @Override
//            public void onChanged(ResponseCart responseCart) {
//                TextView totalbox = findViewById(R.id.tvCTotal);
//                Log.d("main", "RE");
//                String tot = responseCart.getTotal();
//                if (tot != null) {
//                    String total = format(Integer.valueOf(tot));
//                    totalbox.setText("Rs. " + total);
//                } else {
//                    totalbox.setText("Rs. 0");
//                    mRecyclerView.setVisibility(View.GONE);
//                    status.setText("Cart Is Empty");
//                    status.setVisibility(View.VISIBLE);
//                    totalbox.setText("Rs. 0");
//                }
//            }
//        });

        Call<ResponseCart> call1 = RetrofitClient.getInstance().getApi()
                .listcart(token);
        call1.enqueue(new Callback<ResponseCart>() {
            @Override
            public void onResponse(Call<ResponseCart> call, Response<ResponseCart> response) {
                TextView totalbox = findViewById(R.id.tvCTotal);
                String tot = response.body().getTotal();
                Log.d("main", "Total: " + tot);
                if (tot != null) {
                    String total = format(tot);
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

    private String format(String amount) {
        int number = Integer.valueOf(amount);
        return NumberFormat.getNumberInstance(new Locale("en", "in")).format(number);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}