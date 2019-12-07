package top.jfunc.fastdfs.happyfish.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import top.jfunc.fastdfs.happyfish.util.FastdfsUtil;

import java.util.Properties;

/**
 * @author xiongshiyan at 2019/12/5 , contact me with email yanshixiong@126.com or phone 15208384257
 */
@ConfigurationProperties(prefix = "fastdfs")
public class FastdfsConfig {
    public static final FastdfsConfig config = new FastdfsConfig();
    private static final String FASTDFS_CONNECT_TIMEOUT_IN_SECONDS = "fastdfs.connectTimeoutInSeconds";
    private static final String FASTDFS_NETWORK_TIMEOUT_IN_SECONDS = "fastdfs.networkTimeoutInSeconds";
    private static final String FASTDFS_CHARSET                    = "fastdfs.charset";
    private static final String FASTDFS_HTTP_ANTI_STEAL_TOKEN      = "fastdfs.httpAntiStealToken";
    private static final String FASTDFS_HTTP_SECRET_KEY            = "fastdfs.httpSecretKey";
    private static final String FASTDFS_HTTP_TRACKER_HTTP_PORT     = "fastdfs.httpTrackerHttpPort";
    private static final String FASTDFS_TRACKERS                   = "fastdfs.trackers";
    private static final String FASTDFS_URL_PREFIX                 = "fastdfs.urlPrefix";

    private static final int DEFAULT_CONNECT_TIMEOUT_IN_SECONDS    = 5;
    private static final int DEFAULT_NETWORK_TIMEOUT_IN_SECONDS    = 30;
    private static final String DEFAULT_CHARSET                    = "UTF-8";
    private static final boolean DEFAULT_HTTP_ANTI_STEAL_TOKEN     = false;
    private static final String DEFAULT_HTTP_SECRET_KEY            = "FastDFS1234567890";
    private static final int DEFAULT_HTTP_TRACKER_HTTP_PORT        = 80;

    private int connectTimeoutInSeconds = DEFAULT_CONNECT_TIMEOUT_IN_SECONDS;
    private int networkTimeoutInSeconds = DEFAULT_NETWORK_TIMEOUT_IN_SECONDS;
    private String charset = DEFAULT_CHARSET;
    private boolean httpAntiStealToken = DEFAULT_HTTP_ANTI_STEAL_TOKEN;
    private String httpSecretKey = DEFAULT_HTTP_SECRET_KEY;
    private int httpTrackerHttpPort = DEFAULT_HTTP_TRACKER_HTTP_PORT;

    private String trackers;
    private String urlPrefix;

    public int getConnectTimeoutInSeconds() {
        return connectTimeoutInSeconds;
    }

    public void setConnectTimeoutInSeconds(int connectTimeoutInSeconds) {
        this.connectTimeoutInSeconds = connectTimeoutInSeconds;
    }

    public int getNetworkTimeoutInSeconds() {
        return networkTimeoutInSeconds;
    }

    public void setNetworkTimeoutInSeconds(int networkTimeoutInSeconds) {
        this.networkTimeoutInSeconds = networkTimeoutInSeconds;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public boolean isHttpAntiStealToken() {
        return httpAntiStealToken;
    }

    public void setHttpAntiStealToken(boolean httpAntiStealToken) {
        this.httpAntiStealToken = httpAntiStealToken;
    }

    public String getHttpSecretKey() {
        return httpSecretKey;
    }

    public void setHttpSecretKey(String httpSecretKey) {
        this.httpSecretKey = httpSecretKey;
    }

    public int getHttpTrackerHttpPort() {
        return httpTrackerHttpPort;
    }

    public void setHttpTrackerHttpPort(int httpTrackerHttpPort) {
        this.httpTrackerHttpPort = httpTrackerHttpPort;
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
    public static FastdfsConfig initFastdfsConfig(Properties properties) throws Exception{
        if(properties.containsKey(FASTDFS_CONNECT_TIMEOUT_IN_SECONDS)){
            config.connectTimeoutInSeconds = Integer.parseInt(properties.getProperty(FASTDFS_CONNECT_TIMEOUT_IN_SECONDS));
        }
        if(properties.containsKey(FASTDFS_NETWORK_TIMEOUT_IN_SECONDS)){
            config.networkTimeoutInSeconds = Integer.parseInt(properties.getProperty(FASTDFS_NETWORK_TIMEOUT_IN_SECONDS));
        }
        if(properties.containsKey(FASTDFS_CHARSET)){
            config.charset = properties.getProperty(FASTDFS_CHARSET);
        }
        if(properties.containsKey(FASTDFS_HTTP_ANTI_STEAL_TOKEN)){
            config.httpAntiStealToken = Boolean.parseBoolean(properties.getProperty(FASTDFS_HTTP_ANTI_STEAL_TOKEN));
        }
        if(properties.containsKey(FASTDFS_HTTP_SECRET_KEY)){
            config.httpSecretKey = properties.getProperty(FASTDFS_HTTP_SECRET_KEY);
        }
        if(properties.containsKey(FASTDFS_HTTP_TRACKER_HTTP_PORT)){
            config.httpTrackerHttpPort = Integer.parseInt(properties.getProperty(FASTDFS_HTTP_TRACKER_HTTP_PORT));
        }
        config.trackers                      = properties.getProperty(FASTDFS_TRACKERS);
        config.urlPrefix                     = properties.getProperty(FASTDFS_URL_PREFIX);

        //使FastdfsUtil可用
        FastdfsUtil.init(config);

        return config;
    }
}