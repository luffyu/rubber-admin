package com.rubber.admin.core.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rubber.admin.core.base.BaseEntity;
import lombok.Data;

import java.util.Date;
import java.util.Set;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author luffyu-auto
 * @since 2019-05-13
 */
@Data
public class SysUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

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
     * 密码
     */
    private String loginPwd;

    /**
     * 盐值
     */
    private String salt;

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
    private String sex;

    /**
     * 最后登录时间
     */
    private Date loginTime;

    /**
     * 最后登录IP
     */
    private String loginIp;

    /**
     * 登录次数
     */
    private Integer loginCount;

    /**
     * 0可用 -1禁用  -2删除
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改时间
     */
    private Date modifyDate;

    /**
     * 超级管理员0否1是
     */
    private Integer superUser;


    @Version
    private Integer version;

    /**
     * 用户的权限列表
     */
    @TableField(exist = false)
    @JsonIgnore
    private Set<String> permissions;


    /**
     * 获取加密的key值
     * @param psw 用户输入的密码
     * @return
     */
    public String getEncodeKey(String psw){
        return psw + this.salt;
    }
}
