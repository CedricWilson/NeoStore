package com.example.neostore2;

import com.google.gson.annotations.SerializedName;

public class DataCartProduct {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("product_category")
    private String product_category;

    @SerializedName("cost")
    private String cost;

    @SerializedName("product_images")
    private String product_images;

    @SerializedName("sub_total")
    private String sub_total;


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return product_category;
    }

    public String getCost() {
        return cost;
    }

    public String getProductImage() {
        return product_images;
    }

    public String getTotal() {
        return sub_total;
    }
}
