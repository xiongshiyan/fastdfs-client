package top.jfunc.fastdfs.lizw.config;

import org.springframework.context.annotation.Import;
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({FastdfsClientConfig.class})
public @interface EnableFastdfsClient {
}