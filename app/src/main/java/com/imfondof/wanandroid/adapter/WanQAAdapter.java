package com.imfondof.wanandroid.adapter;

import android.text.Html;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.imfondof.wanandroid.R;
import com.imfondof.wanandroid.bean.WanQABean;
import com.imfondof.wanandroid.utils.CollectUtils;
import com.imfondof.wanandroid.utils.StringUtils;
import com.imfondof.wanandroid.utils.ToastUtil;

import java.util.List;

public class WanQAAdapter extends BaseQuickAdapter<WanQABean.DataBean.DatasBean, BaseViewHolder> {
    public WanQAAdapter(List<WanQABean.DataBean.DatasBean> data) {
        super(R.layout.item_wan_qa, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final WanQABean.DataBean.DatasBean item) {
        String desc = Html.fromHtml(item.getDesc()).toString();
        desc = StringUtils.removeAllBank(desc, 2);

        helper.setText(R.id.title, Html.fromHtml(item.getTitle()));
        helper.setText(R.id.desc, desc);
        helper.setText(R.id.time, item.getNiceDate());
        helper.setText(R.id.type, item.getChapterName());
        helper.setText(R.id.username, item.getAuthor());
        helper.setChecked(R.id.vb_collect, item.isCollect());

        helper.setVisible(R.id.zan, item.getZan() > 0 ? true : false);
        helper.setText(R.id.zan, item.getZan() + "赞");

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