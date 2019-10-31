package com.rubber.admin.core.plugins.cache;

import com.rubber.admin.core.system.entity.SysUser;

/**
 * @author luffyu
 * Created on 2019-10-28
 * 对用户信息的缓存
 */
public interface IUserCacheProvider {


    /**
     * 写入用户的缓存信息
     * @param sysUser 用户的信息
     * @param time 缓存时间 单位是毫秒
     * @return false表示更新失败
     */
    boolean write(SysUser sysUser,long time);


    /**
     * 更新用户的缓存信息
     * @param sysUser 用户的信息
     * @param time 缓存时间 单位是毫秒
     * @return true表示更新成功
     */
    boolean update(SysUser sysUser,long time);


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
    SysUser findByKey(String key);

}
