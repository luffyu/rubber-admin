package com.rubber.admin.core.plugins.cache;

/**
 * @author luffyu
 * Created on 2019-10-31
 */
public class CacheProviderFactory {


    public static ICacheProvider builder(){
        return LocalCacheProvider.create();
    }
}
