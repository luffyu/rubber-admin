package com.rubber.admin.core.plugins.security;

/**
 * @author luffyu
 * Created on 2019-10-23
 *
 * 组合起来的权限信息 就是 id + permission
 *
 * 执行某个方法 必须要有某个id 的 permission 权限
 *
 */
public @interface RubberAuthorize {

    /**
     * 某一个唯一的id值
     * @return 返回一个id值
     */
    String key();

    /**
     * 权限信息
     * 如果不填则表示没有权限
     *
     * @return 返回权限类型
     */
    PermissionEnums permission();

}
