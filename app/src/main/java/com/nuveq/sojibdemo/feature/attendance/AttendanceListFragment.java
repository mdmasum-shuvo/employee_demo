package com.nuveq.sojibdemo.feature.attendance;

import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nuveq.sojibdemo.R;
import com.nuveq.sojibdemo.appdata.SharedPreferencesEnum;
import com.nuveq.sojibdemo.common.BaseFragment;
import com.nuveq.sojibdemo.databinding.FragmentAttendanceListBinding;
import com.nuveq.sojibdemo.datamodel.AttendDatePost;
import com.nuveq.sojibdemo.datamodel.attendance.Emp;
import com.nuveq.sojibdemo.feature.admin.datamodel.attendance.Result;
import com.nuveq.sojibdemo.listener.ServerResponseFailedCallback;
import com.nuveq.sojibdemo.utils.CommonUtils;
import com.nuveq.sojibdemo.view.adapter.AttendanceAdapter;
import com.nuveq.sojibdemo.viewmodel.Viewmodel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AttendanceListFragment extends BaseFragment implements ServerResponseFailedCallback {
    FragmentAttendanceListBinding binding;

    private Viewmodel viewModel;
    private Calendar calendar;
    private List<Result> empList = new ArrayList<>();
    private AttendanceAdapter adapter;

    @Override

    protected Integer layoutResourceId() {
        return R.layout.fragment_attendance_list;
    }

    @Override
    protected void initFragmentComponents() {
        binding = (FragmentAttendanceListBinding) getBinding();
        viewModel = ViewModelProviders.of(getActivity()).get(Viewmodel.class);
        viewModel.getAttendanceRepository().setCallbackListener(this);
        binding.rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new AttendanceAdapter(getActivity(), empList);
        binding.rv.setAdapter(adapter);
        binding.checkContainer.setVisibility(View.GONE);
        binding.containerAttendList.setVisibility(View.VISIBLE);
        calendar = Calendar.getInstance();

    }

    @Override
    protected void initFragmentFunctionality() {
        callApi("2019/12/1", CommonUtils.currentDate());
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

        if (!empList.isEmpty()) {
            empList.clear();
        }
        AttendDatePost post = new AttendDatePost();
        post.setEmpid(String.valueOf(SharedPreferencesEnum.getInstance(getActivity()).getInt(SharedPreferencesEnum.Key.USER_ID)));
        post.setFromdate(from);
        post.setTodate(to);
        showProgressDialog();
        viewModel.getAttenDataList(post).observe(this, data -> {
            if (data != null) {
                hideProgressDialog();
                empList.addAll(data);
                adapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onFailed(String msg) {
        hideProgressDialog();
        adapter.notifyDataSetChanged();
        CommonUtils.showCustomAlert(getActivity(), "Failed", msg, false);
    }
}
