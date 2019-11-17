package com.nuveq.sojibdemo.server_repository;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nuveq.sojibdemo.feature.admin.datamodel.employee_list.EmployeeResponse;
import com.nuveq.sojibdemo.feature.admin.datamodel.tracking.AdminTrackingPost;
import com.nuveq.sojibdemo.feature.admin.datamodel.tracking.Result;
import com.nuveq.sojibdemo.feature.admin.datamodel.tracking.TrackingDataresponse;
import com.nuveq.sojibdemo.listener.ServerResponseFailedCallback;
import com.nuveq.sojibdemo.utils.CommonUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminRepository {
    private MutableLiveData<List<Result>> trackingDataList;
    private MutableLiveData<List<com.nuveq.sojibdemo.feature.admin.datamodel.employee_list.Result>> employeeList;
    private Gson gson = new Gson();
    private ServerResponseFailedCallback mListener;


    public void setCallbackListener(ServerResponseFailedCallback mListener) {
        this.mListener = mListener;
    }

    public MutableLiveData<List<Result>> getTrackingDataList(AdminTrackingPost post) {
        trackingDataList = new MutableLiveData<>();
        String jsonString = gson.toJson(post);
        JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
        CommonUtils.getApiService().getTrackingList(jsonObject).enqueue(new Callback<TrackingDataresponse>() {
            @Override
            public void onResponse(Call<TrackingDataresponse> call, Response<TrackingDataresponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {
                        trackingDataList.setValue(response.body().getResult());
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
            public void onFailure(Call<TrackingDataresponse> call, Throwable t) {
                if (mListener != null) {
                    mListener.onFailed("Something went wrong on server or check your connection\ntry again");
                }
            }
        });

        return trackingDataList;
    }

    public MutableLiveData<List<com.nuveq.sojibdemo.feature.admin.datamodel.employee_list.Result>> getEmployeeList(String empId) {
        employeeList = new MutableLiveData<>();
        Map map = new HashMap();
        map.put("empid", empId);
        String jsonString = gson.toJson(map);
        JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
        CommonUtils.getApiService().getEmployeeList(jsonObject).enqueue(new Callback<EmployeeResponse>() {
            @Override
            public void onResponse(Call<EmployeeResponse> call, Response<EmployeeResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {
                        employeeList.setValue(response.body().getResult());
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
            public void onFailure(Call<EmployeeResponse> call, Throwable t) {
                if (mListener != null) {
                    mListener.onFailed("Something went wrong on server or check your connection\ntry again");
                }
            }
        });
        return employeeList;
    }
}
