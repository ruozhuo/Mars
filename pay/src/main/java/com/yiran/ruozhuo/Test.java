package com.yiran.ruozhuo;

import com.yiran.ruozhuo.utils.MySAXHandler;
import com.yiran.ruozhuo.utils.Util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ruozhuo on 2017/1/21.
 */
public class Test {

    public static void main(String[] args) {
        testSAX();
    }

    public static void testSAX() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "mitcc");
        map.put("sex", "male");
        map.put("city", "Shanghai");
        String rootTag = "xml";
        String xmlStr = Util.mapToXml(map, rootTag);


        MySAXHandler handler = new MySAXHandler();
        Map<String, Object> xmlMap = null;
        try {
            xmlMap = handler.getMap(xmlStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("xmlMap: " + xmlMap);
    }
}
