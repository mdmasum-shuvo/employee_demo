package com.nuveq.sojibdemo.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nuveq.sojibdemo.R;
import com.nuveq.sojibdemo.appdata.AppConstants;
import com.nuveq.sojibdemo.appdata.SharedPreferencesEnum;
import com.nuveq.sojibdemo.common.BaseActivity;
import com.nuveq.sojibdemo.listener.ServerResponseFailedCallback;

import com.nuveq.sojibdemo.utils.maputils.GPSTracker;
import com.nuveq.sojibdemo.viewmodel.Viewmodel;


import static com.nuveq.sojibdemo.appdata.AppConstants.ANDROID_ID;

public class SplashActivity extends BaseActivity implements ServerResponseFailedCallback {
    private Viewmodel viewModel;
    GPSTracker gpsTracker;

    @Override
    protected int getLayoutResourceFile() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initComponent() {
        viewModel = ViewModelProviders.of(this).get(Viewmodel.class);
        viewModel.getRepository().setCallbackListener(this);

        initLoader();
        showLoader();
        ANDROID_ID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                viewModel.getMacData(ANDROID_ID).observe(SplashActivity.this, data -> {
                    if (data != null) {
                        SharedPreferencesEnum.getInstance(SplashActivity.this).put(SharedPreferencesEnum.Key.PHONE_NUMBER, data);
                        startActivity(new Intent(SplashActivity.this, RegistrationActivity.class).putExtra(AppConstants.PHONE_NUMBER, data));
                        finish();
                    }
                });
            }
        }, 2000);
    }

    @Override
    protected void initFunctionality() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onFailed(String msg) {
        startActivity(new Intent(this, RegistrationActivity.class));
    }

}
