package com.rubber.admin.core.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rubber.admin.core.base.BaseEntity;
import com.rubber.admin.core.plugins.cache.CacheAble;
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
public class SysUser extends BaseEntity implements CacheAble {

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
    @JsonIgnore
    private String salt;

    /**
     * 邮件地址
     */
    private String email;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 头像信息
     */
    private String avatar;

    /**
     * 性别 0表示女 1表示男 2表示未知
     */
    private Integer sex;

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
     * 账户状态 0可用 -1表示禁用
     */
    private Integer status;

    /**
     * 删除标志 0表示存在  -1表示逻辑删除
     */
    private Integer delFlag;

    /**
     * 超级管理员0否1是
     */
    private Integer superUser;

    /**
     * 备注
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
    private Integer updateBy;

    /**
     * 最好一个更新时间
     */
    private Date updateTime;

    /**
     * 版本号
     */
    private Integer version;


    /**
     * 缓存的版本号
     */
    @TableField(exist = false)
    private int cacheVersion;


    /**
     * 获取加密的key值
     * @param psw 用户输入的密码
     * @return
     */
    public String getEncodeKey(String psw){
        return psw + this.salt;
    }


    public void addCount(){
        if(this.loginCount == null){
            this.loginCount = 1;
        }else {
            this.loginCount ++ ;
        }
    }


    public void addVersion(){
        if(this.version == null){
            this.version = 1;
        }else {
            this.version ++ ;
        }
    }

    @Override
    public String getKey() {
        return this.loginAccount;
    }
}
