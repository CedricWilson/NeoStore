package com.example.neostore2.Data;

import com.google.gson.annotations.SerializedName;

public class DataEditProfile {
    @SerializedName("profile_pic")
    private String pic;

    public String getPic() {
        return pic;
    }
}
