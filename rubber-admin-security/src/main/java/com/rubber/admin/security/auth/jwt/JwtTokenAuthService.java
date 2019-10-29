package com.rubber.admin.security.auth.jwt;

import cn.hutool.coocaa.util.jwt.JwtUtil;
import cn.hutool.core.util.StrUtil;
import com.rubber.admin.core.enums.AdminCode;
import com.rubber.admin.core.plugins.cache.IUserSecurityCache;
import com.rubber.admin.core.plugins.cache.LocalUserSecurityCache;
import com.rubber.admin.security.auth.BaseTokenAuthService;
import com.rubber.admin.security.auth.exception.TokenCreateException;
import com.rubber.admin.security.auth.exception.TokenVerifyException;
import com.rubber.admin.security.config.properties.RubbeSecurityProperties;
import com.rubber.admin.security.handle.PropertiesHandle;
import com.rubber.admin.security.user.bean.LoginUserDetail;
import io.jsonwebtoken.Claims;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @author luffyu
 * Created on 2019-10-22
 */
public class JwtTokenAuthService extends BaseTokenAuthService {

    /**
     * 默认采用本地的缓存方法
     */
    public JwtTokenAuthService() {
        super(new LocalUserSecurityCache());
    }

    public JwtTokenAuthService(IUserSecurityCache userSecurityCache) {
        super(userSecurityCache);
    }

    @Override
    public String doVerify(HttpServletRequest request) throws TokenVerifyException {
        RubbeSecurityProperties.JwtProperties jwtConfig = PropertiesHandle.config.getJwt();
        String jwtToken = request.getHeader(jwtConfig.getHeaderKey());
        if(StrUtil.isEmpty(jwtToken)){
            throw new TokenVerifyException(AdminCode.USER_NOT_LOGIN);
        }
        try {
            Claims claims = JwtUtil.checkToken(jwtToken, jwtConfig.getSecretKey());
            //登陆名称
            return claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new TokenVerifyException(AdminCode.USER_NOT_LOGIN);
        }
    }


    @Override
    public String doCreate(LoginUserDetail loginUserDetail) throws TokenCreateException {
        long now = System.currentTimeMillis();
        RubbeSecurityProperties.JwtProperties jwtConfig = PropertiesHandle.config.getJwt();
        HashMap<String,Object> map = new HashMap<>(4);
        map.put("id",loginUserDetail.getUserId());
        map.put("name",loginUserDetail.getUsername());
        String jwtDefault = JwtUtil.createJwtDefault(loginUserDetail.getLoginName(), now, jwtConfig.getTimeOut(),map, jwtConfig.getSecretKey());
        loginUserDetail.setToken(jwtDefault);
        return jwtDefault;
    }


}
