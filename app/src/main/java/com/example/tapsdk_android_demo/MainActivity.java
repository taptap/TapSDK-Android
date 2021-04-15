package com.example.tapsdk_android_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.tapsdk.bootstrap.TapBootstrap;
import com.tapsdk.bootstrap.account.TapLoginResultListener;

import com.tapsdk.bootstrap.exceptions.TapError;
import com.tapsdk.moment.TapMoment;

import com.tds.common.entities.TapConfig;
import com.tds.common.models.TapRegionType;
import com.tapsdk.bootstrap.account.LoginType;
import com.tds.tapdb.sdk.TapDB;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initSDK();

        initView();

    }

    private void initView() {
        // TapTap登陆
        findViewById(R.id.btn_tap_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 未登录用户会返回 null
                if (TapBootstrap.getCurrentToken() == null) {
                    // 用户未登录过
                    TapBootstrap.login(MainActivity.this, LoginType.TAPTAP);
                } else {
                    // 用户已经登录过
                    Toast.makeText(MainActivity.this, "执行程序业务逻辑", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // 打开动态
        findViewById(R.id.btn_openmoment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TapMoment.open(TapMoment.ORIENTATION_PORTRAIT);

            }
        });

        // 获取用户新通知数量
        findViewById(R.id.btn_closemoment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取到到数据在内嵌动态到回调方法中给出
                TapMoment.fetchNotification();
            }
        });

        // 设置用户名称
        findViewById(R.id.bit_push_dongtai).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TapDB.setName("LeeJiEun");
            }
        });


    }

    /*
     * TapSDK 初始化，动态初始化
     * */
    public void initSDK() {
        // TapSDK 初始化
        TapConfig tapConfig = new TapConfig.Builder()
                .withAppContext(getApplicationContext())
                .withRegionType(TapRegionType.CN) // TapRegionType.CN: 国内  TapRegionType.IO: 国外
                .withClientId("FwFdCIr6u71WQDQwQN")
                .build();
        TapBootstrap.init(MainActivity.this, tapConfig);

        // 动态 初始化
        TapMoment.init(MainActivity.this, "FwFdCIr6u71WQDQwQN", true);

        // 初始化TapDB
        TapDB.init(getApplicationContext(), "FwFdCIr6u71WQDQwQN", "taptap", true);

        // 注册登陆回调监听
        TapBootstrap.registerLoginResultListener(new TapLoginCallback());

        // 注册动态回调监听
        TapMoment.setCallback(new TapMoment.TapMomentCallback() {
            @Override
            public void onCallback(int code, String msg) {
                if (code == TapMoment.CALLBACK_CODE_GET_NOTICE_SUCCESS) {
                    // 获取用户新消息成功
                    Toast.makeText(MainActivity.this, "获取新通知数据为： " + msg, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}


/*
 * 登陆回调方法
 * */
class TapLoginCallback implements TapLoginResultListener {
    private static final String TAG = "TapTap===> ";

    @Override
    public void loginSuccess(com.tds.common.entities.AccessToken accessToken) {
        Log.d(TAG, "onLoginSuccess: " + accessToken.toJSON());
    }

    @Override
    public void loginFail(TapError tapError) {
        Log.d(TAG, "onLoginError: " + tapError.detailMessage);
        Log.d(TAG, "onLoginError: " + tapError.toJSON());
    }

    @Override
    public void loginCancel() {
        Log.d(TAG, "onLoginCancel");
    }
}