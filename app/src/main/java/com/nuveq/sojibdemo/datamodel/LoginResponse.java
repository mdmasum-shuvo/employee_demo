package com.nuveq.sojibdemo.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginResponse implements Serializable {

@SerializedName("status")
@Expose
private Boolean status;
@SerializedName("empId")
@Expose
private Integer empId;
@SerializedName("name")
@Expose
private String name;
@SerializedName("macAddress")
@Expose
private String macAddress;
@SerializedName("picture")
@Expose
private String picture;

public Boolean getStatus() {
return status;
}

public void setStatus(Boolean status) {
this.status = status;
}

public Integer getEmpId() {
return empId;
}

public void setEmpId(Integer empId) {
this.empId = empId;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getMacAddress() {
return macAddress;
}

public void setMacAddress(String macAddress) {
this.macAddress = macAddress;
}

public String getPicture() {
return picture;
}

public void setPicture(String picture) {
this.picture = picture;
}

}