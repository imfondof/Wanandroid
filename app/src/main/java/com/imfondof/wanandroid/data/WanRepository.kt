package com.imfondof.wanandroid.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.imfondof.wanandroid.data.bean.WanHomeListBean
import com.imfondof.wanandroid.data.network.WanNetWork
import com.imfondof.wanandroid.other.utils.WanExecutors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WanRepository private constructor(private val network: WanNetWork) {
    fun getHomeArticle(page: Int, cid: Int?): LiveData<Resource<WanHomeListBean>> {
        val liveData = MutableLiveData<Resource<WanHomeListBean>>()
        liveData.value = Resource.loading(null)
        WanExecutors.diskIO.execute {
            network.fetchHomeArticle(page, cid, object : Callback<WanHomeListBean> {
                override fun onResponse(call: Call<WanHomeListBean>, response: Response<WanHomeListBean>) {
                    WanExecutors.diskIO.execute {
                        liveData.postValue(Resource.success(response.body()))
                    }
                }

                override fun onFailure(call: Call<WanHomeListBean>, t: Throwable) {
                    liveData.postValue(Resource.error("加载失败", null))
                }
            })
        }
        return liveData
    }

    fun getSquareArticle(page: Int, cid: Int?): LiveData<Resource<WanHomeListBean>> {
        val liveData = MutableLiveData<Resource<WanHomeListBean>>()
        liveData.value = Resource.loading(null)
        WanExecutors.diskIO.execute {
            network.fetchSquareArticle(page, cid, object : Callback<WanHomeListBean> {
                override fun onResponse(call: Call<WanHomeListBean>, response: Response<WanHomeListBean>) {
                    WanExecutors.diskIO.execute {
                        liveData.postValue(Resource.success(response.body()))
                    }
                }

                override fun onFailure(call: Call<WanHomeListBean>, t: Throwable) {
                    liveData.postValue(Resource.error("加载失败", null))
                }
            })
        }
        return liveData
    }

    companion object {
        private var instance: WanRepository? = null
        fun getInstance(network: WanNetWork): WanRepository {
            if (instance == null) {
                synchronized(WanRepository::class.java) {
                    if (instance == null) {
                        instance = WanRepository(network)
                    }
                }
            }
            return instance!!
        }
    }
}