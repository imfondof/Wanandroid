package com.imfondof.wanandroid.data.network

import com.imfondof.wanandroid.data.bean.WanCoinRankBean
import com.imfondof.wanandroid.data.bean.WanHomeListBean
import com.imfondof.wanandroid.data.bean.WanQABean
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @Description:
 * @CreateDate: 2022/4/12 15:31
 * @Version: 1.0
 */
interface WanService {
    //主页
    @GET("article/list/{page}/json")
    fun getHomeArticle(@Path("page") page: Int, @Query("cid") cid: Int?): Call<WanHomeListBean>

    //广场
    @GET("user_article/list/{page}/json")
    fun getSquareArticle(@Path("page") page: Int): Call<WanHomeListBean>

    //问答
    @GET("wenda/list/{page}/json")
    fun getQA(@Path("page") page: Int): Call<WanQABean>

    //wanandroid积分排行
    @GET("coin/rank/{page}/json")
    fun getCoinRank(@Path("page") page: Int): Call<WanCoinRankBean>
}