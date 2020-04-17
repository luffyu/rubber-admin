package com.rubber.admin.core.system.model;

import com.rubber.admin.core.plugins.cache.CacheAble;
import com.rubber.admin.core.tools.CacheCommonKeys;
import lombok.Data;

import java.util.Set;

/**
 * <p></p>
 *
 * @author luffyu
 * @date 2020-04-17 10:48
 **/
@Data
public class UserAuthorizeModel implements CacheAble {

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 返回缓存的信息
     */
    private Set<String> authorizeKeys;

    /**
     * 缓存的版本号
     */
    private int cacheVersion;



    public UserAuthorizeModel(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String getKey() {
        return CacheCommonKeys.getByUserId(this.userId.toString());
    }


    public void addVersion(){
        this.cacheVersion ++ ;
    }
}
