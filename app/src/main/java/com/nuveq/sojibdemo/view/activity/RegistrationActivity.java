package com.nuveq.sojibdemo.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nuveq.sojibdemo.appdata.AppConstants;
import com.nuveq.sojibdemo.appdata.SharedPreferencesEnum;
import com.nuveq.sojibdemo.common.BaseActivity;
import com.nuveq.sojibdemo.datamodel.AuthenticationPost;
import com.nuveq.sojibdemo.listener.ServerResponseFailedCallback;
import com.nuveq.sojibdemo.network.ApiService;
import com.nuveq.sojibdemo.datamodel.registration.Data;
import com.nuveq.sojibdemo.utils.CommonUtils;
import com.nuveq.sojibdemo.utils.PermissionUtils;
import com.nuveq.sojibdemo.R;
import com.nuveq.sojibdemo.datamodel.registration.Registration;
import com.nuveq.sojibdemo.network.RestClient;
import com.nuveq.sojibdemo.databinding.ActivityRegistrationBinding;
import com.nuveq.sojibdemo.utils.maputils.GPSTracker;
import com.nuveq.sojibdemo.viewmodel.Viewmodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jrizani.jrspinner.JRSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends BaseActivity implements ServerResponseFailedCallback {

    ActivityRegistrationBinding binding;
    private boolean mPermissionDenied = false, phonePermissionDenied = false;
    private double latitude, longitude;
    private GPSTracker gps;
    private int itemPosition = -1;
    private String name;
    private String phone;
    private String pass;
    private String location = null;
    private String[] branchResponseArrayList;
    private Integer[] branchIdList;
    private Viewmodel viewModel;


    @Override
    protected int getLayoutResourceFile() {
        return R.layout.activity_registration;
    }

    @Override
    protected void initComponent() {
        binding = (ActivityRegistrationBinding) getBinding();
        viewModel = ViewModelProviders.of(this).get(Viewmodel.class);
        viewModel.getRepository().setCallbackListener(this);
        viewModel.getGlobalRepository().setCallbackListener(this);
        initToolbar();
        binding.Toolbar.logout.setVisibility(View.GONE);
        if (getIntentData() != null) {
            binding.etUsername.setText(getIntentData());
            binding.registrationContainer.setVisibility(View.GONE);
            binding.loginLayout.setVisibility(View.VISIBLE);
            setToolbarTitle("Login");
        } else {
            binding.registrationContainer.setVisibility(View.VISIBLE);
            binding.loginLayout.setVisibility(View.GONE);
            setToolbarTitle("Registration");

        }

    }

    @Override
    protected void initFunctionality() {
        showProgressDialog();
        binding.spiner.setMultiple(false);
        binding.spiner.setSelected(true);
        viewModel.getBrachData().observe(this, data -> {
            if (data != null) {
                branchResponseArrayList = new String[data.size()];
                branchIdList = new Integer[data.size()];
                hideProgressDialog();
                for (int i = 0; i < data.size(); i++) {
                    try {
                        branchResponseArrayList[i] = data.get(i).getBranch();
                        branchIdList[i] = data.get(i).getBranchId();
                    } catch (Exception e) {

                    }
                }

                binding.spiner.setItems(branchResponseArrayList);
            }
        });
    }

    @Override
    protected void initListener() {
        binding.spiner.setOnItemClickListener(new JRSpinner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                itemPosition = position;
            }
        });

        binding.btnSave.setOnClickListener(view -> {
            getGpsLocation();

            name = binding.tvName.getText().toString();
            phone = binding.etPhone.getText().toString();
            pass = binding.etPass.getText().toString();

            if (isValid()) {
                alertDialog();
            }
        });

        binding.btnLogin.setOnClickListener(view -> {
            AuthenticationPost post = new AuthenticationPost();
            post.setMacaddress(AppConstants.ANDROID_ID);
            String pass = binding.etPassword.getText().toString().trim();
            if (latitude == 0) {
                getGpsLocation();
                return;
            }
            if (pass.equals("")) {
                CommonUtils.showCustomAlert(this, "Error", "Please enter password", false);
                return;
            }

            post.setPassword(pass);
            showProgressDialog();
            viewModel.getLoginResponse(post).observe(this, data -> {
                if (data != null) {

                    hideProgressDialog();
                    Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                    SharedPreferencesEnum.getInstance(this).put(SharedPreferencesEnum.Key.USER_ID, data.getEmpId());
                    SharedPreferencesEnum.getInstance(this).put(SharedPreferencesEnum.Key.BRANCH_ID, data.getBranchId());
                    SharedPreferencesEnum.getInstance(this).put(SharedPreferencesEnum.Key.ROLE_ID, data.getRoleId());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(AppConstants.INTENT_KEY, data);
                    startActivity(MainActivity.class, true, bundle);
                    finish();
                }
            });
        });
    }

    private boolean isValid() {

        if (name.equals("")) {
            showAlertDialog("Error", "name can't be empty");
            return false;
        } else if (pass.equals("")) {
            showAlertDialog("Error", "password can't be empty");
            return false;
        } else if (phone.equals("")) {
            showAlertDialog("Error", "phone number can't be empty");
            return false;
        } else if (itemPosition < 0) {
            showAlertDialog("Error", "Please select branch");
            return false;
        }
        return true;
    }


    public void getGpsLocation() {
        gps = new GPSTracker(this);
        if (gps.canGetLocation()) {

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

        } else {

            gps.showSettingsAlert();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPermissionDenied) {
            mPermissionDenied = false;
        } else {
            enableMyLocation();
        }


        getGpsLocation();
    }


    private void enableMyLocation() {
        if (PermissionUtils.isPermissionGranted(RegistrationActivity.this, PermissionUtils.LOCATION_PERMISSION, PermissionUtils.REQUEST_LOCATION)) {
            getGpsLocation();
        }

    }

    private String getIntentData() {
        if (getIntent().getExtras() != null) {
            String phone = getIntent().getStringExtra(AppConstants.PHONE_NUMBER);
            return phone;

        }

        return null;
    }

    private void alertDialog() {
        android.app.AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new android.app.AlertDialog.Builder(this, R.style.DialogTheme);
        } else {
            builder = new android.app.AlertDialog.Builder(this);
        }
        builder.setTitle(getString(R.string.alert));
        builder.setMessage(getString(R.string.save_alert));
        builder.setIcon(R.drawable.bell);
        builder.setNegativeButton("No", null);
        builder.setPositiveButton("Yes", (dialog, which) -> {


            if (latitude == 0) {
                getGpsLocation();
                Toast.makeText(RegistrationActivity.this, "GPS Error,please try again", Toast.LENGTH_LONG).show();
                return;
            } else {
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(RegistrationActivity.this, Locale.getDefault());

                try {
                    addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    String address = addresses.get(0).getFeatureName(); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getSubLocality();
                    location = address + "," + state + "," + city;

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
            Data data = new Data();
            data.setName(name);
            data.setPhoneNumber(phone);
            data.setPassword(pass);
            data.setLocation(location);
            data.setMacAddress(AppConstants.ANDROID_ID);
            data.setBranchId(branchIdList[itemPosition]);
            showProgressDialog();
            viewModel.getRegistrationResponse(data).observe(RegistrationActivity.this, isSuccess -> {
                if (isSuccess != null) {
                    hideProgressDialog();
                    CommonUtils.showCustomAlert(RegistrationActivity.this, "succeed", isSuccess, false);
                    binding.registrationContainer.setVisibility(View.GONE);
                    binding.loginLayout.setVisibility(View.VISIBLE);
                    binding.etUsername.setText(binding.etPhone.getText().toString());

                }

            });

        });
        android.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    public ApiService getApiService() {
        return RestClient.getInstance().callRetrofit();
    }

    @Override
    public void onFailed(String msg) {
        hideProgressDialog();
        CommonUtils.showCustomAlert(this, "Failed", msg, false);
    }
}
