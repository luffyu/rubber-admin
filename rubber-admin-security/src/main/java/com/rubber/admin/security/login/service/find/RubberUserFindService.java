package com.rubber.admin.security.login.service.find;

import cn.hutool.core.util.RandomUtil;
import com.rubber.admin.core.enums.AdminCode;
import com.rubber.admin.core.enums.StatusEnums;
import com.rubber.admin.core.system.entity.SysUser;
import com.rubber.admin.core.system.model.SysUserModel;
import com.rubber.admin.core.system.service.ISysUserService;
import com.rubber.admin.security.login.bean.LoginBean;
import com.rubber.admin.security.login.bean.LoginException;
import com.rubber.admin.security.login.bean.LoginType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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


    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public SysUser findByAccount(LoginBean loginBean) {
        SysUser sysUser;
        if(LoginType.ACCOUNT == loginBean.getLoginType()){
            sysUser = doFindByAccount(loginBean);
        }else {
            sysUser = doFindByThird(loginBean);
        }
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

        SysUser sysUser = doCreateByModel(loginBean,sysUserModel);
        sysUser.setSalt(RandomUtil.randomNumbers(6));
        String encode = passwordEncoder.encode(sysUser.getEncodeKey(loginBean.getPassword()));
        sysUser.setLoginPwd(encode);
        return sysUser;
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
        sysUser.setStatus(StatusEnums.NORMAL);
        sysUser.setCreateDate(loginBean.getLoginTime());
        sysUser.setSuperUser(StatusEnums.NORMAL);
        return sysUser;
    }

}
