package com.rubber.admin.core.authorize;

/**
 * <p>一些关键字</p>
 *
 * @author luffyu
 * @date 2020-03-12 16:28
 **/
public class AuthorizeKeys {

    /**
     * 权限链接字符
     */
    public static final String AUTH_LINK_KEY = ":";

    /**
     * url请求的链接key
     */
    public static final String URL_LINK_KEY = "/";

    /**
     * url中的版本字段
     */
    public static final String URL_VERION_REGEX = "^(v[0-9])";


    /**
     * 成员链接字符
     */
    public static final String MEMBER_LINK_KEY = ",";


    /**
     * 默认的模块权限
     */
    public static final String DEFAULT_MODEL_KEY = "common";


    /**
     * 超级管理员的权限
     */
    public static final int SUPER_ADMIN_FLAG = 1;

    /**
     * 下划线链接字符
     */
    public static final String UNDER_LINE_KEY = "_";

}
