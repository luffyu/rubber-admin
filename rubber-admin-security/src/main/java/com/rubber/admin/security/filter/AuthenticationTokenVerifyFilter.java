package com.rubber.admin.security.filter;

import cn.hutool.coocaa.util.result.IResultHandle;
import cn.hutool.coocaa.util.result.ResultMsg;
import cn.hutool.extra.servlet.ServletUtil;
import com.alibaba.fastjson.JSON;
import com.rubber.admin.core.enums.AdminCode;
import com.rubber.admin.core.exceptions.AdminException;
import com.rubber.admin.security.auth.ITokenVerifyService;
import com.rubber.admin.security.config.properties.RubberPropertiesUtils;
import com.rubber.admin.security.login.bean.LoginUserDetail;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author luffyu
 * Created on 2019-10-22
 */
public class AuthenticationTokenVerifyFilter extends OncePerRequestFilter {

    private static AuthenticationTokenVerifyFilter filer = new AuthenticationTokenVerifyFilter();

    private AuthenticationTokenVerifyFilter() {
    }
    /**
     * 返回一个单例的模式
     * @return
     */
    public static AuthenticationTokenVerifyFilter builder(){
        return filer;
    }

    /**
     * 解析token的service方法
     * 注入的哪一个就用哪一个
     */
    private ITokenVerifyService tokenAuth;

    public ITokenVerifyService getTokenAuth() {
        if(tokenAuth == null){
            tokenAuth = RubberPropertiesUtils.getApplicationContext().getBean(ITokenVerifyService.class);
        }
        if(tokenAuth == null){
            throw new RuntimeException("tokenAuth is null");
        }
        return tokenAuth;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain){
        try {
            LoginUserDetail loginUserDetail = getTokenAuth().verify(request);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUserDetail, null, loginUserDetail.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request,response);
        }catch (Exception e){
            unSuccessJwtResult(response,e);
        }
    }
    /**
     * 验证没有登陆成功的请求
     * @param response 返回值
     * @param e 异常的信息
     */
    public void unSuccessJwtResult(HttpServletResponse response,Exception e){
        ResultMsg error = null ;
        if(e instanceof AdminException){
            IResultHandle result = ((AdminException) e).getResult();
            if(result instanceof ResultMsg){
                error = (ResultMsg) result;
            }
        }
        if(error == null){
            error = ResultMsg.error(AdminCode.TOKEN_IS_ERROR);
        }
        ServletUtil.writeJSON(response, JSON.toJSONString(error));
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

}
