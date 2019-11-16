package com.nuveq.sojibdemo.feature.admin;

import com.nuveq.sojibdemo.R;
import com.nuveq.sojibdemo.common.BaseFragment;
import com.nuveq.sojibdemo.databinding.FragmentAdminEmployeeBinding;

public class EmployeeFragment extends BaseFragment {

    private FragmentAdminEmployeeBinding binding;

    @Override
    protected Integer layoutResourceId() {
        return R.layout.fragment_admin_employee;
    }

    @Override
    protected void initFragmentComponents() {
        binding = (FragmentAdminEmployeeBinding) getBinding();

    }

    @Override
    protected void initFragmentFunctionality() {

    }

    @Override
    protected void initFragmentListener() {

    }
}
