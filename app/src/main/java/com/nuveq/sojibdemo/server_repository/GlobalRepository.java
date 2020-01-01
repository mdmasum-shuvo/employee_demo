package com.nuveq.sojibdemo.server_repository;


import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nuveq.sojibdemo.appdata.SharedPreferencesEnum;
import com.nuveq.sojibdemo.datamodel.VisitLocationPost;
import com.nuveq.sojibdemo.datamodel.global.branch.BranchPost;
import com.nuveq.sojibdemo.datamodel.global.branch.BranchResponse;
import com.nuveq.sojibdemo.datamodel.global.branch.Result;
import com.nuveq.sojibdemo.datamodel.global.area.AreaPost;
import com.nuveq.sojibdemo.datamodel.global.area.AreaResponse;
import com.nuveq.sojibdemo.datamodel.global.cat.CatResponse;
import com.nuveq.sojibdemo.listener.ServerResponseFailedCallback;
import com.nuveq.sojibdemo.utils.CommonUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GlobalRepository {
    private MutableLiveData<List<Result>> brachDataList;
    private MutableLiveData<String> locationDataResponse;
    private MutableLiveData<List<com.nuveq.sojibdemo.datamodel.global.cat.Result>> visitCatDataList;
    private MutableLiveData<List<com.nuveq.sojibdemo.datamodel.global.area.Result>> visitAreaDataList;
    private MutableLiveData<List<com.nuveq.sojibdemo.datamodel.global.area.Result>> toDoctorList;
    private ServerResponseFailedCallback mListener;
    private MutableLiveData<List<com.nuveq.sojibdemo.datamodel.global.area.Result>> shiftList;
    Gson gson = new Gson();

    public MutableLiveData<List<Result>> getBranchDataList() {
        brachDataList = new MutableLiveData<>();
        CommonUtils.getApiService().getBranch().enqueue(new Callback<BranchResponse>() {
            @Override
            public void onResponse(Call<BranchResponse> call, Response<BranchResponse> response) {
                if (response.isSuccessful()) {
                    brachDataList.setValue(response.body().getResult());

                } else {
                    if (mListener != null) {
                        mListener.onFailed("Something went wrong on server or check your connection\ntry again");
                    }
                }
            }

            @Override
            public void onFailure(Call<BranchResponse> call, Throwable t) {
                if (mListener != null) {
                    mListener.onFailed("Something went wrong on server or check your connection\ntry again");
                }

            }
        });
        return brachDataList;
    }

    public MutableLiveData<List<com.nuveq.sojibdemo.datamodel.global.cat.Result>> getVisitCatDataList() {
        visitCatDataList = new MutableLiveData<>();

        CommonUtils.getApiService().getCategoryData().enqueue(new Callback<CatResponse>() {
            @Override
            public void onResponse(Call<CatResponse> call, Response<CatResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        visitCatDataList.setValue(response.body().getResult());
                    }
                }
            }

            @Override
            public void onFailure(Call<CatResponse> call, Throwable t) {
                if (mListener != null) {
                    mListener.onFailed("Something went wrong on server or check your connection\ntry again");
                }
            }
        });

        return visitCatDataList;
    }

    public MutableLiveData<String> locationPost(VisitLocationPost post) {
        locationDataResponse = new MutableLiveData<>();

        String jsonString = gson.toJson(post);
        JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
        CommonUtils.getApiService().locationPost(jsonObject).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    locationDataResponse.setValue(response.body());
                } else {
                    if (mListener != null) {
                        mListener.onFailed("Something went wrong on server\ntry again");
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (mListener != null) {
                    mListener.onFailed("Something went wrong on server or check your connection\ntry again");
                }
            }
        });
        return locationDataResponse;

    }

    public MutableLiveData<List<com.nuveq.sojibdemo.datamodel.global.area.Result>> getVisitAreaDataList(String id) {
        visitAreaDataList = new MutableLiveData<>();
        AreaPost areaPost = new AreaPost();
        areaPost.setCategoryid(id);
        areaPost.setEmpid(SharedPreferencesEnum.getInstance().getString(SharedPreferencesEnum.Key.USER_ID));
        String jsonString = gson.toJson(areaPost);
        JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();

        CommonUtils.getApiService().getAreaData(jsonObject).enqueue(new Callback<AreaResponse>() {
            @Override
            public void onResponse(Call<AreaResponse> call, Response<AreaResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getStatus()) {
                            visitAreaDataList.setValue(response.body().getResult());
                        } else {
                            if (mListener != null) {
                                mListener.onFailed("Area " + response.body().getMessage());
                            }
                        }

                    }
                } else {
                    if (mListener != null) {
                        mListener.onFailed("Something went wrong on server\ntry again");
                    }
                }
            }

            @Override
            public void onFailure(Call<AreaResponse> call, Throwable t) {
                if (mListener != null) {
                    mListener.onFailed("Something went wrong on server or check your connection\ntry again");
                }
            }
        });

        return visitAreaDataList;
    }

    public MutableLiveData<List<com.nuveq.sojibdemo.datamodel.global.area.Result>> getToDoctorList(int id) {
        toDoctorList = new MutableLiveData<>();
        BranchPost areaPost = new BranchPost();
        areaPost.setBranchid(id);
        String jsonString = gson.toJson(areaPost);
        JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();

        CommonUtils.getApiService().getToDoctor(jsonObject).enqueue(new Callback<AreaResponse>() {
            @Override
            public void onResponse(Call<AreaResponse> call, Response<AreaResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getStatus()) {
                            toDoctorList.setValue(response.body().getResult());
                        } else {

                        }

                    }
                } else {
                    if (mListener != null) {
                        mListener.onFailed("Something went wrong on server\ntry again");
                    }
                }
            }

            @Override
            public void onFailure(Call<AreaResponse> call, Throwable t) {
                if (mListener != null) {
                    mListener.onFailed("Something went wrong on server or check your connection\ntry again");
                }
            }
        });

        return toDoctorList;
    }

    public MutableLiveData<List<com.nuveq.sojibdemo.datamodel.global.area.Result>> getDoctorDataList() {
        visitAreaDataList = new MutableLiveData<>();

        CommonUtils.getApiService().getDoctor().enqueue(new Callback<AreaResponse>() {
            @Override
            public void onResponse(Call<AreaResponse> call, Response<AreaResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getStatus()) {
                            visitAreaDataList.setValue(response.body().getResult());
                        } else {
                            if (mListener != null) {
                                mListener.onFailed("Doctor " + response.body().getMessage());
                            }
                        }

                    }
                } else {
                    if (mListener != null) {
                        mListener.onFailed("Something went wrong on server or check your connection\ntry again");
                    }
                }
            }

            @Override
            public void onFailure(Call<AreaResponse> call, Throwable t) {
                if (mListener != null) {
                    mListener.onFailed("Something went wrong on server or check your connection\ntry again");
                }
            }
        });

        return visitAreaDataList;
    }

    public MutableLiveData<List<com.nuveq.sojibdemo.datamodel.global.area.Result>> getShiftList() {
        shiftList = new MutableLiveData<>();

        CommonUtils.getApiService().getShiftList().enqueue(new Callback<AreaResponse>() {
            @Override
            public void onResponse(Call<AreaResponse> call, Response<AreaResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {
                        shiftList.setValue(response.body().getResult());
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
            public void onFailure(Call<AreaResponse> call, Throwable t) {
                if (mListener != null) {
                    mListener.onFailed("Something went wrong on server or check your connection\ntry again");
                }
            }
        });
        return shiftList;

    }

    public void setCallbackListener(ServerResponseFailedCallback mListener) {
        this.mListener = mListener;
    }
}
