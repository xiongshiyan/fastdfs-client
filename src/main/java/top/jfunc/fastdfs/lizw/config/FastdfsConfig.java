package top.jfunc.fastdfs.lizw.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import top.jfunc.fastdfs.lizw.util.FastdfsUtil;

import java.util.Properties;

/**
 * @author xiongshiyan at 2019/12/5 , contact me with email yanshixiong@126.com or phone 15208384257
 */
@ConfigurationProperties(prefix = "fastdfs")
public class FastdfsConfig {
    public static final FastdfsConfig config = new FastdfsConfig();
    private static final String FASTDFS_SO_TIMEOUT           = "fastdfs.soTimeout";
    private static final String FASTDFS_CONNECT_TIMEOUT      = "fastdfs.connectTimeout";
    private static final String FASTDFS_MAX_TOTAL            = "fastdfs.maxTotal";
    private static final String FASTDFS_MAX_TOTAL_PER_KEY    = "fastdfs.maxTotalPerKey";
    private static final String FASTDFS_MAX_IDLE_PER_KEY     = "fastdfs.maxIdlePerKey";
    private static final String FASTDFS_TRACKERS             = "fastdfs.trackers";
    private static final String FASTDFS_URL_PREFIX           = "fastdfs.urlPrefix";

    private static final int DEFAULT_SO_TIMEOUT              = 10000;
    private static final int DEFAULT_CONNECT_TIMEOUT         = 5000;
    private static final int DEFAULT_MAX_TOTAL               = 200;
    private static final int DEFAULT_MAX_TOTAL_PER_KEY       = 200;
    private static final int DEFAULT_MAX_IDLE_PER_KEY        = 50;

    private int soTimeout = DEFAULT_SO_TIMEOUT;
    private int connectTimeout = DEFAULT_CONNECT_TIMEOUT;
    private int maxTotal = DEFAULT_MAX_TOTAL;
    private int maxTotalPerKey = DEFAULT_MAX_TOTAL_PER_KEY;
    private int maxIdlePerKey = DEFAULT_MAX_IDLE_PER_KEY;

    private String trackers;
    private String urlPrefix;

    public int getSoTimeout() {
        return soTimeout;
    }

    public void setSoTimeout(int soTimeout) {
        this.soTimeout = soTimeout;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public int getMaxTotalPerKey() {
        return maxTotalPerKey;
    }

    public void setMaxTotalPerKey(int maxTotalPerKey) {
        this.maxTotalPerKey = maxTotalPerKey;
    }

    public int getMaxIdlePerKey() {
        return maxIdlePerKey;
    }

    public void setMaxIdlePerKey(int maxIdlePerKey) {
        this.maxIdlePerKey = maxIdlePerKey;
    }

    public String getTrackers() {
        return trackers;
    }

    public void setTrackers(String trackers) {
        this.trackers = trackers;
    }

    public String getUrlPrefix() {
        return urlPrefix;
    }

    public void setUrlPrefix(String urlPrefix) {
        this.urlPrefix = urlPrefix;
    }

    /**
     * 非 Spring 项目调用此方法初始化config和util
     */
    public static FastdfsConfig initFastdfsConfig(Properties properties){
        if(properties.containsKey(FASTDFS_SO_TIMEOUT)){
            config.soTimeout = Integer.parseInt(properties.getProperty(FASTDFS_SO_TIMEOUT));
        }
        if(properties.containsKey(FASTDFS_CONNECT_TIMEOUT)){
            config.connectTimeout = Integer.parseInt(properties.getProperty(FASTDFS_CONNECT_TIMEOUT));
        }
        if(properties.containsKey(FASTDFS_MAX_TOTAL)){
            config.maxTotal = Integer.parseInt(properties.getProperty(FASTDFS_MAX_TOTAL));
        }
        if(properties.containsKey(FASTDFS_MAX_TOTAL_PER_KEY)){
            config.maxTotalPerKey = Integer.parseInt(properties.getProperty(FASTDFS_MAX_TOTAL_PER_KEY));
        }
        if(properties.containsKey(FASTDFS_MAX_IDLE_PER_KEY)){
            config.maxIdlePerKey = Integer.parseInt(properties.getProperty(FASTDFS_MAX_IDLE_PER_KEY));
        }

        config.trackers       = properties.getProperty(FASTDFS_TRACKERS);
        config.urlPrefix      = properties.getProperty(FASTDFS_URL_PREFIX);

        //使FastdfsUtil可用
        FastdfsUtil.init(config);

        return config;
    }
}
