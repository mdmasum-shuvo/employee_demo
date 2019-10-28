package com.nuveq.sojibdemo.view.fragment;

import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nuveq.sojibdemo.R;
import com.nuveq.sojibdemo.appdata.SharedPreferencesEnum;
import com.nuveq.sojibdemo.common.BaseFragment;
import com.nuveq.sojibdemo.databinding.FragmentSalesListBinding;
import com.nuveq.sojibdemo.datamodel.AttendDatePost;
import com.nuveq.sojibdemo.datamodel.sales.Result;
import com.nuveq.sojibdemo.listener.ServerResponseFailedCallback;
import com.nuveq.sojibdemo.utils.CommonUtils;
import com.nuveq.sojibdemo.view.adapter.AttendanceAdapter;
import com.nuveq.sojibdemo.view.adapter.PlanListAdapter;
import com.nuveq.sojibdemo.view.adapter.SalesAdapter;
import com.nuveq.sojibdemo.viewmodel.Viewmodel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SalesListFragment extends BaseFragment implements ServerResponseFailedCallback {

    private Viewmodel viewmodel;
    private SalesAdapter adapter;
    private List<Result> saleList = new ArrayList<>();
    private FragmentSalesListBinding binding;
    private Calendar calendar;

    @Override
    protected Integer layoutResourceId() {
        return R.layout.fragment_sales_list;
    }

    @Override
    protected void initFragmentComponents() {
        binding = (FragmentSalesListBinding) getBinding();
        viewmodel = ViewModelProviders.of(this).get(Viewmodel.class);
        viewmodel.getSalesRepository().setCallbackListener(this);
        binding.rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new SalesAdapter(getActivity(), saleList);
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
        if (!saleList.isEmpty()) {
            saleList.clear();
        }
        AttendDatePost post = new AttendDatePost();
        post.setEmpid(String.valueOf(SharedPreferencesEnum.getInstance(getActivity()).getInt(SharedPreferencesEnum.Key.USER_ID)));
        post.setFromdate(from);
        post.setTodate(to);
        showProgressDialog();
        viewmodel.getSalesList(post).observe(this, data -> {
            if (data != null) {
                hideProgressDialog();
                saleList.addAll(data);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onFailed(String msg) {
        hideProgressDialog();
        CommonUtils.showCustomAlert(getActivity(), "Failed!", msg, false);
    }
}
