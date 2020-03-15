package com.rubber.admin.core.system.service.impl;

import cn.hutool.luffyu.util.result.code.SysCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rubber.admin.core.base.BaseAdminService;
import com.rubber.admin.core.enums.AdminCode;
import com.rubber.admin.core.enums.StatusEnums;
import com.rubber.admin.core.system.entity.SysRole;
import com.rubber.admin.core.system.exception.RoleException;
import com.rubber.admin.core.system.mapper.SysRoleMapper;
import com.rubber.admin.core.system.service.ISysRoleMenuService;
import com.rubber.admin.core.system.service.ISysRoleService;
import com.rubber.admin.core.tools.ServletUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author luffyu-auto
 * @since 2019-05-13
 */
@Service
public class SysRoleServiceImpl extends BaseAdminService<SysRoleMapper, SysRole> implements ISysRoleService {

    @Resource
    private ISysRoleMenuService iSysRoleMenuService;

    /**
     * 通过用户的id查询角色信息
     * @param userId 用户id
     * @return 返回用户的角色list信息
     */
    @Override
    public List<SysRole> findByUserId(Integer userId) {
        return getBaseMapper().findByUserId(userId);
    }




    @Override
    public SysRole getAndVerifyById(Integer roleId) throws RoleException {
        if(roleId == null){
            throw new RoleException(AdminCode.ROLE_NOT_EXIST);
        }
        SysRole byId = getById(roleId);
        if(byId == null){
            throw new RoleException(AdminCode.ROLE_NOT_EXIST);
        }
        if (byId.getDelFlag() == StatusEnums.DISABLE){
            throw new RoleException(AdminCode.ROLE_IS_DELETE);
        }
        return byId;
    }


    @Override
    public SysRole getByRoleKey(String roleKey)  {
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_key",roleKey);
        return getOne(queryWrapper);
    }


    @Transactional(
            rollbackFor = Exception.class
    )
    @Override
    public boolean saveOrUpdateRole(SysRole sysRole) throws RoleException {
        if(sysRole == null){
            return false;
        }
        if(sysRole.getRoleId() == null){
            doSave(sysRole);
        }else {
            doUpdate(sysRole);
        }
        iSysRoleMenuService.addRoleMenuOption(sysRole);
        return true;
    }

    @Transactional(
            rollbackFor = Exception.class
    )
    @Override
    public void delRoleById(Integer roleId) throws RoleException {
        SysRole dbRole = getAndVerifyById(roleId);
        dbRole.setDelFlag(StatusEnums.DELETE);
        if(!updateById(dbRole)){
            throw new RoleException(AdminCode.ROLE_NOT_EXIST,"删除角色信息失败",dbRole);
        }
    }


    /**
     * 保存角色信息
     * @param sysRole
     * @return
     */
    private boolean doSave(SysRole sysRole) throws RoleException {
        //验证key是否已经存在
        SysRole byRoleKey = getByRoleKey(sysRole.getRoleKey());
        if(byRoleKey != null){
            throw new RoleException(AdminCode.ROLE_KEY_EXIST);
        }
        Date now = new Date();
        Integer loginUserId = ServletUtils.getLoginUserId();

        sysRole.setCreateBy(loginUserId);
        sysRole.setCreateTime(now);
        sysRole.setUpdateBy(loginUserId);
        sysRole.setUpdateTime(now);
        if(!save(sysRole)){
            throw new RoleException(SysCode.SYSTEM_ERROR,"添加角色信息{}失败",sysRole);
        }
        return true;
    }


    private boolean doUpdate(SysRole sysRole) throws RoleException {
        SysRole dbRole = getAndVerifyById(sysRole.getRoleId());

        dbRole.setRoleName(sysRole.getRoleName());
        dbRole.setRemark(sysRole.getRemark());
        dbRole.setSeq(sysRole.getSeq());
        dbRole.setStatus(sysRole.getStatus());

        if(!updateById(dbRole)){
            throw new RoleException(AdminCode.ROLE_NOT_EXIST,"更新角色信息失败",dbRole);
        }
        return true;
    }



    @Override
    public boolean updateById(SysRole entity) {
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
