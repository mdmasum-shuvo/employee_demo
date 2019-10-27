package com.nuveq.sojibdemo.view.fragment;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.nuveq.sojibdemo.BuildConfig;
import com.nuveq.sojibdemo.R;
import com.nuveq.sojibdemo.appdata.SharedPreferencesEnum;
import com.nuveq.sojibdemo.common.BaseFragment;
import com.nuveq.sojibdemo.databinding.FragmentAttendanceListBinding;
import com.nuveq.sojibdemo.datamodel.AttendancePost;
import com.nuveq.sojibdemo.datamodel.CheckOutPost;
import com.nuveq.sojibdemo.datamodel.TrackingPost;
import com.nuveq.sojibdemo.listener.ServerResponseFailedCallback;
import com.nuveq.sojibdemo.service.LocationMonitoringService;
import com.nuveq.sojibdemo.utils.CommonUtils;
import com.nuveq.sojibdemo.utils.maputils.GPSTracker;
import com.nuveq.sojibdemo.view.activity.RegistrationActivity;
import com.nuveq.sojibdemo.viewmodel.Viewmodel;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class AddAttendanceFragment extends BaseFragment implements ServerResponseFailedCallback {

    private Viewmodel viewModel;
    private FragmentAttendanceListBinding binding;
    String location = null;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private boolean mAlreadyStartedService = false;


    @Override
    protected Integer layoutResourceId() {
        return R.layout.fragment_attendance_list;
    }

    @Override
    protected void initFragmentComponents() {
        binding = (FragmentAttendanceListBinding) getBinding();
        viewModel = ViewModelProviders.of(getActivity()).get(Viewmodel.class);
        viewModel.getAttendanceRepository().setCallbackListener(this);
        binding.checkContainer.setVisibility(View.VISIBLE);
        binding.containerAttendList.setVisibility(View.GONE);
    }


    @Override
    protected void initFragmentFunctionality() {
        Intent intent = new Intent(getActivity(), LocationMonitoringService.class);
        binding.btnCheckIn.setOnClickListener(view -> {
            resetAllButton();
            binding.btnCheckIn.setCardBackgroundColor(getResources().getColor(R.color.gray));
            if (latitude == 0) {
                getGpsLocation();
                Toast.makeText(getActivity(), "GPS Error", Toast.LENGTH_LONG).show();
                return;
            } else {
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(getActivity(), Locale.getDefault());

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

            AttendancePost attendancePost = new AttendancePost();
            attendancePost.setEmpid(String.valueOf(SharedPreferencesEnum.getInstance(getActivity()).getInt(SharedPreferencesEnum.Key.USER_ID)));
            attendancePost.setDate(CommonUtils.currentDate());
            attendancePost.setCheckintime(CommonUtils.currentTime());
            attendancePost.setCheckinlocation(location);
            showProgressDialog();
            viewModel.getCheckIn(attendancePost).observe(getActivity(), data -> {
                if (data != null) {
                    startLocationService(intent);
                    hideProgressDialog();
                    CommonUtils.showCustomAlert(getActivity(), "Info", data, false);


                }
            });

        });
        binding.btnCheckOut.setOnClickListener(view -> {
            resetAllButton();
            binding.btnCheckOut.setCardBackgroundColor(getResources().getColor(R.color.gray));

            TrackingPost post = new TrackingPost();
            post.setDate(CommonUtils.currentDate());
            post.setEmpid(String.valueOf(SharedPreferencesEnum.getInstance(getActivity()).getInt(SharedPreferencesEnum.Key.USER_ID)));
            post.setLatpoint("" + latitude);
            post.setLogpoint("" + longitude);
            post.setTime(CommonUtils.currentTime());

            if (latitude == 0) {
                getGpsLocation();
                Toast.makeText(getActivity(), "GPS Error", Toast.LENGTH_LONG).show();
                return;
            } else {
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(getActivity(), Locale.getDefault());

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

            CheckOutPost attendancePost = new CheckOutPost();
            attendancePost.setCheckoutlocation(location);
            attendancePost.setCheckouttime(CommonUtils.currentTime());
            attendancePost.setDate(CommonUtils.currentDate());
            attendancePost.setEmpid(String.valueOf(SharedPreferencesEnum.getInstance(getActivity()).getInt(SharedPreferencesEnum.Key.USER_ID)));
            showProgressDialog();
            viewModel.getCheckOut(attendancePost).observe(getActivity(), data -> {
                if (data != null) {
                    stopService(intent);
                    hideProgressDialog();
                    CommonUtils.showCustomAlert(getActivity(), "Info", data, false);
                }
            });
        });

    }

    private void resetAllButton() {
        binding.btnCheckIn.setCardBackgroundColor(getResources().getColor(R.color.off_white));
        binding.btnCheckOut.setCardBackgroundColor(getResources().getColor(R.color.off_white));

    }


    @Override
    protected void initFragmentListener() {


    }

    @Override
    public void onFailed(String msg) {
        hideProgressDialog();
        CommonUtils.showCustomAlert(getActivity(), "Failed", msg, false);
    }


    @Override
    public void onResume() {
        super.onResume();
        getGpsLocation();
    }


    private void startLocationService(Intent intent) {
        getActivity().startService(intent);
    }

    private void stopService(Intent intent) {
        getActivity().stopService(intent);

    }


}
