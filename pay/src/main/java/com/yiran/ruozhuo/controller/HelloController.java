package com.yiran.ruozhuo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ruozhuo on 2017/1/19.
 */
@Controller
public class HelloController {

    @ResponseBody
    @RequestMapping(value = "/hello")
    private String home() {
        return "Pay Homepage!";
    }

    @ResponseBody
    @RequestMapping(value = "/test")
    private String test(@RequestParam(name = "name", required = false, defaultValue = "World") String name) {
        return "Hello, " + name + "! This is a test page!";
    }

}
