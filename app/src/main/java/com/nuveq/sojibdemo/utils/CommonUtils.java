package com.nuveq.sojibdemo.utils;

import android.app.Activity;

import com.nuveq.sojibdemo.network.ApiService;
import com.nuveq.sojibdemo.network.RestClient;
import com.nuveq.sojibdemo.utils.custom_dialog.Activity.SmartDialog;
import com.nuveq.sojibdemo.utils.custom_dialog.ListenerCallBack.SmartDialogClickListener;

public class CommonUtils {


    public static ApiService getApiService() {
        return RestClient.getInstance().callRetrofit();
    }
    public static void showCustomAlert(Activity context, String title, String massage, boolean isFinish) {
        new SmartDialogBuilder(context)
                .setTitle(title)
                .setSubTitle(massage)
                .setCancalable(false)
                .setNegativeButtonHide(true) //hide cancel button
                .setPositiveButton("OK", new SmartDialogClickListener() {
                    @Override
                    public void onClick(SmartDialog smartDialog) {
                        smartDialog.dismiss();
                        if (isFinish) {
                            context.finish();
                        }
                    }
                }).setNegativeButton("Cancel", new SmartDialogClickListener() {
            @Override
            public void onClick(SmartDialog smartDialog) {
                smartDialog.dismiss();

            }
        }).build().show();
    }  //alert


}
