package com.rubber.admin.security.auth.jwt;

import com.luffyu.piece.utils.StringTools;
import com.luffyu.piece.utils.jwt.JwtUtil;
import com.rubber.admin.core.enums.AdminCode;
import com.rubber.admin.core.system.entity.SysUser;
import com.rubber.admin.security.config.RubberConfigProperties;
import com.rubber.admin.security.handle.PropertiesHandle;
import com.rubber.admin.security.login.LoginUserDetail;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @author luffyu
 * Created on 2019-10-22
 */
@Component
public class JwtTokenAuthHandle {

    /**
     * 验证用户的token
     * @param request
     */
    public LoginUserDetail checkToken(HttpServletRequest request){
        RubberConfigProperties.JwtProperties jwtConfig = PropertiesHandle.config.getJwt();
        String jwtToken = request.getHeader(jwtConfig.getHeaderKey());
        if(StringTools.isEmpty(jwtToken)){
            throw new JwtException(AdminCode.USER_NOT_LOGIN);
        }
        try {
            Claims claims = JwtUtil.checkToken(jwtToken, jwtConfig.getSecretKey());
            //登陆名称
            String subject = claims.getSubject();
            return new LoginUserDetail(subject);
        } catch (Exception e) {
            e.printStackTrace();
            throw new JwtException(AdminCode.USER_NOT_LOGIN);
        }
    }


    /**
     * 生成jwt的token
     * @param loginUserDetail 用户的基本信息
     * @return 返回用户的jwt信息
     */
    public String creatJwtToken(LoginUserDetail loginUserDetail){
        long now = System.currentTimeMillis();
        RubberConfigProperties.JwtProperties jwtConfig = PropertiesHandle.config.getJwt();
        String jwtDefault = JwtUtil.createJwtDefault(loginUserDetail.getLoginName(), now, jwtConfig.getTimeOut(), new HashMap<>(2), jwtConfig.getSecretKey());
        loginUserDetail.setToken(jwtDefault);
        return jwtDefault;
    }


}
