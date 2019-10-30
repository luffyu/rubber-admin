package com.rubber.admin.security.login.bean;

/**
 * @author luffyu
 * Created on 2019-10-30
 * 登陆类型
 */
public interface LoginType {

    /**
     * 通过账户密码登陆
     */
    int ACCOUNT = 0;


    /**
     * 域账户登陆
     */
    int LADP = 1;

    /**
     * 自定义登陆方法
     */
    int THIRD = 2;

}
