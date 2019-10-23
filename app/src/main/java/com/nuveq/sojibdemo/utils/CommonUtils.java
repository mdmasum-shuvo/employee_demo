package com.nuveq.sojibdemo.utils;

import android.app.Activity;

import com.nuveq.sojibdemo.appdata.AppConstants;
import com.nuveq.sojibdemo.network.ApiService;
import com.nuveq.sojibdemo.network.RestClient;
import com.nuveq.sojibdemo.utils.custom_dialog.Activity.SmartDialog;
import com.nuveq.sojibdemo.utils.custom_dialog.ListenerCallBack.SmartDialogClickListener;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    public static String currentDate() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(AppConstants.DATE_PATTERN);
        String strDate = formatter.format(date);
        return strDate;
    }

    public static String currentTime() {
        Date date = new Date();
        SimpleDateFormat formatter2 = new SimpleDateFormat(AppConstants.TIME_PATTERN);
        String strDate = formatter2.format(date);
        return strDate;
    }
    public static String currentDate(String currentDate) {
        String builder = currentDate.replace("T00:00:00", "");
        SimpleDateFormat currentFormatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = currentFormatter.parse(builder);
            SimpleDateFormat formatter2 = new SimpleDateFormat(AppConstants.DATE_PATTERN_WORD);
            String strDate = formatter2.format(date);
            return strDate;
        } catch (Exception e) {
        }
        return null;
    }

    public static String currentTime(String currentTime) {
        SimpleDateFormat currentFormatter = new SimpleDateFormat(AppConstants.TIME_PATTERN);
        try {
            Date date = currentFormatter.parse(currentTime);
            SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm");
            String strDate = formatter2.format(date);
            return strDate;
        } catch (Exception e) {
        }
        return null;
    }
}
