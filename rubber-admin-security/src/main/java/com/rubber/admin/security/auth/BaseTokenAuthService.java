package com.rubber.admin.security.auth;

import com.rubber.admin.core.plugins.cache.IUserSecurityCache;
import com.rubber.admin.core.system.entity.SysUser;
import com.rubber.admin.core.system.service.impl.SysUserServiceImpl;
import com.rubber.admin.security.auth.exception.TokenCreateException;
import com.rubber.admin.security.auth.exception.TokenVerifyException;
import com.rubber.admin.security.config.properties.RubberPropertiesUtils;
import com.rubber.admin.security.user.bean.LoginUserDetail;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author luffyu
 * Created on 2019-10-28
 */
public abstract class BaseTokenAuthService implements ITokenAuthService {

    @Resource
    private SysUserServiceImpl sysUserService;

    @Resource
    private IUserSecurityCache userSecurityCache;


    /**
     * 返回具体的缓存设置
     * @return 返回具体的缓存类型
     */
    public IUserSecurityCache getSecurityCache(){
        return userSecurityCache;
    }

    /**
     * 具体的验证key
     * @param request 请求参数
     * @return 返回具体的验证key
     */
    public abstract String doVerify(HttpServletRequest request) throws TokenVerifyException ;


    /**
     * 具体的创建session信息
     * @param loginUserDetail
     * @return
     */
    public abstract String doCreate(LoginUserDetail loginUserDetail) throws TokenCreateException;


    @Override
    public LoginUserDetail verify(HttpServletRequest request) throws TokenVerifyException {
        //获取验证的信息
        String subject = doVerify(request);
        SysUser sysUser = getSecurityCache().findByKey(subject);
        if(sysUser == null){
            sysUser = sysUserService.getByLoginName(subject);
            //token写入到缓存中
            getSecurityCache().update(sysUser,getTokenTimeOut());
        }
        return createUserDetail(sysUser);
    }


    /**
     * 每次重新创建的时候都会写入一次缓存信息
     * @param loginUserDetail 用户的基本信息
     * @return
     */
    @Override
    public String create(LoginUserDetail loginUserDetail) throws TokenCreateException{
        //创建token
        String s = doCreate(loginUserDetail);
        //token写入到缓存中
        getSecurityCache().update(loginUserDetail.getSysUser(),getTokenTimeOut());
        //写入token
        loginUserDetail.setToken(s);
        return s;
    }


    /**
     * 返回session的设置时间
     * @return 返回异常的信息
     */
    protected long getTokenTimeOut(){
        return  RubberPropertiesUtils.getSecurityProperties().getSessionTime();
    }

    /**
     * 创建userDetail信息
     * @param sysUser 用户的基本信息
     * @return
     */
    protected LoginUserDetail createUserDetail(SysUser sysUser){
        return new LoginUserDetail(sysUser);
    }

}
