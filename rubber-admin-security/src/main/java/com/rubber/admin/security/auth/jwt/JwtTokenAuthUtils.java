package com.rubber.admin.security.auth.jwt;

import cn.hutool.coocaa.util.jwt.JwtUtil;
import cn.hutool.core.util.StrUtil;
import com.rubber.admin.core.enums.AdminCode;
import com.rubber.admin.core.plugins.security.PermissionUtils;
import com.rubber.admin.security.bean.RubbeSecurityProperties;
import com.rubber.admin.security.handle.PropertiesHandle;
import com.rubber.admin.security.user.bean.LoginUserDetail;
import io.jsonwebtoken.Claims;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author luffyu
 * Created on 2019-10-22
 */
public class JwtTokenAuthUtils {


    /**
     * 验证用户的token
     * @param request
     */
    public static LoginUserDetail checkToken(HttpServletRequest request){
        RubbeSecurityProperties.JwtProperties jwtConfig = PropertiesHandle.config.getJwt();
        String jwtToken = request.getHeader(jwtConfig.getHeaderKey());
        if(StrUtil.isEmpty(jwtToken)){
            throw new JwtException(AdminCode.USER_NOT_LOGIN);
        }
        try {
            Claims claims = JwtUtil.checkToken(jwtToken, jwtConfig.getSecretKey());
            //登陆名称
            String subject = claims.getSubject();
            Integer id = (Integer) claims.get("id");
            String name = (String) claims.get("name");
            LoginUserDetail loginUserDetail = new LoginUserDetail(id, name, subject, jwtToken);
            HashSet<String> hashSet = new HashSet<>();
            hashSet.add(PermissionUtils.SUPER_ADMIN_PERMISSION);
            loginUserDetail.setPermissions(hashSet);
            return loginUserDetail;
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
    public static String creatJwtToken(LoginUserDetail loginUserDetail){
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
