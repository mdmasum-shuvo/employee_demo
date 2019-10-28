package com.nuveq.sojibdemo.server_repository;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nuveq.sojibdemo.datamodel.sales.SalesPost;
import com.nuveq.sojibdemo.listener.ServerResponseFailedCallback;
import com.nuveq.sojibdemo.utils.CommonUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalesRepository {

    public MutableLiveData<String> salesEntryResponse;
    Gson gson = new Gson();
    private ServerResponseFailedCallback mListener;

    public MutableLiveData<String> getSalesEntryResponse(SalesPost post) {

        salesEntryResponse = new MutableLiveData<>();
        String jsonString = gson.toJson(post);
        JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();

        CommonUtils.getApiService().salePost(jsonObject).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    salesEntryResponse.setValue(response.body());
                } else {
                    if (mListener != null) {
                        mListener.onFailed(response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (mListener != null) {
                    mListener.onFailed("Something went wrong on server\ntry again");
                }
            }
        });

        return salesEntryResponse;
    }


    public void setCallbackListener(ServerResponseFailedCallback mListener) {
        this.mListener = mListener;
    }
}
