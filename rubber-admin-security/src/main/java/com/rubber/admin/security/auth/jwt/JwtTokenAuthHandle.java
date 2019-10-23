package com.rubber.admin.security.auth.jwt;

import com.luffyu.piece.utils.StringTools;
import com.luffyu.piece.utils.jwt.JwtUtil;
import com.rubber.admin.core.enums.AdminCode;
import com.rubber.admin.security.bean.RubberConfigProperties;
import com.rubber.admin.security.handle.PropertiesHandle;
import com.rubber.admin.security.user.bean.LoginUserDetail;
import io.jsonwebtoken.Claims;
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
            Integer id = (Integer) claims.get("id");
            String name = (String) claims.get("name");
            return new LoginUserDetail(id,name,subject,jwtToken);
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
        HashMap<String,Object> map = new HashMap<>(4);
        map.put("id",loginUserDetail.getUserId());
        map.put("name",loginUserDetail.getUsername());
        String jwtDefault = JwtUtil.createJwtDefault(loginUserDetail.getLoginName(), now, jwtConfig.getTimeOut(),map, jwtConfig.getSecretKey());
        loginUserDetail.setToken(jwtDefault);
        return jwtDefault;
    }


}
