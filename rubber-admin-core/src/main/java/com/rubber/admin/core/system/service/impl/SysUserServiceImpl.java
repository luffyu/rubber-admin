package com.rubber.admin.core.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.luffyu.piece.utils.result.code.SysCode;
import com.rubber.admin.core.system.entity.SysUser;
import com.rubber.admin.core.exceptions.AdminException;
import com.rubber.admin.core.system.mapper.SysUserMapper;
import com.rubber.admin.core.system.service.ISysUserService;
import com.rubber.admin.core.base.BaseService;
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
        queryWrapper.eq("status",0);

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

    @Override
    public void register(SysUser sysUser) {
        save(sysUser);
    }

    /**
     * 检测用户的基本信息
     * @param sysUser
     */
    private void checkSysUser(SysUser sysUser){

    }
}
