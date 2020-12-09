package com.example.neostore2;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetroRepo {

    private API api;
    private Application application;
    private final MutableLiveData<ResponseOrder> orderplace = new MutableLiveData<>();
    private final MutableLiveData<DataLogin> dataL = new MutableLiveData<>();
    private final MutableLiveData<List<DataOrderList>> orderL = new MutableLiveData<>();
    private final MutableLiveData<DataOrderDetail> orderDetail = new MutableLiveData<>();
    private final MutableLiveData<ResponseEditProfile> editProfile = new MutableLiveData<>();
    private final MutableLiveData<ResponseChangePassword> changePass = new MutableLiveData<>();
    private LiveData<ResponseForgotPassword> forgotPass;


    public RetroRepo(Application application) {
        api = RetrofitClient.getInstance().getApi();
        this.application = application;
    }

    public LiveData<ResponseForgotPassword> getForgotPass(String email) {
        Call<ResponseForgotPassword> call = api.forgotPass(email);
        call.enqueue(new Callback<ResponseForgotPassword>() {
            @Override
            public void onResponse(Call<ResponseForgotPassword> call, Response<ResponseForgotPassword> response) {
                Toast.makeText(application, response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseForgotPassword> call, Throwable t) {

            }
        });
        return forgotPass;
    }

    public MutableLiveData<ResponseChangePassword> getChangePass(String token, String old, String pass, String confirm){
        Call<ResponseChangePassword> call = api.changePass(token, old, pass, confirm);
        call.enqueue(new Callback<ResponseChangePassword>() {
            @Override
            public void onResponse(Call<ResponseChangePassword> call, Response<ResponseChangePassword> response) {
                changePass.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ResponseChangePassword> call, Throwable t) {

            }
        });


        return changePass;
    }

    public MutableLiveData<ResponseEditProfile> getEditProfile(String token, String fname, String lname, String email, String dob, String phone, String pic) {
        Call<ResponseEditProfile> call = api.editProfile(token, fname, lname, email, dob, phone, pic);
        call.enqueue(new Callback<ResponseEditProfile>() {
            @Override
            public void onResponse(Call<ResponseEditProfile> call, Response<ResponseEditProfile> response) {
                editProfile.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ResponseEditProfile> call, Throwable t) {

            }
        });

        return editProfile;
    }

    public MutableLiveData<DataOrderDetail> getOrderDetail(String token, String id) {
        Call<ResponseOrderDetail> call = api.orderDetail(token, id);
        call.enqueue(new Callback<ResponseOrderDetail>() {
            @Override
            public void onResponse(Call<ResponseOrderDetail> call, Response<ResponseOrderDetail> response) {
                orderDetail.setValue(response.body().getData());
            }

            @Override
            public void onFailure(Call<ResponseOrderDetail> call, Throwable t) {

            }
        });

        return orderDetail;
    }

    public MutableLiveData<List<DataOrderList>> getOrderL(String token) {
        Call<ResponseOrderList> orderout = api.orderList(token);
        orderout.enqueue(new Callback<ResponseOrderList>() {
            @Override
            public void onResponse(Call<ResponseOrderList> call, Response<ResponseOrderList> response) {
                orderL.setValue(response.body().getData());
            }

            @Override
            public void onFailure(Call<ResponseOrderList> call, Throwable t) {

            }
        });
        return orderL;
    }


    public MutableLiveData<ResponseOrder> getOrderplace(String token, String address) {
        Call<ResponseOrder> orderout = api.placeorder(token, address);
        orderout.enqueue(new Callback<ResponseOrder>() {
            @Override
            public void onResponse(Call<ResponseOrder> call, Response<ResponseOrder> response) {
                if (response.isSuccessful()) {
                    orderplace.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseOrder> call, Throwable t) {
                orderplace.setValue(null);
            }
        });
        return orderplace;
    }

    public MutableLiveData<DataLogin> getDataL(String email, String password) {
        Call<ResponseLogin> call = api.loginuser(email, password);
        call.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                if (response.isSuccessful()) {
                    dataL.setValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {

            }
        });
        return dataL;
    }

//    public LiveData<DataLogin> getLoginD(String email, String password){
//        Call<ResponseLogin> call = api.loginuser(email, password);
//        call.enqueue(new Callback<ResponseLogin>() {
//            @Override
//            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
//                if (response.isSuccessful()) {
//                    DataLogin loginD= response.body().getData();
//                    Log.d("mail", loginD.getEmail());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseLogin> call, Throwable t) {
//                Log.d("main", "FailedRepo");
//            }
//        });
//
//        try {
//            Log.d("mail", "LoginD is: "+loginD.getEmail());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return loginD;
//    }


}
