package com.nuveq.sojibdemo.view.fragment;

import com.nuveq.sojibdemo.R;
import com.nuveq.sojibdemo.common.BaseFragment;
import com.nuveq.sojibdemo.databinding.FragmentAddSalesBinding;
import com.nuveq.sojibdemo.utils.CommonUtils;

import java.util.Calendar;

public class AddSalesFragment extends BaseFragment {
    FragmentAddSalesBinding binding;
    Calendar calendar;

    @Override
    protected Integer layoutResourceId() {
        return R.layout.fragment_add_sales;
    }

    @Override
    protected void initFragmentComponents() {
        binding = (FragmentAddSalesBinding) getBinding();
        calendar = Calendar.getInstance();
    }

    @Override
    protected void initFragmentFunctionality() {

    }

    @Override
    protected void initFragmentListener() {
        binding.tvDate.setOnClickListener(v -> {
            CommonUtils.showDatePicker(getActivity(), binding.tvDate, calendar);
        });
    }
}
