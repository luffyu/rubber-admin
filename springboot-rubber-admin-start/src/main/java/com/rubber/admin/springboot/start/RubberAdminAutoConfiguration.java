package com.rubber.admin.springboot.start;

import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.rubber.admin.core.plugins.cache.ICacheProvider;
import com.rubber.admin.core.plugins.cache.LocalCacheProvider;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
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
@ServletComponentScan
public class RubberAdminAutoConfiguration {


    /**
     * 默认的缓存信息
     */
    @Bean
    @ConditionalOnMissingBean(ICacheProvider.class)
    public ICacheProvider cacheProvider() {
        return new LocalCacheProvider();
    }


    /**
     * 开启MybatisPlus分页插件
     */
    @Bean
    @ConditionalOnMissingBean(PaginationInterceptor.class)
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }


    /**
     * 开启MyBatisPlus乐观锁插件
     */
    @Bean
    @ConditionalOnMissingBean(OptimisticLockerInterceptor.class)
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

}
