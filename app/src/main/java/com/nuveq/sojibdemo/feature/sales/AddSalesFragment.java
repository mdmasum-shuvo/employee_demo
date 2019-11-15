package com.nuveq.sojibdemo.feature.sales;

import android.os.Build;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.lifecycle.ViewModelProviders;

import com.nuveq.sojibdemo.R;
import com.nuveq.sojibdemo.appdata.SharedPreferencesEnum;
import com.nuveq.sojibdemo.common.BaseFragment;
import com.nuveq.sojibdemo.databinding.FragmentAddSalesBinding;
import com.nuveq.sojibdemo.datamodel.sales.SalesPost;
import com.nuveq.sojibdemo.datamodel.visitplan.VisitPlanDataPost;
import com.nuveq.sojibdemo.listener.ServerResponseFailedCallback;
import com.nuveq.sojibdemo.utils.CommonUtils;
import com.nuveq.sojibdemo.viewmodel.Viewmodel;

import java.util.ArrayList;
import java.util.Calendar;

import jrizani.jrspinner.JRSpinner;

public class AddSalesFragment extends BaseFragment implements ServerResponseFailedCallback {
    private FragmentAddSalesBinding binding;
    private Calendar calendar;
    private Viewmodel viewmodel;
    private int areaItemPosition = -1;
    private String[] areaList;
    private Integer[] areaIdList;
    private String date, time, name, phone, address, desc;

    @Override
    protected Integer layoutResourceId() {
        return R.layout.fragment_add_sales;
    }

    @Override
    protected void initFragmentComponents() {
        binding = (FragmentAddSalesBinding) getBinding();
        calendar = Calendar.getInstance();
        viewmodel = ViewModelProviders.of(this).get(Viewmodel.class);
        viewmodel.getGlobalRepository().setCallbackListener(this);
        viewmodel.getSalesRepository().setCallbackListener(this);
        binding.spiner.setMultiple(false);
        binding.spiner.setSelected(true);
    }

    @Override
    protected void initFragmentFunctionality() {
        showProgressDialog();
        viewmodel.getDoctorAreaData().observe(getActivity(), data -> {
            if (data != null) {
                hideProgressDialog();
                areaList = new String[data.size()];
                areaIdList = new Integer[data.size()];
                for (int i = 0; i < data.size(); i++) {
                    try {
                        if (data.get(i).getName() != null) {
                            areaList[i] = data.get(i).getName();
                            areaIdList[i] = data.get(i).getId();
                        }
                    } catch (Exception e) {

                    }
                }

                binding.spiner.setItems(areaList);
            }

        });

    }

    @Override
    protected void initFragmentListener() {
        binding.tvDate.setOnClickListener(v -> {
            CommonUtils.showDatePicker(getActivity(), binding.tvDate, calendar);
        });
        binding.tvTime.setOnClickListener(v -> {
            CommonUtils.showTimePicker(getActivity(), binding.tvTime, calendar);
        });

        binding.spiner.setOnItemClickListener(new JRSpinner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                areaItemPosition = position;
            }
        });


        binding.btnSave.setOnClickListener(view -> {
            date = binding.tvDate.getText().toString();
            time = binding.tvTime.getText().toString();

            name = binding.tvName.getText().toString();
            address = binding.etAddress.getText().toString();
            phone = binding.etPhone.getText().toString();
            desc = binding.etDesc.getText().toString();
            if (isValid()) {
                saveAlert();
            }
        });
    }


    private void saveAlert() {
        android.app.AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new android.app.AlertDialog.Builder(getActivity(), R.style.DialogTheme);
        } else {
            builder = new android.app.AlertDialog.Builder(getActivity());
        }
        builder.setTitle(getString(R.string.alert));
        builder.setMessage(getString(R.string.save_alert));
        builder.setIcon(R.drawable.bell);
        builder.setNegativeButton("No", null);
        builder.setPositiveButton("Yes", (dialog, which) -> {
            showProgressDialog();
            SalesPost post = new SalesPost();
            post.setDate(date);
            post.setTime(CommonUtils.currentTime_24(time));
            post.setName(name);
            post.setAddress(address);
            post.setPhone(phone);
            post.setReferbydr("" + areaIdList[areaItemPosition]);
            post.setReferbyemp("" + SharedPreferencesEnum.getInstance(getActivity()).getInt(SharedPreferencesEnum.Key.USER_ID));
            post.setDescription(desc);

            viewmodel.getSalesEntry(post).observe(getActivity(), data -> {
                if (data != null) {
                    hideProgressDialog();
                    CommonUtils.showCustomAlert(getActivity(), "Info", data, false);
                }
            });
        });
        android.app.AlertDialog dialog = builder.create();
        dialog.show();
    }


    private boolean isValid() {

        if (name.equals("")) {
            showAlertDialog("Error", "Please enter Patient name");
            return false;
        } else if (phone.equals("")) {
            showAlertDialog("Error", "Please enter Patient phone number");
            return false;
        } else if (address.equals("")) {
            showAlertDialog("Error", "Please Enter Patient addresses");
            return false;
        } else if (areaItemPosition < 0) {
            showAlertDialog("Error", "Please select Reference doctor");
            return false;
        } else if (date.equals("")) {
            showAlertDialog("Error", "Please pick up a visit date");
            return false;
        } else if (time.equals("")) {
            showAlertDialog("Error", "Please pick up a visit date");
            return false;
        }
        return true;
    }

    @Override
    public void onFailed(String msg) {
        hideProgressDialog();
        CommonUtils.showCustomAlert(getActivity(), "Failed!", msg, false);
    }
}
