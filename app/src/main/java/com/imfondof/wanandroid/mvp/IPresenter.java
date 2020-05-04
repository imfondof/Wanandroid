package com.imfondof.wanandroid.mvp;

public interface IPresenter<V extends IView> {
    void attach(V view);

    void detach();
}
