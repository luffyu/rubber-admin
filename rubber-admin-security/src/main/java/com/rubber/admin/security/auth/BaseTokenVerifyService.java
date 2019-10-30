package com.rubber.admin.security.auth;

import com.rubber.admin.core.enums.AdminCode;
import com.rubber.admin.core.plugins.cache.IUserSecurityCache;
import com.rubber.admin.core.plugins.cache.UserSecurityNoCache;
import com.rubber.admin.core.system.entity.SysUser;
import com.rubber.admin.core.system.service.ISysUserService;
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
public abstract class BaseTokenVerifyService implements ITokenVerifyService {

    @Resource
    private ISysUserService sysUserService;

    /**
     * 缓存配置信息
     */
    private IUserSecurityCache userSecurityCache;



    public BaseTokenVerifyService(IUserSecurityCache userSecurityCache) {
        this.userSecurityCache = userSecurityCache;
    }


    public ISysUserService getUserService(){
        if(sysUserService == null){
            sysUserService = RubberPropertiesUtils.getApplicationContext().getBean(ISysUserService.class);
        }
        return sysUserService;
    }


    /**
     * 具体的验证key
     * @param request 请求参数
     * @return 返回具体的验证key
     * @throws TokenVerifyException 验证异常
     */
    public abstract TokenVerifyBean doVerify(HttpServletRequest request) throws TokenVerifyException ;


    /**
     * 具体的创建session信息
     * @param loginUserDetail 用户的detail信息
     * @return 返回唯一的token值
     * @throws  TokenCreateException 创建的异常信息
     */
    public abstract String doCreate(LoginUserDetail loginUserDetail) throws TokenCreateException;


    /**
     * 验证参数信息
     * @param request 请求值
     * @return
     * @throws TokenVerifyException
     */
    @Override
    public LoginUserDetail verify(HttpServletRequest request) throws TokenVerifyException {
        //获取验证的信息
        TokenVerifyBean verifyBean = doVerify(request);
        SysUser sysUser = doFindByCache(verifyBean.getSubject());
        if(sysUser == null){
            sysUser = getUserService().getByLoginAccount(verifyBean.getSubject());
            if(sysUser == null){
                throw new TokenVerifyException(AdminCode.LOGIN_USER_NOT_EXIST);
            }
            //验证版本是否合法
            if (!sysUser.getVersion().equals(verifyBean.getVersion())){
                throw new TokenVerifyException(AdminCode.TOKEN_IS_EXPIRED);
            }
            //token写入到缓存中
            doUpdateByCache(sysUser);
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
        doUpdateByCache(loginUserDetail.getSysUser());
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


    /**
     * 通过缓存更新参数
     * @param subject
     */
    private void doUpdateByCache(SysUser subject){
        if(checkCacheAble()){
            if(subject != null){
                userSecurityCache.update(subject,getTokenTimeOut());
            }

        }
    }

    /**
     * 通过缓存查询缓存
     * @param subject 查询缓存的基本信息
     * @return 返回具体的对象信息
     */
    private SysUser doFindByCache(String subject){
        if(!checkCacheAble()){
            return null;
        }
        return userSecurityCache.findByKey(subject);
    }

    /**
     * 检测是否配置了缓存
     * 如果没有配置则不在使用
     * @return 返回true表示可以使用缓存 false表示不用使用缓存
     */
    private boolean checkCacheAble(){
        if(userSecurityCache == null){
            return false;
        }
        if(userSecurityCache instanceof UserSecurityNoCache){
            return false;
        }
        return true;
    }

}
