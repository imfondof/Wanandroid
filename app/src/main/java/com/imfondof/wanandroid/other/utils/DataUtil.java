package com.imfondof.wanandroid.other.utils;

import android.text.TextUtils;

/**
 * @author jingbin
 */
public class DataUtil {
    public static String getName(String username, String nickname) {
        if (!TextUtils.isEmpty(nickname)) {
            return username + "(" + nickname + ")";
        } else {
            return username;
        }
    }

    /**
     * 直接使用html格式化会有性能问题
     */
    public static String getHtmlString(String content) {
        if (content != null && content.contains("&amp;")) {
            return content.replace("&amp;", "&");
        }
        return content;
    }
}
