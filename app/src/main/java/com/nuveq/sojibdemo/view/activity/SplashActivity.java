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
import com.nuveq.sojibdemo.datamodel.AuthenticationPost;
import com.nuveq.sojibdemo.datamodel.MacResponse;
import com.nuveq.sojibdemo.datamodel.registration.Registration;
import com.nuveq.sojibdemo.listener.NetworkConnectivityListener;
import com.nuveq.sojibdemo.listener.ServerResponseFailedCallback;
import com.nuveq.sojibdemo.network.HTTP_PARAM;
import com.nuveq.sojibdemo.receiver.NetworkChangeReceiver;
import com.nuveq.sojibdemo.utils.PermissionUtils;
import com.nuveq.sojibdemo.utils.maputils.GPSTracker;
import com.nuveq.sojibdemo.viewmodel.Viewmodel;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nuveq.sojibdemo.appdata.AppConstants.ANDROID_ID;

public class SplashActivity extends BaseActivity implements ServerResponseFailedCallback {
    private Viewmodel viewModel;
    GPSTracker gpsTracker;
    private NetworkChangeReceiver mNetworkReceiver;

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

/*    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PermissionUtils.REQUEST_LOCATION: {
                if (PermissionUtils.isPermissionResultGranted(grantResults)) {

                } else {

                    Toast.makeText(this, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mNetworkReceiver = new NetworkChangeReceiver();
        registerNetworkBroadcastForNougat();
        mNetworkReceiver.setNetworkChangeListener(new NetworkConnectivityListener() {
            @Override
            public void onConnect(boolean isConnect) {
                if (isConnect) {
                    gpsTracker = new GPSTracker(SplashActivity.this);
                    if (gpsTracker.canGetLocation()) {
                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {

                            }
                        }, 1500);
                    } else {
                        gpsTracker.showSettingsAlert();
                    }
                } else {
                }
            }
        });
    }

    private void registerNetworkBroadcastForNougat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    protected void unregisterNetworkChanges() {
        try {
            unregisterReceiver(mNetworkReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterNetworkChanges();
    }*/
}
