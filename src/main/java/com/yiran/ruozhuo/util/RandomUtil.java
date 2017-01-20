package com.yiran.ruozhuo.util;

import org.apache.commons.lang.math.RandomUtils;

/**
 * Created by ruozhuo on 2017/1/19.
 */
public class RandomUtil {

    public static String getRandomNumber() {
        String s1 = String.valueOf(RandomUtils.nextInt());
        String s2 = String.valueOf(RandomUtils.nextInt());
        return s1 + s2;
    }

}
