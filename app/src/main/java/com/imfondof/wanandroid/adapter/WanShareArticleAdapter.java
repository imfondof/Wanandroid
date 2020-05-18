package com.imfondof.wanandroid.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.imfondof.wanandroid.R;
import com.imfondof.wanandroid.bean.WanCollectArticleBean;
import com.imfondof.wanandroid.bean.WanShareArticleBean;

import java.util.List;

public class WanShareArticleAdapter extends BaseQuickAdapter<WanShareArticleBean.DataBean.ShareArticlesBean.DatasBean, BaseViewHolder> {

    List<WanCollectArticleBean.DataBean.DatasBean> mDatas;

    public WanShareArticleAdapter(List<WanShareArticleBean.DataBean.ShareArticlesBean.DatasBean> data) {
        super(R.layout.item_wan_home_article, data);
    }

    public WanShareArticleAdapter() {
        super(R.layout.item_wan_home_article);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final WanShareArticleBean.DataBean.ShareArticlesBean.DatasBean item) {
        helper.setVisible(R.id.username, false);
        helper.setText(R.id.title, item.getTitle());
        helper.setText(R.id.time, item.getNiceDate());
        helper.setText(R.id.type, item.getChapterName());
        helper.setVisible(R.id.vb_collect, false);

        helper.addOnClickListener(R.id.article);
    }
}
