package com.nuveq.sojibdemo.appdata.room;


import androidx.room.Dao;
import androidx.room.Insert;


@Dao
public interface DatabaseDao {

    @Insert
    long insertTrackingData(TrackingPost request);

}
