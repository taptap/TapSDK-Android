Changelog
# v2.1.3 2021/05/28
## Feature
* 新增繁体中文、日文、韩文、泰语、印度尼西亚语5种新的翻译，并可通过 `TapBootstrap setPreferredLanguage:` 设定

## Bugfix
* TapMoment 在部分带刘海设备上判断失败导致UI可能被遮挡的问题


# v2.1.2 2021/05/17
## TapBootstrap
### Breaking changes
* 删除 `openUserCenter` 接口

## TapFriends
### Feature
* 新增消息回调的接口
``` java
    class Constants {
        static class Event {
            public static final int INVITATION = 130001;
        }
    }

    TapFriends.registerMessageCallback(new ComponentMessageCallback() {
        /**
        * @param code @see com.tapsdk.friends.constants.Constants.Event
        * @param extras @{user_id:{userId}}   
        */
        void onMessage(int code, Map<String, String> extras) {

        }
    })
```

* 工程配置
在 Android 项目的 AndroidManifest.xml 文件中添加如下内容
``` java
        <activity
            android:name="com.tapsdk.friends.TapFriendsRouterPageActivity"
            android:allowTaskReparenting="true"
            android:exported="true"
            android:launchMode="singleTask"
            android:screenOrientation="nosensor"
            android:theme="@android:style/Theme.Translucent.NoTitleBar">
            <intent-filter>
                <action android:name="android.intent.action.VIEW" />

                <category android:name="android.intent.category.DEFAULT" />
                <category android:name="android.intent.category.BROWSABLE" />

                <data
                    android:host="{client_id}"
                    android:path="/friends"
                    android:scheme="tapsdk" />
            </intent-filter>
        </activity>
```

* 新增获取好友邀请链接的接口
``` java
    TapFriends.generateFriendInvitation(new Callback<String> callback) {
        @Override
        public void onSuccess(String shareUrl) {
        }

        @Override
        public void onFail(TapFriendError error) {
        }
    }
```

* 新增调用系统分享控件分享好友邀请链接的接口
``` java
    TapFriends.sendFriendInvitation(new Callback<Boolean> callback) {
        @Override
        public void onSuccess(Boolean success) {
            if (success) {
                // 分享成功
            } else {
                // 分享失败
            }

        }

        @Override
        public void onFail(TapFriendError error) {
            // 分享出错
        }
    }
```

* 新增搜索用户的接口（需要登录状态）
``` java
    TapFriends.searchUser(userId, new Callback<TapUserRelationship>() {
        void onSuccess(TapUserRelationship tapUserRelationship) {
            // FriendRelationshipUtil.isFollowed(tapUserRelationship.relationship) 是否关注
            // FriendRelationshipUtil.isFollower(tapUserRelationship.relationship) 是否被关注
            // FriendRelationshipUtil.isBlocked(tapUserRelationship.relationship) sshi

        }

        void onFail(TapFriendError error) {
            // 查询失败
        }
    })
```

## TapFriendUI
### Feature
增加收到好友邀请消息时的弹窗

## TapCommon
* 支持性升级


# v2.1.1 2021/05/10

## TapBootstrap
### Feature
* 新增篝火测试资格校验接口
    ``` java
        TapBootstrap.getTestQualification(Callback<Boolean> callback) {
            void onSuccess(Boolean result) {

            }
            void onFail(TapError error) {

            }
        }
    }];
    ```
* 引入TapDB库时，自动初始化 TapDB，可以通过 TapDBConfig 配置 channel、gameVersion、enable 参数
    ``` java
        TapDBConfig dbConfig = new TapDBConfig();
        dbConfig.setEnable(enable);
        dbConfig.setChannel(channel);
        dbConfig.setGameVersion(gameVersion);

        TapConfig tapConfig = new TapConfig.Builder().withAppContext(activity)
                .withClientId(clientId)
                .withClientSecret(clientSecret)
                .withRegionType(regionType)
                .withTapDBConfig(dbConfig)
                .build();
    ```

### Breaking changes
* TapConfig 新增 clientSecret 字段，必填

## TapMoment

### Feature
* 新增打开特定页面的接口（打开场景化入口等） 
    ``` java
    /**
     * @param orientation [-1:default, 0:landscape,1:portrait, 2:sensor]
     * @page page Page.PAGE_SHORT_CUT
     * @extras extras {"scene_id":{scene_id}}
     */
    public static void directlyOpen(@Orientation orientation, String page, Map<String, String> extras)
    ```

## TapDB

### Optimize
* 优化TapDB兼容性

## TapLicense
### Feature
* 支持检查本体购买
* 支持 DLC 购买状态查询
* 支持唤起 DLC 购买

## TapCommon
### Feature
* 新增检查 TapTap 客户端是否安装接口
* 新增唤起 TapTap 客户端更新页面接口
* 新增唤起 TapTap 客户端评论页面接口

## v2.0.0 2021/04/08