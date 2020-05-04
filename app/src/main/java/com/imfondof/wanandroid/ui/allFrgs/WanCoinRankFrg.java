package com.imfondof.wanandroid.ui.allFrgs;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.imfondof.wanandroid.R;
import com.imfondof.wanandroid.adapter.WanCoinRankAdapter;
import com.imfondof.wanandroid.base.BaseFragment;
import com.imfondof.wanandroid.bean.WanCoinRankBean;
import com.imfondof.wanandroid.http.HttpClient;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WanCoinRankFrg extends BaseFragment {
    private WanCoinRankAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private List<WanCoinRankBean.DataBean.DatasBean> mDatas;
    private SmartRefreshLayout mRefreshLayout;
    int page = 1;

    public static Fragment newInstance() {
        Bundle bundle = new Bundle();
        WanCoinRankFrg fragment = new WanCoinRankFrg();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int setContent() {
        return R.layout.frg_refresh_recyclerview;
    }

    public void getData(int page) {
        Call<WanCoinRankBean> call = HttpClient.Builder.getWanAndroidService().getCoinRank(page);
        call.enqueue(new Callback<WanCoinRankBean>() {
            @Override
            public void onResponse(Call<WanCoinRankBean> call, Response<WanCoinRankBean> response) {
                if (response.body() != null && response.body().getData() != null) {
                    mDatas.addAll(response.body().getData().getDatas());
                    mAdapter.setNewData(mDatas);
                }
            }

            @Override
            public void onFailure(Call<WanCoinRankBean> call, Throwable t) {

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
        mAdapter = new WanCoinRankAdapter(mDatas);
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
