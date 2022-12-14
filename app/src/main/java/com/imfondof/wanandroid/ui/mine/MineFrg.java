package com.imfondof.wanandroid.ui.mine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.imfondof.wanandroid.R;
import com.imfondof.wanandroid.other.utils.EasyDataStore;
import com.imfondof.wanandroid.ui.base.App;
import com.imfondof.wanandroid.ui.base.BaseFragment;
import com.imfondof.wanandroid.data.bean.WanCoinBean;
import com.imfondof.wanandroid.data.http.HttpClient;
import com.imfondof.wanandroid.ui.base.EmptyAct;
import com.imfondof.wanandroid.other.utils.SPConstants;
import com.imfondof.wanandroid.other.utils.SPUtils;
import com.imfondof.wanandroid.other.utils.ToastUtil;
import com.imfondof.wanandroid.other.utils.UserUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MineFrg extends BaseFragment {
    private TextView usernameTv;
    private TextView coinTv;
    private RelativeLayout coinRv;
    private MyBroadCastReceiver myBroadCastReceiver;

    public static Fragment newInstance() {
        Bundle bundle = new Bundle();
        Fragment fragment = new MineFrg();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int setContent() {
        return R.layout.frg_mine;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.to_login:
                startActivity(new Intent(getActivity(), WanLoginAct.class));
                break;
            case R.id.rv_coin:
                if (UserUtils.isLogin()) {
                    EmptyAct.jump(getActivity(), EmptyAct.WAN_COIN_TYPE, EmptyAct.WAN_COIN_TITLE);
                } else {
                    ToastUtil.showToast("请登录wanandroid");
                }
                break;
            case R.id.tv_collect:
                if (UserUtils.isLogin()) {
                    EmptyAct.jump(getActivity(), EmptyAct.WAN_COLLECT_TYPE, EmptyAct.WAN_COLLECT_TITLE);
                } else {
                    ToastUtil.showToast("请登录wanandroid");
                }
                break;
            case R.id.tv_share:
                if (UserUtils.isLogin()) {
                    EmptyAct.jump(getActivity(), EmptyAct.WAN_SHARE_TYPE, EmptyAct.WAN_SHARE_TITLE);
                } else {
                    ToastUtil.showToast("请登录wanandroid");
                }
                break;
            case R.id.tv_settings:
                ToastUtil.showToast("感谢wanandroid提供的接口；\n感谢gankio提供的接口；\n感谢开源项目cloudreader；\n敬请期待功能的完善！");
                break;
        }
    }

    @Override
    protected void initView() {
        initUser();

        getView(R.id.to_login).setOnClickListener(this);
        getView(R.id.rv_coin).setOnClickListener(this);
        getView(R.id.tv_share).setOnClickListener(this);
        getView(R.id.tv_collect).setOnClickListener(this);
        getView(R.id.tv_settings).setOnClickListener(this);

        //注册广播，监听登录
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("user");
        myBroadCastReceiver = new MyBroadCastReceiver();
        App.getInstance().getApplicationContext().registerReceiver(myBroadCastReceiver, intentFilter);
    }

    private void initUser() {
        coinTv = getView(R.id.tv_coin);
        coinTv.setText(EasyDataStore.INSTANCE.getData("COIN_TEXT", ""));
        usernameTv = getView(R.id.to_login);
        if (UserUtils.isLogin()) {
            coinTv.setVisibility(View.VISIBLE);
            Call<WanCoinBean> call = HttpClient.Builder.getWanAndroidService().getCoin();
            usernameTv.setText(SPUtils.getString(SPConstants.USER_NAME, "请登录"));
            call.enqueue(new Callback<WanCoinBean>() {
                @Override
                public void onResponse(Call<WanCoinBean> call, Response<WanCoinBean> response) {
                    if (response.body() != null && response.body().getData() != null) {
                        EasyDataStore.INSTANCE.putData("COIN_TEXT", response.body().getData().getCoinCount() + "  I  " + response.body().getData().getRank());
                        coinTv.setText(EasyDataStore.INSTANCE.getData("COIN_TEXT", ""));
                    }
                }

                public void onFailure(Call<WanCoinBean> call, Throwable t) {
                }
            });
        }
    }

    class MyBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            usernameTv.setText(SPUtils.getString(SPConstants.USER_NAME, "请登录"));
            initUser();
        }
    }
}
