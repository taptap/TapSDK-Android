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
    // ????????????????????????????????????????????????????????????
    public List<LCFriendshipRequest> currentUserLcFriendshipRequests = new ArrayList<>();
    // ????????????????????????
    LCFriendship queriedUserLCFriendship = null;
    // ???????????????
    TapGameSave gameSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initSDK();

    }

    private void initView() {
        // ???????????????
        // ??????????????????????????????
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
        // TapTap ????????????
        btTapLoginOnly = findViewById(R.id.btn_tap_login_only);
        btTapLoginStatusOnly = findViewById(R.id.btn_tap_loginstatus_only);
        btTapLogoutOnly = findViewById(R.id.btn_tap_logout_only);

        // ?????????
        btAntiAddictionInit = findViewById(R.id.btn_tap_antiaddiction_init);
        btAntiAddictionTapLogin = findViewById(R.id.btn_tap_antiaddiction_taplogin);
        btAntiAddictionManual = findViewById(R.id.btn_tap_antiaddiction_manual);
        btAntiAddictionAgeRange = findViewById(R.id.btn_tap_antiaddiction_agerange);
        btAntiAddictionLogout = findViewById(R.id.btn_tap_antiaddiction_logout);

        // ??????????????????
        btTapFetchNotification = findViewById(R.id.btn_tap_fetch_notification);
        btTapOpenMoment = findViewById(R.id.btn_tap_open_moment);
        btTapDirectlyOpen = findViewById(R.id.btn_tap_directly_open);
        btTapCloseMoment = findViewById(R.id.btn_tap_close_moment);
        btTapOneKeyPublish = findViewById(R.id.btn_tap_one_key_publish);
        // ????????????
        btTapFriendRequestStatus = findViewById(R.id.btn_tap_friend_request_status);
        btTapAddFriend = findViewById(R.id.btn_tap_add_friend);
        btTapGetFriendRequestList = findViewById(R.id.btn_tap_getfriend_request_list);
        btTapDeclineFriendshipRequest = findViewById(R.id.btn_tap_decline_friendship_request);
        btTapAcceptFriendshipRequest = findViewById(R.id.btn_tap_accept_friendship_request);
        btTapDeleteFriendshipRequest = findViewById(R.id.btn_tap_delete_friendship_request);
        btTapGetFriendsList = findViewById(R.id.btn_tap_getfriends_list);
        btTapDeleteFriend = findViewById(R.id.btn_tap_delete_friend);
        btTapQueryFriend = findViewById(R.id.btn_tap_query_friend);
        // ????????????
        btRegisterAchievement = findViewById(R.id.btn_tap_achieve_register);
        btInitAchievement = findViewById(R.id.btn_tap_achieve_init);
        btFetchAllAchievement = findViewById(R.id.btn_tap_achieve_fetch_all);
        btFetchUserAchievement = findViewById(R.id.btn_tap_achieve_fetch_user);
        btFReachAchievement = findViewById(R.id.btn_tap_achieve_reach);
        btFGrowAchievement = findViewById(R.id.btn_tap_achieve_grow);
        btSwitchToastAchievement = findViewById(R.id.btn_tap_achieve_toast);
        btShowAchievement = findViewById(R.id.btn_tap_achieve_open);
        // ???????????????
        btSnapchatCreatsc = findViewById(R.id.btn_tap_snapchat_creatsc);
        btSnapchatSavesc = findViewById(R.id.btn_tap_snapchat_savesc);
        btSnapchatQueryCurrentUserAll = findViewById(R.id.btn_tap_snapchat_current_user);
        btSnapchatQueryByConditon = findViewById(R.id.btn_tap_snapchat_current_user_condition);

        // ?????????
        btRankingListSubmitAchievement = findViewById(R.id.btn_tap_rankinglist_submit_achievement);
        btRankingListGetRank = findViewById(R.id.btn_tap_rankinglist_get_rank);
        btRankingListUpdateStatistic = findViewById(R.id.btn_tap_rankinglist_update_statistic);
        btRankingListGetUserStatistic = findViewById(R.id.btn_tap_rankinglist_get_user_statistic);
        btRankingListGetResults = findViewById(R.id.btn_tap_rankinglist_get_results);


        // ???????????????
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
        // ?????????
        btAntiAddictionInit.setOnClickListener(this);
        btAntiAddictionTapLogin.setOnClickListener(this);
        btAntiAddictionManual.setOnClickListener(this);
        btAntiAddictionAgeRange.setOnClickListener(this);
        btAntiAddictionLogout.setOnClickListener(this);
        // ??????????????????
        btTapFetchNotification.setOnClickListener(this);
        btTapOpenMoment.setOnClickListener(this);
        btTapDirectlyOpen.setOnClickListener(this);
        btTapCloseMoment.setOnClickListener(this);
        btTapOneKeyPublish.setOnClickListener(this);

        // ????????????
        btTapFriendRequestStatus.setOnClickListener(this);
        btTapAddFriend.setOnClickListener(this);
        btTapGetFriendRequestList.setOnClickListener(this);
        btTapDeclineFriendshipRequest.setOnClickListener(this);
        btTapAcceptFriendshipRequest.setOnClickListener(this);
        btTapDeleteFriendshipRequest.setOnClickListener(this);
        btTapGetFriendsList.setOnClickListener(this);
        btTapDeleteFriend.setOnClickListener(this);
        btTapQueryFriend.setOnClickListener(this);

        // ????????????
        btRegisterAchievement.setOnClickListener(this);
        btInitAchievement.setOnClickListener(this);
        btFetchAllAchievement.setOnClickListener(this);
        btFetchUserAchievement.setOnClickListener(this);
        btFReachAchievement.setOnClickListener(this);
        btFGrowAchievement.setOnClickListener(this);
        btSwitchToastAchievement.setOnClickListener(this);
        btShowAchievement.setOnClickListener(this);

        // ???????????????
        btSnapchatCreatsc.setOnClickListener(this);
        btSnapchatSavesc.setOnClickListener(this);
        btSnapchatQueryCurrentUserAll.setOnClickListener(this);
        btSnapchatQueryByConditon.setOnClickListener(this);

        // ?????????
        btRankingListSubmitAchievement.setOnClickListener(this);
        btRankingListGetRank.setOnClickListener(this);
        btRankingListUpdateStatistic.setOnClickListener(this);
        btRankingListGetUserStatistic.setOnClickListener(this);
        btRankingListGetResults.setOnClickListener(this);


    }

    /*
     * TapSDK ????????????TapDB?????????????????????????????????
     * */
    public void initSDK() {
        LeanCloud.setLogLevel(LCLogger.Level.DEBUG);
        WebView.setWebContentsDebuggingEnabled(true);
        // ???????????????????????? SDK ?????????
        // TapDB ?????????
        TapDBConfig tapDBConfig = new TapDBConfig();
        tapDBConfig.setEnable(true);
        tapDBConfig.setChannel("gameChannel");
        tapDBConfig.setGameVersion("gameVersion");
        // TapSDK ?????????
        TapConfig tapConfig = new TapConfig.Builder()
                .withAppContext(getApplicationContext())
                .withRegionType(TapRegionType.CN) // TapRegionType.CN: ??????  TapRegionType.IO: ??????
                // ???????????? - ??????
                .withClientId("**** Yourself ClientID From TapDC ****")
                .withClientToken("****** Yourself ClientToken From TapDC ******")
                /* ???????????? ?????? TapTap ?????????????????????????????????????????? */
                .withServerUrl("****** Yourself ServerUrl From TapDC ******")
                .withTapDBConfig(tapDBConfig)
                .build();
        TapBootstrap.init(MainActivity.this, tapConfig);

        // ????????????????????????
        TapMoment.setCallback(new TapMoment.TapMomentCallback() {
            @Override
            public void onCallback(int code, String msg) {
                if (code == TapMoment.CALLBACK_CODE_GET_NOTICE_SUCCESS) {
                    // ???????????????????????????
                    Toast.makeText(MainActivity.this, "??????????????????????????? " + msg, Toast.LENGTH_SHORT).show();
                }
                if(code == TapMoment.CALLBACK_CODE_LOGIN_SUCCESS){
                    // ?????????????????????
                    Toast.makeText(MainActivity.this, "???????????????????????? " + msg, Toast.LENGTH_SHORT).show();
                }
                if(code == TapMoment.CALLBACK_CODE_PUBLISH_SUCCESS){
                    // ??????????????????
                    Toast.makeText(MainActivity.this, "????????????????????? " + msg, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_tap_login:
                // ????????????
                taptapLogin();
                break;
            case R.id.btn_tap_loginstatus:
                // ????????????
                taptapLoginStatus();
                break;
            case R.id.btn_tap_gouhuo:
                //??????????????????
                taptapGouHuo();
                break;
            case R.id.btn_tap_logout:
                // ????????????
                taptapLogout();
                break;
            case R.id.btn_tap_license:
                // ????????????
                taptapLicense();
                break;
            case R.id.btn_tap_binding:
                // ??????
                taptapBinding();
                break;
            case R.id.btn_tap_binding_again:
                // ????????????
                taptapBindingAgain();
                break;
            case R.id.btn_tap_thirdLogin:
                // ???????????????
                taptapThirdLogin();
                break;
            case R.id.btn_tap_thirdLogin_Again:
                // ??????????????? Again
                taptapThirdLoginAgain();
                break;
            case R.id.btn_tap_init_only:
                // TapTap ?????? ????????????
                taptapInitOnly();
                break;
            case R.id.btn_tap_login_only:
                // TapTap ?????? ????????????
                taptapLoginOnly();
                break;
            case R.id.btn_tap_loginstatus_only:
                // TapTap ?????? ????????????
                taptapLoginStatusOnly();
                break;
            case R.id.btn_tap_logout_only:
                // TapTap ?????? ????????????
                taptapLogoutOnly();
                break;
                // ?????????
            case R.id.btn_tap_antiaddiction_init:
                // ??????????????????
                taptapAntiAddictionInit();
                break;
            case R.id.btn_tap_antiaddiction_taplogin:
                // ????????? TapLogin
                taptapAntiAddictionTapLogin();
                break;
            case R.id.btn_tap_antiaddiction_manual:
                // ?????????????????????
                taptapAntiAddictionManual();
                break;
            case R.id.btn_tap_antiaddiction_agerange:
                // ????????????????????????
                taptapAntiAddictionAgeRange();
                break;
            case R.id.btn_tap_antiaddiction_logout:
                // ?????????????????????
                taptapAntiAddictionLogout();
                break;
            case R.id.btn_tap_fetch_notification:
                // ???????????????
                taptapFetchNotification();
                break;
            case R.id.btn_tap_open_moment:
                // ????????????
                taptapOpenMoment();
                break;
            case R.id.btn_tap_directly_open:
                // ???????????????
                taptapDirectlyOpen();
                break;
            case R.id.btn_tap_close_moment:
                // ??????????????????
                taptapCloseMoment();
                break;
            case R.id.btn_tap_one_key_publish:
                // ?????????????????????????????????
                taptapOneKeyPublish();
                break;
            case R.id.btn_tap_friend_request_status:
                // ????????????????????????????????????????????????????????????????????????
                taptapFriendshipRequestStatus();
                break;
            case R.id.btn_tap_add_friend:
                // ??????????????????
                taptapAddFriend();
                break;
            case R.id.btn_tap_getfriend_request_list:
                // ????????????????????????
                taptapGetFriendRequestList();
                break;
            case R.id.btn_tap_decline_friendship_request:
                // ???????????????????????????
                taptapDeclineFriendshipRequest();
                break;
            case R.id.btn_tap_accept_friendship_request:
                // ???????????????????????????
                taptapAcceptFriendshipRequest();
                break;
            case R.id.btn_tap_delete_friendship_request:
                // ???????????????????????????????????????????????????
                taptapDeleteFriendshipRequest();
                break;
            case R.id.btn_tap_getfriends_list:
                // ??????????????????
                taptapGetFriendsList();
                break;
            case R.id.btn_tap_delete_friend:
                // ????????????
                taptapDeleteFriend();
                break;
            case R.id.btn_tap_query_friend:
                // ??????????????????
                taptapQueryFriend();
                break;
            case R.id.btn_tap_achieve_register:
                // ??????????????????
                taptapRegisterAchieve();
                break;
            case R.id.btn_tap_achieve_init:
                // ???????????????
                taptapInitAchieve();
                break;
            case R.id.btn_tap_achieve_fetch_all:
                // ????????????????????????
                taptapFetchAllAchieveData();
                break;
            case R.id.btn_tap_achieve_fetch_user:
                // ????????????????????????
                taptapFetchUserAchieveData();
                break;
            case R.id.btn_tap_achieve_reach:
                // ??????????????????
                taptapReachAchieve();
                break;
            case R.id.btn_tap_achieve_grow:
                // ??????????????????
                taptapGrowAchieve();
                break;
            case R.id.btn_tap_achieve_toast:
                // ??????????????????
                taptapSetShowToast();
                break;
            case R.id.btn_tap_achieve_open:
                // ?????????????????????
                taptapShowAchieve();
                break;
            case R.id.btn_tap_snapchat_creatsc:
                // ?????????????????????
                taptapSnapshotCreated();
                break;
            case R.id.btn_tap_snapchat_savesc:
                // ????????????
                taptapSnapshotSave();
                break;
            case R.id.btn_tap_snapchat_current_user:
                // ?????????????????????????????????
                taptapSnapshotQueryCurrentAll();
                break;
            case R.id.btn_tap_snapchat_current_user_condition:
                // ????????????????????????????????????
                taptapSnapshotQueryByconditon();
                break;
            case R.id.btn_tap_rankinglist_submit_achievement:
                // ????????????
                taptapSubmitAchievement();
                break;
            case R.id.btn_tap_rankinglist_get_rank:
                // ????????????
                taptapGetRank();
                break;
            case R.id.btn_tap_rankinglist_update_statistic:
                // ??????????????????
                taptapUpdateStatistic();
                break;
            case R.id.btn_tap_rankinglist_get_user_statistic:
                // ???????????????????????????
                taptapGetUserStatistic();
                break;
            case R.id.btn_tap_rankinglist_get_results:
                // ???????????????????????????(Top 10)
                taptapRankingListGetResults();
                break;


        }
    }

    private void taptapAntiAddictionLogout() {
        AntiAddictionUIKit.logout();
    }

    private void taptapAntiAddictionAgeRange() {
        int ageRange = AntiAddictionKit.currentUserAgeLimit();
        Log.d(TAG, "????????????????????????" + String.valueOf(ageRange));
    }

    private void taptapAntiAddictionManual() {

        String userIdentifier = "?????????????????????NNNNNNNNYHHYWWTTAABC";
        AntiAddictionUIKit.startup(MainActivity.this, false, userIdentifier, "");
    }

    private void taptapAntiAddictionTapLogin() {
        AccessToken accessToken = TapLoginHelper.getCurrentAccessToken();
//        String tapTapAccessToken = accessToken.toJsonString();
        boolean useTapLogin = true;
        String userIdentifier = "?????????????????????NNNNNNNNYHHYWWTTAABC";
        String tapTapAccessToken = "TapTap ?????????????????? access token";
        AntiAddictionUIKit.startup(MainActivity.this, useTapLogin, userIdentifier, tapTapAccessToken);
    }

    private void taptapAntiAddictionInit() {
        // Android SDK ???????????????????????????????????? Activity?????????????????????
        String gameIdentifier = "6Rap5XF2ncLQB2oIiW";
        AntiAddictionFunctionConfig config = new AntiAddictionFunctionConfig.Builder()
                .enablePaymentLimit(true) // ??????????????????????????????
                .enableOnLineTimeLimit(true) // ??????????????????????????????
                .build();
        AntiAddictionUIKit.init(MainActivity.this, gameIdentifier, config,
                new AntiAddictionUICallback() {
                    @Override
                    public void onCallback(int code, Map<String, Object> extras) {
                        // ?????? code ??????????????????????????????????????????????????????
                        if(null != extras){
                            Log.d(TAG, extras.toString());
                            Log.d(TAG, String.valueOf(code));
                        }
                        switch (code){
                            case Constants.ANTI_ADDICTION_CALLBACK_CODE.LOGIN_SUCCESS:
//                                Log.d(TAG, extras.toString());
                                Log.d(TAG, "?????????????????????");
                                break;
                            case Constants.ANTI_ADDICTION_CALLBACK_CODE.LOGOUT:
//                                Log.d(TAG, extras.toString());
                                Log.d(TAG, "??????????????????");
                                break;
                            case Constants.ANTI_ADDICTION_CALLBACK_CODE.OPEN_ALERT_TIP:
                                Log.d(TAG, "????????????????????????????????????");
                                break;
                            case Constants.ANTI_ADDICTION_CALLBACK_CODE.NIGHT_STRICT:
                                Log.d(TAG, "??????????????????????????????????????????");
                                break;
                            case Constants.ANTI_ADDICTION_CALLBACK_CODE.REAL_NAME_STOP:
                                Log.d(TAG, "??????????????????????????????????????????????????????");
                                break;
                            case Constants.ANTI_ADDICTION_CALLBACK_CODE.SWITCH_ACCOUNT:
                                Log.d(TAG, "?????????????????????????????????????????????????????????");
                                break;
                        }
                    }
                }
        );
    }

    private void taptapThirdLogin() {
        Map<String, Object> thirdPartyData = new HashMap<String, Object>();
        // ??????
        thirdPartyData.put("expires_in", 930516);
        thirdPartyData.put("openid", "LeeJiEun_Giant_Openid");
        thirdPartyData.put("access_token", "LeeJiEun_Giant_ACCESS_TOKEN");
        // ??????
        thirdPartyData.put("refresh_token", "LeeJiEun_Giant_REFRESH_TOKEN");
        thirdPartyData.put("scope", "LeeJiEun_Giant_SCOPE");
        TDSUser.loginWithAuthData(TDSUser.class, thirdPartyData, "giant").subscribe(new Observer<TDSUser>() {
            public void onSubscribe(Disposable disposable) {
            }
            public void onNext(TDSUser user) {
                Log.d(TAG, "?????????????????????");
                Log.d(TAG, user.toJSONString());
            }
            public void onError(Throwable throwable) {
                Log.d(TAG, "????????????????????????????????????????????????: " + throwable.getMessage());
            }
            public void onComplete() {
            }
        });
    }

    private void taptapThirdLoginAgain() {
        Map<String, Object> thirdPartyData = new HashMap<String, Object>();
        // ??????
        thirdPartyData.put("expires_in", 19930516);
        thirdPartyData.put("openid", "LeeJiEun_Giant_Openid_Again");
        thirdPartyData.put("access_token", "LeeJiEun_Giant_ACCESS_TOKEN_Again");
        // ??????
        thirdPartyData.put("refresh_token", "LeeJiEun_Giant_REFRESH_TOKEN_Again");
        thirdPartyData.put("scope", "LeeJiEun_Giant_SCOPE_Again");
        TDSUser.loginWithAuthData(TDSUser.class, thirdPartyData, "taptap").subscribe(new Observer<TDSUser>() {
            public void onSubscribe(Disposable disposable) {
            }
            public void onNext(TDSUser user) {
                Log.d(TAG, "???????????????????????????");
                Log.d(TAG, user.toJSONString());
            }
            public void onError(Throwable throwable) {
                Log.d(TAG, "??????????????????????????????????????????????????????: " + throwable.getMessage());
            }
            public void onComplete() {
            }
        });
    }

    private void taptapBinding() {
        Map<String, Object> thirdPartyData = new HashMap<String, Object>();
        // ??????
        thirdPartyData.put("expires_in", 7200);
        thirdPartyData.put("openid", "BANDINGpcb6jiHAjB82k8H9MjKkiQ==");
        thirdPartyData.put("access_token", "BandingHAHAHAHAHA1/e3fyiwgwBGXeor93rQGfB_qfOpsArBYvze6W7zmV73zxKH1mpJUgehCWRbj0-c-ZrTlSV3qlAAaQW1C4tjJFlZjjxlvpJhGQ0JXHX7bfZwwKxiI8DJ0zu5XXOmE2LdwRXXMjbI0Syeuua5Ym5W2uK-JNfinO2jen6Sb7p_1GeJF-j3W_6nmYZPVJSP9BQap5b61zLOZ1c0r7-5t3d1Id-TeAj8Km78tj4rZ1QkLzgUFauRSxvHKMhkPOzW3LDVpMw3dns5B2Am_hw5ybgAOT0PDdVVRNe68DWz1JySB2G5ARPwDLonYwn13-_BoPl9ldaTK_ogF9chFmfLF_V5DFKg");
        // ??????
//        thirdPartyData.put("expires_in", 7200);
//        thirdPartyData.put("openid", "QDNJfr2wFRRmFu8oqL2pCg==");
//        thirdPartyData.put("access_token", "1/B2sMNYgxvmuwNPg82IEuOZIAoT30WmB-L2FkHUxprcF39RCBTlFVKbcV_fHSMvSQMp5m_9cLnC78GzimhvGll4t8R0X5Vp_KAiTVk-JrnunHKYObD310JM5HikHz6YMaex9TPVaDtZV1jCFVZo1cUfDlCrpmm3o0urx_LZYqTamvDU_JnZTyunq7lD-2YI_LVekpqP5ZznhvcfyLA-r48lrwa1FuZM3cQygH5H_xvYTHHP1pPiPOPhzhZWJu7NP9Ya6ReNKPpMtAiFXnzokVhB1QKfcaPhYr9g60ogY6a3vii2Jn-hCWV61NqLFhGl3HoiWBmw7F1BQ4FnbbVidyHQ");
        thirdPartyData.put("taptap_name", "lrj3zwhy01pr4ltbu4hiww2ba");
//        thirdPartyData.put("refresh_token", "TapTap_REFRESH_TOKEN");
//        thirdPartyData.put("scope", "TapTap_SCOPE");
        TDSUser currentUser = TDSUser.getCurrentUser();
        currentUser.associateWithAuthData(thirdPartyData, "taptap").subscribe(new Observer<LCUser>() {
            @Override
            public void onSubscribe(@androidx.annotation.NonNull Disposable d) {

            }

            @Override
            public void onNext(@androidx.annotation.NonNull LCUser lcUser) {
                Log.d(TAG, "???????????????");
                Log.d(TAG, "???????????? TDSUserID : " + lcUser.getObjectId());

            }

            @Override
            public void onError(@androidx.annotation.NonNull Throwable e) {
                Log.d(TAG, "??????????????? " + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void taptapBindingAgain() {
        Map<String, Object> thirdPartyData = new HashMap<String, Object>();
        // ??????
        thirdPartyData.put("expires_in", 7200);
        thirdPartyData.put("openid", "WanWan_QDNJfr2wFRRmFu8oqL2pCg==");
        thirdPartyData.put("access_token", "WanWan_1/B2sMNYgxvmuwNPg82IEuOZIAoT30WmB-L2FkHUxprcF39RCBTlFVKbcV_fHSMvSQMp5m_9cLnC78GzimhvGll4t8R0X5Vp_KAiTVk-JrnunHKYObD310JM5HikHz6YMaex9TPVaDtZV1jCFVZo1cUfDlCrpmm3o0urx_LZYqTamvDU_JnZTyunq7lD-2YI_LVekpqP5ZznhvcfyLA-r48lrwa1FuZM3cQygH5H_xvYTHHP1pPiPOPhzhZWJu7NP9Ya6ReNKPpMtAiFXnzokVhB1QKfcaPhYr9g60ogY6a3vii2Jn-hCWV61NqLFhGl3HoiWBmw7F1BQ4FnbbVidyHQ");
        // ??????
        thirdPartyData.put("refresh_token", "WanWan_TapTap_REFRESH_TOKEN");
        thirdPartyData.put("scope", "WanWan_TapTap_SCOPE");
        TDSUser currentUser = TDSUser.getCurrentUser();
        currentUser.associateWithAuthData(thirdPartyData, "TapTap").subscribe(new Observer<LCUser>() {
            @Override
            public void onSubscribe(@androidx.annotation.NonNull Disposable d) {

            }

            @Override
            public void onNext(@androidx.annotation.NonNull LCUser lcUser) {
                Log.d(TAG, "?????????????????????");
                Log.d(TAG, "?????????????????? TDSUserID : " + lcUser.getObjectId());

            }

            @Override
            public void onError(@androidx.annotation.NonNull Throwable e) {
                Log.d(TAG, "??????????????? " + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void taptapRankingListGetResults() {
        // ???????????????????????????(Top 10)
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
        // ???????????????????????????
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
                Toast.makeText(MainActivity.this, "??????????????? " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {}
        });
    }

    private void taptapUpdateStatistic() {
        // ??????????????????
        TDSUser currentUser = TDSUser.getCurrentUser();
        if (null == currentUser){
            Toast.makeText(MainActivity.this, "???????????? TapTap ??????", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(MainActivity.this, "???????????????", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(@NotNull Throwable throwable) {
                // handle error
                Toast.makeText(MainActivity.this, "??????????????? " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {}
        });
    }

    private void taptapGetRank() {
        // ????????????
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
        // ????????????
        TDSUser currentUser = TDSUser.getCurrentUser();
        if (null == currentUser){
            Toast.makeText(MainActivity.this, "???????????? TapTap ??????", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(MainActivity.this, "?????????????????????", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(@androidx.annotation.NonNull Throwable e) {
                Toast.makeText(MainActivity.this, "????????????????????? " + e.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, "????????????????????? " + e.toString(), Toast.LENGTH_SHORT).show();
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
        // ?????????????????? AccessToken ????????????
        if(TapLoginHelper.getCurrentAccessToken() == null ){
            Toast.makeText(MainActivity.this, "?????????", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(MainActivity.this, "?????????", Toast.LENGTH_SHORT).show();
        }
    }

    private void taptapLoginOnly() {
        TapLoginHelper.TapLoginResultCallback loginCallback = new TapLoginHelper.TapLoginResultCallback() {
            @Override
            public void onLoginSuccess(AccessToken token) {
                Log.d(TAG, "TapTap authorization succeed");
                // ??????????????? TapLoginHelper.getCurrentProfile() ????????????????????????????????????????????????????????????????????????
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
        // ?????? Summary ??? GameSnapshot_Description ????????????
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
                        Log.d(TAG, "??????????????? " + e.toString());
                        Log.d(TAG, "??????????????? " + e.getMessage());
                        Toast.makeText(MainActivity.this, "??????????????? " + e.toString(), Toast.LENGTH_SHORT).show();
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
                        Log.d(TAG, "??????????????? " + e.getMessage());
                        Log.d(TAG, "??????????????? " + e.toString());
                        Toast.makeText(MainActivity.this, "??????????????? " + e.toString(), Toast.LENGTH_SHORT).show();
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
                Log.d(TAG, "?????????????????????" + gameSave.toJSONString());
                System.out.println("?????????????????????" + gameSave.toJSONString());
                Toast.makeText(MainActivity.this, "?????????????????????" + gameSave.toJSONString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(@NotNull Throwable e) {
                Log.d(TAG, "?????????????????????" + e.getMessage());
                Log.d(TAG, "?????????????????????" + e.toString());
                Toast.makeText(MainActivity.this, "????????????????????? " + e.toString(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        });

    }

    private void taptapSnapshotCreated() {
        gameSave = new TapGameSave();
        // ?????????
        gameSave.setName("GameSnapshot_Name");
        // ????????????
        gameSave.setSummary("GameSnapshot_Description");
        // ????????????
        gameSave.setPlayedTime(999);
        // ????????????
        gameSave.setProgressValue(99);
        // ??????????????????????????? png ?????? jpeg ??????
//        gameSave.setCover("cover_path");
        // ???????????????   /storage/emulated/0/Android/data/packname/cache
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
        // ????????????
        gameSave.setModifiedAt(new Date());
    }

    boolean flagShow = false;
    private void taptapSetShowToast() {
        flagShow = !flagShow;
        if(flagShow){
            Toast.makeText(this, "??????????????????", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "??????????????????", Toast.LENGTH_SHORT).show();
        }
        TapAchievement.setShowToast(flagShow);
    }

    private void taptapGrowAchieve() {
        // ?????????????????????????????????????????????
        // growSteps ?????????????????????????????????????????????????????????5???????????????5????????????
        // makeSteps ???????????????????????????????????????
        // displayID ?????????????????????????????????????????????????????? ??????ID
        // TapAchievement.growSteps("displayID", 5);
        TapAchievement.makeSteps("displayID", 100);
    }

    private void taptapReachAchieve() {
        // displayID ?????????????????????????????????????????????????????? ??????ID
        TapAchievement.reach("displayID");
    }

    private void taptapFetchUserAchieveData() {
        // ????????????
        List<TapAchievementBean> userList = TapAchievement.getLocalUserAchievementList();
        if(null != userList && !userList.isEmpty()){
            for (TapAchievementBean tab : userList){
                Log.d(TAG, tab.toString());
            }
            Toast.makeText(this, userList.toString(), Toast.LENGTH_SHORT).show();
        }

        // ???????????????
        TapAchievement.fetchUserAchievementList(new GetAchievementListCallBack() {
            @Override
            public void onGetAchievementList(List<TapAchievementBean> achievementList, AchievementException exception) {
                if (exception != null) {
                    switch (exception.errorCode) {
                        case AchievementException.SDK_NOT_INIT:
                            // SDK ?????????????????????
                            Log.d(TAG, "SDK ?????????????????????");
                            Toast.makeText(MainActivity.this, "SDK ?????????????????????", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            // ??????????????????
                            Log.d(TAG, "??????????????????");
                            Toast.makeText(MainActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // ??????????????????
                    Log.d(TAG, "??????????????????");
                    Toast.makeText(MainActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
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
        // ????????????
        List<TapAchievementBean> allList = TapAchievement.getLocalAllAchievementList();
        if(allList == null && !allList.isEmpty()){
            for(TapAchievementBean tab : allList){
                Log.d(TAG, tab.toString());
            }
            Toast.makeText(this, allList.toString(), Toast.LENGTH_SHORT).show();
        }

        // ???????????????
        TapAchievement.fetchAllAchievementList(new GetAchievementListCallBack() {
            @Override
            public void onGetAchievementList(List<TapAchievementBean> achievementList, AchievementException exception) {
                if (exception != null) {
                    switch (exception.errorCode) {
                        case AchievementException.SDK_NOT_INIT:
                            // SDK ?????????????????????
                            Log.d(TAG, "SDK ?????????????????????");
                            Toast.makeText(MainActivity.this, "SDK ?????????????????????", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            // ??????????????????
                            Log.d(TAG, "??????????????????");
                            Toast.makeText(MainActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // ??????????????????
                    Log.d(TAG, "??????????????????");
                    Toast.makeText(MainActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
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
                // ??????????????????
                Log.d(TAG, "?????????????????????");
                Toast.makeText(MainActivity.this, "?????????????????????", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAchievementSDKInitFail(AchievementException exception) {
                // ??????????????????????????????
                Log.d(TAG, "????????????????????? " + exception.toString());
                Toast.makeText(MainActivity.this, "????????????????????? " + exception.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAchievementStatusUpdate(TapAchievementBean item, AchievementException exception) {
                if (exception != null) {
                    // ??????????????????
                    Log.d(TAG, "??????????????????: " + exception.toString());
                    Toast.makeText(MainActivity.this, "??????????????????: " + exception.toString(), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (item != null) {
                    // item ????????????
                    Log.d(TAG, "??????????????????");
                    Toast.makeText(MainActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(MainActivity.this, "?????????????????????", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, "???????????????????????????????????????????????????????????????", Toast.LENGTH_SHORT).show();
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
                    // lcFriendships ??????????????????
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
        // leeJiEun ??????????????? leeJiDong ???????????????
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
        // leeJiEun ??????????????? leeJiDong ???????????????
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
                Log.d(TAG, "?????????????????????");
                Toast.makeText(MainActivity.this, "?????????????????????", Toast.LENGTH_SHORT).show();
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
        // leeJiEun ??????????????? leeJiDong ???????????????
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
            Toast.makeText(this, "???????????????", Toast.LENGTH_SHORT).show();
        }else {
            TDSUser leeJiEun = null;
            Map<String, Object> attr = new HashMap<String, Object>();
            attr.put("group", "SoloQueen");
            try {
                // TODO ????????????????????????????????????????????? OcjectID
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
                    Toast.makeText(MainActivity.this, "???????????? ???", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(@NotNull Throwable e) {
                    System.out.println("failed to apply friend request");
                    Toast.makeText(MainActivity.this, "???????????????" + e.toString(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onComplete() {

                }
            });
        }
    }

    private void taptapLicense() {
        //??????????????? SDK ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        TapLicenseHelper.setLicenseCallback(new TapLicenseCallback() {
            @Override
            public void onLicenseSuccess() {
                Log.d(TAG, "????????????????????????");
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
                    // ??????????????????????????????
                    Toast.makeText(MainActivity.this, "????????????????????????????????????", Toast.LENGTH_SHORT).show();
                }else {
                    // ????????????????????????????????? ????????????????????????
                    Toast.makeText(MainActivity.this, "????????????????????????????????????", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                // ???????????????????????????????????????
                Toast.makeText(MainActivity.this, "???????????????????????????????????????", Toast.LENGTH_SHORT).show();
                Log.d(TAG,throwable.toString());
            }
        });
    }

    private void taptapLoginStatus() {
        // ???????????????????????? null
        TDSUser currentUser = TDSUser.currentUser();
        // ???????????????????????? null
        if (currentUser == null) {
            // ??????????????????
            Toast.makeText(MainActivity.this, "????????????????????????????????????", Toast.LENGTH_SHORT).show();
        } else {
            // ?????????????????????
            Toast.makeText(MainActivity.this, "???????????????????????????????????????", Toast.LENGTH_SHORT).show();
            String accessToken = TapLoginHelper.getCurrentAccessToken().toJsonString();
            Log.d(TAG, accessToken);
        }
    }

    private void taptapLogin() {
        TDSUser currentUser = TDSUser.currentUser();

        // ???????????????????????? null
        if (currentUser == null) {
            // ??????????????????
            TDSUser.loginWithTapTap(MainActivity.this, new Callback<TDSUser>() {
                @Override
                public void onSuccess(TDSUser resultUser) {
                    // ????????????????????? resultUser ??????????????????????????????
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
            // ?????????????????????
            Toast.makeText(MainActivity.this, "???????????????????????????????????????", Toast.LENGTH_SHORT).show();
        }


    }

    private void taptapOneKeyPublish() {
        Toast.makeText(MainActivity.this, "????????? ??????????????????????????? ??????????????????????????????????????????????????????????????????????????????????????????", Toast.LENGTH_SHORT).show();
        // ????????? ??????????????????????????? ??????????????????????????????????????????????????????????????????????????????????????????
        int orientation = TapMoment.ORIENTATION_PORTRAIT;
        String content = "??????";
        String[] imagePaths = new String[]{"content://hello.jpg", "/sdcard/world.jpg"};
        TapMoment.publish(orientation, imagePaths, content);
    }

    private void taptapCloseMoment() {
        TapMoment.close();
//        TapMoment.closeWithConfirmWindow("??????", "???????????????????????????");
    }

    private void taptapDirectlyOpen() {
        Map<String, String> extras = new HashMap<>();
        // ?????????????????? key ???????????????"scene_id"??? ???????????????????????????????????????????????????????????????????????????????????????
        extras.put("scene_id", "LeeJiEun");
        // ????????????????????????????????? "tap://moment/scene/"
        TapMoment.directlyOpen(TapMoment.ORIENTATION_DEFAULT, "tap://moment/scene/", extras);
    }

    private void taptapOpenMoment() {
        TapMoment.open(TapMoment.ORIENTATION_PORTRAIT);
    }
    // ???????????????????????????
    private void taptapFetchNotification() {
        // ?????????????????????????????????????????????????????????
        TapMoment.fetchNotification();
    }

}