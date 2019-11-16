package com.nuveq.sojibdemo.view.activity;

import com.nuveq.sojibdemo.R;
import com.nuveq.sojibdemo.appdata.room.RoomDataRepository;
import com.nuveq.sojibdemo.common.BaseActivity;
import com.nuveq.sojibdemo.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;
    private RoomDataRepository mRepo;

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
        mRepo = RoomDataRepository.getInstance(this);

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
