package com.yiran.ruozhuo.controller;

import com.yiran.ruozhuo.model.Order;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Reader;

/**
 * Created by ruozhuo on 2017/1/15.
 */
@Controller
public class PayController {

    // 1. 生成图文消息链接
    // 向用户呈现订单详细信息
    @ResponseBody
    @RequestMapping(value = "/pay")
    public String pay(HttpServletRequest request, HttpServletResponse response) {
        return "pay";
    }

    // 4.生成商户订单
    @RequestMapping(value = "/generateorder")
    private String generateOrder(HttpServletRequest request, HttpServletResponse response,
                                 @ModelAttribute Order order) {
        return "";
    }

    // 6.生成预付定单
    @RequestMapping(value = "/prepay", method = RequestMethod.POST)
    public String prepay(HttpServletRequest request, HttpServletResponse response,
                         @RequestParam(name = "prepay_id") String prepay_id,
                         @RequestParam(name = "paySign") String paySign, Model model) {

        model.addAttribute("prepay_id", prepay_id);
        model.addAttribute("paySign", paySign);

        return "";
    }

    // 7.用户点击发起支付
    @RequestMapping(value = "/userPay")
    public String userPay() {
        // 7.1 输入密码正确
        // 7.2 输入密码错误

        return "";
    }

    // 9.确认支付，输入密码
    @RequestMapping(value = "/confirmpay", method = RequestMethod.POST)
    private String confirmPay(HttpServletRequest request, HttpServletResponse response) {
        return "";
    }

    // 11. 告知微信通知处理结果
    @RequestMapping(value = "/notifyresult", method = RequestMethod.POST)
    private String notifyResult(HttpServletRequest request, HttpServletResponse response) {

        return "";
    }

    // 13.查询商户后台支付结果
    @RequestMapping(value = "/querypayresult", method = RequestMethod.GET)
    private String queryPayResult(HttpServletRequest request, HttpServletResponse response) {


        return "";
    }

    // 14.调用查询API，查询支付结果
    @RequestMapping(value = "/querypayresult", method = RequestMethod.POST)
    private String queryPayResult(HttpServletRequest request, HttpServletResponse response,
                                  Model model) {

        return "";
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


}
