package com.imfondof.wanandroid.ui.allFrgs;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.imfondof.wanandroid.R;
import com.imfondof.wanandroid.adapter.WanCoinDetailAdapter;
import com.imfondof.wanandroid.adapter.WanHomeAdapter;
import com.imfondof.wanandroid.base.BaseFragment;
import com.imfondof.wanandroid.bean.WanCoinDetailBean;
import com.imfondof.wanandroid.bean.WanHomeListBean;
import com.imfondof.wanandroid.http.HttpClient;
import com.imfondof.wanandroid.utils.ToastUtil;
import com.imfondof.wanandroid.view.webView.WebViewActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WanCoinDetailFrg extends BaseFragment {
    private WanCoinDetailAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private List<WanCoinDetailBean.DataBean.DatasBean> mDatas;
    private SmartRefreshLayout mRefreshLayout;
    int page = 0;

    public static Fragment newInstance() {
        Bundle bundle = new Bundle();
        WanCoinDetailFrg fragment = new WanCoinDetailFrg();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int setContent() {
        return R.layout.frg_refresh_recyclerview;
    }

    public void getData(int page) {
        Call<WanCoinDetailBean> call = HttpClient.Builder.getWanAndroidService().getCoinDetail(page);
        call.enqueue(new Callback<WanCoinDetailBean>() {
            @Override
            public void onResponse(Call<WanCoinDetailBean> call, Response<WanCoinDetailBean> response) {
                if (response.body().getErrorCode() == 0) {
                    if (response.body() != null && response.body().getData() != null) {
                        mDatas.addAll(response.body().getData().getDatas());
                        mAdapter.setNewData(mDatas);
                    }
                } else {//getErrorCode为-1001时，提醒内容为“请先登录！”
                    ToastUtil.showToast(response.body().getErrorMsg());
                }
            }

            @Override
            public void onFailure(Call<WanCoinDetailBean> call, Throwable t) {

            }
        });
    }

    @Override
    protected void initView() {
        super.initView();
        mRecyclerView = getView(R.id.recycler_view);
        mDatas = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mAdapter = new WanCoinDetailAdapter(mDatas);
        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout = getView(R.id.refresh_layout);
        getData(page);

        mRefreshLayout.setEnableRefresh(true);//是否启用下拉刷新功能
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mDatas.clear();
                getData(0);
                mRefreshLayout.finishRefresh();
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getData(++page);
                mRefreshLayout.finishLoadMore();
            }
        });
    }
}
