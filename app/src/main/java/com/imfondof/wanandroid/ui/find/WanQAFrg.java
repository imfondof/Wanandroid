package com.imfondof.wanandroid.ui.find;

import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.imfondof.wanandroid.R;
import com.imfondof.wanandroid.data.bean.WanQABean;
import com.imfondof.wanandroid.data.http.HttpClient;
import com.imfondof.wanandroid.other.view.webView.WebViewActivity;
import com.imfondof.wanandroid.ui.base.BaseFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * wanandroid 问答模块
 */
public class WanQAFrg extends BaseFragment {
    private WanQAAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefreshLayout;
    int page = 1;

    @Override
    public int setContent() {
        return R.layout.frg_refresh_recyclerview;
    }

    @Override
    protected void loadData() {
        showLoading();
        mRefreshLayout.autoRefresh();
    }

    @Override
    protected void initView() {
        super.initView();
        mRecyclerView = getView(R.id.recycler_view);
        mRefreshLayout = getView(R.id.refresh_layout);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mAdapter = new WanQAAdapter();
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
                getData(1);
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

    private void getData(final int page) {
        Call<WanQABean> call = HttpClient.Builder.getWanAndroidService().getQA(page);
        call.enqueue(new Callback<WanQABean>() {
            @Override
            public void onResponse(Call<WanQABean> call, Response<WanQABean> response) {
                dissmissLoding();
                if (response.body() != null
                        && response.body().getData() != null
                        && response.body().getData().getDatas() != null
                        && response.body().getData().getDatas().size() >= 0) {
                    if (page == 0) {
                        mAdapter.setNewData(response.body().getData().getDatas());
                    } else {
                        mAdapter.addData(response.body().getData().getDatas());
                    }
                }
            }

            @Override
            public void onFailure(Call<WanQABean> call, Throwable t) {
                dissmissLoding();
            }
        });
    }
}
