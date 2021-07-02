package com.example.tapsdk_android_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tapsdk.bootstrap.Callback;
import com.tapsdk.bootstrap.TapBootstrap;
import com.tapsdk.bootstrap.account.TapLoginResultListener;

import com.tapsdk.bootstrap.account.entities.TapUser;
import com.tapsdk.bootstrap.exceptions.TapError;
import com.tapsdk.friends.Callback0;
import com.tapsdk.friends.ListCallback;
import com.tapsdk.friends.TapFriends;
import com.tapsdk.friends.entities.TapUserRelationship;
import com.tapsdk.friends.exceptions.TapFriendError;
import com.tapsdk.moment.TapMoment;

import com.taptap.pay.sdk.library.TapLicenseCallback;
import com.taptap.pay.sdk.library.TapLicenseHelper;
import com.tds.common.entities.AccessToken;
import com.tds.common.entities.TapConfig;
import com.tds.common.entities.TapDBConfig;
import com.tds.common.models.ComponentMessageCallback;
import com.tds.common.models.TapRegionType;
import com.tapsdk.bootstrap.account.LoginType;
import com.tds.common.utils.TapGameUtil;
import com.tds.tapdb.sdk.TapDB;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btTapLogin;
    private Button btTapLoginStatus;
    private Button btTapGouhuo;
    private Button btTapUserInfo;
    private Button btTapLogout;
    private Button btTapFetchNotification;
    private Button btTapOpenMoment;
    private Button btTapDirectlyOpen;
    private Button btTapCloseMoment;
    private Button btTapOneKeyPublish;
    private Button btTapAddFriend;
    private Button btTapDeleteFriend;
    private Button btTapBlockFriend;
    private Button btTapUnBlockFriend;
    private Button btTapGetFollowingList;
    private Button btTapGetFollowerList;
    private Button btTapGetBlockList;
    private Button btTapSendInvitation;
    private Button btTapGetInvitation;
    private Button btTapSearchUser;
    private Button btTapRichToken;
    private Button btTapRichVar;
    private Button btTapRichClear;
    private Button btTapLicense;

    private Map<String, String> extras;
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
        btTapUserInfo = findViewById(R.id.btn_tap_userinfo);
        btTapLogout = findViewById(R.id.btn_tap_logout);
        btTapLicense = findViewById(R.id.btn_tap_license);
        // 内嵌动态相关
        btTapFetchNotification = findViewById(R.id.btn_tap_fetch_notification);
        btTapOpenMoment = findViewById(R.id.btn_tap_open_moment);
        btTapDirectlyOpen = findViewById(R.id.btn_tap_directly_open);
        btTapCloseMoment = findViewById(R.id.btn_tap_close_moment);
        btTapOneKeyPublish = findViewById(R.id.btn_tap_one_key_publish);
        // 好友相关
        btTapAddFriend = findViewById(R.id.btn_tap_add_friend);
        btTapDeleteFriend = findViewById(R.id.btn_tap_delete_friend);
        btTapBlockFriend = findViewById(R.id.btn_tap_block_friend);
        btTapUnBlockFriend = findViewById(R.id.btn_tap_unblock_friend);
        btTapGetFollowingList = findViewById(R.id.btn_tap_get_following_list);
        btTapGetFollowerList = findViewById(R.id.btn_tap_get_follower_list);
        btTapGetBlockList = findViewById(R.id.btn_tap_get_block_list);
        btTapSendInvitation = findViewById(R.id.btn_tap_send_invitation);
        btTapGetInvitation = findViewById(R.id.btn_tap_get_invitation);
        btTapSearchUser = findViewById(R.id.btn_tap_search_user);
        btTapRichToken = findViewById(R.id.btn_tap_rich_token);
        btTapRichVar = findViewById(R.id.btn_tap_rich_var);
        btTapRichClear = findViewById(R.id.btn_tap_rich_clear);


        // 注册监听器
        btTapLogin.setOnClickListener(this);
        btTapLoginStatus.setOnClickListener(this);
        btTapGouhuo.setOnClickListener(this);
        btTapUserInfo.setOnClickListener(this);
        btTapLogout.setOnClickListener(this);
        btTapLicense.setOnClickListener(this);
        // 内嵌动态相关
        btTapFetchNotification.setOnClickListener(this);
        btTapOpenMoment.setOnClickListener(this);
        btTapDirectlyOpen.setOnClickListener(this);
        btTapCloseMoment.setOnClickListener(this);
        btTapOneKeyPublish.setOnClickListener(this);
        // 好友相关
        btTapAddFriend.setOnClickListener(this);
        btTapDeleteFriend.setOnClickListener(this);
        btTapBlockFriend.setOnClickListener(this);
        btTapUnBlockFriend.setOnClickListener(this);
        btTapGetFollowingList.setOnClickListener(this);
        btTapGetFollowerList.setOnClickListener(this);
        btTapGetBlockList.setOnClickListener(this);
        btTapSendInvitation.setOnClickListener(this);
        btTapGetInvitation.setOnClickListener(this);
        btTapSearchUser.setOnClickListener(this);
        btTapRichToken.setOnClickListener(this);
        btTapRichVar.setOnClickListener(this);
        btTapRichClear.setOnClickListener(this);


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
                .withClientId("Client ID From Tap Developer Center")
                .withClientSecret("Client Token From Tap Developer Center")
                .withTapDBConfig(tapDBConfig)
                .build();
        TapBootstrap.init(MainActivity.this, tapConfig);

        // 动态 初始化
//        TapMoment.init(MainActivity.this, "FwFdCIr6u71WQDQwQN", true);

        // 初始化TapDB
//        TapDB.init(getApplicationContext(), "FwFdCIr6u71WQDQwQN", "taptap", true);

        // 注册登陆回调监听
        TapBootstrap.registerLoginResultListener(new TapLoginResultListener() {
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
        });

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

        // 注册好友回调监听
        TapFriends.registerMessageCallback(new ComponentMessageCallback() {
            @Override
            public void onMessage(int code, Map<String, String> map) {

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
            case R.id.btn_tap_userinfo:
                // 获取用户信息
                taptapGetUserInfo();
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
            case R.id.btn_tap_add_friend:
                // 添加好友
                taptapAddFriend();
                break;
            case R.id.btn_tap_delete_friend:
                // 删除好友
                taptapDeleteFriend();
                break;
            case R.id.btn_tap_block_friend:
                // 拉黑好友
                taptapBlockFriend();
                break;
            case R.id.btn_tap_unblock_friend:
                // 取消拉黑好友
                taptapUnblockFriend();
                break;
            case R.id.btn_tap_get_following_list:
                // 获取关注好友列表
                taptapGetFollowingList();
                break;
            case R.id.btn_tap_get_follower_list:
                // 获取粉丝列表
                taptapGetFollowerList();
                break;
            case R.id.btn_tap_get_block_list:
                // 获取黑名单列表
                taptapGetBlockList();
                break;
            case R.id.btn_tap_send_invitation:
                // 分享好友邀请
                taptapSendInvitation();
                break;
            case R.id.btn_tap_get_invitation:
                // 获取好友邀请
                taptapGetInvitation();
                break;
            case R.id.btn_tap_search_user:
                // 搜索用户
                taptapSearchUser();
                break;
            case R.id.btn_tap_rich_token:
                // 富信息令牌
                taptapRichVar();
                break;
            case R.id.btn_tap_rich_var:
                // 富信息变量
                taptapRichToken();
                break;
            case R.id.btn_tap_rich_clear:
                // 清除富信息
                taptapRichClear();
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

    private void taptapRichClear() {
        // 清除富信息
        TapFriends.clearRichPresence("display", new Callback0() {
            @Override
            public void handlerResult(TapFriendError tapFriendError) {
                if (null == tapFriendError){
                    // 清除成功
                }else {
                    Log.d(TAG, tapFriendError.detailMessage);
                    Log.d(TAG, String.valueOf(tapFriendError.code));
                }
            }
        });
    }

    private void taptapRichVar() {
        // 富信息 令牌 参考文档中的服务端设置，这里的key值"display"文档中是"token"令牌形式， 所以value值需要以#开头
        TapFriends.setRichPresence("display", "#playing", new Callback0() {
            @Override
            public void handlerResult(TapFriendError tapFriendError) {
                if (null == tapFriendError){
                    // 设置成功
                }else {
                    Log.d(TAG, tapFriendError.detailMessage);
                    Log.d(TAG, String.valueOf(tapFriendError.code));
                }
            }
        });
    }

    private void taptapRichToken() {
        // 富信息 变量 这里的key值"score"文档中是"variable"形式， 所以value值不需要以#开头
        TapFriends.setRichPresence("score", "100", new Callback0() {
            @Override
            public void handlerResult(TapFriendError tapFriendError) {
                if (null == tapFriendError){
                    // 设置成功
                }else {
                    Log.d(TAG, tapFriendError.detailMessage);
                    Log.d(TAG, String.valueOf(tapFriendError.code));
                }
            }
        });

    }


    private void taptapLogout() {
        TapBootstrap.logout();
    }

    private void taptapGetUserInfo() {
        TapBootstrap.getUser(new Callback<TapUser>() {
            @Override
            public void onSuccess(TapUser tapUser) {
                Toast.makeText(MainActivity.this, "用户信息为：" + tapUser.toJSON(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "用户信息为: " + tapUser.toJSON());
                // userid 字段 在使用好友功能时会作为参数传递
                Log.d(TAG, "userID: " + tapUser.userId);
            }

            @Override
            public void onFail(TapError tapError) {
                Toast.makeText(MainActivity.this, "获取用户信息失败：" + tapError.detailMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void taptapGouHuo() {
        TapBootstrap.getTestQualification(new Callback<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                // 该玩家已拥有测试资格
                Toast.makeText(MainActivity.this, "该玩家已具有篝火测试资格", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(TapError tapError) {
                // 该玩家不具备测试资格， 游戏层面进行拦截
                Toast.makeText(MainActivity.this, "该玩家不具备篝火测试资格", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void taptapLoginStatus() {
        // 未登录用户会返回 null
        if (TapBootstrap.getCurrentToken() == null) {
            // 用户未登录过
            Toast.makeText(MainActivity.this, "用户未登陆，请执行登陆功能", Toast.LENGTH_SHORT).show();
        } else {
            // 用户已经登录过
            Toast.makeText(MainActivity.this, "已经登陆，继续执行自己业务逻辑", Toast.LENGTH_SHORT).show();
        }
    }

    private void taptapLogin() {
        // 未登录用户会返回 null
        if (TapBootstrap.getCurrentToken() == null) {
            // 用户未登录过
            TapBootstrap.login(MainActivity.this, LoginType.TAPTAP, "public_profile");
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

    private void taptapAddFriend() {
        //     7daf77b3b1d548fea656b74548d68f0c
        TapFriends.addFriend("fc252cbd9ed84e0e8584a78e696f0e0c", new com.tapsdk.friends.Callback<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                Toast.makeText(MainActivity.this, "添加好友成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(TapFriendError tapFriendError) {
                Toast.makeText(MainActivity.this, "添加好友失败： " + tapFriendError.detailMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void taptapDeleteFriend() {
        TapFriends.deleteFriend("fc252cbd9ed84e0e8584a78e696f0e0c", new com.tapsdk.friends.Callback<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                Toast.makeText(MainActivity.this, "删除好友成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(TapFriendError tapFriendError) {
                Toast.makeText(MainActivity.this, "删除好友失败： " + tapFriendError.detailMessage, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void taptapBlockFriend() {
        TapFriends.blockUser("fc252cbd9ed84e0e8584a78e696f0e0c", new com.tapsdk.friends.Callback<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                Toast.makeText(MainActivity.this, "拉黑好友成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(TapFriendError tapFriendError) {
                Toast.makeText(MainActivity.this, "拉黑好友失败： " + tapFriendError.detailMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void taptapUnblockFriend() {
        TapFriends.unblockUser("fc252cbd9ed84e0e8584a78e696f0e0c", new com.tapsdk.friends.Callback<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                Toast.makeText(MainActivity.this, "取消拉黑好友成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(TapFriendError tapFriendError) {
                Toast.makeText(MainActivity.this, "取消拉黑好友失败： " + tapFriendError.detailMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void taptapGetFollowingList() {
        TapFriends.getFollowingList(0, true, 100, new ListCallback<TapUserRelationship>() {
            @Override
            public void onSuccess(List<TapUserRelationship> list) {
                for (TapUserRelationship u : list) {
                    System.out.println(u);
                }
                Log.d(TAG, "获取关注好友列表成功: " + list.toString());
                Toast.makeText(MainActivity.this, "获取关注好友列表成功： " + list.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(TapFriendError tapFriendError) {
                Toast.makeText(MainActivity.this, "获取关注好友列表失败： " + tapFriendError.detailMessage, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "获取关注好友列表失败: " + tapFriendError.toJSON());
            }
        });
    }

    private void taptapGetFollowerList() {
        TapFriends.getFollowerList(0, 100, new ListCallback<TapUserRelationship>() {
            @Override
            public void onSuccess(List<TapUserRelationship> list) {
                for (TapUserRelationship f : list) {
                    System.out.println(f);
                }
                Log.d(TAG, "获取粉丝列表成功: " + list.toString());
                Toast.makeText(MainActivity.this, "获取粉丝列表成功: " + list.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(TapFriendError tapFriendError) {
                Toast.makeText(MainActivity.this, "获取粉丝列表失败： " + tapFriendError.detailMessage, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "获取粉丝列表失败: " + tapFriendError.toJSON());
            }
        });
    }

    private void taptapGetBlockList() {
        TapFriends.getBlockList(0, 100, new ListCallback<TapUserRelationship>() {
            @Override
            public void onSuccess(List<TapUserRelationship> list) {
                for (TapUserRelationship b : list) {
                    System.out.println(b);
                }
                Toast.makeText(MainActivity.this, "获取黑名单列表成功： " + list.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(TapFriendError tapFriendError) {
                Toast.makeText(MainActivity.this, "获取黑名单列表失败： " + tapFriendError.detailMessage, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "获取黑名单列表失败: " + tapFriendError.toJSON());
            }
        });
    }

    private void taptapSendInvitation() {
        TapFriends.sendFriendInvitation(MainActivity.this, new com.tapsdk.friends.Callback<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                Toast.makeText(MainActivity.this, "分享好友邀请成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(TapFriendError tapFriendError) {
                Toast.makeText(MainActivity.this, "分享好友邀请失败： " + tapFriendError.detailMessage, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "分享好友邀请失败: " + tapFriendError.toJSON());
            }
        });
    }

    private void taptapGetInvitation() {
        TapFriends.generateFriendInvitation(new com.tapsdk.friends.Callback<String>() {
            @Override
            public void onSuccess(String s) {
                Toast.makeText(MainActivity.this, "获取好友邀请成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(TapFriendError tapFriendError) {
                Toast.makeText(MainActivity.this, "获取好友邀请失败： " + tapFriendError.detailMessage, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "获取好友邀请失败: " + tapFriendError.toJSON());
            }
        });
    }

    private void taptapSearchUser() {
        TapFriends.searchUser("fc252cbd9ed84e0e8584a78e696f0e0c", new com.tapsdk.friends.Callback<TapUserRelationship>() {
            @Override
            public void onSuccess(TapUserRelationship tapUserRelationship) {
                Toast.makeText(MainActivity.this, "搜索用户成功", Toast.LENGTH_SHORT).show();
                tapUserRelationship.toJSON();
            }

            @Override
            public void onFail(TapFriendError tapFriendError) {
                Toast.makeText(MainActivity.this, "搜索用户失败： " + tapFriendError.detailMessage, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "搜索用户失败: " + tapFriendError.toJSON());
            }
        });
    }

}