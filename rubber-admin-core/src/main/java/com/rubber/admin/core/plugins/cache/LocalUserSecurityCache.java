package com.rubber.admin.core.plugins.cache;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.LFUCache;
import com.rubber.admin.core.system.entity.SysUser;

/**
 * @author luffyu
 * Created on 2019-10-28
 */
public class LocalUserSecurityCache implements IUserSecurityCache {

    /**
     * LFU(least frequently used) 最少使用率缓存<br>
     * 根据使用次数来判定对象是否被持续缓存<br>
     * 使用率是通过访问次数计算的。<br>
     * 当缓存满时清理过期对象。<br>
     * 清理后依旧满的情况下清除最少访问（访问计数最小）的对象并将其他对象的访问数减去这个最小访问数，以便新对象进入后可以公平计数。
     */
    private LFUCache<String, SysUser> cache = CacheUtil.newLFUCache(400);


    @Override
    public boolean write(SysUser sysUser, long time) {
        if(sysUser == null){
            return false;
        }
        cache.put(sysUser.getLoginAccount(),sysUser,time);
        return true;
    }

    @Override
    public boolean update(SysUser sysUser, long time) {
        delete(sysUser.getLoginAccount());
        return write(sysUser,time);
    }

    @Override
    public boolean delete(String key) {
        cache.remove(key);
        return true;
    }

    @Override
    public SysUser findByKey(String key) {
        return cache.get(key);
    }

}
