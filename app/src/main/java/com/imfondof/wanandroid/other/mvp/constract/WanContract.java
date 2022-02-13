package com.imfondof.wanandroid.other.mvp.constract;

import com.imfondof.wanandroid.other.mvp.IModel;
import com.imfondof.wanandroid.other.mvp.IPresenter;
import com.imfondof.wanandroid.other.mvp.IView;

public interface WanContract {
    interface Model extends IModel{
        void doHttp();
    }

    interface View extends IView {
        void initData();
    }

    interface Presenter extends IPresenter<View> {
        void getHttp();
    }
}
