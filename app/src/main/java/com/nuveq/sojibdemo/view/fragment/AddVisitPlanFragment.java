package com.nuveq.sojibdemo.view.fragment;

import android.widget.Toast;

import com.nuveq.sojibdemo.R;
import com.nuveq.sojibdemo.common.BaseFragment;
import com.nuveq.sojibdemo.databinding.FragmentPlanListBinding;
import com.nuveq.sojibdemo.utils.CommonUtils;

import java.util.Calendar;

public class AddVisitPlanFragment extends BaseFragment {

    FragmentPlanListBinding binding;
    Calendar calendar;

    @Override
    protected Integer layoutResourceId() {
        return R.layout.fragment_plan_list;
    }

    @Override
    protected void initFragmentComponents() {
        binding = (FragmentPlanListBinding) getBinding();
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
        binding.tvTime.setOnClickListener(v -> {
            CommonUtils.showTimePicker(getActivity(), binding.tvTime, calendar);
        });
        binding.btnSave.setOnClickListener(v -> {
            toast("feature is on test");
        });
    }
}
