package top.jfunc.fastdfs.lizw.util;

import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.cleverframe.fastdfs.client.DefaultStorageClient;
import org.cleverframe.fastdfs.client.DefaultTrackerClient;
import org.cleverframe.fastdfs.client.StorageClient;
import org.cleverframe.fastdfs.client.TrackerClient;
import org.cleverframe.fastdfs.conn.DefaultCommandExecutor;
import org.cleverframe.fastdfs.model.StorePath;
import org.cleverframe.fastdfs.pool.ConnectionPool;
import org.cleverframe.fastdfs.pool.PooledConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.jfunc.common.propenv.Prop;
import top.jfunc.common.propenv.PropertiesUtils;
import top.jfunc.common.utils.FileUtil;
import top.jfunc.common.utils.IoUtil;
import top.jfunc.fastdfs.lizw.config.FastdfsConfig;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 文件服务器fastdfs相关工具类
 * @author xiongshiyan at 2019/12/5 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class FastdfsUtil {
    private static final Logger logger = LoggerFactory.getLogger(FastdfsUtil.class);
    private static ConnectionPool connectionPool;
    private static TrackerClient trackerClient;
    private static StorageClient storageClient;
    /**
     * 目前只有一组，并且名字为group1，如果以后增加组了，就需要动态指定
     */
    private static final String GROUP = "group1";

    /**
     * 需要在程序启动的时候调用此方法初始化
     */
    public static void init(FastdfsConfig fastdfsConfig){
        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory(fastdfsConfig.getFastdfsSoTimeout(),fastdfsConfig.getFastdfsConnectTimeout());
        GenericKeyedObjectPoolConfig conf = new GenericKeyedObjectPoolConfig();
        conf.setMaxTotal(fastdfsConfig.getFastdfsMaxTotal());
        conf.setMaxTotalPerKey(fastdfsConfig.getFastdfsMaxTotalPerKey());
        conf.setMaxIdlePerKey(fastdfsConfig.getFastdfsMaxIdlePerKey());
        connectionPool = new ConnectionPool(pooledConnectionFactory, conf);

        String[] split = fastdfsConfig.getFastdfsTrackers().split(",");
        Set<String> trackerSet = new HashSet<>(split.length);
        trackerSet.addAll(Arrays.asList(split));
        DefaultCommandExecutor commandExecutor = new DefaultCommandExecutor(trackerSet, connectionPool);
        trackerClient = new DefaultTrackerClient(commandExecutor);
        storageClient = new DefaultStorageClient(commandExecutor, trackerClient);
        logger.info("初始化fastdfs客户端成功");
    }

    public static TrackerClient getTrackerClient(){
        return trackerClient;
    }

    public static StorageClient getStorageClient(){
        return storageClient;
    }


    public static StorePath upload(InputStream stream , long length , String ext){
        return storageClient.uploadFile(GROUP, stream, length, ext);
    }

    public static StorePath upload(File file){
        InputStream stream = IoUtil.getFileInputStream(file.getAbsolutePath());
        return storageClient.uploadFile(GROUP , stream , file.length() , FileUtil.getExtensionName(file.getAbsolutePath()));
    }

    public static void main(String[] args) {
        System.setProperty("env" , "test");
        Prop fastdfsProp = PropertiesUtils.use("src/main/profile/dev/config/" + "fastdfs.properties");
        logger.info(fastdfsProp.getProperties().toString());
        FastdfsConfig.initFastdfsConfig(fastdfsProp.getProperties());

        File file = new File("E:\\c.jpg");
        StorePath storePath = upload(file);
        System.out.println(storePath.getFullPath());
    }
}
