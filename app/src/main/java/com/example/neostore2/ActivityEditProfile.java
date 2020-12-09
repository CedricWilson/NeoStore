package com.example.neostore2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityEditProfile extends AppCompatActivity {
    Bitmap bitmap;
    ImageView profile;
    Button selectImg,uploadImg;
    EditText imgTitle;
    private  static final int IMAGE = 100;
    String TAG = "mail";
    String birthday;
    String bitprofile;
    private RetroViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        model = new ViewModelProvider(this).get(RetroViewModel.class);
        String deffname = HelperShared.Helper.getInstance(getApplicationContext()).fetchUser().getFirstname();
        String deflname = HelperShared.Helper.getInstance(getApplicationContext()).fetchUser().getLastname();
        String defemail = HelperShared.Helper.getInstance(getApplicationContext()).fetchUser().getEmail();
        String defphone = HelperShared.Helper.getInstance(getApplicationContext()).fetchUser().getPhone();

        EditText firstnamebox = findViewById(R.id.etFirstName);
        EditText lastnamebox = findViewById(R.id.etLastName);
        EditText emailbox = findViewById(R.id.etMail);
        EditText phonebox = findViewById(R.id.etPhone);
        EditText birthdaybox= findViewById(R.id.etBirthday);

        firstnamebox.setText(deffname);
        lastnamebox.setText(deflname);
        emailbox.setText(defemail);
        phonebox.setText(defphone);

        final Calendar myCalendar = Calendar.getInstance();


        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(birthdaybox, myCalendar);
            }

        };

        birthdaybox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(ActivityEditProfile.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        profile = findViewById(R.id.ivEditProfile);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        Button submit = findViewById(R.id.btEditSubmit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = HelperShared.Helper.getInstance(getApplicationContext()).fetchUser().getToken();


                String firstname = firstnamebox.getText().toString().trim();
                String lastname = lastnamebox.getText().toString().trim();
                String email = emailbox.getText().toString().trim();
                String phone = phonebox.getText().toString().trim();

                if(firstname ==null||lastname ==null||email ==null||phone ==null||birthday ==null||bitprofile ==null){
                    Toast.makeText(ActivityEditProfile.this, "All Fields must be Filled", Toast.LENGTH_SHORT).show();
                }else {
                    model.getEditProfile(token,firstname,lastname,email,phone,birthday,bitprofile).observe(ActivityEditProfile.this, new Observer<ResponseEditProfile>() {
                        @Override
                        public void onChanged(ResponseEditProfile responseEditProfile) {
                            String s = responseEditProfile.getMessage();
                            Toast.makeText(ActivityEditProfile.this, s, Toast.LENGTH_SHORT).show();

                        }
                    });

                }




            }
        });

        TextView reset = findViewById(R.id.tvResetPass);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reset = new Intent(ActivityEditProfile.this, ActivityChangePassword.class);
                startActivity(reset);
            }
        });



    }

    private void selectImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== IMAGE && resultCode==RESULT_OK && data!=null)
        {
            Uri path = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                profile.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
            byte[] imgByte = byteArrayOutputStream.toByteArray();
            bitprofile=  Base64.encodeToString(imgByte, Base64.DEFAULT);
            bitprofile="data:image/jpg;base64,"+bitprofile;
        }
    }



    private String convertToString()
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        bitprofile=  Base64.encodeToString(imgByte, Base64.DEFAULT);
        bitprofile="data:image/jpg;base64,"+bitprofile;
        return bitprofile;
    }


    private void updateLabel(EditText edittext, Calendar myCalendar) {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        birthday = sdf.format(myCalendar.getTime());

        edittext.setText(sdf.format(myCalendar.getTime()));
    }
}

