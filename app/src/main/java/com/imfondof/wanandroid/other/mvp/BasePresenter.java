package com.imfondof.wanandroid.other.mvp;

import androidx.annotation.CallSuper;

import java.lang.ref.WeakReference;

public abstract class BasePresenter<V extends IView, M extends IModel> implements IPresenter<V> {
    private WeakReference<V> mView;
    private M mModel;

    public void attach(V view) {
        mView = new WeakReference<>(view);
        mModel = createModel();
    }

    @CallSuper
    public void detach() {
        if (mView != null) {
            mView.clear();
            mView = null;
        }
    }

    protected abstract M createModel();

    protected V getView() {
        if (mView != null) {
            return mView.get();
        }
        return null;
    }

    protected M getModel() {
        return mModel;
    }
}
