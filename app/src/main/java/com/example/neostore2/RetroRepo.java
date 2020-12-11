package com.example.neostore2;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetroRepo {

    private API api;
    private Application application;
    private final MutableLiveData<ResponseOrder> orderplace = new MutableLiveData<>();
    private final MutableLiveData<List<DataOrderList>> orderL = new MutableLiveData<>();
    private final MutableLiveData<DataOrderDetail> orderDetail = new MutableLiveData<>();
    private final MutableLiveData<ResponseEditProfile> editProfile = new MutableLiveData<>();
    private final MutableLiveData<ResponseChangePassword> changePass = new MutableLiveData<>();
    private LiveData<ResponseForgotPassword> forgotPass;
    private MutableLiveData<ResponseRegistration> register = new MutableLiveData<>();
    private final MutableLiveData<ResponseLogin> login = new MutableLiveData<>();
    private final MutableLiveData<String> cartcount = new MutableLiveData<>();
    private final MutableLiveData<List<DataProduct>> product = new MutableLiveData<>();
    private final MutableLiveData<ResponseDetails> details = new MutableLiveData<>();
    private String testrate;
    private MutableLiveData<String> addcart = new MutableLiveData<>();
    private final MutableLiveData<ResponseCart> listcart = new MutableLiveData<>();
    private String deletecart;
    private String editCart;

    public RetroRepo(Application application) {
        api = RetrofitClient.getInstance().getApi();
        this.application = application;
    }

    public String getEditCart(String token, String id, String quantity){
        Call<ResponseEditCart> call = api.editCart(token, id, quantity);
        call.enqueue(new Callback<ResponseEditCart>() {
            @Override
            public void onResponse(Call<ResponseEditCart> call, Response<ResponseEditCart> response) {

            }

            @Override
            public void onFailure(Call<ResponseEditCart> call, Throwable t) {

            }
        });

        return editCart;
    }

    public String getDeletecart(String token, String id) {
        Call<ResponseDeleteCart> call = api.deleteCart(token, id);
        call.enqueue(new Callback<ResponseDeleteCart>() {
            @Override
            public void onResponse(Call<ResponseDeleteCart> call, Response<ResponseDeleteCart> response) {
                Toast.makeText(application, response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseDeleteCart> call, Throwable t) {

            }
        });

        return deletecart;
    }

    public MutableLiveData<ResponseCart> getListcart(String token) {
        Call<ResponseCart> call = api.listcart(token);
        call.enqueue(new Callback<ResponseCart>() {
            @Override
            public void onResponse(Call<ResponseCart> call, Response<ResponseCart> response) {
                listcart.postValue(response.body());
            }

            @Override
            public void onFailure(Call<ResponseCart> call, Throwable t) {

            }
        });
        return listcart;
    }

    public MutableLiveData<String> getAddcart(String token, String id, String quantity) {
        Call<ResponseAddCart> call = api.addcart(token, id, quantity);
        call.enqueue(new Callback<ResponseAddCart>() {
            @Override
            public void onResponse(Call<ResponseAddCart> call, Response<ResponseAddCart> response) {
                Toast.makeText(application, response.body().getUser_msg(), Toast.LENGTH_SHORT).show();
                if (response.body().getUser_msg() != null) {
                    addcart.postValue("1");
                }
            }

            @Override
            public void onFailure(Call<ResponseAddCart> call, Throwable t) {

            }
        });

        return addcart;
    }

    public String getTestrate(String id, String valrate) {
        Call<ResponseRate> call = api.setRating(id, valrate);
        call.enqueue(new Callback<ResponseRate>() {
            @Override
            public void onResponse(Call<ResponseRate> call, Response<ResponseRate> response) {
                Toast.makeText(application, response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseRate> call, Throwable t) {

            }
        });
        return testrate;
    }

    public MutableLiveData<ResponseDetails> getDetails(String id) {
        Call<ResponseDetails> call = api.getDetails(id);
        call.enqueue(new Callback<ResponseDetails>() {
            @Override
            public void onResponse(Call<ResponseDetails> call, Response<ResponseDetails> response) {
                details.postValue(response.body());
            }

            @Override
            public void onFailure(Call<ResponseDetails> call, Throwable t) {

            }
        });


        return details;
    }

    public MutableLiveData<List<DataProduct>> getProductList(String val) {
        Call<ResponseProductList> call = api.getList(val);

        call.enqueue(new Callback<ResponseProductList>() {
            @Override
            public void onResponse(Call<ResponseProductList> call, Response<ResponseProductList> response) {
                product.postValue(response.body().getData());
            }

            @Override
            public void onFailure(Call<ResponseProductList> call, Throwable t) {

            }
        });

        return product;
    }

    public MutableLiveData<String> getCartcount(String token) {
        Call<ResponseCart> call = api
                .listcart(token);
        call.enqueue(new Callback<ResponseCart>() {
            @Override
            public void onResponse(Call<ResponseCart> call, Response<ResponseCart> response) {

                cartcount.postValue(response.body().getCount());

            }

            @Override
            public void onFailure(Call<ResponseCart> call, Throwable t) {

            }
        });

        return cartcount;

    }

    public MutableLiveData<ResponseLogin> login(String email, String password) {
        Call<ResponseLogin> call = api
                .loginuser(email, password);
        call.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(
                            application,
                            response.body().getMessage(),
                            Toast.LENGTH_LONG).show();

                    login.postValue(response.body());

                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(
                                application,
                                jObjError.getString("user_msg"),
                                Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(application, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {

            }
        });
        return login;
    }


    public MutableLiveData<ResponseRegistration> getRegister(String firstname, String lastname, String email, String password, String confirm, String gender, String phone) {
        Call<ResponseRegistration> call = api
                .createUser(firstname, lastname, email, password, confirm, gender, phone);
        call.enqueue(new Callback<ResponseRegistration>() {
            @Override
            public void onResponse(Call<ResponseRegistration> call, Response<ResponseRegistration> response) {
                if (response.isSuccessful()) {
                    Log.d("main", "" + response.body().getMessage());
                    register.postValue(response.body());


                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d("main", jObjError.toString());
                        Toast.makeText(
                                application,
                                jObjError.getString("user_msg"),
                                Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(application, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseRegistration> call, Throwable t) {

            }
        });
        return register;
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

    public MutableLiveData<ResponseChangePassword> getChangePass(String token, String old, String pass, String confirm) {
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


}
