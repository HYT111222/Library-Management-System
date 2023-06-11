package com.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * md5加密密码
 * @Auther HYT
 * @Date 2023/6/11
 * @Desc
 */
public class md5 {
    public static String code(String psd, String salt){
        try {
            StringBuffer stingBuffer = new StringBuffer();
            // 指定加密算法
            MessageDigest digest = MessageDigest.getInstance("MD5");
            // 拼接密码和盐，并转化成byte类型的数据，然后哈希
            byte[] bs = digest.digest((psd + salt).getBytes());
            // 遍历bs,让其生成32位字符串，拼接字符串
            for (byte b : bs) {
                int i = b & 0xff;
                String hexString = Integer.toHexString(i);
                if (hexString.length() < 2) {
                    hexString = "0" + hexString;
                }
                stingBuffer.append(hexString);
            }
            return stingBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;

    }
}
