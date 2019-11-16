package com.nuveq.sojibdemo.appdata.room;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


@Dao
public interface DatabaseDao {

    @Insert
    long insertTrackingData(TrackingPost request);

    @Query("select * from tracking_table where status=0")
    List<TrackingPost> retrieveData();


    @Query("select COUNT(*) from tracking_table where status=0")
    long countData();


    @Query("delete from tracking_table where id=:trackId")
    int deleteData(int trackId);

}
