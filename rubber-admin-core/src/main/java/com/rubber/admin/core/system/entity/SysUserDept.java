package com.rubber.admin.core.system.entity;

import com.rubber.admin.core.base.BaseEntity;
import lombok.Data;

/**
 * <p>
 * 角色和部门关联表
 * </p>
 *
 * @author luffyu-auto
 * @since 2019-05-13
 */
@Data
public class SysUserDept extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 部门ID
     */
    private Integer deptId;


}
