package com.nuveq.sojibdemo.server_repository;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nuveq.sojibdemo.datamodel.VisitPlanDataPost;
import com.nuveq.sojibdemo.listener.ServerResponseFailedCallback;
import com.nuveq.sojibdemo.utils.CommonUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VisitPlanRepository {

    private MutableLiveData<String> addPlanResponse;
    private ServerResponseFailedCallback mListener;
    Gson gson = new Gson();

    public MutableLiveData<String> getAddPlanResponse(VisitPlanDataPost post) {
        addPlanResponse = new MutableLiveData<>();
        String jsonString = gson.toJson(post);
        JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
        CommonUtils.getApiService().visitPlanPost(jsonObject).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        addPlanResponse.setValue(response.body());
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
                    mListener.onFailed("Something went wrong on server\ntry again");
                }
            }
        });
        return addPlanResponse;
    }

    public void setCallbackListener(ServerResponseFailedCallback mListener) {
        this.mListener = mListener;
    }

}
