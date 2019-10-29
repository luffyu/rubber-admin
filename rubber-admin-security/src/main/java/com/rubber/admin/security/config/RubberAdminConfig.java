package com.rubber.admin.security.config;

import com.rubber.admin.core.plugins.cache.IUserSecurityCache;
import com.rubber.admin.core.plugins.cache.LocalUserSecurityCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author luffyu
 * Created on 2019-10-29
 */
@Configuration
public class RubberAdminConfig {



    @Bean
    public IUserSecurityCache getCache(){
        return new LocalUserSecurityCache();
    }
}
