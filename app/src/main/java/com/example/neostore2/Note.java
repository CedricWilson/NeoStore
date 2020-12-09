package com.example.neostore2;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Note {

    @PrimaryKey(autoGenerate = true)

    int id;
    String email;
    String name;
    String address;



    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Note(String email, String name, String address){

        this.email= email;
        this.name = name;
        this.address = address;
    }


}
