package com.example.neostore2;

import com.google.gson.annotations.SerializedName;

public class DataProduct {

    @SerializedName("id")
    private String id;

    @SerializedName("product_category_id")
    private String cid;

    @SerializedName("name")
    private String name;
    @SerializedName("producer")
    private String producer;

    @SerializedName("cost")
    private String cost;
    @SerializedName("rating")
    private Float rating;

    @SerializedName("product_images")
    private String product_images;

    public String getId() {
        return id;
    }

    public String getCid() {
        return cid;
    }

    public String getName() {
        return name;
    }

    public String getProducer() {
        return producer;
    }

    public String getCost() {
        return cost;
    }

    public Float getRating() {
        return rating;
    }

    public String getProductImage() {
        return product_images;
    }
}
