package com.nuveq.sojibdemo.appdata.room;


import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = RoomConstant.TRACKING_TABLE)
public class TrackingPost {


    @ColumnInfo(name = "empId")
    private String empid;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "time")
    private String time;

    @ColumnInfo(name = "ltd")
    private String latpoint;

    @ColumnInfo(name = "lng")
    private String logpoint;

    @ColumnInfo(name = "status")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLatpoint() {
        return latpoint;
    }

    public void setLatpoint(String latpoint) {
        this.latpoint = latpoint;
    }

    public String getLogpoint() {
        return logpoint;
    }

    public void setLogpoint(String logpoint) {
        this.logpoint = logpoint;
    }

}
