package com.rubber.admin.framework.shiro.session.redis;

import com.alibaba.fastjson.JSON;
import com.rubber.admin.framework.shiro.session.RedisCacheSessionDao;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.BinaryJedisPubSub;

/**
 * @author luffyu
 * Created on 2019-05-29
 */
public class RedisSessionPubSub extends BinaryJedisPubSub {

    private static Logger logger = LoggerFactory.getLogger(RedisSessionPubSub.class);


    private RedisCacheSessionDao sessionDAO;

    public RedisSessionPubSub() {
    }

    public RedisSessionPubSub(RedisCacheSessionDao sessionDAO) {
        this.sessionDAO = sessionDAO;
    }

    /**
     * 接受到消息的方法
     * @param channel
     * @param message
     */
    @Override
    public void onMessage(byte[] channel, byte[] message) {
        SendSessionMsg sessionMsg =  (SendSessionMsg)ShiroSessionRedisUtil.valueDeserialize(message);
        String channelString = ShiroSessionRedisUtil.keyDeserialize(channel);
        logger.info("接受到渠道{}的通知，信息为{}",channelString,message);
        sessionDAO.uncache(sessionMsg.getSession());
        logger.info("删除本地缓存的session成功");
    }

    /**
     * 订阅信息
     */
    @Override
    public void onSubscribe(byte[] channel, int subscribedChannels) {
        String channelString = ShiroSessionRedisUtil.keyDeserialize(channel);
        logger.info("成功订阅渠道{}",channelString);
    }

    /**
     * 取消订阅
     */
    @Override
    public void onUnsubscribe(byte[] channel, int subscribedChannels) {
        String channelString = ShiroSessionRedisUtil.keyDeserialize(channel);
        logger.info("取消订阅渠道{}",channelString);
    }

}
