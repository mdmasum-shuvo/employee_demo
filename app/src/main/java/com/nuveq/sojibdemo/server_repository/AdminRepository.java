package com.nuveq.sojibdemo.server_repository;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nuveq.sojibdemo.feature.admin.datamodel.AdminTracking;
import com.nuveq.sojibdemo.feature.admin.datamodel.Result;
import com.nuveq.sojibdemo.feature.admin.datamodel.TrackingDataresponse;
import com.nuveq.sojibdemo.utils.CommonUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminRepository {
    private MutableLiveData<Result> trackingDataList;
    private Gson gson = new Gson();

    public MutableLiveData<Result> getTrackingDataList(AdminTracking post) {
        trackingDataList = new MutableLiveData<>();
        String jsonString = gson.toJson(post);
        JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
        CommonUtils.getApiService().getTrackingList(jsonObject).enqueue(new Callback<TrackingDataresponse>() {
            @Override
            public void onResponse(Call<TrackingDataresponse> call, Response<TrackingDataresponse> response) {

            }

            @Override
            public void onFailure(Call<TrackingDataresponse> call, Throwable t) {

            }
        });

        return trackingDataList;
    }
}
