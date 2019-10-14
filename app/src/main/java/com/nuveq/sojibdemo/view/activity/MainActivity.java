package com.nuveq.sojibdemo.view.activity;

import android.content.Context;
import android.view.View;
import android.widget.SearchView;


import com.nuveq.sojibdemo.R;
import com.nuveq.sojibdemo.common.BaseActivity;
import com.nuveq.sojibdemo.databinding.ActivityMainBinding;
import com.nuveq.sojibdemo.listener.OnItemClickListener;

import java.util.ArrayList;

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
        homeFragment();
    }

    @Override
    protected void initFunctionality() {
        //  binding.recyclerView.setAdapter(adapter);

    }

    @Override
    protected void initListener() {




    }




}
