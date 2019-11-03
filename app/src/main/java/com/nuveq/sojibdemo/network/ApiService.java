package com.nuveq.sojibdemo.network;


import com.google.gson.JsonObject;
import com.nuveq.sojibdemo.datamodel.LoginResponse;
import com.nuveq.sojibdemo.datamodel.ServerResponse;
import com.nuveq.sojibdemo.datamodel.attendance.AttendanceDataResponse;
import com.nuveq.sojibdemo.datamodel.global.branch.BranchResponse;
import com.nuveq.sojibdemo.datamodel.global.area.AreaResponse;
import com.nuveq.sojibdemo.datamodel.global.cat.CatResponse;
import com.nuveq.sojibdemo.datamodel.sales.SalesResponse;
import com.nuveq.sojibdemo.datamodel.visitplan.VisitPlanResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Contains all API call declarations
 */
public interface ApiService {


    @POST(HTTP_PARAM.REGISTRATION)
    Call<String> register(@Body JsonObject object);


    @POST(HTTP_PARAM.LOGIN)
    Call<LoginResponse> getLogin(@Body JsonObject object);

    @Headers("Content-Type: application/json")
    @POST(HTTP_PARAM.USER)
    Call<String> getDataBytMac(@Body JsonObject data);

    @POST(HTTP_PARAM.CHECK_IN)
    Call<String> postCheckIn(@Body JsonObject data);

    @Headers("Content-Type: application/json")
    @POST(HTTP_PARAM.CHECK_OUT)
    Call<String> postCheckOut(@Body JsonObject data);


    @Headers("Content-Type: application/json")
    @POST(HTTP_PARAM.TACKING)
    Call<String> postTracking(@Body JsonObject data);

    @POST(HTTP_PARAM.ATTENDANCE_INFO)
    Call<AttendanceDataResponse> getAttendanceData(@Body JsonObject jsonObject);

    @POST(HTTP_PARAM.ADD_VISIT)
    Call<String> visitPlanPost(@Body JsonObject object);


    //Global
    @GET(HTTP_PARAM.BRANCH)
    Call<BranchResponse> getBranch();

    @GET(HTTP_PARAM.CATEGORY)
    Call<CatResponse> getCategoryData();

    @POST(HTTP_PARAM.AREA)
    Call<AreaResponse> getAreaData(@Body JsonObject object);

    @GET(HTTP_PARAM.DOCTOR)
    Call<AreaResponse> getDoctor();

    //plan data
    @POST(HTTP_PARAM.PENDING_PLAN_DATA)
    Call<VisitPlanResponse> getPendingPlanData(@Body JsonObject object);

    @POST(HTTP_PARAM.APPROVED_PLAN_LIST)
    Call<VisitPlanResponse> getApprovedPlanList(@Body JsonObject object);

    @POST(HTTP_PARAM.VISITED_PLAN_LIST)
    Call<VisitPlanResponse> getVisitedPlanList(@Body JsonObject object);


    @POST(HTTP_PARAM.VISIT_ENTRY)
    Call<ServerResponse> addVisit(@Body JsonObject object);

    @POST(HTTP_PARAM.VISIT_AREA_ENTRY)
    Call<String> locationPost(@Body JsonObject jsonObject);


    @POST(HTTP_PARAM.SALES_ENTRY)
    Call<String> salePost(@Body JsonObject object);

    @POST(HTTP_PARAM.SALES_DATA)
    Call<SalesResponse> getSalesData(@Body JsonObject object);

    @GET(HTTP_PARAM.SHIFT_LIST)
    Call<AreaResponse> getShiftList();



}


