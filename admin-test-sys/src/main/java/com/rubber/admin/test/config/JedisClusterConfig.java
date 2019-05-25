package com.rubber.admin.test.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * @author luffyu
 * Created on 2019-05-25
 */
@Configuration
public class JedisClusterConfig {

    @Value("${spring.redis.cluster.nodes}")
    private String clusterNodes;
    @Value("${spring.redis.cluster.connectionTimeout}")
    private int connectionTimeout;
    @Value("${spring.redis.cluster.soTimeout}")
    private int soTimeout;
    @Value("${spring.redis.cluster.maxAttempts}")
    private int maxAttempts;
    @Value("${spring.redis.cluster.password}")
    private String password;



    @Bean
    public JedisCluster getJedisCluster() {
        JedisPoolConfig poolConfig= new JedisPoolConfig();

        // 截取集群节点
        String[] cluster = clusterNodes.split(",");
        // 创建set集合
        Set<HostAndPort> nodes = new HashSet<HostAndPort>();
        // 循环数组把集群节点添加到set集合中
        for (String node : cluster) {
            String[] host = node.split(":");
            //添加集群节点
            nodes.add(new HostAndPort(host[0], Integer.parseInt(host[1])));
        }
        JedisCluster jc = new JedisCluster(nodes,connectionTimeout,soTimeout,maxAttempts,password,poolConfig);

        return jc;
    }


}
