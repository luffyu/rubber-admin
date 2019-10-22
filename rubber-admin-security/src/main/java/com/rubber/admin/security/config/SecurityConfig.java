package com.rubber.admin.security.config;

import com.rubber.admin.security.filter.JwtAuthenticationTokenFilter;
import com.rubber.admin.security.handle.AuthenticationEntryPointImpl;
import com.rubber.admin.security.handle.LogoutSuccessHandlerImpl;
import com.rubber.admin.security.login.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.luffyu.piece.utils.CollectionUtils;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.header.Header;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * @author luffyu
 * Created on 2019-10-13
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private AuthenticationEntryPointImpl authenticationEntryPoint;

    @Autowired
    private LogoutSuccessHandlerImpl logoutSuccessHandler;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private RubberConfigProperties rubberConfigProperties;

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // 防止CRSF攻击，禁止使用session
                .csrf().disable().sessionManagement().disable()
                //禁止使用表单提交
                .formLogin().disable()
                // 认证失败处理类
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);

        //支持跨域
        httpSecurity.cors().and().headers().addHeaderWriter(new StaticHeadersWriter(Arrays.asList(
                new Header("Access-control-Allow-Origin","*"),
                new Header("Access-Control-Expose-Headers",rubberConfigProperties.getJwt().getHeaderKey()))));


        Map<HttpMethod, Set<String>> permitAll = rubberConfigProperties.getPermitAll();
        if(CollectionUtils.isNotEmpty(permitAll)){
            for(Map.Entry<HttpMethod, Set<String>> map:permitAll.entrySet()){
                if(CollectionUtils.isEmpty(map.getValue())){
                    continue;
                }
                String[] value = new String[map.getValue().size()];
                httpSecurity.authorizeRequests().antMatchers(map.getKey(),map.getValue().toArray(value)).permitAll();
            }
        }
        //验证配置
        Set<String> anonymous = rubberConfigProperties.getAllAnonymous();
        if(CollectionUtils.isNotEmpty(anonymous)){
            String[] value = new String[anonymous.size()];
            httpSecurity.authorizeRequests().antMatchers(anonymous.toArray(value)).anonymous();
        }
        //角色配置
        Map<String, Set<String>> rolePatterns = rubberConfigProperties.getRolePatterns();
        if(!CollectionUtils.isEmpty(rolePatterns)){
            for(Map.Entry<String,Set<String>> entry:rolePatterns.entrySet()){
                if(CollectionUtils.isEmpty(entry.getValue())){
                    continue;
                }
                String[] value = new String[entry.getValue().size()];
                httpSecurity.authorizeRequests().antMatchers(entry.getValue().toArray(value)).hasRole(entry.getKey());
            }
        }
        // 除上面外的所有请求全部需要鉴权认证
        httpSecurity.authorizeRequests().anyRequest().authenticated().and()
                .headers().frameOptions().disable();

        //执行退出登陆 和退出登陆成功之后 执行的操作
        httpSecurity.logout().logoutUrl(rubberConfigProperties.getLogoutUrl()).logoutSuccessHandler(logoutSuccessHandler);

        // 后面穿入的class 只是为了标示过滤器的顺序、在验证用户和密码信息之前 进行过滤验证
        //具体的顺序参考 https://docs.spring.io/spring-security/site/docs/5.0.0.M1/reference/htmlsingle/#ns-custom-filters
        httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 身份认证接口
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(new BCryptPasswordEncoder());
    }


    /**
     * 解决 无法直接注入 AuthenticationManager
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}
