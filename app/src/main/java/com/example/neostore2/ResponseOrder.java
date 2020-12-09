package com.example.neostore2;

import com.google.gson.annotations.SerializedName;

public class ResponseOrder {
    @SerializedName("user_msg")
    private String user_msg;

    public String getUser_msg() {
        return user_msg;
    }
}
