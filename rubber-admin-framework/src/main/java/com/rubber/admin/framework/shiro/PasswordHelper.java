package com.rubber.admin.framework.shiro;

import com.rubber.admin.core.entity.SysUser;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import java.util.Random;

/**
 * @author luffyu
 * Created on 2019-05-13
 */
public class PasswordHelper {

    /**
     * MD5
     */
    public static final String ALGORITHM_NAME = "MD5";
    /**
     * hash的code值
     */
    public static final int HASH_ITERATIONS = 1024;

    /**
     * 为user用户设置盐值
     * @param user
     */
    public static void encryptPassword(SysUser user) {
        RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
        // User对象包含最基本的字段Username和Password
        // 将用户的注册密码经过散列算法替换成一个不可逆的新密码保存进数据，散列过程使用了盐
        user.setSalt("1234");
        String newPassword = new SimpleHash(ALGORITHM_NAME, user.getPassword(),
                ByteSource.Util.bytes(user.getSalt()), HASH_ITERATIONS).toHex();
        user.setPassword(newPassword);
    }


    /**
     * 获取用户的密码
     * @param password 密码信息
     * @param salt 研制
     * @return
     */
    public static String encryptPassword(String password,String salt) {
        RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
        return new SimpleHash(ALGORITHM_NAME, password,
                ByteSource.Util.bytes(salt), HASH_ITERATIONS).toHex();

    }


    public static void main(String[] args) {
        String salt = "12345";
        String password = "123456";
        RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
        // User对象包含最基本的字段Username和Password
        // 将用户的注册密码经过散列算法替换成一个不可逆的新密码保存进数据，散列过程使用了盐
        String newPassword = new SimpleHash(ALGORITHM_NAME, password,
                ByteSource.Util.bytes(salt), HASH_ITERATIONS).toHex();
        System.out.println(newPassword);
    }

}
