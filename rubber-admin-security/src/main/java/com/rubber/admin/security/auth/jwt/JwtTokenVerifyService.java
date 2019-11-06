package com.rubber.admin.security.auth.jwt;

import cn.hutool.coocaa.util.jwt.JwtUtil;
import cn.hutool.core.util.StrUtil;
import com.rubber.admin.core.enums.AdminCode;
import com.rubber.admin.security.auth.BaseTokenVerifyService;
import com.rubber.admin.security.auth.TokenVerifyBean;
import com.rubber.admin.security.auth.exception.TokenCreateException;
import com.rubber.admin.security.auth.exception.TokenVerifyException;
import com.rubber.admin.security.config.properties.RubbeSecurityProperties;
import com.rubber.admin.security.handle.PropertiesHandle;
import com.rubber.admin.security.login.bean.LoginUserDetail;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @author luffyu
 * Created on 2019-10-22
 */
public class JwtTokenVerifyService extends BaseTokenVerifyService {


    @Override
    public TokenVerifyBean doVerify(HttpServletRequest request) throws TokenVerifyException {
        RubbeSecurityProperties.JwtProperties jwtConfig = PropertiesHandle.config.getJwt();
        String jwtToken = request.getHeader(jwtConfig.getHeaderKey());
        if(StrUtil.isEmpty(jwtToken)){
            throw new TokenVerifyException(AdminCode.USER_NOT_LOGIN);
        }
        try {
            Claims claims = JwtUtil.checkToken(jwtToken, jwtConfig.getSecretKey());
            //登陆名称
            String subject = claims.getSubject();
            Integer version = Integer.valueOf(claims.get("version").toString());
            return new TokenVerifyBean(version,subject);
        }catch (ExpiredJwtException e){
            throw new TokenVerifyException(AdminCode.TOKEN_IS_EXPIRED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new TokenVerifyException(AdminCode.TOKEN_IS_ERROR);
        }
    }


    @Override
    public String doCreate(LoginUserDetail loginUserDetail) throws TokenCreateException {
        long now = System.currentTimeMillis();
        RubbeSecurityProperties.JwtProperties jwtConfig = PropertiesHandle.config.getJwt();
        HashMap<String,Object> map = new HashMap<>(4);
        map.put("version",loginUserDetail.getSysUser().getVersion());
        String jwtDefault = JwtUtil.createJwtDefault(loginUserDetail.getSysUser().getLoginAccount(), now, jwtConfig.getTimeOut(),map, jwtConfig.getSecretKey());
        loginUserDetail.setToken(jwtDefault);
        return jwtDefault;
    }


}
