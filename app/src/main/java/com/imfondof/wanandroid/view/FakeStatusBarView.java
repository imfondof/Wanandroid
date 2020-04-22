package com.imfondof.wanandroid.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.imfondof.wanandroid.utils.StatusBarUtil;

/**
 * 状态栏
 */
public class FakeStatusBarView extends View {
    private static int statusBarHeight;
    private static boolean enableCompat;

    public FakeStatusBarView(Context context) {
        super(context);
        this.init();
    }

    public FakeStatusBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public FakeStatusBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    private void init() {
        if (statusBarHeight == 0) {
            statusBarHeight = StatusBarUtil.getHeight(this.getContext());
            enableCompat = StatusBarUtil.enableCompat();
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = enableCompat ? statusBarHeight : 0;
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
