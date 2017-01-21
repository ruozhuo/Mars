package com.yiran.ruozhuo.util;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by ruozhuo on 2017/1/21.
 */
public class CommonUtil {

    public static String getSign(Map<String, String> paramsMap) {
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

    public static String getIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                ip = ip.substring(0, index);
            }
            return ip;
        }
        ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }

}
