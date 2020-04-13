package com.rubber.admin.core.system.service;

import com.rubber.admin.core.base.IBaseService;
import com.rubber.admin.core.system.entity.SysDept;
import com.rubber.admin.core.system.exception.DeptException;

/**
 * <p>
 * 部门表 服务类
 * </p>
 *
 * @author luffyu-auto
 * @since 2019-05-13
 */
public interface ISysDeptService extends IBaseService<SysDept> {

    /**
     * 获取部门列表的菜单结构信息
     * @return 返回菜单的结构信息
     */
    SysDept rootTreeList();

    /**
     * 保存或者更新部门信息
     * @param sysDept 系统的部门信息
     * @throws DeptException 部门的异常
     */
    void saveOrUpdateDept(SysDept sysDept) throws DeptException;



    /**
     * 获取并验证一个部门信息
     * @param deptId deptId
     * @return
     * @throws DeptException
     */
    SysDept getAndVerifyById(Integer deptId) throws DeptException;


    /**
     * 通过id删除部门信息
     * @param deptId 部门信息
     * @throws DeptException
     */
    void delById(Integer deptId) throws DeptException;
}
