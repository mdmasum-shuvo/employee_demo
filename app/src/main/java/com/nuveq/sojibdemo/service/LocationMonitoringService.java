package com.nuveq.sojibdemo.service;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.nuveq.sojibdemo.appdata.AppConstants;
import com.nuveq.sojibdemo.appdata.room.RoomDataRepository;
import java.util.Timer;
import java.util.TimerTask;



public class LocationMonitoringService extends Service implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    Gson gson = new Gson();
    private static final String TAG = LocationMonitoringService.class.getSimpleName();
    GoogleApiClient mLocationClient;
    LocationRequest mLocationRequest = new LocationRequest();

    private MyRoomDataInsertTask localInsertTask;
    private RoomDataRetriveSaveToServerTask toServerTask;
    private RoomDataRepository mRepo;
    public static final String EXTRA_LATITUDE = "extra_latitude";
    public static final String EXTRA_LONGITUDE = "extra_longitude";
    public static final long NOTIFY_INTERVAL = (5 * 1000);// 10 seconds
    // run on another Thread to avoid crash
    private Handler mHandler = new Handler();
    // timer handling
    private Timer mTimer;
    private TimerTask mTimerTask;
    int count = 0;
    int i = 0;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mLocationClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mLocationRequest.setInterval(AppConstants.FASTEST_LOCATION_INTERVAL);
        mLocationRequest.setFastestInterval(AppConstants.FASTEST_LOCATION_INTERVAL);
        mRepo = RoomDataRepository.getInstance();

        int priority = LocationRequest.PRIORITY_HIGH_ACCURACY; //by default
        //PRIORITY_BALANCED_POWER_ACCURACY, PRIORITY_LOW_POWER, PRIORITY_NO_POWER are the other priority modes


        mLocationRequest.setPriority(priority);
        mLocationClient.connect();
        //Make it stick to the notification panel so it is less prone to get cancelled by the Operating System.
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /*
     * LOCATION CALLBACKS
     */
    @Override
    public void onConnected(Bundle dataBundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            Log.d(TAG, "== Error On onConnected() Permission not granted");
            //Permission not granted by user so cancel the further execution.

            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mLocationClient, mLocationRequest, (com.google.android.gms.location.LocationListener) this);

        Log.d(TAG, "Connected to Google API");
    }

    /*
     * Called by Location Services if the connection to the
     * location client drops because of an error.
     */
    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "Connection suspended");
    }

    //to get the location change
    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "Location changed");


        if (location != null) {
            Log.d(TAG, "== location != null");
            // Toast.makeText(this, String.valueOf(location.getLatitude()) + " \n" + String.valueOf(location.getLongitude()), Toast.LENGTH_SHORT).show();
            //Send result to activities

     /*       if (CommonUtils.isNetworkAvailable()) {
                TrackingPost post = new TrackingPost();
                post.setDate(CommonUtils.currentDate());
                post.setEmpid(String.valueOf(SharedPreferencesEnum.getInstance(this).getInt(SharedPreferencesEnum.Key.USER_ID)));
                post.setLatpoint("" + location.getLatitude());
                post.setLogpoint("" + location.getLongitude());
                post.setTime(CommonUtils.currentTime());
                String jsonString = gson.toJson(post);
                JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();

                callApi(jsonObject);

            } else {

                com.nuveq.sojibdemo.appdata.room.TrackingPost post1 = new com.nuveq.sojibdemo.appdata.room.TrackingPost();
                post1.setDate(CommonUtils.currentDate());
                post1.setEmpid(String.valueOf(SharedPreferencesEnum.getInstance(this).getInt(SharedPreferencesEnum.Key.USER_ID)));
                post1.setLatpoint("" + location.getLatitude());
                post1.setLogpoint("" + location.getLongitude());
                post1.setTime(CommonUtils.currentTime());
                post1.setStatus("0");
                localInsertTask = new MyRoomDataInsertTask(mRepo);
                localInsertTask.execute(post1);
            }
*/
            //sendMessageToUI(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));

        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    private void callApi(JsonObject jsonObject) {
    }

    private void insertDataToServer() {
        toServerTask = new RoomDataRetriveSaveToServerTask(mRepo, this);
        toServerTask.execute();
    }

    @Override
    public void onDestroy() {
        if (mLocationClient.isConnected())
            mLocationClient.disconnect();

        if (localInsertTask != null) {
            localInsertTask.cancel(true);

        }
        if (toServerTask != null) {
            toServerTask.cancel(true);
            toServerTask.destroyTask();
        }
        super.onDestroy();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }
}