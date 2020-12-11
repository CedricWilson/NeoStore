package com.example.neostore2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.neostore2.Helpers.HelperShared;
import com.example.neostore2.Helpers.RetroViewModel;

public class ActivityLogin extends AppCompatActivity implements View.OnClickListener {

    private EditText usernamebox;
    private EditText passwordbox;
    private RetroViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        model = new ViewModelProvider(this).get(RetroViewModel.class);


        usernamebox = findViewById(R.id.etUsername);
        passwordbox = findViewById(R.id.etPassword);

        findViewById(R.id.fabAdd).setOnClickListener(this);
        findViewById(R.id.btLogin).setOnClickListener(this);
        findViewById(R.id.ivForgotPass).setOnClickListener(this);


        Intent i = getIntent();
        usernamebox.setText(i.getStringExtra("email"));
        passwordbox.setText(i.getStringExtra("password"));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (HelperShared.Helper.getInstance(this).isLoggedIn()) {
            Intent home = new Intent(getApplicationContext(), ActivityHomepage.class);
            home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(home);
        }
    }

    private void login() {

        String username = usernamebox.getText().toString().trim();
        String password = passwordbox.getText().toString().trim();


        if (username.isEmpty()) {
            usernamebox.setError("Email is Required");
            usernamebox.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            usernamebox.setError("Enter a Valid Email");
            usernamebox.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            passwordbox.setError("Password is Required");
            passwordbox.requestFocus();
            return;
        }


        if (password.length() < 6) {
            passwordbox.setError("Minimum length 6");
            passwordbox.requestFocus();
            return;
        }

        model.login(username, password).observe(this, responseLogin -> {

            String s = responseLogin.getMessage();

            HelperShared.Helper.getInstance(getApplicationContext()).saveUser(responseLogin.getData());

            if (s.equals("Logged In successfully")) {
                Intent home = new Intent(getApplicationContext(), ActivityHomepage.class);
                home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(home);
            }

        });

    }

    private void register() {
        Intent intentregister = new Intent(ActivityLogin.this, ActivityRegistration.class);
        startActivity(intentregister);
    }

    private void forgot() {

        Intent forgot = new Intent(ActivityLogin.this, ActivityForgotPassword.class);
        startActivity(forgot);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabAdd:
                register();
                break;
            case R.id.btLogin:
                login();
                break;
            case R.id.ivForgotPass:
                forgot();
                break;
        }
    }


}