package com.example.neostore2;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API {

    @FormUrlEncoded
    @POST("users/register")
    Call<ResponseRegistration> createUser(
            @Field("first_name") String first_name,
            @Field("last_name") String last_name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("confirm_password") String confirm_password,
            @Field("gender") String gender,
            @Field("phone_no") String phone_no
    );

    @FormUrlEncoded
    @POST("users/login")
    Call<ResponseLogin> loginuser(
            @Field("email") String email,
            @Field("password") String password
    );

    @GET("products/getList")
    Call<ResponseProductList> getList(
            @Query("product_category_id") String id
    );

    @GET("products/getDetail")
    Call<ResponseDetails> getDetails(
            @Query("product_id") String id
    );

    @FormUrlEncoded
    @POST("addToCart")
    Call<ResponseAddCart> addcart(
            @Header("access_token") String token,

            @Field("product_id") String id,
            @Field("quantity") String quantity
    );

    @GET("cart")
    Call<ResponseCart> listcart(
            @Header("access_token") String String
    );

    @FormUrlEncoded
    @POST("products/setRating")
    Call<ResponseRate> setRating(
            @Field("product_id") String id,
            @Field("rating") String rate
    );

    @FormUrlEncoded
    @POST("editCart")
    Call<ResponseEditCart> editCart(
            @Header("access_token") String token,

            @Field("product_id") String id,
            @Field("quantity") String quantity
    );

    @FormUrlEncoded
    @POST("deleteCart")
    Call<ResponseDeleteCart> deleteCart(
            @Header("access_token") String token,

            @Field("product_id") String id

    );

    @FormUrlEncoded
    @POST("order")
    Call<ResponseOrder> placeorder(
            @Header("access_token") String token,

            @Field("address") String address
    );

    @GET("orderList")
    Call<ResponseOrderList> orderList(
            @Header("access_token") String token

    );

    @GET("orderDetail")
    Call<ResponseOrderDetail> orderDetail(
            @Header("access_token") String token,
            @Query("order_id") String id
    );

    @FormUrlEncoded
    @POST("users/update")
    Call<ResponseEditProfile> editProfile(
            @Header("access_token") String token,
            @Field("first_name") String fname,
            @Field("last_name") String lname,
            @Field("email") String email,
            @Field("dob") String dob,
            @Field("phone_no") String phone,
            @Field("profile_pic") String pic
    );

    @FormUrlEncoded
    @POST("users/change")
    Call<ResponseChangePassword> changePass(
            @Header("access_token") String token,
            @Field("old_password") String oldpass,
            @Field("password") String newpass,

            @Field("confirm_password") String confirm
    );

    @FormUrlEncoded
    @POST("users/forgot")
    Call<ResponseForgotPassword> forgotPass(
            @Field("email") String email
    );


}
