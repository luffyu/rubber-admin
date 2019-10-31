package com.rubber.admin.core.enums;


import cn.hutool.coocaa.util.result.code.ICodeHandle;

/**
 * @author luffyu
 * Created on 2019-05-13
 *
 *  错误代码模块 code
 *
 *  编号规则： 1-00-00
 *
 *  第一部分： 一位，系统模块
 *  第二部分： 二位，业务模块
 *  第三部分： 二位，友好提示编号
 *  统一的成功Code 为 10100
 *
 *  全局系统模块 1-**-**
 *
 *  后台管理模块 2-**-**
 *
 *  其他的模块 3-**-**
 *
 */
public enum AdminCode implements ICodeHandle {

    /**
     * 错误状态码
     */
    SYS_BUSY("10202","系统繁忙"),
    PARAM_ERROR("10203","参数错误"),
    SHIRO_AUTH_ERROR("10204","没有操作权限"),

    /**
     * 已  2-**-** 开头
     */
    /**
     * 201** 登陆类型的错误code
     */
    USER_NOT_LOGIN("20100","用户未登陆"),
    LOGIN_AUTH_ERROR("20101","登陆密码错误"),
    LOGIN_USER_NOT_EXIST("20102","用户不存在"),
    USER_IS_DISABLE("20103","用户已经被禁用"),
    USER_IS_DELETE("20104","用户已经被删除"),
    TOKEN_IS_EXPIRED("20105","登陆token过期"),
    TOKEN_IS_ERROR("20106","登陆token错误"),
    LOGIN_TYPE_NOT_SUPPORT("20107","不支持的登陆类型"),

    ;
    /**
     * 业务错误码 000000的结构
     *
     * 前面 00 表示
     */
    public String code;

    public String msg;

    AdminCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }}
