package com.example.neostore2.Data;

import com.google.gson.annotations.SerializedName;

public class DataCart {
    @SerializedName("id")
    private String id;

    @SerializedName("product_category")
    private String cid;

    @SerializedName("quantity")
    private String quantity;

    @SerializedName("product")
    private DataCartProduct product;

    public String getId() {
        return id;
    }

    public String getCid() {
        return cid;
    }

    public String getQuantity() {
        return quantity;
    }

    public DataCartProduct getProduct() {
        return product;
    }
}
