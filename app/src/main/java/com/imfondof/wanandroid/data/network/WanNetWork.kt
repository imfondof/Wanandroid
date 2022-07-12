package com.imfondof.wanandroid.data.network

import com.imfondof.wanandroid.data.bean.WanCoinRankBean
import com.imfondof.wanandroid.data.bean.WanHomeListBean
import com.imfondof.wanandroid.data.bean.WanQABean
import retrofit2.Callback

/**
 * @Description:
 * @CreateDate: 2022/4/12 15:31
 * @Version: 1.0
 */
class WanNetWork {
    private val wanService = ServiceCreator.create(WanService::class.java)

    fun fetchHomeArticle(page: Int, cid: Int?, callback: Callback<WanHomeListBean>) = wanService.getHomeArticle(page, cid).enqueue(callback)

    fun fetchSquareArticle(page: Int, cid: Int?, callback: Callback<WanHomeListBean>) = wanService.getSquareArticle(page).enqueue(callback)
    fun fetchQA(page: Int, callback: Callback<WanQABean>) = wanService.getQA(page).enqueue(callback)
    fun fetchCoinRank(page: Int, callback: Callback<WanCoinRankBean>) = wanService.getCoinRank(page).enqueue(callback)

    companion object {
        private var netWork: WanNetWork? = null
        fun getInstance(): WanNetWork {
            if (netWork == null) {
                synchronized(WanNetWork::class.java) {
                    if (netWork == null) {
                        netWork = WanNetWork()
                    }
                }
            }
            return netWork!!
        }
    }
}