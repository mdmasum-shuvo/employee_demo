package com.nuveq.sojibdemo.view.fragment.visitplan;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.lifecycle.ViewModelProviders;

import com.nuveq.sojibdemo.R;
import com.nuveq.sojibdemo.appdata.SharedPreferencesEnum;
import com.nuveq.sojibdemo.common.BaseFragment;
import com.nuveq.sojibdemo.databinding.FragmentAddPlanBinding;
import com.nuveq.sojibdemo.datamodel.CheckOutPost;
import com.nuveq.sojibdemo.datamodel.TrackingPost;
import com.nuveq.sojibdemo.datamodel.visitplan.VisitPlanDataPost;
import com.nuveq.sojibdemo.listener.ServerResponseFailedCallback;
import com.nuveq.sojibdemo.utils.CommonUtils;
import com.nuveq.sojibdemo.viewmodel.Viewmodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import jrizani.jrspinner.JRSpinner;

public class AddVisitPlanFragment extends BaseFragment implements ServerResponseFailedCallback {

    FragmentAddPlanBinding binding;
    Calendar calendar;

    String[] catList;
    String[] areaList;
    Integer[] areaIdList;
    Integer[] catIdList;

    Viewmodel viewModel;
    int catItemPosition = -1, areaItemPosition = -1;
    private String date, time;

    @Override
    protected Integer layoutResourceId() {
        return R.layout.fragment_add_plan;
    }

    @Override
    protected void initFragmentComponents() {
        binding = (FragmentAddPlanBinding) getBinding();
        calendar = Calendar.getInstance();
        viewModel = ViewModelProviders.of(this).get(Viewmodel.class);
        viewModel.getGlobalRepository().setCallbackListener(this);
        viewModel.getplanRespository().setCallbackListener(this);
    }

    @Override
    protected void initFragmentFunctionality() {

        showProgressDialog();
        viewModel.getVisitCatData().observe(getActivity(), data -> {
            if (data != null) {
                hideProgressDialog();
                catList = new String[data.size()];
                catIdList = new Integer[data.size()];
                for (int i = 0; i < data.size(); i++) {
                    try {
                        catList[i] = data.get(i).getCategory();
                        catIdList[i] = data.get(i).getId();
                    } catch (Exception e) {

                    }
                }

                binding.spinerCat.setItems(catList);
            }
        });

        binding.spinerCat.setMultiple(false);
        binding.spinerArea.setMultiple(false);
        binding.spinerArea.setSelected(true);
        binding.spinerCat.setSelected(true);

    }

    @Override
    protected void initFragmentListener() {

        binding.spinerCat.setOnItemClickListener(new JRSpinner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                catItemPosition = position;
                areaItemPosition = -1;
                if (catItemPosition < 0) {
                    return;
                }
                areaList = new String[0];
                areaIdList = new Integer[0];

                showProgressDialog();
                viewModel.getVisitAreaData("" + catIdList[catItemPosition]).observe(getActivity(), data -> {
                    if (data != null) {
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

                        binding.spinerArea.setText("");
                        binding.spinerArea.setHint("Select one");
                        binding.spinerArea.setItems(areaList);
                    }


                    hideProgressDialog();
                });
            }
        });

        binding.spinerArea.setOnItemClickListener(new JRSpinner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                areaItemPosition = position;
            }
        });


        binding.tvDate.setOnClickListener(v -> {
            CommonUtils.showDatePicker(getActivity(), binding.tvDate, calendar);
        });
        binding.tvTime.setOnClickListener(v -> {
            CommonUtils.showTimePicker(getActivity(), binding.tvTime, calendar);
        });
        binding.btnSave.setOnClickListener(v -> {
            date = binding.tvDate.getText().toString();
            time = binding.tvTime.getText().toString();
            if (isValid()) {
                saveAlert();
            }

            //toast("feature is on test");
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
            VisitPlanDataPost post = new VisitPlanDataPost();
            post.setEmpId(String.valueOf(SharedPreferencesEnum.getInstance(getActivity()).getInt(SharedPreferencesEnum.Key.USER_ID)));
            post.setDate(date);
            post.setTime(CommonUtils.currentTime_24(time));
            post.setStatus("Pending");
            post.setVisitAreaId("" + areaIdList[areaItemPosition]);
            viewModel.getVisitPlanData(post).observe(getActivity(), data -> {
                if (data != null) {
                    hideProgressDialog();
                    CommonUtils.showCustomAlert(getActivity(), "Success", data, false);
                }
            });
        });
        android.app.AlertDialog dialog = builder.create();
        dialog.show();
    }


    private boolean isValid() {

        if (date.equals("")) {
            showAlertDialog("Error", "Please pick up a visit date");
            return false;
        } else if (time.equals("")) {
            showAlertDialog("Error", "Please pick up visit time");
            return false;
        } else if (areaItemPosition < 0) {
            showAlertDialog("Error", "Please select Type and Area");
            return false;
        }
        return true;
    }

    @Override
    public void onFailed(String msg) {
        hideProgressDialog();
    }
}
