package com.rubber.admin.security.login.service.find;

import com.rubber.admin.core.system.model.SysUserModel;
import com.rubber.admin.security.login.bean.LoginBean;

/**
 * @author luffyu
 * Created on 2019-10-31
 * 第三方登陆扩展
 */
public interface ExpandUserFindProvider {

    /**
     * 返回登陆成功之后的用户基本信息
     * @param loginBean 登陆的bean信息
     * @return 返回null表示登陆失败 返回具体的对象表示登陆成功
     */
    SysUserModel findByLogin(LoginBean loginBean);

}
