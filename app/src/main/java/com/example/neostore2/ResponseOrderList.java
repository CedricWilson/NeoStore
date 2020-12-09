package com.example.neostore2;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseOrderList {

    @SerializedName("data")
    private List<DataOrderList> data;

    public List<DataOrderList> getData() {
        return data;
    }
}
