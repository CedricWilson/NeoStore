package com.example.neostore2.Response;

import com.google.gson.annotations.SerializedName;

public class ResponseAddCart {

    @SerializedName("user_msg")
    private String user_msg;

    public String getUser_msg() {
        return user_msg;
    }
}
