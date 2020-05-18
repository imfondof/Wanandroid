package com.imfondof.wanandroid.ui.allFrgs;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.imfondof.wanandroid.R;
import com.imfondof.wanandroid.adapter.WanShareArticleAdapter;
import com.imfondof.wanandroid.base.BaseFragment;
import com.imfondof.wanandroid.bean.WanShareArticleBean;
import com.imfondof.wanandroid.http.HttpClient;
import com.imfondof.wanandroid.http.HttpUtils;
import com.imfondof.wanandroid.view.webView.WebViewActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WanShareArticlesFrg extends BaseFragment {
    private WanShareArticleAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefreshLayout;
    int page = 0;

    public static Fragment newInstance() {
        Bundle bundle = new Bundle();
        WanShareArticlesFrg fragment = new WanShareArticlesFrg();
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
        Call<WanShareArticleBean> call = HttpClient.Builder.getWanAndroidService().getShareArticle(page);
        call.enqueue(new Callback<WanShareArticleBean>() {
            @Override
            public void onResponse(Call<WanShareArticleBean> call, Response<WanShareArticleBean> response) {
                dissmissLoding();
                if (response.body() != null
                        && response.body().getData() != null
                        && response.body().getData().getShareArticles() != null
                        && response.body().getData().getShareArticles().getSize() >= 0) {
                    if (page == 0) {
                        mAdapter.setNewData(response.body().getData().getShareArticles().getDatas());
                    } else {
                        mAdapter.addData(response.body().getData().getShareArticles().getDatas());
                    }
                }
            }

            @Override
            public void onFailure(Call<WanShareArticleBean> call, Throwable t) {
                dissmissLoding();
            }
        });
    }

    @Override
    protected void initView() {
        super.initView();
        mRecyclerView = getView(R.id.recycler_view);
        mRefreshLayout = getView(R.id.refresh_layout);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new WanShareArticleAdapter();
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
        if (mAdapter.getData().size() > 0 && mAdapter.getData().size() < 10) {
            mRefreshLayout.setEnableLoadMore(false);
        }else {
            mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                    getData(++page);
                    mRefreshLayout.finishLoadMore();
                }
            });
        }
    }
}
