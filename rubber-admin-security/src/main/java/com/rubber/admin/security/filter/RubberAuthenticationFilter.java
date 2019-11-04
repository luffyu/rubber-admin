package com.rubber.admin.security.filter;

import com.rubber.admin.core.plugins.security.PermissionAuthorizeProvider;
import com.rubber.admin.core.plugins.security.PermissionUtils;
import com.rubber.admin.core.system.entity.SysUser;
import com.rubber.admin.security.config.properties.RubberPropertiesUtils;
import com.rubber.admin.security.login.bean.LoginUserDetail;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author luffyu
 * Created on 2019-10-24
 */
public class RubberAuthenticationFilter extends OncePerRequestFilter {

    private static RubberAuthenticationFilter filer = new RubberAuthenticationFilter();
    private RubberAuthenticationFilter() {
    }
    /**
     * 返回一个单例的模式
     * @return
     */
    public static RubberAuthenticationFilter builder(){
        return filer;
    }



    /**
     * 可以重写
     * @param request
     * @return 返回为true时，则不过滤即不会执行doFilterInternal
     * @throws ServletException
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return RubberPropertiesUtils.verifyNotFilter(request);
    }



    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //获取用户的基本信息
        LoginUserDetail loginUserDetail = LoginUserDetail.getByHttp(httpServletRequest);
        SysUser sysUser = loginUserDetail.getSysUser();
        if(sysUser == null){
            throw new AuthenticationCredentialsNotFoundException("Permission denied");
        }
        if(sysUser.getSuperUser() == null || sysUser.getSuperUser() != PermissionUtils.SUPER_ADMIN_FLAG){
            String servletPath = httpServletRequest.getServletPath();
            //通过http请求获取用户必须要的权限
            String authorizeKey = PermissionAuthorizeProvider.getMappingAuthorize().get(servletPath);
            //验证是否有权限
            if(!PermissionUtils.havePermission(sysUser.getPermissions(),authorizeKey)){
                throw new AuthenticationCredentialsNotFoundException("Permission denied");
            }
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);

    }
}
