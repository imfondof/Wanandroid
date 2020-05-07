package com.imfondof.wanandroid.adapter;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.imfondof.wanandroid.R;
import com.imfondof.wanandroid.bean.GankIoDataBean;
import com.imfondof.wanandroid.utils.GlideUtil;

import java.util.List;

public class GankIOAdapter extends BaseQuickAdapter<GankIoDataBean.DataBean, BaseViewHolder> {

    public GankIOAdapter(List<GankIoDataBean.DataBean> data) {
        super(R.layout.item_gank_io, data);
    }

    public GankIOAdapter() {
        super(R.layout.item_gank_io);
    }

    @Override
    protected void convert(BaseViewHolder helper, GankIoDataBean.DataBean data) {
        helper.addOnClickListener(R.id.card_view);
        helper.setText(R.id.tv_desc, data.getDesc());
        helper.setText(R.id.tv_author, data.getCreatedAt() + "   " + data.getAuthor());
        if (data.getImages() != null && data.getImages().size() > 0 && !TextUtils.isEmpty(data.getImages().get(0))) {
            helper.setVisible(R.id.iv_img, true);
            GlideUtil.displayGif(data.getImages().get(0), (ImageView) helper.getView(R.id.iv_img));
        } else {
            helper.setVisible(R.id.iv_img, false);
        }
    }
}
