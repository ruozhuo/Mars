package com.yiran.ruozhuo.controller;

import com.yiran.ruozhuo.model.User;
import com.yiran.ruozhuo.service.WechatService;
import com.yiran.ruozhuo.util.AccessTokenUtil;
import com.yiran.ruozhuo.util.CheckUtil;
import com.yiran.ruozhuo.util.Const;
import com.yiran.ruozhuo.util.HttpUtil;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.URLEncoder;

/**
 * Created by ruozhuo on 2017/1/6.
 */
@Controller
public class WechatController {

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

    private static Logger log = Logger.getLogger(WechatController.class);

    @Autowired
    WechatService wechatService;

    @ResponseBody
    @RequestMapping(value = "/wechat", method = RequestMethod.GET)
    public void wechatService(PrintWriter out, HttpServletResponse response,
                              @RequestParam(value = "signature", required = false) String signature,
                              @RequestParam String timestamp,
                              @RequestParam String nonce,
                              @RequestParam String echostr) {
        log.info("signature：" + signature + "\ntimestamp：" + timestamp + "\nnonce：" + nonce + "\nechostr：" + echostr);
        if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
            log.info(echostr);
            out.print(echostr);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/wechat", method = RequestMethod.POST)
    public void wechatServicePost(PrintWriter out, HttpServletRequest request, HttpServletResponse response) {
        String responseMessage = wechatService.processRequest(request);
        out.print(responseMessage);
        out.flush();
    }

    @ResponseBody
    @RequestMapping(value = "/wechat/gettoken")
    public String getToken() {
        return AccessTokenUtil.getAccessToken();
    }

    @ResponseBody
    @RequestMapping(value = "/wechat/view")
    public String wechatView() {
        return "View Test Page!";
    }

    @ResponseBody
    @RequestMapping(value = "/wechat/redirect", method = RequestMethod.GET)
    public String wechatRedirect(HttpServletResponse response) throws IOException {
        String encodedUrl = URLEncoder.encode(Const.REDIRECT_URI, "utf-8");
        String openUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + Const.APPID + "&redirect_uri=" + encodedUrl + "&response_type=code&scope=snsapi_base#wechat_redirect";
        System.out.println(openUrl);
        response.sendRedirect(openUrl);
        return "/wechat/oauth";
    }

    @RequestMapping(value = "/wechat/oauth", method = RequestMethod.GET)
    public static String oauth(HttpServletRequest request, HttpServletResponse response, RedirectAttributes attributes) throws IOException {
        String code = request.getParameter("code");
        System.out.println("-->>wecaht/oauth.code = " + code);
        String goodscode = request.getParameter("goodscode");
        System.out.println("-->>oauth.goodscode = " + goodscode);
        String URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + Const.APPID + "&secret=" + Const.APPSECRET + "&code=" + code + "&grant_type=authorization_code";
        String jsonStr = HttpUtil.sendGet(URL);
        JSONObject jsonObject = new JSONObject(jsonStr);

        String access_token = jsonObject.get("access_token").toString();
        String openid = jsonObject.get("openid").toString();

        System.out.println("-->>wechat.oauth.jsonObject.toString() = " + jsonObject.toString());
        System.out.println("-->>wechat.oauth.access_token = " + access_token);
        System.out.println("-->>wechat.oauth.openid = " + openid);
        String lang = "en";
        String userInfoURL = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openid + "&lang=" + lang;
        jsonStr = HttpUtil.sendGet(userInfoURL);
        System.out.println("jsonStr = " + jsonStr);

        jsonObject = new JSONObject(jsonStr);
        User user = new User();
        user.setOpenid(jsonObject.getString("openid"));
        user.setNickname(jsonObject.getString("nickname"));
        user.setSex(jsonObject.getInt("sex"));
        user.setCity(jsonObject.getString("city"));
        user.setProvince(jsonObject.getString("province"));
        user.setCountry(jsonObject.getString("country"));
        user.setHeadimgurl(jsonObject.getString("headimgurl"));
        user.setPrivilege(jsonObject.get("privilege").toString());


        if (!checkUserExists(user.getOpenid())) {
            insertUser(user);
            System.out.println("-->>After insert, use.getUserid = " + user.getUserid());
        }
        attributes.addFlashAttribute("user", user);
        attributes.addFlashAttribute("goodscode", goodscode);
        return "redirect:/order";
    }

    private static int insertUser(User user) {
        SqlSession session = sqlSessionFactory.openSession();
        session.insert("User.insertUser", user);
        session.commit();
        session.close();
        return user.getUserid();
    }

    private static boolean checkUserExists(String openid) {
        SqlSession session = sqlSessionFactory.openSession();
        int count = session.selectOne("User.checkUserExists", openid);
        session.commit();
        session.close();
        if (count > 0) {
            System.out.println("-->>user already exists!");
        } else {
            System.out.println("-->>user doesn't exist!");
        }
        return count > 0;
    }

}
