package com.yiran.ruozhuo.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.Reader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by ruozhuo on 2017/1/21.
 */
public class Util {

    public static String getSign(Map<String, String> paramsMap) {
        if (paramsMap == null || paramsMap.size() == 0) {
            return null;
        }
        SortedSet<String> keys = new TreeSet<String>(paramsMap.keySet());
        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            sb.append("&").append(key).append("=").append(paramsMap.get(key));
        }
        String appid = paramsMap.get("appid");
        String key = selectKeyByAppid(appid);
        sb.append("&").append(key);
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

    public static Map<String, Object> xmlToMap(String xmlStr, String rootTag) throws Exception {
        MySAXHandler handler = new MySAXHandler();
        Map<String, Object> xmlMap = handler.getMap(xmlStr);
        return xmlMap;
    }

    public static String mapToXml(Map<String, Object> paramsMap, String rootTag) {
        String body = mapToXmlWithoutRoot(paramsMap);
        return "<" + rootTag + ">" + body + "</" + rootTag + ">";
    }

    public static String mapToXml(Map<String, Object> paramsMap) {
        String body = mapToXmlWithoutRoot(paramsMap);
        return "<xml>" + body + "</xml>";
    }

    public static String mapToXmlWithoutRoot (Map<String, Object> paramsMap) {
        String xmlStr;
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry entry : paramsMap.entrySet()) {
            stringBuilder.append("<").append(entry.getKey()).append(">")
                    .append(entry.getValue()).append("</").append(entry.getKey()).append(">");
        }
        xmlStr = stringBuilder.toString();
        return xmlStr;
    }

    private static SqlSessionFactory sqlSessionFactory;
    private static Reader reader;

    static {
        try {
            reader = Resources.getResourceAsReader("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String selectKeyByAppid(String appid) {
        SqlSession session = sqlSessionFactory.openSession();
        String key = session.selectOne("Pay.selectKeyByAppid", appid);
        session.commit();
        session.close();
        return key;
    }

}
