package com.example.neostore2;

import com.google.gson.annotations.SerializedName;

public class ResponseEditCart {
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    @SerializedName("data")
    private DataEditProfile data;

    public DataEditProfile getData() {
        return data;
    }

}
