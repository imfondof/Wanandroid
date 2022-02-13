package com.imfondof.wanandroid.ui.index;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.imfondof.wanandroid.R;
import com.imfondof.wanandroid.ui.base.BaseFragment;
import com.imfondof.wanandroid.data.bean.WanHomeListBean;
import com.imfondof.wanandroid.data.http.HttpClient;
import com.imfondof.wanandroid.other.utils.SPUtils;
import com.imfondof.wanandroid.other.view.webView.WebViewActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WanHomeFrg extends BaseFragment {
    private WanHomeAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefreshLayout;
    private int page = 0;

    private int type = 0;//首页文章0 或者最新项目1

    public WanHomeFrg() {
    }

    public WanHomeFrg(int type) {
        this.type = type;
    }

    public static Fragment newInstance(int type) {
        Bundle bundle = new Bundle();
        WanHomeFrg fragment = new WanHomeFrg(type);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static Fragment newInstance() {
        Bundle bundle = new Bundle();
        WanHomeFrg fragment = new WanHomeFrg();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int setContent() {
        return R.layout.frg_wan_home;
    }

    @Override
    protected void initView() {
        super.initView();
        mRecyclerView = getView(R.id.recycler_view);
        mRefreshLayout = getView(R.id.refresh_layout);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mAdapter = new WanHomeAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public boolean onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                if (view.getId() == R.id.article) {
                    if (!TextUtils.isEmpty(mAdapter.getData().get(position).getLink())) {
                        WebViewActivity.loadUrl(getActivity(), mAdapter.getData().get(position).getLink(), mAdapter.getData().get(position).getTitle());
                    }
                }
                return true;
            }
        });
        getLocalData();
    }

    private void getLocalData() {
        WanHomeListBean data = SPUtils.getObjectCache("WanHomeFrg"+type, WanHomeListBean.class);
        if (data != null
                && data.getData() != null
                && data.getData().getDatas() != null) {
            mAdapter.setNewData(data.getData().getDatas());
        }
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

    @Override
    protected void loadData() {
        showLoading();
//        mRefreshLayout.autoRefresh();
    }

    public void getData(final int page) {
        Call<WanHomeListBean> call = type == 0 ?
                HttpClient.Builder.getWanAndroidService().getHomeList(page, null) :
                HttpClient.Builder.getWanAndroidService().getProhectList(page);
        call.enqueue(new Callback<WanHomeListBean>() {
            @Override
            public void onResponse(Call<WanHomeListBean> call, Response<WanHomeListBean> response) {
                dissmissLoding();
                if (response.body() != null
                        && response.body().getData() != null
                        && response.body().getData().getDatas() != null
                        && response.body().getData().getDatas().size() >= 0) {
                    if (page == 0) {
                        SPUtils.saveObjectCache("WanHomeFrg"+type, response.body());
                        mAdapter.setNewData(response.body().getData().getDatas());
                    } else {
                        mAdapter.addData(response.body().getData().getDatas());
                    }
                }
            }

            @Override
            public void onFailure(Call<WanHomeListBean> call, Throwable t) {
                dissmissLoding();
            }
        });
    }
}
