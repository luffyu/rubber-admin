package com.rubber.admin.core.enums;


import cn.hutool.luffyu.util.result.code.ICodeHandle;

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
     * 201** 用户类型的错误code
     */
    USER_NOT_LOGIN("20100","用户未登陆"),
    LOGIN_AUTH_ERROR("20101","登陆密码错误"),
    USER_NOT_EXIST("20102","用户不存在"),
    USER_IS_DISABLE("20103","用户已经被禁用"),
    USER_IS_DELETE("20104","用户已经被删除"),
    TOKEN_IS_EXPIRED("20105","登陆token过期"),
    TOKEN_IS_ERROR("20106","登陆token错误"),
    LOGIN_TYPE_NOT_SUPPORT("20107","不支持的登陆类型"),
    ACCOUNT_IS_EXIST("20108","账户已经存在"),
    LOGIN_VERSION_ILLEGAL("20109","账户登陆状态异常"),


    /**
     * 202** 角色类型的错误
     */
    ROLE_NOT_EXIST("20200","角色不存在"),
    ROLE_PRIVILEGE_ILLEGAL("20201","角色权限信息不合法"),
    ROLE_KEY_EXIST("20202","角色的key值已经存在"),
    ROLE_IS_DELETE("20203","角色已经被删除"),

    /**
     * 203** 菜单类型的错误
     */
    MENU_NOT_EXIST("20300","菜单不存在"),
    MENU_HAVE_CHILD("20301","菜单存在子菜单"),



    /**
     * 204** 部门类型的错误
     */
    DEPT_NOT_EXIST("20300","部门不存在"),
    DEPT_HAVE_CHILD("20301","部门存在子部门"),

    /**
     * 205** 字典类型的错误
     */
    DICT_IS_EXIST("20500","该Key值已经存在"),
    DICT_IS_NOT_EXIST("20501","该Key值已经存在"),

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
