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

        return null;
    }

    public void destroyTask() {
        if (deleteDataTask != null) {
            deleteDataTask.cancel(true);
            deleteDataTask = null;
        }
    }
}
