package com.imfondof.wanandroid.ui.allFrgs;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.imfondof.wanandroid.R;
import com.imfondof.wanandroid.adapter.WanHomeAdapter;
import com.imfondof.wanandroid.adapter.WanQAAdapter;
import com.imfondof.wanandroid.base.BaseFragment;
import com.imfondof.wanandroid.bean.WanQABean;
import com.imfondof.wanandroid.http.HttpClient;
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

/**
 * wanandroid 问答模块
 */

public class WanQAFrg extends BaseFragment {
    private WanQAAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private List<WanQABean.DataBean.DatasBean> mDatas;
    private SmartRefreshLayout mRefreshLayout;
    int page = 0;

    @Override
    public int setContent() {
        return R.layout.frg_refresh_recyclerview;
    }

    @Override
    protected void initView() {
        super.initView();
        mRecyclerView = getView(R.id.recycler_view);
        mDatas = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mAdapter = new WanQAAdapter(mDatas);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (!TextUtils.isEmpty(mDatas.get(position).getLink())) {
                    WebViewActivity.loadUrl(getActivity(), mDatas.get(position).getLink(), mDatas.get(position).getTitle());
                }
                return true;
            }
        });
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

    private void getData(int page) {
        Call<WanQABean> call = HttpClient.Builder.getWanAndroidService().getQA(page);
        call.enqueue(new Callback<WanQABean>() {
            @Override
            public void onResponse(Call<WanQABean> call, Response<WanQABean> response) {
                mDatas.addAll(response.body().getData().getDatas());
                mAdapter.setNewData(mDatas);
            }

            @Override
            public void onFailure(Call<WanQABean> call, Throwable t) {

            }
        });
    }
}