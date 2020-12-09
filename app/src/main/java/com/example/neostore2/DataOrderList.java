package com.example.neostore2;

import com.google.gson.annotations.SerializedName;

public class DataOrderList {

    @SerializedName("id")
    private String id;

    @SerializedName("cost")
    private String cost;

    @SerializedName("created")
    private String date;

    public String getId() {
        return id;
    }

    public String getCost() {
        return cost;
    }

    public String getDate() {
        return date;
    }
}
