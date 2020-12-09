package com.example.neostore2;

import com.google.gson.annotations.SerializedName;

public class ResponseForgotPassword {
    @SerializedName("user_msg")
    private String message;

    public String getMessage() {
        return message;
    }

}
