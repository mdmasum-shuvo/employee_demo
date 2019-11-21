package com.nuveq.sojibdemo.service;

import android.os.AsyncTask;
import android.util.Log;

import com.nuveq.sojibdemo.appdata.room.RoomDataRepository;
import com.nuveq.sojibdemo.appdata.room.TrackingPost;
import com.nuveq.sojibdemo.utils.CommonUtils;

class MyRoomDataInsertTask extends AsyncTask<TrackingPost, Void, Long> {
    RoomDataRepository roomDataRepository;

    public MyRoomDataInsertTask(RoomDataRepository roomDataRepository) {
        this.roomDataRepository = roomDataRepository;
    }


    @Override
    protected Long doInBackground(TrackingPost... trackingPosts) {
        long id = roomDataRepository.insertTrackingData(trackingPosts[0]);
        Log.e("run", "location data save in local:" + id);
        return id;
    }

    @Override
    protected void onPostExecute(Long integer) {
        super.onPostExecute(integer);
    }
}
