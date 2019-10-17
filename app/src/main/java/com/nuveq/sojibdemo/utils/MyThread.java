package com.nuveq.sojibdemo.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

public class MyThread extends Thread {

    private Activity context;

    public MyThread(Activity context) {
        this.context = context;
    }

    @Override
    public void run() {
        super.run();
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "call thread", Toast.LENGTH_SHORT).show();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
