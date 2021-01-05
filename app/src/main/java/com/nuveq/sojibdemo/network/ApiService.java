package com.nuveq.sojibdemo.network;


import com.google.gson.JsonObject;

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

    @POST(HTTP_PARAM.CHECK_IN)
    Call<String> postCheckIn(@Body JsonObject data);

    @Headers("Content-Type: application/json")
    @POST(HTTP_PARAM.CHECK_OUT)
    Call<String> postCheckOut(@Body JsonObject data);


    @Headers("Content-Type: application/json")
    @POST(HTTP_PARAM.TACKING)
    Call<String> postTracking(@Body JsonObject data);

    @POST(HTTP_PARAM.ADD_VISIT)
    Call<String> visitPlanPost(@Body JsonObject object);




}


