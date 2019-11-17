package com.nuveq.sojibdemo.feature.admin;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nuveq.sojibdemo.R;
import com.nuveq.sojibdemo.appdata.SharedPreferencesEnum;
import com.nuveq.sojibdemo.common.BaseFragment;
import com.nuveq.sojibdemo.databinding.FragmentMapBinding;
import com.nuveq.sojibdemo.feature.admin.datamodel.tracking.AdminTrackingPost;
import com.nuveq.sojibdemo.feature.admin.datamodel.tracking.Result;
import com.nuveq.sojibdemo.listener.ServerResponseFailedCallback;
import com.nuveq.sojibdemo.utils.AppUtils;
import com.nuveq.sojibdemo.utils.CommonUtils;
import com.nuveq.sojibdemo.utils.PermissionUtils;
import com.nuveq.sojibdemo.viewmodel.Viewmodel;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import jrizani.jrspinner.JRSpinner;

public class MapFragment extends BaseFragment implements OnMapReadyCallback,
        GoogleMap.OnMyLocationButtonClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback, AdapterView.OnItemClickListener, ServerResponseFailedCallback {
    private FragmentMapBinding binding;
    //view
    private GoogleMap mMap;
    private View mapView;
    private boolean mPermissionDenied = false;
    private Viewmodel viewModel;
    private String[] areaList;
    private Integer[] areaIdList;
    private int areaItemPosition = -1;
    private Calendar calendar;

    private String date, myEmployeId;

    @Override
    protected Integer layoutResourceId() {
        return R.layout.fragment_map;
    }

    @Override
    protected void initFragmentComponents() {
        binding = (FragmentMapBinding) getBinding();
        viewModel = ViewModelProviders.of(getActivity()).get(Viewmodel.class);
        viewModel.getAdminRepository().setCallbackListener(this);
        binding.spiner.setMultiple(false);
        binding.spiner.setSelected(true);
        calendar = Calendar.getInstance();

    }

    @Override
    protected void initFragmentFunctionality() {

        if (isNetworkAvailable()) {
            showProgressDialog();
            viewModel.getAdminEmployeeList(String.valueOf(SharedPreferencesEnum.getInstance(getActivity()).getInt(SharedPreferencesEnum.Key.USER_ID))).observe(getActivity(), data -> {
                if (!data.isEmpty()) {
                    hideProgressDialog();
                    areaList = new String[data.size()];
                    areaIdList = new Integer[data.size()];
                    for (int i = 0; i < data.size(); i++) {
                        try {
                            if (data.get(i).getName() != null) {
                                areaList[i] = data.get(i).getMarketingCode() + "-" + data.get(i).getName();
                                areaIdList[i] = data.get(i).getEmpid();
                            }
                        } catch (Exception e) {

                        }
                    }

                    binding.spiner.setItems(areaList);
                }
            });
        }
    }

    @Override
    protected void initFragmentListener() {
        binding.spiner.setOnItemClickListener(new JRSpinner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                areaItemPosition = position;
                date = binding.etDate.getText().toString();
                if (areaItemPosition >= 0) {
                    myEmployeId = String.valueOf(areaIdList[areaItemPosition]);
                }

                if (isValid())
                    callApi(date, myEmployeId);
            }
        });
        binding.etDate.setOnClickListener(v -> {
            showDatePicker(getContext(), binding.etDate, calendar);

        });
    }

    private void callApi(String date, String myEmployeId) {
        if (!isNetworkAvailable()) {
            CommonUtils.showCustomAlert(getActivity(), getString(R.string.no_internet), getString(R.string.internet_not_available), false);
            return;
        }

        showProgressDialog();
        AdminTrackingPost post = new AdminTrackingPost();
        post.setDate(date);
        post.setEmpid(myEmployeId);
        viewModel.getAdminTrackingList(post).observe(getActivity(), dataList -> {
            if (!dataList.isEmpty()) {
                mapCameraUpdate(new LatLng(dataList.get(0).getLatitude(), dataList.get(0).getLongitude()));
                addMarkerToMap(dataList);
            }
        });


    }


    private void showDatePicker(Context context, final EditText editText, Calendar c) {
        DatePickerDialog dpd = new DatePickerDialog(context, R.style.DatePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int yy, int mm, int dd) {
                mm += 1;
                editText.setText(yy + "-" + (mm) + "-" + dd);
                date = editText.getText().toString();
                if (isValid())
                    callApi(date, myEmployeId);
            }
        },
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH));
        dpd.show();

    }

    private void addMarkerToMap(List<Result> dataList) {
        for (int i = 0; i < dataList.size(); i++) {

            MarkerOptions markerOption = new MarkerOptions()
                    .position(new LatLng(dataList.get(i).getLatitude(), dataList.get(i).getLongitude()))
                    .snippet(CommonUtils.currentTime(dataList.get(i).getTime()))
                    .icon(AppUtils.getMapMarker(getActivity()));
            mMap.addMarker(markerOption);

 /*           Marker marker = mMap.addMarker(markerOption);
            markerList.add(marker);*/

        }

        hideProgressDialog();

    }

    private boolean isValid() {
        if (date.equals("") || date == null) {
            return false;
        } else if (areaItemPosition < 0) {
            return false;
        }

        return true;
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


    private void initMapView() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapView = mapFragment.getView();
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public boolean onMyLocationButtonClick() {
        if (latitude > 0) {
            mapCameraUpdate(new LatLng(latitude, longitude));
        }
        return false;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        if (mMap != null) {
            mMap.clear();
        }
        getGpsLocation();
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        // myMapUtils = new MyMapUtils(getActivity(), mMap);

        if (mapView != null && mapView.findViewById(Integer.parseInt("1")) != null) {
            setMyLocationButtonPosition();
        }

        if (latitude > 0) {
            mapCameraUpdate(new LatLng(latitude, longitude));
        }

        mMap.setOnMyLocationButtonClickListener(this);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        } else {
            mMap.setMyLocationEnabled(true);
        }

    }

    private void setMyLocationButtonPosition() {
        View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                locationButton.getLayoutParams();
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        layoutParams.setMargins(0, 0, 30, 150);
    }

    private void mapCameraUpdate(LatLng latLng) {
        if (latLng != null) {
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
            mMap.animateCamera(cameraUpdate);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PermissionUtils.REQUEST_LOCATION: {
                if (PermissionUtils.isPermissionResultGranted(grantResults)) {
                    enableMyLocation();
                } else {
                    mPermissionDenied = true;
                    Toast.makeText(getActivity(), getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    private void enableMyLocation() {
        if (PermissionUtils.isPermissionGranted(getActivity(), PermissionUtils.LOCATION_PERMISSION, PermissionUtils.REQUEST_LOCATION)) {
            initMapView();

        }
    }

    @Override
    public void onFailed(String msg) {
        hideProgressDialog();
        CommonUtils.showCustomAlert(getActivity(),getString(R.string.failed),msg, false);
    }
}
