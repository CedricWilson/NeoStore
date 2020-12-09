package com.example.neostore2;

import com.google.gson.annotations.SerializedName;


public class ResponseDetails {

    @SerializedName("data")
    private DataDetails data;

    public DataDetails getData() {
        return data;
    }
}
