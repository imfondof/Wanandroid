package com.imfondof.wanandroid.ui.wanandroid;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.imfondof.wanandroid.R;
import com.imfondof.wanandroid.base.BaseFragment;

public class WanAndroidFragment extends BaseFragment {

    public static Fragment newInstance() {
        Bundle bundle = new Bundle();
        Fragment fragment = new WanAndroidFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int setContent() {
        return R.layout.frg_wanandroid;
    }
}
