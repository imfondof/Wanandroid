package com.imfondof.wanandroid.other.ext

import android.app.Activity
import android.app.Dialog
import android.view.View
import com.imfondof.wanandroid.ui.base.BaseFragment

fun <T : View> Activity.id(id: Int) = lazy {
    findViewById<T>(id)
}

fun <T : View> BaseFragment.id(id: Int) = lazy {
    getView<T>(id)
}

fun <T : View> View.id(id: Int) = lazy {
    findViewById<T>(id)
}

fun <T : View> Dialog.id(id: Int) = lazy {
    findViewById<T>(id)
}