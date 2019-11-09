package com.nuveq.sojibdemo.appdata.room;

import android.content.Context;


public class RoomDataRepository {
    private static RoomDataRepository sInstance;
    private final MyDatabase mDatabase;

    private RoomDataRepository(final MyDatabase database) {
        mDatabase = database;
    }

    public static RoomDataRepository getInstance(Context context) {
        if (sInstance == null) {
            synchronized (RoomDataRepository.class) {
                if (sInstance == null) {
                    sInstance = new RoomDataRepository(MyDatabase.getInstance(context));
                }
            }
        }
        return sInstance;
    }

    public long insertTrackingData(TrackingPost request) {
        return mDatabase.myDatabaseDao().insertTrackingData(request);
    }

}
