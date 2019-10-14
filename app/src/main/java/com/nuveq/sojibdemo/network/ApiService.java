package com.nuveq.sojibdemo.network;


import com.google.gson.JsonObject;
import com.nuveq.sojibdemo.datamodel.registration.ResponseData;

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


    @GET("Login")
    Call<Object> getLogin(@Body JsonObject object);
}


