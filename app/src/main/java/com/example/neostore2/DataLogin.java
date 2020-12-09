package com.example.neostore2;

import com.google.gson.annotations.SerializedName;

public class DataLogin {
    @SerializedName("id")
    private int id;
    @SerializedName("first_name")
    private String firstname;
    @SerializedName("last_name")
    private String lastname;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("confirm_password")
    private String confirmpassword;
    @SerializedName("gender")
    private String gender;
    @SerializedName("phone_no")
    private String phone;
    @SerializedName("access_token")
    private String token;
    @SerializedName("username")
    private String username;

    public DataLogin(int id, String first_name, String last_name, String email, String username, String gender, String access_token) {
        this.id=id;
        this.firstname=first_name;
        this.lastname=last_name;
        this.email=email;
        this.username=username;
        this.gender=gender;
        this.token=access_token;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmpassword() {
        return confirmpassword;
    }

    public String getGender() {
        return gender;
    }

    public String getPhone() {
        return phone;
    }
}
