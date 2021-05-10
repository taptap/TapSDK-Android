Changelog
# v2.1.1 2020/05/10

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

## v2.0.0 2020/04/08