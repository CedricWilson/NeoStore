package com.example.neostore2.Response;

import com.google.gson.annotations.SerializedName;

public class ResponseDeleteCart {
    @SerializedName("user_msg")
    private String message;

    public String getMessage() {
        return message;
    }
}
