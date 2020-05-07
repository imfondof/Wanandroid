package com.imfondof.wanandroid.ui.allFrgs;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.imfondof.wanandroid.R;
import com.imfondof.wanandroid.adapter.GankWelfareAdapter;
import com.imfondof.wanandroid.base.BaseFragment;
import com.imfondof.wanandroid.bean.GankIoDataBean;
import com.imfondof.wanandroid.http.HttpClient;
import com.imfondof.wanandroid.view.bigImage.ViewBigImageActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GankWelfareFrg extends BaseFragment {

    private SmartRefreshLayout mRefreshLayout;
    private ArrayList<String> imgList = new ArrayList<>();
    private ArrayList<String> imgTitleList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    GankWelfareAdapter mAdapter;
    int page = 1;

    public static Fragment newInstance() {
        Bundle bundle = new Bundle();
        Fragment fragment = new GankWelfareFrg();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int setContent() {
        return R.layout.frg_gank_welfare;
    }

    public void getData(final int page) {
        Call<GankIoDataBean> call = HttpClient.Builder.getGankService().getGankIoData("Girl", "Girl", page, 10);
        call.enqueue(new Callback<GankIoDataBean>() {
            @Override
            public void onResponse(Call<GankIoDataBean> call, Response<GankIoDataBean> response) {
                if (response.body() != null
                        && response.body().getData() != null
                        && response.body().getData().size() > 0) {
                    if (page == 0) {
                        mAdapter.setNewData(response.body().getData());
                    } else {
                        mAdapter.addData(response.body().getData());
                    }
                }
            }

            @Override
            public void onFailure(Call<GankIoDataBean> call, Throwable t) {
            }
        });
    }


    @Override
    protected void initView() {
        super.initView();
        mRecyclerView = getView(R.id.recycler_view);
        mAdapter = new GankWelfareAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setHasFixedSize(true);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                imgList.clear();
                imgTitleList.clear();
                for (int i = 0; i < mAdapter.getData().size(); i++) {
                    imgList.add(mAdapter.getData().get(i).getUrl());
                    imgTitleList.add(mAdapter.getData().get(i).getTitle());
                }
                ViewBigImageActivity.startImageList(getContext(), position, imgList, imgTitleList);
                return true;
            }
        });

        mRefreshLayout = getView(R.id.refresh_layout);
        getData(page);
        mRefreshLayout.setEnableRefresh(false);//是否启用下拉刷新功能
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
