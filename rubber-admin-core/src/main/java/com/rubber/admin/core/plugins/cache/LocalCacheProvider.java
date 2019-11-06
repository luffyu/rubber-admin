package com.rubber.admin.core.plugins.cache;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.LFUCache;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author luffyu
 * Created on 2019-10-28
 * 需要一个单例模式
 */
public class LocalCacheProvider implements ICacheProvider {

    /**
     * 创建一个单例的cache信息
     */
    private static LocalCacheProvider securityCache = new LocalCacheProvider();

    private static LFUCache<String, CacheAble> cache;

    /**
     * 版本号
     */
    private AtomicInteger cacheVersion = new AtomicInteger(0);


    public LocalCacheProvider() {
        /**
         * LFU(least frequently used) 最少使用率缓存<br>
         * 根据使用次数来判定对象是否被持续缓存<br>
         * 使用率是通过访问次数计算的。<br>
         * 当缓存满时清理过期对象。<br>
         * 清理后依旧满的情况下清除最少访问（访问计数最小）的对象并将其他对象的访问数减去这个最小访问数，以便新对象进入后可以公平计数。
         */
        cache = CacheUtil.newLFUCache(400);
    }

    public static LocalCacheProvider create(){
        return securityCache;
    }


    @Override
    public boolean write(CacheAble cacheAble, long time) {
        if(cacheAble == null){
            return false;
        }
        cacheAble.setCacheVersion(version());
        cache.put(cacheAble.getKey(),cacheAble,time);
        return true;
    }

    @Override
    public boolean update(CacheAble cacheAble, long time) {
        delete(cacheAble.getKey());
        return write(cacheAble,time);
    }

    @Override
    public boolean delete(String key) {
        cache.remove(key);
        return true;
    }

    @Override
    public CacheAble findByKey(String key) {
        return cache.get(key);
    }


    @Override
    public int incrVersion() {
        return cacheVersion.getAndIncrement();
    }

    /**
     * 返回当前的版本信息
     * @return 返回版本信息
     */
    @Override
    public int version(){
        return cacheVersion.get();
    }
}
