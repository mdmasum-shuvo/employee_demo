package com.nuveq.sojibdemo.utils;

import android.content.Context;
import android.graphics.Typeface;

import com.nuveq.sojibdemo.utils.custom_dialog.Activity.SmartDialog;
import com.nuveq.sojibdemo.utils.custom_dialog.ListenerCallBack.SmartDialogClickListener;


public class SmartDialogBuilder {

    private Typeface titleTf, subTitleTf;
    private boolean isCancelable;
    private String title, subtitle, okButtonLable, cancelButtonLable;
    private Context context;
    int img = -1;
    private SmartDialogClickListener okListener;
    private SmartDialogClickListener cancelListener;
    private boolean isNegativeBtnHide;

    public SmartDialogBuilder(Context context) {
        this.context = context;
    }

    public SmartDialogBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public SmartDialogBuilder setSubTitle(String subTitle) {
        this.subtitle = subTitle;
        return this;
    }

    public SmartDialogBuilder setImage(int img) {
        this.img = img;
        return this;
    }

    public SmartDialogBuilder setTitleFont(Typeface titleFont) {
        this.titleTf = titleFont;
        return this;
    }

    public SmartDialogBuilder setSubTitleFont(Typeface subTFont) {
        this.subTitleTf = subTFont;
        return this;
    }

    public SmartDialogBuilder setPositiveButton(String lable, SmartDialogClickListener listener) {
        this.okListener = listener;
        this.okButtonLable = lable;
        return this;
    }

    public SmartDialogBuilder setNegativeButton(String lable, SmartDialogClickListener listener) {
        this.cancelListener = listener;
        this.cancelButtonLable = lable;
        return this;
    }

    public SmartDialogBuilder setCancalable(boolean isCancelable) {
        this.isCancelable = isCancelable;
        return this;
    }

    public SmartDialogBuilder setNegativeButtonHide(boolean isHide) {
        this.isNegativeBtnHide = isHide;
        return this;
    }

    public SmartDialog build() {
        SmartDialog dialog = new SmartDialog(context, title, img, subtitle, titleTf, subTitleTf, isCancelable, isNegativeBtnHide);
        dialog.setNegative(cancelButtonLable, cancelListener);
        dialog.setPositive(okButtonLable, okListener);
        return dialog;
    }
}