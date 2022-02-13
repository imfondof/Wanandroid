package com.imfondof.wanandroid.other.view.banner.holder;

public interface HolderCreator<VH extends BannerViewHolder> {

    /**
     * 创建ViewHolder
     */
    VH createViewHolder();
}
