package com.example.neostore2.Response;

import com.google.gson.annotations.SerializedName;

public class ResponseChangePassword {
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }
}
