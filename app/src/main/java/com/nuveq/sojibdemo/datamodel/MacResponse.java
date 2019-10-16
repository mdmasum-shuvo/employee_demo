package com.nuveq.sojibdemo.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MacResponse implements Serializable {

@SerializedName("status")
@Expose
private Boolean status;
@SerializedName("phoneNumber")
@Expose
private String phoneNumber;


public Boolean getStatus() {
return status;
}

public void setStatus(Boolean status) {
this.status = status;
}

public String getPhoneNumber() {
return phoneNumber;
}

public void setPhoneNumber(String phoneNumber) {
this.phoneNumber = phoneNumber;
}



}