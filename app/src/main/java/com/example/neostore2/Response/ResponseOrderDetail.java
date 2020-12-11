package com.example.neostore2.Response;

import com.example.neostore2.Data.DataOrderDetail;
import com.google.gson.annotations.SerializedName;

public class ResponseOrderDetail {
    @SerializedName("data")
    private DataOrderDetail data;

    public DataOrderDetail getData() {
        return data;
    }
}
