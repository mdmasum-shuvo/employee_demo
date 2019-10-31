package com.nuveq.sojibdemo.datamodel.visitplan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddVisitPost {

@SerializedName("id")
@Expose
private String id;
@SerializedName("lat")
@Expose
private String lat;
@SerializedName("log")
@Expose
private String log;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getLat() {
return lat;
}

public void setLat(String lat) {
this.lat = lat;
}

public String getLog() {
return log;
}

public void setLog(String log) {
this.log = log;
}

}