package com.rubber.admin.core.system.service.impl;

import cn.hutool.coocaa.util.result.code.SysCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rubber.admin.core.base.BaseService;
import com.rubber.admin.core.exceptions.AdminException;
import com.rubber.admin.core.system.entity.SysUser;
import com.rubber.admin.core.system.mapper.SysUserMapper;
import com.rubber.admin.core.system.service.ISysUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author luffyu-auto
 * @since 2019-05-13
 */
@Service
public class SysUserServiceImpl extends BaseService<SysUserMapper, SysUser> implements ISysUserService {

    @Override
    public SysUser getByLoginName(String loginName) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("login_name",loginName);
        return getOne(queryWrapper);
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
            throw new AdminException(SysCode.SYSTEM_ERROR,"更新系统失败");
        }
    }

    /**
     * 检测用户的基本信息
     * @param sysUser
     */
    private void checkSysUser(SysUser sysUser){

    }
}
