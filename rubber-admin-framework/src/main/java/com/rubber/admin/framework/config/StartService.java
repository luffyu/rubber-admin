package com.rubber.admin.framework.config;

import com.rubber.admin.framework.shiro.session.RedisCacheSessionDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * @author luffyu
 * Created on 2019-05-29
 */
@Component
@Order(value = 1)
public class StartService implements ApplicationRunner {

    private static Logger logger = LoggerFactory.getLogger(StartService.class);


    @Autowired
    private RedisCacheSessionDao redisCacheSessionDao;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("开始执行首先运行的方法》》》》》》");

        redisCacheSessionDao.listerToDelLocalSession();
    }
}
