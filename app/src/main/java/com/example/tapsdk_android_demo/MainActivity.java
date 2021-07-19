package com.example.tapsdk_android_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tapsdk.bootstrap.Callback;
import com.tapsdk.bootstrap.TapBootstrap;
import com.tapsdk.bootstrap.account.TDSUser;
import com.tapsdk.bootstrap.exceptions.TapError;
import com.tapsdk.moment.TapMoment;
import com.taptap.pay.sdk.library.TapLicenseCallback;
import com.taptap.pay.sdk.library.TapLicenseHelper;
import com.taptap.sdk.TapLoginHelper;
import com.taptap.sdk.net.Api;
import com.tds.common.entities.TapConfig;
import com.tds.common.entities.TapDBConfig;
import com.tds.common.models.TapRegionType;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btTapLogin;
    private Button btTapLoginStatus;
    private Button btTapGouhuo;
    private Button btTapLogout;
    private Button btTapFetchNotification;
    private Button btTapOpenMoment;
    private Button btTapDirectlyOpen;
    private Button btTapCloseMoment;
    private Button btTapOneKeyPublish;
    private Button btTapLicense;

    private static final String TAG = "LeeJiEun ===> ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initSDK();

    }

    private void initView() {
        // 初始化按钮
        // 登陆相关
        btTapLogin = findViewById(R.id.btn_tap_login);
        btTapLoginStatus = findViewById(R.id.btn_tap_loginstatus);
        btTapGouhuo = findViewById(R.id.btn_tap_gouhuo);
        btTapLogout = findViewById(R.id.btn_tap_logout);
        btTapLicense = findViewById(R.id.btn_tap_license);
        // 内嵌动态相关
        btTapFetchNotification = findViewById(R.id.btn_tap_fetch_notification);
        btTapOpenMoment = findViewById(R.id.btn_tap_open_moment);
        btTapDirectlyOpen = findViewById(R.id.btn_tap_directly_open);
        btTapCloseMoment = findViewById(R.id.btn_tap_close_moment);
        btTapOneKeyPublish = findViewById(R.id.btn_tap_one_key_publish);

        // 注册监听器
        btTapLogin.setOnClickListener(this);
        btTapLoginStatus.setOnClickListener(this);
        btTapGouhuo.setOnClickListener(this);
        btTapLogout.setOnClickListener(this);
        btTapLicense.setOnClickListener(this);
        // 内嵌动态相关
        btTapFetchNotification.setOnClickListener(this);
        btTapOpenMoment.setOnClickListener(this);
        btTapDirectlyOpen.setOnClickListener(this);
        btTapCloseMoment.setOnClickListener(this);
        btTapOneKeyPublish.setOnClickListener(this);

    }

    /*
     * TapSDK 初始化，TapDB初始化，内嵌动态初始化
     * */
    public void initSDK() {
        // TapDB 初始化
        TapDBConfig tapDBConfig = new TapDBConfig();
        tapDBConfig.setEnable(true);
        tapDBConfig.setChannel("gameChannel");
        tapDBConfig.setGameVersion("gameVersion");
        // TapSDK 初始化
        TapConfig tapConfig = new TapConfig.Builder()
                .withAppContext(getApplicationContext())
                .withRegionType(TapRegionType.CN) // TapRegionType.CN: 国内  TapRegionType.IO: 国外
                .withClientId("Client ID from DC")
                .withClientToken("Client Token from DC")
                .withServerUrl("https://0rialmny.cloud.tds1.tapapis.cn")
                .withTapDBConfig(tapDBConfig)
                .build();
        TapBootstrap.init(MainActivity.this, tapConfig);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_tap_login:
                // 登陆功能
                taptapLogin();
                break;
            case R.id.btn_tap_loginstatus:
                // 登陆状态
                taptapLoginStatus();
                break;
            case R.id.btn_tap_gouhuo:
                //篝火测试资格
                taptapGouHuo();
                break;
            case R.id.btn_tap_logout:
                // 登出功能
                taptapLogout();
                break;
            case R.id.btn_tap_license:
                // 付费验证
                taptapLicense();
                break;
            case R.id.btn_tap_fetch_notification:
                // 获取新消息
                taptapFetchNotification();
                break;
            case R.id.btn_tap_open_moment:
                // 打开动态
                taptapOpenMoment();
                break;
            case R.id.btn_tap_directly_open:
                // 场景化入口
                taptapDirectlyOpen();
                break;
            case R.id.btn_tap_close_moment:
                // 关闭内嵌动态
                taptapCloseMoment();
                break;
            case R.id.btn_tap_one_key_publish:
                // 一键发布内容到内嵌动态
                taptapOneKeyPublish();
                break;

        }
    }

    private void taptapLicense() {
        //默认情况下 SDK 会弹出不可由玩家手动取消的弹窗来避免未授权玩家进入游戏，如果需要回调来触发流程，请添加如下代码
        TapLicenseHelper.setLicenseCallback(new TapLicenseCallback() {
            @Override
            public void onLicenseSuccess() {
                Log.d(TAG, "用户已经付费购买");
            }
        });
        TapLicenseHelper.check(this);
    }

    private void taptapLogout() {
        TDSUser.logOut();
    }

    private void taptapGouHuo() {
        TapLoginHelper.getTestQualification(new Api.ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                // 该玩家已拥有测试资格
                Toast.makeText(MainActivity.this, "该玩家已具有篝火测试资格", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable throwable) {
                // 该玩家不具备测试资格， 游戏层面进行拦截
                Toast.makeText(MainActivity.this, "该玩家不具备篝火测试资格", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void taptapLoginStatus() {
        // 未登录用户会返回 null
        TDSUser currentUser = TDSUser.currentUser();
        // 未登录用户会返回 null
        if (currentUser == null) {
            // 用户未登录过
            Toast.makeText(MainActivity.this, "没有登陆，请执行登陆操作", Toast.LENGTH_SHORT).show();
        } else {
            // 用户已经登录过
            Toast.makeText(MainActivity.this, "已经登陆，执行程序业务逻辑", Toast.LENGTH_SHORT).show();
        }
    }

    private void taptapLogin() {
        TDSUser currentUser = TDSUser.currentUser();
        // 未登录用户会返回 null
        if (currentUser == null) {
            // 用户未登录过
            TDSUser.loginWithTapTap(MainActivity.this, new Callback<TDSUser>() {
                @Override
                public void onSuccess(TDSUser resultUser) {
                    // 开发者可以调用 resultUser 的方法获取更多属性。
                    String userID = resultUser.getObjectId();
                    String userName = resultUser.getUsername();
                    String avatar = (String) resultUser.get("avatar");
                    String openID = (String) resultUser.get("openid");
                    String unionID = (String) resultUser.get("unionid");
                    Log.d(TAG, "userID: " + userID);
                    Log.d(TAG, "userName: " +userName);
                    Log.d(TAG, "avatar: "+ avatar);
                    Log.d(TAG, "openID: "+ openID);
                    Log.d(TAG, "unionID: "+ unionID);
                    Toast.makeText(MainActivity.this, "succeed to login with Taptap.", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFail(TapError error) {
                    Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, error.detailMessage);
                }
            }, TapLoginHelper.SCOPE_PUBLIC_PROFILE);
        } else {
            // 用户已经登录过
            Toast.makeText(MainActivity.this, "已经登陆，执行程序业务逻辑", Toast.LENGTH_SHORT).show();
        }
    }

    private void taptapOneKeyPublish() {
        Toast.makeText(MainActivity.this, "注意： 该方法不建议使用， 动态应该类似于朋友圈，玩家自己打开内嵌动态后自己选择内容发布", Toast.LENGTH_SHORT).show();
        // 注意： 该方法不建议使用， 动态应该类似于朋友圈，玩家自己打开内嵌动态后自己选择内容发布
        int orientation = TapMoment.ORIENTATION_PORTRAIT;
        String content = "描述";
        String[] imagePaths = new String[]{"content://hello.jpg", "/sdcard/world.jpg"};
        TapMoment.publish(orientation, imagePaths, content);
    }

    private void taptapCloseMoment() {
        TapMoment.close();
//        TapMoment.closeWithConfirmWindow("提示", "匹配成功，进入游戏");
    }

    private void taptapDirectlyOpen() {
        Map<String, String> extras = new HashMap<>();
        // 注意：这里的 key 是固定的，"scene_id"； 第二个参数：开发者后台开启场景化入口并配置相关项后可以得到
        extras.put("scene_id", "LeeJiEun");
        // 注意：第二个参数固定为 "tap://moment/scene/"
        TapMoment.directlyOpen(TapMoment.ORIENTATION_DEFAULT, "tap://moment/scene/", extras);
    }

    private void taptapOpenMoment() {
        TapMoment.open(TapMoment.ORIENTATION_PORTRAIT);
    }

    // 获取用户新通知数量
    private void taptapFetchNotification() {
        // 获取到到数据在内嵌动态到回调方法中给出
        TapMoment.fetchNotification();
    }

}