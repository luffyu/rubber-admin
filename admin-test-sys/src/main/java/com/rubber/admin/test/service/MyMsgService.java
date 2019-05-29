package com.rubber.admin.test.service;

import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;

/**
 * @author luffyu
 * Created on 2019-05-29
 */
@Service
public class MyMsgService {

    private static final String channel = "My_Test";


    @Resource
    private JedisCluster jedisCluster;


    public void send(String msg){
        jedisCluster.publish(channel,msg);
    }


    public void liseter(){
        jedisCluster.subscribe(new MyPubSub(),channel);
    }



}
