package com.nuveq.sojibdemo.service;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nuveq.sojibdemo.appdata.SharedPreferencesEnum;
import com.nuveq.sojibdemo.appdata.room.RoomDataRepository;
import com.nuveq.sojibdemo.appdata.room.TrackingPost;
import com.nuveq.sojibdemo.utils.CommonUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomDataRetriveSaveToServerTask extends AsyncTask<Void, Void, Void> {
    RoomDataRepository roomDataRepository;
    Context context;
    int i = 0;
    DeleteDataTask deleteDataTask;
    Gson gson = new Gson();

    public RoomDataRetriveSaveToServerTask(RoomDataRepository roomDataRepository, Context context) {
        this.roomDataRepository = roomDataRepository;
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if (roomDataRepository.countRow() > 0) {
            ArrayList<TrackingPost> trackingDataList = new ArrayList<>();
            trackingDataList.addAll(roomDataRepository.getLocalTrackingData());
            i = 0;
            while (i < trackingDataList.size()) {
                com.nuveq.sojibdemo.datamodel.TrackingPost post = new com.nuveq.sojibdemo.datamodel.TrackingPost();
                post.setDate(trackingDataList.get(i).getDate());
                post.setEmpid(String.valueOf(SharedPreferencesEnum.getInstance(context).getInt(SharedPreferencesEnum.Key.USER_ID)));
                post.setLatpoint(trackingDataList.get(i).getLatpoint());
                post.setLogpoint(trackingDataList.get(i).getLogpoint());
                post.setTime(trackingDataList.get(i).getTime());
                String jsonString = gson.toJson(post);
                JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
                CommonUtils.getApiService().postTracking(jsonObject).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (i < trackingDataList.size()) {
                                    deleteDataTask = new DeleteDataTask(roomDataRepository);
                                    deleteDataTask.execute(trackingDataList.get(i).getId());
                                }
                                Log.e("run", "local to server");
                                i++;
                            } else {
                            }
                        } else {
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                    }
                });
            }
        }

        return null;
    }

    public void destroyTask() {
        if (deleteDataTask != null) {
            deleteDataTask.cancel(true);
            deleteDataTask = null;
        }
    }
}
