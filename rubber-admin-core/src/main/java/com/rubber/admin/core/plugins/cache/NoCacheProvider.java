package com.rubber.admin.core.plugins.cache;

/**
 * @author luffyu
 * Created on 2019-10-28
 * 不写入缓存信息
 */
public class NoCacheProvider implements ICacheProvider {
    @Override
    public boolean write(CacheAble cacheAble, long time) {
        return true;
    }

    @Override
    public boolean update(CacheAble cacheAble, long time) {
        return true;
    }

    @Override
    public boolean delete(String key) {
        return true;
    }

    @Override
    public CacheAble findByKey(String key) {
        return null;
    }

    @Override
    public int incrVersion() {
        return 0;
    }
    @Override
    public int version(){
        return 0;
    }
}
