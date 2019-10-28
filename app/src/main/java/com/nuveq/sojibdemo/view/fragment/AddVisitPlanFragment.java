package com.nuveq.sojibdemo.view.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.lifecycle.ViewModelProviders;

import com.nuveq.sojibdemo.R;
import com.nuveq.sojibdemo.appdata.SharedPreferencesEnum;
import com.nuveq.sojibdemo.common.BaseFragment;
import com.nuveq.sojibdemo.databinding.FragmentAddPlanBinding;
import com.nuveq.sojibdemo.datamodel.visitplan.VisitPlanDataPost;
import com.nuveq.sojibdemo.listener.ServerResponseFailedCallback;
import com.nuveq.sojibdemo.utils.CommonUtils;
import com.nuveq.sojibdemo.viewmodel.Viewmodel;

import java.util.ArrayList;
import java.util.Calendar;

public class AddVisitPlanFragment extends BaseFragment implements ServerResponseFailedCallback {

    FragmentAddPlanBinding binding;
    Calendar calendar;

    ArrayList<String> catList = new ArrayList<>();
    ArrayList<String> areaList = new ArrayList<>();
    ArrayList<Integer> areaIdList = new ArrayList<>();
    ArrayList<Integer> catIdList = new ArrayList<>();
    Viewmodel viewModel;
    ArrayAdapter<String> adapter, areaAdapter;
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
        viewModel.getplanRespository().setCallbackListener(this);
        viewModel.getGlobalRepository().setCallbackListener(this);
    }

    @Override
    protected void initFragmentFunctionality() {

        showProgressDialog();
        viewModel.getVisitCatData().observe(getActivity(), data -> {
            if (data != null) {
                hideProgressDialog();
                for (int i = 0; i < data.size(); i++) {
                    try {
                        catList.add(data.get(i).getCategory());
                        catIdList.add(data.get(i).getId());
                    } catch (Exception e) {

                    }
                }

                adapter = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, catList);
                binding.spinerCat.setAdapter(adapter);
            }
        });


    }

    @Override
    protected void initFragmentListener() {
        binding.spinerCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                catItemPosition = position;
                if (catItemPosition < 0) {
                    return;
                }

                showProgressDialog();
                viewModel.getVisitAreaData("" + catIdList.get(catItemPosition)).observe(getActivity(), data -> {
                    if (data != null) {
                        if (!areaList.isEmpty() || !areaIdList.isEmpty()) {
                            areaList.clear();
                            areaIdList.clear();
                        }
                        for (int i = 0; i < data.size(); i++) {
                            try {
                                if (data.get(i).getName() != null) {
                                    areaList.add(data.get(i).getName());
                                    areaIdList.add(data.get(i).getId());
                                }
                            } catch (Exception e) {

                            }
                        }

                        areaAdapter = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, areaList);
                        binding.spinerArea.setAdapter(areaAdapter);
                    }

                    hideProgressDialog();
                });
            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.spinerArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                areaItemPosition = position;

            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> parent) {

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
                showProgressDialog();
                VisitPlanDataPost post = new VisitPlanDataPost();
                post.setEmpId(String.valueOf(SharedPreferencesEnum.getInstance(getActivity()).getInt(SharedPreferencesEnum.Key.USER_ID)));
                post.setDate(date);
                post.setTime(time);
                post.setStatus("Pending");
                post.setVisitAreaId("" + areaIdList.get(areaItemPosition));
                viewModel.getVisitPlanData(post).observe(getActivity(), data -> {
                    if (data != null) {
                        hideProgressDialog();
                        CommonUtils.showCustomAlert(getActivity(), "Success", data, false);
                    }
                });
            }


            //toast("feature is on test");
        });
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
        areaAdapter.notifyDataSetChanged();
        CommonUtils.showCustomAlert(getActivity(), "Failed", msg, false);

    }
}
