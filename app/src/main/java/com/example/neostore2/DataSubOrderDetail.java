package com.example.neostore2;

import com.google.gson.annotations.SerializedName;

public class DataSubOrderDetail {

    @SerializedName("id")
    private String id;

    @SerializedName("product_id")
    private String pid;

    @SerializedName("quantity")
    private String quantity;
    @SerializedName("prod_name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("total")
    private String total;

    @SerializedName("prod_cat_name")
    private String cat;

    @SerializedName("prod_image")
    private String image;

    public String getId() {
        return id;
    }

    public String getPid() {
        return pid;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getTotal() {
        return total;
    }

    public String getCategory() {
        return cat;
    }

    public String getImage() {
        return image;
    }
}
