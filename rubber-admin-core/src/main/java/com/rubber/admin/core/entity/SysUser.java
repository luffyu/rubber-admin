package com.rubber.admin.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.rubber.admin.core.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(Integer lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getLoginNumber() {
        return loginNumber;
    }

    public void setLoginNumber(Integer loginNumber) {
        this.loginNumber = loginNumber;
    }

    public Boolean getSuperAdmin() {
        return superAdmin;
    }

    public void setSuperAdmin(Boolean superAdmin) {
        this.superAdmin = superAdmin;
    }

    @Override
    public String toString() {
        return "SysUser{" +
                "userId=" + userId +
                ", loginName=" + loginName +
                ", loginPassword=" + password +
                ", salt=" + salt +
                ", userName=" + userName +
                ", avatar=" + avatar +
                ", sex=" + sex +
                ", email=" + email +
                ", phone=" + phone +
                ", status=" + status +
                ", remark=" + remark +
                ", createBy=" + createBy +
                ", createTime=" + createTime +
                ", lastUpdateBy=" + lastUpdateBy +
                ", lastUpdateTime=" + lastUpdateTime +
                ", lastLoginIp=" + lastLoginIp +
                ", lastLoginTime=" + lastLoginTime +
                ", loginNumber=" + loginNumber +
                "}";
    }


}
