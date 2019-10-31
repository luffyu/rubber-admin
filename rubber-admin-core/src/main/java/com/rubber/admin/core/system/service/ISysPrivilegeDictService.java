package com.rubber.admin.core.system.service;

import com.rubber.admin.core.base.IBaseService;
import com.rubber.admin.core.system.entity.SysPrivilegeDict;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
     * 通过类型获取权限字典表
     * @param type 权限的字典表
     * @return 返回权限的字典信息
     */
    Map<SysPrivilegeDict, Set<String>> selectDictByType(String type);


}
