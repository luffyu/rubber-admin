package com.rubber.admin.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.rubber.admin.core.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.rubber.admin.core.enums.MenuTypeEnums;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 菜单权限表
 * </p>
 *
 * @author luffyu-auto
 * @since 2019-05-13
 */
public class SysMenu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID
     */
    @TableId(value = "menu_id", type = IdType.AUTO)
    private Integer menuId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 父菜单ID
     */
    private Integer parentId;

    /**
     * 现实排序 最大支持 255
     */
    private Integer seq;

    /**
     * 请求地址
     */
    private String url;

    /**
     * 菜单类型（M目录 C菜单,B按钮）
     */
    private MenuTypeEnums menuType;

    /**
     * 权限标示key值
     */
    private String authKey;

    /**
     * 状态（0正常 1停用）
     */
    private Integer status;

    /**
     * 菜单图标
     */
    private String icon;

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
     * 备注
     */
    private String remark;


    /**
     * 父级菜单
     */
    @TableField(exist = false)
    private SysMenu fatherMenu;


    /**
     * 子节点菜单
     */
    @TableField(exist = false)
    private List<SysMenu> childMenus = new ArrayList<>();


    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public MenuTypeEnums getMenuType() {
        return menuType;
    }

    public void setMenuType(MenuTypeEnums menuType) {
        this.menuType = menuType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }



    public Integer getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(Integer lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public SysMenu getFatherMenu() {
        return fatherMenu;
    }

    public void setFatherMenu(SysMenu fatherMenu) {
        this.fatherMenu = fatherMenu;
    }

    public List<SysMenu> getChildMenus() {
        return childMenus;
    }

    public void setChildMenus(List<SysMenu> childMenus) {
        this.childMenus = childMenus;
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    @Override
    public String toString() {
        return "SysMenu{" +
                "menuId=" + menuId +
                ", menuName=" + menuName +
                ", parentId=" + parentId +
                ", seq=" + seq +
                ", url=" + url +
                ", menuType=" + menuType +
                ", status=" + status +
                ", icon=" + icon +
                ", createBy=" + createBy +
                ", createTime=" + createTime +
                ", lastUpdateBy=" + lastUpdateBy +
                ", lastUpdateTime=" + lastUpdateTime +
                ", remark=" + remark +
                "}";
    }
}
