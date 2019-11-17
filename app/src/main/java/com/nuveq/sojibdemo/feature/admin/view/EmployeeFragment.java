package com.nuveq.sojibdemo.feature.admin.view;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nuveq.sojibdemo.R;
import com.nuveq.sojibdemo.appdata.SharedPreferencesEnum;
import com.nuveq.sojibdemo.common.BaseFragment;
import com.nuveq.sojibdemo.databinding.FragmentAdminEmployeeBinding;
import com.nuveq.sojibdemo.feature.admin.datamodel.AdminAttendPost;
import com.nuveq.sojibdemo.feature.admin.datamodel.attendance.Result;
import com.nuveq.sojibdemo.listener.ServerResponseFailedCallback;
import com.nuveq.sojibdemo.utils.CommonUtils;
import com.nuveq.sojibdemo.view.adapter.AttendanceAdapter;
import com.nuveq.sojibdemo.viewmodel.Viewmodel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import jrizani.jrspinner.JRSpinner;

public class EmployeeFragment extends BaseFragment implements ServerResponseFailedCallback {

    private FragmentAdminEmployeeBinding binding;
    private Viewmodel viewModel;
    private String[] areaList;
    private Integer[] areaIdList;
    private int areaItemPosition = -1;
    private Calendar calendar;
    private AlertDialog askIdDialog;
    private String myEmployeId;
    private List<Result> empList = new ArrayList<>();
    private AttendanceAdapter adapter;

    @Override
    protected Integer layoutResourceId() {
        return R.layout.fragment_admin_employee;
    }

    @Override
    protected void initFragmentComponents() {
        binding = (FragmentAdminEmployeeBinding) getBinding();
        viewModel = ViewModelProviders.of(getActivity()).get(Viewmodel.class);
        viewModel.getAdminRepository().setCallbackListener(this);
        viewModel.getAttendanceRepository().setCallbackListener(this);
        calendar = Calendar.getInstance();
        binding.rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new AttendanceAdapter(getActivity(), empList);
        binding.rv.setAdapter(adapter);
    }

    @Override
    protected void initFragmentFunctionality() {

        if (isNetworkAvailable()) {
            showProgressDialog();
            viewModel.getAdminEmployeeList(String.valueOf(SharedPreferencesEnum.getInstance(getActivity()).getInt(SharedPreferencesEnum.Key.USER_ID))).observe(getActivity(), data -> {
                if (!data.isEmpty()) {
                    hideProgressDialog();
                    areaList = new String[data.size()];
                    areaIdList = new Integer[data.size()];
                    for (int i = 0; i < data.size(); i++) {
                        try {
                            if (data.get(i).getName() != null) {
                                areaList[i] = data.get(i).getMarketingCode() + "-" + data.get(i).getName();
                                areaIdList[i] = data.get(i).getEmpid();
                            }
                        } catch (Exception e) {

                        }
                    }


                }
            });
        }
    }

    @Override
    protected void initFragmentListener() {

        binding.btnFloatFilter.setOnClickListener(view -> {
            showAskIdDialog();
        });
    }

    private void showAskIdDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_admin_filter, null, false);
        builder.setView(view);
        final EditText etDateFrom = view.findViewById(R.id.etDateFrom);
        final EditText etDateTo = view.findViewById(R.id.etDateTo);
        final JRSpinner spiner = view.findViewById(R.id.spiner);
        Button btnSearch = view.findViewById(R.id.btnFilter);

        etDateFrom.setOnClickListener(v -> {
            CommonUtils.showDatePicker(getContext(), etDateFrom, calendar);
        });
        etDateTo.setOnClickListener(v -> {
            CommonUtils.showDatePicker(getContext(), etDateTo, calendar);
        });
        spiner.setMultiple(false);
        spiner.setSelected(true);
        spiner.setItems(areaList);

        spiner.setOnItemClickListener(new JRSpinner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                areaItemPosition = position;
                if (areaItemPosition >= 0) {
                    myEmployeId = String.valueOf(areaIdList[areaItemPosition]);
                }

                binding.tvEmpName.setText(areaList[areaItemPosition]);
            }
        });
        askIdDialog = builder.create();
        askIdDialog.show();
        // user="demo1254";
        // pass="demo#123";
        btnSearch.setOnClickListener(view1 -> {
            String fromDate = etDateFrom.getText().toString();
            String toDate = etDateTo.getText().toString();

            if (fromDate.equals("") || toDate.equals("")) {
                hideProgressDialog();
                showAlertDialog(getString(R.string.error_text), getString(R.string.user_empty));
                return;
            }
            if (areaItemPosition < 0) {
                hideProgressDialog();
                showAlertDialog(getString(R.string.error_text), getString(R.string.select_empl));
                return;
            }

            askIdDialog.dismiss();
            callApi(fromDate, toDate);


        });
    }

    private void callApi(String fromDate, String toDate) {
        if (!isNetworkAvailable()) {
            CommonUtils.showCustomAlert(getActivity(), getString(R.string.no_internet), getString(R.string.internet_not_available), false);
            return;
        }
        showProgressDialog();
        AdminAttendPost post = new AdminAttendPost();
        post.setFromdate(fromDate);
        post.setTodate(toDate);
        post.setAdmid(String.valueOf(SharedPreferencesEnum.getInstance(getActivity()).getInt(SharedPreferencesEnum.Key.USER_ID)));
        post.setBranchid(String.valueOf(SharedPreferencesEnum.getInstance(getActivity()).getInt(SharedPreferencesEnum.Key.BRANCH_ID)));
        post.setRoleid(String.valueOf(SharedPreferencesEnum.getInstance(getActivity()).getInt(SharedPreferencesEnum.Key.ROLE_ID)));
        post.setEmpid(myEmployeId);
        viewModel.getAdminAttenDataList(post).observe(getActivity(), dataList -> {
            if (dataList != null) {
                hideProgressDialog();
                if (!empList.isEmpty()) {
                    empList.clear();
                }
                empList.addAll(dataList);
                empList.addAll(dataList);
                empList.addAll(dataList);
                empList.addAll(dataList);
                empList.addAll(dataList);
                empList.addAll(dataList);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onFailed(String msg) {
        hideProgressDialog();
        adapter.notifyDataSetChanged();
        CommonUtils.showCustomAlert(getActivity(), getString(R.string.failed), msg, false);
    }
}
