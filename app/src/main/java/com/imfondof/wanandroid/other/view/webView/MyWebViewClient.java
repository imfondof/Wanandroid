package com.imfondof.wanandroid.other.view.webView;

import android.text.TextUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.imfondof.wanandroid.other.utils.CheckNetwork;

public class MyWebViewClient extends WebViewClient {
    private IWebPageView mIWebPageView;
    private WebViewActivity mActivity;

    public MyWebViewClient(IWebPageView mIWebPageView) {
        this.mIWebPageView = mIWebPageView;
        mActivity = (WebViewActivity) mIWebPageView;

    }

    @SuppressWarnings("deprecation")
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        return mIWebPageView.handleOverrideUrl(url);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        if (!CheckNetwork.isNetworkConnected(mActivity)) {
            mIWebPageView.hindProgressBar();
        }
        // html加载完成之后，添加监听图片的点击js函数
        mIWebPageView.addImageClickListener();
        super.onPageFinished(view, url);
    }

    // 视频全屏播放按返回页面被放大的问题
    @Override
    public void onScaleChanged(WebView view, float oldScale, float newScale) {
        super.onScaleChanged(view, oldScale, newScale);
        if (newScale - oldScale > 7) {
            view.setInitialScale((int) (oldScale / newScale * 100)); //异常放大，缩回去。
        }
    }
}
