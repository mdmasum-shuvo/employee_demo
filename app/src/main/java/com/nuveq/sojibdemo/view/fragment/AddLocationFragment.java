package com.nuveq.sojibdemo.view.fragment;


import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;

import com.nuveq.sojibdemo.R;
import com.nuveq.sojibdemo.appdata.AppConstants;
import com.nuveq.sojibdemo.common.BaseFragment;
import com.nuveq.sojibdemo.databinding.FragmentPlanListBinding;
import com.nuveq.sojibdemo.databinding.FragmentProfileBinding;
import com.nuveq.sojibdemo.datamodel.VisitLocationPost;
import com.nuveq.sojibdemo.datamodel.login.Result;
import com.nuveq.sojibdemo.listener.ServerResponseFailedCallback;
import com.nuveq.sojibdemo.utils.CommonUtils;
import com.nuveq.sojibdemo.viewmodel.Viewmodel;

import jrizani.jrspinner.JRSpinner;

public class AddLocationFragment extends BaseFragment  implements ServerResponseFailedCallback {
    FragmentProfileBinding binding;
    private String[] catList;
    private String[] areaList;
    private Integer[] areaIdList;
    private Integer[] catIdList;

    private Viewmodel viewModel;
    private int catItemPosition = -1, areaItemPosition = -1;
    @Override
    protected Integer layoutResourceId() {
        return R.layout.fragment_profile;
    }

    @Override
    protected void initFragmentComponents() {
        getGpsLocation();
        binding = (FragmentProfileBinding) getBinding();
        viewModel = ViewModelProviders.of(this).get(Viewmodel.class);
        viewModel.getGlobalRepository().setCallbackListener(this);
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            if (bundle != null) {
                Result data = (Result) bundle.getSerializable(AppConstants.INTENT_KEY);
                try {
                    binding.setModel(data);
                } catch (Exception e) {
                }
            }
        }

        binding.layoutProfile.setVisibility(View.GONE);
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


        binding.btnSave.setOnClickListener(view -> {
            getGpsLocation();
            if (areaItemPosition < 0) {
                showAlertDialog("Error", "Please select Type and Area");
                return;
            } else if (longitude <= 0) {
                showAlertDialog("GPS ERROR", getString(R.string.gps_chec));
                return;
            }

            showProgressDialog();
            VisitLocationPost post = new VisitLocationPost();
            post.setId("" + areaIdList[areaItemPosition]);
            post.setVisitLatPoint("" + latitude);
            post.setVisitLogPoint("" + longitude);

            viewModel.locationPost(post).observe(getActivity(), data -> {
                if (data != null) {
                    hideProgressDialog();
                    CommonUtils.showCustomAlert(getActivity(), "Info", data, false);

                }
            });
        });
    }

    @Override
    public void onFailed(String msg) {
        hideProgressDialog();
        CommonUtils.showCustomAlert(getActivity(), getString(R.string.failed), msg, false);

    }
}
