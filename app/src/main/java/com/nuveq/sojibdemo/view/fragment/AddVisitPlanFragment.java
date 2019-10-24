package com.nuveq.sojibdemo.view.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import com.nuveq.sojibdemo.R;
import com.nuveq.sojibdemo.common.BaseFragment;
import com.nuveq.sojibdemo.databinding.FragmentPlanListBinding;
import com.nuveq.sojibdemo.datamodel.VisitPlanDataPost;
import com.nuveq.sojibdemo.listener.ServerResponseFailedCallback;
import com.nuveq.sojibdemo.utils.CommonUtils;
import com.nuveq.sojibdemo.viewmodel.Viewmodel;

import java.util.ArrayList;
import java.util.Calendar;

public class AddVisitPlanFragment extends BaseFragment implements ServerResponseFailedCallback {

    FragmentPlanListBinding binding;
    Calendar calendar;

    ArrayList<String> catList = new ArrayList<>();
    ArrayList<String> areaList = new ArrayList<>();
    ArrayList<Integer> catIdList = new ArrayList<>();
    ArrayList<Integer> areaIdList = new ArrayList<>();
    Viewmodel viewModel;
    ArrayAdapter<String> adapter, areaAdapter;
    int catItemPosition = -1, areaItemPosition = -1;

    @Override
    protected Integer layoutResourceId() {
        return R.layout.fragment_plan_list;
    }

    @Override
    protected void initFragmentComponents() {
        binding = (FragmentPlanListBinding) getBinding();
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

                viewModel.getVisitAreaData("" + catIdList.get(catItemPosition)).observe(getActivity(), data -> {
                    if (data != null) {
                        for (int i = 0; i < data.size(); i++) {
                            try {
                                areaList.add(data.get(i).getVisitLocation());
                                areaIdList.add(data.get(i).getId());
                            } catch (Exception e) {

                            }
                        }

                        adapter = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, catList);
                        binding.spinerCat.setAdapter(adapter);
                    }
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
            VisitPlanDataPost post = new VisitPlanDataPost();

            viewModel.getVisitPlanData(post).observe(getActivity(), data -> {
                if (data != null) {
                    CommonUtils.showCustomAlert(getActivity(), "Success", data, false);
                }
            });

            //toast("feature is on test");
        });
    }

    @Override
    public void onFailed(String msg) {
        CommonUtils.showCustomAlert(getActivity(), "Failed", msg, false);

    }
}
