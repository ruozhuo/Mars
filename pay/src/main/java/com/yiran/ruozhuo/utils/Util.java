package com.yiran.ruozhuo.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by ruozhuo on 2017/1/19.
 */
public class Util {

    private static String getSign(Map<String, String> paramsMap) {
        if (paramsMap == null || paramsMap.size() == 0) {
            return null;
        }
        SortedSet<String> keys = new TreeSet<String>(paramsMap.keySet());
        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            sb.append("&").append(key).append("=").append(paramsMap.get(key));
        }
        sb.append("&").append(Const.KEY);
        sb.deleteCharAt(0);
        String sign = md5(sb.toString()).toUpperCase();
        return sign;
    }

    public static String md5(String plainText) {
        String md5Str = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte[] bytes = md.digest();
            StringBuffer stringBuffer = new StringBuffer("");
            for (int i = 0; i < bytes.length; ++i) {
                int b = bytes[i];
                if (b < 0) {
                    b += 256;
                }
                if (b < 16) {
                    stringBuffer.append("0");
                }
                stringBuffer.append(Integer.toHexString(b));
            }
            md5Str = stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            md5Str = e.toString();
        }
        return md5Str;
    }

}
