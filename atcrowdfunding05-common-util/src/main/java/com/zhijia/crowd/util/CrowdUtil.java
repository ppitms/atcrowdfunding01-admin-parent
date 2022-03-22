package com.zhijia.crowd.util;

import com.zhijia.crowd.constart.CrowdConstant;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 请求工具类
 *
 * @author zhijia
 * @create 2022-03-15 10:59
 */
public class CrowdUtil {

    //对字符串进行MD%加密
    public static String md5(String source) {
        //1、是否有效
        if (source == null || source.length() == 0) {
            //2、无效抛异常
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }

        try {
            //3、获取MessageDigest对象
            String algorithm = "md5";

            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);

            //4、获取明文字字符串对应的字节数组
            byte[] input = source.getBytes();

            //5、执行加密
            byte[] output = messageDigest.digest(input);

            //6、创建BigInteger对象  1为正数
            BigInteger bigInteger = new BigInteger(1, output);
            //7、按照16进制将Integer转化字符串
            String encoded = bigInteger.toString(16).toUpperCase();
            return encoded;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


    //判断当前请求是否为ajax请求
    public static boolean judgeRequestType(HttpServletRequest request) {

        //1、获取请求消息头
        String accept = request.getHeader("Accept");
        String xRequestHeader = request.getHeader("X-Requested-With");

        //2、判断
        return ((accept != null && accept.contains("application/json")) ||
                (xRequestHeader != null && xRequestHeader.equals("XMLHttpRequest")));
    }
}
