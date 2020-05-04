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
import com.imfondof.wanandroid.view.webView.WebViewActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

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
    private ArrayList<GankIoDataBean.DataBean> mDatas = new ArrayList<>();
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
    public int setContent() {
        return R.layout.frg_refresh_recyclerview;
    }

    public void getData(int page) {
        Call<GankIoDataBean> call = HttpClient.Builder.getGankService().getGankIoData(category, type, page, 10);
        call.enqueue(new Callback<GankIoDataBean>() {
            @Override
            public void onResponse(Call<GankIoDataBean> call, Response<GankIoDataBean> response) {
                if (response.body().getData() != null) {
                    mDatas.addAll(response.body().getData());
                    mAdapter.setNewData(mDatas);
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
        mAdapter = new GankIOAdapter(mDatas);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (!TextUtils.isEmpty(mDatas.get(position).getUrl())) {
                    WebViewActivity.loadUrl(getActivity(), mDatas.get(position).getUrl(), mDatas.get(position).getTitle());
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
