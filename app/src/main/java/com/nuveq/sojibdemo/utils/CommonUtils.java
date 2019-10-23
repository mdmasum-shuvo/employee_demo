package com.nuveq.sojibdemo.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.nuveq.sojibdemo.R;
import com.nuveq.sojibdemo.appdata.AppConstants;
import com.nuveq.sojibdemo.network.ApiService;
import com.nuveq.sojibdemo.network.RestClient;
import com.nuveq.sojibdemo.utils.custom_dialog.Activity.SmartDialog;
import com.nuveq.sojibdemo.utils.custom_dialog.ListenerCallBack.SmartDialogClickListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    public static void showDatePicker(Context context, final EditText editText, Calendar c) {
        DatePickerDialog dpd = new DatePickerDialog(context, R.style.DatePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int yy, int mm, int dd) {
                mm += 1;
                editText.setText(yy + "-" + (mm) + "-" + dd);

            }
        },
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH));
        dpd.show();
    }

    public static void showTimePicker(Context context, final EditText editText, Calendar c) {

        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minutes = c.get(Calendar.MINUTE);
        // time picker dialog
        TimePickerDialog picker = new TimePickerDialog(context, R.style.DatePickerDialogTheme,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                        editText.setText(sHour + ":" + sMinute);
                    }
                }, hour, minutes, true);
        picker.show();
    }

}
