package com.wangyu.kgbackend.util;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wongy
 * 字符串的方法
 */
public class StringUtils {
    /**
     * 获得存储图片用的随机命名字符串
     * @param length 字符串长度
     * @return 一个指定长度的随机字符串
     */
    public static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 返回json文件中的字符串可能在使用时需要对引号做处理
     * @param str 处理前字符串
     * @return 处理后字符串
     */
    public static String uniformJsonString(String str){
        return str.replaceAll("\"([^\"]+)\"(\\s*:\\s*)", "$1$2");
    }

    /**
     * 判断字符串是不是一个数字
     * @param str 一个字符串
     * @return 是数字返回true，否则返回false
     */
    public static boolean isNumeric(String str){
        final Matcher isNum = Pattern.compile("[0-9]*\\.?[0-9]+").matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }
}
