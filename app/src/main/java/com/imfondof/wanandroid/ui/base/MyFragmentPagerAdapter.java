package com.imfondof.wanandroid.ui.base;

import android.text.Html;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

public class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private List<?> mFragment;
    private List<String> mTitleList;

    /**
     * 普通，主页使用
     */
    public MyFragmentPagerAdapter(FragmentManager fm, List<?> mFragment) {
        super(fm);
        this.mFragment = mFragment;
    }

    /**
     * 接收首页传递的标题
     */
    public MyFragmentPagerAdapter(FragmentManager fm, List<?> mFragment, List<String> mTitleList) {
        super(fm);
        this.mFragment = mFragment;
        this.mTitleList = mTitleList;
    }

    /**
     * 接收首页传递的标题
     */
    public void setData(List<?> mFragment, List<String> mTitleList) {
        this.mFragment = mFragment;
        this.mTitleList = mTitleList;
    }

    @Override
    public Fragment getItem(int position) {
        return (Fragment) mFragment.get(position);
    }

    @Override
    public int getCount() {
        return mFragment.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    /**
     * 首页显示title，每日推荐等..
     * 若有问题，移到对应单独页面
     */
    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitleList != null && position < mTitleList.size()) {
            return Html.fromHtml(mTitleList.get(position));
        } else {
            return "";
        }
    }

    public void addFragmentList(List<?> fragment) {
        this.mFragment.clear();
        this.mFragment = null;
        this.mFragment = fragment;
        notifyDataSetChanged();
    }
}
