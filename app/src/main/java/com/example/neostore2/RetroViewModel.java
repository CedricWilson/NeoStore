package com.example.neostore2;

import android.app.Application;
import android.icu.text.Collator;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class RetroViewModel extends AndroidViewModel {

    private final RetroRepo retroRepo;
    private MutableLiveData<ResponseOrder> orderplace = new MutableLiveData<>();
    private MutableLiveData<DataLogin> dataL = new MutableLiveData<>();


    public RetroViewModel(@NonNull Application application) {
        super(application);
        retroRepo = new RetroRepo(application);
    }

    public LiveData<ResponseForgotPassword> getForgotPass(String email){
        return retroRepo.getForgotPass(email);
    }


    public MutableLiveData<ResponseChangePassword> getChangePassword(String token, String old, String pass, String confirm){
        return retroRepo.getChangePass(token, old, pass, confirm);
    }

    public MutableLiveData<ResponseEditProfile> getEditProfile(String token, String fname, String lname, String email, String dob, String phone, String pic) {
        return retroRepo.getEditProfile(token, fname, lname, email, dob, phone, pic);
    }

    public MutableLiveData<DataOrderDetail> getOrderDetail(String token, String id) {
        return retroRepo.getOrderDetail(token, id);
    }

    public MutableLiveData<List<DataOrderList>> getOrderList(String token) {
        return retroRepo.getOrderL(token);
    }


    public MutableLiveData<ResponseOrder> getOrderplace(String token, String address) {
        orderplace = loadOrder(token, address);
        return orderplace;
    }

    private MutableLiveData<ResponseOrder> loadOrder(String token, String address) {
        return retroRepo.getOrderplace(token, address);
    }

//    public DataLogin getLoginD(String email, String password){
//        return  retroRepo.getLoginD(email,password);
//    }

    public MutableLiveData<DataLogin> getLoginD(String email, String password) {
        return retroRepo.getDataL(email, password);
    }


}
