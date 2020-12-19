package com.nuveq.sojibdemo.view.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;

import com.nuveq.sojibdemo.R;
import com.nuveq.sojibdemo.appdata.AppConstants;
import com.nuveq.sojibdemo.common.BaseFragment;
import com.nuveq.sojibdemo.databinding.FragmentProfileBinding;
import com.nuveq.sojibdemo.datamodel.login.LoginResponse;
import com.nuveq.sojibdemo.datamodel.VisitLocationPost;
import com.nuveq.sojibdemo.datamodel.login.Result;
import com.nuveq.sojibdemo.listener.ServerResponseFailedCallback;
import com.nuveq.sojibdemo.utils.CommonUtils;
import com.nuveq.sojibdemo.viewmodel.Viewmodel;

import jrizani.jrspinner.JRSpinner;

public class ProfileFragment extends BaseFragment {

    @Override
    protected Integer layoutResourceId() {
        return R.layout.fragment_profile;
    }

    @Override
    protected void initFragmentComponents() {

    }

    @Override
    protected void initFragmentFunctionality() {

    }

    @Override
    protected void initFragmentListener() {

    }
}