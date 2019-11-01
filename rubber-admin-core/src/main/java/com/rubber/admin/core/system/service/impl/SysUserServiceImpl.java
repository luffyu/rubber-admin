package com.rubber.admin.core.system.service.impl;

import cn.hutool.coocaa.util.result.code.SysCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rubber.admin.core.base.BaseAdminService;
import com.rubber.admin.core.exceptions.AdminRunTimeException;
import com.rubber.admin.core.plugins.security.PrivilegeUtils;
import com.rubber.admin.core.system.entity.SysUser;
import com.rubber.admin.core.system.mapper.SysUserMapper;
import com.rubber.admin.core.system.service.ISysUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author luffyu-auto
 * @since 2019-05-13
 */
@Service
public class SysUserServiceImpl extends BaseAdminService<SysUserMapper, SysUser> implements ISysUserService {

    @Override
    public SysUser getByLoginAccount(String loginName) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("login_account",loginName);
        SysUser sysUser = getOne(queryWrapper);
        if(sysUser != null){
            Set<String> set = new HashSet<>();
            set.add(PrivilegeUtils.SUPER_ADMIN_PERMISSION);
            sysUser.setPermissions(set);
        }
        return sysUser;
    }


    @Override
    public SysUser getByUserName(String userName) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",userName);
        return getOne(queryWrapper);
    }


    @Transactional(
            rollbackFor = Throwable.class
    )
    @Override
    public void checkAndUpdate(SysUser sysUser) {
        checkSysUser(sysUser);
        if(!updateById(sysUser)){
            throw new AdminRunTimeException(SysCode.SYSTEM_ERROR,"更新系统失败");
        }
    }

    /**
     * 检测用户的基本信息
     * @param sysUser
     */
    private void checkSysUser(SysUser sysUser){

    }
}
