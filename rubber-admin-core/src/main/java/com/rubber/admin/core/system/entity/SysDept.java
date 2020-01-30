package com.rubber.admin.core.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.rubber.admin.core.base.BaseEntity;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 部门表
 * </p>
 *
 * @author luffyu-auto
 * @since 2019-05-13
 */
@Data
public class SysDept extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 部门id
     */
    @TableId(value = "dept_id", type = IdType.AUTO)
    private Integer deptId;

    /**
     * 父部门id 0表示根目录
     */
    private Integer parentId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 现实排序 最大支持 255
     */
    private Integer seq;

    /**
     * 负责人
     */
    private String leader;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 部门状态（0正常 1停用）
     */
    private Integer status;

    /**
     * 逻辑删除标志（0代表存在 -1代表删除）
     */
    private Integer delFlag;

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
     * 子结构
     */
    @TableField(exist = false)
    private List<SysDept> children;

}
