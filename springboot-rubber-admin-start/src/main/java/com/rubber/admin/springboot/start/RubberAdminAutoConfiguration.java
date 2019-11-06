package com.rubber.admin.springboot.start;

import com.rubber.admin.core.plugins.cache.ICacheProvider;
import com.rubber.admin.core.plugins.cache.LocalCacheProvider;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author luffyu
 * Created on 2019-05-21
 */
@Configuration
@ComponentScan("com.rubber.admin.**")
@MapperScan("com.rubber.admin.**.mapper.**")
public class RubberAdminAutoConfiguration {


    /**
     * 默认的缓存信息
     */
    @Bean
    @ConditionalOnMissingBean(ICacheProvider.class)
    public ICacheProvider cacheProvider() {
        return new LocalCacheProvider();
    }


}
