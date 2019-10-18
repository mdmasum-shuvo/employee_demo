package com.nuveq.sojibdemo.server_repository;

import android.util.JsonReader;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

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

    MutableLiveData<Boolean> isRegister;
    MutableLiveData<MacResponse> getMacData;
    private ServerResponseFailedCallback mListener;

    public MutableLiveData<Boolean> getRegistrationResponse(Data data) {
        isRegister = new MutableLiveData<>();
        Gson gson = new Gson();
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


    public MutableLiveData<MacResponse> getMacData(String mac) {
        getMacData = new MutableLiveData<>();
        Gson gson = new Gson();
        AuthenticationPost post = new AuthenticationPost();
        post.setMacaddress("6c55e5653fc2d8");
        post.setPassword("f");
        String jsonString = gson.toJson(post);
        JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
        CommonUtils.getApiService().getDataBytMac(jsonObject).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() !=null) {
                       // getMacData.setValue(response.body());
                    } else {
                        if (mListener != null) {
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
