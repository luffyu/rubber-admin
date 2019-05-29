package com.rubber.admin.framework.shiro.session;

import com.rubber.admin.framework.shiro.session.redis.RedisSessionTools;
import org.apache.shiro.cache.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author luffyu
 * Created on 2019-05-23
 *
 * redis加本地缓存的双重操作 实现分布式的缓存机制
 */
public class RedisCacheSessionDao extends CachingSessionDAO {

    private static Logger logger = LoggerFactory.getLogger(RedisCacheSessionDao.class);

    /**
     * 默认session的失效时间
     */
    private static final int DEFAULT_SESSION_TIMEOUT = 30;

    @Autowired(required = false)
    private RedisSessionTools redisSessionTools;


    /**
     * session失效时间 单位为分钟
     */
    private int sessionTimeOut;


    public RedisCacheSessionDao() {
        setCacheManager(new AbstractCacheManager() {
            @Override
            protected Cache<Serializable, Session> createCache(String name) throws CacheException {
                return new MapCache<>(name, new ConcurrentHashMap<Serializable, Session>());
            }
        });
        this.sessionTimeOut = DEFAULT_SESSION_TIMEOUT;
    }


    public RedisCacheSessionDao(int sessionTimeOut) {
        setCacheManager(new AbstractCacheManager() {
            @Override
            protected Cache<Serializable, Session> createCache(String name) throws CacheException {
                return new MapCache<>(name, new ConcurrentHashMap<Serializable, Session>());
            }
        });
        this.sessionTimeOut = sessionTimeOut;
    }

    /**
     * 创建session 并 写入到本地缓存与redis缓存
     * @param session
     * @return
     */
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        //写入到缓存中
        redisSessionTools.doSetRedis(session,getSessionTimeOut() * 60);
        logger.info("创建session>>>" + session.getId());
        return sessionId;
    }


    /**
     * 当本地缓存中没有的时候会执行 doReadSession
     * 则调用次方法从缓存中读取
     * @param sessionId sessionId
     * @return
     */
    @Override
    protected Session doReadSession(Serializable sessionId) {
        logger.info("从redis中读取>>>" + sessionId);
        return redisSessionTools.doGetRedis(sessionId);
    }


    /**
     * 更新操作的时候 会执行次方法
     * 可以在这里设置缓存的过期时间
     * @param session 当前的session信息
     */
    @Override
    protected void doUpdate(Session session) {
        //does nothing - parent class persists to cache.
        redisSessionTools.doSetRedis(session,getSessionTimeOut() * 60);
        logger.info("更新session>>>" + session.getId());
    }


    /**
     * 删除 redis中的缓存信息
     * @param session session信息
     */
    @Override
    protected void doDelete(Session session) {
        //does nothing - parent class removes from cache.
        redisSessionTools.doDelRedis(session);
        //通知全部的信息 执行删除本地缓存的操作
        logger.info("删除redis中的session>>>" + session.getId());
    }


    public int getSessionTimeOut() {
        return sessionTimeOut;
    }


    /**
     * 重写一下 uncache 修改方法的访问级别为publish
     * @param session the session to remove from the cache.
     */
    @Override
    public void uncache(Session session) {
        super.uncache(session);
    }



    /**
     * 监听是否删除本地session
     */
    public void listerToDelLocalSession(){
        redisSessionTools.listerNodeSessionMsg(this);
    }

}
