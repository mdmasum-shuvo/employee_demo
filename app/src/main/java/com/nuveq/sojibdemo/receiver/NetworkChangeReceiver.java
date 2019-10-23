package com.nuveq.sojibdemo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.nuveq.sojibdemo.listener.NetworkConnectivityListener;
import com.nuveq.sojibdemo.service.LocationMonitoringService;
import com.nuveq.sojibdemo.utils.AppUtils;


public class NetworkChangeReceiver extends BroadcastReceiver {

    private static NetworkConnectivityListener listener;


    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            if (AppUtils.isNetworkAvailable(context)) {
                listener.onConnect(true);
                LocalBroadcastManager.getInstance(context).registerReceiver(
                        new BroadcastReceiver() {
                            @Override
                            public void onReceive(Context context, Intent intent) {
                                String latitude = intent.getStringExtra(LocationMonitoringService.EXTRA_LATITUDE);
                                String longitude = intent.getStringExtra(LocationMonitoringService.EXTRA_LONGITUDE);

                                // Toast.makeText(context, latitude + " \n" + longitude, Toast.LENGTH_SHORT).show();

                            }
                        }, new IntentFilter(LocationMonitoringService.ACTION_LOCATION_BROADCAST)
                );
                Log.e("keshav", "Online Connect Intenet ");
            } else {
                listener.onConnect(false);
                Log.e("keshav", "Conectivity Failure !!! ");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void setNetworkChangeListener(NetworkConnectivityListener listener) {
        this.listener = listener;
    }
}