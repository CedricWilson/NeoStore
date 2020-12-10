package com.example.neostore2;

import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProvider;
import androidx.room.Database;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityAddAddress extends AppCompatActivity {
    public static final String EXTRA_ADDRESS = "com.example.neostore2.EXTRA_ADDRESS";
    public static final String EXTRA_NAME = "com.example.neostore2.EXTRA_NAME";
    public static final String EXTRA_EMAIL = "com.example.neostore2.EXTRA_EMAIL";
    NoteRoomDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        String email = HelperShared.Helper.getInstance(getApplicationContext()).fetchUser().getEmail();
        String name = HelperShared.Helper.getInstance(getApplicationContext()).fetchUser().getFirstname();


        Button submit = findViewById(R.id.btASubmit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveNote(email, name);

                finish();
            }
        });

        findViewById(R.id.ivBack).setOnClickListener(v -> { finish(); });


    }

    private void saveNote(String email, String name) {
        EditText addr = findViewById(R.id.etAddress);
        String address = addr.getText().toString().trim();

        if (address.isEmpty()) {
            Toast.makeText(this, "Please Enter Adress", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();
        data.putExtra(EXTRA_ADDRESS, address);
        data.putExtra(EXTRA_NAME, name);
        data.putExtra(EXTRA_EMAIL, email);

        setResult(RESULT_OK, data);
    }
}