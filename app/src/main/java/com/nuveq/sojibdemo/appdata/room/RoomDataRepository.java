package com.nuveq.sojibdemo.appdata.room;

import java.util.List;


public class RoomDataRepository {
    private static RoomDataRepository sInstance;
    private final MyDatabase mDatabase;

    private RoomDataRepository(final MyDatabase database) {
        mDatabase = database;
    }

    public static RoomDataRepository getInstance() {
        if (sInstance == null) {
            synchronized (RoomDataRepository.class) {
                if (sInstance == null) {
                    sInstance = new RoomDataRepository(MyDatabase.getInstance());
                }
            }
        }
        return sInstance;
    }

    public long insertTrackingData(TrackingPost request) {
        return mDatabase.myDatabaseDao().insertTrackingData(request);
    }

    public List<TrackingPost> getLocalTrackingData() {
        return mDatabase.myDatabaseDao().retrieveData();
    }

    public int deleteOne(int trackId) {
        return mDatabase.myDatabaseDao().deleteData(trackId);
    }


    public long countRow() {
        return mDatabase.myDatabaseDao().countData();
    }

}
