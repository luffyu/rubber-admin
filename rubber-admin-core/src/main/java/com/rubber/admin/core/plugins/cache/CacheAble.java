package com.rubber.admin.core.plugins.cache;

/**
 * @author luffyu
 * Created on 2019-11-06
 */
public interface CacheAble {

    /**
     * 缓存信息的版本
     * @return 返回缓存信息的版本
     */
    int getCacheVersion();



    /**
     * 设置缓存信息的版本
     * @param version 设置当前的版本号
     */
    void setCacheVersion(int version);

    /**
     * 返回缓存key值
     * @return 返回缓存的key值
     */
    String getKey();
}
