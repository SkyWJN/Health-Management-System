package com.wujiajun.config;

import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wujiajun
 * @date 2023/4/26/ 16:12
 */
@Configuration
public class BeanConfig {
    @Bean
    public UploadManager uploadManager() {
        com.qiniu.storage.Configuration cfg = new com.qiniu.storage.Configuration(Region.region2());
        return new UploadManager(cfg);
    }
}
