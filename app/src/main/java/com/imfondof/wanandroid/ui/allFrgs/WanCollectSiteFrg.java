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
import com.imfondof.wanandroid.adapter.WanCollectSiteAdapter;
import com.imfondof.wanandroid.base.BaseFragment;
import com.imfondof.wanandroid.bean.WanCollectSiteBean;
import com.imfondof.wanandroid.http.HttpClient;
import com.imfondof.wanandroid.view.webView.WebViewActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WanCollectSiteFrg extends BaseFragment {
    private WanCollectSiteAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private List<WanCollectSiteBean.DataBean> mDatas;
    private SmartRefreshLayout mRefreshLayout;

    public static Fragment newInstance() {
        Bundle bundle = new Bundle();
        WanCollectSiteFrg fragment = new WanCollectSiteFrg();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int setContent() {
        return R.layout.frg_refresh_recyclerview;
    }

    public void getData() {
        Call<WanCollectSiteBean> call = HttpClient.Builder.getWanAndroidService().getCollectSite();
        call.enqueue(new Callback<WanCollectSiteBean>() {
            @Override
            public void onResponse(Call<WanCollectSiteBean> call, Response<WanCollectSiteBean> response) {
                if (response != null) {
                    Collections.reverse(response.body().getData());
                    mDatas.addAll(response.body().getData());
                    mAdapter.setNewData(mDatas);
                }
            }

            @Override
            public void onFailure(Call<WanCollectSiteBean> call, Throwable t) {

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
        mAdapter = new WanCollectSiteAdapter(mDatas);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (!TextUtils.isEmpty(mDatas.get(position).getLink())) {
                    WebViewActivity.loadUrl(getActivity(), mDatas.get(position).getLink(), mDatas.get(position).getName());
                }
                return true;
            }
        });
        mRefreshLayout = getView(R.id.refresh_layout);
        getData();

        mRefreshLayout.setEnableRefresh(true);//是否启用下拉刷新功能
        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mDatas.clear();
                getData();
                mRefreshLayout.finishRefresh();
            }
        });
    }
}
