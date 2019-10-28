package com.nuveq.sojibdemo.view.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.lifecycle.ViewModelProviders;

import com.nuveq.sojibdemo.R;
import com.nuveq.sojibdemo.common.BaseFragment;
import com.nuveq.sojibdemo.databinding.FragmentAddSalesBinding;
import com.nuveq.sojibdemo.datamodel.sales.SalesPost;
import com.nuveq.sojibdemo.listener.ServerResponseFailedCallback;
import com.nuveq.sojibdemo.utils.CommonUtils;
import com.nuveq.sojibdemo.viewmodel.Viewmodel;

import java.util.ArrayList;
import java.util.Calendar;

public class AddSalesFragment extends BaseFragment implements ServerResponseFailedCallback {
    private FragmentAddSalesBinding binding;
    private Calendar calendar;
    private Viewmodel viewmodel;
    ArrayAdapter<String> areaAdapter;
    int areaItemPosition = -1;
    ArrayList<String> areaList = new ArrayList<>();
    ArrayList<Integer> areaIdList = new ArrayList<>();
    private String date, name, phone, address;

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

    }

    @Override
    protected void initFragmentFunctionality() {
        viewmodel.getDoctorAreaData().observe(getActivity(), data -> {
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
                binding.spiner.setAdapter(areaAdapter);
            }

            hideProgressDialog();
        });

    }

    @Override
    protected void initFragmentListener() {
        binding.tvDate.setOnClickListener(v -> {
            CommonUtils.showDatePicker(getActivity(), binding.tvDate, calendar);
        });

        binding.spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                areaItemPosition = position;

            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        binding.btnSave.setOnClickListener(view -> {
            date = binding.tvDate.getText().toString();
            name = binding.tvName.getText().toString();
            address = binding.etAddress.getText().toString();
            phone = binding.etPhone.getText().toString();
            String desc = binding.etDesc.getText().toString();

            if (isValid()) {
                showProgressDialog();
                SalesPost post = new SalesPost();
                post.setDate(date);
                post.setName(name);
                post.setAddress(address);
                post.setPhone(phone);
                post.setReferbydr("" + areaIdList.get(areaItemPosition));
                post.setDescription(desc);

                viewmodel.getSalesEntry(post).observe(getActivity(), data -> {
                    if (data != null) {
                        hideProgressDialog();
                        CommonUtils.showCustomAlert(getActivity(), "Info", data, false);
                    }
                });
            }
        });
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
        }
        return true;
    }

    @Override
    public void onFailed(String msg) {
        hideProgressDialog();
        areaAdapter.notifyDataSetChanged();
        CommonUtils.showCustomAlert(getActivity(), "Failed!", msg, false);
    }
}
