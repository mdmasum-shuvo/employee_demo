package com.nuveq.sojibdemo.service;

import android.os.AsyncTask;

import com.nuveq.sojibdemo.appdata.room.RoomDataRepository;

public class DeleteDataTask extends AsyncTask<Integer, Void, Void> {
    RoomDataRepository roomDataRepository;

    public DeleteDataTask(RoomDataRepository roomDataRepository) {
        this.roomDataRepository = roomDataRepository;
    }

    @Override
    protected Void doInBackground(Integer... integers) {
        roomDataRepository.deleteOne(integers[0]);
        return null;
    }
}
