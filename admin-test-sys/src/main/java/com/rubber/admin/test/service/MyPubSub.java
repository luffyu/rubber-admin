package com.rubber.admin.test.service;

import redis.clients.jedis.JedisPubSub;

/**
 * @author luffyu
 * Created on 2019-05-29
 */
public class MyPubSub extends JedisPubSub {

    @Override
    public void onMessage(String channel, String message) {
        System.out.println(channel+">>>>"+message);
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {
        System.out.println(2);
    }

    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
        System.out.println(channel+"?>>>>>"+subscribedChannels);
    }

    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {
        System.out.println(4);
    }

    @Override
    public void onPUnsubscribe(String pattern, int subscribedChannels) {
        System.out.println(5);
    }

    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        System.out.println(6);
    }

    @Override
    public void onPong(String pattern) {
        System.out.println(7);
    }

}
