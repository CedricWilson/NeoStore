package com.example.neostore2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ActivityForgotPassword extends AppCompatActivity {
    private RetroViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        model = new ViewModelProvider(this).get(RetroViewModel.class);
        findViewById(R.id.ivBack).setOnClickListener(v -> { finish(); });

        EditText emailbox = findViewById(R.id.etMail);
        Button submit = findViewById(R.id.btSubmit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailbox.getText().toString().trim();

                model.getForgotPass(email);


            }
        });


    }
}