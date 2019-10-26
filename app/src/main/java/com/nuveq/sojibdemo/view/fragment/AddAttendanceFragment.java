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
import com.nuveq.sojibdemo.receiver.NetworkChangeReceiver;
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

    NetworkChangeReceiver receiver;

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
        binding.checkContainer.setVisibility(View.VISIBLE);
        binding.containerAttendList.setVisibility(View.GONE);
        receiver = new NetworkChangeReceiver();
    }

    @Override
    protected void initFragmentFunctionality() {

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
                    startLocationService();
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
                    stopService();
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

        startStep1();
    }


    /**
     * Step 1: Check Google Play services
     */
    private void startStep1() {

        //Check whether this user has installed Google play service which is being used by Location updates.
        if (isGooglePlayServicesAvailable()) {

            //Passing null to indicate that it is executing for the first time.
            startStep2(null);

        } else {
            Toast.makeText(getActivity(), "google service available", Toast.LENGTH_LONG).show();
        }
    }


    /**
     * Step 2: Check & Prompt Internet connection
     */
    private Boolean startStep2(DialogInterface dialog) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            promptInternetConnect();
            return false;
        }


        if (dialog != null) {
            dialog.dismiss();
        }

        //Yes there is active internet connection. Next check Location is granted by user or not.

        if (checkPermissions()) { //Yes permissions are granted by the user. Go to the next step.
            startStep3();
        } else {  //No user has not granted the permissions yet. Request now.
            requestPermissions();
        }
        return true;
    }

    /**
     * Show A Dialog with button to refresh the internet state.
     */
    private void promptInternetConnect() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("");
        builder.setMessage("");

        String positiveText = "yes";
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        //Block the Application Execution until user grants the permissions
                        if (startStep2(dialog)) {

                            //Now make sure about location permission.
                            if (checkPermissions()) {

                                //Step 2: Start the Location Monitor Service
                                //Everything is there to start the service.
                                startStep3();
                            } else if (!checkPermissions()) {
                                requestPermissions();
                            }

                        }
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Step 3: Start the Location Monitor Service
     */
    private void startStep3() {

        //And it will be keep running until you close the entire application from task manager.
        //This method will executed only once.

        if (!mAlreadyStartedService) {

            //Start location sharing service to app server.........
            Intent intent = new Intent(getActivity(), LocationMonitoringService.class);
            getActivity().startService(intent);

            mAlreadyStartedService = true;
            //Ends................................................
        }
    }

    /**
     * Return the availability of GooglePlayServices
     */
    public boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(getActivity());
        if (status != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(getActivity(), status, 2404).show();
            }
            return false;
        }
        return true;
    }


    /**
     * Return the current state of the permissions needed.
     */
    private boolean checkPermissions() {
        int permissionState1 = ActivityCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION);

        int permissionState2 = ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION);

        return permissionState1 == PackageManager.PERMISSION_GRANTED && permissionState2 == PackageManager.PERMISSION_GRANTED;

    }

    /**
     * Start permissions requests.
     */
    private void requestPermissions() {

        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        android.Manifest.permission.ACCESS_FINE_LOCATION);

        boolean shouldProvideRationale2 =
                ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.ACCESS_COARSE_LOCATION);


        // Provide an additional rationale to the img_user. This would happen if the img_user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale || shouldProvideRationale2) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");
            showSnackbar(R.string.share,
                    android.R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                                    REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    });
        } else {
            Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the img_user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }


    /**
     * Shows a {@link Snackbar}.
     *
     * @param mainTextStringId The id for the string resource for the Snackbar text.
     * @param actionStringId   The text of the action item.
     * @param listener         The listener associated with the Snackbar action.
     */
    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(
                getActivity().findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If img_user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Log.i(TAG, "Permission granted, updates requested, starting location updates");
                startStep3();

            } else {
                // Permission denied.

                // Notify the img_user via a SnackBar that they have rejected a core permission for the
                // app, which makes the Activity useless. In a real app, core permissions would
                // typically be best requested during a welcome-screen flow.

                // Additionally, it is important to remember that a permission might have been
                // rejected without asking the img_user for permission (device policy or "Never ask
                // again" prompts). Therefore, a img_user interface affordance is typically implemented
                // when permissions are denied. Otherwise, your app could appear unresponsive to
                // touches or interactions which have required permissions.
                showSnackbar(R.string.share_text,
                        R.string.settings, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
            }
        }
    }

    private void startLocationService() {
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receiver, new IntentFilter(LocationMonitoringService.ACTION_LOCATION_BROADCAST)
        );
    }

    private void stopService() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);

    }


}
