package com.example.neostore2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdapterAddress extends RecyclerView.Adapter<AdapterAddress.ExampleViewHolder> {
private List<Note> notes = new ArrayList<>();
    int selectedPosition=0;
    int itempos=0;
    Activity context;


    public class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView aName;
        public TextView aAddress;


        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            aName = itemView.findViewById(R.id.tvAddrName);
            aAddress = itemView.findViewById(R.id.tvAddrAddress);

        }
    }

    public AdapterAddress(Activity context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.frame_address, parent, false);
        ExampleViewHolder evh = new AdapterAddress.ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {

        Note current = notes.get(position);
        holder.aName.setText(current.getName());
        holder.aAddress.setText(current.getAddress());

        if(selectedPosition==position)
            holder.itemView.setBackground(ContextCompat.getDrawable(context, R.drawable.roundedbox));
        else
            holder.itemView.setBackground(ContextCompat.getDrawable(context, R.drawable.roundedbox_white));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition=position;
                itempos = position;
                notifyDataSetChanged();

            }
        });


    }

    public String getItemAt(){
        int position = itempos;
        if(notes.size()==0){
            return "";
        }
        else {
            return notes.get(position).address;
        }
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<Note> notes){
        this.notes = notes;
        notifyDataSetChanged();
    }

    public Note getNoteAt(int position){
        return notes.get(position);
    }




}
