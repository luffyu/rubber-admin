package com.rubber.admin.core.plugins.cache;

/**
 * @author luffyu
 * Created on 2019-10-28
 * 对用户信息的缓存
 */
public interface ICacheProvider {


    /**
     * 写入用户的缓存信息
     * @param cacheAble 用户的信息
     * @param time 缓存时间 单位是毫秒
     * @return false表示更新失败
     */
    boolean write(CacheAble cacheAble,long time);


    /**
     * 更新用户的缓存信息
     * @param cacheAble 用户的信息
     * @param time 缓存时间 单位是毫秒
     * @return true表示更新成功
     */
    boolean update(CacheAble cacheAble,long time);


    /**
     * 删除用户的缓存信息
     * @param key 必要的key值
     * @return true表示删除成功
     */
    boolean delete(String key);


    /**
     * 通过key查询用户的基本信息
     * @param key 缓存key
     * @return 返回用户的基本信息
     */
    CacheAble findByKey(String key);


    /**
     * 更新version
     * 更新了版本之前，上一个版本的缓存全部都会失效
     * @return 返回更新之后的版本号
     */
    int incrVersion();

    /**
     * 获取当前的缓存版本号
     * @return
     */
    int version();

}
