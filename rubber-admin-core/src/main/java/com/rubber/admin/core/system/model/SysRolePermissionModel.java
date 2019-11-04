package com.rubber.admin.core.system.model;

import com.rubber.admin.core.base.BaseModel;
import com.rubber.admin.core.system.entity.SysRole;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


/**
 * @author luffyu
 * Created on 2019-11-01
 *
 * 用户角色有的权限列表信息
 */
@Data
@AllArgsConstructor
public class SysRolePermissionModel extends BaseModel {

    /**
     * 角色信息
     */
    private SysRole role;

    /**
     * 角色的权限信息
     */
    private List<PermissionBean> permissionList;

}
