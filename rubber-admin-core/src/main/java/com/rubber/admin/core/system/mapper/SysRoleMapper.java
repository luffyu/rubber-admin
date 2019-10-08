package com.rubber.admin.core.system.mapper;

import com.rubber.admin.core.system.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 角色信息表 Mapper 接口
 * </p>
 *
 * @author luffyu-auto
 * @since 2019-05-13
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {


    /**
     * 通过用户id 查找所有的用户信息
     * @param userId 用户id
     * @return 返回用户的角色信息
     */
    List<SysRole> findByUserId(Integer userId);
}
