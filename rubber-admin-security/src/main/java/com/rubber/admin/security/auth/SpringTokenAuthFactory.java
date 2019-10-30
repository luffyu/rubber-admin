package com.rubber.admin.security.auth;

import com.rubber.admin.core.plugins.cache.IUserSecurityCache;
import com.rubber.admin.security.auth.jwt.JwtTokenVerifyService;
import com.rubber.admin.security.config.properties.RubbeSecurityProperties;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author luffyu
 * Created on 2019-10-28
 */
@Component
public class SpringTokenAuthFactory implements FactoryBean<ITokenVerifyService> {

    @Autowired
    private RubbeSecurityProperties rubbeSecurityProperties;

    @Autowired(required = false)
    private IUserSecurityCache userSecurityCache;


    @Override
    public ITokenVerifyService getObject() throws Exception {
        AuthType authType = rubbeSecurityProperties.getAuthType();
        switch (authType){
            case jwt:
                if(userSecurityCache == null){
                    return new JwtTokenVerifyService();
                }else {
                    return new JwtTokenVerifyService(userSecurityCache);
                }
            case session:
            case global_session:
            default:
                return new JwtTokenVerifyService();
        }
    }

    @Override
    public Class<?> getObjectType() {
        return ITokenVerifyService.class;
    }
}
