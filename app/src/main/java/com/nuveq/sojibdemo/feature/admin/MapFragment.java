package com.nuveq.sojibdemo.feature.admin;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.nuveq.sojibdemo.R;
import com.nuveq.sojibdemo.common.BaseFragment;
import com.nuveq.sojibdemo.databinding.FragmentMapBinding;
import com.nuveq.sojibdemo.utils.PermissionUtils;

import java.util.Locale;

public class MapFragment extends BaseFragment implements OnMapReadyCallback,
        GoogleMap.OnMyLocationButtonClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback, AdapterView.OnItemClickListener {
    private FragmentMapBinding binding;
    //view
    private GoogleMap mMap;
    private View mapView;
    private boolean mPermissionDenied = false;

    @Override
    protected Integer layoutResourceId() {
        return R.layout.fragment_map;
    }

    @Override
    protected void initFragmentComponents() {
        binding = (FragmentMapBinding) getBinding();
    }

    @Override
    protected void initFragmentFunctionality() {

    }

    @Override
    protected void initFragmentListener() {

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
        mapCameraUpdate();
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

        mapCameraUpdate();
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
        layoutParams.setMargins(0, 0, 30, 200);
    }

    private void mapCameraUpdate() {
        if (latitude > 0) {
            LatLng latLng = new LatLng(latitude, longitude);
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

}
