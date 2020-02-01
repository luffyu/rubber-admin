package com.rubber.admin.core.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.luffyu.util.result.code.SysCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rubber.admin.core.base.BaseAdminService;
import com.rubber.admin.core.exceptions.AdminException;
import com.rubber.admin.core.system.entity.SysUser;
import com.rubber.admin.core.system.entity.SysUserRole;
import com.rubber.admin.core.system.exception.RoleException;
import com.rubber.admin.core.system.mapper.SysUserRoleMapper;
import com.rubber.admin.core.system.model.SysUserRoleModel;
import com.rubber.admin.core.system.service.ISysRoleService;
import com.rubber.admin.core.system.service.ISysUserRoleService;
import com.rubber.admin.core.system.service.ISysUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户和角色关联表 服务实现类
 * </p>
 *
 * @author luffyu-auto
 * @since 2019-05-13
 */
@Service
public class SysUserRoleServiceImpl extends BaseAdminService<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {


    @Resource
    private ISysUserService sysUserService;

    @Resource
    private ISysRoleService sysRoleService;

    /**
     * 用户的角色信息
     * @param sysUserRoleModel 系统的角色
     */
    @Transactional(
            rollbackFor = Exception.class
    )
    @Override
    public void saveUserRole(SysUserRoleModel sysUserRoleModel) throws AdminException {
        SysUser sysUser = sysUserService.getAndVerifyNullById(sysUserRoleModel.getUserId());
        List<SysUserRole> sysUserRoles = null;
        Set<Integer> roleIds = sysUserRoleModel.getRoleIds();
        if(CollectionUtil.isNotEmpty(roleIds)){
            sysUserRoles = new ArrayList<>(roleIds.size());
            for(Integer id:roleIds){
                sysRoleService.getAndVerifyById(id);
                sysUserRoles.add(new SysUserRole(sysUser.getUserId(),id));
            }
        }
        if(!removeByUserId(sysUser.getUserId())){
            throw new RoleException(SysCode.SYSTEM_ERROR,"删除的{}的角色信息异常",sysUser.getUserId());
        }
        if(CollectionUtil.isNotEmpty(sysUserRoles)){
            if(!saveBatch(sysUserRoles)){
                throw new RoleException(SysCode.SYSTEM_ERROR,"新增用户的{}的角色信息异常",sysUser.getUserId());
            }
        }
    }



    @Override
    public boolean removeByUserId(Integer userId) {
        if(userId == null || userId <= 0){
            return false;
        }
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        int count = count(queryWrapper);
        int delete = getBaseMapper().delete(queryWrapper);
        if(delete == count){
            return true;
        }
        return false;
    }



    @Override
    public SysUserRoleModel getRoleByUserId(Integer userId) {
        if(userId == null){
            return null;
        }
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        SysUserRoleModel sysUserRoleModel = new SysUserRoleModel(userId);
        List<SysUserRole> list = list(queryWrapper);
        if(CollectionUtil.isNotEmpty(list)){
            Set<Integer> collect = list.stream().map(SysUserRole::getRoleId).collect(Collectors.toSet());
            sysUserRoleModel.setRoleIds(collect);
        }
        return sysUserRoleModel;
    }
}
