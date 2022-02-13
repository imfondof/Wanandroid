package com.imfondof.wanandroid.ui.mine;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.imfondof.wanandroid.R;
import com.imfondof.wanandroid.ui.base.BaseFragment;
import com.imfondof.wanandroid.data.bean.WanCoinDetailBean;
import com.imfondof.wanandroid.data.http.HttpClient;
import com.imfondof.wanandroid.other.utils.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WanCoinDetailFrg extends BaseFragment {
    private WanCoinDetailAdapter mAdapter;
    private RecyclerView mRecyclerView;
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

    @Override
    protected void loadData() {
        showLoading();
        mRefreshLayout.autoRefresh();
    }

    public void getData(final int page) {
        Call<WanCoinDetailBean> call = HttpClient.Builder.getWanAndroidService().getCoinDetail(page);
        call.enqueue(new Callback<WanCoinDetailBean>() {
            @Override
            public void onResponse(Call<WanCoinDetailBean> call, Response<WanCoinDetailBean> response) {
                dissmissLoding();
                if (response.body().getErrorCode() == 0) {
                    if (response.body() != null
                            && response.body().getData() != null
                            && response.body().getData().getDatas() != null
                            && response.body().getData().getDatas().size() >= 0) {
                        if (page == 1) {
                            mAdapter.setNewData(response.body().getData().getDatas());
                        } else {
                            mAdapter.addData(response.body().getData().getDatas());
                        }
                    }
                } else if (response.body().getErrorCode() == -1001) {//getErrorCode为-1001时，提醒内容为“请先登录！”
                    ToastUtil.showToast(response.body().getErrorMsg());
                }
            }

            @Override
            public void onFailure(Call<WanCoinDetailBean> call, Throwable t) {
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
        mAdapter = new WanCoinDetailAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout = getView(R.id.refresh_layout);

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
}
