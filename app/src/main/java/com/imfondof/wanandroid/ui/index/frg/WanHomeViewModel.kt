package com.imfondof.wanandroid.ui.index.frg

import androidx.lifecycle.ViewModel
import com.imfondof.wanandroid.data.WanRepository
import com.imfondof.wanandroid.data.bean.WanHomeListBean

/**
 * @Description:
 * @CreateDate: 2022/4/12 17:23
 * @Version: 1.0
 */
class WanHomeViewModel(private val repository: WanRepository) : ViewModel() {
    var dataList = ArrayList<WanHomeListBean.DataBean.DatasBean>()

    fun getHomeArticle(page: Int, cid: Int?) = repository.getHomeArticle(page, cid)
    fun getSquareArticle(page: Int, cid: Int?) = repository.getSquareArticle(page, cid)
}