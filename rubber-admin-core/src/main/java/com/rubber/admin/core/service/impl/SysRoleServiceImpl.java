package com.rubber.admin.core.service.impl;

import com.rubber.admin.core.entity.SysRole;
import com.rubber.admin.core.mapper.SysRoleMapper;
import com.rubber.admin.core.service.ISysRoleService;
import com.rubber.admin.core.base.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author luffyu-auto
 * @since 2019-05-13
 */
@Service
public class SysRoleServiceImpl extends BaseService<SysRoleMapper, SysRole> implements ISysRoleService {


    /**
     * 通过用户的id查询角色信息
     * @param userId 用户id
     * @return 返回用户的角色list信息
     */
    @Override
    public List<SysRole> findByUserId(Integer userId) {
        return getBaseMapper().findByUserId(userId);
    }


    /**
     * 获取用户的角色信息
     * @param userId 用户的id
     * @return 返回用户的角色信息
     */
    @Override
    public Set<String> findRoleNameByUserId(Integer userId){
        List<SysRole> userRoleEntityList = findByUserId(userId);
        if(!CollectionUtils.isEmpty(userRoleEntityList)){
            Set<String> roleName = new HashSet<>(userRoleEntityList.size());
            userRoleEntityList.forEach(roleEntity->{
                roleName.add(roleEntity.getRoleKey());
            });
            return roleName;
        }
        return new HashSet<>(1);
    }
}
