package com.example.neostore2.Response;

import com.example.neostore2.Data.DataProduct;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseProductList {

    @SerializedName("data")
    private List<DataProduct> data;

    public List<DataProduct> getData() {
        return data;
    }
}
