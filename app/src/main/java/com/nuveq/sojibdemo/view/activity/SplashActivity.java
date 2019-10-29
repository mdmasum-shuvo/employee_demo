package com.nuveq.sojibdemo.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;
import com.nuveq.sojibdemo.R;
import com.nuveq.sojibdemo.appdata.AppConstants;
import com.nuveq.sojibdemo.appdata.SharedPreferencesEnum;
import com.nuveq.sojibdemo.listener.ServerResponseFailedCallback;
import com.nuveq.sojibdemo.viewmodel.Viewmodel;
import static com.nuveq.sojibdemo.appdata.AppConstants.ANDROID_ID;

public class SplashActivity extends AppCompatActivity implements ServerResponseFailedCallback {
    private Viewmodel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);


        viewModel = ViewModelProviders.of(this).get(Viewmodel.class);
        viewModel.getRepository().setCallbackListener(this);

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
    public void onFailed(String msg) {
        startActivity(new Intent(this, RegistrationActivity.class));
    }

}
