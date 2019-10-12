package com.nuveq.sojibdemo;


import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Contains all API call declarations
 */
public interface ApiService {


    @POST("EmpInfoes")
    Call<ResponseData> register(@Body JsonObject object);


    @GET("BranchInfoes")
    Call<ArrayList<ResponseData>> getBranch();
}


