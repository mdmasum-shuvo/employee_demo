package com.nuveq.sojibdemo.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttendancePost {

    @SerializedName("shift")
    @Expose
    private String shift;


    @SerializedName("empid")
    @Expose
    private String empid;

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("checkintime")
    @Expose
    private String checkintime;
    @SerializedName("checkinlocation")
    @Expose
    private String checkinlocation;

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

    public String getCheckintime() {
        return checkintime;
    }

    public void setCheckintime(String checkintime) {
        this.checkintime = checkintime;
    }

    public String getCheckinlocation() {
        return checkinlocation;
    }

    public void setCheckinlocation(String checkinlocation) {
        this.checkinlocation = checkinlocation;
    }


    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }
}