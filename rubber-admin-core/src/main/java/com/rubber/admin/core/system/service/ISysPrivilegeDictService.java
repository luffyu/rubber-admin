package com.rubber.admin.core.system.service;

import com.rubber.admin.core.base.IBaseService;
import com.rubber.admin.core.system.entity.SysPrivilegeDict;
import com.rubber.admin.core.system.model.PrivilegeBean;

import java.util.List;

/**
 * <p>
 * 权限字典名称 服务类
 * </p>
 *
 * @author luffyu-auto
 * @since 2019-10-31
 */
public interface ISysPrivilegeDictService extends IBaseService<SysPrivilegeDict> {


    /**
     * 通过类型获取权限字典表
     * @param type 权限的字典表
     * @return 返回权限的字典信息
     */
    List<SysPrivilegeDict> selectByType(String type);



    /**
     * 获取全部的权限列表
     * @return 全部的权限信息
     */
    List<PrivilegeBean> allPrivilege();


}
