package com.imfondof.wanandroid.view.webView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.imfondof.wanandroid.R;
import com.imfondof.wanandroid.base.App;
import com.imfondof.wanandroid.base.BaseActivity;
import com.imfondof.wanandroid.utils.BaseTools;
import com.imfondof.wanandroid.utils.CheckNetwork;
import com.imfondof.wanandroid.utils.CollectUtils;
import com.imfondof.wanandroid.utils.PermissionHandler;
import com.imfondof.wanandroid.utils.RxSaveImage;
import com.imfondof.wanandroid.utils.StatusBarUtil;
import com.imfondof.wanandroid.utils.ToastUtil;

public class WebViewActivity extends BaseActivity implements IWebPageView {

    // 进度条
    private WebProgress mProgressBar;
    private WebView webView;
    // 全屏时视频加载view
    private FrameLayout videoFullView;
    private Toolbar mTitleToolBar;
    // 加载视频相关
    private MyWebChromeClient mWebChromeClient;
    // title
    private String mTitle;
    // 网页链接
    private String mUrl;
    // 可滚动的title 使用简单 没有渐变效果，文字两旁有阴影
    private TextView tvGunTitle;
    private boolean isTitleFix;
//    private CollectModel collectModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_web_view);

        getIntentData();
        initTitle();
        initWebView();
        webView.loadUrl(mUrl);
        getDataFromBrowser(getIntent());
    }

    private void getIntentData() {
        if (getIntent() != null) {
            mTitle = getIntent().getStringExtra("mTitle");
            mUrl = getIntent().getStringExtra("mUrl");
            isTitleFix = getIntent().getBooleanExtra("isTitleFix", false);
        }
    }

    private void initTitle() {
//        StatusBarUtil.setColor(this, CommonUtils.getColor(R.color.colorTheme), 0);
        StatusBarUtil.setStatusBarColor(this, getResources().getColor(R.color.colorWhite), true);
        webView = findViewById(R.id.webview_detail);
        mTitleToolBar = findViewById(R.id.title_tool_bar);
        tvGunTitle = findViewById(R.id.tv_gun_title);
        mProgressBar = findViewById(R.id.pb_progress);
        mProgressBar.setColor(getResources().getColor(R.color.colorTheme));
        mProgressBar.show();
        initToolBar();
    }

    private void initToolBar() {
        setSupportActionBar(mTitleToolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false);
        }
        mTitleToolBar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.ic_more));
        tvGunTitle.postDelayed(new Runnable() {
            @Override
            public void run() {
                tvGunTitle.setSelected(true);
            }
        }, 2000);
        tvGunTitle.setText(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.webview_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // 返回键
                handleFinish();
                break;
            case R.id.actionbar_share:
                // 分享到
//                String shareText = mTitle + " " + webView.getUrl() + " (分享自云阅 " + Constants.DOWNLOAD_URL + ")";
//                ShareUtils.share(WebViewActivity.this, shareText);
                break;
            case R.id.actionbar_cope:
                // 复制链接
                BaseTools.copy(webView.getUrl());
                ToastUtil.showToast("已复制到剪贴板");
                break;
            case R.id.actionbar_open:
                // 打开链接
//                BaseTools.openLink(WebViewActivity.this, webView.getUrl());
                break;
            case R.id.actionbar_webview_refresh:
                // 刷新页面
                webView.reload();
                break;
            case R.id.actionbar_collect:
                // 添加到收藏
                CollectUtils.instance().collectUrl(mTitle, mUrl, new CollectUtils.OnCollectListener() {
                    @Override
                    public void onSuccess() {
                        ToastUtil.showToastLong("收藏成功");
                    }

                    @Override
                    public void onFailure() {
                        ToastUtil.showToastLong(App.getInstance().getResources().getString(R.string.net_not_login_error));
                    }
                });
//                if (UserUtil.isLogin(webView.getContext())) {
//                    if (SPUtils.getBoolean(Constants.IS_FIRST_COLLECTURL, true)) {
//                        DialogBuild.show(webView, "网址不同于文章，相同网址可多次进行收藏，且不会显示收藏状态。", "知道了", (DialogInterface.OnClickListener) (dialog, which) -> {
//                            SPUtils.putBoolean(Constants.IS_FIRST_COLLECTURL, false);
//                            collectUrl();
//                        });
//                    } else {
//                        collectUrl();
//                    }
//                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void handleFinish() {
        supportFinishAfterTransition();
    }

    private void initWebView() {
        WebSettings ws = webView.getSettings();
        // 网页内容的宽度是否可大于WebView控件的宽度
        ws.setLoadWithOverviewMode(false);
        // 保存表单数据
        ws.setSaveFormData(true);
        // 是否应该支持使用其屏幕缩放控件和手势缩放
        ws.setSupportZoom(true);
        ws.setBuiltInZoomControls(true);
        ws.setDisplayZoomControls(false);
        // 启动应用缓存
        ws.setAppCacheEnabled(true);
        // 设置缓存模式
        ws.setCacheMode(WebSettings.LOAD_DEFAULT);
        // setDefaultZoom  api19被弃用
        // 设置此属性，可任意比例缩放。
        ws.setUseWideViewPort(true);
        // 不缩放
        webView.setInitialScale(100);
        // 告诉WebView启用JavaScript执行。默认的是false。
        ws.setJavaScriptEnabled(true);
        //  页面加载好以后，再放开图片
        ws.setBlockNetworkImage(false);
        // 使用localStorage则必须打开
        ws.setDomStorageEnabled(true);
        // 排版适应屏幕
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        // WebView是否新窗口打开(加了后可能打不开网页)
//        ws.setSupportMultipleWindows(true);

        // webview从5.0开始默认不允许混合模式,https中不能加载http资源,需要设置开启。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ws.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        /** 设置字体默认缩放大小(改变网页字体大小,setTextSize  api14被弃用)*/
        ws.setTextZoom(100);

        mWebChromeClient = new MyWebChromeClient(this);
        webView.setWebChromeClient(mWebChromeClient);
        // 与js交互
        webView.addJavascriptInterface(new ImageClickInterface(this), "injectedObject");
        webView.setWebViewClient(new MyWebViewClient(this));
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return handleLongImage();
            }
        });
    }

    public void initView() {

    }

    protected void initEvent() {

    }

    private void getDataFromBrowser(Intent intent) {

    }

    /**
     * 长按图片事件处理
     */
    private boolean handleLongImage() {
        final WebView.HitTestResult hitTestResult = webView.getHitTestResult();
        // 如果是图片类型或者是带有图片链接的类型
        if (hitTestResult.getType() == WebView.HitTestResult.IMAGE_TYPE ||
                hitTestResult.getType() == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
            // 弹出保存图片的对话框
            new AlertDialog.Builder(WebViewActivity.this)
                    .setItems(new String[]{"发送给朋友", "保存到相册"}, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String picUrl = hitTestResult.getExtra();
                            //获取图片
//                            Log.e("picUrl", picUrl);
                            switch (which) {
                                case 0:
//                                    ShareUtils.shareNetImage(WebViewActivity.this, picUrl);
                                    break;
                                case 1:
                                    if (!PermissionHandler.isHandlePermission(WebViewActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                        return;
                                    }
                                    RxSaveImage.saveImageToGallery(WebViewActivity.this, picUrl, picUrl);
                                    break;
                                default:
                                    break;
                            }
                        }
                    })
                    .show();
            return true;
        }
        return false;
    }

    /**
     * 打开网页:
     *
     * @param mContext 上下文
     * @param mUrl     要加载的网页url
     * @param mTitle   title
     */
    public static void loadUrl(Context mContext, String mUrl, String mTitle) {
        loadUrl(mContext, mUrl, mTitle, false);
    }

    /**
     * 打开网页:
     *
     * @param mContext     上下文
     * @param mUrl         要加载的网页url
     * @param mTitle       title
     * @param isTitleFixed title是否固定
     */
    public static void loadUrl(Context mContext, String mUrl, String mTitle, boolean isTitleFixed) {
        if (CheckNetwork.isNetworkConnected(mContext)) {
            Intent intent = new Intent(mContext, WebViewActivity.class);
            intent.putExtra("mUrl", mUrl);
            intent.putExtra("isTitleFix", isTitleFixed);
            intent.putExtra("mTitle", mTitle == null ? "" : mTitle);
            mContext.startActivity(intent);
        } else {
            ToastUtil.showToastLong(App.getInstance().getResources().getString(R.string.net_error));
        }
    }


    public FrameLayout getVideoFullView() {
        return videoFullView;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //全屏播放退出全屏
            if (mWebChromeClient.inCustomView()) {
                hideCustomView();
                return true;

                //返回网页上一页
            } else if (webView.canGoBack()) {
                webView.goBack();
                return true;

                //退出网页
            } else {
                handleFinish();
            }
        }
        return false;
    }

    /**
     * 全屏时按返加键执行退出全屏方法
     */
    public void hideCustomView() {
        mWebChromeClient.onHideCustomView();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public void hindProgressBar() {
        mProgressBar.hide();
    }

    @Override
    public void showWebView() {
        webView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hindWebView() {
        webView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void startProgress(int newProgress) {
        mProgressBar.setWebProgress(newProgress);
    }

    @Override
    public void addImageClickListener() {

    }

    @Override
    public void fullViewAddView(View view) {
        FrameLayout decor = (FrameLayout) getWindow().getDecorView();
        videoFullView = new FullscreenHolder(WebViewActivity.this);
        videoFullView.addView(view);
        decor.addView(videoFullView);
    }

    @Override
    public void showVideoFullView() {
        videoFullView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hindVideoFullView() {
        videoFullView.setVisibility(View.GONE);
    }

    @Override
    public void setTitle(String title) {
        if (!isTitleFix) {
            tvGunTitle.setText(mTitle);
            this.mTitle = mTitle;
        }
    }

    @Override
    public boolean handleOverrideUrl(String url) {
        return WebUtil.handleThirdApp(this, mUrl, url);
    }
}
