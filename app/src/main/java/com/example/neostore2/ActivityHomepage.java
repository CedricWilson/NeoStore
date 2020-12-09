package com.example.neostore2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.rd.PageIndicatorView;

import java.util.Timer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityHomepage extends AppCompatActivity implements View.OnClickListener {
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000;
    Context context;
    int currentPage = 0;
    Timer timer;
    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        if (!HelperShared.Helper.getInstance(this).isLoggedIn()) {

            Intent home = new Intent(getApplicationContext(), ActivityLogin.class);
            home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(home);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();


        //Hamburger
        ImageView ham = findViewById(R.id.ivHamburger);
        drawerLayout = findViewById(R.id.drawerlayout);
        ham.setOnClickListener(view -> drawerLayout.openDrawer(Gravity.LEFT));

        NavigationView navigationView = findViewById(R.id.nvHome);
        View hView = navigationView.getHeaderView(0);
        TextView hamname = hView.findViewById(R.id.hamName);
        TextView hammail = hView.findViewById(R.id.hamMail);


        hamname.setText(HelperShared.Helper.getInstance(getApplicationContext()).fetchUser().getFirstname() + " " + HelperShared.Helper.getInstance(getApplicationContext()).fetchUser().getLastname());
        hammail.setText(HelperShared.Helper.getInstance(getApplicationContext()).fetchUser().getEmail());

        String tok = HelperShared.Helper.getInstance(this).fetchUser().getToken();
        cartcounter(tok);


        //

        //Viewpager
        ViewPager viewPager = findViewById(R.id.viewPager);
        AdapterViewPager adapter = new AdapterViewPager(this);
        viewPager.setAdapter(adapter);

        PageIndicatorView pageIndicatorView = findViewById(R.id.pageIndicatorView);
        pageIndicatorView.setCount(4);
        pageIndicatorView.setSelection(0);

//        final Handler handler = new Handler();
//        final Runnable Update = () -> {
//            if (currentPage == 4) {
//                currentPage = 0;
//            }
//            viewPager.setCurrentItem(currentPage++, true);
//        };
//
//        timer = new Timer(); // This will create a new Thread
//        timer.schedule(new TimerTask() { // task to be scheduled
//            @Override
//            public void run() {
//                handler.post(Update);
//            }
//        }, DELAY_MS, PERIOD_MS);
        //

        //Image Buttons

        findViewById(R.id.ivTable).setOnClickListener(this);
        findViewById(R.id.ivChair).setOnClickListener(this);
        findViewById(R.id.ivSofa).setOnClickListener(this);
        findViewById(R.id.ivBed).setOnClickListener(this);
        ImageView homecart = findViewById(R.id.homecart);

        homecart.setOnClickListener(v -> {
            Intent cart1 = new Intent(this, ActivityCart.class);
            startActivity(cart1);

        });

        TextView v = findViewById(R.id.toolbar_title);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(ActivityHomepage.this, ActivityChangePassword.class);
                startActivity(profile);
            }
        });


        //  six.setText(HelperShared.Companion.getInstance(context).fetchUser().getToken());

//        log.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                HelperShared.Companion.getInstance(context).clear();
//                Intent logout = new Intent(getApplicationContext(), ActivityLogin.class);
//                logout.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(logout);
//            }
//        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                switch (id) {
                    case R.id.MyCart:
                        Intent cart = new Intent(getApplicationContext(), ActivityCart.class);
                        startActivity(cart);
                        break;

                    case R.id.myorders:
                        Intent order = new Intent(getApplicationContext(), ActivityOrderList.class);
                        startActivity(order);
                        break;
                    case R.id.Tables:
                        furniture("1");
                        break;
                    case R.id.Chairs:
                        furniture("2");
                        break;
                    case R.id.Sofas:
                        furniture("3");
                        break;
                    case R.id.Cupboard:
                        furniture("4");
                        break;
                    case R.id.logout:
                        HelperShared.Helper.getInstance(context).clear();
                        Intent logout = new Intent(getApplicationContext(), ActivityLogin.class);
                        logout.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(logout);
                        break;
                }


                return true;
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        String id;
        switch (v.getId()) {
            case R.id.ivTable:
                id = "1";
                break;
            case R.id.ivChair:
                id = "2";
                break;
            case R.id.ivSofa:
                id = "3";
                break;
            case R.id.ivBed:
                id = "4";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
        furniture(id);
    }

    private void furniture(String id) {
        Intent ptable = new Intent(this, ActivityProductTable.class);
        ptable.putExtra("id", id);
        startActivity(ptable);
    }

    private void cartcounter(String token) {
        Call<ResponseCart> call2 = RetrofitClient.getInstance().getApi()
                .listcart(token);

        call2.enqueue(new Callback<ResponseCart>() {
            @Override
            public void onResponse(Call<ResponseCart> call, Response<ResponseCart> response) {

                if (response.isSuccessful()) {
                    String s = response.body().getCount();
                    HelperShared.Helper.getInstance(getApplicationContext()).updateCount(s);

                    NavigationView navigationView = findViewById(R.id.nvHome);
                    TextView hamcart = navigationView.getMenu().findItem(R.id.MyCart).getActionView().findViewById(R.id.counter);
                    TextView homecounter = findViewById(R.id.tvCartCount);

                                if (s == null) {
                                hamcart.setVisibility(View.GONE);
                                homecounter.setVisibility(View.GONE);
                            } else {
                                hamcart.setVisibility(View.VISIBLE);
                                homecounter.setVisibility(View.VISIBLE);
                                hamcart.setText(s);
                                homecounter.setText(s);
                            }
                }


            }

            @Override
            public void onFailure(Call<ResponseCart> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String token = HelperShared.Helper.getInstance(this).fetchUser().getToken();

        cartcounter(token);


    }

}
