package com.nuveq.sojibdemo.view.fragment;

import android.location.Address;
import android.location.Geocoder;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import com.google.gson.Gson;
import com.nuveq.sojibdemo.R;
import com.nuveq.sojibdemo.appdata.SharedPreferencesEnum;
import com.nuveq.sojibdemo.common.BaseFragment;
import com.nuveq.sojibdemo.databinding.FragmentAttendanceListBinding;
import com.nuveq.sojibdemo.datamodel.AttendancePost;
import com.nuveq.sojibdemo.listener.ServerResponseFailedCallback;
import com.nuveq.sojibdemo.utils.CommonUtils;
import com.nuveq.sojibdemo.utils.maputils.GPSTracker;
import com.nuveq.sojibdemo.view.activity.RegistrationActivity;
import com.nuveq.sojibdemo.viewmodel.Viewmodel;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AddAttendanceFragment extends BaseFragment implements ServerResponseFailedCallback {

    private Viewmodel viewModel;
    private FragmentAttendanceListBinding binding;
    private boolean mPermissionDenied = false;
    Gson gson = new Gson();
    private GPSTracker gps;
    String location = null;

    @Override
    protected Integer layoutResourceId() {
        return R.layout.fragment_attendance_list;
    }

    @Override
    protected void initFragmentComponents() {
        binding = (FragmentAttendanceListBinding) getBinding();
        viewModel = ViewModelProviders.of(getActivity()).get(Viewmodel.class);
        viewModel.getAttendanceRepository().setCallbackListener(this);
        getGpsLocation();
    }

    @Override
    protected void initFragmentFunctionality() {

        binding.btnCheckIn.setOnClickListener(view -> {
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
            attendancePost.setCheckinlocation(location);
            attendancePost.setCheckintime(CommonUtils.currentTime());
            attendancePost.setDate(CommonUtils.currentDate());
            attendancePost.setEmpid(String.valueOf(SharedPreferencesEnum.getInstance(getActivity()).getInt(SharedPreferencesEnum.Key.USER_ID)));
            viewModel.getCheckIn(attendancePost).observe(getActivity(), data -> {
                if (data != null) {
                    CommonUtils.showCustomAlert(getActivity(), "Success", data, false);
                }
            });

        });
        binding.btnCheckOut.setOnClickListener(view -> {
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
            attendancePost.setCheckinlocation(location);
            attendancePost.setCheckintime(CommonUtils.currentTime());
            attendancePost.setDate(CommonUtils.currentDate());
            attendancePost.setEmpid(String.valueOf(SharedPreferencesEnum.getInstance(getActivity()).getInt(SharedPreferencesEnum.Key.USER_ID)));
            viewModel.getCheckOut(attendancePost).observe(getActivity(), data -> {
                if (data != null) {
                    CommonUtils.showCustomAlert(getActivity(), "Success", data, false);
                }
            });
        });

    }

    @Override
    protected void initFragmentListener() {

    }

    @Override
    public void onFailed(String msg) {
        CommonUtils.showCustomAlert(getActivity(), "Failed", msg, false);
    }
}
