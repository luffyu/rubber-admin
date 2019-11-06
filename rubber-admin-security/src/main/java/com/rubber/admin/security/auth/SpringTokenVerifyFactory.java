package com.rubber.admin.security.auth;

import com.rubber.admin.security.auth.jwt.JwtTokenVerifyService;
import com.rubber.admin.security.config.properties.RubberSecurityProperties;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author luffyu
 * Created on 2019-10-28
 */
@Component
public class SpringTokenVerifyFactory implements FactoryBean<ITokenVerifyService> {

    @Autowired
    private RubberSecurityProperties rubberSecurityProperties;

    @Override
    public ITokenVerifyService getObject() throws Exception {
        AuthType authType = rubberSecurityProperties.getAuthType();
        switch (authType){
            case jwt:
                return new JwtTokenVerifyService();
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
