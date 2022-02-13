package com.imfondof.wanandroid.ui.mine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.imfondof.wanandroid.R;
import com.imfondof.wanandroid.data.bean.WanLoginBean;
import com.imfondof.wanandroid.data.http.HttpClient;
import com.imfondof.wanandroid.other.utils.StatusBarUtil;
import com.imfondof.wanandroid.other.utils.ToastUtil;
import com.imfondof.wanandroid.other.utils.UserUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WanLoginAct extends Activity {

    EditText usernameEt;
    EditText passwordEt;
    Button loginBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_wan_login);

        StatusBarUtil.setStatusBarColor(this, getResources().getColor(R.color.colorWhite), true);
        initView();
    }

    public void initView() {
        usernameEt = findViewById(R.id.et_account);
        passwordEt = findViewById(R.id.et_password);
        loginBtn = findViewById(R.id.btn_login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(usernameEt.getText().toString(), passwordEt.getText().toString());
            }
        });
    }

    protected void initEvent() {

    }

    public void login(String username, String password) {
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "请输入账号和密码", Toast.LENGTH_SHORT).show();
            return;
        }
        Call<WanLoginBean> call = HttpClient.Builder.getWanAndroidService().wanLogin(username, password);
        call.enqueue(new Callback<WanLoginBean>() {
            @Override
            public void onResponse(Call<WanLoginBean> call, Response<WanLoginBean> response) {
                ToastUtil.showToast(response.body().errorMsg);
                if (response.body() != null && response.body().getData() != null) {
                    ToastUtil.showToast(response.body().getData().getUsername() + "登录成功");
                    UserUtils.handleLoginSuccess(response.body());
                    Intent intent = new Intent();//广播通知我的界面进行刷新
                    intent.setAction("user");
                    sendBroadcast(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<WanLoginBean> call, Throwable t) {

            }
        });
    }

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, WanLoginAct.class);
        mContext.startActivity(intent);
    }
}
