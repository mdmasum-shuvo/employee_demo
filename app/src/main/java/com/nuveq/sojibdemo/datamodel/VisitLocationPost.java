package com.nuveq.sojibdemo.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VisitLocationPost {

@SerializedName("id")
@Expose
private String id;
@SerializedName("VisitLatPoint")
@Expose
private String visitLatPoint;
@SerializedName("VisitLogPoint")
@Expose
private String visitLogPoint;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getVisitLatPoint() {
return visitLatPoint;
}

public void setVisitLatPoint(String visitLatPoint) {
this.visitLatPoint = visitLatPoint;
}

public String getVisitLogPoint() {
return visitLogPoint;
}

public void setVisitLogPoint(String visitLogPoint) {
this.visitLogPoint = visitLogPoint;
}

}