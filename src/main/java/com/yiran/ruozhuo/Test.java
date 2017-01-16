package com.yiran.ruozhuo;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by ruozhuo on 2017/1/16.
 */
public class Test {

    public static void main(String[] args) throws IOException {
        URL url = new URL("http://127.0.0.1:8080/sendXmlAndReturnXml.do");
        String xml = "<aaa><ddd>cccccc客户端请求的xml数据cccccccc</ddd></aaa>";
        URLConnection conn = null;
        conn = url.openConnection();
        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Length", Integer.toString(xml.length()));
        conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
        OutputStream ops = conn.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(ops, "utf-8");
        osw.write(xml);
        osw.flush();
        osw.close();

        //发送成功后，获取服务器的响应xml串：
        StringBuffer sb = new StringBuffer();
        String line = "";
        InputStream is = conn.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));//三层包装
        while ((line = br.readLine()) != null) {
            sb.append(line+ "\r\n");
        }
        System.out.println(sb.toString());
    }

}
