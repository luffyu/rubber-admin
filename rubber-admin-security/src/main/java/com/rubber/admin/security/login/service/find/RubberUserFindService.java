package com.rubber.admin.security.login.service.find;

import com.rubber.admin.core.enums.AdminCode;
import com.rubber.admin.core.enums.StatusEnums;
import com.rubber.admin.core.exceptions.AdminException;
import com.rubber.admin.core.plugins.cache.CacheAble;
import com.rubber.admin.core.plugins.cache.ICacheProvider;
import com.rubber.admin.core.system.entity.SysUser;
import com.rubber.admin.core.system.model.SysUserModel;
import com.rubber.admin.core.system.service.ISysUserService;
import com.rubber.admin.security.auth.TokenVerifyBean;
import com.rubber.admin.security.auth.exception.TokenVerifyException;
import com.rubber.admin.security.config.properties.RubberSecurityProperties;
import com.rubber.admin.security.login.bean.LoginBean;
import com.rubber.admin.security.login.bean.LoginException;
import com.rubber.admin.security.login.bean.LoginType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author luffyu
 * Created on 2019-10-31
 */
@Slf4j
@Service
public class RubberUserFindService implements IUserFindService {

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private RubberSecurityProperties rubbeSecurityProperties;


    @Autowired(required = false)
    private ExpandUserFindProvider expandUserFindService;

    /**
     * 缓存配置信息
     */
    @Autowired(required = false)
    private ICacheProvider cacheProvider;



    @Override
    public SysUser findByAccount(LoginBean loginBean) {
        SysUser sysUser;
        if(LoginType.ACCOUNT == loginBean.getLoginType()){
            sysUser = doFindByAccount(loginBean);
        }else {
            sysUser = doFindByThird(loginBean);
        }
        if (sysUser == null){
            //抛出用户的信息
            throw new LoginException(AdminCode.USER_NOT_EXIST,"用户{}不存在", loginBean.getAccount());
        }else if (sysUser.getStatus() == null || StatusEnums.DISABLE == sysUser.getStatus()){
            //抛出用户的信息
            throw new LoginException(AdminCode.USER_IS_DISABLE,"用户{}被禁用", loginBean.getAccount());
        }else if(StatusEnums.DELETE == sysUser.getDelFlag()){
            throw new LoginException(AdminCode.USER_IS_DELETE,"用户{}被删除", loginBean.getAccount());
        }
        sysUserService.setUserPermission(sysUser);
        return sysUser;
    }



    @Override
    public SysUser findByAccount(LoginBean loginBean, TokenVerifyBean verifyBean) {
        boolean updateCache = false;
        SysUser sysUser = doReadFormCache(loginBean.getAccount());
        if(sysUser == null){
            sysUser = findByAccount(loginBean);
            updateCache = true;
        }
        if(sysUser == null){
            throw new TokenVerifyException(AdminCode.USER_NOT_EXIST);
        }
        //验证版本是否合法
        if (!sysUser.getVersion().equals(verifyBean.getVersion())){
            throw new TokenVerifyException(AdminCode.LOGIN_VERSION_ILLEGAL,"账户{}在{}在另外一台设备ip{}上登陆",sysUser.getLoginAccount(),sysUser.getLoginTime(),sysUser.getLoginIp());
        }
        if(updateCache){
            doUpdateUserCache(sysUser);
        }
        return findByAccount(loginBean);
    }


    /**
     * 通过本地账户查询到账户信息
     * @param loginBean loginBean的信息
     * @return 返回SysUser
     */
    private SysUser doFindByAccount(LoginBean loginBean){
        return sysUserService.getByLoginAccount(loginBean.getAccount());
    }



    /**
     * 通过本地账户查询到账户信息
     * @param loginBean loginBean的信息
     * @return 返回SysUser
     */
    private SysUser doFindByThird(LoginBean loginBean){
        if(expandUserFindService == null){
            throw new LoginException(AdminCode.LOGIN_TYPE_NOT_SUPPORT);
        }
        SysUserModel sysUserModel = expandUserFindService.findByLogin(loginBean);
        //是否需要注册
        if(sysUserModel == null){
            return null;
        }
        SysUser sysUser = doFindByAccount(loginBean);
        if(sysUser == null){
            sysUser = doRegister(loginBean,sysUserModel);
        }
        return sysUser;
    }

    /**
     * 注册用户的基本信息
     * @param loginBean
     * @param sysUserModel
     * @return
     */
    public SysUser doRegister(LoginBean loginBean,SysUserModel sysUserModel){
        try {
            SysUser sysUser = doCreateByModel(loginBean, sysUserModel);
            sysUserService.saveUser(sysUser);
            return sysUser;
        } catch (AdminException e) {
            e.printStackTrace();
            throw new LoginException(e.getResult());
        }
    }

    private SysUser doCreateByModel(LoginBean loginBean,SysUserModel sysUserModel){
        SysUser sysUser = new SysUser();
        sysUser.setLoginAccount(loginBean.getAccount());
        sysUser.setLoginPwd(loginBean.getPassword());
        sysUser.setLoginTime(loginBean.getLoginTime());

        sysUser.setUserName(sysUserModel.getUserName());
        sysUser.setSex(sysUserModel.getSex());
        sysUser.setAvatar(sysUserModel.getAvatar());
        sysUser.setEmail(sysUserModel.getEmail());
        return sysUser;
    }


    /**
     * 冲缓存中读取 用户信息
     * @param account 账户信息
     * @return 返回用户的全部信息
     */
    private SysUser doReadFormCache(String account){
        if(cacheProvider == null){
            return null;
        }
        CacheAble byKey = cacheProvider.findByKey(account);
        if(byKey != null){
            if(cacheProvider.version() != byKey.getCacheVersion()){
                log.warn("用户{}缓存信息的版本号{}和当前的版本号{}不一致",account,byKey.getCacheVersion(),cacheProvider.version());
                return null;
            }
            return (SysUser)byKey;
        }
        return null;
    }

    /**
     * 返回超时时间
     * @param sysUser 用户的基本信息
     */
    private void doUpdateUserCache(SysUser sysUser){
        if(cacheProvider != null && sysUser != null){
            cacheProvider.update(sysUser,rubbeSecurityProperties.getSessionTime());
        }
    }

}
