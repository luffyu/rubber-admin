package com.rubber.admin.core.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.rubber.admin.core.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

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
     * 用户id
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    /**
     * 登陆账户
     */
    private String loginName;

    /**
     * 登陆密码
     */
    private String password;

    /**
     * 加密盐值
     */
    private String salt;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户的头像
     */
    private String avatar;

    /**
     * 用户性别 0表示女 1表示男 2表示未知
     */
    private String sex;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 0秒杀正常 1表示停用
     */
    private Integer status;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 创建人id
     */
    private Integer createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后一次更新人id
     */
    private Integer lastUpdateBy;

    /**
     * 最好一个更新时间
     */
    private Date lastUpdateTime;

    /**
     * 最后一次登陆的ip地址
     */
    private String lastLoginIp;

    /**
     * 最后一次登陆的时间
     */
    private Date lastLoginTime;

    /**
     * 总的登陆次数
     */
    private Integer loginNumber;


    /**
     * 是否是超级管理员 0表示不是 1表示是
     */
    private Boolean superAdmin;




}
