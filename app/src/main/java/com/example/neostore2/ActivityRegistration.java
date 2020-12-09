package com.example.neostore2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityRegistration extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        EditText firstnamebox = findViewById(R.id.etFirstName);
        EditText lastnamebox = findViewById(R.id.etLastName);
        EditText emailbox = findViewById(R.id.etMail);
        EditText passwordbox = findViewById(R.id.etPassword);
        EditText confirmbox = findViewById(R.id.etPasswordConfirm);
        EditText phonebox = findViewById(R.id.etPhone);
        CheckBox checkbox = findViewById(R.id.cbTerms);
        RadioGroup rg = findViewById(R.id.rgGender);
        Button register = findViewById(R.id.btRegister);
        ImageView back = findViewById(R.id.ivBack);


        back.setOnClickListener(v -> {
            Intent i = new Intent(ActivityRegistration.this, ActivityLogin.class);
            startActivity(i);
        });


        register.setOnClickListener(v -> {

            String firstname = firstnamebox.getText().toString().trim();
            String lastname = lastnamebox.getText().toString().trim();
            String email = emailbox.getText().toString().trim();
            String password = passwordbox.getText().toString().trim();
            String confirm = confirmbox.getText().toString().trim();
            String phone = phonebox.getText().toString().trim();

            int selectedId = rg.getCheckedRadioButtonId();
            RadioButton radioButton = findViewById(selectedId);
            String gender = radioButton.getText().toString();

            if (firstname.isEmpty()) {
                firstnamebox.setError("Name is Required");
                firstnamebox.requestFocus();
                return;
            }

            if (lastname.isEmpty()) {
                lastnamebox.setError("Name is Required");
                lastnamebox.requestFocus();
                return;
            }

            if (email.isEmpty()) {
                emailbox.setError("Email is Required");
                emailbox.requestFocus();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailbox.setError("Enter a Valid Email");
                emailbox.requestFocus();
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

//            if(!confirm.equals(password)){
//                confirmbox.setError("Unmatching");
//                confirmbox.requestFocus();
//                return;
//            }

            if (phone.isEmpty()) {
                phonebox.setError("Phone is Required");
                phonebox.requestFocus();
                return;
            }
//            if(phone.length()< 10 && phone.length() > 13){
//                phonebox.setError("Invalid Phone Number");
//                phonebox.requestFocus();
//                return;
//            }

//            if(!checkbox.isChecked()){
//                Toast.makeText(this, "Please Accept Agreement", Toast.LENGTH_SHORT).show();
//                checkbox.requestFocus();
//                return;
//            }


            Call<ResponseRegistration> call = RetrofitClient.getInstance().getApi()
                    .createUser(firstname, lastname, email, password, confirm, gender, phone);

            call.enqueue(new Callback<ResponseRegistration>() {
                @Override
                public void onResponse(Call<ResponseRegistration> call, Response<ResponseRegistration> response) {


                    //OLD


                    if (response.isSuccessful()) {


                        Toast.makeText(
                                getApplicationContext(),
                                response.body().getMessage(),
                                Toast.LENGTH_LONG
                        ).show();

                        Intent i = new Intent(ActivityRegistration.this, ActivityLogin.class);
                        i.putExtra("email", email);
                        i.putExtra("password", password);
                        startActivity(i);
                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Log.d("main", jObjError.toString());
                            Toast.makeText(
                                    getApplicationContext(),
                                    jObjError.getString("message"),
                                    Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<ResponseRegistration> call, Throwable t) {
                    Toast.makeText(ActivityRegistration.this, t.getMessage(), Toast.LENGTH_LONG).show();

                }
            });


        });
    }
}