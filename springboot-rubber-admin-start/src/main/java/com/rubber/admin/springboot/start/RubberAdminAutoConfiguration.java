package com.rubber.admin.springboot.start;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisCluster;

/**
 * @author luffyu
 * Created on 2019-05-21
 */
@Configuration
@ConditionalOnClass
public class RubberAdminAutoConfiguration {

}
