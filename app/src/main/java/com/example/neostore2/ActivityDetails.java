package com.example.neostore2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
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

import com.bumptech.glide.Glide;

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

        //cart coun
        String tok = HelperShared.Helper.getInstance(this).fetchUser().getToken();
        cartcounter(tok);
        //


        Button buy = findViewById(R.id.btBuy);
        Button rate = findViewById(R.id.btRate);

        Intent i = getIntent();
        String id = i.getStringExtra("id");

        Call<ResponseDetails> call = RetrofitClient.getInstance().getApi()
                .getDetails(id);

        call.enqueue(new Callback<ResponseDetails>() {
            @Override
            public void onResponse(Call<ResponseDetails> call, Response<ResponseDetails> response) {
                TextView name = findViewById(R.id.tvDname);
                TextView producer = findViewById(R.id.tvDproducer);
                TextView cat = findViewById(R.id.tvDcategory);
                TextView price = findViewById(R.id.tvDprice);
                TextView description = findViewById(R.id.tvDdescription);
                RatingBar ratingBar = findViewById(R.id.DratingBar);
                ImageView mainpic = findViewById(R.id.ivDpic);

                DataDetails data = response.body().getData();

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
                Glide.with(getApplicationContext()).load(s).into(mainpic);

                List<DataProductImages> imagelist = data.getProduct_images();
                mAdapter = new AdapterImage(imagelist, ActivityDetails.this, mainpic);
                mRecyclerView.setAdapter(mAdapter);

                buy.setOnClickListener(v -> { buyClick(data,s); });
                rate.setOnClickListener(v -> { rateClick(data,s,id); });
            }

            @Override
            public void onFailure(Call<ResponseDetails> call, Throwable t) {
                Log.d("main", "Failed");
            }
        });


    }

    @SuppressLint("ClickableViewAccessibility")
    private void rateClick(DataDetails data, String image, String s) {
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
            }});
        Button btrate = alertDialog.findViewById(R.id.btRSubmit);
        btrate.setOnClickListener(v -> {
            setrate(rate[0],s);
        });
    }

    private void setrate(String rate, String s) {

        Call<ResponseRate> call = RetrofitClient.getInstance().getApi()
                .setRating(s,rate);

        call.enqueue(new Callback<ResponseRate>() {
            @Override
            public void onResponse(Call<ResponseRate> call, Response<ResponseRate> response) {

                Toast.makeText(
                            getApplicationContext(),
                            response.body().getMessage(),
                            Toast.LENGTH_LONG
                    ).show();


            }

            @Override
            public void onFailure(Call<ResponseRate> call, Throwable t) {
                Log.d("main", "FAILED");
            }
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
        if(iq>8){
            Toast.makeText(this, "Max Quatity allowed 8", Toast.LENGTH_SHORT).show();
        }else if(iq==0){
            Toast.makeText(this, "Min Quantity 1", Toast.LENGTH_SHORT).show();
        }
        else{
            String token = HelperShared.Helper.getInstance(getApplicationContext()).fetchUser().getToken();

            Call<ResponseAddCart> call1 = RetrofitClient.getInstance().getApi()
                    .addcart(token, data.getId(), q);
            Log.d("main", token+" "+data.getId()+" "+q);

            call1.enqueue(new Callback<ResponseAddCart>() {
                @Override
                public void onResponse(Call<ResponseAddCart> call, Response<ResponseAddCart> response) {
                    String mes = response.body().getUser_msg();

                    Toast.makeText(getApplicationContext(), mes, Toast.LENGTH_SHORT).show();
                    alertDialog.cancel();
                    cartcounter(token);

                }

                @Override
                public void onFailure(Call<ResponseAddCart> call, Throwable t) {

                }
            });
        }

    }

    private void cartcounter(String token) {
        Call<ResponseCart> call2 = RetrofitClient.getInstance().getApi()
                .listcart(token);

        call2.enqueue(new Callback<ResponseCart>() {
            @Override
            public void onResponse(Call<ResponseCart> call, Response<ResponseCart> response) {

                String s = response.body().getCount();
                Log.d("mail", "Cart1st: "+s);
                TextView cart = findViewById(R.id.tvCartCount);
                if(s==null){
                    cart.setVisibility(View.GONE);
                }else {
                    cart.setVisibility(View.VISIBLE);
                    cart.setText(s);
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

}
