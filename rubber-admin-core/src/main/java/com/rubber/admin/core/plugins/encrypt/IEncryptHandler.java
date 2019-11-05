package com.rubber.admin.core.plugins.encrypt;

/**
 * @author luffyu
 * Created on 2019-11-05
 */
public interface IEncryptHandler {

    /**
     * 需要加密的密码信息
     * @param psw 未加密密码
     * @param salt 盐值
     * @return 返回加密密码
     */
    String encrypt(String psw,String salt);

    /**
     * 创建盐值
     * @param length 长度
     * @return 返回创建的盐值
     */
    String createSalt(int length);


    /**
     * 检测密码是否正确
     * @param psw 密码信息
     * @param salt 盐值
     * @param encrypt 加密的值
     * @return
     */
    boolean matches(String psw,String salt,String encrypt);

}
