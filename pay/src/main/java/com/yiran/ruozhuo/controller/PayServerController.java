package com.yiran.ruozhuo.controller;

import com.yiran.ruozhuo.utils.Util;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ruozhuo on 2017/1/17.
 */
@Controller
public class PayServerController {

    @ResponseBody
    @RequestMapping(value = "/pay/unifiedorder", method = RequestMethod.POST)
    public String creatPayOrder(HttpServletRequest request, HttpServletResponse response) {
        /*
                                @RequestParam(name = "appid") String appid,
                                @RequestParam(name = "mch_id") String mch_id,
                                @RequestParam(name = "device_info") String device_info,
                                @RequestParam(name = "nonce_str") String nonce_str,
                                @RequestParam(name = "sign") String sign,
                                @RequestParam(name = "sign_type", required = false) String sign_typ,
                                @RequestParam(name = "body") String body,
                                @RequestParam(name = "detail", required = false) String detail,
                                @RequestParam(name = "attach", required = false) String attach,
                                @RequestParam(name = "out_trade_no") String out_trade_no,
                                @RequestParam(name = "fee_type", required = false) String fee_type,
                                @RequestParam(name = "total_fee") int total_fee,
                                @RequestParam(name = "spbill_create_ip") String spbill_create_ip,
                                @RequestParam(name = "time_start", required = false) String time_start,
                                @RequestParam(name = "time_expire", required = false) String time_expire,
                                @RequestParam(name = "goodstag", required = false) String goodstag,
                                @RequestParam(name = "notify_url") String notify_url,
                                @RequestParam(name = "trade_type") String trade_type,
                                @RequestParam(name = "product_id", required = false) String product_id,
                                @RequestParam(name = "limit_pay", required = false) String limit_pay,
                                @RequestParam(name = "openid", required = false) String openid
         */

        Map<String, String[]> paramsMap = request.getParameterMap();
        Map<String, String> map = new HashMap<String, String>();
        for (Map.Entry entry : paramsMap.entrySet()) {
            map.put(String.valueOf(entry.getKey()), ((String[]) entry.getValue())[0]);
            System.out.println("key = " + entry.getKey() + ", value = " + ((String[]) entry.getValue())[0]);
        }

        System.out.println("-->>map = " + map);
        String inputSign = map.remove("sign");
        System.out.println("-->>inputSign = " + inputSign);
        System.out.println("-->>map = " + map);

        String sign = Util.getSign(map);
        System.out.println("-->>newSign = " + sign);

        StringBuilder sb = new StringBuilder();
        String return_code = sign.equals(inputSign) ? "SUCCESS" : "FAIL";
        String return_msg = "";
        String appid = map.get("appid");
        String mch_id = map.get("mch_id");
        String device_info = map.get("device_info");
        String nonce_str = map.get("nonce_str");
//        String sign = "";
        String result_code = "SUCCESS";
        String err_code = "";
        String err_code_des = "";

        String trade_type = map.get("trade_type");
        String prepay_id = "";

        String code_url = "";

        long timestamp = System.currentTimeMillis();


        sb.append("<xml>");
        sb.append("<return_code>").append("<![CDATA[").append(return_code).append("]]>").append("</return_code>");
        sb.append("<return_msg>").append("<![CDATA[").append(return_msg).append("]]>").append("</return_msg>");

        // when return_code is "SUCCESS"
        if (return_code.equals("SUCCESS")) {
            sb.append("<appid>").append("<![CDATA[").append(appid).append("]]>").append("</appid>");
            sb.append("<mch_id>").append("<![CDATA[").append(mch_id).append("]]>").append("</mch_id>");
            sb.append("<device_info>").append("<![CDATA[").append(device_info).append("]]>").append("</device_info>");
            sb.append("<nonce_str>").append("<![CDATA[").append(nonce_str).append("]]>").append("</nonce_str");
            sb.append("<sign>").append("<![CDATA[").append(sign).append("]]>").append("</sign>");
            sb.append("<result_code>").append("<![CDATA[").append(result_code).append("]]>").append("</result_code>");
            sb.append("<err_code>").append("<![CDATA[").append(err_code).append("]]>").append("</err_code>");
            sb.append("<err_code_des>").append("<![CDATA[").append(err_code_des).append("]]>").append("</err_code_des>");

            // when result_code is "SUCCESS"
            if (result_code.equals("SUCCESS")) {
                sb.append("<trade_type>").append("<![CDATA[").append(trade_type).append("]]>").append("</trade_type>");
                sb.append("<prepay_id>").append("<![CDATA[").append(prepay_id).append("]]>").append("</prepay_id>");
                sb.append("<code_url>").append("<![CDATA[").append(code_url).append("]]>").append("</code_url>");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }

    // 8.检查参数合法性
    @RequestMapping(value = "/payordercheck", method = RequestMethod.POST)
    private String payOrderCheck(HttpServletRequest request, HttpServletResponse response) {

        return "";
    }

    // 9-10.验证授权
    @RequestMapping(value = "/authenticate")
    private String authenticate(HttpServletRequest request, HttpServletResponse response) {

        return "";
    }

    // 10.异步通知商户支付结果
    @RequestMapping(value = "/")
    private String syncNotify(HttpServletRequest request, HttpServletResponse response) {

        return "";
    }

    // 12.返回支付结果，并发微信消息提示
    @RequestMapping(value = "/gobackpayresult", method = RequestMethod.POST)
    private String goBackPayResult(HttpServletRequest request, HttpServletResponse response) {

        return "";
    }

    // 14.1 返回支付结果
    @RequestMapping(value = "/returnpayresult")
    private String returnPayResult(HttpServletRequest request, HttpServletResponse response) {

        return "";
    }

}
