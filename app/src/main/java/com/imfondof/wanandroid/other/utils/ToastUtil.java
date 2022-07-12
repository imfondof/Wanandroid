package com.imfondof.wanandroid.other.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.widget.Toast;

import com.imfondof.wanandroid.ui.base.App;
import com.imfondof.wanandroid.other.view.toast.ToastCompat;

public class ToastUtil {
    private static ToastCompat mToast;

    @SuppressLint("ShowToast")
    public static void showToast(String text) {
        if (!TextUtils.isEmpty(text)) {
            if (mToast == null) {
                mToast = ToastCompat.makeText(App.getInstance(), text, Toast.LENGTH_SHORT);
            } else {
                mToast.cancel();
                mToast = ToastCompat.makeText(App.getInstance(), text, Toast.LENGTH_SHORT);
            }
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.setText(text);
            mToast.show();
        }
    }

    @SuppressLint("ShowToast")
    public static void showToastLong(String text) {
        if (!TextUtils.isEmpty(text)) {
            if (mToast == null) {
                mToast = ToastCompat.makeText(App.getInstance(), text, Toast.LENGTH_LONG);
            } else {
                mToast.cancel();
            }
            mToast.setDuration(Toast.LENGTH_LONG);
            mToast.setText(text);
            mToast.show();
        }
    }
}
