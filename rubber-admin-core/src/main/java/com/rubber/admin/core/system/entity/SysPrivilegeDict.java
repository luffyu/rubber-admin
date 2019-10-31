package com.rubber.admin.core.system.entity;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.rubber.admin.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
* <p>
    * 权限字典名称
    * </p>
*
* @author luffyu-auto
* @since 2019-10-31
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysPrivilegeDict extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
    * 自增id
    */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
    * 字典关键值
    */
    private String dictKey;

    /**
    * 字典名称
    */
    private String dictName;

    /**
    * 字典值
    */
    private String dictValue;

    /**
    * 字典类型
    */
    private String dictType;

    /**
    * 状态（0正常 -1停用）
    */
    private Integer status;

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


    @TableField(exist = false)
    private String[] dictCollect;


    public void setDictValue(String dictValue) {
        this.dictValue = dictValue;
        if(StrUtil.isNotEmpty(dictValue)){
            this.dictCollect = StrUtil.split(dictValue, ",");
        }
    }
}
