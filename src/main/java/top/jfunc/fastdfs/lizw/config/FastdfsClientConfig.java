package top.jfunc.fastdfs.lizw.config;

import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.cleverframe.fastdfs.client.DefaultStorageClient;
import org.cleverframe.fastdfs.client.DefaultTrackerClient;
import org.cleverframe.fastdfs.client.StorageClient;
import org.cleverframe.fastdfs.client.TrackerClient;
import org.cleverframe.fastdfs.conn.CommandExecutor;
import org.cleverframe.fastdfs.conn.DefaultCommandExecutor;
import org.cleverframe.fastdfs.pool.ConnectionPool;
import org.cleverframe.fastdfs.pool.PooledConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 提供连接池功能
 * @see <a href="https://gitee.com/LiZhiW/fastdfs-java-client" target="_blank">FastDFS文件服务器Java客户端</a>
 * @author xiongshiyan at 2019/12/5 , contact me with email yanshixiong@126.com or phone 15208384257
 */
@Configuration
@EnableConfigurationProperties(FastdfsConfig.class)
//@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
public class FastdfsClientConfig {
    @Autowired
    private FastdfsConfig fastdfsConfig;

    @Bean(name = "fastdfsConnectionPool" , destroyMethod = "close")
    public ConnectionPool fastdfsConnectionPool(){
        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory(fastdfsConfig.getFastdfsSoTimeout() , fastdfsConfig.getFastdfsConnectTimeout());
        GenericKeyedObjectPoolConfig genericKeyedObjectPoolConfig = new GenericKeyedObjectPoolConfig();
        genericKeyedObjectPoolConfig.setMaxTotal(fastdfsConfig.getFastdfsMaxTotal());
        genericKeyedObjectPoolConfig.setMaxTotalPerKey(fastdfsConfig.getFastdfsMaxTotalPerKey());
        genericKeyedObjectPoolConfig.setMaxIdlePerKey(fastdfsConfig.getFastdfsMaxIdlePerKey());
        return new ConnectionPool(pooledConnectionFactory, genericKeyedObjectPoolConfig);
    }

    @Bean
    public CommandExecutor commandExecutor(ConnectionPool connectionPool){
        return new DefaultCommandExecutor(fastdfsConfig.getFastdfsTrackers() , connectionPool);
    }

    @Bean
    public TrackerClient trackerClient(CommandExecutor commandExecutor){
        return new DefaultTrackerClient(commandExecutor);
    }

    @Bean
    public StorageClient storageClient(CommandExecutor commandExecutor , TrackerClient trackerClient){
        return new DefaultStorageClient(commandExecutor, trackerClient);
    }
}
