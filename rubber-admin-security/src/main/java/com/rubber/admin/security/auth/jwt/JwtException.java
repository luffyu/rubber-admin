package com.rubber.admin.security.auth.jwt;

import com.luffyu.piece.utils.result.code.ICodeHandle;
import com.rubber.admin.core.exceptions.AdminException;

/**
 * @author luffyu
 * Created on 2019-10-22
 */
public class JwtException extends AdminException {


    public JwtException(ICodeHandle handle) {
        super(handle);
    }
}
