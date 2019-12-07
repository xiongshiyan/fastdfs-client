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
    public static final String FASTDFS_SO_TIMEOUT           = "fastdfs.soTimeout";
    public static final String FASTDFS_CONNECT_TIMEOUT      = "fastdfs.connectTimeout";
    public static final String FASTDFS_MAX_TOTAL            = "fastdfs.maxTotal";
    public static final String FASTDFS_MAX_TOTAL_PER_KEY    = "fastdfs.maxTotalPerKey";
    public static final String FASTDFS_MAX_IDLE_PER_KEY     = "fastdfs.maxIdlePerKey";
    public static final String FASTDFS_TRACKERS             = "fastdfs.trackers";
    public static final String FASTDFS_URL_PREFIX           = "fastdfs.urlPrefix";

    private int fastdfsSoTimeout;
    private int fastdfsConnectTimeout;
    private int fastdfsMaxTotal;
    private int fastdfsMaxTotalPerKey;
    private int fastdfsMaxIdlePerKey;

    private String fastdfsTrackers;
    private String fastdfsUrlPrefix;

    public int getFastdfsSoTimeout() {
        return fastdfsSoTimeout;
    }

    public void setFastdfsSoTimeout(int fastdfsSoTimeout) {
        this.fastdfsSoTimeout = fastdfsSoTimeout;
    }

    public int getFastdfsConnectTimeout() {
        return fastdfsConnectTimeout;
    }

    public void setFastdfsConnectTimeout(int fastdfsConnectTimeout) {
        this.fastdfsConnectTimeout = fastdfsConnectTimeout;
    }

    public int getFastdfsMaxTotal() {
        return fastdfsMaxTotal;
    }

    public void setFastdfsMaxTotal(int fastdfsMaxTotal) {
        this.fastdfsMaxTotal = fastdfsMaxTotal;
    }

    public int getFastdfsMaxTotalPerKey() {
        return fastdfsMaxTotalPerKey;
    }

    public void setFastdfsMaxTotalPerKey(int fastdfsMaxTotalPerKey) {
        this.fastdfsMaxTotalPerKey = fastdfsMaxTotalPerKey;
    }

    public int getFastdfsMaxIdlePerKey() {
        return fastdfsMaxIdlePerKey;
    }

    public void setFastdfsMaxIdlePerKey(int fastdfsMaxIdlePerKey) {
        this.fastdfsMaxIdlePerKey = fastdfsMaxIdlePerKey;
    }

    public String getFastdfsTrackers() {
        return fastdfsTrackers;
    }

    public void setFastdfsTrackers(String fastdfsTrackers) {
        this.fastdfsTrackers = fastdfsTrackers;
    }

    public String getFastdfsUrlPrefix() {
        return fastdfsUrlPrefix;
    }

    public void setFastdfsUrlPrefix(String fastdfsUrlPrefix) {
        this.fastdfsUrlPrefix = fastdfsUrlPrefix;
    }

    /**
     * 非 Spring 项目调用此方法初始化config和util
     */
    public static FastdfsConfig initFastdfsConfig(Properties properties){
        config.fastdfsSoTimeout      = Integer.parseInt(properties.getProperty(FASTDFS_SO_TIMEOUT));
        config.fastdfsConnectTimeout = Integer.parseInt(properties.getProperty(FASTDFS_CONNECT_TIMEOUT));
        config.fastdfsMaxTotal       = Integer.parseInt(properties.getProperty(FASTDFS_MAX_TOTAL));
        config.fastdfsMaxTotalPerKey = Integer.parseInt(properties.getProperty(FASTDFS_MAX_TOTAL_PER_KEY));
        config.fastdfsMaxIdlePerKey  = Integer.parseInt(properties.getProperty(FASTDFS_MAX_IDLE_PER_KEY));
        config.fastdfsTrackers       = properties.getProperty(FASTDFS_TRACKERS);
        config.fastdfsUrlPrefix      = properties.getProperty(FASTDFS_URL_PREFIX);

        //使FastdfsUtil可用
        FastdfsUtil.init(config);

        return config;
    }
}
