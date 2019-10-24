package com.nuveq.sojibdemo.server_repository;

import android.util.JsonReader;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.common.internal.service.Common;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nuveq.sojibdemo.datamodel.AuthenticationPost;
import com.nuveq.sojibdemo.datamodel.LoginResponse;
import com.nuveq.sojibdemo.datamodel.MacResponse;
import com.nuveq.sojibdemo.datamodel.registration.Data;
import com.nuveq.sojibdemo.datamodel.registration.Registration;
import com.nuveq.sojibdemo.listener.ServerResponseFailedCallback;
import com.nuveq.sojibdemo.network.HTTP_PARAM;
import com.nuveq.sojibdemo.utils.CommonUtils;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticationRepository {

    private MutableLiveData<Boolean> isRegister;
    private MutableLiveData<String> getMacData;
    private MutableLiveData<LoginResponse> getLoginData;
    private Gson gson = new Gson();
    private ServerResponseFailedCallback mListener;

    public MutableLiveData<Boolean> getRegistrationResponse(Data data) {
        isRegister = new MutableLiveData<>();
        String jsonString = gson.toJson(data);
        JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();

        CommonUtils.getApiService().register(jsonObject).enqueue(new Callback<Registration>() {
            @Override
            public void onResponse(Call<Registration> call, Response<Registration> response) {
                if (response.isSuccessful()) {
                    if (mListener != null) {
                        mListener.onFailed(response.body().getMessage());
                    }
                } else {
                    if (mListener != null) {
                        mListener.onFailed(response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<Registration> call, Throwable t) {
                if (mListener != null) {
                    mListener.onFailed(t.getMessage());
                }
            }
        });

        return isRegister;
    }

    public MutableLiveData<LoginResponse> getLoginData(AuthenticationPost object) {
        getLoginData = new MutableLiveData<>();
        String jsonString = gson.toJson(object);
        JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();

        CommonUtils.getApiService().getLogin(jsonObject).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getStatus()) {
                            getLoginData.setValue(response.body());
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
