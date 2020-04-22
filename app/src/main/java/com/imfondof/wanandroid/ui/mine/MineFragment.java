package com.imfondof.wanandroid.ui.mine;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import com.imfondof.wanandroid.R;
import com.imfondof.wanandroid.base.BaseFragment;

public class MineFragment extends BaseFragment {

    public static Fragment newInstance() {
        Bundle bundle = new Bundle();
        Fragment fragment = new MineFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int setContent() {
        return R.layout.frg_mine;
    }
}
