package com.imfondof.wanandroid.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.imfondof.wanandroid.R;
import com.imfondof.wanandroid.bean.WanCollectSiteBean;

import java.util.List;

public class WanCollectSiteAdapter extends BaseQuickAdapter<WanCollectSiteBean.DataBean, BaseViewHolder> {
    public WanCollectSiteAdapter(List<WanCollectSiteBean.DataBean> data) {
        super(R.layout.item_wan_collect_site, data);
    }

    public WanCollectSiteAdapter() {
        super(R.layout.item_wan_collect_site);
    }

    @Override
    protected void convert(BaseViewHolder helper, WanCollectSiteBean.DataBean item) {
        helper.setText(R.id.tv_collect_site, item.getName());
        helper.addOnClickListener(R.id.tv_collect_site);
    }
}
