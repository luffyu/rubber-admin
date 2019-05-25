package com.rubber.admin.core.entity;

import com.rubber.admin.core.base.BaseEntity;

/**
 * <p>
 * 角色和部门关联表
 * </p>
 *
 * @author luffyu-auto
 * @since 2019-05-13
 */
public class SysRoleDept extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 部门ID
     */
    private Integer deptId;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    @Override
    public String toString() {
        return "SysRoleDept{" +
                "roleId=" + roleId +
                ", deptId=" + deptId +
                "}";
    }
}
