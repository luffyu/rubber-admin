package com.rubber.admin.security.auth;

import com.rubber.admin.security.auth.jwt.JwtTokenAuthService;
import com.rubber.admin.security.config.properties.RubbeSecurityProperties;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author luffyu
 * Created on 2019-10-28
 */
@Component
public class SpringTokenAuthFactory implements FactoryBean<ITokenAuthService> {

    @Autowired
    private RubbeSecurityProperties rubbeSecurityProperties;


    @Override
    public ITokenAuthService getObject() throws Exception {
        AuthType authType = rubbeSecurityProperties.getAuthType();
        switch (authType){
            case jwt:
                return new JwtTokenAuthService();
            case session:
            case global_session:
            default:
                return new JwtTokenAuthService();
        }
    }

    @Override
    public Class<?> getObjectType() {
        return ITokenAuthService.class;
    }
}
