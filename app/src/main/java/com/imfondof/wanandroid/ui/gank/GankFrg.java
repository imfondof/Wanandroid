package com.imfondof.wanandroid.ui.gank;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
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
    private boolean mIsPrepared;

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadData();
        if (mTitleList.size() <= 1) {
            mTab.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initView() {
        super.initView();
        mTab = getView(R.id.tab);
//        mTab.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, mTab.getHeight()));
        mViewPager = getView(R.id.vp);
    }

    @Override
    protected void loadData() {
        initFragmentList();
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getChildFragmentManager(), mFragments, mTitleList);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(3);
        adapter.notifyDataSetChanged();
        mTab.setupWithViewPager(mViewPager);
    }

    private void initFragmentList() {
        mTitleList.add("android");
        mFragments.add(GankIoFrg.newInstance(GankIoFrg.CATEGORY_ALL, GankIoFrg.TYPE_ANDROID));

//        mTitleList.add("ios");
//        mFragments.add(GankIoFrg.newInstance(GankIoFrg.CATEGORY_ALL, GankIoFrg.TYPE_IOS));

        mTitleList.add("flutter");
        mFragments.add(GankIoFrg.newInstance(GankIoFrg.CATEGORY_ALL, GankIoFrg.TYPE_FLUTTER));
    }
}