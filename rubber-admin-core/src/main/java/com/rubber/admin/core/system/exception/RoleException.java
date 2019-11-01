package com.rubber.admin.core.system.exception;

import cn.hutool.coocaa.util.result.code.ICodeHandle;
import com.rubber.admin.core.exceptions.AdminException;

/**
 * @author luffyu
 * Created on 2019-11-01
 */
public class RoleException extends AdminException {

    public RoleException(ICodeHandle handle) {
        super(handle);
    }

    public RoleException(ICodeHandle handle, String msg, Object... arguments) {
        super(handle, msg, arguments);
    }
}
