package com.nuveq.sojibdemo.network;


import com.google.gson.JsonObject;
import com.nuveq.sojibdemo.datamodel.AuthenticationPost;
import com.nuveq.sojibdemo.datamodel.LoginResponse;
import com.nuveq.sojibdemo.datamodel.MacResponse;
import com.nuveq.sojibdemo.datamodel.VisitPlanDataPost;
import com.nuveq.sojibdemo.datamodel.attendance.AttendanceDataResponse;
import com.nuveq.sojibdemo.datamodel.global.CategoryDatum;
import com.nuveq.sojibdemo.datamodel.global.area.AreaResponse;
import com.nuveq.sojibdemo.datamodel.global.area.Emp;
import com.nuveq.sojibdemo.datamodel.registration.Registration;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Contains all API call declarations
 */
public interface ApiService {


    @POST(HTTP_PARAM.REGISTRATION)
    Call<Registration> register(@Body JsonObject object);


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
    Call<ArrayList<Registration>> getBranch();

    @GET(HTTP_PARAM.CATEGORY)
    Call<ArrayList<CategoryDatum>> getCategoryData();

    @POST(HTTP_PARAM.AREA)
    Call<AreaResponse> getAreaData(@Body JsonObject object);


}


