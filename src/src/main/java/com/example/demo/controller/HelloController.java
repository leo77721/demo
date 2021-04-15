package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.HashMap;
@Controller  //注意这里必须为Controller
public class HelloController {
    /**
     * 本地访问内容地址 ：http://localhost:8080/hello
     * @param map
     * @return
     */
    @RequestMapping("/hello")
    public String helloHtml(HashMap<String, Object> map, Model model) {
        model.addAttribute("say","欢迎欢迎,热烈欢迎");
        map.put("hello", "欢迎进入HTML页面");
        return "hello";
    }
}