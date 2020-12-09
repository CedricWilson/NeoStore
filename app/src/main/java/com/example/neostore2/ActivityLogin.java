package com.example.neostore2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityLogin extends AppCompatActivity implements View.OnClickListener {

    private EditText usernamebox;
    private EditText passwordbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

        Call<ResponseLogin> call = RetrofitClient.getInstance().getApi()
                .loginuser(username, password);

        call.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {

                if (response.isSuccessful()) {

                    Toast.makeText(
                            getApplicationContext(),
                            response.body().getMessage(),
                            Toast.LENGTH_LONG).show();


                    HelperShared.Helper.getInstance(getApplicationContext()).saveUser(response.body().getData());


                    Intent home = new Intent(getApplicationContext(), ActivityHomepage.class);
                    home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(home);


                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(
                                getApplicationContext(),
                                jObjError.getString("user_msg"),
                                Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                Toast.makeText(ActivityLogin.this, t.getMessage(), Toast.LENGTH_LONG).show();

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