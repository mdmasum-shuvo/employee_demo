package com.nuveq.sojibdemo.datamodel.registration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("macAddress")
    @Expose
    private String macAddress;

    @SerializedName("marketingcode")
    @Expose
    private String marketingcode;

    @SerializedName("branchId")
    @Expose
    private Integer branchId;

    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("roleId")
    @Expose
    private Integer roleId;
    @SerializedName("createDate")
    @Expose
    private String createDate;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getMarketingcode() {
        return marketingcode;
    }

    public void setMarketingcode(String marketingcode) {
        this.marketingcode = marketingcode;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

}