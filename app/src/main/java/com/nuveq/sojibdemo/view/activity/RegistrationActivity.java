package com.nuveq.sojibdemo.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends BaseActivity implements ServerResponseFailedCallback {

    ActivityRegistrationBinding binding;
    private boolean mPermissionDenied = false;
    private double latitude, longitude;
    Gson gson = new Gson();
    private GPSTracker gps;
    int itemPosition = -1;
    String name;
    String phone;
    String pass;
    String location = null;
    ArrayList<String> branchResponseArrayList = new ArrayList<>();
    ArrayList<Integer> branchIdList = new ArrayList<>();
    ArrayAdapter<String> adapter;
    private Viewmodel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutResourceFile() {
        return R.layout.activity_registration;
    }

    @Override
    protected void initComponent() {
        binding = (ActivityRegistrationBinding) getBinding();
        viewModel = ViewModelProviders.of(this).get(Viewmodel.class);
        viewModel.getRepository().setCallbackListener(this);
        getGpsLocation();

        if (getIntentData() != null) {
            binding.etUsername.setText(getIntentData());
            binding.registrationContainer.setVisibility(View.GONE);
            binding.loginLayout.setVisibility(View.VISIBLE);
        } else {
            binding.registrationContainer.setVisibility(View.VISIBLE);
            binding.loginLayout.setVisibility(View.GONE);
        }
        CommonUtils.getApiService().getBranch().enqueue(new Callback<ArrayList<Registration>>() {
            @Override
            public void onResponse(Call<ArrayList<Registration>> call, Response<ArrayList<Registration>> response) {
                if (response.isSuccessful()) {
                    for (int i = 0; i < response.body().size(); i++) {
                        try {
                            branchResponseArrayList.add(response.body().get(i).getBranch());
                            branchIdList.add(response.body().get(i).getBranchId());
                        } catch (Exception e) {

                        }

                    }

                    adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, branchResponseArrayList);
                    binding.spiner.setAdapter(adapter);
                    Log.e("", "");

                } else {
                    Log.e("", "");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Registration>> call, Throwable t) {
                Log.e("", "");

            }
        });

        binding.spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemPosition = position;

            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = binding.tvName.getText().toString();
                phone = binding.etPhone.getText().toString();
                pass = binding.etPass.getText().toString();

                if (!isValid()) {
                    return;
                }

                if (latitude == 0) {
                    getGpsLocation();
                    Toast.makeText(RegistrationActivity.this, "GPS Error", Toast.LENGTH_LONG).show();
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
                data.setBranchId(branchIdList.get(itemPosition));

                viewModel.getRegistrationResponse(data).observe(RegistrationActivity.this, isSuccess -> {
                    if (isSuccess) {
                        CommonUtils.showCustomAlert(RegistrationActivity.this, "succeed", "Registration successfully done", false);
                        binding.registrationContainer.setVisibility(View.GONE);
                        binding.loginLayout.setVisibility(View.VISIBLE);

                    }

                });


            }
        });

        binding.btnLogin.setOnClickListener(view -> {
            AuthenticationPost post = new AuthenticationPost();
            post.setMacaddress(AppConstants.ANDROID_ID);
            String pass = binding.etPassword.getText().toString().trim();
            if (pass.equals("")) {
                CommonUtils.showCustomAlert(this, "Error", "Please enter password", false);
                return;
            }
            post.setPassword(pass);
            viewModel.getLoginResponse(post).observe(this, data -> {
                if (data != null) {
                    SharedPreferencesEnum.getInstance(this).put(SharedPreferencesEnum.Key.USER_ID, data.getEmpId());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(AppConstants.INTENT_KEY, data);
                    startActivity(MainActivity.class, true, bundle);
                }
            });
        });

    }

    @Override
    protected void initFunctionality() {

    }

    @Override
    protected void initListener() {

    }

    private boolean isValid() {

        if (name.equals("")) {
            binding.tvName.setError("name can't be empty");
            return false;
        }
        if (pass.equals("")) {
            binding.etPass.setError("password can't be empty");

            return false;
        }
        if (phone.equals("")) {
            binding.etPhone.setError("phone number can't be empty");
            return false;
        }

        if (itemPosition < 0) {

            Toast.makeText(getApplicationContext(), "Please select branch", Toast.LENGTH_SHORT).show();
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
    }


    private void enableMyLocation() {
        if (PermissionUtils.isPermissionGranted(RegistrationActivity.this, PermissionUtils.LOCATION_PERMISSION, PermissionUtils.REQUEST_LOCATION)) {
        }
    }

    private String getIntentData() {
        if (getIntent().getExtras() != null) {
            String phone = getIntent().getStringExtra(AppConstants.PHONE_NUMBER);
            return phone;

        }

        return null;
    }

    public ApiService getApiService() {
        return RestClient.getInstance().callRetrofit();
    }

    @Override
    public void onFailed(String msg) {
        CommonUtils.showCustomAlert(this, "Failed", msg, false);
    }
}
