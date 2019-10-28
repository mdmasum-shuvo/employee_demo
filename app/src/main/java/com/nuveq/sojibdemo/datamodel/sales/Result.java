package com.nuveq.sojibdemo.datamodel.sales;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("name")
@Expose
private String name;
@SerializedName("phone")
@Expose
private String phone;
@SerializedName("address")
@Expose
private String address;
@SerializedName("referBydr")
@Expose
private Integer referBydr;
@SerializedName("referByEmp")
@Expose
private Integer referByEmp;
@SerializedName("date")
@Expose
private String date;
@SerializedName("time")
@Expose
private String time;
@SerializedName("description")
@Expose
private String description;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

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

public String getAddress() {
return address;
}

public void setAddress(String address) {
this.address = address;
}

public Integer getReferBydr() {
return referBydr;
}

public void setReferBydr(Integer referBydr) {
this.referBydr = referBydr;
}

public Integer getReferByEmp() {
return referByEmp;
}

public void setReferByEmp(Integer referByEmp) {
this.referByEmp = referByEmp;
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