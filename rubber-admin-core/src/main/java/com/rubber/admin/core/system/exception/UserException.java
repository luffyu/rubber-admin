package com.rubber.admin.core.system.exception;

import cn.hutool.coocaa.util.result.code.ICodeHandle;
import com.rubber.admin.core.exceptions.AdminException;

/**
 * @author luffyu
 * Created on 2019-11-04
 */
public class UserException extends AdminException {


    public UserException(ICodeHandle handle) {
        super(handle);
    }

    public UserException(ICodeHandle handle, String msg, Object... arguments) {
        super(handle, msg, arguments);
    }
}
