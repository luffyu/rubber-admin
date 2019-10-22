package com.rubber.admin.security.auth;

/**
 * @author luffyu
 * Created on 2019-10-22
 * 验证的类型
 */
public enum AuthType {

    /**
     * jwt类型
     */
    jwt,

    /**
     * session类型
     */
    session,


    /**
     * 全局session
     */
    global_session

    ;


}
