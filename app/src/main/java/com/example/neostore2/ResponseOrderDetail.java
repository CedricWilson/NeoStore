package com.example.neostore2;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseOrderDetail {
    @SerializedName("data")
    private DataOrderDetail data;

    public DataOrderDetail getData() {
        return data;
    }
}
