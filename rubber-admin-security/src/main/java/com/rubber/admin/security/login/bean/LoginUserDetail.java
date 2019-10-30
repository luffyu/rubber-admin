package com.rubber.admin.security.login.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rubber.admin.core.enums.AdminCode;
import com.rubber.admin.core.system.entity.SysUser;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Collection;

/**
 * @author luffyu
 * Created on 2019-10-13
 */
@Data
public class LoginUserDetail implements UserDetails {

    /**
     * 用户的id
     */
    private Integer userId;

    /**
     * 用户的名称 不是下面的getUsername的返回值
     */
    private String name;

    /**
     * token
     */
    private String token;


    /**
     * 用户的实体信息
     */
    @JsonIgnore
    private SysUser sysUser;


    public LoginUserDetail(SysUser sysUser) {
        this.userId = sysUser.getUserId();
        this.name = sysUser.getUserName();
        this.sysUser = sysUser;
    }


    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return this.sysUser.getLoginPwd();
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return sysUser.getLoginAccount();
    }

    /**
     * 账户是否未过期,过期无法验证
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 指定用户是否解锁,锁定的用户无法进行身份验证
     *
     * @return
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 指示是否已过期的用户的凭据(密码),过期的凭据防止认证
     *
     * @return
     */
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否可用 ,禁用的用户不能身份验证
     *
     * @return
     */
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }


    /**
     * 通过http信息获取用户的detail信息
     * @param request http请求
     * @return 返回用户的detail值
     */
    public static LoginUserDetail getByHttp(HttpServletRequest request){
        Principal userPrincipal = request.getUserPrincipal();
        if(userPrincipal instanceof Authentication){
            Authentication authentication = (Authentication)userPrincipal;
            Object details = authentication.getPrincipal();
            if(details instanceof LoginUserDetail){
                return (LoginUserDetail)details;
            }
        }
        throw new LoginException(AdminCode.USER_NOT_LOGIN);
    }
}
