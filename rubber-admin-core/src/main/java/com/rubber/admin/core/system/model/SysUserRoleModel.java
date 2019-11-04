package com.rubber.admin.core.system.model;

import com.rubber.admin.core.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

/**
 * @author luffyu
 * Created on 2019-11-04
 */
@Data
@AllArgsConstructor
public class SysUserRoleModel extends BaseModel {

    /**
     * 用户的id
     */
    private Integer userId;

    /**
     * 用户的角色id
     */
    private Set<Integer>  roleIds;


    public SysUserRoleModel() {
    }

    public SysUserRoleModel(Integer userId) {
        this.userId = userId;
    }
}
