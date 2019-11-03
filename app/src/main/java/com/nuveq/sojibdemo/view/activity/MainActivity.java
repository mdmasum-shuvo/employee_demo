package com.nuveq.sojibdemo.view.activity;

import com.nuveq.sojibdemo.R;
import com.nuveq.sojibdemo.common.BaseActivity;
import com.nuveq.sojibdemo.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {

    ActivityMainBinding binding;

    @Override
    protected int getLayoutResourceFile() {
        return R.layout.activity_main;
    }

    @Override
    protected void initComponent() {
        binding = (ActivityMainBinding) getBinding();
        initToolbar();
        initDrawer();
        loadHomeFragment();

        binding.Toolbar.logout.setOnClickListener(view -> {
            logout();
        });
    }

    @Override
    protected void initFunctionality() {

    }

    @Override
    protected void initListener() {

    }


}
