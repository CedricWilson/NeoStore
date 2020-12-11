package com.example.neostore2.Helpers;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.neostore2.Data.DataOrderDetail;
import com.example.neostore2.Data.DataOrderList;
import com.example.neostore2.Data.DataProduct;
import com.example.neostore2.Response.ResponseCart;
import com.example.neostore2.Response.ResponseChangePassword;
import com.example.neostore2.Response.ResponseDetails;
import com.example.neostore2.Response.ResponseEditProfile;
import com.example.neostore2.Response.ResponseForgotPassword;
import com.example.neostore2.Response.ResponseLogin;
import com.example.neostore2.Response.ResponseOrder;
import com.example.neostore2.Response.ResponseRegistration;

import java.util.List;

public class RetroViewModel extends AndroidViewModel {

    private final RetroRepo retroRepo;
    private MutableLiveData<ResponseOrder> orderplace = new MutableLiveData<>();

    public RetroViewModel(@NonNull Application application) {
        super(application);
        retroRepo = new RetroRepo(application);
    }

    public String editcart(String token,String id,String quanitity){
        return retroRepo.getEditCart(token, id, quanitity);
    }

    public String deletecart(String token,String id){
        return retroRepo.getDeletecart(token, id);
    }

    public MutableLiveData<ResponseCart> listcart(String token){
        return retroRepo.getListcart(token);
    }

    public MutableLiveData<String> addcart(String token, String id, String quantity){
        return retroRepo.getAddcart(token, id, quantity);
    }

    public String testrate(String id,String rate){
        return retroRepo.getTestrate(id, rate);
    }

    public MutableLiveData<ResponseDetails> productdetails(String id){
        return retroRepo.getDetails(id);
    }

    public MutableLiveData<List<DataProduct>> listproduct(String val){
        return retroRepo.getProductList(val);
    }

    public MutableLiveData<String> cartcount(String token){
        return retroRepo.getCartcount(token);
    }

    public MutableLiveData<ResponseLogin> login(String email, String password){
        return retroRepo.login(email, password);
    }

    public MutableLiveData<ResponseRegistration> register(String firstname, String lastname, String email, String password, String confirm, String gender, String phone){
        return retroRepo.getRegister(firstname, lastname, email, password, confirm, gender, phone);
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



}
