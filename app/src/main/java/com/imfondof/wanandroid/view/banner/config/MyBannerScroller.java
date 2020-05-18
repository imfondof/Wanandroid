package com.imfondof.wanandroid.view.banner.config;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

public class MyBannerScroller extends Scroller {

    private int mDuration = MyBannerConfig.DURATION;

    public MyBannerScroller(Context context) {
        super(context);
    }

    public MyBannerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public MyBannerScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    public void setDuration(int time) {
        mDuration = time;
    }
}
