package com.imfondof.wanandroid.other.mvp;

public interface IPresenter<V extends IView> {
    void attach(V view);

    void detach();
}
