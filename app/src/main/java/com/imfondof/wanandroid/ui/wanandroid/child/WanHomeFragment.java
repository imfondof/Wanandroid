package com.imfondof.wanandroid.ui.wanandroid.child;

import androidx.fragment.app.Fragment;

import com.imfondof.wanandroid.R;
import com.imfondof.wanandroid.base.BaseFragment;

import java.util.ArrayList;

public class WanHomeFragment extends BaseFragment {

    private ArrayList<String> mTitleList = new ArrayList<>(4);
    private ArrayList<Fragment> mFragments = new ArrayList<>(4);

    @Override
    public int setContent() {
        return R.layout.frg_wan_home;
    }
}
