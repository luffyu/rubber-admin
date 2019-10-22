package com.rubber.admin.security.login;

import com.rubber.admin.core.system.entity.SysUser;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author luffyu
 * Created on 2019-10-13
 */
@Data
public class LoginUserDetail implements UserDetails {

    /**
     * 登陆的名称
     */
    private String loginName;

    private String password;

    /**
     * token
     */
    private String token;

    /**
     * 用户的基本信息
     */
    private SysUser sysUser;

    public LoginUserDetail(String loginName) {
        this.loginName = loginName;
    }

    public LoginUserDetail(SysUser sysUser) {
        this.sysUser = sysUser;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return loginName;
    }

    /**
     * 账户是否未过期,过期无法验证
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 指定用户是否解锁,锁定的用户无法进行身份验证
     *
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 指示是否已过期的用户的凭据(密码),过期的凭据防止认证
     *
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否可用 ,禁用的用户不能身份验证
     *
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
