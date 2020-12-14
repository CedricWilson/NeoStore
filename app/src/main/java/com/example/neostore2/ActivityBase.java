package com.example.neostore2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public abstract class ActivityBase extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }


    protected void showToast(String mToastMsg) {
        Toast.makeText(this, mToastMsg, Toast.LENGTH_LONG).show();
    }

    protected void BackPressed(int in, int out) {
        findViewById(R.id.ivBack).setOnClickListener(v -> {
            finish();
            overridePendingTransition(in, out);
        });

    }

//    protected void BackPressed(int in, int out, Activity one, Activity two) {
//        findViewById(R.id.ivBack).setOnClickListener(v -> {
//            Intent jump =new Intent(one.getApplicationContext(), two.getClass());
//            startActivity(jump);
//            overridePendingTransition(in, out);
//        });
//    }

}