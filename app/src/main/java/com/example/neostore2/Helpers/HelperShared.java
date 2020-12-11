package com.example.neostore2.Helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.neostore2.Data.DataLogin;

import org.jetbrains.annotations.NotNull;

public class HelperShared {

    private final Context context;
    private static final String SHARED_PREF_NAME = "Shared";
    private static HelperShared mInstance;
    public static final HelperShared.Helper Helper = new HelperShared.Helper();

    public HelperShared(Context context) {
        this.context = context;
    }

    public static final class Helper {
        public final synchronized HelperShared getInstance(Context context) {

            if (HelperShared.mInstance == null) {
                HelperShared.mInstance = new HelperShared(context);
            }
            return HelperShared.mInstance;
        }
    }

    public final boolean isLoggedIn() {
        SharedPreferences sharedPreferences = this.context.getSharedPreferences(SHARED_PREF_NAME, 0);
        return sharedPreferences.getInt("id", 0) != 0;
    }

    public final void saveUser(@NotNull DataLogin data) {
        SharedPreferences sharedPreferences = this.context.getSharedPreferences(SHARED_PREF_NAME, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("id", data.getId());
        editor.putString("first_name", data.getFirstname());
        editor.putString("last_name", data.getLastname());
        editor.putString("email", data.getEmail());
        editor.putString("username", data.getUsername());
        editor.putString("pic", data.getPic());
        editor.putString("gender", data.getGender());
        editor.putString("access_token", data.getToken());
        editor.apply();
    }



    public final DataLogin fetchUser(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, 0);
        return new DataLogin(
                sharedPreferences.getInt("id", 0),
                sharedPreferences.getString("first_name", ""),
                sharedPreferences.getString("last_name", ""),
                sharedPreferences.getString("email", ""),
                sharedPreferences.getString("username", ""),
                sharedPreferences.getString("gender", ""),
                sharedPreferences.getString("access_token", ""),
                sharedPreferences.getString("pic", "")
        );

    }

    public final void updateImage(String image,String num) {
        SharedPreferences sharedPreferences = this.context.getSharedPreferences(SHARED_PREF_NAME, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("pic", image);
        editor.putString("picedited",num);

        editor.apply();
    }

    public final void clear() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public final void updateCount(String count) {
        SharedPreferences sharedPreferences = this.context.getSharedPreferences(SHARED_PREF_NAME, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("count", count);

        editor.apply();
    }



    public final String fetchCount(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, 0);
        return sharedPreferences.getString("count","0");

    }





}
