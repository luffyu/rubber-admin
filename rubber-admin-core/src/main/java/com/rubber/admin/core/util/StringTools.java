package com.rubber.admin.core.util;

import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description: 字符串处理类
 * @author: YGF
 */
@SuppressWarnings("unused")
public class StringTools extends StringUtils {

    /**
     * 整数判断
     */
    private static final Pattern INT_NUM_PATTER = Pattern.compile("^[-+]?[\\d]*$");
    /**
     * double数判断
     */
    private static final Pattern DOUBLE_MUN_PATTER = Pattern.compile("^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$");
    /**
     * 特殊字符判断
     */
    private static final Pattern SPECIAL_STRING_PATTER = Pattern.compile(".*&|@|#|～|\\*|\\^|\\\\|\r|\n|\t|\\$|~|update|delete|insert.*");


    private static Pattern underline = Pattern.compile("[A-Z]");

    private static Pattern camel = Pattern.compile("_(\\w)");

    /**
     * 把字符串用& 链接起来 key1&key2&key2
     * @param keys 需要链接的数据
     * @return 一个链接起来的字符串
     */
    public static String linkKey(Object... keys){
        if(keys == null){
            return null;
        }
        StringBuilder stringBuffer = new StringBuilder();
        boolean first = true;
        for(Object key:keys){
            if(first){
                stringBuffer.append(key);
                first = false;
                continue;
            }
            stringBuffer.append("&").append(key);
        }
        return stringBuffer.toString();
    }


    /**
     * @return 把map中的值用 key1=value1&key2=value2的形式 组合起来
     */
    public static String linkMap(Map<String,String> maps){
        if(maps == null){
            return null;
        }
        StringBuilder stringBuffer = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String,String> map:maps.entrySet()){
            String value = map.getKey()+"="+map.getValue();
            if(first){
                stringBuffer.append(value);
                first = false;
                continue;
            }
            stringBuffer.append("&").append(value);
        }
        return stringBuffer.toString();
    }

    /**
     * 验证 字符串是不是都是数字
     * @param str 需要验证的字符串
     * @return 是整数返回true,否则返回false
     */
    public static boolean isInteger(Object str) {
        if(str == null || StringTools.isEmpty(String.valueOf(str))){
            return false;
        }
        return INT_NUM_PATTER.matcher(String.valueOf(str)).matches();
    }
    public static boolean isInteger(String str) {
        return INT_NUM_PATTER.matcher(str).matches();
    }

    /**
     * 验证 字符串是不是都是double
     * @param str 需要验证的字符串
     * @return 是整数返回true,否则返回false
     */
    private static boolean isDouble(String str) {
        return DOUBLE_MUN_PATTER.matcher(str).matches();
    }

    private static boolean isDouble(Object str) {
        if(str == null || StringTools.isEmpty(String.valueOf(str))){
            return false;
        }
        return DOUBLE_MUN_PATTER.matcher(String.valueOf(str)).matches();
    }


    /**
     * 验证 字符串是否是数字
     * @param str 需要验证的字符串
     * @return 是整数返回true,否则返回false
     */
    private static boolean isNumber(String str){
        return isInteger(str) || isDouble(str);
    }
    public static boolean isNumber(Object str){
        return isInteger(str) || isDouble(str);
    }

    /**
     * 验证所有字符串是不是 都是数字
     * @param str 需要验证的字符串
     * @return 是整数返回true,否则返回false
     */
    public static boolean isNumberList(String ... str){
        if(str == null){
            return false;
        }
        for(String s:str){
            if(!isNumber(s)){
                return false;
            }
        }
        return true;
    }

    /**
     * 最大长度限制 如果超过了最大限制 末尾用 ...代替
     * @param str 需要限制长度的字符串
     * @param maxLength 字符串的最大长度
     * @return 返回限制最大长度的字符串
     */
    public static String limitLength(String str,int maxLength){
        if(isEmpty(str)){
            return str;
        }
        if(str.length() <= maxLength){
            return str;
        }
        return str.substring(0,maxLength - 4) + "...";
    }

    /**
     * @param dit 几位数
     * @return 生成多少位的随机数
     */
    private static String getRandNo(int dit){
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < dit; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    /**
     * 生成一个随机字符串
     * @return 返回这个随机字符串
     */
    public static String createRandString(){
        String key = DateUtils.formatData(new Date(),DateUtils.IN_yyyy_MM_dd_HH_mm_ss);
       return key + getRandNo(6);
    }


    /**
     * 特殊符号过滤
     * @param str 需要被过滤的字符
     * @return true : 有特殊字符 false : 无特殊字符
     */
    public static Boolean specialStringFilter(String str){
        Matcher m = SPECIAL_STRING_PATTER.matcher(str);
        return m.find();
    }

    /**
     * 过滤字符串的值为 -
     * @param obj 需要过滤的字符串
     * @return
     */
    public static String valueObjectNull(Object obj){
        return (obj == null) ? "-" : obj.toString();
    }

    public static String replaceObjectNull(Object obj,String replace){
        return (obj == null) ? replace : obj.toString();
    }


    /**
     * 驼峰转下划线
     * @param str
     * @return
     */
    public static String underline(String str){
        if(isEmpty(str)){
            return str;
        }
        StringBuffer s = new StringBuffer(str);
        return underline(s).toString();
    }
    public static StringBuffer underline(StringBuffer str) {
        Matcher matcher = underline.matcher(str);
        StringBuffer sb = new StringBuffer(str);
        if(matcher.find()) {
            sb = new StringBuffer();
            //将当前匹配子串替换为指定字符串，并且将替换后的子串以及其之前到上次匹配子串之后的字符串段添加到一个StringBuffer对象里。
            //正则之前的字符和被替换的字符
            matcher.appendReplacement(sb,"_"+matcher.group(0).toLowerCase());
            //把之后的也添加到StringBuffer对象里
            matcher.appendTail(sb);
        }else {
            return sb;
        }
        return underline(sb);
    }


    /**
     * 下划线转驼峰
     * @param str
     * @return
     */
    public static String camelS(String str){
        if(isEmpty(str)){
            return str;
        }
        StringBuffer s = new StringBuffer(str);
        return camel(s).toString();
    }
    public static StringBuffer camel(StringBuffer str) {
        //利用正则删除下划线，把下划线后一位改成大写
        Matcher matcher = camel.matcher(str);
        StringBuffer sb = new StringBuffer(str);
        if(matcher.find()) {
            sb = new StringBuffer();
            //将当前匹配子串替换为指定字符串，并且将替换后的子串以及其之前到上次匹配子串之后的字符串段添加到一个StringBuffer对象里。
            //正则之前的字符和被替换的字符
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
            //把之后的也添加到StringBuffer对象里
            matcher.appendTail(sb);
        }else {
            return sb;
        }
        return camel(sb);
    }


    /**
     * 判断字段是不是空的
     * @param obj
     * @return
     */
    public static boolean checkParamsNull(Object ... obj){
        if(obj == null){
            return false;
        }
        for(Object o:obj){
            if(o == null){
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字段是不是空的 并且不能为空字符串
     * @param obj
     * @return
     */
    public static boolean checkParamsStringNull(Object ... obj){
        if(obj == null){
            return false;
        }
        for(Object o:obj){
            if(o instanceof String){
                if(isEmpty(String.valueOf(o))){
                    return false;
                }
            }else if(o == null){
                return false;
            }
        }
        return true;
    }


}
