package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.HashMap;
@Controller  //注意这里必须为Controller
public class ReactController {
    /**
     * 本地访问内容地址 ：http://localhost:8080/hello
     * @param map
     * @return
     */
    @RequestMapping("/react")
    public String helloHtml(HashMap<String, Object> map, Model model) {
        return "react/index";
    }
}