package com.rubber.admin.core.service;

import com.rubber.admin.core.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author luffyu-auto
 * @since 2019-05-13
 */
public interface ISysUserService extends IService<SysUser> {


    SysUser getByLoginName(String loginName);

}
