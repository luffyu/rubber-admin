package com.rubber.admin.security.login.service.find;

import com.rubber.admin.core.system.entity.SysUser;
import com.rubber.admin.security.auth.TokenVerifyBean;
import com.rubber.admin.security.login.bean.LoginBean;

/**
 * @author luffyu
 * Created on 2019-10-31
 */
public interface IUserFindService {


    /**
     * 通过账户查找一个SysUser
     * @param loginBean 登陆的bean
     * @return 查询之后的用户信息
     */
    SysUser findByAccount(LoginBean loginBean);


    /**
     * 通过账户查找一个SysUser
     * @param loginBean 登陆的bean
     * @param verifyBean 验证的bean
     * @return 查询之后的用户信息
     */
    SysUser findByAccount(LoginBean loginBean, TokenVerifyBean verifyBean);

}
