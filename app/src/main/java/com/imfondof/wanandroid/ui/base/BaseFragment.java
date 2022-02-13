package com.imfondof.wanandroid.ui.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * 懒加载
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    public boolean mIsPrepared = false;     //是否准备好
    public boolean mIsFirst = true;         //是否第一次加载
    protected boolean mIsVisible = false;   //是否显示了

    public View mView;
    private SparseArray<View> views = new SparseArray<>();
    ProgressDialog mDialog;

    /**
     * 布局
     */
    public abstract int setContent();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(setContent(), container, false);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        mIsPrepared = true;  // 准备就绪
        isCanLoadData();
    }

    protected void initView() {
    }

    /**
     * 显示时加载数据,需要这样的使用
     * 注意声明 isPrepared，先初始化
     * 生命周期会先执行 setUserVisibleHint 再执行onActivityCreated
     * 在 onActivityCreated 之后第一次显示加载数据，只加载一次
     */
    protected void loadData() {
    }

    protected void isCanLoadData() {
        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }
        loadData();
        mIsFirst = false;
    }

    /**
     * 在这里实现Fragment数据的缓加载.
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            mIsVisible = true;
            onVisible();
        } else {
            mIsVisible = false;
            onInvisible();
        }
    }

    private void onVisible() {
        isCanLoadData();
    }

    private void onInvisible() {//比如执行opause，用来停止banner的自动滑动
    }

    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = mView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) views.get(viewId);
    }

    public void onClick(View v) {
    }

    public void showLoading() {
        if (mDialog == null) {
            mDialog = new ProgressDialog(getActivity());
        }
        mDialog.setTitle("加载中");
//        mDialog.show();
    }

    public void dissmissLoding() {
        if (mDialog != null) {
            mDialog.cancel();
        }
    }
}
