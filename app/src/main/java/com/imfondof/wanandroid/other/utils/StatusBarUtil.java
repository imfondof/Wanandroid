package com.imfondof.wanandroid.other.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

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

    public static void setTranslucentStatus(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            activity.getWindow()
                    .getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else {
            activity.getWindow()
                    .setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    public static void setLightMode(Activity activity) {
        setMIUIStatusBarDarkIcon(activity, true);
        setMeizuStatusBarDarkIcon(activity, true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    /**
     * 修改 MIUI V6  以上状态栏颜色
     */
    private static void setMIUIStatusBarDarkIcon(@NonNull Activity activity, boolean darkIcon) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            int darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkIcon ? darkModeFlag : 0, darkModeFlag);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    /**
     * 修改魅族状态栏字体颜色 Flyme 4.0
     */
    private static void setMeizuStatusBarDarkIcon(@NonNull Activity activity, boolean darkIcon) {
        try {
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            if (darkIcon) {
                value |= bit;
            } else {
                value &= ~bit;
            }
            meizuFlags.setInt(lp, value);
            activity.getWindow().setAttributes(lp);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

}
