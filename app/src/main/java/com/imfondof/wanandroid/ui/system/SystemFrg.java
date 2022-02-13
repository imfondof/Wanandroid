package com.imfondof.wanandroid.ui.system;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.imfondof.wanandroid.R;
import com.imfondof.wanandroid.data.bean.SystemResult;
import com.imfondof.wanandroid.data.bean.WanHomeListBean;
import com.imfondof.wanandroid.data.http.HttpClient;
import com.imfondof.wanandroid.other.utils.ListUtil;
import com.imfondof.wanandroid.other.utils.SPUtils;
import com.imfondof.wanandroid.ui.base.MyFragmentPagerAdapter;
import com.imfondof.wanandroid.ui.base.BaseFragment;
import com.imfondof.wanandroid.ui.index.WanHomeFrg;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SystemFrg extends BaseFragment {
    private TextView titleTv;
    private RecyclerView systemRv;
    private SystemAdapter systemAdapter;

    public static Fragment newInstance() {
        Bundle bundle = new Bundle();
        Fragment fragment = new SystemFrg();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int setContent() {
        return R.layout.frg_system;
    }

    @Override
    protected void initView() {
        super.initView();
        titleTv = getView(R.id.title_tv);
        titleTv.setText("体系");
        systemRv = getView(R.id.recycler_view);

        getSystem();

        systemAdapter = new SystemAdapter();
        systemRv.setLayoutManager(new LinearLayoutManager(getContext()));
        systemRv.setAdapter(systemAdapter);
    }

    public void getSystem() {
        Call<SystemResult> call = HttpClient.Builder.getWanAndroidService().getSystem();
        call.enqueue(new Callback<SystemResult>() {
            @Override
            public void onResponse(Call<SystemResult> call, Response<SystemResult> response) {
                systemAdapter.setNewData(response.body().data);
                dissmissLoding();
            }

            @Override
            public void onFailure(Call<SystemResult> call, Throwable t) {
                dissmissLoding();
            }
        });
    }
}
