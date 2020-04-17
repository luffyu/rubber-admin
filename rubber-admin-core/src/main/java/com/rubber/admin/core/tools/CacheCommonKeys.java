package com.rubber.admin.core.tools;

/**
 * <p>
 *     缓存公共的key值信息
 * </p>
 *
 * @author luffyu
 * @date 2020-04-17 10:28
 **/
public class CacheCommonKeys {

    /**
     * 用户的权限信息缓存
     */
    private static final String USER_AUTHORIZE_KEY = "user:authorize:key:";


    /**
     * 返回用户的缓存信息
     * @param userKeyId 用户keyId
     * @return 返回缓存的key值
     */
    public static String getByUserId(String userKeyId){
        return USER_AUTHORIZE_KEY + userKeyId;
    }
}
