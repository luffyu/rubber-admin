package com.rubber.admin.framework.exception;

import com.rubber.admin.core.enums.MsgCode;
import com.rubber.admin.core.exceptions.AdminException;

/**
 * @author luffyu
 * Created on 2019-05-13
 */
public class LoginException extends AdminException {

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
