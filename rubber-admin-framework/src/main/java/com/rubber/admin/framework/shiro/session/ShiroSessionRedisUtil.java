package com.rubber.admin.framework.shiro.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;

import java.io.*;
import java.nio.charset.Charset;

/**
 * @author luffyu
 * Created on 2019-05-24
 */
public class ShiroSessionRedisUtil {


    private static final Charset charset = Charset.forName("UTF8");

    /**
     * 把session对象转化为byte数组
     * @param session session信息
     * @return 返回当前的session 序列化值
     */
    public static byte[] sessionToByte(Session session){
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        byte[] bytes = null;
        try {
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(session);
            bytes = bo.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * 把byte数组还原为session
     * @param bytes 把序列化值
     * @return 返回当前的session 序列化值
     */
    public static Session byteToSimpleSession(byte[] bytes){
        ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
        ObjectInputStream in;
        Session session = null;
        try {
            in = new ObjectInputStream(bi);
            session = (SimpleSession) in.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return session;
    }


    /**
     * 序列化key值
     * @param string 字符串信息
     * @return 返回需理会之后的值
     */
    public static byte[] serializeKey(String string){
        return(string == null ? null : string.getBytes(charset));
    }
    public static byte[] serializeKey(Serializable id){
        if(id == null){
            return null;
        }
       return serializeKey(id.toString());
    }




}
