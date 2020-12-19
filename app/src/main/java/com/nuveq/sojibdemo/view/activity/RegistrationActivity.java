package com.nuveq.sojibdemo.view.activity;

import androidx.lifecycle.ViewModelProviders;

import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.nuveq.sojibdemo.appdata.AppConstants;
import com.nuveq.sojibdemo.appdata.SharedPreferencesEnum;
import com.nuveq.sojibdemo.common.BaseActivity;
import com.nuveq.sojibdemo.datamodel.AuthenticationPost;
import com.nuveq.sojibdemo.listener.ServerResponseFailedCallback;
import com.nuveq.sojibdemo.network.ApiService;
import com.nuveq.sojibdemo.datamodel.registration.Data;
import com.nuveq.sojibdemo.utils.CommonUtils;
import com.nuveq.sojibdemo.utils.PermissionUtils;
import com.nuveq.sojibdemo.R;
import com.nuveq.sojibdemo.network.RestClient;
import com.nuveq.sojibdemo.databinding.ActivityRegistrationBinding;
import com.nuveq.sojibdemo.utils.maputils.GPSTracker;
import com.nuveq.sojibdemo.viewmodel.Viewmodel;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import jrizani.jrspinner.JRSpinner;

public class RegistrationActivity extends BaseActivity {
    ActivityRegistrationBinding binding;

    @Override
    protected int getLayoutResourceFile() {
        return R.layout.activity_registration;
    }

    @Override
    protected void initComponent() {

        binding = (ActivityRegistrationBinding) getBinding();
    }

    @Override
    protected void initFunctionality() {

    }

    @Override
    protected void initListener() {
        binding.btnLogin.setOnClickListener(view -> {
            startActvity(this, MainActivity.class, true);
        });

    }
}