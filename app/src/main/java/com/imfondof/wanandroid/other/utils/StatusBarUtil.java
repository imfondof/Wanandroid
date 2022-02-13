package com.imfondof.wanandroid.other.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.Window;

import androidx.annotation.ColorInt;

/**
 * Imfondof on 2020/1/16 9:53
 */
public class StatusBarUtil {
    private static boolean sEnableCompat = true;

    public StatusBarUtil() {
    }

    public static void setEnableCompat(boolean enableCompat) {
        sEnableCompat = enableCompat;
    }

    public static int getHeight(Context context) {
        int statusBarHeight = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    public static boolean enableCompat() {
        return sEnableCompat && Build.VERSION.SDK_INT >= 23;
    }

    public static void transparentStatusbarAndLayoutInsert(Activity activity) {
        transparentStatusbarAndLayoutInsert(activity, true);
    }

    public static void transparentStatusbarAndLayoutInsert(Activity activity, boolean light) {
        if (Build.VERSION.SDK_INT >= 23) {
            Window window = activity.getWindow();
            window.clearFlags(67108864);
            window.addFlags(-2147483648);
            window.getDecorView().setSystemUiVisibility(1280);
            window.setStatusBarColor(0);
            statusBarLightMode(activity, light);
        }
    }

    public static void statusBarLightMode(Activity activity, boolean light) {
        if (Build.VERSION.SDK_INT >= 23) {
            int systemUiVisibility = activity.getWindow().getDecorView().getSystemUiVisibility();
            if (light) {
                systemUiVisibility |= 8192;
            } else {
                systemUiVisibility &= -8193;
            }
            activity.getWindow().getDecorView().setSystemUiVisibility(systemUiVisibility);
        }
    }

    public static void whiteStatusBar(Activity activity) {
        setStatusBarColor(activity, -1, true);
    }

    public static void setStatusBarColor(Activity activity, @ColorInt int color, boolean light) {
        if (Build.VERSION.SDK_INT >= 23) {
            Window window = activity.getWindow();
            window.clearFlags(67108864);
            window.addFlags(-2147483648);
            window.setStatusBarColor(color);
            statusBarLightMode(activity, light);
        }
    }
}
