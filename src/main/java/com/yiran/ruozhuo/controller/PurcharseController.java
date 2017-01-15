package com.yiran.ruozhuo.controller;

import com.yiran.ruozhuo.model.Order;
import com.yiran.ruozhuo.model.User;
import com.yiran.ruozhuo.util.Const;
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
        String openUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + Const.APPID + "&redirect_uri=" + encodedUrl + "&response_type=code&scope=snsapi_base#wechat_redirect";
        response.sendRedirect(openUrl);
        return "nothing";
    }

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public String order(HttpServletRequest request, HttpServletResponse response, Model model) {
        Map<String, ?> map = RequestContextUtils.getInputFlashMap(request);
        System.out.println("-->>order.get.map = " + map);
        User user = (User) map.get("user");
        String goodscode = (String) map.get("goodscode");

        int price = selectGoodsPrice(goodscode);

        model.addAttribute("order", new Order());
        model.addAttribute("goodscode", goodscode);
        model.addAttribute("openid", user.getOpenid());
        model.addAttribute("price", price);
        return "order";
    }

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public String orderDetail(HttpServletRequest request, @ModelAttribute Order order) {
        order.setTotalcost(order.getCount() * order.getPrice());
        System.out.println("-->>order.post.order = " + order);
        insertOrder(order);
        System.out.println("-->>order.post.orderid = " + order.getOrderid());
        return "orderdetail";
    }

    @ResponseBody
    @RequestMapping(value = "/pay")
    public String pay(HttpServletRequest request, HttpServletResponse response) {
        return "pay";
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

    private int selectGoodsPrice(String goodscode) {
        SqlSession session = sqlSessionFactory.openSession();
        int price = session.selectOne("User.selectGoodsPrice", goodscode);
        session.commit();
        session.close();
        return price;
    }

    private int insertOrder(Order order) {
        SqlSession session = sqlSessionFactory.openSession();
        session.insert("User.insertOrder", order);
        session.commit();
        session.close();
        return order.getOrderid();
    }

}
