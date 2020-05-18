package com.imfondof.wanandroid.ui.wanandroid;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.imfondof.wanandroid.R;
import com.imfondof.wanandroid.adapter.MyFragmentPagerAdapter;
import com.imfondof.wanandroid.base.BaseFragment;
import com.imfondof.wanandroid.ui.allFrgs.WanHomeFrg;
import com.imfondof.wanandroid.ui.allFrgs.WanQAFrg;
import com.imfondof.wanandroid.ui.allFrgs.WanSquareFrg;

import java.util.ArrayList;

public class WanAndroidFrg extends BaseFragment {
    private ViewPager mViewPager;
    private TabLayout mTab;

    private ArrayList<String> mTitleList = new ArrayList<>(4);
    private ArrayList<Fragment> mFragments = new ArrayList<>(4);

    public static Fragment newInstance() {
        Bundle bundle = new Bundle();
        Fragment fragment = new WanAndroidFrg();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int setContent() {
        return R.layout.frg_tab_viewpager;
    }

    @Override
    protected void initView() {
        super.initView();
        mTab = getView(R.id.tab);
        mViewPager = getView(R.id.vp);

        initFragmentList();
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getChildFragmentManager(), mFragments, mTitleList);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(mTitleList.size());
        adapter.notifyDataSetChanged();
        mTab.setupWithViewPager(mViewPager);
    }

    private void initFragmentList() {
        mTitleList.add("最新博文");
        mFragments.add(WanHomeFrg.newInstance());
        mTitleList.add("广场");
        mFragments.add(WanSquareFrg.newInstance());
        mTitleList.add("问答");
        mFragments.add(new WanQAFrg());
        mTitleList.add("最新项目");
        mFragments.add(WanHomeFrg.newInstance(1));

        if (mTitleList.size() == 1) {
            mTab.setVisibility(View.GONE);
        }
    }
}
