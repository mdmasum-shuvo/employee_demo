package com.nuveq.sojibdemo.server_repository;

import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nuveq.sojibdemo.R;
import com.nuveq.sojibdemo.datamodel.global.CategoryDatum;
import com.nuveq.sojibdemo.datamodel.global.area.AreaPost;
import com.nuveq.sojibdemo.datamodel.global.area.AreaResponse;
import com.nuveq.sojibdemo.datamodel.global.area.Emp;
import com.nuveq.sojibdemo.datamodel.registration.Registration;
import com.nuveq.sojibdemo.listener.ServerResponseFailedCallback;
import com.nuveq.sojibdemo.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GlobalRepository {
    private MutableLiveData<List<Registration>> brachDataList;
    private MutableLiveData<List<CategoryDatum>> visitCatDataList;
    private MutableLiveData<List<Emp>> visitAreaDataList;
    private ServerResponseFailedCallback mListener;

    public MutableLiveData<List<Registration>> getBranchDataList() {
        brachDataList = new MutableLiveData<>();
        CommonUtils.getApiService().getBranch().enqueue(new Callback<ArrayList<Registration>>() {
            @Override
            public void onResponse(Call<ArrayList<Registration>> call, Response<ArrayList<Registration>> response) {
                if (response.isSuccessful()) {
                    brachDataList.setValue(response.body());

                } else {
                    Log.e("", "");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Registration>> call, Throwable t) {
                Log.e("", "");


            }
        });
        return brachDataList;
    }

    public MutableLiveData<List<CategoryDatum>> getVisitCatDataList() {
        visitCatDataList = new MutableLiveData<>();

        CommonUtils.getApiService().getCategoryData().enqueue(new Callback<ArrayList<CategoryDatum>>() {
            @Override
            public void onResponse(Call<ArrayList<CategoryDatum>> call, Response<ArrayList<CategoryDatum>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        visitCatDataList.setValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CategoryDatum>> call, Throwable t) {

            }
        });

        return visitCatDataList;
    }

    public MutableLiveData<List<Emp>> getVisitAreaDataList(String id) {
        visitAreaDataList = new MutableLiveData<>();
        Gson gson = new Gson();
        AreaPost areaPost = new AreaPost();
        areaPost.setCategoryid(id);
        String jsonString = gson.toJson(areaPost);
        JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();

        CommonUtils.getApiService().getAreaData(jsonObject).enqueue(new Callback<AreaResponse>() {
            @Override
            public void onResponse(Call<AreaResponse> call, Response<AreaResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getStatus()) {
                            visitAreaDataList.setValue(response.body().getEmp());
                        } else {
                            if (mListener != null) {
                                mListener.onFailed("Area " + response.body().getMessage());
                            }
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<AreaResponse> call, Throwable t) {

            }
        });

        return visitAreaDataList;
    }


    public void setCallbackListener(ServerResponseFailedCallback mListener) {
        this.mListener = mListener;
    }
}
