package com.imfondof.wanandroid.ui.allFrgs;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.imfondof.wanandroid.R;
import com.imfondof.wanandroid.adapter.WanCollectArticleAdapter;
import com.imfondof.wanandroid.base.BaseFragment;
import com.imfondof.wanandroid.bean.WanCollectArticleBean;
import com.imfondof.wanandroid.http.HttpClient;
import com.imfondof.wanandroid.http.HttpUtils;
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

public class WanCollectArticleFrg extends BaseFragment {
    List<WanCollectArticleBean.DataBean.DatasBean> mData;
    private WanCollectArticleAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefreshLayout;
    int page = 0;

    public static Fragment newInstance() {
        Bundle bundle = new Bundle();
        WanCollectArticleFrg fragment = new WanCollectArticleFrg();
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

    public void getData(final int page) {
        Call<WanCollectArticleBean> call = HttpClient.Builder.getWanAndroidService().getCollectArticle(page);
        call.enqueue(new Callback<WanCollectArticleBean>() {
            @Override
            public void onResponse(Call<WanCollectArticleBean> call, Response<WanCollectArticleBean> response) {
                dissmissLoding();
                if (response.body() != null
                        && response.body().getData() != null
                        && response.body().getData().getDatas() != null
                        && response.body().getData().getDatas().size() >= 0) {
                    if (page == 0) {
                        mData.clear();
                        mData = response.body().getData().getDatas();
                        mAdapter.setNewData(response.body().getData().getDatas());
                    } else {
                        mData.addAll(response.body().getData().getDatas());
                        mAdapter.addData(response.body().getData().getDatas());
                    }
                }
            }

            @Override
            public void onFailure(Call<WanCollectArticleBean> call, Throwable t) {
                dissmissLoding();
            }
        });
    }

    @Override
    protected void initView() {
        super.initView();
        mRecyclerView = getView(R.id.recycler_view);
        mRefreshLayout = getView(R.id.refresh_layout);
        mData = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mAdapter = new WanCollectArticleAdapter(mData);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (!TextUtils.isEmpty(mAdapter.getData().get(position).getLink())) {
                    WebViewActivity.loadUrl(getActivity(), mAdapter.getData().get(position).getLink(), mAdapter.getData().get(position).getTitle());
                }
                return true;
            }
        });

        mRefreshLayout.setEnableRefresh(true);//是否启用下拉刷新功能
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
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
