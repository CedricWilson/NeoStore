package com.example.neostore2.Response;

import com.example.neostore2.Data.DataLogin;
import com.google.gson.annotations.SerializedName;

public class ResponseLogin {

    @SerializedName("status")
    private int check;

    @SerializedName("data")
    private DataLogin data;

    public DataLogin getData() {
        return data;
    }

    @SerializedName("user_msg")
    private String user_msg;

    public String getMessage() {
        return user_msg;
    }

    public int getCheck() {
        return check;
    }
}
