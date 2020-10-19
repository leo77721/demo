package com.example.demo.controller;

import com.example.demo.bean.User;
import com.example.demo.utils.BaseRestResult;
import com.example.demo.utils.JWTUtils;
import com.example.demo.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
@Controller
@RequestMapping("/api")
public class LoginController {
    @Autowired
    private RedisUtils redisUtils ;

    @RequestMapping("/login")
    @ResponseBody
    public BaseRestResult login(@RequestBody Map<String, String> params){
        String username = params.get("name");
        String userId = params.get("id");
        String password = params.get("password");
        User user = new User();
        user.setuId(Integer.parseInt(userId));
        user.setuName(username);
        user.setPassword(password);
        //验证账户
        String token = JWTUtils.createToken(userId,userId,user);
        redisUtils.set("token", token);

        return new BaseRestResult(0, redisUtils.get("token").toString());
    }
}
