package com.rubber.admin.security.handle;

import com.rubber.admin.security.config.properties.RubberSecurityProperties;
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
    private RubberSecurityProperties rubberConfigProperties;

    public static RubberSecurityProperties config;

    @PostConstruct
    public void init() {
        config = this.rubberConfigProperties;
    }


}
