package com.nuveq.sojibdemo.server_repository;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nuveq.sojibdemo.datamodel.AttendDatePost;
import com.nuveq.sojibdemo.datamodel.AttendancePost;
import com.nuveq.sojibdemo.datamodel.sales.Result;
import com.nuveq.sojibdemo.datamodel.sales.SalesPost;
import com.nuveq.sojibdemo.datamodel.sales.SalesResponse;
import com.nuveq.sojibdemo.listener.ServerResponseFailedCallback;
import com.nuveq.sojibdemo.utils.CommonUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalesRepository {

    public MutableLiveData<String> salesEntryResponse;
    public MutableLiveData<List<Result>> salesList;
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

    public MutableLiveData<List<Result>> getSalesList(AttendDatePost post) {
        salesList = new MutableLiveData<>();
        String jsonString = gson.toJson(post);
        JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
        CommonUtils.getApiService().getSalesData(jsonObject).enqueue(new Callback<SalesResponse>() {
            @Override
            public void onResponse(Call<SalesResponse> call, Response<SalesResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {
                        salesList.setValue(response.body().getResult());
                    } else {
                        if (mListener != null) {
                            mListener.onFailed(response.body().getMessage());
                        }
                    }
                } else {
                    if (mListener != null) {
                        mListener.onFailed(response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<SalesResponse> call, Throwable t) {
                if (mListener != null) {
                    mListener.onFailed("Something went wrong on server\ntry again");
                }
            }
        });


        return salesList;
    }


    public void setCallbackListener(ServerResponseFailedCallback mListener) {
        this.mListener = mListener;
    }
}
