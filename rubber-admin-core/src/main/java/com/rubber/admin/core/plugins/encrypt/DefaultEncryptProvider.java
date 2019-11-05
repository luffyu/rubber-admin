package com.rubber.admin.core.plugins.encrypt;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import org.springframework.stereotype.Component;

/**
 * @author luffyu
 * Created on 2019-11-05
 */
@Component
public class DefaultEncryptProvider implements IEncryptHandler {


    @Override
    public String encrypt(String psw,String salt) {
        String pswSalt = joinPswAndSalt(psw,salt);
        return BCrypt.hashpw(pswSalt);
    }


    @Override
    public String createSalt(int length) {
        return BCrypt.gensalt(length);
    }



    @Override
    public boolean matches(String psw, String salt, String encrypt) {
        try{
            String pswSalt = joinPswAndSalt(psw,salt);
            return BCrypt.checkpw(pswSalt,encrypt);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    private String joinPswAndSalt(String psw,String salt){
        return StrUtil.join("-",psw,salt);
    }


}
