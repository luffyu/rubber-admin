package com.rubber.admin.core.system.model;

import com.rubber.admin.core.base.BaseModel;
import lombok.Data;

import java.util.Set;


/**
 * @author luffyu
 * Created on 2019-11-01
 *
 * 用户角色有的权限列表信息
 */
@Data
public class SysRoleMenuModel extends BaseModel {

    /**
     * 角色信息
     */
    private Integer roleId;

    /**
     * 角色的菜单信息
     */
    private Set<Integer> menuIds;

}
