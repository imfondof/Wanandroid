package com.imfondof.wanandroid.ui.index;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.imfondof.wanandroid.R;
import com.imfondof.wanandroid.other.utils.StatusBarUtil;
import com.imfondof.wanandroid.ui.base.BaseFragment;
import com.imfondof.wanandroid.ui.base.MyFragmentPagerAdapter;
import com.imfondof.wanandroid.ui.find.WanQAFrg;
import com.imfondof.wanandroid.ui.index.frg.WanHomeFragment;
import com.imfondof.wanandroid.ui.index.frg.WanSquareFrg;

import java.util.ArrayList;

public class IndexFrg extends BaseFragment {
    private ViewPager mViewPager;
    private TabLayout mTab;
    private View fakeStatusBar;

    private ArrayList<String> mTitleList = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    public static Fragment newInstance() {
        Bundle bundle = new Bundle();
        Fragment fragment = new IndexFrg();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int setContent() {
        return R.layout.frg_index;
    }

    @Override
    protected void initView() {
        super.initView();

        mTab = getView(R.id.tab);
        mViewPager = getView(R.id.vp);
        fakeStatusBar = getView(R.id.fake_status_bar);

        if (StatusBarUtil.enableCompat()) {
            StatusBarUtil.statusBarLightMode(getActivity(), true);
            StatusBarUtil.transparentStatusbarAndLayoutInsert(getActivity(), true);
        }

        initFragmentList();
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getChildFragmentManager(), mFragments, mTitleList);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(mTitleList.size());
        adapter.notifyDataSetChanged();
        mTab.setupWithViewPager(mViewPager);
    }

    private void initFragmentList() {
        mTitleList.add("首页");
        mFragments.add(new WanHomeFragment());
        mTitleList.add("广场");
        mFragments.add(WanSquareFrg.newInstance());
        mTitleList.add("问答");
        mFragments.add(new WanQAFrg());

        if (mTitleList.size() == 1) {
            mTab.setVisibility(View.GONE);
        }
    }
}
