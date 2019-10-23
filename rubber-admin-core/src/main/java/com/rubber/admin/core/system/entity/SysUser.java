package com.rubber.admin.core.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.rubber.admin.core.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

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
    private String loginName;

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
     * 最后登录时间
     */
    private LocalDateTime loginTime;

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
    private LocalDateTime createDate;

    /**
     * 修改时间
     */
    private LocalDateTime modifyDate;

    /**
     * 超级管理员0否1是
     */
    private Integer superUser;

    /**
     * 是否是超级管理员 0表示不是 1表示是
     */
    @TableField(exist = false)
    private Boolean superAdmin;




}
