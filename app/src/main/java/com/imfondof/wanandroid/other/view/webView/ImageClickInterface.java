package com.imfondof.wanandroid.other.view.webView;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;

public class ImageClickInterface {

    public ImageClickInterface(Context context) {
    }

    @JavascriptInterface
    public void imageClick(String imgUrl, String hasLink) {
        Log.e("----点击了图片 url: ", "" + imgUrl);
    }

    @JavascriptInterface
    public void textClick(String type, String item_pk) {
        if (!TextUtils.isEmpty(type) && !TextUtils.isEmpty(item_pk)) {
            Log.e("----点击了文字", "");
        }
    }
}
