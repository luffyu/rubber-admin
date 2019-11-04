package com.rubber.admin.core.system.entity;

import com.rubber.admin.core.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <p>
 * 用户和角色关联表
 * </p>
 *
 * @author luffyu-auto
 * @since 2019-05-13
 */
@Data
@AllArgsConstructor
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


}
