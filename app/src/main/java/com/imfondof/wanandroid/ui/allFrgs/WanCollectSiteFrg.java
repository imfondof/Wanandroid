package com.imfondof.wanandroid.ui.allFrgs;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

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
import com.imfondof.wanandroid.http.HttpUtils;
import com.imfondof.wanandroid.view.webView.WebViewActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WanCollectSiteFrg extends BaseFragment {
    private WanCollectSiteAdapter mAdapter;
    private RecyclerView mRecyclerView;
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

    @Override
    protected void loadData() {
        showLoading();
        mRefreshLayout.autoRefresh();
    }

    public void getData() {
        Call<WanCollectSiteBean> call = HttpClient.Builder.getWanAndroidService().getCollectSite();
        call.enqueue(new Callback<WanCollectSiteBean>() {
            @Override
            public void onResponse(Call<WanCollectSiteBean> call, Response<WanCollectSiteBean> response) {
                dissmissLoding();
                if (response.body() != null
                        && response.body().getData() != null
                        && response.body().getData().size() >= 0) {
                    Collections.reverse(response.body().getData());
                    mAdapter.setNewData(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<WanCollectSiteBean> call, Throwable t) {
                dissmissLoding();
            }
        });
    }

    @Override
    protected void initView() {
        super.initView();
        mRecyclerView = getView(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mAdapter = new WanCollectSiteAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (!TextUtils.isEmpty(mAdapter.getData().get(position).getLink())) {
                    WebViewActivity.loadUrl(getActivity(), mAdapter.getData().get(position).getLink(), mAdapter.getData().get(position).getName());
                }
                return true;
            }
        });
        mRefreshLayout = getView(R.id.refresh_layout);

        mRefreshLayout.setEnableRefresh(false);//是否启用下拉刷新功能
        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setNoMoreData(true);
        mRefreshLayout.setEnableFooterFollowWhenNoMoreData(true);
    }
}
