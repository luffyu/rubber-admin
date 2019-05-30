package com.rubber.admin.framework.shiro.session.redis;

import com.rubber.admin.framework.shiro.session.RedisCacheSessionDao;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import java.io.Serializable;

/**
 * @author luffyu
 * Created on 2019-05-24
 */
@Component
public class RedisSessionTools {


    private static Logger logger = LoggerFactory.getLogger(RedisSessionTools.class);


    @Autowired(required = false)
    private JedisCluster jedisCluster;

    /**
     * json对象格式
     */
    private static final String SESSION_REDIS_KEY = "session_key_";

    /**
     * 消息订阅的信息
     */
    private static final String SESSION_REDIS_CHANNEL = "session_channel_clear";


    /**
     * 获取 jedis客户端信息
     * @return 返回jedisCluster客户端
     */
    private JedisCluster getJedisCluster() {
        if(jedisCluster == null){
            throw new RuntimeException("jedisCluster is null");
        }
        return jedisCluster;
    }



    /**
     * 写入session到redis中
     * @param session 当前的session信息
     */
    public byte[] doSetRedis(Session session){
        byte[] values = ShiroSessionRedisUtil.sessionToByte(session);
        byte[] key = ShiroSessionRedisUtil.serializeKey(SESSION_REDIS_KEY+session.getId());
        getJedisCluster().set(key,values);
        return key;
    }

    /**
     * 更新session信息 并设置过期时间
     * @param session session信息
     * @param sessionTimeOut 设置session的过期时间
     */
    public void doSetRedis(Session session,int sessionTimeOut){
        byte[] key = doSetRedis(session);
        if(sessionTimeOut < 0){
            sessionTimeOut = 1;
        }
        getJedisCluster().expire(key,sessionTimeOut);
    }


    /**
     * 从缓存中获取缓存信息
     * @param sessionId 缓存的id
     * @return
     */
    public Session doGetRedis(Serializable sessionId){
        //从redis中读取session并返回
        if(sessionId == null){
            return null;
        }
        byte[] values = getJedisCluster().get(ShiroSessionRedisUtil.serializeKey(SESSION_REDIS_KEY+sessionId));
        if (values == null){
            return null;
        }
        Session session =  ShiroSessionRedisUtil.byteToSimpleSession(values);
        return session;
    }


    /**
     * 从redis中删除缓存
     * @param session session信息
     */
    public void doDelRedis(Session session){
        if(session != null){
            getJedisCluster().del(ShiroSessionRedisUtil.serializeKey(SESSION_REDIS_KEY+session.getId()));
            sendNodeToClearSession(session);
        }
    }


    /**
     * 通知其他的部署节点删除session信息
     * @param session session信息
     */
    public void sendNodeToClearSession(Session session){
        if(session !=null){
            byte[] values = ShiroSessionRedisUtil.serializeValue(new SendSessionMsg(session));
            byte[] key = ShiroSessionRedisUtil.serializeKey(SESSION_REDIS_CHANNEL);
            getJedisCluster().publish(key,values);
            logger.info("通过其他节点删除sessionId"+session.getId());
        }
    }


    /**
     * 监听session的基本信息
     */
    public void listerNodeSessionMsg(RedisCacheSessionDao sessionDAO){
        byte[] key = ShiroSessionRedisUtil.serializeKey(SESSION_REDIS_CHANNEL);
        getJedisCluster().subscribe(new RedisSessionPubSub(sessionDAO),key);
    }


}
