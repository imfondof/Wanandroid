package com.imfondof.wanandroid.other.test.databinding;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.imfondof.wanandroid.BR;

public class Account extends BaseObservable {
    private String name;
    private String password;

    public Account(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String level) {
        this.password = level;
        notifyPropertyChanged(BR.password);
    }
}
