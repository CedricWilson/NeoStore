package com.example.neostore2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityDetails extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RetroViewModel model;


    @Override
    protected void onStart() {
        super.onStart();
        if (!HelperShared.Helper.getInstance(this).isLoggedIn()) {
            Intent home = new Intent(getApplicationContext(), ActivityLogin.class);
            home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(home);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mRecyclerView = findViewById(R.id.imageRecycler);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        model = new ViewModelProvider(this).get(RetroViewModel.class);

        findViewById(R.id.ivBack).setOnClickListener(v -> {
            finish();
        });
        findViewById(R.id.cart).setOnClickListener(v -> {
            Intent cart = new Intent(ActivityDetails.this, ActivityCart.class);
            startActivity(cart);

        });

        //cart coun
        String tok = HelperShared.Helper.getInstance(this).fetchUser().getToken();
        cartcounter(tok);
        //


        Button buy = findViewById(R.id.btBuy);
        Button rate = findViewById(R.id.btRate);

        Intent i = getIntent();
        String id = i.getStringExtra("id");

        model.productdetails(id).observe(this, new Observer<ResponseDetails>() {
            @Override
            public void onChanged(ResponseDetails responseDetails) {

                TextView name = findViewById(R.id.tvDname);
                TextView producer = findViewById(R.id.tvDproducer);
                TextView cat = findViewById(R.id.tvDcategory);
                TextView price = findViewById(R.id.tvDprice);
                TextView description = findViewById(R.id.tvDdescription);
                RatingBar ratingBar = findViewById(R.id.DratingBar);
                ImageView mainpic = findViewById(R.id.ivDpic);

                DataDetails data = responseDetails.getData();

                String id = data.getId();
                name.setText(data.getName());
                producer.setText(data.getProducer());
                price.setText("Rs." + " " + format(data.getCost()));
                ratingBar.setRating(data.getRating());

                String i = data.getCid();

                switch (i) {
                    case "1":
                        cat.setText("Table");
                        break;
                    case "2":
                        cat.setText("Chair");
                        break;
                    case "3":
                        cat.setText("Sofa");
                        break;
                    case "4":
                        cat.setText("Bed");
                        break;
                }
                description.setText(data.getDescription());
                description.setMovementMethod(new ScrollingMovementMethod());

                String s = data.getProduct_images().get(0).getImage();
                Glide.with(ActivityDetails.this).load(s).into(mainpic);

                List<DataProductImages> imagelist = data.getProduct_images();
                mAdapter = new AdapterImage(imagelist, ActivityDetails.this, mainpic);
                mRecyclerView.setAdapter(mAdapter);

                buy.setOnClickListener(v -> {
                    buyClick(data, s);
                });
                rate.setOnClickListener(v -> {
                    rateClick(data, s, id);
                });
            }
        });

//        Call<ResponseDetails> call = RetrofitClient.getInstance().getApi()
//                .getDetails(id);
//
//        call.enqueue(new Callback<ResponseDetails>() {
//            @Override
//            public void onResponse(Call<ResponseDetails> call, Response<ResponseDetails> response) {
//                TextView name = findViewById(R.id.tvDname);
//                TextView producer = findViewById(R.id.tvDproducer);
//                TextView cat = findViewById(R.id.tvDcategory);
//                TextView price = findViewById(R.id.tvDprice);
//                TextView description = findViewById(R.id.tvDdescription);
//                RatingBar ratingBar = findViewById(R.id.DratingBar);
//                ImageView mainpic = findViewById(R.id.ivDpic);
//
//                DataDetails data = response.body().getData();
//
//                String id = data.getId();
//                name.setText(data.getName());
//                producer.setText(data.getProducer());
//                price.setText("Rs." + " " + format(data.getCost()));
//                ratingBar.setRating(data.getRating());
//
//                String i = data.getCid();
//
//                switch (i) {
//                    case "1":
//                        cat.setText("Table");
//                        break;
//                    case "2":
//                        cat.setText("Chair");
//                        break;
//                    case "3":
//                        cat.setText("Sofa");
//                        break;
//                    case "4":
//                        cat.setText("Bed");
//                        break;
//                }
//                description.setText(data.getDescription());
//                description.setMovementMethod(new ScrollingMovementMethod());
//
//                String s = data.getProduct_images().get(0).getImage();
//                Glide.with(getApplicationContext()).load(s).into(mainpic);
//
//                List<DataProductImages> imagelist = data.getProduct_images();
//                mAdapter = new AdapterImage(imagelist, ActivityDetails.this, mainpic);
//                mRecyclerView.setAdapter(mAdapter);
//
//                buy.setOnClickListener(v -> { buyClick(data,s); });
//                rate.setOnClickListener(v -> { rateClick(data,s,id); });
//            }
//
//            @Override
//            public void onFailure(Call<ResponseDetails> call, Throwable t) {
//                Log.d("main", "Failed");
//            }
//        });


    }

    @SuppressLint("ClickableViewAccessibility")
    private void rateClick(DataDetails data, String image, String id) {
        ViewGroup viewGroup = findViewById(android.R.id.content);

        View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.pop_rate, viewGroup,
                false);

        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityDetails.this);

        builder.setView(dialogView);

        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        TextView tv = alertDialog.findViewById(R.id.tvRtitle);
        tv.setText(data.getName());

        ImageView ppic = dialogView.findViewById(R.id.ivRpic);

        Glide.with(getApplicationContext()).load(image).into(ppic);

        RatingBar ratingBar = alertDialog.findViewById(R.id.rbRate);
        final String[] rate = new String[1];
        ratingBar.setEnabled(true);
        ratingBar.setClickable(true);
        ratingBar.setRating(0);
        ratingBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    float touchPositionX = event.getX();
                    float width = ratingBar.getWidth();
                    float starsf = (touchPositionX / width) * 5.0f;
                    ratingBar.setRating(starsf);

                    rate[0] = String.valueOf(ratingBar.getRating());


                    v.setPressed(false);
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setPressed(true);
                }

                if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                    v.setPressed(false);
                }
                return true;
            }
        });

        alertDialog.findViewById(R.id.btRSubmit).setOnClickListener(v -> {

            model.testrate(id, rate[0]);
            alertDialog.cancel();

        });

    }

    private void buyClick(DataDetails data, String s) {
        ViewGroup viewGroup = findViewById(android.R.id.content);

        View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.pop_buy, viewGroup,
                false);

        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityDetails.this);

        builder.setView(dialogView);

        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        TextView tv = alertDialog.findViewById(R.id.tvBTitle);
        tv.setText(data.getName());

        ImageView ppic = dialogView.findViewById(R.id.ivBpic);
        Glide.with(getApplicationContext()).load(s).into(ppic);


        Button submit = alertDialog.findViewById(R.id.btBSubmit);
        submit.setOnClickListener(v1 -> {
            buydailogue(dialogView, data, alertDialog);


        });
        Button cancel = alertDialog.findViewById(R.id.btBCancel);
        cancel.setOnClickListener(v1 -> {
            alertDialog.cancel();
        });

    }


    private void buydailogue(View dialogView, DataDetails data, AlertDialog alertDialog) {
        EditText quantity = dialogView.findViewById(R.id.etQuantity);
        String q = quantity.getText().toString();
        int iq = Integer.parseInt(q);
        if (iq > 8) {
            Toast.makeText(this, "Max Quatity allowed 8", Toast.LENGTH_SHORT).show();
        } else if (iq == 0) {
            Toast.makeText(this, "Min Quantity 1", Toast.LENGTH_SHORT).show();
        } else {
            String token = HelperShared.Helper.getInstance(getApplicationContext()).fetchUser().getToken();

            model.addcart(token, data.getId(), q).observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    if (s.equals("1")) {
                        alertDialog.cancel();
                        cartcounter(token);
                    }
                }
            });
        }

    }

    private void cartcounter(String token) {

        model.cartcount(token).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                TextView cart = findViewById(R.id.tvCartCount);
                if (s == null) {
                    cart.setVisibility(View.GONE);
                } else {
                    cart.setVisibility(View.VISIBLE);
                    cart.setText(s);
                }
            }
        });

    }

    private String format(String amount) {
        int number = Integer.valueOf(amount);
        return NumberFormat.getNumberInstance(new Locale("en", "in")).format(number);
    }

}
