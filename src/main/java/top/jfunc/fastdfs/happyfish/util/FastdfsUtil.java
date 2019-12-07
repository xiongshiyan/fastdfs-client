package top.jfunc.fastdfs.happyfish.util;

import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.jfunc.common.utils.FileUtil;
import top.jfunc.common.utils.IoUtil;
import top.jfunc.fastdfs.happyfish.config.FastdfsConfig;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author xiongshiyan at 2019/12/6 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class FastdfsUtil {
    private static final Logger logger = LoggerFactory.getLogger(FastdfsUtil.class);
    private static StorageClient1 storageClient1 = null;

    public static StorageClient1 init(FastdfsConfig fastdfsConfig) throws Exception{
        Properties properties = propertiesFromConfig(fastdfsConfig);
        ClientGlobal.initByProperties(properties);

        TrackerClient trackerClient = new TrackerClient(ClientGlobal.g_tracker_group);
        TrackerServer trackerServer = trackerClient.getConnection();
        if (trackerServer == null) {
            logger.error("getConnection return null");
        }
        StorageServer storageServer = trackerClient.getStoreStorage(trackerServer);
        if (storageServer == null) {
            logger.error("getStoreStorage return null");
        }
        storageClient1 = new StorageClient1(trackerServer, storageServer);
        return storageClient1;
    }
    private static Properties propertiesFromConfig(FastdfsConfig fastdfsConfig) {
        Properties properties = new Properties();
        properties.put(ClientGlobal.PROP_KEY_CONNECT_TIMEOUT_IN_SECONDS , fastdfsConfig.getConnectTimeoutInSeconds());
        properties.put(ClientGlobal.PROP_KEY_NETWORK_TIMEOUT_IN_SECONDS , fastdfsConfig.getNetworkTimeoutInSeconds());
        properties.put(ClientGlobal.PROP_KEY_CHARSET ,                    fastdfsConfig.getCharset());
        properties.put(ClientGlobal.PROP_KEY_HTTP_SECRET_KEY ,            fastdfsConfig.getHttpSecretKey());
        properties.put(ClientGlobal.PROP_KEY_HTTP_ANTI_STEAL_TOKEN ,      fastdfsConfig.isHttpAntiStealToken());
        properties.put(ClientGlobal.PROP_KEY_HTTP_TRACKER_HTTP_PORT ,     fastdfsConfig.getHttpTrackerHttpPort());
        properties.put(ClientGlobal.PROP_KEY_TRACKER_SERVERS ,            fastdfsConfig.getTrackers());
        return properties;
    }
    /**
     * 获取此{@link StorageClient1}就可以做任何事
     */
    public static StorageClient1 getStorageClient1(){
        return storageClient1;
    }

    /**
     *
     * @param file 文件
     * @return 返回Null则为失败
     */
    public static String uploadFile(File file) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            int len = fis.available();
            byte[] fileBuff = new byte[len];
            fis.read(fileBuff);

            String extName = FileUtil.getExtensionName(file.getName());
            return storageClient1.upload_file1(fileBuff, extName, null);
        } catch (Exception ex) {
            logger.error(ex.getMessage() , ex);
            return null;
        }finally{
            IoUtil.close(fis);
        }
    }

    public static String uploadFile(InputStream inputStream , String fileExt) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            IoUtil.copy(inputStream , outputStream);

            return storageClient1.upload_file1(outputStream.toByteArray(), fileExt, null);
        } catch (Exception ex) {
            logger.error(ex.getMessage() , ex);
            return null;
        }
    }

    /**
     * 根据组名和远程文件名来删除一个文件
     *
     * @param groupName
     *            例如 "group1" 如果不指定该值，默认为group1
     * @param fileName
     *            例如"M00/00/00/wKgxgk5HbLvfP86RAAAAChd9X1Y736.jpg"
     * @return 0为成功，非0为失败，具体为错误代码
     */
    public static int deleteFile(String groupName, String fileName) {
        try {
            return storageClient1.delete_file(groupName == null ? "group1" : groupName, fileName);
        } catch (Exception ex) {
            logger.error(ex.getMessage() , ex);
            return 0;
        }
    }

    /**
     * 根据fileId来删除一个文件（我们现在用的就是这样的方式，上传文件时直接将fileId保存在了数据库中）
     *
     * @param fileId
     *            file_id源码中的解释file_id the file id(including group name and filename);例如 group1/M00/00/00/ooYBAFM6MpmAHM91AAAEgdpiRC0012.xml
     * @return 0为成功，非0为失败，具体为错误代码
     */
    public static int deleteFile(String fileId) {
        try {
            return storageClient1.delete_file1(fileId);
        } catch (Exception ex) {
            logger.error(ex.getMessage() , ex);
            return 0;
        }
    }

    /**
     * 修改一个已经存在的文件
     *
     * @param oldFileId
     *            原来旧文件的fileId, file_id源码中的解释file_id the file id(including group name and filename);例如 group1/M00/00/00/ooYBAFM6MpmAHM91AAAEgdpiRC0012.xml
     * @param file
     *            新文件
     * @return 返回空则为失败
     */
    public static String modifyFile(String oldFileId, File file) {
        String fileId = null;
        try {
            // 先上传
            fileId = uploadFile(file);
            if (fileId == null) {
                return null;
            }
            // 再删除
            int delResult = deleteFile(oldFileId);
            if (delResult != 0) {
                return null;
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage() , ex);
            return null;
        }
        return fileId;
    }

    /**
     * 文件下载
     * @return 返回一个字节数组
     */
    public static byte[] downloadFile(String fileId) {
        try {
            return storageClient1.download_file1(fileId);
        } catch (Exception ex) {
            logger.error(ex.getMessage() , ex);
            return null;
        }
    }
}
