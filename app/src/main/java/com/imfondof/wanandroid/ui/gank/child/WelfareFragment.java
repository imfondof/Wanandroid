package com.imfondof.wanandroid.ui.gank.child;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.imfondof.wanandroid.R;
import com.imfondof.wanandroid.base.BaseFragment;

public class WelfareFragment extends BaseFragment {

    public static Fragment newInstance() {
        Bundle bundle = new Bundle();
        Fragment fragment = new WelfareFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int setContent() {
        return R.layout.frg_gank_welfare;
    }

    private void loadWelfareData() {

    }
}
