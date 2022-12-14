package com.imfondof.wanandroid.ui.mine;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.imfondof.wanandroid.R;
import com.imfondof.wanandroid.data.bean.WanCoinDetailBean;

import java.util.List;

public class WanCoinDetailAdapter extends BaseQuickAdapter<WanCoinDetailBean.DataBean.DatasBean, BaseViewHolder> {
    public WanCoinDetailAdapter(List<WanCoinDetailBean.DataBean.DatasBean> data) {
        super(R.layout.item_wan_coin_detail, data);
    }

    public WanCoinDetailAdapter() {
        super(R.layout.item_wan_coin_detail);
    }

    @Override
    protected void convert(BaseViewHolder helper, WanCoinDetailBean.DataBean.DatasBean item) {
        helper.setText(R.id.tv_reason, item.getReason());
        helper.setText(R.id.tv_desc, item.getDesc());
        helper.setText(R.id.tv_count, "+"+item.getCoinCount());
    }
}
