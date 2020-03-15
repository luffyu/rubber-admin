package com.rubber.admin.core.authorize.exception;

import cn.hutool.luffyu.util.result.code.ICodeHandle;
import com.rubber.admin.core.exceptions.AdminException;

/**
 * <p>
 *     权限相关的异常信息
 * </p>
 *
 * @author luffyu
 * @date 2020-03-14 10:32
 **/
public class AuthGroupException extends AdminException {

    public AuthGroupException(ICodeHandle handle) {
        super(handle);
    }

    public AuthGroupException(ICodeHandle handle, String msg, Object... arguments) {
        super(handle, msg, arguments);
    }
}
