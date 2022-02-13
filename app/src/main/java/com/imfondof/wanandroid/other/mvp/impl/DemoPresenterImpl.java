package com.imfondof.wanandroid.other.mvp.impl;

import android.content.Context;

import com.imfondof.wanandroid.other.mvp.constract.WanContract;
import com.imfondof.wanandroid.other.mvp.BasePresenter;

public class DemoPresenterImpl extends BasePresenter<WanContract.View, WanContract.Model> implements WanContract.Presenter {

    @Override
    public void getHttp() {
        getView().showDialog();
        getModel().doHttp();
    }

    @Override
    protected WanContract.Model createModel() {
        return new DemoModelImpl((Context) getView());
    }
}
