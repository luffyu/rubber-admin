package com.rubber.admin.core.system.exception;

import cn.hutool.coocaa.util.result.code.ICodeHandle;
import com.rubber.admin.core.exceptions.AdminException;

/**
 * @author luffyu
 * Created on 2019-11-04
 */
public class MenuException extends AdminException {

    public MenuException(ICodeHandle handle, Object data) {
        super(handle, data);
    }

    public MenuException(ICodeHandle handle) {
        super(handle);
    }

    public MenuException(ICodeHandle handle, String msg, Object... arguments) {
        super(handle, msg, arguments);
    }
}
