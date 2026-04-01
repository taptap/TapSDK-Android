# TapSDKv3 Android 


## 仓库介绍

本仓库为 TapSDK v3 在 Android 平台的产物仓库，包含 TapTap 登录、更新唤起、合规认证、数据分析等完整模块。开发者在根据 TapTap 开发者中心接入文档集成相关业务功能时，可通过本仓库获取并下载所需的依赖库文件


### 获取 SDK

进入 [Releases](https://github.com/taptap/tapsdk-v3-android/releases) 页面，选择需要的版本及模块下载。

每个版本通常包含：

* 各模块的 `*.aar` 依赖库文件
* 更新说明（Release Notes）


## 接入文档

完整的集成指南、API 参考和使用示例，请访问：
**[接入文档](https://developer.taptap.cn/docs/v3/sdk/)**


## 接入方式（AAR）

### 1. 下载 AAR

将下载的 `*.aar` 文件放入项目的 `libs/` 目录。

### 2. 配置 Gradle

在模块的 `build.gradle` 中添加：

```gradle
dependencies {
    implementation files('libs/<artifact-name>.aar')
}
```

如 SDK 依赖其他库，请根据文档额外添加依赖。


## 支持与反馈

如有问题或需求，请通过以下方式联系我们：

- 开发者中心：[TapTap 开发者中心](https://developer.taptap.cn/)
- 工单咨询：[提交工单](https://developer.taptap.cn/docs/store/#%E4%BA%8C%E5%B7%A5%E5%8D%95%E5%92%A8%E8%AF%A2)



