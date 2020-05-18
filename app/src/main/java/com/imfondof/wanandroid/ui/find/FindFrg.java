package com.imfondof.wanandroid.ui.find;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.imfondof.wanandroid.R;
import com.imfondof.wanandroid.base.BaseFragment;
import com.imfondof.wanandroid.ui.allActs.EmptyAct;
import com.imfondof.wanandroid.utils.ToastUtil;

public class FindFrg extends BaseFragment {

    public static Fragment newInstance() {
        Bundle bundle = new Bundle();
        Fragment fragment = new FindFrg();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int setContent() {
        return R.layout.frg_find;
    }

    @Override
    protected void initView() {
        super.initView();
        getView(R.id.tv_qa).setOnClickListener(this);
        getView(R.id.tv_welfare).setOnClickListener(this);
        getView(R.id.tv_square).setOnClickListener(this);
        getView(R.id.tv_more).setOnClickListener(this);
        getView(R.id.tv_view).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_qa:
                EmptyAct.jump(getActivity(), EmptyAct.WAN_QA_TYPE, EmptyAct.WAN_QA_TITLE);
                break;
            case R.id.tv_welfare:
                EmptyAct.jump(getActivity(), EmptyAct.GANK_WELFARE_TYPE, EmptyAct.GANK_WELFARE_TITLE);
                break;
            case R.id.tv_square:
                EmptyAct.jump(getActivity(), EmptyAct.WAN_SQUARE_TYPE, EmptyAct.WAN_SQUARE_TITLE);
                break;
            case R.id.tv_view:
                ToastUtil.showToast("自定义view");
                break;
            default:
                break;
        }
    }
}
