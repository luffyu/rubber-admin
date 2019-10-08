package com.rubber.admin.core.system.entity;

import com.rubber.admin.core.base.BaseEntity;

/**
 * <p>
 * 用户和角色关联表
 * </p>
 *
 * @author luffyu-auto
 * @since 2019-05-13
 */
public class SysUserRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 角色ID
     */
    private Integer roleId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "SysUserRole{" +
                "userId=" + userId +
                ", roleId=" + roleId +
                "}";
    }
}
