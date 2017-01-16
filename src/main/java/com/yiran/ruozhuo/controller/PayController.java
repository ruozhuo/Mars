package com.yiran.ruozhuo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by ruozhuo on 2017/1/15.
 */
@Controller
public class PayController {

    @RequestMapping("/pay/unifiedorder")
    public String creatPayOrder(@RequestParam(name = "appid") String appid,
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
                                @RequestParam(name = "openid", required = false) String openid) {



        return "";
    }

}
