package com.rubber.admin.core.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rubber.admin.core.base.BaseAdminService;
import com.rubber.admin.core.enums.AdminCode;
import com.rubber.admin.core.enums.StatusEnums;
import com.rubber.admin.core.system.entity.SysUser;
import com.rubber.admin.core.system.exception.UserException;
import com.rubber.admin.core.system.mapper.SysUserMapper;
import com.rubber.admin.core.system.service.ISysUserService;
import org.springframework.stereotype.Service;

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
        return getOne(queryWrapper);
    }


    @Override
    public SysUser getByUserName(String userName) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",userName);
        return getOne(queryWrapper);
    }



    @Override
    public SysUser getAndVerifyById(Integer userId) throws UserException {
        if(userId == null || userId <= 0){
            throw new UserException(AdminCode.PARAM_ERROR,"查询的用户id为空");
        }
        SysUser sysUser = getById(userId);
        if(sysUser == null){
            throw new UserException(AdminCode.USER_NOT_EXIST);
        }
        if(StatusEnums.DELETE == sysUser.getDelFlag()){
            throw new UserException(AdminCode.USER_IS_DELETE);
        }
        if(StatusEnums.DISABLE == sysUser.getStatus()){
            throw new UserException(AdminCode.USER_IS_DISABLE);
        }
        return sysUser;
    }


}
