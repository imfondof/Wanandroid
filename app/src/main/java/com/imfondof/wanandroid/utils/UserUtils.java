package com.imfondof.wanandroid.utils;

import android.content.Context;

import com.imfondof.wanandroid.bean.WanLoginBean;
import com.imfondof.wanandroid.ui.mine.WanLoginAct;

public class UserUtils {
    /**
     * 是否登录，没有进入登录页面
     */
    public static boolean isLogin(Context context) {
        boolean isLogin = SPUtils.getBoolean(SPConstants.IS_LOGIN, false);
        if (!isLogin) {
            ToastUtil.showToastLong("请先登录~");
            WanLoginAct.start(context);
            return false;
        } else {
            return true;
        }
    }

    public static void handleLoginFailure() {
        SPUtils.putBoolean(SPConstants.IS_LOGIN, false);
        SPUtils.putString("cookie", "");
        SPUtils.remove("cookie");

        SPUtils.putString(SPConstants.USER_NAME, "请登录");
        SPUtils.putInt(SPConstants.USER_ID, 666);
    }

    /**
     * 是否登录
     */
    public static boolean isLogin() {
        return SPUtils.getBoolean(SPConstants.IS_LOGIN, false);
    }

    public static void handleLoginSuccess(WanLoginBean bean) {
        SPUtils.putString(SPConstants.USER_NAME, bean.getData().getUsername());
        SPUtils.putInt(SPConstants.USER_ID, bean.getData().getId());
        SPUtils.putBoolean(SPConstants.IS_LOGIN, true);
    }
}
