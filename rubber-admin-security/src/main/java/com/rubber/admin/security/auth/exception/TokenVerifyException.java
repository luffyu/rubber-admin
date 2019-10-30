package com.rubber.admin.security.auth.exception;

import cn.hutool.coocaa.util.result.code.ICodeHandle;
import com.rubber.admin.core.exceptions.AdminException;

/**
 * @author luffyu
 * Created on 2019-10-22
 */
public class TokenVerifyException extends AdminException {


    public TokenVerifyException(ICodeHandle handle) {
        super(handle);
    }
}