package com.rubber.admin.framework.shiro.session;

import redis.clients.jedis.JedisPubSub;

/**
 * @author luffyu
 * Created on 2019-05-25
 */
public class RedisMsgPubSubListener extends JedisPubSub {

    @Override
    public void onMessage(String channel, String message) {
        System.out.println("channel:" + channel + "receives message :" + message);
        this.unsubscribe();
    }


    @Override
    public void onPMessage(String pattern, String channel, String message) {
        System.out.println(1);
    }

    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
        System.out.println(2);
    }

    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {
        System.out.println(3);
    }

    @Override
    public void onPUnsubscribe(String pattern, int subscribedChannels) {
        System.out.println(4);
    }

    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        System.out.println(5);
    }

    @Override
    public void onPong(String pattern) {

    }


}
