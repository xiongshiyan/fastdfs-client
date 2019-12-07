## 封装了两套fastdfs的java客户端
1. 一套基于fastdfs作者余庆的 https://github.com/happyfish100/fastdfs-client-java；
2. 一套基于提供连接池功能的 https://gitee.com/LiZhiW/fastdfs-java-client


1.1 下载jar包(fastdfs-client-java-1.27-SNAPSHOT.jar)引入

1.2 配置文件
```properties
fastdfs.connectTimeoutInSeconds=5
fastdfs.networkTimeoutInSeconds=30
fastdfs.charset=UTF-8
fastdfs.httpAntiStealToken=false
fastdfs.httpSecretKey=FastDFS1234567890
fastdfs.httpTrackerHttpPort=80
#多个用逗号隔开
fastdfs.trackers=ip:port
#url访问前缀
fastdfs.urlPrefix=??
```
1.3 非springboot项目使用

首先使用`top.jfunc.fastdfs.happyfish.config.FastdfsConfig#initFastdfsConfig()` 初始化，再使用 `top.jfunc.fastdfs.happyfish.util.FastdfsUtil`。

1.4 springboot项目
首先使用`top.jfunc.fastdfs.happyfish.config.EnableFastdfsClient` 注解开启配置，再使用的地方注入`org.csource.fastdfs.StorageClient1`。



2.1 下载jar包(fastdfs-java-client-1.0.0.RELEASE.jar)引入
2.2 配置文件
```properties
fastdfs.soTimeout=10000
fastdfs.connectTimeout=5000
fastdfs.maxTotal=200
fastdfs.maxTotalPerKey=200
fastdfs.maxIdlePerKey=50
#多个用逗号隔开
fastdfs.trackers=ip:port
#url访问前缀
fastdfs.urlPrefix=??
```
2.3 非springboot项目使用

首先使用`top.jfunc.fastdfs.lizw.config.FastdfsConfig#initFastdfsConfig()` 初始化，再使用 `top.jfunc.fastdfs.lizw.util.FastdfsUtil`。

2.4 springboot项目

首先使用`top.jfunc.fastdfs.lizw.config.EnableFastdfsClient` 注解开启配置，再使用的地方注入`org.cleverframe.fastdfs.client.TrackerClient`和`org.cleverframe.fastdfs.client.StorageClient`。
