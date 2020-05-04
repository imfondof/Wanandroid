package com.imfondof.wanandroid.utils;

import com.imfondof.wanandroid.bean.WanHomeListBean;
import com.imfondof.wanandroid.http.HttpClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 收藏 工具类
 */

public class CollectUtils {
    private CollectUtils() {
    }

    private static CollectUtils instance;

    public static CollectUtils instance() {
        if (instance == null) {
            instance = new CollectUtils();
        }
        return instance;
    }

    /**
     * 收藏或取消收藏
     */
    public interface OnCollectListener {
        void onSuccess();

        void onFailure();
    }

    /**
     * 收藏
     */
    public void collect(int id, final OnCollectListener listener) {
        Call<WanHomeListBean> call = HttpClient.Builder.getWanAndroidService().collect(id);
        call.enqueue(new Callback<WanHomeListBean>() {
            @Override
            public void onResponse(Call<WanHomeListBean> call, Response<WanHomeListBean> response) {
                if (response.body() != null && response.body().getErrorCode() == 0) {
                    listener.onSuccess();
                } else {
                    listener.onFailure();
                }
            }

            @Override
            public void onFailure(Call<WanHomeListBean> call, Throwable t) {
                listener.onFailure();
            }
        });
    }

    /**
     * 处理首页收藏的操作
     */
    public void handleCollect(boolean isCollect, int id, OnCollectListener listener) {
        if (isCollect) {
            unCollectOrigin(id, listener);
        } else {
            collect(id, listener);
        }
    }

    /**
     * 首页取消收藏
     */
    public void unCollectOrigin(int id, final OnCollectListener listener) {
        Call<WanHomeListBean> call = HttpClient.Builder.getWanAndroidService().unCollectOrigin(id);
        call.enqueue(new Callback<WanHomeListBean>() {
            @Override
            public void onResponse(Call<WanHomeListBean> call, Response<WanHomeListBean> response) {
                if (response.body() != null && response.body().getErrorCode() == 0) {
                    listener.onSuccess();
                } else {
                    listener.onFailure();
                }
            }

            @Override
            public void onFailure(Call<WanHomeListBean> call, Throwable t) {
                listener.onFailure();
            }
        });
    }

    /**
     * 收藏页取消收藏
     */
    public void unCollect(int id, int originId, final OnCollectListener listener) {
        Call<WanHomeListBean> call = HttpClient.Builder.getWanAndroidService().unCollect(id, originId);
        call.enqueue(new Callback<WanHomeListBean>() {
            @Override
            public void onResponse(Call<WanHomeListBean> call, Response<WanHomeListBean> response) {
                if (response.body() != null && response.body().getErrorCode() == 0) {
                    listener.onSuccess();
                } else {
                    listener.onFailure();
                }
            }

            @Override
            public void onFailure(Call<WanHomeListBean> call, Throwable t) {
                listener.onFailure();
            }
        });
    }

    /**
     * 收藏url
     */
    public void collectUrl(String name, String link, final OnCollectListener listener) {
        Call<WanHomeListBean> call = HttpClient.Builder.getWanAndroidService().collectUrl(name, link);
        call.enqueue(new Callback<WanHomeListBean>() {
            @Override
            public void onResponse(Call<WanHomeListBean> call, Response<WanHomeListBean> response) {
                if (response.body() != null && response.body().getErrorCode() == 0) {
                    listener.onSuccess();
                } else {
                    listener.onFailure();
                }
            }

            @Override
            public void onFailure(Call<WanHomeListBean> call, Throwable t) {
                listener.onFailure();
            }
        });
    }

    /**
     * 取消收藏url
     */
    public void unCollectUrl(int id, final OnCollectListener listener) {
        Call<WanHomeListBean> call = HttpClient.Builder.getWanAndroidService().unCollectUrl(id);
        call.enqueue(new Callback<WanHomeListBean>() {
            @Override
            public void onResponse(Call<WanHomeListBean> call, Response<WanHomeListBean> response) {
                if (response.body() != null && response.body().getErrorCode() == 0) {
                    listener.onSuccess();
                } else {
                    listener.onFailure();
                }
            }

            @Override
            public void onFailure(Call<WanHomeListBean> call, Throwable t) {
                listener.onFailure();
            }
        });
    }

    /**
     * 取消收藏url
     */
    public void updateCollectUrl(int id, String name, String link, final OnCollectListener listener) {
        Call<WanHomeListBean> call = HttpClient.Builder.getWanAndroidService().updateUrl(id, name, link);
        call.enqueue(new Callback<WanHomeListBean>() {
            @Override
            public void onResponse(Call<WanHomeListBean> call, Response<WanHomeListBean> response) {
                if (response.body() != null && response.body().getErrorCode() == 0) {
                    listener.onSuccess();
                } else {
                    listener.onFailure();
                }
            }

            @Override
            public void onFailure(Call<WanHomeListBean> call, Throwable t) {
                listener.onFailure();
            }
        });
    }
}
