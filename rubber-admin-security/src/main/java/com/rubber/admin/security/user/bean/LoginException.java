package com.rubber.admin.security.user.bean;

import cn.hutool.coocaa.util.result.code.ICodeHandle;
import com.rubber.admin.core.exceptions.AdminException;

/**
 * @author luffyu
 * Created on 2019-10-23
 */
public class LoginException extends AdminException {


    public LoginException(ICodeHandle handle) {
        super(handle);
    }


    public LoginException(ICodeHandle handle, String msg, Object... arguments) {
        super(handle, msg, arguments);
    }
}
