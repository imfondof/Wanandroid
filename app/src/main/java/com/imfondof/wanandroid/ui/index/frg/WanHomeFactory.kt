package com.imfondof.wanandroid.ui.index.frg

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.imfondof.wanandroid.data.WanRepository

/**
 * @Description:
 * @CreateDate: 2022/4/12 17:28
 * @Version: 1.0
 */
class WanHomeFactory(private val repository: WanRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WanHomeViewModel(repository) as T
    }
}