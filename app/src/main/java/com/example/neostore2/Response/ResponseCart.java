package com.example.neostore2.Response;

import com.example.neostore2.Data.DataCart;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseCart {
    @SerializedName("status")
    private int check;

    @SerializedName("data")
    private List<DataCart> data;

    @SerializedName("count")
    private String count;

    @SerializedName("total")
    private String total;

    public String getTotal() {
        return total;
    }

    public List<DataCart> getData() {
        return data;
    }

    public String getCount() {
        return count;
    }

    public ResponseCart(String count) {
        this.count=count;
    }

    public int getCheck() {
        return check;
    }
}
