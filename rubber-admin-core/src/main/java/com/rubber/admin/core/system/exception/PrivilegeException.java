package com.rubber.admin.core.system.exception;

import cn.hutool.coocaa.util.result.code.ICodeHandle;
import com.rubber.admin.core.exceptions.AdminException;

/**
 * @author luffyu
 * Created on 2019-11-01
 */
public class PrivilegeException extends AdminException {

    public PrivilegeException(ICodeHandle handle) {
        super(handle);
    }

    public PrivilegeException(ICodeHandle handle, String msg, Object... arguments) {
        super(handle, msg, arguments);
    }
}
