package com.nuveq.sojibdemo.network;


import com.google.gson.JsonObject;
import com.nuveq.sojibdemo.datamodel.AuthenticationPost;
import com.nuveq.sojibdemo.datamodel.LoginResponse;
import com.nuveq.sojibdemo.datamodel.MacResponse;
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


    @POST("EmpInfoes")
    Call<Registration> register(@Body JsonObject object);


    @GET("BranchInfoes")
    Call<ArrayList<Registration>> getBranch();


    @POST("login")
    Call<LoginResponse> getLogin(@Body JsonObject object);

    @Headers("Content-Type: application/json")
    @POST("loginview")
    Call<String> getDataBytMac(@Body JsonObject data);

    @Headers("Content-Type: application/json")
    @POST("checkin")
    Call<String> postCheckIn(@Body JsonObject data);

    @Headers("Content-Type: application/json")
    @POST("checkout")
    Call<String> postCheckOut(@Body JsonObject data);


}


