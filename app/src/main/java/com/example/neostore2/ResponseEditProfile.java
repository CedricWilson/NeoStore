package com.example.neostore2;

import com.google.gson.annotations.SerializedName;

public class ResponseEditProfile {
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

}
