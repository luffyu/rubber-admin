package com.rubber.admin.security.auth;

import com.rubber.admin.security.auth.exception.TokenCreateException;
import com.rubber.admin.security.auth.exception.TokenVerifyException;
import com.rubber.admin.security.login.bean.LoginUserDetail;

import javax.servlet.http.HttpServletRequest;

/**
 * @author luffyu
 * Created on 2019-10-28
 */
public interface ITokenVerifyService {


    /**
     * 认证token
     * @param request 请求值
     * @return 返回解析成功的token
     * @throws TokenVerifyException 验证的一次信息
     */
    LoginUserDetail verify(HttpServletRequest request) throws TokenVerifyException;

    /**
     * 生成token
     * @param loginUserDetail 用户的基本信息
     * @return 返回用户的jwt信息
     * @throws TokenCreateException 创建异常的信息
     */
   String create(LoginUserDetail loginUserDetail) throws TokenCreateException;
}
