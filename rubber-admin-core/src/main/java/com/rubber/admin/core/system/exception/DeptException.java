package com.rubber.admin.core.system.exception;

import cn.hutool.coocaa.util.result.code.ICodeHandle;
import com.rubber.admin.core.exceptions.AdminException;

/**
 * @author luffyu
 * Created on 2019-11-06
 */
public class DeptException extends AdminException {





    public DeptException(ICodeHandle handle) {
        super(handle);
    }

    public DeptException(ICodeHandle handle, String msg, Object... arguments) {
        super(handle, msg, arguments);
    }
}
