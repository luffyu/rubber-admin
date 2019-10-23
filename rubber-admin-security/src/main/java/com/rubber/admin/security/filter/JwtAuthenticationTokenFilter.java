package com.rubber.admin.security.filter;

import cn.hutool.coocaa.util.result.IResultHandle;
import cn.hutool.coocaa.util.result.ResultMsg;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.alibaba.fastjson.JSON;
import com.rubber.admin.core.enums.AdminCode;
import com.rubber.admin.core.exceptions.AdminException;
import com.rubber.admin.security.auth.jwt.JwtTokenAuthHandle;
import com.rubber.admin.security.bean.RubberConfigProperties;
import com.rubber.admin.security.bean.SpringContextBeans;
import com.rubber.admin.security.user.bean.LoginUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author luffyu
 * Created on 2019-10-22
 */

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {


    @Autowired
    private JwtTokenAuthHandle jwtTokenAuthHandle;


    @Autowired
    private RubberConfigProperties rubberConfigProperties;


    private OrRequestMatcher orRequestMatcher;

    /**
     * 初始化操作不需要验证jwt的值
     */
    @PostConstruct
    public void init() {
        Set<String> anonymous = rubberConfigProperties.getAllAnonymous();
        Map<HttpMethod, Set<String>> permitAll = rubberConfigProperties.getPermitAll();
        if(CollectionUtil.isNotEmpty(permitAll)){
            Collection<Set<String>> values = permitAll.values();
            values.forEach(anonymous::addAll);
        }
        List<RequestMatcher> matchers =anonymous.stream().map(AntPathRequestMatcher::new).collect(Collectors.toList());
        orRequestMatcher = new OrRequestMatcher(matchers);
    }



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain){
        RequestMappingHandlerMapping bean = SpringContextBeans.getApplicationContext().getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = bean.getHandlerMethods();
        for (RequestMappingInfo rmi : handlerMethods.keySet()) {
            PatternsRequestCondition pc = rmi.getPatternsCondition();
            Set<String> pSet = pc.getPatterns();
            System.out.println(pSet);
        }


        try {

            LoginUserDetail loginUserDetail = jwtTokenAuthHandle.checkToken(request);
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
            error = ResultMsg.error(AdminCode.USER_NOT_LOGIN);
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
        return orRequestMatcher.matches(request);
    }

}
