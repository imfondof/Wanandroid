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
import com.imfondof.wanandroid.adapter.GankIOAdapter;
import com.imfondof.wanandroid.base.BaseFragment;
import com.imfondof.wanandroid.bean.GankIoDataBean;
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

public class GankIoFrg extends BaseFragment {
    public static String CATEGORY_ALL = "All";
    public static String CATEGORY_ARTICLE = "Article";
    public static String CATEGORY_GANHUO = "GanHuo";
    public static String CATEGORY_GRIL = "Girl";

    public static String TYPE_ALL = "All";
    public static String TYPE_ANDROID = "Android";
    public static String TYPE_IOS = "iOS";
    public static String TYPE_FLUTTER = "Flutter";
    public static String TYPE_GIRL = "Girl";

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private GankIOAdapter mAdapter;
    int page = 1;

    private static String category;
    private String type;

    public GankIoFrg(String category, String type) {
        this.category = category;
        this.type = type;
    }

//    public GankIoFrg() {
//    }
//
//    public static Fragment newInstance() {
//        Bundle bundle = new Bundle();
//        Fragment fragment = new GankIoFrg();
//        fragment.setArguments(bundle);
//        return fragment;
//    }

    public static Fragment newInstance(String category, String type) {
        Bundle bundle = new Bundle();
        Fragment fragment = new GankIoFrg(category, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void loadData() {
        showLoading();
        mRefreshLayout.autoRefresh();
    }

    @Override
    public int setContent() {
        return R.layout.frg_refresh_recyclerview;
    }

    public void getData(final int page) {
        HttpClient.Builder.getGankService().getGankIoData(category, type, page, 10).enqueue(new Callback<GankIoDataBean>() {
            @Override
            public void onResponse(Call<GankIoDataBean> call, Response<GankIoDataBean> response) {
                dissmissLoding();
                if (response.body() != null
                        && response.body().getData() != null
                        && response.body().getData().size() >= 0) {
                    if (page == 1) {
                        mAdapter.setNewData(response.body().getData());
                    } else {
                        mAdapter.addData(response.body().getData());
                    }
                }
            }

            @Override
            public void onFailure(Call<GankIoDataBean> call, Throwable t) {
                dissmissLoding();
            }
        });
    }

    @Override
    protected void initView() {
        super.initView();
        mRecyclerView = getView(R.id.recycler_view);
        mRefreshLayout = getView(R.id.refresh_layout);
        mAdapter = new GankIOAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (!TextUtils.isEmpty(mAdapter.getData().get(position).getUrl())) {
                    WebViewActivity.loadUrl(getActivity(), mAdapter.getData().get(position).getUrl(), mAdapter.getData().get(position).getTitle());
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
}
