package com.rubber.admin.security.auth;

import com.rubber.admin.core.system.entity.SysUser;
import com.rubber.admin.security.auth.exception.TokenCreateException;
import com.rubber.admin.security.auth.exception.TokenVerifyException;
import com.rubber.admin.security.config.properties.RubberPropertiesUtils;
import com.rubber.admin.security.login.bean.LoginBean;
import com.rubber.admin.security.login.bean.LoginUserDetail;
import com.rubber.admin.security.login.service.find.IUserFindService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author luffyu
 * Created on 2019-10-28
 */
public abstract class BaseTokenVerifyService implements ITokenVerifyService {

    private IUserFindService userFindService;



    public IUserFindService getFindUserService(){
        if(userFindService == null){
            userFindService = RubberPropertiesUtils.getApplicationContext().getBean(IUserFindService.class);
        }
        return userFindService;
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
        //返回账户信息
        SysUser sysUser = getFindUserService().findByAccount(new LoginBean(verifyBean.getSubject()),verifyBean);

        return createUserDetail(sysUser);
    }


    /**
     * 每次重新创建的时候都会写入一次缓存信息
     * @param loginUserDetail 用户的基本信息
     * @return
     */
    @Override
    public String create(LoginUserDetail loginUserDetail) throws TokenCreateException{
        //添加版本号
        loginUserDetail.getSysUser().addVersion();
        //创建token
        String s = doCreate(loginUserDetail);
        //写入token
        loginUserDetail.setToken(s);
        return s;
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
