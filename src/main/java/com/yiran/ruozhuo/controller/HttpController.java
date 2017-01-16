package com.yiran.ruozhuo.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by ruozhuo on 2017/1/16.
 */
@Controller
public class HttpController {

    @RequestMapping("/sendXmlAndReturnXml.do")
    public void sendXmlAndReturnXml(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取请求的xml
        System.out.println("请求的xml数据为：\n" + getXmlFromRequest(request));
        //服务端响应xml数据
        response.getWriter().print("<aaa><ddd>bbbccc Server return response xml data, can be got from client</ddd></aaa>");
    }

    //从HTTP请求中取出请求的XML
    private String getXmlFromRequest(HttpServletRequest request)throws IOException {
        String reqXml = "";
        reqXml = request.getQueryString(); //GET请求
        if (StringUtils.isBlank(reqXml)) { //POST请求
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            reqXml = sb.toString();
        }
        return reqXml;
    }

}
