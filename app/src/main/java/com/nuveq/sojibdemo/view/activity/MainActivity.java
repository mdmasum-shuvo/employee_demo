package com.nuveq.sojibdemo.view.activity;

import android.os.Bundle;

import com.nuveq.sojibdemo.R;
import com.nuveq.sojibdemo.appdata.AppConstants;
import com.nuveq.sojibdemo.common.BaseActivity;
import com.nuveq.sojibdemo.databinding.ActivityMainBinding;
import com.nuveq.sojibdemo.datamodel.LoginResponse;
import com.nuveq.sojibdemo.listener.OnItemClickListener;
import com.nuveq.sojibdemo.view.fragment.ProfileFragment;

public class MainActivity extends BaseActivity {

    ActivityMainBinding binding;
    OnItemClickListener onItemClickListener;

    @Override
    protected int getLayoutResourceFile() {
        // for view
        return R.layout.activity_main;
    }

    @Override
    protected void initComponent() {
        binding = (ActivityMainBinding) getBinding();
        initToolbar();
        initDrawer();
        loadHomeFragment();

        binding.Toolbar.logout.setOnClickListener(view -> {
            finish();
        });
    }

    @Override
    protected void initFunctionality() {
        //binding.recyclerView.setAdapter(adapter);
        locationBroadcast();
    }

    @Override
    protected void initListener() {

    }


}
