package com.imfondof.wanandroid.mvp;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.Nullable;

public abstract class BaseMVPActivity<V extends IView,P extends IPresenter<V>> extends Activity {
    private P mPresenter;
    private Dialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        mPresenter.attach(getView());
        mDialog = new Dialog(this);
    }

    public void showDialog() {
        if (mDialog == null) {
            mDialog = new Dialog(this);
        }
        mDialog.show();
    }

    public void dissDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
        mDialog = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detach();
        }
        dissDialog();
    }

    protected P getPresenter() {
        return mPresenter;
    }

    protected abstract P createPresenter();

    protected abstract V getView();
}
