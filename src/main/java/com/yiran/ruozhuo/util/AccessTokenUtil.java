package com.yiran.ruozhuo.util;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ruozhuo on 2017/1/8.
 */
public class AccessTokenUtil {

    private static Logger log = Logger.getLogger(AccessTokenUtil.class);
    /**
     * 过期时间7200秒， 因为微信token过期时间为2小时，即7200秒
     */
    private static int expireTime = 7200 * 1000;
    private static long refreshTime;


    public static synchronized String getAccessToken() {
        return getAccessToken(false);
    }

    public static synchronized String getAccessToken(boolean refresh) {
        /*
        if (StringUtils.isBlank(access_token) || (System.currentTimeMillis() - refreshTime) > expireTime || refresh) {
            access_token = initAccessToken();
            refreshTime = System.currentTimeMillis();
        }
        return access_token;
        */
        return initAccessToken();
    }

    private static String initAccessToken() {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
                + Const.APPID + "&secret=" + Const.APPSECRET;
        String responseContent = HttpUtil.sendGet(url);
        JSONObject object = new JSONObject();
        try {
            object = new JSONObject(responseContent);
            return (String) object.get("access_token");
        } catch (JSONException e) {
            try {
                log.error("获取token失败 errcode:" + object.get("errcode") + " errmsg:" + object.getString("errmsg"));
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return null;
    }

}
