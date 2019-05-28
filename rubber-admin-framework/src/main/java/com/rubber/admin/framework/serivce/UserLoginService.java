package com.rubber.admin.framework.serivce;

import com.rubber.admin.core.entity.SysUser;
import com.rubber.admin.core.enums.MsgCode;
import com.rubber.admin.core.enums.StatusEnums;
import com.rubber.admin.core.exceptions.LoginException;
import com.rubber.admin.core.exceptions.base.BaseException;
import com.rubber.admin.core.service.ISysUserService;
import com.rubber.admin.framework.shiro.PasswordHelper;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


/**
 * @author luffyu
 * Created on 2019-05-13
 */
@Service
public class UserLoginService {

    @Autowired
    private ISysUserService sysUserService;


    /**
     * 用户的登陆逻辑信息
     * @param loginName 登陆名称
     * @param password 登陆密码
     * @return 登陆成功之后 返回登陆信息 否则抛出异常信息
     */
    public SysUser getLoginUser(String loginName,String password){
        if (StringUtils.isEmpty(loginName) || StringUtils.isEmpty(password)){
            throw new LoginException(MsgCode.PARAM_ERROR,"名称和密码不能为空");
        }
        SysUser sysUser = sysUserService.getByLoginName(loginName);
        //账号不存在
        if(sysUser == null){
            throw new UnknownAccountException();
        }
        //已经被停用
        if (StatusEnums.DISABLE.key == sysUser.getStatus()){
            throw new DisabledAccountException();
        }
        return sysUser;
    }


    /**
     * 更改用户的密码
     * @param sysUser 用户的信息
     * @param oldPassword 老密码
     * @param newPassword 新密码
     * @return
     */
    public SysUser changeUserPassword(SysUser sysUser,String oldPassword,String newPassword){
        String createUserPassword = PasswordHelper.encryptPassword(oldPassword,sysUser.getSalt());
        if(!sysUser.getPassword().equalsIgnoreCase(createUserPassword)){
            throw new BaseException(MsgCode.LOGIN_AUTH_ERROR);
        }
        sysUser.setSalt("123456");
        String password = PasswordHelper.encryptPassword(newPassword, sysUser.getSalt());
        sysUser.setPassword(password);
        sysUserService.checkAndUpdate(sysUser);
        return sysUser;
    }

}
