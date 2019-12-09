package com.nuveq.sojibdemo.datamodel.global.area;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AreaPost {

    @SerializedName("categoryid")
    @Expose
    private String categoryid;
    @SerializedName("marketingcode")
    @Expose
    private String marketingcode;

    public String getMarketingcode() {
        return marketingcode;
    }

    public void setMarketingcode(String marketingcode) {
        this.marketingcode = marketingcode;
    }

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }

}