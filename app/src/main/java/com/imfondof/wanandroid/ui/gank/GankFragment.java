package com.imfondof.wanandroid.ui.gank;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.imfondof.wanandroid.R;
import com.imfondof.wanandroid.adapter.MyFragmentPagerAdapter;
import com.imfondof.wanandroid.base.BaseFragment;
import com.imfondof.wanandroid.ui.gank.child.WelfareFragment;

import java.util.ArrayList;

public class GankFragment extends BaseFragment {

    private ViewPager mViewPager;
    private TabLayout mTab;

    private ArrayList<String> mTitleList = new ArrayList<>(4);
    private ArrayList<Fragment> mFragments = new ArrayList<>(4);
    private boolean mIsFirst = true;
    private boolean mIsPrepared;

    public static Fragment newInstance() {
        Bundle bundle = new Bundle();
        Fragment fragment = new GankFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int setContent() {
        return R.layout.frg_gank;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadData();
    }

    @Override
    protected void findView(View view) {
        mTab = view.findViewById(R.id.tab_gank);
        mViewPager = view.findViewById(R.id.vp_gank);
    }

    @Override
    protected void loadData() {
        initFragmentList();
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getChildFragmentManager(), mFragments, mTitleList);
        mViewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        mTab.setupWithViewPager(mViewPager);
    }

    private void initFragmentList() {
        mTitleList.add("福利");
        mTitleList.add("福利");
        mFragments.add(WelfareFragment.newInstance());
        mFragments.add(WelfareFragment.newInstance());
    }
}
