# android-jdbc-mysql-demo v1.0

Android 端**直连 MySQL** 的连通性实验：通过 `mysql-connector-java` 在子线程里用 JDBC 建连、查表，再用 `Handler` 把结果回传到 `TextView`。用于验证「手机能否绕过后端直接访问数据库」这一常见疑问。

包名 `com.coolweather.mysqlconnectiontest`，`compileSdk 34 / minSdk 23`。

## 文件结构

```text
app/src/main/java/com/coolweather/mysqlconnectiontest/
├── MainActivity.java   按钮触发连接，Handler 回传结果到 TextView
├── DBOpenHelper.java   DriverManager 建连、驱动加载、异常上报
└── Test.java           简单实体（id / name），承载查询结果
app/libs/mysql-connector-java-5.0.7.jar   本地依赖，不走 Maven
app/src/main/res/layout/activity_main.xml
```

## 启动

1. Android Studio 打开工程、同步 Gradle（`libs/` 已内置 5.0.7 驱动 jar）；
2. 在 `DBOpenHelper` 里填入自己的数据库地址与账号（本仓库版本为课程环境，**请勿复用其中的凭据**）；
3. 运行后点击按钮，界面会显示连接成功或异常信息；
4. 需要 `INTERNET` 权限，且数据库需允许来自外网的连接。

## 功能落点表

| # | 功能 | 状态 | 落点 |
|---|---|---|---|
| 1 | 加载 MySQL JDBC 驱动 | 完成 | `DBOpenHelper` |
| 2 | 子线程建连，避免主线程网络异常 | 完成 | `MainActivity` + `Handler` |
| 3 | 执行查询并回传结果 | 完成 | `Statement` / `ResultSet` → `TextView` |
| 4 | 本地 jar 依赖方式 | 完成 | `app/libs/mysql-connector-java-5.0.7.jar` |
| 5 | 连接参数外置 | 未实现 | 目前写在源码内 |

## 版本记录

### v0.1 · 建库

- `chore: 初始化仓库（LICENSE / .gitignore）`：网页端建库。

### v1.0 · 连通性验证

- `feat: JDBC 子线程建连，Handler 回显查询结果`：工程一次性推入，含驱动 jar、建连逻辑与结果回显。

## 已知限制

- **这是反模式**：移动端直连数据库会把库暴露在公网，且驱动与账号随 APK 分发。生产应由服务端提供 HTTP 接口。
- `mysql-connector-java 5.0.7` 已停止维护，与新版 MySQL 8 的认证插件不兼容。
- `AndroidManifest.xml` 中 `INTERNET` 权限重复声明了两次。
- 数据库账号口令写在源码中，**仅限本地实验**；建议改为 `BuildConfig` 注入并在提交前移除。
