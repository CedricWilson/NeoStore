package com.example.neostore2.Response;

import com.google.gson.annotations.SerializedName;

public class ResponseRate {
    @SerializedName("user_msg")
    private String user_msg;

    public String getMessage() {
        return user_msg;
    }
}
