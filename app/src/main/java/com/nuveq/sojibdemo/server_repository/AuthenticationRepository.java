package com.nuveq.sojibdemo.server_repository;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nuveq.sojibdemo.datamodel.AuthenticationPost;
import com.nuveq.sojibdemo.datamodel.login.LoginResponse;
import com.nuveq.sojibdemo.datamodel.login.Result;
import com.nuveq.sojibdemo.datamodel.registration.Data;
import com.nuveq.sojibdemo.listener.ServerResponseFailedCallback;
import com.nuveq.sojibdemo.utils.CommonUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticationRepository {

    private MutableLiveData<String> isRegister;
    private MutableLiveData<String> getMacData;
    private MutableLiveData<Result> getLoginData;
    private Gson gson = new Gson();
    private ServerResponseFailedCallback mListener;

    public MutableLiveData<String> getRegistrationResponse(Data data) {
        isRegister = new MutableLiveData<>();
        String jsonString = gson.toJson(data);
        JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();

        CommonUtils.getApiService().register(jsonObject).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    if (mListener != null) {
                        isRegister.setValue(response.body());
                    }
                } else {
                    if (mListener != null) {
                        mListener.onFailed(response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (mListener != null) {
                    mListener.onFailed(t.getMessage());
                }
            }
        });

        return isRegister;
    }

    public MutableLiveData<Result> getLoginData(AuthenticationPost object) {
        getLoginData = new MutableLiveData<>();
        String jsonString = gson.toJson(object);
        JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();

        CommonUtils.getApiService().getLogin(jsonObject).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getStatus()) {
                            getLoginData.setValue(response.body().getResult().get(0));
                        } else {
                            if (mListener != null) {
                                mListener.onFailed("Login Failed");
                            }
                        }
                    } else {
                        if (mListener != null) {
                            mListener.onFailed(response.message());
                        }
                    }
                } else {
                    if (mListener != null) {
                        mListener.onFailed(response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
        return getLoginData;
    }


    public MutableLiveData<String> getMacData(String mac) {
        getMacData = new MutableLiveData<>();
        Gson gson = new Gson();
        AuthenticationPost post = new AuthenticationPost();
        post.setMacaddress(mac);
        String jsonString = gson.toJson(post);
        JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
        CommonUtils.getApiService().getDataBytMac(jsonObject).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (!response.body().contains("Invalid Account!")) {
                            getMacData.setValue(response.body());
                        } else {
                            if (mListener != null) {
                                mListener.onFailed("failed");
                            }
                        }
                        // getMacData.setValue(response.body());
                    } else {
                        if (mListener != null) {
                            mListener.onFailed(response.message());
                        }
                    }
                } else {
                    if (mListener != null) {
                        mListener.onFailed(response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (mListener != null) {
                    mListener.onFailed(t.getMessage());
                }
            }
        });
        return getMacData;
    }


    public void setCallbackListener(ServerResponseFailedCallback mListener) {
        this.mListener = mListener;
    }

}
