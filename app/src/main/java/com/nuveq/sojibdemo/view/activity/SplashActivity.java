package com.nuveq.sojibdemo.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nuveq.sojibdemo.R;
import com.nuveq.sojibdemo.appdata.AppConstants;
import com.nuveq.sojibdemo.appdata.SharedPreferencesEnum;
import com.nuveq.sojibdemo.common.BaseActivity;
import com.nuveq.sojibdemo.datamodel.AuthenticationPost;
import com.nuveq.sojibdemo.datamodel.MacResponse;
import com.nuveq.sojibdemo.datamodel.registration.Registration;
import com.nuveq.sojibdemo.listener.ServerResponseFailedCallback;
import com.nuveq.sojibdemo.network.HTTP_PARAM;
import com.nuveq.sojibdemo.viewmodel.Viewmodel;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends BaseActivity implements ServerResponseFailedCallback {
    private Viewmodel viewModel;


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
        final String androidID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        viewModel.getMacData(androidID).observe(this, data -> {
            SharedPreferencesEnum.getInstance(this).put(SharedPreferencesEnum.Key.PHONE_NUMBER, data.getPhoneNumber());
            startActivity(new Intent(this, RegistrationActivity.class).putExtra(AppConstants.PHONE_NUMBER, data.getPhoneNumber()));
        });
   /*     new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActvity(SplashActivity.this, RegistrationActivity.class, true);

            }
        }, 2000);*/
    }

    @Override
    protected void initFunctionality() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onFailed(String msg) {
        startActivity(new Intent(this, RegistrationActivity.class));

    }
}
