package com.rubber.admin.security.auth;

import lombok.Data;

/**
 * @author luffyu
 * Created on 2019-10-30
 *
 * 认证的参数bean参数
 */
@Data
public class TokenVerifyBean {

    /**
     * token的版本 和 SysUser的版本一样
     * 当用户的SysUser发生了更新之后 之前的token将无效
     */
    private Integer version;

    /**
     * token中的subject
     */
    private String subject;


    public TokenVerifyBean(Integer version, String subject) {
        this.version = version;
        this.subject = subject;
    }
}
