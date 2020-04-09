package com.rubber.admin.core.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.rubber.admin.core.base.BaseEntity;
import com.rubber.admin.core.enums.MenuTypeEnums;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 菜单权限表
 * </p>
 *
 * @author luffyu-auto
 * @since 2019-05-13
 */
@Data
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
     * 删除标志（0代表存在 -1代表删除）
     */
    private Integer delFlag;


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
    private Integer updateBy;

    /**
     * 最好一个更新时间
     */
    private Date updateTime;

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
    private List<SysMenu> children ;


    /**
     * 当前菜单所有的权限key
     */
    @TableField(exist = false)
    private Set<String> menuOptionGroup;


    /**
     * 当前菜单可以操作的key值
     */
    @TableField(exist = false)
    private Set<String> optionKeys;

}
