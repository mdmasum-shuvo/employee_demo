package com.nuveq.sojibdemo.datamodel.visitplan;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VisitPlanResponse {

@SerializedName("status")
@Expose
private Boolean status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("plan")
@Expose
private List<Plan> plan = null;

public Boolean getStatus() {
return status;
}

public void setStatus(Boolean status) {
this.status = status;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public List<Plan> getPlan() {
return plan;
}

public void setPlan(List<Plan> plan) {
this.plan = plan;
}

}