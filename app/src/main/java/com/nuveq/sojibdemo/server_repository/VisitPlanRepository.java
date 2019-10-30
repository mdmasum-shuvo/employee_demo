package com.nuveq.sojibdemo.server_repository;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nuveq.sojibdemo.datamodel.AttendDatePost;
import com.nuveq.sojibdemo.datamodel.visitplan.Plan;
import com.nuveq.sojibdemo.datamodel.visitplan.VisitPlanDataPost;
import com.nuveq.sojibdemo.datamodel.visitplan.VisitPlanResponse;
import com.nuveq.sojibdemo.listener.ServerResponseFailedCallback;
import com.nuveq.sojibdemo.utils.CommonUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VisitPlanRepository {

    private MutableLiveData<String> addPlanResponse;
    private MutableLiveData<List<Plan>> planDataList;
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


    public MutableLiveData<List<Plan>> getPendingPlanList(AttendDatePost post) {
        planDataList = new MutableLiveData<>();
        String jsonString = gson.toJson(post);
        JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
        CommonUtils.getApiService().getPendingPlanData(jsonObject).enqueue(new Callback<VisitPlanResponse>() {
            @Override
            public void onResponse(Call<VisitPlanResponse> call, Response<VisitPlanResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {
                        planDataList.setValue(response.body().getPlan());

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
            public void onFailure(Call<VisitPlanResponse> call, Throwable t) {
                if (mListener != null) {
                    mListener.onFailed("Something went wrong on server\ntry again");
                }
            }
        });
        return planDataList;
    }

    public MutableLiveData<List<Plan>> getApprovedPlanList(AttendDatePost post) {
        planDataList = new MutableLiveData<>();
        String jsonString = gson.toJson(post);
        JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
        CommonUtils.getApiService().getApprovedPlanList(jsonObject).enqueue(new Callback<VisitPlanResponse>() {
            @Override
            public void onResponse(Call<VisitPlanResponse> call, Response<VisitPlanResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {
                        planDataList.setValue(response.body().getPlan());

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
            public void onFailure(Call<VisitPlanResponse> call, Throwable t) {
                if (mListener != null) {
                    mListener.onFailed("Something went wrong on server\ntry again");
                }
            }
        });
        return planDataList;
    }

    public MutableLiveData<List<Plan>> getVisitedPlanList(AttendDatePost post) {
        planDataList = new MutableLiveData<>();
        String jsonString = gson.toJson(post);
        JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
        CommonUtils.getApiService().getVisitedPlanList(jsonObject).enqueue(new Callback<VisitPlanResponse>() {
            @Override
            public void onResponse(Call<VisitPlanResponse> call, Response<VisitPlanResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {
                        planDataList.setValue(response.body().getPlan());

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
            public void onFailure(Call<VisitPlanResponse> call, Throwable t) {
                if (mListener != null) {
                    mListener.onFailed("Something went wrong on server\ntry again");
                }
            }
        });
        return planDataList;
    }

    public void setCallbackListener(ServerResponseFailedCallback mListener) {
        this.mListener = mListener;
    }

}
