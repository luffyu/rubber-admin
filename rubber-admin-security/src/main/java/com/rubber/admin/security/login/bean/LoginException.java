package com.rubber.admin.security.login.bean;

import cn.hutool.coocaa.util.result.IResultHandle;
import cn.hutool.coocaa.util.result.code.ICodeHandle;
import com.rubber.admin.core.exceptions.AdminRunTimeException;

/**
 * @author luffyu
 * Created on 2019-10-23
 */
public class LoginException extends AdminRunTimeException {

    public LoginException(IResultHandle handle) {
        super(handle);
    }

    public LoginException(ICodeHandle handle) {
        super(handle);
    }


    public LoginException(ICodeHandle handle, String msg, Object... arguments) {
        super(handle, msg, arguments);
    }
}
