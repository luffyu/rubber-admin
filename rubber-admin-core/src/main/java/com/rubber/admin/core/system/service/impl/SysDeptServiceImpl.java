package com.rubber.admin.core.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.luffyu.util.ArrayHashMap;
import cn.hutool.luffyu.util.result.code.SysCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rubber.admin.core.base.BaseAdminService;
import com.rubber.admin.core.enums.AdminCode;
import com.rubber.admin.core.enums.StatusEnums;
import com.rubber.admin.core.system.entity.SysDept;
import com.rubber.admin.core.system.exception.DeptException;
import com.rubber.admin.core.system.mapper.SysDeptMapper;
import com.rubber.admin.core.system.service.ISysDeptService;
import com.rubber.admin.core.tools.ServletUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author luffyu-auto
 * @since 2019-05-13
 */
@Service
public class SysDeptServiceImpl extends BaseAdminService<SysDeptMapper, SysDept> implements ISysDeptService {



    @Override
    public List<SysDept> treeList() {
        QueryWrapper<SysDept> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("seq");
        List<SysDept> list = list(queryWrapper);
        return handleListToTree(list);
    }

    /**
     * 获取菜单的树形结果
     * @param list 结果信息
     * @return
     */
    public List<SysDept> handleListToTree(List<SysDept> list){
        if(CollUtil.isEmpty(list)){
            return null;
        }
        ArrayHashMap<Integer,SysDept> arrayHashMap = new ArrayHashMap<>();
        list.forEach(i -> arrayHashMap.putAndAdd(i.getParentId(),i));
        SysDept rootDept = new SysDept();
        rootDept.setDeptId(0);
        findChildren(rootDept,arrayHashMap);
        return rootDept.getChildren();
    }
    /**
     * 查询出list的结果信息
     * @param sysDept 系统的部门
     * @param arrayHashMap 整理的结果信息
     */
    private void findChildren(SysDept sysDept,ArrayHashMap<Integer,SysDept> arrayHashMap){
        ArrayList<SysDept> children = arrayHashMap.get(sysDept.getDeptId());
        if(children != null){
            for(SysDept dept:children){
                findChildren(dept,arrayHashMap);
            }
            sysDept.setChildren(children);
        }
    }


    @Override
    public void saveOrUpdateDept(SysDept sysDept) throws DeptException {
        if(sysDept == null || sysDept.getParentId() == null){
            throw new DeptException(AdminCode.PARAM_ERROR,"保存的部门不存在");
        }
        if (sysDept.getParentId() != 0){
            getAndVerifyById(sysDept.getParentId());
        }
        if (sysDept.getDeptId() == null){
            doSaveDept(sysDept);
        }else {
            doUpdateDept(sysDept);
        }
    }



    @Override
    public SysDept getAndVerifyById(Integer deptId) throws DeptException {
        if(deptId == null || deptId <= 0){
            throw new DeptException(AdminCode.DEPT_NOT_EXIST,"部门id为{}",deptId);
        }
        SysDept byId = getById(deptId);
        if(byId == null){
            throw new DeptException(AdminCode.DEPT_NOT_EXIST,"部门id为{}",deptId);
        }
        return byId;
    }


    @Override
    public void delById(Integer deptId) throws DeptException {
        SysDept dbDept = getAndVerifyById(deptId);
        dbDept.setDelFlag(StatusEnums.DELETE);
        if(!updateById(dbDept)){
            throw new DeptException(SysCode.SYSTEM_ERROR,"删除部门信息失败",dbDept);
        }
    }


    private void doSaveDept(SysDept sysDept) throws DeptException {
        sysDept.setDelFlag(StatusEnums.NORMAL);
        sysDept.setStatus(StatusEnums.NORMAL);
        Date now = new Date();
        Integer loginUserId = ServletUtils.getLoginUserId();
        sysDept.setCreateBy(loginUserId);
        sysDept.setUpdateBy(loginUserId);
        sysDept.setCreateTime(now);
        sysDept.setUpdateTime(now);
        if(!save(sysDept)){
            throw new DeptException(SysCode.SYSTEM_ERROR,"添加部门信息失败",sysDept);
        }
    }



    private void doUpdateDept(SysDept sysDept) throws DeptException {
        SysDept dbDept = getAndVerifyById(sysDept.getDeptId());
        sysDept.setCreateBy(dbDept.getCreateBy());
        sysDept.setCreateTime(dbDept.getCreateTime());
        sysDept.setDelFlag(dbDept.getDelFlag());
        if(!updateById(sysDept)){
            throw new DeptException(SysCode.SYSTEM_ERROR,"添加部门信息失败",sysDept);
        }
    }




    @Override
    public boolean updateById(SysDept entity) {
        if(entity == null){
            return false;
        }
        Date now = new Date();
        Integer loginUserId = ServletUtils.getLoginUserId();
        entity.setUpdateBy(loginUserId);
        entity.setUpdateTime(now);
        return super.updateById(entity);
    }

}
