package com.nuveq.sojibdemo.server_repository;

import android.util.JsonReader;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;
import com.nuveq.sojibdemo.datamodel.MacResponse;
import com.nuveq.sojibdemo.datamodel.registration.Registration;
import com.nuveq.sojibdemo.listener.ServerResponseFailedCallback;
import com.nuveq.sojibdemo.utils.CommonUtils;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticationRepository {

    MutableLiveData<Boolean> isRegister;
    MutableLiveData<MacResponse> getMacData;
    private ServerResponseFailedCallback mListener;

    public MutableLiveData<Boolean> getRegistrationResponse(JsonObject jsonObject) {
        isRegister = new MutableLiveData<>();


        CommonUtils.getApiService().register(jsonObject).enqueue(new Callback<Registration>() {
            @Override
            public void onResponse(Call<Registration> call, Response<Registration> response) {
                if (response.isSuccessful()) {
                    isRegister.setValue(true);
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
        Map<String, String> map = new HashMap<String, String>();
        map.put("macaddress", "6c55e5653fc2d84");
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("macaddress", "6c55e5653fc2d84");
        CommonUtils.getApiService().getDataBytMac("6c55e5653fc2d84").enqueue(new Callback<MacResponse>() {
            @Override
            public void onResponse(Call<MacResponse> call, Response<MacResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("true")) {
                        getMacData.setValue(response.body());
                    } else {
                        if (mListener != null) {
                           // mListener.onFailed(response.body().getMessage());
                        }
                    }
                }
                else
                if (mListener != null) {
                    mListener.onFailed(response.message());
                }
            }

            @Override
            public void onFailure(Call<MacResponse> call, Throwable t) {
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
