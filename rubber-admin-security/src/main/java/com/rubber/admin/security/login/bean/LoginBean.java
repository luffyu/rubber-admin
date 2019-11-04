package com.rubber.admin.security.login.bean;

import lombok.Data;

import java.util.Date;

/**
 * @author luffyu
 * Created on 2019-10-30
 */
@Data
public class LoginBean {
    /**
     * 登陆名称
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 登陆类型
     */
    private int loginType;

    /**
     * 登陆的时间
     */
    private Date loginTime = new Date();


    public LoginBean() {
    }

    public LoginBean(String account) {
        this.account = account;
    }
}
