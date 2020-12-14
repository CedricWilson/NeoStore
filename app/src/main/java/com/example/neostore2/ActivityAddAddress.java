package com.example.neostore2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.neostore2.Helpers.HelperShared;
import com.example.neostore2.Helpers.NoteRoomDatabase;

public class ActivityAddAddress extends ActivityBase {
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
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

            }
        });

        BackPressed(R.anim.slide_in_left, R.anim.slide_out_right);


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

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}