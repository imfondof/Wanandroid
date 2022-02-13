package com.imfondof.wanandroid.other.mvp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.imfondof.wanandroid.R;
import com.imfondof.wanandroid.other.mvp.constract.WanContract;
import com.imfondof.wanandroid.other.mvp.impl.DemoPresenterImpl;
import com.imfondof.wanandroid.other.mvp.BaseMVPActivity;

public class MVPDemoActivity extends BaseMVPActivity<WanContract.View, WanContract.Presenter> implements WanContract.View {
    private Button mBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_mvpdemo);

        mBtn = findViewById(R.id.btn);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().getHttp();
            }
        });
    }

    @Override
    protected WanContract.Presenter createPresenter() {
        return new DemoPresenterImpl();
    }

    @Override
    protected WanContract.View getView() {
        return this;
    }

    @Override
    public void initData() {

    }
}
