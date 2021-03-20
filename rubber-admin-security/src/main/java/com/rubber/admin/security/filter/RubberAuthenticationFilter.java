package com.rubber.admin.security.filter;

import com.rubber.admin.core.authorize.RequestOriginProvider;
import com.rubber.admin.core.authorize.RubberAuthorizeGroupContext;
import com.rubber.admin.core.system.entity.SysUser;
import com.rubber.admin.security.config.properties.RubberPropertiesUtils;
import com.rubber.admin.security.config.properties.RubberSecurityProperties;
import com.rubber.admin.security.login.bean.LoginUserDetail;
import org.springframework.context.ApplicationContext;
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

    private static RubberAuthorizeGroupContext rubberAuthorizeGroupContext;


    private static RubberSecurityProperties rubberSecurityProperties;

    private RubberAuthenticationFilter() {
        ApplicationContext applicationContext = RequestOriginProvider.getApplicationContext();
        rubberAuthorizeGroupContext = applicationContext.getBean(RubberAuthorizeGroupContext.class);
        rubberSecurityProperties = applicationContext.getBean(RubberSecurityProperties.class);
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
        return RubberPropertiesUtils.verifyNotPermessionFilter(request);
    }



    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //获取用户的基本信息
        LoginUserDetail loginUserDetail = LoginUserDetail.getByHttp(httpServletRequest);
        SysUser sysUser = loginUserDetail.getSysUser();
        if(sysUser == null){
            throw new AuthenticationCredentialsNotFoundException("Permission denied");
        }
        if (rubberSecurityProperties.isOpenInterfaceAuth()){
            if(!rubberAuthorizeGroupContext.verifyUserRequestAuthorize(httpServletRequest,sysUser)){
                throw new AuthenticationCredentialsNotFoundException("Permission denied");
            }
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);

    }
}
