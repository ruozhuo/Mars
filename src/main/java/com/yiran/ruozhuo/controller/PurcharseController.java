package com.yiran.ruozhuo.controller;

import com.yiran.ruozhuo.model.Goods;
import com.yiran.ruozhuo.model.Order;
import com.yiran.ruozhuo.model.OrderExt;
import com.yiran.ruozhuo.model.User;
import com.yiran.ruozhuo.util.CommonUtil;
import com.yiran.ruozhuo.util.Const;
import com.yiran.ruozhuo.util.HttpUtil;
import com.yiran.ruozhuo.util.RandomUtil;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ruozhuo on 2017/1/10.
 */
@Controller
public class PurcharseController {

    @ResponseBody
    @RequestMapping(value = "/wechat/purchase")
    public String purchase(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String goodscode = request.getParameter("goodscode");
        // get user information.
        // 带着用户信息、商品id、数量跳转进入订单生成页面
        String encodedUrl = URLEncoder.encode(Const.REDIRECT_URI + "?goodscode=" + goodscode, "utf-8");
        System.out.println("-->>purchase.encodeUrl = " + encodedUrl);
        String openUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + Const.APPID
                + "&redirect_uri=" + encodedUrl + "&response_type=code&scope=snsapi_base#wechat_redirect";
        response.sendRedirect(openUrl);
        return "nothing";
    }

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public String order(HttpServletRequest request, HttpServletResponse response, Model model) {
        Map<String, ?> map = RequestContextUtils.getInputFlashMap(request);
        User user = (User) map.get("user");
        String goodscode = (String) map.get("goodscode");

        Goods goods = selectGoodsByGoodscode(goodscode);
        System.out.println("-->>order.get.goods = " + goods);

//        model.addAttribute("order", new Order());
//        model.addAttribute("goodscode", goodscode);
//        model.addAttribute("openid", user.getOpenid());
//        model.addAttribute("price", goods.getPrice());
//        model.addAttribute("catogory", goods.getCatogory());

        model.addAttribute("orderExt", new OrderExt());
        model.addAttribute("goodscode", goodscode);
        model.addAttribute("openid", user.getOpenid());
        model.addAttribute("price", goods.getPrice());
        model.addAttribute("catogory", goods.getCatogory());
        return "order";
    }

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public String orderDetail(HttpServletRequest request, @ModelAttribute OrderExt orderExt) {
        orderExt.setTotalcost(orderExt.getCount() * orderExt.getPrice());
        System.out.println("-->>order.post.orderExt = " + orderExt);
//        insertOrder(order);
        Order order = new Order(orderExt.getOrderid(), orderExt.getOpenid(),
                orderExt.getGoodscode(), orderExt.getCount(), orderExt.getPrice(),
                orderExt.getCount(), orderExt.getCreatetime(), orderExt.getPaymentid());

        //生成商户订单
        insertOrder(order);

        System.out.println("-->>order.id = " + order.getOrderid());


        // 5.调用统一下单API()，生成预付订单
        String appid = Const.APPID;
        String mch_id = Const.MCH_ID;
        String device_info = Const.DEVICE_INFO;
        String nonce_str = RandomUtil.getRandomNumber();

        String body = Const.BRAND + "-" + orderExt.getCatogory();
        String out_trade_no = order.getOpenid() + "-" + order.getOrderid() + "-" + System.currentTimeMillis();
        String total_fee = String.valueOf(orderExt.getTotalcost());
        String spbil_create_ip = CommonUtil.getIP(request);
        String notify_url = Const.URL_PAY_CALLBACK;
        String trade_type = Const.TRADE_TYPE;

        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("appid", appid);
        paramsMap.put("mch_id", mch_id);
        paramsMap.put("device_info", device_info);
        paramsMap.put("nonce_str", nonce_str);
        paramsMap.put("body", body);
        paramsMap.put("out_trade_no", out_trade_no);
        paramsMap.put("total_fee", total_fee);
        paramsMap.put("spbill_create_ip", spbil_create_ip);
        paramsMap.put("notify_url", notify_url);
        paramsMap.put("trade_type", trade_type);

        paramsMap.put("orderid", String.valueOf(order.getOrderid()));

        String sign = CommonUtil.getSign(paramsMap);
        paramsMap.put("sign", sign);

        String url = "http://pay.ittun.com/pay/unifiedorder";
        String xmlStr = HttpUtil.sendPost(url, paramsMap);
        return "orderdetail";
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

    private Goods selectGoodsByGoodscode(String goodscode) {
        SqlSession session = sqlSessionFactory.openSession();
        Goods goods = session.selectOne("User.selectGoodsByGoodscode", goodscode);
        session.commit();
        session.close();
        return goods;
    }

    private int insertOrder(Order order) {
        SqlSession session = sqlSessionFactory.openSession();
        session.insert("User.insertOrder", order);
        session.commit();
        session.close();
        return order.getOrderid();
    }

}
