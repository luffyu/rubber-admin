package com.rubber.admin.core.authorize.entity;

    import com.baomidou.mybatisplus.annotation.IdType;
    import com.rubber.admin.core.base.BaseEntity;
    import com.baomidou.mybatisplus.annotation.TableId;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

/**
* <p>
    * 权限族和菜单管理表
    * </p>
*
* @author luffyu-auto
* @since 2020-03-13
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class AuthGroupMenu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
    * 部门id
    */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
    * 组关键字
    */
    private Integer menuId;

    /**
    * 操作的key值
    */
    private String optionKey;

    /**
    * 想关联的应用名称
    */
    private String relatedApply;


}
