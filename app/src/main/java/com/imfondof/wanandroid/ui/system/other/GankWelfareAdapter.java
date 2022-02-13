package com.imfondof.wanandroid.ui.system.other;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.imfondof.wanandroid.R;
import com.imfondof.wanandroid.data.bean.GankIoDataBean;

import java.util.List;

public class GankWelfareAdapter extends BaseQuickAdapter<GankIoDataBean.DataBean, BaseViewHolder> {

    public GankWelfareAdapter(List<GankIoDataBean.DataBean> data) {
        super(R.layout.item_gank_welfare, data);
    }

    public GankWelfareAdapter() {
        super(R.layout.item_gank_welfare);
    }

    @Override
    protected void convert(BaseViewHolder helper, GankIoDataBean.DataBean data) {
        Glide.with(mContext)
                .load(data.getUrl())
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .centerCrop()
                .into((ImageView) helper.getView(R.id.iv_welfare));

        helper.addOnClickListener(R.id.iv_welfare);
    }
}
