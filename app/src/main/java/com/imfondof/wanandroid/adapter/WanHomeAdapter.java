package com.imfondof.wanandroid.adapter;

import android.text.Html;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.imfondof.wanandroid.R;
import com.imfondof.wanandroid.bean.WanHomeListBean;
import com.imfondof.wanandroid.utils.CollectUtils;
import com.imfondof.wanandroid.utils.ToastUtil;

import java.util.List;

public class WanHomeAdapter extends BaseQuickAdapter<WanHomeListBean.DataBean.DatasBean, BaseViewHolder> {

    List<WanHomeListBean.DataBean.DatasBean> mDatas;

    public WanHomeAdapter(List<WanHomeListBean.DataBean.DatasBean> data) {
        super(R.layout.item_wan_home_article, data);
        mDatas = data;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final WanHomeListBean.DataBean.DatasBean item) {
        helper.setText(R.id.title, item.getTitle());
        helper.setText(R.id.time, item.getNiceDate());
        helper.setText(R.id.type, item.getChapterName());
        helper.setText(R.id.username, TextUtils.isEmpty(item.getShareUser()) ? "安卓爱好者" : item.getShareUser());
        helper.setChecked(R.id.vb_collect, item.isCollect());

        helper.addOnClickListener(R.id.article);

        helper.setOnClickListener(R.id.vb_collect, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollectUtils.instance().handleCollect(item.isCollect(), item.getId(), new CollectUtils.OnCollectListener() {
                    @Override
                    public void onSuccess() {
                        item.setCollect(!item.isCollect());
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure() {
                        ToastUtil.showToastLong("操作失败，请检查你的网络设置");
                        item.setCollect(item.isCollect());
                        notifyDataSetChanged();
                    }
                });
            }
        });
    }
}
