package com.example.neostore2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ActivityAddress extends AppCompatActivity {
    public static final int ADD_NOTE_REQ = 1;
    private NoteViewModel noteViewModel;
    private RetroViewModel retroViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        ImageView add = findViewById(R.id.ivAddAddress);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent create = new Intent(ActivityAddress.this, ActivityAddAddress.class);
                startActivityForResult(create, ADD_NOTE_REQ);
            }
        });

        RecyclerView mRecycler = findViewById(R.id.AddressRecycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));

        final AdapterAddress adapter = new AdapterAddress(ActivityAddress.this);
        mRecycler.setAdapter(adapter);

        String mail = HelperShared.Helper.getInstance(getApplicationContext()).fetchUser().getEmail();
        String token = HelperShared.Helper.getInstance(getApplicationContext()).fetchUser().getToken();

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        retroViewModel = new ViewModelProvider(this).get(RetroViewModel.class);

        noteViewModel.getAllNotes(mail).observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.setNotes(notes);

            }
        });

        String address = adapter.getItemAt();
        if (address==null){
            mRecycler.setVisibility(View.INVISIBLE);
            TextView tv = findViewById(R.id.tvEmptyAddress);
            tv.setVisibility(View.VISIBLE);
        }



        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT| ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    noteViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                    Toast.makeText(ActivityAddress.this, "Address Deleted", Toast.LENGTH_SHORT).show();
            }

        }).attachToRecyclerView(mRecycler);

        Button order = findViewById(R.id.btAdSubmit);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String address = adapter.getItemAt();
                if(address.equals("")){
                    Toast.makeText(ActivityAddress.this, "Enter an Address", Toast.LENGTH_SHORT).show();
                }
                else{
                    retroViewModel.getOrderplace(token, address).observe(ActivityAddress.this, new Observer<ResponseOrder>() {
                        @Override
                        public void onChanged(ResponseOrder responseOrder) {
                                String toast = responseOrder.getUser_msg();
                                Toast.makeText(ActivityAddress.this, toast, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });







    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQ && resultCode == RESULT_OK){
            String name = data.getStringExtra(ActivityAddAddress.EXTRA_NAME);
            String email = data.getStringExtra(ActivityAddAddress.EXTRA_EMAIL);
            String address = data.getStringExtra(ActivityAddAddress.EXTRA_ADDRESS);

            Note note = new Note(email, name, address);
            noteViewModel.insert(note);

            Toast.makeText(this, "Address Saved", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Invalid Address", Toast.LENGTH_SHORT).show();
        }
    }


}