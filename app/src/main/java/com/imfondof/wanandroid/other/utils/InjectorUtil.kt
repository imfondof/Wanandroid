package com.imfondof.wanandroid.other.utils

import com.imfondof.wanandroid.data.WanRepository
import com.imfondof.wanandroid.data.network.WanNetWork
import com.imfondof.wanandroid.ui.index.frg.WanHomeFactory

object InjectorUtil {
    private fun getWeatherRepository() = WanRepository.getInstance(WanNetWork.getInstance())

    fun getHomeModelFactory() = WanHomeFactory(getWeatherRepository())
}