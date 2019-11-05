package com.rubber.admin.security.login.service.find;

import com.rubber.admin.core.enums.AdminCode;
import com.rubber.admin.core.enums.StatusEnums;
import com.rubber.admin.core.exceptions.AdminException;
import com.rubber.admin.core.system.entity.SysUser;
import com.rubber.admin.core.system.model.SysUserModel;
import com.rubber.admin.core.system.service.ISysUserService;
import com.rubber.admin.security.login.bean.LoginBean;
import com.rubber.admin.security.login.bean.LoginException;
import com.rubber.admin.security.login.bean.LoginType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author luffyu
 * Created on 2019-10-31
 */
@Service
public class RubberUserFindService implements IUserFindService {

    @Resource
    private ISysUserService sysUserService;


    @Autowired(required = false)
    private ExpandUserFindProvider expandUserFindService;




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
            sysUserService.addUser(sysUser);
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

}
