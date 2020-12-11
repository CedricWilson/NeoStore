package com.example.neostore2.Response;

import com.example.neostore2.Data.DataOrderList;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseOrderList {

    @SerializedName("data")
    private List<DataOrderList> data;

    public List<DataOrderList> getData() {
        return data;
    }
}
