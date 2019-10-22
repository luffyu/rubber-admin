package com.rubber.admin.security.handle;

import com.rubber.admin.security.config.RubberConfigProperties;
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
    private RubberConfigProperties rubberConfigProperties;

    public static RubberConfigProperties config;

    @PostConstruct
    public void init() {
        config = this.rubberConfigProperties;
    }


}
