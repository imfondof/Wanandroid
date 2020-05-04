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
import com.imfondof.wanandroid.adapter.WanHomeAdapter;
import com.imfondof.wanandroid.base.BaseFragment;
import com.imfondof.wanandroid.bean.WanHomeListBean;
import com.imfondof.wanandroid.http.HttpClient;
import com.imfondof.wanandroid.utils.CollectUtils;
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

public class WanHomeFrg extends BaseFragment {
    private WanHomeAdapter mWanHomeAdapter;
    private RecyclerView mRecyclerView;
    private List<WanHomeListBean.DataBean.DatasBean> mDatas;
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

    public void getData(int page) {
        Call<WanHomeListBean> call = type == 0 ?
                HttpClient.Builder.getWanAndroidService().getHomeList(page, null) :
                HttpClient.Builder.getWanAndroidService().getProhectList(page);
        call.enqueue(new Callback<WanHomeListBean>() {
            @Override
            public void onResponse(Call<WanHomeListBean> call, Response<WanHomeListBean> response) {
                if (response != null) {
                    mDatas.addAll(response.body().getData().getDatas());
                    mWanHomeAdapter.setNewData(mDatas);
                }
            }

            @Override
            public void onFailure(Call<WanHomeListBean> call, Throwable t) {

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
        mWanHomeAdapter = new WanHomeAdapter(mDatas);
        mRecyclerView.setAdapter(mWanHomeAdapter);
        mWanHomeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public boolean onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                if (view.getId() == R.id.article) {
                    if (!TextUtils.isEmpty(mDatas.get(position).getLink())) {
                        WebViewActivity.loadUrl(getActivity(), mDatas.get(position).getLink(), mDatas.get(position).getTitle());
                    }
                }
//                else if (view.getId() == R.id.vb_collect) {
//                    CollectUtils.instance().collect(mDatas.get(position).getId(), new CollectUtils.OnCollectListener() {
//                        @Override
//                        public void onSuccess() {
//                            mDatas.get(position).setCollect(mDatas.get(position).isCollect());
//                        }
//
//                        @Override
//                        public void onFailure() {
//
//                        }
//                    });
//                }
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
}
