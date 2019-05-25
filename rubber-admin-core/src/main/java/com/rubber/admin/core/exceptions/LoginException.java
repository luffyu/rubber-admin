package com.rubber.admin.core.exceptions;

import com.rubber.admin.core.enums.MsgCode;
import com.rubber.admin.core.exceptions.base.BaseException;

/**
 * @author luffyu
 * Created on 2019-05-13
 */
public class LoginException extends BaseException {

    public LoginException() {
    }

    public LoginException(String msg) {
        super(msg);
    }

    public LoginException(String code, String msg, Object data) {
        super(code, msg, data);
    }


    public LoginException(MsgCode msgCode, Object data) {
        super(msgCode, data);
    }
}
