package com.imfondof.wanandroid.ui.base;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.imfondof.wanandroid.R;
import com.imfondof.wanandroid.other.utils.StatusBarUtil;

public abstract class BaseActivity extends AppCompatActivity {

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StatusBarUtil.transparentStatusbarAndLayoutInsert(this, isStatusbarLight());//默认设置背景为白色
        StatusBarUtil.setStatusBarColor(this, getResources().getColor(R.color.colorGray), true);
    }

    public abstract void initView();

    protected abstract void initEvent();

    /**
     * 配合xml的fakeStatusBarView，动态设置状态栏字体的颜色true为黑  false为白
     * （此方法应封装为基类的抽象方法，子类继承重写）
     *
     * @return
     */
    public boolean isStatusbarLight() {
        return true;
    }
}
