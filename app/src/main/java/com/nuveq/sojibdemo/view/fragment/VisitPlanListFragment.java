package com.nuveq.sojibdemo.view.fragment;

import android.text.TextUtils;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nuveq.sojibdemo.R;
import com.nuveq.sojibdemo.appdata.SharedPreferencesEnum;
import com.nuveq.sojibdemo.common.BaseFragment;
import com.nuveq.sojibdemo.databinding.FragmentPlanListBinding;
import com.nuveq.sojibdemo.datamodel.AttendDatePost;
import com.nuveq.sojibdemo.datamodel.visitplan.Plan;
import com.nuveq.sojibdemo.listener.ServerResponseFailedCallback;
import com.nuveq.sojibdemo.utils.CommonUtils;
import com.nuveq.sojibdemo.view.adapter.AttendanceAdapter;
import com.nuveq.sojibdemo.view.adapter.PlanListAdapter;
import com.nuveq.sojibdemo.viewmodel.Viewmodel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class VisitPlanListFragment extends BaseFragment implements ServerResponseFailedCallback {
    private FragmentPlanListBinding binding;
    private Viewmodel viewModel;
    private Calendar calendar;
    private List<Plan> planList = new ArrayList<>();
    PlanListAdapter adapter;

    @Override
    protected Integer layoutResourceId() {
        return R.layout.fragment_plan_list;
    }

    @Override
    protected void initFragmentComponents() {
        binding = (FragmentPlanListBinding) getBinding();
        binding.rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        viewModel = ViewModelProviders.of(this).get(Viewmodel.class);
        viewModel.getplanRespository().setCallbackListener(this);
        adapter = new PlanListAdapter(getActivity(), planList);
        binding.rv.setAdapter(adapter);
        calendar = Calendar.getInstance();

    }

    @Override
    protected void initFragmentFunctionality() {
        callApi("2019/10/1", CommonUtils.currentDate());
    }

    @Override
    protected void initFragmentListener() {
        binding.etDateFrom.setOnClickListener(v -> {
            CommonUtils.showDatePicker(getContext(), binding.etDateFrom, calendar);
        });
        binding.etDateTo.setOnClickListener(v -> {
            CommonUtils.showDatePicker(getContext(), binding.etDateTo, calendar);
        });


        binding.btnFilter.setOnClickListener(v -> {
            if ((!TextUtils.isEmpty(binding.etDateFrom.getText())) && ((!TextUtils.isEmpty(binding.etDateTo.getText())))) {
                callApi(binding.etDateFrom.getText().toString(), binding.etDateTo.getText().toString());
            } else {
                showToast("please select both dates");
            }

        });
    }

    private void callApi(String from, String to) {
        if (!planList.isEmpty()) {
            planList.clear();
        }

        AttendDatePost post = new AttendDatePost();
        post.setEmpid(String.valueOf(16));
        post.setFromdate(from);
        post.setTodate(to);
        showProgressDialog();
        viewModel.getVisitPlanDataList(post).observe(this, data -> {
            if (data != null) {
                hideProgressDialog();
                planList.addAll(data);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onFailed(String msg) {
        hideProgressDialog();
        adapter.notifyDataSetChanged();
        CommonUtils.showCustomAlert(getActivity(), "Failed", msg, false);
    }
}
