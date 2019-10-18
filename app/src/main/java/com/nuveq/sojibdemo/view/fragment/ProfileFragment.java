package com.nuveq.sojibdemo.view.fragment;

import android.os.Bundle;

import com.nuveq.sojibdemo.R;
import com.nuveq.sojibdemo.appdata.AppConstants;
import com.nuveq.sojibdemo.common.BaseFragment;
import com.nuveq.sojibdemo.databinding.FragmentProfileBinding;
import com.nuveq.sojibdemo.datamodel.LoginResponse;

public class ProfileFragment extends BaseFragment {

    FragmentProfileBinding binding;

    @Override
    protected Integer layoutResourceId() {
        return R.layout.fragment_profile;
    }

    @Override
    protected void initFragmentComponents() {
        binding = (FragmentProfileBinding) getBinding();

        if (getArguments() != null) {
            Bundle bundle = getArguments();
            if (bundle != null) {
                LoginResponse data = (LoginResponse) bundle.getSerializable(AppConstants.INTENT_KEY);
                try {
                    binding.name.setText(data.getName());
                    binding.bloodGroup.setText("" + data.getEmpId());
                } catch (Exception e) {
                }

            }
        }

    }

    @Override
    protected void initFragmentFunctionality() {

    }

    @Override
    protected void initFragmentListener() {

    }
}
