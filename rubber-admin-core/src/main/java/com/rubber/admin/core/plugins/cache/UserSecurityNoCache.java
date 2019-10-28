package com.rubber.admin.core.plugins.cache;

import com.rubber.admin.core.system.entity.SysUser;

/**
 * @author luffyu
 * Created on 2019-10-28
 * 不写入缓存信息
 */
public class UserSecurityNoCache implements IUserSecurityCache {
    @Override
    public boolean write(SysUser sysUser, long time) {
        return true;
    }

    @Override
    public boolean update(SysUser sysUser, long time) {
        return true;
    }

    @Override
    public boolean delete(String key) {
        return true;
    }

    @Override
    public SysUser findByKey(String key) {
        return null;
    }
}
