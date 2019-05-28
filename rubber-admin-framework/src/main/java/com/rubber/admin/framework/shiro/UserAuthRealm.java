package com.rubber.admin.framework.shiro;

import com.rubber.admin.core.entity.SysUser;
import com.rubber.admin.core.service.ISysMenuService;
import com.rubber.admin.core.service.ISysRoleService;
import com.rubber.admin.framework.serivce.UserLoginService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Set;

/**
 * @author luffyu
 * Created on 2019-05-13
 */

@Component(value = "authRealm")
public class UserAuthRealm extends AuthorizingRealm {

    private Logger logger = LoggerFactory.getLogger(UserAuthRealm.class);

    @Resource
    private UserLoginService userLoginService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysMenuService sysMenuService;


    /**
     * 设定密码校验的Hash算法与迭代次数
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(PasswordHelper.ALGORITHM_NAME);
        matcher.setHashIterations(PasswordHelper.HASH_ITERATIONS);
        setCredentialsMatcher(matcher);
    }


    /**
     * 授权 查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.info(">>>>>登陆授权");
        //如果数据库中有该用户名对应的记录，就进行授权操作
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获取用户的信息
        SysUser user = (SysUser) principals.getPrimaryPrincipal();
        if(user.getSuperAdmin() == null || user.getSuperAdmin()){
            info.addRole("admin");
            info.addStringPermission("*:*:*");
        }else {
            Set<String> roleNames = roleService.findRoleNameByUserId(user.getUserId());
            Set<String> authKeys = sysMenuService.findAuthKey(user.getUserId());
            info.addRoles(roleNames);
            info.setStringPermissions(authKeys);
        }
        return info;
    }


    /**
     * 登陆认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken){
        try {
            System.out.println(">>>到达验证到token");
            //token携带了用户信息
            UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
            //登陆用户的基本信息
            String loginName = usernamePasswordToken.getUsername();
            String password =  new String(usernamePasswordToken.getPassword());
            SysUser user = userLoginService.getLoginUser(loginName, password);
            //获取盐值
            ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt());
            return new SimpleAuthenticationInfo(user, user.getPassword(), credentialsSalt, getName());
        }catch (Exception e){
            throw e;
        }finally {
        }
    }

    /**
     * 登陆方法的密码验证
     * @param token token信息
     * @param info info信息
     * @throws AuthenticationException
     */
    /*@Override
    protected void assertCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String loginName = usernamePasswordToken.getUsername();
        String password =  new String(usernamePasswordToken.getPassword());

        System.out.println(">>>>>"+loginName + ">>>>>>" + password);


        SysUser sysUser = (SysUser) info.getPrincipals().getPrimaryPrincipal();
        System.out.println(sysUser.getLoginName() + ">>>" +  sysUser.getPassword() + ">>>" + sysUser.getSalt());

//        CredentialsMatcher cm = this.getCredentialsMatcher();
//        if (cm != null) {
//            if (!cm.doCredentialsMatch(token, info)) {
//                String msg = "Submitted credentials for token [" + token + "] did not match the expected credentials.";
//                throw new IncorrectCredentialsException(msg);
//            }
//        } else {
//            throw new AuthenticationException("A CredentialsMatcher must be configured in order to verify credentials during authentication.  If you do not wish for credentials to be examined, you can configure an " + AllowAllCredentialsMatcher.class.getName() + " instance.");
//        }
    }
*/





}
