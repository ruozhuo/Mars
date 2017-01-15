package com.yiran.ruozhuo.util;

import java.util.Arrays;

/**
 * Created by ruozhuo on 2017/1/8.
 */
public class CheckUtil {

    private static final String token = "ruozhuo";

    public static boolean checkSignature(String signature, String timestamp, String nonce) {
        // 拼接字符串
        String[] arr = new String[] { token, timestamp, nonce };
        // 排序
        Arrays.sort(arr);
        // 生成字符串
        StringBuffer content = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        // SHA1加密
        String tmp = DecriptUtil.SHA1(content.toString());
        return tmp.equals(signature);
    }

}
