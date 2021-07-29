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
import com.tds.achievement.AchievementCallback;
import com.tds.achievement.AchievementException;
import com.tds.achievement.GetAchievementListCallBack;
import com.tds.achievement.TapAchievement;
import com.tds.achievement.TapAchievementBean;
import com.tds.common.entities.TapConfig;
import com.tds.common.entities.TapDBConfig;
import com.tds.common.models.TapRegionType;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.leancloud.LCException;
import cn.leancloud.LCFriendship;
import cn.leancloud.LCFriendshipRequest;
import cn.leancloud.LCObject;
import cn.leancloud.LCQuery;
import cn.leancloud.callback.LCCallback;
import cn.leancloud.types.LCNull;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import tds.androidx.annotation.NonNull;


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
    private Button btTapAddFriend;
    private Button btTapGetFriendRequestList;
    private Button btTapDeclineFriendshipRequest;
    private Button btTapAcceptFriendshipRequest;
    private Button btTapDeleteFriendshipRequest;
    private Button btTapFriendRequestStatus;
    private Button btTapGetFriendsList;
    private Button btTapDeleteFriend;
    private Button btTapQueryFriend;

    private Button btShowAchievement;
    private Button btRegisterAchievement;
    private Button btInitAchievement;
    private Button btFetchAllAchievement;
    private Button btFetchUserAchievement;
    private Button btFGrowAchievement;
    private Button btFReachAchievement;
    private Button btSwitchToastAchievement;

    public static final String LeeJiDongID = "61012c565d0493087d3bf63a";
    public static final String LeeJiEunID = "60f2df4fd1773b17a7c43e4f";

    private static final String TAG = "LeeJiEun ===> ";
    // 获取好友申请列表信息保存，多个接口使用到
    public List<LCFriendshipRequest> currentUserLcFriendshipRequests = new ArrayList<>();
    // 删除好友接口使用
    LCFriendship queriedUserLCFriendship = null;

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
                .withClientId("0RiAlMny7jiz086FaU")
                .withClientToken("8V8wemqkpkxmAN7qKhvlh6v0pXc8JJzEZe3JFUnU")
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
        }
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
//        TapAchievement.growSteps("displayID", 5);
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
                            break;
                        default:
                            // 数据获取失败
                            Log.d(TAG, "数据获取失败");
                    }
                } else {
                    // 成功获取数据
                    Log.d(TAG, "成功获取数据");
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
                            break;
                        default:
                            // 数据获取失败
                            Log.d(TAG, "数据获取失败");
                    }
                } else {
                    // 成功获取数据
                    Log.d(TAG, "成功获取数据");
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
            }

            @Override
            public void onAchievementSDKInitFail(AchievementException exception) {
                // 数据加载失败，请重试
                Log.d(TAG, "数据加载失败： " + exception.toString());
            }

            @Override
            public void onAchievementStatusUpdate(TapAchievementBean item, AchievementException exception) {
                if (exception != null) {
                    // 成就更新失败
                    Log.d(TAG, "成就更新失败: " + exception.toString());
                    return;
                }
                if (item != null) {
                    // item 更新成功
                    Log.d(TAG, "成就更新成功");
                }
            }
        });
    }

    private void taptapShowAchieve() {
        TapAchievement.showAchievementPage();
    }

    private void taptapQueryFriend() {
        // TODO 好友关系是单向的吗？A申请添加B为好友，B同意了，那么对A来说，B是A的好友，而A不一定是B的好友；
        TDSUser currentUser = TDSUser.currentUser();
        LCQuery<LCFriendship> query = currentUser.friendshipQuery();
        try {
             // TODO 第二步更改：测试需要更改这里的 OcjectID
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
                    System.out.println("failed to delete friendship of jerry. cause: " + e.getMessage());
                    Log.d(TAG,"failed to delete friendship of QueriedUser. cause: " + e.getMessage());
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
                Log.d(TAG, "failed to query friendship of jerry. cause: " + e.getMessage());
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
            }

            @Override
            public void onRequestAccepted(LCFriendshipRequest request) {
                Log.d(TAG, request.toString());
            }

            @Override
            public void onRequestDeclined(LCFriendshipRequest request) {
                Log.d(TAG, request.toString());
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
                System.out.println("failed to delete friendship request of jerry. cause: " + e.getMessage());
            }

            @Override
            public void onComplete() {}
        });
    }

    private void taptapAcceptFriendshipRequest() {
        // TODO 执行好友申请同意、拒绝、删除后，然后删除了好友，那么再次执行申请好友的处理接口还依旧有效。是否预期之内？
        // TODO 同意好友申请，然后删除了该好友，好友列表为0，再次点击同意申请，好友列表信息又有了之前被删除的用户信息。
        TDSUser currentUser = TDSUser.currentUser();
        LCFriendshipRequest queriedRequests = null;
        // leeJiEun 同意了来自 leeJiDong 的好友请求
        for(LCFriendshipRequest fr:currentUserLcFriendshipRequests ){

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
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG,"failed to accept friendship request of jerry. cause: " + e.getMessage());
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
                System.out.println("failed to decline friendship request of jerry. cause: " + e.getMessage());
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
                            System.out.println(request);
                            Log.d(TAG, request.toString());
                            Log.d(TAG, request.getFriend().getObjectId());
                            currentUserLcFriendshipRequests.add(request);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "failed to query friendship request of jerry. cause: " + e.getMessage());
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
                    System.out.println("failed to apply friend request to jerry.");
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