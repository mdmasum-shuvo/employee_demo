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

import java.util.UUID;

import static com.nuveq.sojibdemo.appdata.AppConstants.ANDROID_ID;

public class SplashActivity extends AppCompatActivity {
    private LinearLayout loadingView;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingView = findViewById(R.id.loadingView);


                loadingView.setVisibility(View.GONE);
                startActivity(new Intent(SplashActivity.this, RegistrationActivity.class));
                finish();

            }
        },3000);


    }


}
