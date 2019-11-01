package com.rubber.admin.core.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.rubber.admin.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
* <p>
* 角色权限列表
* </p>
*
* @author luffyu-auto
* @since 2019-11-01
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysRolePrivilege extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
    * 角色ID
    */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
    * 角色id
    */
    private Integer roleId;

    /**
    * 权限模块key
    */
    private String module;

    /**
    * 权限单元操作key
    */
    private String unitArray;

    /**
    * 创建人id
    */
    private Integer createBy;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 备注
    */
    private String remark;



    public SysRolePrivilege(SysRole sysRole, String module, String unitArray,Integer loginUserId) {
        this.roleId = sysRole.getRoleId();
        this.module = module;
        this.unitArray = unitArray;
        this.createBy = loginUserId;
        this.createTime = new Date();
    }
}
