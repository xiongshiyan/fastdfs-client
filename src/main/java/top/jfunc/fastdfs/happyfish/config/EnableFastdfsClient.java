package top.jfunc.fastdfs.happyfish.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({FastdfsClientConfig.class})
public @interface EnableFastdfsClient {
}