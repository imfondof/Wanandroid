package com.imfondof.wanandroid.ui.system;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.imfondof.wanandroid.R;
import com.imfondof.wanandroid.data.bean.SystemResult;


public class SystemAdapter extends BaseSectionQuickAdapter<SystemResult.DataBean, BaseViewHolder> {
    public SystemAdapter() {
        super(R.layout.item_system_content, R.layout.item_system_title, null);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, SystemResult.DataBean item) {
        helper.setText(R.id.system_title_tv, item.name);
    }

    @Override
    protected void convert(BaseViewHolder helper, SystemResult.DataBean item) {
        helper.setText(R.id.system_content_tv, ""+item.id);
    }
}
