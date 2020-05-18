package com.imfondof.wanandroid.view.banner.holder;

public interface HolderCreator<VH extends BannerViewHolder> {

    /**
     * 创建ViewHolder
     */
    VH createViewHolder();
}
