package com.rubber.admin.security.auth.exception;

import cn.hutool.luffyu.util.result.code.ICodeHandle;
import org.springframework.security.core.AuthenticationException;

/**
 * @author luffyu
 * Created on 2019-10-22
 */
public class TokenCreateException extends AuthenticationException {


    public TokenCreateException(ICodeHandle handle) {
        super(handle.getMsg());
    }
}
