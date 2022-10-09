package com.example.tapsdk_android_demo;

import static com.tapsdk.bootstrap.gamesave.TapGameSave.GAME_SAVE_SUMMARY;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.tapsdk.antiaddiction.AntiAddictionKit;
import com.tapsdk.antiaddiction.Config;
import com.tapsdk.antiaddiction.config.AntiAddictionFunctionConfig;
import com.tapsdk.antiaddiction.constants.Constants;
import com.tapsdk.antiaddictionui.AntiAddictionUICallback;
import com.tapsdk.antiaddictionui.AntiAddictionUIKit;
import com.tapsdk.bootstrap.Callback;
import com.tapsdk.bootstrap.TapBootstrap;
import com.tapsdk.bootstrap.account.TDSUser;
import com.tapsdk.bootstrap.exceptions.TapError;
import com.tapsdk.bootstrap.gamesave.TapGameSave;
import com.tapsdk.moment.TapMoment;
import com.taptap.pay.sdk.library.TapLicenseCallback;
import com.taptap.pay.sdk.library.TapLicenseHelper;
import com.taptap.sdk.AccessToken;
import com.taptap.sdk.AccountGlobalError;
import com.taptap.sdk.LoginSdkConfig;
import com.taptap.sdk.Profile;
import com.taptap.sdk.RegionType;
import com.taptap.sdk.TapLoginHelper;
import com.taptap.sdk.net.Api;
import com.tds.achievement.AchievementCallback;
import com.tds.achievement.AchievementException;
import com.tds.achievement.GetAchievementListCallBack;
import com.tds.achievement.TapAchievement;
import com.tds.achievement.TapAchievementBean;
import com.tds.common.entities.TapConfig;
import com.tds.common.entities.TapDBConfig;
import com.tds.common.models.TapRegionType;

import org.jetbrains.annotations.NotNull;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.leancloud.LCException;
import cn.leancloud.LCFriendship;
import cn.leancloud.LCFriendshipRequest;
import cn.leancloud.LCLeaderboard;
import cn.leancloud.LCLeaderboardResult;
import cn.leancloud.LCLogger;
import cn.leancloud.LCObject;
import cn.leancloud.LCQuery;
import cn.leancloud.LCRanking;
import cn.leancloud.LCStatistic;
import cn.leancloud.LCStatisticResult;
import cn.leancloud.LCUser;
import cn.leancloud.LeanCloud;
import cn.leancloud.callback.LCCallback;
import cn.leancloud.json.JSON;
import cn.leancloud.types.LCNull;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import tds.androidx.annotation.NonNull;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btTapLogin;
    private Button btTapLoginOnly;
    private Button btTapLoginStatus;
    private Button btTapLoginStatusOnly;
    private Button btTapGouhuo;
    private Button btTapLogout;
    private Button btTapBinding;
    private Button btTapBindingAgain;
    private Button btTapLogoutOnly;
    private Button btTapInitOnly;
    private Button btTapFetchNotification;
    private Button btTapOpenMoment;
    private Button btTapDirectlyOpen;
    private Button btTapCloseMoment;
    private Button btTapOneKeyPublish;
    private Button btTapLicense;
    private Button btTapAddFriend;
    private Button btTapGetFriendRequestList;
    private Button btTapDeclineFriendshipRequest;
    private Button btTapAcceptFriendshipRequest;
    private Button btTapDeleteFriendshipRequest;
    private Button btTapFriendRequestStatus;
    private Button btTapGetFriendsList;
    private Button btTapDeleteFriend;
    private Button btTapQueryFriend;
    private Button btTapThirdLogin;
    private Button btTapThirdLoginAgain;

    private Button btShowAchievement;
    private Button btRegisterAchievement;
    private Button btInitAchievement;
    private Button btFetchAllAchievement;
    private Button btFetchUserAchievement;
    private Button btFGrowAchievement;
    private Button btFReachAchievement;
    private Button btSwitchToastAchievement;
    private Button btSnapchatCreatsc;
    private Button btSnapchatSavesc;
    private Button btSnapchatQueryCurrentUserAll;
    private Button btSnapchatQueryByConditon;

    private Button btRankingListSubmitAchievement;
    private Button btRankingListGetRank;
    private Button btRankingListUpdateStatistic;
    private Button btRankingListGetUserStatistic;
    private Button btRankingListGetResults;

    private Button btAntiAddictionInit;
    private Button btAntiAddictionTapLogin;
    private Button btAntiAddictionManual;
    private Button btAntiAddictionAgeRange;
    private Button btAntiAddictionLogout;




    public static final String LeeJiDongID = "61012c565d0493087d3bf63a";
    public static final String LeeJiEunID = "60f2df4fd1773b17a7c43e4f";
    String userID = "";
    String accessToken = "";

    private static final String TAG = "LeeJiEun ===> ";
    // 获取好友申请列表信息保存，多个接口使用到
    public List<LCFriendshipRequest> currentUserLcFriendshipRequests = new ArrayList<>();
    // 删除好友接口使用
    LCFriendship queriedUserLCFriendship = null;
    // 云存档示例
    TapGameSave gameSave;


    // 开发者中心后台应用配置信息
    public static final String TDS_ClientID = "替换为您的ClientID";
    public static final String TDS_ClientToken = "替换为您的ClientToken";
    public static final String TDS_ServerUrl = "替换为您的ServerUrl";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initSDK();

    }

    private void initView() {
        // 初始化按钮
        // 内建账户方式登陆相关
        btTapLogin = findViewById(R.id.btn_tap_login);
        btTapLoginStatus = findViewById(R.id.btn_tap_loginstatus);
        btTapGouhuo = findViewById(R.id.btn_tap_gouhuo);
        btTapLogout = findViewById(R.id.btn_tap_logout);
        btTapLicense = findViewById(R.id.btn_tap_license);
        btTapInitOnly = findViewById(R.id.btn_tap_init_only);
        btTapBinding = findViewById(R.id.btn_tap_binding);
        btTapBindingAgain = findViewById(R.id.btn_tap_binding_again);
        btTapThirdLogin = findViewById(R.id.btn_tap_thirdLogin);
        btTapThirdLoginAgain = findViewById(R.id.btn_tap_thirdLogin_Again);
        // TapTap 单独登陆
        btTapLoginOnly = findViewById(R.id.btn_tap_login_only);
        btTapLoginStatusOnly = findViewById(R.id.btn_tap_loginstatus_only);
        btTapLogoutOnly = findViewById(R.id.btn_tap_logout_only);

        // 防沉迷
        btAntiAddictionInit = findViewById(R.id.btn_tap_antiaddiction_init);
        btAntiAddictionTapLogin = findViewById(R.id.btn_tap_antiaddiction_taplogin);
        btAntiAddictionManual = findViewById(R.id.btn_tap_antiaddiction_manual);
        btAntiAddictionAgeRange = findViewById(R.id.btn_tap_antiaddiction_agerange);
        btAntiAddictionLogout = findViewById(R.id.btn_tap_antiaddiction_logout);

        // 内嵌动态相关
        btTapFetchNotification = findViewById(R.id.btn_tap_fetch_notification);
        btTapOpenMoment = findViewById(R.id.btn_tap_open_moment);
        btTapDirectlyOpen = findViewById(R.id.btn_tap_directly_open);
        btTapCloseMoment = findViewById(R.id.btn_tap_close_moment);
        btTapOneKeyPublish = findViewById(R.id.btn_tap_one_key_publish);
        // 好友相关
        btTapFriendRequestStatus = findViewById(R.id.btn_tap_friend_request_status);
        btTapAddFriend = findViewById(R.id.btn_tap_add_friend);
        btTapGetFriendRequestList = findViewById(R.id.btn_tap_getfriend_request_list);
        btTapDeclineFriendshipRequest = findViewById(R.id.btn_tap_decline_friendship_request);
        btTapAcceptFriendshipRequest = findViewById(R.id.btn_tap_accept_friendship_request);
        btTapDeleteFriendshipRequest = findViewById(R.id.btn_tap_delete_friendship_request);
        btTapGetFriendsList = findViewById(R.id.btn_tap_getfriends_list);
        btTapDeleteFriend = findViewById(R.id.btn_tap_delete_friend);
        btTapQueryFriend = findViewById(R.id.btn_tap_query_friend);
        // 成就相关
        btRegisterAchievement = findViewById(R.id.btn_tap_achieve_register);
        btInitAchievement = findViewById(R.id.btn_tap_achieve_init);
        btFetchAllAchievement = findViewById(R.id.btn_tap_achieve_fetch_all);
        btFetchUserAchievement = findViewById(R.id.btn_tap_achieve_fetch_user);
        btFReachAchievement = findViewById(R.id.btn_tap_achieve_reach);
        btFGrowAchievement = findViewById(R.id.btn_tap_achieve_grow);
        btSwitchToastAchievement = findViewById(R.id.btn_tap_achieve_toast);
        btShowAchievement = findViewById(R.id.btn_tap_achieve_open);
        // 云存档相关
        btSnapchatCreatsc = findViewById(R.id.btn_tap_snapchat_creatsc);
        btSnapchatSavesc = findViewById(R.id.btn_tap_snapchat_savesc);
        btSnapchatQueryCurrentUserAll = findViewById(R.id.btn_tap_snapchat_current_user);
        btSnapchatQueryByConditon = findViewById(R.id.btn_tap_snapchat_current_user_condition);

        // 排行榜
        btRankingListSubmitAchievement = findViewById(R.id.btn_tap_rankinglist_submit_achievement);
        btRankingListGetRank = findViewById(R.id.btn_tap_rankinglist_get_rank);
        btRankingListUpdateStatistic = findViewById(R.id.btn_tap_rankinglist_update_statistic);
        btRankingListGetUserStatistic = findViewById(R.id.btn_tap_rankinglist_get_user_statistic);
        btRankingListGetResults = findViewById(R.id.btn_tap_rankinglist_get_results);


        // 注册监听器
        btTapLogin.setOnClickListener(this);
        btTapLoginOnly.setOnClickListener(this);
        btTapLoginStatus.setOnClickListener(this);
        btTapLoginStatusOnly.setOnClickListener(this);
        btTapGouhuo.setOnClickListener(this);
        btTapLogout.setOnClickListener(this);
        btTapLogoutOnly.setOnClickListener(this);
        btTapLicense.setOnClickListener(this);
        btTapInitOnly.setOnClickListener(this);
        btTapBinding.setOnClickListener(this);
        btTapBindingAgain.setOnClickListener(this);
        btTapThirdLogin.setOnClickListener(this);
        btTapThirdLoginAgain.setOnClickListener(this);
        // 防沉迷
        btAntiAddictionInit.setOnClickListener(this);
        btAntiAddictionTapLogin.setOnClickListener(this);
        btAntiAddictionManual.setOnClickListener(this);
        btAntiAddictionAgeRange.setOnClickListener(this);
        btAntiAddictionLogout.setOnClickListener(this);
        // 内嵌动态相关
        btTapFetchNotification.setOnClickListener(this);
        btTapOpenMoment.setOnClickListener(this);
        btTapDirectlyOpen.setOnClickListener(this);
        btTapCloseMoment.setOnClickListener(this);
        btTapOneKeyPublish.setOnClickListener(this);

        // 好友相关
        btTapFriendRequestStatus.setOnClickListener(this);
        btTapAddFriend.setOnClickListener(this);
        btTapGetFriendRequestList.setOnClickListener(this);
        btTapDeclineFriendshipRequest.setOnClickListener(this);
        btTapAcceptFriendshipRequest.setOnClickListener(this);
        btTapDeleteFriendshipRequest.setOnClickListener(this);
        btTapGetFriendsList.setOnClickListener(this);
        btTapDeleteFriend.setOnClickListener(this);
        btTapQueryFriend.setOnClickListener(this);

        // 成就相关
        btRegisterAchievement.setOnClickListener(this);
        btInitAchievement.setOnClickListener(this);
        btFetchAllAchievement.setOnClickListener(this);
        btFetchUserAchievement.setOnClickListener(this);
        btFReachAchievement.setOnClickListener(this);
        btFGrowAchievement.setOnClickListener(this);
        btSwitchToastAchievement.setOnClickListener(this);
        btShowAchievement.setOnClickListener(this);

        // 云存档相关
        btSnapchatCreatsc.setOnClickListener(this);
        btSnapchatSavesc.setOnClickListener(this);
        btSnapchatQueryCurrentUserAll.setOnClickListener(this);
        btSnapchatQueryByConditon.setOnClickListener(this);

        // 排行榜
        btRankingListSubmitAchievement.setOnClickListener(this);
        btRankingListGetRank.setOnClickListener(this);
        btRankingListUpdateStatistic.setOnClickListener(this);
        btRankingListGetUserStatistic.setOnClickListener(this);
        btRankingListGetResults.setOnClickListener(this);


    }

    /*
     * TapSDK 初始化，TapDB初始化，内嵌动态初始化
     * */
    public void initSDK() {
        LeanCloud.setLogLevel(LCLogger.Level.DEBUG);
        WebView.setWebContentsDebuggingEnabled(true);
        // 内建账户方式登陆 SDK 初始化
        // TapDB 初始化
        TapDBConfig tapDBConfig = new TapDBConfig();
        tapDBConfig.setEnable(true);
        tapDBConfig.setChannel("gameChannel");
        tapDBConfig.setGameVersion("gameVersion");
        // TapSDK 初始化
        TapConfig tapConfig = new TapConfig.Builder()
                .withAppContext(getApplicationContext())
                .withRegionType(TapRegionType.CN) // TapRegionType.CN: 国内  TapRegionType.IO: 国外
                // 自己账号
                .withClientId(TDS_ClientID)
                .withClientToken(TDS_ClientToken)
                /* 如果使用 单独 TapTap 授权，则不需要配置自定义域名 */
                .withServerUrl(TDS_ServerUrl)
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
                if(code == TapMoment.CALLBACK_CODE_LOGIN_SUCCESS){
                    // 动态内登陆成功
                    Toast.makeText(MainActivity.this, "动态内登陆成功： " + msg, Toast.LENGTH_SHORT).show();
                }
                if(code == TapMoment.CALLBACK_CODE_PUBLISH_SUCCESS){
                    // 动态发布成功
                    Toast.makeText(MainActivity.this, "动态发布成功： " + msg, Toast.LENGTH_SHORT).show();
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
            case R.id.btn_tap_binding:
                // 绑定
                taptapBinding();
                break;
            case R.id.btn_tap_binding_again:
                // 再次绑定
                taptapBindingAgain();
                break;
            case R.id.btn_tap_thirdLogin:
                // 第三方登陆
                taptapThirdLogin();
                break;
            case R.id.btn_tap_thirdLogin_Again:
                // 第三方登陆 Again
                taptapThirdLoginAgain();
                break;
            case R.id.btn_tap_init_only:
                // TapTap 单独 登陆功能
                taptapInitOnly();
                break;
            case R.id.btn_tap_login_only:
                // TapTap 单独 登陆功能
                taptapLoginOnly();
                break;
            case R.id.btn_tap_loginstatus_only:
                // TapTap 单独 登陆状态
                taptapLoginStatusOnly();
                break;
            case R.id.btn_tap_logout_only:
                // TapTap 单独 登出功能
                taptapLogoutOnly();
                break;
                // 防沉迷
            case R.id.btn_tap_antiaddiction_init:
                // 防沉迷初始化
                taptapAntiAddictionInit();
                break;
            case R.id.btn_tap_antiaddiction_taplogin:
                // 防沉迷 TapLogin
                taptapAntiAddictionTapLogin();
                break;
            case R.id.btn_tap_antiaddiction_manual:
                // 防沉迷手动实名
                taptapAntiAddictionManual();
                break;
            case R.id.btn_tap_antiaddiction_agerange:
                // 防沉迷年龄段查询
                taptapAntiAddictionAgeRange();
                break;
            case R.id.btn_tap_antiaddiction_logout:
                // 防沉迷登出功能
                taptapAntiAddictionLogout();
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
            case R.id.btn_tap_friend_request_status:
                // 注册监听：响应好友变化通知，应该要先进行注册监听
                taptapFriendshipRequestStatus();
                break;
            case R.id.btn_tap_add_friend:
                // 申请成为好友
                taptapAddFriend();
                break;
            case R.id.btn_tap_getfriend_request_list:
                // 获取好友申请列表
                taptapGetFriendRequestList();
                break;
            case R.id.btn_tap_decline_friendship_request:
                // 处理好友申请：拒绝
                taptapDeclineFriendshipRequest();
                break;
            case R.id.btn_tap_accept_friendship_request:
                // 处理好友申请：同意
                taptapAcceptFriendshipRequest();
                break;
            case R.id.btn_tap_delete_friendship_request:
                // 处理好友申请：无视删除添加好友请求
                taptapDeleteFriendshipRequest();
                break;
            case R.id.btn_tap_getfriends_list:
                // 获取好友列表
                taptapGetFriendsList();
                break;
            case R.id.btn_tap_delete_friend:
                // 删除好友
                taptapDeleteFriend();
                break;
            case R.id.btn_tap_query_friend:
                // 查询好友关系
                taptapQueryFriend();
                break;
            case R.id.btn_tap_achieve_register:
                // 注册监听回调
                taptapRegisterAchieve();
                break;
            case R.id.btn_tap_achieve_init:
                // 初始化数据
                taptapInitAchieve();
                break;
            case R.id.btn_tap_achieve_fetch_all:
                // 获取全部成就数据
                taptapFetchAllAchieveData();
                break;
            case R.id.btn_tap_achieve_fetch_user:
                // 获取当前用户数据
                taptapFetchUserAchieveData();
                break;
            case R.id.btn_tap_achieve_reach:
                // 达成某个成就
                taptapReachAchieve();
                break;
            case R.id.btn_tap_achieve_grow:
                // 成就增长步数
                taptapGrowAchieve();
                break;
            case R.id.btn_tap_achieve_toast:
                // 设置冒泡开关
                taptapSetShowToast();
                break;
            case R.id.btn_tap_achieve_open:
                // 打开成就展示页
                taptapShowAchieve();
                break;
            case R.id.btn_tap_snapchat_creatsc:
                // 构建云存档数据
                taptapSnapshotCreated();
                break;
            case R.id.btn_tap_snapchat_savesc:
                // 保存存档
                taptapSnapshotSave();
                break;
            case R.id.btn_tap_snapchat_current_user:
                // 查询当前用户的所有存档
                taptapSnapshotQueryCurrentAll();
                break;
            case R.id.btn_tap_snapchat_current_user_condition:
                // 根据条件查询当前用户存档
                taptapSnapshotQueryByconditon();
                break;
            case R.id.btn_tap_rankinglist_submit_achievement:
                // 提交成绩
                taptapSubmitAchievement();
                break;
            case R.id.btn_tap_rankinglist_get_rank:
                // 获取名次
                taptapGetRank();
                break;
            case R.id.btn_tap_rankinglist_update_statistic:
                // 更新用户信息
                taptapUpdateStatistic();
                break;
            case R.id.btn_tap_rankinglist_get_user_statistic:
                // 查询排行榜成员成绩
                taptapGetUserStatistic();
                break;
            case R.id.btn_tap_rankinglist_get_results:
                // 获取指定区间的排名(Top 10)
                taptapRankingListGetResults();
                break;


        }
    }

    private void taptapAntiAddictionLogout() {
        AntiAddictionUIKit.logout();
    }

    private void taptapAntiAddictionAgeRange() {
        int ageRange = AntiAddictionKit.currentUserAgeLimit();
        Log.d(TAG, "玩家段年龄段是：" + String.valueOf(ageRange));
    }

    // 手动认证
    private void taptapAntiAddictionManual() {
        String userIdentifier = "XXXXXXXXXXXXXXX";
        // 进行实名认证的初始化时将 enableTapLogin(false) 即可
        AntiAddictionUIKit.startup(this, userIdentifier);
    }

    // Tap 快速认证
    private void taptapAntiAddictionTapLogin() {
        String userIdentifier = "XXXXXXXX-XXXXX";
        AntiAddictionUIKit.startup(this, userIdentifier);
    }
    private void taptapAntiAddictionInit() {
        // Android SDK 的各接口第一个参数是当前 Activity，以下不再说明
        Config config = new Config.Builder()
                .withClientId(TDS_ClientID) // TapTap 开发者中心对应 Client ID
                .enableTapLogin(true)       // 是否启动 TapTap 快速认证, 如果使用手动验证，设置为 false 即可
                .showSwitchAccount(false)   // 是否显示切换账号按钮
                .build();
        AntiAddictionUIKit.init(this, config, (code, extras) -> {
            switch (code){
                case Constants.ANTI_ADDICTION_CALLBACK_CODE.LOGIN_SUCCESS:
                    Log.d(TAG, "防沉迷登陆成功");
                    break;
                case Constants.ANTI_ADDICTION_CALLBACK_CODE.EXITED:
                    Log.d(TAG, "退出账号");
                    break;
                case Constants.ANTI_ADDICTION_CALLBACK_CODE.DURATION_LIMIT:
                    Log.d(TAG, "时长限制");
                    break;
                case Constants.ANTI_ADDICTION_CALLBACK_CODE.PERIOD_RESTRICT:
                    Log.d(TAG, "防沉迷未成年玩家无法进行游戏");
                    break;
                case Constants.ANTI_ADDICTION_CALLBACK_CODE.REAL_NAME_STOP:
                    Log.d(TAG, "防沉迷实名认证过程中点击了关闭实名窗");
                    break;
                case Constants.ANTI_ADDICTION_CALLBACK_CODE.SWITCH_ACCOUNT:
                    Log.d(TAG, "防沉迷实名认证过程中点击了切换账号按钮");
                    break;
            }
        });

    }

    private void taptapThirdLogin() {
        Map<String, Object> thirdPartyData = new HashMap<String, Object>();
        // 必须
        thirdPartyData.put("expires_in", 930516);
        thirdPartyData.put("openid", "LeeJiEun_Giant_Openid");
        thirdPartyData.put("access_token", "LeeJiEun_Giant_ACCESS_TOKEN");
        // 可选
        thirdPartyData.put("refresh_token", "LeeJiEun_Giant_REFRESH_TOKEN");
        thirdPartyData.put("scope", "LeeJiEun_Giant_SCOPE");
        TDSUser.loginWithAuthData(TDSUser.class, thirdPartyData, "giant").subscribe(new Observer<TDSUser>() {
            public void onSubscribe(Disposable disposable) {
            }
            public void onNext(TDSUser user) {
                Log.d(TAG, "第三方登陆成功");
                Log.d(TAG, user.toJSONString());
            }
            public void onError(Throwable throwable) {
                Log.d(TAG, "尝试使用第三方账号登录，发生错误: " + throwable.getMessage());
            }
            public void onComplete() {
            }
        });
    }

    private void taptapThirdLoginAgain() {
        Map<String, Object> thirdPartyData = new HashMap<String, Object>();
        // 必须
        thirdPartyData.put("expires_in", 19930516);
        thirdPartyData.put("openid", "LeeJiEun_Giant_Openid_Again");
        thirdPartyData.put("access_token", "LeeJiEun_Giant_ACCESS_TOKEN_Again");
        // 可选
        thirdPartyData.put("refresh_token", "LeeJiEun_Giant_REFRESH_TOKEN_Again");
        thirdPartyData.put("scope", "LeeJiEun_Giant_SCOPE_Again");
        TDSUser.loginWithAuthData(TDSUser.class, thirdPartyData, "taptap").subscribe(new Observer<TDSUser>() {
            public void onSubscribe(Disposable disposable) {
            }
            public void onNext(TDSUser user) {
                Log.d(TAG, "第三方再次登陆成功");
                Log.d(TAG, user.toJSONString());
            }
            public void onError(Throwable throwable) {
                Log.d(TAG, "尝试使用第三方账号再次登录，发生错误: " + throwable.getMessage());
            }
            public void onComplete() {
            }
        });
    }

    private void taptapBinding() {
        Map<String, Object> thirdPartyData = new HashMap<String, Object>();
        // 必须
        thirdPartyData.put("expires_in", 7200);
        thirdPartyData.put("openid", "BANDINGpcb6jiHAjB82k8H9MjKkiQ==");
        thirdPartyData.put("access_token", "BandingHAHAHAHAHA1/e3fyiwgwBGXeor93rQGfB_qfOpsArBYvze6W7zmV73zxKH1mpJUgehCWRbj0-c-ZrTlSV3qlAAaQW1C4tjJFlZjjxlvpJhGQ0JXHX7bfZwwKxiI8DJ0zu5XXOmE2LdwRXXMjbI0Syeuua5Ym5W2uK-JNfinO2jen6Sb7p_1GeJF-j3W_6nmYZPVJSP9BQap5b61zLOZ1c0r7-5t3d1Id-TeAj8Km78tj4rZ1QkLzgUFauRSxvHKMhkPOzW3LDVpMw3dns5B2Am_hw5ybgAOT0PDdVVRNe68DWz1JySB2G5ARPwDLonYwn13-_BoPl9ldaTK_ogF9chFmfLF_V5DFKg");
        // 可选
        thirdPartyData.put("taptap_name", "lrj3zwhy01pr4ltbu4hiww2ba");

        TDSUser currentUser = TDSUser.getCurrentUser();
        currentUser.associateWithAuthData(thirdPartyData, "taptap").subscribe(new Observer<LCUser>() {
            @Override
            public void onSubscribe(@androidx.annotation.NonNull Disposable d) {

            }

            @Override
            public void onNext(@androidx.annotation.NonNull LCUser lcUser) {
                Log.d(TAG, "绑定成功！");
                Log.d(TAG, "绑定成功 TDSUserID : " + lcUser.getObjectId());

            }

            @Override
            public void onError(@androidx.annotation.NonNull Throwable e) {
                Log.d(TAG, "绑定失败： " + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void taptapBindingAgain() {
        Map<String, Object> thirdPartyData = new HashMap<String, Object>();
        // 必须
        thirdPartyData.put("expires_in", 7200);
        thirdPartyData.put("openid", "WanWan_QDNJfr2wFRRmFu8oqL2pCg==");
        thirdPartyData.put("access_token", "WanWan_1/B2sMNYgxvmuwNPg82IEuOZIAoT30WmB-L2FkHUxprcF39RCBTlFVKbcV_fHSMvSQMp5m_9cLnC78GzimhvGll4t8R0X5Vp_KAiTVk-JrnunHKYObD310JM5HikHz6YMaex9TPVaDtZV1jCFVZo1cUfDlCrpmm3o0urx_LZYqTamvDU_JnZTyunq7lD-2YI_LVekpqP5ZznhvcfyLA-r48lrwa1FuZM3cQygH5H_xvYTHHP1pPiPOPhzhZWJu7NP9Ya6ReNKPpMtAiFXnzokVhB1QKfcaPhYr9g60ogY6a3vii2Jn-hCWV61NqLFhGl3HoiWBmw7F1BQ4FnbbVidyHQ");
        // 可选
        thirdPartyData.put("refresh_token", "WanWan_TapTap_REFRESH_TOKEN");
        thirdPartyData.put("scope", "WanWan_TapTap_SCOPE");
        TDSUser currentUser = TDSUser.getCurrentUser();
        currentUser.associateWithAuthData(thirdPartyData, "TapTap").subscribe(new Observer<LCUser>() {
            @Override
            public void onSubscribe(@androidx.annotation.NonNull Disposable d) {

            }

            @Override
            public void onNext(@androidx.annotation.NonNull LCUser lcUser) {
                Log.d(TAG, "再次绑定成功！");
                Log.d(TAG, "再次绑定成功 TDSUserID : " + lcUser.getObjectId());

            }

            @Override
            public void onError(@androidx.annotation.NonNull Throwable e) {
                Log.d(TAG, "绑定失败： " + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void taptapRankingListGetResults() {
        // 获取指定区间的排名(Top 10)
        LCLeaderboard leaderboard = LCLeaderboard.createWithoutData("Uaena");
        leaderboard.getResults(0, 10, null, null).subscribe(new Observer<LCLeaderboardResult>() {
            @Override
            public void onSubscribe(@NotNull Disposable disposable) {}

            @Override
            public void onNext(@NotNull LCLeaderboardResult leaderboardResult) {
                List<LCRanking> rankings = leaderboardResult.getResults();
                for (LCRanking lcr : rankings){
                    Log.d(TAG, lcr.toString());
                }
            }

            @Override
            public void onError(@NotNull Throwable throwable) {
                // handle error
            }

            @Override
            public void onComplete() {}
        });
    }

    private void taptapGetUserStatistic() {
        // 查询排行榜成员成绩
        LCUser otherUser = null;
        try {
            otherUser = LCUser.createWithoutData(LCUser.class, LeeJiDongID);
        } catch (LCException e) {
            e.printStackTrace();
        }
        LCLeaderboard.getUserStatistics(otherUser).subscribe(new Observer<LCStatisticResult>() {
            @Override
            public void onSubscribe(@NotNull Disposable disposable) {}

            @Override
            public void onNext(@NotNull LCStatisticResult lcStatisticResult) {
                List<LCStatistic> statistics = lcStatisticResult.getResults();
                for (LCStatistic statistic : statistics) {
                    Log.d(TAG, statistic.getName());
                    Log.d(TAG, String.valueOf(statistic.getValue()));
                }
            }

            @Override
            public void onError(@NotNull Throwable throwable) {
                // handle error
                Toast.makeText(MainActivity.this, "查询失败： " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {}
        });
    }

    private void taptapUpdateStatistic() {
        // 更新用户信息
        TDSUser currentUser = TDSUser.getCurrentUser();
        if (null == currentUser){
            Toast.makeText(MainActivity.this, "请先执行 TapTap 登陆", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, Double> statistic  = new HashMap<>();
        statistic.put("score", 3458.0);
        statistic.put("kills", 28.0);
        LCLeaderboard.updateStatistic(currentUser, statistic).subscribe(new Observer<LCStatisticResult>() {
            @Override
            public void onSubscribe(@NotNull Disposable disposable) {}

            @Override
            public void onNext(@NotNull LCStatisticResult jsonObject) {
                // saved
                Toast.makeText(MainActivity.this, "更新成功！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(@NotNull Throwable throwable) {
                // handle error
                Toast.makeText(MainActivity.this, "更新失败： " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {}
        });
    }

    private void taptapGetRank() {
        // 获取名次
        LCLeaderboard leaderboard = LCLeaderboard.createWithoutData("world");
        leaderboard.getResults(0, 10, null, null).subscribe(new Observer<LCLeaderboardResult>() {
            @Override
            public void onSubscribe(@NotNull Disposable disposable) {}

            @Override
            public void onNext(@NotNull LCLeaderboardResult leaderboardResult) {
                List<LCRanking> rankings = leaderboardResult.getResults();
                // process rankings
                for (LCRanking lr : rankings){
                    Log.d(TAG, String.valueOf(lr.getRank()));
                }
            }

            @Override
            public void onError(@NotNull Throwable throwable) {
                // handle error
            }

            @Override
            public void onComplete() {}
        });
    }

    private void taptapSubmitAchievement() {
        // 提交成绩
        TDSUser currentUser = TDSUser.getCurrentUser();
        if (null == currentUser){
            Toast.makeText(MainActivity.this, "请先执行 TapTap 登陆", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.d(TAG, currentUser.toJSONString());
        Map<String, Double> statistic = new HashMap<>();
        statistic.put("Uaena", 26.0);
        LCLeaderboard.updateStatistic(currentUser, statistic).subscribe(new Observer<LCStatisticResult>() {
            @Override
            public void onSubscribe(@androidx.annotation.NonNull Disposable d) {

            }

            @Override
            public void onNext(@androidx.annotation.NonNull LCStatisticResult lcStatisticResult) {
                Toast.makeText(MainActivity.this, "成绩提交成功！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(@androidx.annotation.NonNull Throwable e) {
                Toast.makeText(MainActivity.this, "成绩提交失败： " + e.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, "成绩提交失败： " + e.toString(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, e.toString());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void taptapInitOnly() {
        LoginSdkConfig loginSdkConfig = new LoginSdkConfig(true, true, RegionType.CN);
        TapLoginHelper.init(getApplicationContext(), "0RiAlMny7jiz086FaU", loginSdkConfig);
//        TapLoginHelper.init(getApplicationContext(), "6Rap5XF2ncLQB2oIiW", loginSdkConfig);

    }

    private void taptapLogoutOnly() {
        TapLoginHelper.logout();

    }

    private void taptapLoginStatusOnly() {
        // 判断登陆后的 AccessToken 是否为空
        if(TapLoginHelper.getCurrentAccessToken() == null ){
            Toast.makeText(MainActivity.this, "未登陆", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(MainActivity.this, "已登陆", Toast.LENGTH_SHORT).show();
        }
    }

    private void taptapLoginOnly() {
        TapLoginHelper.TapLoginResultCallback loginCallback = new TapLoginHelper.TapLoginResultCallback() {
            @Override
            public void onLoginSuccess(AccessToken token) {
                Log.d(TAG, "TapTap authorization succeed");
                // 开发者调用 TapLoginHelper.getCurrentProfile() 可以获得当前用户的一些基本信息，例如名称、头像。
                Profile profile = TapLoginHelper.getCurrentProfile();
            }

            @Override
            public void onLoginCancel() {
                Log.d(TAG, "TapTap authorization cancelled");
            }

            @Override
            public void onLoginError(AccountGlobalError globalError) {
                Log.d(TAG, "TapTap authorization failed. cause: " + globalError.getMessage());
            }
        };
        TapLoginHelper.registerLoginCallback(loginCallback);
        TapLoginHelper.startTapLogin(MainActivity.this, TapLoginHelper.SCOPE_PUBLIC_PROFILE);
    }

    private void taptapSnapshotQueryByconditon() {
        LCQuery<TapGameSave> query = TapGameSave.getQueryWithUser();
        // 查询 Summary 为 GameSnapshot_Description 的云存档
        query.whereEqualTo(GAME_SAVE_SUMMARY, "GameSnapshot_Description");
        query.findInBackground()
                .subscribe(new Observer<List<TapGameSave>>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NotNull List<TapGameSave> tapGameSaves) {
                        Log.d(TAG, "gameSave:" + JSON.toJSONString(tapGameSaves));
                        System.out.println("gameSave:" + JSON.toJSONString(tapGameSaves));
                        Toast.makeText(MainActivity.this, "gameSave:" + JSON.toJSONString(tapGameSaves), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        Log.d(TAG, "查询失败： " + e.toString());
                        Log.d(TAG, "查询失败： " + e.getMessage());
                        Toast.makeText(MainActivity.this, "查询失败： " + e.toString(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void taptapSnapshotQueryCurrentAll() {
        TapGameSave.getCurrentUserGameSaves()
                .subscribe(new Observer<List<TapGameSave>>() {
                    @Override
                    public void onSubscribe(@androidx.annotation.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@androidx.annotation.NonNull List<TapGameSave> tapGameSaves) {
                        Log.d(TAG, "gameSave:" + JSON.toJSONString(tapGameSaves));
                        System.out.println("gameSave:" + JSON.toJSONString(tapGameSaves));
                        Toast.makeText(MainActivity.this, "gameSave:" + JSON.toJSONString(tapGameSaves), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@androidx.annotation.NonNull Throwable e) {
                        Log.d(TAG, "查询失败： " + e.getMessage());
                        Log.d(TAG, "查询失败： " + e.toString());
                        Toast.makeText(MainActivity.this, "查询失败： " + e.toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void taptapSnapshotSave() {
        gameSave.saveInBackground().subscribe(new Observer<TapGameSave>() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {

            }

            @Override
            public void onNext(@NotNull TapGameSave gameSave) {
                Log.d(TAG, "存档保存成功：" + gameSave.toJSONString());
                System.out.println("存档保存成功：" + gameSave.toJSONString());
                Toast.makeText(MainActivity.this, "存档保存成功：" + gameSave.toJSONString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(@NotNull Throwable e) {
                Log.d(TAG, "存档保存失败：" + e.getMessage());
                Log.d(TAG, "存档保存失败：" + e.toString());
                Toast.makeText(MainActivity.this, "存档保存失败： " + e.toString(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        });

    }

    private void taptapSnapshotCreated() {
        gameSave = new TapGameSave();
        // 存档名
        gameSave.setName("GameSnapshot_Name");
        // 存档概览
        gameSave.setSummary("GameSnapshot_Description");
        // 游玩时间
        gameSave.setPlayedTime(999);
        // 游戏进度
        gameSave.setProgressValue(99);
        // 存档封面图，仅支持 png 或者 jpeg 格式
//        gameSave.setCover("cover_path");
        // 存档源文件   /storage/emulated/0/Android/data/packname/cache
        String filePath = getApplicationContext().getExternalCacheDir().getAbsolutePath() + "/" + "LeeJiEun.txt";
        String fileContent = "LeeJiEun You're My Celebtiry";
        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream(filePath);
            fout.write(fileContent.getBytes());
            fout.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (null != fout){
                try {
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        gameSave.setGameFile(filePath);
        // 修改时间
        gameSave.setModifiedAt(new Date());
    }

    boolean flagShow = false;
    private void taptapSetShowToast() {
        flagShow = !flagShow;
        if(flagShow){
            Toast.makeText(this, "冒泡开关关闭", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "冒泡开关开启", Toast.LENGTH_SHORT).show();
        }
        TapAchievement.setShowToast(flagShow);
    }

    private void taptapGrowAchieve() {
        // 成就增长步数提供两种方式调用，
        // growSteps 中传递当前增量达成的步数（例如：多走了5步，则传递5即可），
        // makeSteps 中传递当前成就已达成的步数
        // displayID 是在开发者中心中添加成就时自行设定的 成就ID
        // TapAchievement.growSteps("displayID", 5);
        TapAchievement.makeSteps("displayID", 100);
    }

    private void taptapReachAchieve() {
        // displayID 是在开发者中心中添加成就时自行设定的 成就ID
        TapAchievement.reach("displayID");
    }

    private void taptapFetchUserAchieveData() {
        // 本地数据
        List<TapAchievementBean> userList = TapAchievement.getLocalUserAchievementList();
        if(null != userList && !userList.isEmpty()){
            for (TapAchievementBean tab : userList){
                Log.d(TAG, tab.toString());
            }
            Toast.makeText(this, userList.toString(), Toast.LENGTH_SHORT).show();
        }

        // 服务端数据
        TapAchievement.fetchUserAchievementList(new GetAchievementListCallBack() {
            @Override
            public void onGetAchievementList(List<TapAchievementBean> achievementList, AchievementException exception) {
                if (exception != null) {
                    switch (exception.errorCode) {
                        case AchievementException.SDK_NOT_INIT:
                            // SDK 还未初始化数据
                            Log.d(TAG, "SDK 还未初始化数据");
                            Toast.makeText(MainActivity.this, "SDK 还未初始化数据", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            // 数据获取失败
                            Log.d(TAG, "数据获取失败");
                            Toast.makeText(MainActivity.this, "数据获取失败", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // 成功获取数据
                    Log.d(TAG, "成功获取数据");
                    Toast.makeText(MainActivity.this, "成功获取数据", Toast.LENGTH_SHORT).show();
                    if(!achievementList.isEmpty()){
                        for(TapAchievementBean tab: achievementList){
                            Log.d(TAG, tab.toString());
                        }
                    }
                }
            }
        });
    }

    private void taptapFetchAllAchieveData() {
        // 本地数据
        List<TapAchievementBean> allList = TapAchievement.getLocalAllAchievementList();
        if(allList == null && !allList.isEmpty()){
            for(TapAchievementBean tab : allList){
                Log.d(TAG, tab.toString());
            }
            Toast.makeText(this, allList.toString(), Toast.LENGTH_SHORT).show();
        }

        // 服务端数据
        TapAchievement.fetchAllAchievementList(new GetAchievementListCallBack() {
            @Override
            public void onGetAchievementList(List<TapAchievementBean> achievementList, AchievementException exception) {
                if (exception != null) {
                    switch (exception.errorCode) {
                        case AchievementException.SDK_NOT_INIT:
                            // SDK 还未初始化数据
                            Log.d(TAG, "SDK 还未初始化数据");
                            Toast.makeText(MainActivity.this, "SDK 还未初始化数据", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            // 数据获取失败
                            Log.d(TAG, "数据获取失败");
                            Toast.makeText(MainActivity.this, "数据获取失败", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // 成功获取数据
                    Log.d(TAG, "成功获取数据");
                    Toast.makeText(MainActivity.this, "成功获取数据", Toast.LENGTH_SHORT).show();
                    if(!achievementList.isEmpty()){
                        for(TapAchievementBean tab: achievementList){
                            Log.d(TAG, tab.toString());
                        }
                    }
                }
            }
        });
    }

    private void taptapInitAchieve() {
        TapAchievement.initData();
    }

    private void taptapRegisterAchieve() {
        TapAchievement.registerCallback(new AchievementCallback() {
            @Override
            public void onAchievementSDKInitSuccess() {
                // 数据加载成功
                Log.d(TAG, "数据加载成功！");
                Toast.makeText(MainActivity.this, "数据加载成功！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAchievementSDKInitFail(AchievementException exception) {
                // 数据加载失败，请重试
                Log.d(TAG, "数据加载失败： " + exception.toString());
                Toast.makeText(MainActivity.this, "数据加载失败： " + exception.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAchievementStatusUpdate(TapAchievementBean item, AchievementException exception) {
                if (exception != null) {
                    // 成就更新失败
                    Log.d(TAG, "成就更新失败: " + exception.toString());
                    Toast.makeText(MainActivity.this, "成就更新失败: " + exception.toString(), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (item != null) {
                    // item 更新成功
                    Log.d(TAG, "成就更新成功");
                    Toast.makeText(MainActivity.this, "成就更新成功", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void taptapShowAchieve() {
        TapAchievement.showAchievementPage();
    }

    private void taptapQueryFriend() {
        TDSUser currentUser = TDSUser.currentUser();
        LCQuery<LCFriendship> query = currentUser.friendshipQuery();
        try {
            query.whereEqualTo(LCFriendship.ATTR_FOLLOWEE, TDSUser.createWithoutData(TDSUser.class, LeeJiEunID));
        } catch (LCException e) {
            e.printStackTrace();
        }
        query.countInBackground().subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {}

            @Override
            public void onNext(Integer result) {
                if (null != result && result > 0) {
                    Log.d(TAG,"CurrentUser is a friend of QueriedUser");
;                } else {
                    Log.d(TAG,"CurrentUser isn't a friend of QueriedUser");
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG,"current user failed to query friendship of QueriedUser. cause: " + e.getMessage());
            }

            @Override
            public void onComplete() {}
        });
    }

    private void taptapDeleteFriend() {
        if(null != queriedUserLCFriendship){
            queriedUserLCFriendship.deleteInBackground().subscribe(new Observer<LCNull>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {}

                @Override
                public void onNext(LCNull response) {
                    // succeed to delete friendship.
                    Toast.makeText(MainActivity.this, "删除好友成功！", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    System.out.println("failed to delete friendship cause: " + e.getMessage());
                    Log.d(TAG,"failed to delete friendship of QueriedUser. cause: " + e.getMessage());
                    Toast.makeText(MainActivity.this, "failed to delete friendship of QueriedUser. cause: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onComplete() {}
            });
        }else {
            Toast.makeText(this, "删除好友失败，没有查找到要删除好友的关系！", Toast.LENGTH_SHORT).show();
        }
    }

    private void taptapGetFriendsList() {
        TDSUser currentUser = TDSUser.currentUser();
        LCQuery<LCFriendship> query = currentUser.friendshipQuery();
        query.findInBackground().subscribe(new Observer<List<LCFriendship>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {}

            @Override
            public void onNext(List<LCFriendship> lcFriendships) {
                Log.d(TAG, lcFriendships.toArray().length + "");
                if (null != lcFriendships && !lcFriendships.isEmpty()) {
                    // lcFriendships 即为好友关系
                    Log.d(TAG, lcFriendships.toString());
                    queriedUserLCFriendship = lcFriendships.get(0);
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "failed to query friendship cause: " + e.getMessage());
                Toast.makeText(MainActivity.this, "failed to query friendship cause: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {}
        });
    }

    private void taptapFriendshipRequestStatus() {
        TDSUser currentUser = TDSUser.currentUser();
        TDSUser.FriendshipNotification friendshipNotification = new TDSUser.FriendshipNotification() {
            @Override
            public void onNewRequestComing(LCFriendshipRequest request) {
                Log.d(TAG, request.toString());
                Toast.makeText(MainActivity.this, request.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRequestAccepted(LCFriendshipRequest request) {
                Log.d(TAG, request.toString());
                Toast.makeText(MainActivity.this, request.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRequestDeclined(LCFriendshipRequest request) {
                Log.d(TAG, request.toString());
                Toast.makeText(MainActivity.this, request.toString(), Toast.LENGTH_SHORT).show();
            }
        };

        currentUser.registerFriendshipNotification(friendshipNotification, new LCCallback<LCNull>() {
            @Override
            protected void internalDone0(LCNull lcNull, LCException LCException) {

            }
        });
    }

    private void taptapDeleteFriendshipRequest() {
        TDSUser currentUser = TDSUser.currentUser();
        LCFriendshipRequest queriedRequests = null;
        // leeJiEun 同意了来自 leeJiDong 的好友请求
        for(LCFriendshipRequest fr:currentUserLcFriendshipRequests ){
            Toast.makeText(this, fr.getObjectId(), Toast.LENGTH_SHORT).show();
        }
        if (!currentUserLcFriendshipRequests.isEmpty()){
            queriedRequests = currentUserLcFriendshipRequests.get(0);
        }

        currentUser.deleteFriendshipRequest(queriedRequests).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {}

            @Override
            public void onNext(Boolean result) {}

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("failed to delete friendship request cause: " + e.getMessage());
                Toast.makeText(MainActivity.this, "failed to delete friendship request cause: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {}
        });
    }

    private void taptapAcceptFriendshipRequest() {
        TDSUser currentUser = TDSUser.currentUser();
        LCFriendshipRequest queriedRequests = null;
        // leeJiEun 同意了来自 leeJiDong 的好友请求
        for(LCFriendshipRequest fr:currentUserLcFriendshipRequests ){
            Log.d(TAG, fr.getFriend().getObjectId());
        }
        if (!currentUserLcFriendshipRequests.isEmpty()){
            queriedRequests = currentUserLcFriendshipRequests.get(0);
        }

        currentUser.acceptFriendshipRequest(queriedRequests, null).subscribe(new Observer<LCFriendshipRequest>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {}

            @Override
            public void onNext(LCFriendshipRequest result) {
                Log.d(TAG, result.getFriend().getObjectId());
                Log.d(TAG, "同意好友请求！");
                Toast.makeText(MainActivity.this, "同意好友请求！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG,"failed to accept friendship request cause: " + e.getMessage());
                Toast.makeText(MainActivity.this, "failed to accept friendship request cause: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {}
        });

    }

    private void taptapDeclineFriendshipRequest() {
        TDSUser currentUser = TDSUser.currentUser();
        LCFriendshipRequest queriedRequests = null;
        // leeJiEun 同意了来自 leeJiDong 的好友请求
        for(LCFriendshipRequest fr:currentUserLcFriendshipRequests ){

        }
        if (!currentUserLcFriendshipRequests.isEmpty()){
            queriedRequests = currentUserLcFriendshipRequests.get(0);
        }

        currentUser.declineFriendshipRequest(queriedRequests).subscribe(new Observer<LCFriendshipRequest>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {}

            @Override
            public void onNext(LCFriendshipRequest result) {}

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("failed to decline friendship request cause: " + e.getMessage());
                Toast.makeText(MainActivity.this, "failed to decline friendship request cause: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {}
        });
    }

    private void taptapGetFriendRequestList() {
        TDSUser currentUser = TDSUser.currentUser();
        currentUser.friendshipRequestQuery(LCFriendshipRequest.STATUS_PENDING, false, true)
                .findInBackground()
                .subscribe(new Observer<List<LCFriendshipRequest>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {}

                    @Override
                    public void onNext(List<LCFriendshipRequest> lcFriendshipRequests) {
                        Log.d(TAG, "friendship requests number to leeJiEun is: " +lcFriendshipRequests.size());
                        for(LCFriendshipRequest request: lcFriendshipRequests) {
                            request.getSourceUser();
                            request.getFriend();
                            System.out.println(request);
                            Log.d(TAG, request.toString());
                            Log.d(TAG, request.getFriend().getObjectId());
                            currentUserLcFriendshipRequests.add(request);
                            Toast.makeText(MainActivity.this, request.getFriend().getObjectId(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "failed to query friendship request cause: " + e.getMessage());
                        Toast.makeText(MainActivity.this, "failed to query friendship request cause: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {}
                });
    }

    private void taptapAddFriend() {
        TDSUser currentUser = TDSUser.currentUser();
        if (null == currentUser){
            Toast.makeText(this, "请先登陆！", Toast.LENGTH_SHORT).show();
        }else {
            TDSUser leeJiEun = null;
            Map<String, Object> attr = new HashMap<String, Object>();
            attr.put("group", "SoloQueen");
            try {
                // TODO 第一步更改：测试需要更改这里的 OcjectID
                leeJiEun =  LCObject.createWithoutData(TDSUser.class, LeeJiEunID);


                LCQuery<LCObject> query = new LCQuery<>("TDSUser");


            } catch (LCException e) {
                e.printStackTrace();
            }
            currentUser.applyFriendshipInBackground(leeJiEun, attr).subscribe(new Observer<LCFriendshipRequest>() {
                @Override
                public void onSubscribe(@NotNull Disposable d) {

                }

                @Override
                public void onNext(@NotNull LCFriendshipRequest lcFriendshipRequest) {
                    Toast.makeText(MainActivity.this, "添加成功 ！", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(@NotNull Throwable e) {
                    System.out.println("failed to apply friend request");
                    Toast.makeText(MainActivity.this, "添加失败：" + e.toString(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onComplete() {

                }
            });
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
                if(aBoolean){
                    // 该玩家已拥有测试资格
                    Toast.makeText(MainActivity.this, "该玩家已具有篝火测试资格", Toast.LENGTH_SHORT).show();
                }else {
                    // 该玩家不具备测试资格， 游戏层面进行拦截
                    Toast.makeText(MainActivity.this, "该玩家不具备篝火测试资格", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                // 服务端检查出错或者网络异常
                Toast.makeText(MainActivity.this, "服务端检查出错或者网络异常", Toast.LENGTH_SHORT).show();
                Log.d(TAG,throwable.toString());
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
            String accessToken = TapLoginHelper.getCurrentAccessToken().toJsonString();
            Log.d(TAG, accessToken);
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
                    userID = resultUser.getObjectId();

                    String userName = resultUser.getUsername();
                    String avatar = (String) resultUser.get("avatar");
                    Log.d(TAG, "userID: " + userID);
                    Log.d(TAG, "userName: " +userName);
                    Log.d(TAG, "avatar: "+ avatar);
                    Map<String,Object> authData = (Map<String,Object>)resultUser.get("authData");
                    Map<String,Object> taptapAuthData = (Map<String, Object>) authData.get("taptap");
                    Log.d(TAG,"authData:"+ JSON.toJSONString(authData));
                    Map<String, Object> authDataResult = (Map<String, Object>) ((Map<String, Object>) resultUser.get("authData")).get("taptap");
                    Log.d(TAG, "unionid:"+taptapAuthData.get("unionid").toString());
                    Log.d(TAG, "openid:"+taptapAuthData.get("openid").toString());
                    Toast.makeText(MainActivity.this, "succeed to login with Taptap.", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFail(TapError error) {
                    Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, error.detailMessage);
                    Log.d(TAG, error.getMessage());
                    Log.d(TAG, error.toJSON());
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