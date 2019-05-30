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
 *
 * 在项目启动的时候 自动订阅删除本地session的消息
 * 如果接受到了小心 则删除本地缓存的session信息
 */
@Component
@Order(value = 1)
public class StartService implements ApplicationRunner {

    private static Logger logger = LoggerFactory.getLogger(StartService.class);


    @Autowired
    private RedisCacheSessionDao redisCacheSessionDao;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        redisCacheSessionDao.listerToDelLocalSession();
    }
}
