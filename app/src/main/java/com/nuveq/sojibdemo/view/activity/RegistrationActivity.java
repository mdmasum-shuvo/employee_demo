package com.nuveq.sojibdemo.view.activity;

import com.nuveq.sojibdemo.common.BaseActivity;
import com.nuveq.sojibdemo.R;
import com.nuveq.sojibdemo.databinding.ActivityRegistrationBinding;

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
        binding.btnLogin.btnLogin.setOnClickListener(view -> {
            startActvity(this, HomeActivity.class, true);
        });

    }
}