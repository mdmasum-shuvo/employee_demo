package com.nuveq.sojibdemo.feature.admin.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

@SerializedName("time")
@Expose
private String time;
@SerializedName("latitude")
@Expose
private Double latitude;
@SerializedName("longitude")
@Expose
private Double longitude;

public String getTime() {
return time;
}

public void setTime(String time) {
this.time = time;
}

public Double getLatitude() {
return latitude;
}

public void setLatitude(Double latitude) {
this.latitude = latitude;
}

public Double getLongitude() {
return longitude;
}

public void setLongitude(Double longitude) {
this.longitude = longitude;
}

}