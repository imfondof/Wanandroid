package com.imfondof.wanandroid.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.imfondof.wanandroid.R;
import com.imfondof.wanandroid.base.App;
import com.imfondof.wanandroid.bean.WanCoinRankBean;
import com.imfondof.wanandroid.utils.SPConstants;
import com.imfondof.wanandroid.utils.SPUtils;
import com.imfondof.wanandroid.utils.UserUtils;

import java.util.List;

public class WanCoinRankAdapter extends BaseQuickAdapter<WanCoinRankBean.DataBean.DatasBean, BaseViewHolder> {
    public WanCoinRankAdapter(List<WanCoinRankBean.DataBean.DatasBean> data) {
        super(R.layout.item_wan_coin_rank, data);
    }

    public WanCoinRankAdapter() {
        super(R.layout.item_wan_coin_rank);
    }

    @Override
    protected void convert(BaseViewHolder helper, WanCoinRankBean.DataBean.DatasBean item) {
        helper.setText(R.id.tv_rank, "" + item.getRank());
        helper.setText(R.id.tv_username, item.getUsername());
        helper.setText(R.id.tv_count, "" + item.getCoinCount());

        if (UserUtils.isLogin() && item.getUsername().length() > 3 && SPUtils.getString(SPConstants.USER_NAME, "Imfondof").length() > 3) {
            helper.setTextColor(R.id.tv_username,
                    TextUtils.equals(item.getUsername().substring(3), SPUtils.getString(SPConstants.USER_NAME, "Imfondof").substring(3)) ?
                            App.getInstance().getResources().getColor(R.color.colorTheme) :
                            App.getInstance().getResources().getColor(R.color.colorTitleGray));
        }
    }
}
