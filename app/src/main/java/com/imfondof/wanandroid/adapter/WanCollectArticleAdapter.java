package com.imfondof.wanandroid.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.imfondof.wanandroid.R;
import com.imfondof.wanandroid.bean.WanCollectArticleBean;
import com.imfondof.wanandroid.utils.CollectUtils;
import com.imfondof.wanandroid.utils.ToastUtil;

import java.util.List;

/**
 * 收藏的文章列表，这里复用 首页文章item布局
 */
public class WanCollectArticleAdapter extends BaseQuickAdapter<WanCollectArticleBean.DataBean.DatasBean, BaseViewHolder> {

    List<WanCollectArticleBean.DataBean.DatasBean> mDatas;

    public WanCollectArticleAdapter(List<WanCollectArticleBean.DataBean.DatasBean> data) {
        super(R.layout.item_wan_home_article, data);
        mDatas = data;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final WanCollectArticleBean.DataBean.DatasBean item) {
        helper.setVisible(R.id.username, false);
        helper.setText(R.id.title, item.getTitle());
        helper.setText(R.id.time, item.getNiceDate());
        helper.setText(R.id.type, item.getChapterName());
        helper.setChecked(R.id.vb_collect, true);

        helper.addOnClickListener(R.id.article);

        helper.setOnClickListener(R.id.vb_collect, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollectUtils.instance().unCollect(item.getId(),item.getOriginId(), new CollectUtils.OnCollectListener() {
                    @Override
                    public void onSuccess() {
                        mDatas.remove(item);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure() {
                        helper.setChecked(R.id.vb_collect, true);
                        ToastUtil.showToastLong("操作失败，请检查你的网络设置");
                        notifyDataSetChanged();
                    }
                });
            }
        });
    }
}