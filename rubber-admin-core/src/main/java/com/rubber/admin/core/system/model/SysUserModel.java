package com.rubber.admin.core.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.rubber.admin.core.base.BaseModel;
import com.rubber.admin.core.system.entity.SysUser;
import lombok.Data;

/**
 * @author luffyu
 * Created on 2019-10-30
 */
@Data
public class SysUserModel extends BaseModel {

    /**
     * ID
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 所属部门编号
     */
    private Integer deptId;

    /**
     * 账号
     */
    private String loginAccount;

    /**
     * 密码 明文 非加密字段
     */
    private String psw;


    /**
     * 邮件地址
     */
    private String email;

    /**
     * 头像信息
     */
    private String avatar;

    /**
     * 性别信息
     */
    private Integer sex;


    /**
     * 0可用 -1禁用  -2删除
     */
    private Integer status;


    /**
     * 超级管理员0否1是
     */
    private Integer superUser;


    public SysUserModel() {
    }

    public SysUserModel(SysUser sysUser) {
        if(sysUser != null){
            this.userId = sysUser.getUserId();
            this.userName = sysUser.getUserName();
            this.deptId = sysUser.getDeptId();
            this.loginAccount = sysUser.getLoginAccount();
            this.email = sysUser.getEmail();
            this.avatar = sysUser.getAvatar();
            this.sex = sysUser.getSex();
            this.status = sysUser.getStatus();
            this.superUser = sysUser.getSuperUser();
        }

    }

}
