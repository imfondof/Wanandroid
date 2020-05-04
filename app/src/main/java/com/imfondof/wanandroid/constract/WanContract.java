package com.imfondof.wanandroid.constract;

import com.imfondof.wanandroid.mvp.IModel;
import com.imfondof.wanandroid.mvp.IPresenter;
import com.imfondof.wanandroid.mvp.IView;

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
