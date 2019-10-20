package com.nuveq.sojibdemo.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckOutPost {

@SerializedName("empid")
@Expose
private String empid;
@SerializedName("date")
@Expose
private String date;
@SerializedName("checkouttime")
@Expose
private String checkouttime;
@SerializedName("checkoutlocation")
@Expose
private String checkoutlocation;

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

public String getCheckouttime() {
return checkouttime;
}

public void setCheckouttime(String checkouttime) {
this.checkouttime = checkouttime;
}

public String getCheckoutlocation() {
return checkoutlocation;
}

public void setCheckoutlocation(String checkoutlocation) {
this.checkoutlocation = checkoutlocation;
}

}