package com.imfondof.wanandroid.data.http;

import com.imfondof.wanandroid.data.bean.GankIoDataBean;
import com.imfondof.wanandroid.data.bean.SystemResult;
import com.imfondof.wanandroid.data.bean.WanCoinBean;
import com.imfondof.wanandroid.data.bean.WanCoinDetailBean;
import com.imfondof.wanandroid.data.bean.WanCoinRankBean;
import com.imfondof.wanandroid.data.bean.WanCollectArticleBean;
import com.imfondof.wanandroid.data.bean.WanCollectSiteBean;
import com.imfondof.wanandroid.data.bean.WanHomeListBean;
import com.imfondof.wanandroid.data.bean.WanLoginBean;
import com.imfondof.wanandroid.data.bean.WanQABean;
import com.imfondof.wanandroid.data.bean.WanShareArticleBean;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 一个接口对应一个方法
 */
public interface HttpClient {

    /**
     * OkHttpClient client = new OkHttpClient.Builder()
     * .addInterceptor(new HttpUtils.AddCookiesInterceptor()) //这部分
     * .addInterceptor(new HttpUtils.ReceivedCookiesInterceptor()) //这部分
     * .build();
     * <p>
     * //创建Retrofit对象
     * Retrofit retrofit = new Retrofit.Builder()
     * .baseUrl(BASE_URL)
     * .addConverterFactory(GsonConverterFactory.create())// 设置数据解析器
     * .client(client)
     * .build();
     */
    class Builder {
        public static HttpClient getWanAndroidService() {
            return BuildFactory.getInstance().create(HttpClient.class, HttpUtils.API_WAN_ANDROID);
        }

        public static HttpClient getGankService() {
            return BuildFactory.getInstance().create(HttpClient.class, HttpUtils.API_GANK);
        }
    }


    /**
     * 玩安卓，文章列表、知识体系(需要cid)下的文章
     *
     * @param page 页码，从0开始
     * @param cid  体系id
     */
    @GET("article/list/{page}/json")
    Call<WanHomeListBean> getHomeList(@Path("page") int page, @Query("cid") Integer cid);

    /**
     * 玩安卓，最新项目
     */
    @GET("article/listproject/{page}/json")
    Call<WanHomeListBean> getProhectList(@Path("page") int page);

    /**
     * wanandroid登录
     */
    @FormUrlEncoded
    @POST("user/login")
    Call<WanLoginBean> wanLogin(@Field("username") String username, @Field("password") String password);

    /**
     * wanandroid问答模块
     */
    @GET("wenda/list/{page}/json")
    Call<WanQABean> getQA(@Path("page") int page);

    /**
     * wanandroid问答模块
     */
    @GET("tree/json")
    Call<SystemResult> getSystem();

    /**
     * wanandroid积分
     */
    @GET("lg/coin/userinfo/json")
    Call<WanCoinBean> getCoin();

    /**
     * wanandroid积分排行
     */
    @GET("coin/rank/{page}/json")
    Call<WanCoinRankBean> getCoinRank(@Path("page") int page);

    /**
     * wanandroid积分获取明细
     */
    @GET("lg/coin/list/{page}/json")
    Call<WanCoinDetailBean> getCoinDetail(@Path("page") int page);

    /**
     * wanandroid广场（相同的数据结构，所以复用homelistbean以及adapter）
     */
    @GET("user_article/list/{page}/json")
    Call<WanHomeListBean> getSquareArticle(@Path("page") int page);

    /**
     * wanandroid收藏文章列表
     */
    @GET("lg/collect/list/{page}/json")
    Call<WanCollectArticleBean> getCollectArticle(@Path("page") int page);

    /**
     * 收藏站内文章
     * id 文章id
     */
    @POST("lg/collect/{id}/json")
    Call<WanHomeListBean> collect(@Path("id") int id);

    /**
     * 取消收藏文章 （首页）
     * id 文章id
     */
    @POST("lg/uncollect_originId/{id}/json")
    Call<WanHomeListBean> unCollectOrigin(@Path("id") int id);

    /**
     * 取消收藏文章 （收藏页面）
     *
     * @param id       文章id
     * @param originId 列表页下发，无则为-1
     *                 (代表的是你收藏之前的那篇文章本身的id；
     *                 但是收藏支持主动添加，这种情况下，没有originId则为-1)
     */
    @FormUrlEncoded
    @POST("lg/uncollect/{id}/json")
    Call<WanHomeListBean> unCollect(@Path("id") int id, @Field("originId") int originId);

    /**
     * wanandroid收藏网址列表
     */
    @GET("user/lg/private_articles/{page}/json")
    Call<WanShareArticleBean> getShareArticle(@Path("page") int page);

    /**
     * wanandroid收藏网址列表
     */
    @GET("lg/collect/usertools/json")
    Call<WanCollectSiteBean> getCollectSite();

    /**
     * 收藏网址
     *
     * @param name 标题
     * @param link 链接
     */
    @FormUrlEncoded
    @POST("lg/collect/addtool/json")
    Call<WanHomeListBean> collectUrl(@Field("name") String name, @Field("link") String link);

    /**
     * 编辑收藏网站
     *
     * @param name 标题
     * @param link 链接
     */
    @FormUrlEncoded
    @POST("lg/collect/updatetool/json")
    Call<WanHomeListBean> updateUrl(@Field("id") int id, @Field("name") String name, @Field("link") String link);

    /**
     * 删除收藏网站
     *
     * @param id 收藏网址id
     */
    @FormUrlEncoded
    @POST("lg/collect/deletetool/json")
    Call<WanHomeListBean> unCollectUrl(@Field("id") int id);



    //gank
    //category 可接受参数 All(所有分类) | Article | GanHuo | Girl
    //type 可接受参数 All(全部类型) | Android | iOS | Flutter | Girl ...，即分类API返回的类型数据
    //count: [10, 50]
    //page: >=1
    @GET("data/category/{category}/type/{type}/page/{page}/count/{count}")
    Call<GankIoDataBean> getGankIoData(@Path("category") String category, @Path("type") String type, @Path("page") int page, @Path("count") int count);
}
