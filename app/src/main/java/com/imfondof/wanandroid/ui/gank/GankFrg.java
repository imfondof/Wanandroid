package com.imfondof.wanandroid.ui.gank;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.imfondof.wanandroid.R;
import com.imfondof.wanandroid.adapter.MyFragmentPagerAdapter;
import com.imfondof.wanandroid.base.BaseFragment;
import com.imfondof.wanandroid.ui.allFrgs.GankIoFrg;
import com.imfondof.wanandroid.ui.allFrgs.GankWelfareFrg;

import java.util.ArrayList;

public class GankFrg extends BaseFragment {
    private ViewPager mViewPager;
    private TabLayout mTab;

    private ArrayList<String> mTitleList = new ArrayList<>(4);
    private ArrayList<Fragment> mFragments = new ArrayList<>(4);
    private boolean mIsFirst = true;

    public static Fragment newInstance() {
        Bundle bundle = new Bundle();
        Fragment fragment = new GankFrg();
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
        mTitleList.add("android");
        mFragments.add(GankIoFrg.newInstance(GankIoFrg.CATEGORY_ALL, GankIoFrg.TYPE_ANDROID));
        mTitleList.add("flutter");
        mFragments.add(GankIoFrg.newInstance(GankIoFrg.CATEGORY_ALL, GankIoFrg.TYPE_FLUTTER));
        mTitleList.add("ios");
        mFragments.add(GankIoFrg.newInstance(GankIoFrg.CATEGORY_ALL, GankIoFrg.TYPE_IOS));
        mTitleList.add("福利");
        mFragments.add(GankWelfareFrg.newInstance());

        if (mTitleList.size() <= 1) {
            mTab.setVisibility(View.GONE);
        }
    }
}
