package com.nuveq.sojibdemo.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttendDatePost {

@SerializedName("empid")
@Expose
private String empid;
@SerializedName("fromdate")
@Expose
private String fromdate;
@SerializedName("todate")
@Expose
private String todate;

public String getEmpid() {
return empid;
}

public void setEmpid(String empid) {
this.empid = empid;
}

public String getFromdate() {
return fromdate;
}

public void setFromdate(String fromdate) {
this.fromdate = fromdate;
}

public String getTodate() {
return todate;
}

public void setTodate(String todate) {
this.todate = todate;
}

}