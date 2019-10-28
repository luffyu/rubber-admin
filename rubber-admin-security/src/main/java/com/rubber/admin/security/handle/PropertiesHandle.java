package com.rubber.admin.security.handle;

import com.rubber.admin.security.config.properties.RubbeSecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author luffyu
 * Created on 2019-10-22
 */
@Component
public class PropertiesHandle {


    @Autowired
    private RubbeSecurityProperties rubberConfigProperties;

    public static RubbeSecurityProperties config;

    @PostConstruct
    public void init() {
        config = this.rubberConfigProperties;
    }


}
