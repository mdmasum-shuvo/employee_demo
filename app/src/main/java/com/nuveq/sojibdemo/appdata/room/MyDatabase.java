package com.nuveq.sojibdemo.appdata.room;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.nuveq.sojibdemo.common.App;


@Database(entities = {TrackingPost.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {

    public abstract DatabaseDao myDatabaseDao();

    private static MyDatabase instance;

    public static synchronized MyDatabase getInstance() {
        if (instance == null) {
            instance = Room.databaseBuilder(App.getContext(), MyDatabase.class, "db")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback).build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

        }
    };



}