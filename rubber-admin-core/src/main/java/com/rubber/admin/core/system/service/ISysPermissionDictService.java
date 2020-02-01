package com.rubber.admin.core.system.service;

import com.rubber.admin.core.base.IBaseService;
import com.rubber.admin.core.system.entity.SysPermissionDict;
import com.rubber.admin.core.system.exception.DictException;

import java.util.List;

/**
 * <p>
 * 权限字典名称 服务类
 * </p>
 *
 * @author luffyu-auto
 * @since 2019-10-31
 */
public interface ISysPermissionDictService extends IBaseService<SysPermissionDict> {


    /**
     * 通过类型获取权限字典表
     * @param type 权限的字典表
     * @return 返回权限的字典信息
     */
    List<SysPermissionDict> selectByType(String type);


    /**
     * 验证并保存
     * @param sysPermissionDict 当前字典的key值
     */
    void verifyAndSave(SysPermissionDict sysPermissionDict) throws DictException;
}
