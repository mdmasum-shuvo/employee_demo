package com.nuveq.sojibdemo.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrackingPost {

@SerializedName("empid")
@Expose
private String empid;
@SerializedName("date")
@Expose
private String date;
@SerializedName("time")
@Expose
private String time;
@SerializedName("latpoint")
@Expose
private String latpoint;
@SerializedName("logpoint")
@Expose
private String logpoint;

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
