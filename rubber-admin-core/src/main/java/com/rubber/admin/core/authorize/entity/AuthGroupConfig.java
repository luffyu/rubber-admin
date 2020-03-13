package com.rubber.admin.core.authorize.entity;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.rubber.admin.core.authorize.AuthorizeKeys;
import com.rubber.admin.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
* <p>
* 权限族群配置表
* </p>
*
* @author luffyu-auto
* @since 2020-03-12
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class AuthGroupConfig extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
    * 部门id
    */
    @TableId(value = "group_id", type = IdType.AUTO)
    private Integer groupId;

    /**
    * 组关键字
    */
    private String groupKey;

    /**
    * 组名称
    */
    private String groupName;

    /**
    * 组类型
    */
    private String groupType;

    /**
    * 组成员 多个组成员用英文逗号隔开
    */
    private String groupMember;

    /**
    * 状态（0正常 -1停用）
    */
    private Integer status;

    /**
    * 最后一次更新人id
    */
    private Integer updateBy;

    /**
    * 最好一个更新时间
    */
    private LocalDateTime updateTime;

    /**
    * 备注
    */
    private String remark;


    /**
     * 独立的成员信息
     */
    @TableField(exist = false)
    private String[] members;


    public void setGroupMember(String groupMember) {
        this.groupMember = groupMember;
        if(StrUtil.isNotEmpty(groupMember)){
            this.members = StrUtil.split(groupMember, AuthorizeKeys.MEMBER_LINK_KEY);
        }
    }

}
