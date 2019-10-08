package com.rubber.admin.core.exceptions;

import com.luffyu.piece.utils.result.IResultHandle;
import com.luffyu.piece.utils.result.code.ICodeHandle;
import com.luffyu.piece.utils.result.exception.BaseResultException;
import com.luffyu.piece.utils.result.exception.BaseResultRunTimeException;

/**
 * @author luffyu
 * Created on 2019-05-13
 */
public class AdminException extends BaseResultRunTimeException {


    public AdminException(String msg) {
        super(msg);
    }

    public AdminException(IResultHandle handle) {
        super(handle);
    }

    public AdminException(String code, String msg, Object data) {
        super(code, msg, data);
    }

    public AdminException(ICodeHandle handle, Object data) {
        super(handle, data);
    }

    public AdminException(ICodeHandle handle) {
        super(handle);
    }

    public AdminException(ICodeHandle handle, String msg, Object... arguments) {
        super(handle, msg, arguments);
    }
}
