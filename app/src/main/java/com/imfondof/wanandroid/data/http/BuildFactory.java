package com.imfondof.wanandroid.data.http;

import androidx.annotation.Nullable;

public class BuildFactory {
    private static BuildFactory instance;
    private Object gankHttps;
    private Object wanAndroidHttps;

    public static BuildFactory getInstance() {
        if (instance == null) {
            synchronized (BuildFactory.class) {
                if (instance == null) {
                    instance = new BuildFactory();
                }
            }
        }
        return instance;
    }

    public <T> T create(Class<T> a, @Nullable String type) {
        switch (type) {
            case HttpUtils.API_GANK:
                if (gankHttps == null) {
                    synchronized (BuildFactory.class) {
                        if (gankHttps == null) {
                            gankHttps = HttpUtils.getInstance()
                                    .getBuilder(type)
                                    .build()
                                    .create(a);
                        }
                    }
                }
                return (T) gankHttps;
            default:
                if (wanAndroidHttps == null) {
                    synchronized (BuildFactory.class) {
                        if (wanAndroidHttps == null) {
                            wanAndroidHttps = HttpUtils.getInstance()
                                    .getBuilder(type)
                                    .build()
                                    .create(a);
                        }
                    }
                }
                return (T) wanAndroidHttps;
        }
    }
}
