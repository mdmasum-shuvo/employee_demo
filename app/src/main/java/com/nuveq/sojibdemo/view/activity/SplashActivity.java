package com.nuveq.sojibdemo.view.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.nuveq.sojibdemo.R;
import com.nuveq.sojibdemo.appdata.AppConstants;
import com.nuveq.sojibdemo.appdata.SharedPreferencesEnum;
import com.nuveq.sojibdemo.listener.ServerResponseFailedCallback;
import com.nuveq.sojibdemo.viewmodel.Viewmodel;

import java.util.UUID;

import static com.nuveq.sojibdemo.appdata.AppConstants.ANDROID_ID;

public class SplashActivity extends AppCompatActivity implements ServerResponseFailedCallback {
    private Viewmodel viewModel;
    private LinearLayout loadingView;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        loadingView = findViewById(R.id.loadingView);

        viewModel = ViewModelProviders.of(this).get(Viewmodel.class);
        viewModel.getRepository().setCallbackListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 2);
        } else {
            TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            // ANDROID_ID = tManager.getDeviceId();
            ANDROID_ID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    viewModel.getMacData(ANDROID_ID).observe(SplashActivity.this, data -> {
                        if (data != null) {
                            loadingView.setVisibility(View.GONE);
                            SharedPreferencesEnum.getInstance(SplashActivity.this).put(SharedPreferencesEnum.Key.PHONE_NUMBER, data);
                            startActivity(new Intent(SplashActivity.this, RegistrationActivity.class).putExtra(AppConstants.PHONE_NUMBER, data));
                            finish();
                        }
                    });
                }
            }, 2000);
        }
    }


    @Override
    public void onFailed(String msg) {
        loadingView.setVisibility(View.GONE);
        startActivity(new Intent(this, RegistrationActivity.class));
    }

}
