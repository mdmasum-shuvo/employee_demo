package com.nuveq.sojibdemo.service;

import android.os.AsyncTask;

import com.nuveq.sojibdemo.appdata.room.RoomDataRepository;
import com.nuveq.sojibdemo.appdata.room.TrackingPost;

class MyRoomDataInsertTask extends AsyncTask<TrackingPost, Void, Long> {
    RoomDataRepository roomDataRepository;

    public MyRoomDataInsertTask(RoomDataRepository roomDataRepository) {
        this.roomDataRepository = roomDataRepository;
    }


    @Override
    protected Long doInBackground(TrackingPost... trackingPosts) {
        long id = roomDataRepository.insertTrackingData(trackingPosts[0]);
        return id;
    }

    @Override
    protected void onPostExecute(Long integer) {
        super.onPostExecute(integer);
    }
}
