package com.example.neostore2;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataOrderDetail {
    @SerializedName("id")
    private String id;

    @SerializedName("cost")
    private String cost;



    @SerializedName("created")
    private String date;

    @SerializedName("order_details")
    private List<DataSubOrderDetail> orderdetails;

    public String getId() {
        return id;
    }


    public String getCost() {
        return cost;
    }

    public String getDate() {
        return date;
    }

    public List<DataSubOrderDetail> getOrderdetails() {
        return orderdetails;
    }
}
