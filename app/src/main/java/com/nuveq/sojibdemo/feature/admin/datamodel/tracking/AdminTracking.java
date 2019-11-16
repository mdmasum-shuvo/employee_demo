package com.nuveq.sojibdemo.feature.admin.datamodel.tracking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdminTracking {

@SerializedName("empid")
@Expose
private String empid;
@SerializedName("date")
@Expose
private String date;

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

}