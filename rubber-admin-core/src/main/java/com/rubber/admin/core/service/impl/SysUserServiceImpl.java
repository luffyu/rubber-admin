package com.rubber.admin.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rubber.admin.core.entity.SysUser;
import com.rubber.admin.core.mapper.SysUserMapper;
import com.rubber.admin.core.service.ISysUserService;
import com.rubber.admin.core.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
