package com.example.neostore2.Response;

import com.example.neostore2.Data.DataDetails;
import com.google.gson.annotations.SerializedName;


public class ResponseDetails {

    @SerializedName("data")
    private DataDetails data;

    public DataDetails getData() {
        return data;
    }
}
