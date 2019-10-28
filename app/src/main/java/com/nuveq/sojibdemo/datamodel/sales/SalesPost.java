package com.nuveq.sojibdemo.datamodel.sales;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SalesPost {

@SerializedName("name")
@Expose
private String name;
@SerializedName("phone")
@Expose
private String phone;
@SerializedName("referbydr")
@Expose
private String referbydr;
@SerializedName("referbyemp")
@Expose
private String referbyemp;
@SerializedName("address")
@Expose
private String address;
@SerializedName("date")
@Expose
private String date;
@SerializedName("time")
@Expose
private String time;
@SerializedName("Description")
@Expose
private String description;

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getPhone() {
return phone;
}

public void setPhone(String phone) {
this.phone = phone;
}

public String getReferbydr() {
return referbydr;
}

public void setReferbydr(String referbydr) {
this.referbydr = referbydr;
}

public String getReferbyemp() {
return referbyemp;
}

public void setReferbyemp(String referbyemp) {
this.referbyemp = referbyemp;
}

public String getAddress() {
return address;
}

public void setAddress(String address) {
this.address = address;
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

public String getDescription() {
return description;
}

public void setDescription(String description) {
this.description = description;
}

}