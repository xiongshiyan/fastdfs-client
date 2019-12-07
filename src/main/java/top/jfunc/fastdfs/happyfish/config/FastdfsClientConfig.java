package top.jfunc.fastdfs.happyfish.config;

import org.csource.fastdfs.StorageClient1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.jfunc.fastdfs.happyfish.util.FastdfsUtil;

/**
 * fastdfs作者余庆自己写的java客户端，应该是最权威的
 * @see <a href="https://github.com/happyfish100/fastdfs-client-java" target="_blank">FastDFS文件服务器Java客户端</a>
 * @author xiongshiyan at 2019/12/6 , contact me with email yanshixiong@126.com or phone 15208384257
 */
@Configuration
@EnableConfigurationProperties(FastdfsConfig.class)
public class FastdfsClientConfig {
    @Autowired
    private FastdfsConfig fastdfsConfig;

    @Bean
    public StorageClient1 storageClient1() throws Exception{
        return FastdfsUtil.init(fastdfsConfig);
    }
}
