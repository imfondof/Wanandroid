package com.imfondof.wanandroid.other.ext

import android.widget.Toast
import com.imfondof.wanandroid.other.utils.ToastUtil

fun String.show(time: Int = Toast.LENGTH_SHORT) {
    ToastUtil.showToast(this)
}