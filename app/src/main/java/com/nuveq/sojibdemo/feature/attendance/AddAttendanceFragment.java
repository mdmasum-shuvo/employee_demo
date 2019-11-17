package com.nuveq.sojibdemo.feature.attendance;


import android.content.Intent;

import android.location.Address;
import android.location.Geocoder;

import android.os.Build;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import com.nuveq.sojibdemo.R;
import com.nuveq.sojibdemo.appdata.AppConstants;
import com.nuveq.sojibdemo.appdata.SharedPreferencesEnum;
import com.nuveq.sojibdemo.common.BaseFragment;
import com.nuveq.sojibdemo.databinding.FragmentAttendanceListBinding;
import com.nuveq.sojibdemo.datamodel.AttendancePost;
import com.nuveq.sojibdemo.datamodel.CheckOutPost;
import com.nuveq.sojibdemo.datamodel.TrackingPost;
import com.nuveq.sojibdemo.datamodel.registration.Data;
import com.nuveq.sojibdemo.listener.ServerResponseFailedCallback;
import com.nuveq.sojibdemo.service.LocationMonitoringService;
import com.nuveq.sojibdemo.utils.CommonUtils;
import com.nuveq.sojibdemo.view.activity.RegistrationActivity;
import com.nuveq.sojibdemo.viewmodel.Viewmodel;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import jrizani.jrspinner.JRSpinner;

public class AddAttendanceFragment extends BaseFragment implements ServerResponseFailedCallback {

    private Viewmodel viewModel;
    private FragmentAttendanceListBinding binding;
    private String location = null;
    private int areaItemPosition = -1;
    private String[] areaList;
    private Integer[] areaIdList;

    @Override
    protected Integer layoutResourceId() {
        return R.layout.fragment_attendance_list;
    }

    @Override
    protected void initFragmentComponents() {
        binding = (FragmentAttendanceListBinding) getBinding();
        viewModel = ViewModelProviders.of(getActivity()).get(Viewmodel.class);
        viewModel.getAttendanceRepository().setCallbackListener(this);
        viewModel.getGlobalRepository().setCallbackListener(this);
        binding.checkContainer.setVisibility(View.VISIBLE);
        binding.containerAttendList.setVisibility(View.GONE);
        binding.spiner.setMultiple(false);
        binding.spiner.setSelected(true);
    }


    @Override
    protected void initFragmentFunctionality() {

        showProgressDialog();
        viewModel.getShiftList().observe(getActivity(), data -> {
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

    private void resetAllButton() {
        binding.btnCheckIn.setCardBackgroundColor(getResources().getColor(R.color.off_white));
        binding.btnCheckOut.setCardBackgroundColor(getResources().getColor(R.color.off_white));

    }


    @Override
    protected void initFragmentListener() {
        Intent intent = new Intent(getActivity(), LocationMonitoringService.class);
        binding.btnCheckIn.setOnClickListener(view -> {
            getGpsLocation();
            if (isValid()) {
                checkInAlertDialog(intent);
            }
        });
        binding.btnCheckOut.setOnClickListener(view -> {
            getGpsLocation();
            if (isValid()) {
                checkoutAlertDialog(intent);
            }
        });


        binding.spiner.setOnItemClickListener(new JRSpinner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                areaItemPosition = position;
            }
        });


    }

    private void checkInAlertDialog(Intent intent) {
        android.app.AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new android.app.AlertDialog.Builder(getActivity(), R.style.DialogTheme);
        } else {
            builder = new android.app.AlertDialog.Builder(getActivity());
        }
        builder.setTitle(getString(R.string.alert));
        builder.setMessage(getString(R.string.check_in_alert));
        builder.setIcon(R.drawable.bell);
        builder.setNegativeButton("No", null);
        builder.setPositiveButton("Yes", (dialog, which) -> {

            resetAllButton();
            binding.btnCheckIn.setCardBackgroundColor(getResources().getColor(R.color.gray));
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(getActivity(), Locale.getDefault());

            if (longitude == 0) {
                showAlertDialog("GPS ERROR", "Please check your gps connection");
                return;
            }

            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1);
                String address = addresses.get(0).getFeatureName(); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getSubLocality();
                location = address + "," + state + "," + city;

            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
            }


            AttendancePost attendancePost = new AttendancePost();
            attendancePost.setEmpid(String.valueOf(SharedPreferencesEnum.getInstance(getActivity()).getInt(SharedPreferencesEnum.Key.USER_ID)));
            attendancePost.setDate(CommonUtils.currentDate());
            attendancePost.setCheckintime(CommonUtils.currentTime());
            attendancePost.setCheckinlocation(location);
            attendancePost.setShift("" + areaIdList[areaItemPosition]);
            showProgressDialog();
            viewModel.getCheckIn(attendancePost).observe(getActivity(), data -> {
                if (data != null) {
                    startLocationService(intent);
                    hideProgressDialog();
                    CommonUtils.showCustomAlert(getActivity(), "Info", data, false);


                }
            });


        });
        android.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void checkoutAlertDialog(Intent intent) {
        android.app.AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new android.app.AlertDialog.Builder(getActivity(), R.style.DialogTheme);
        } else {
            builder = new android.app.AlertDialog.Builder(getActivity());
        }
        builder.setTitle(getString(R.string.alert));
        builder.setMessage(getString(R.string.check_out_alert));
        builder.setIcon(R.drawable.bell);
        builder.setNegativeButton("No", null);
        builder.setPositiveButton("Yes", (dialog, which) -> {
            resetAllButton();
            binding.btnCheckOut.setCardBackgroundColor(getResources().getColor(R.color.gray));

            TrackingPost post = new TrackingPost();
            post.setDate(CommonUtils.currentDate());
            post.setEmpid(String.valueOf(SharedPreferencesEnum.getInstance(getActivity()).getInt(SharedPreferencesEnum.Key.USER_ID)));
            post.setLatpoint("" + latitude);
            post.setLogpoint("" + longitude);
            post.setTime(CommonUtils.currentTime());

            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(getActivity(), Locale.getDefault());
            if (longitude == 0) {
                showAlertDialog("GPS ERROR", "Please check your gps connection");
                return;
            }
            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1);
                String address = addresses.get(0).getFeatureName(); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getSubLocality();
                location = address + "," + state + "," + city;

            } catch (IOException e) {
                e.printStackTrace();
            }


            CheckOutPost attendancePost = new CheckOutPost();
            attendancePost.setCheckoutlocation(location);
            attendancePost.setCheckouttime(CommonUtils.currentTime());
            attendancePost.setDate(CommonUtils.currentDate());
            attendancePost.setEmpid(String.valueOf(SharedPreferencesEnum.getInstance(getActivity()).getInt(SharedPreferencesEnum.Key.USER_ID)));
            attendancePost.setShift("" + areaIdList[areaItemPosition]);
            showProgressDialog();
            viewModel.getCheckOut(attendancePost).observe(getActivity(), data -> {
                if (data != null) {
                    stopService(intent);
                    hideProgressDialog();
                    CommonUtils.showCustomAlert(getActivity(), "Info", data, false);
                }
            });


        });
        android.app.AlertDialog dialog = builder.create();
        dialog.show();
    }


    private boolean isValid() {
        if (areaItemPosition < 0) {
            showAlertDialog("ERROR", "Please Select and confirm your shift first");
            return false;
        }
        return true;
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
