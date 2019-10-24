package com.rubber.admin.core.plugins.security;

/**
 * @author luffyu
 * Created on 2019-10-23
 * 权限类型
 */
public enum PermissionEnums {

    /**
     * 查询权限
     */
    list,

    /**
     * 编辑权限
     */
    edit,

    /**
     * 添加权限
     */
    add,

    /**
     * 删除权限
     */
    delete,

    /**
     * 审核权限
     * 上架 下架
     */
    verify,


    /**
     * 公共权限
     * 默认登陆成功之后都有都权限
     */
    common,

    ;

}
