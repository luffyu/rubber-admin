package com.rubber.admin.core.system.exception;

import cn.hutool.luffyu.util.result.code.ICodeHandle;
import com.rubber.admin.core.exceptions.AdminException;

/**
 * @author luffyu
 * Created on 2019-11-01
 */
public class DictException extends AdminException {

    public DictException(ICodeHandle handle) {
        super(handle);
    }

    public DictException(ICodeHandle handle, String msg, Object... arguments) {
        super(handle, msg, arguments);
    }
}
