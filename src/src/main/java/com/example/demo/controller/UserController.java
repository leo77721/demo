package com.example.demo.controller;

import com.example.demo.bean.User;
import com.example.demo.service.IUserService;
import com.example.demo.utils.BaseRestResult;
import com.example.demo.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/api/user")
public class UserController {
    private final static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Resource
    private IUserService userService;
    @Resource
    private RedisUtils redisUtils ;

    @RequestMapping("test2/{id}")
    @ResponseBody
    public User test2(@PathVariable Integer id) {
        User user = userService.findById(id);
        return user;
    }

    @RequestMapping("test1")
    @ResponseBody
    public User test1(@RequestParam("id") Integer id, @RequestParam("name") String name) {
        User resUser = userService.findById(id);
        return resUser;
    }


    @RequestMapping("test3")
    @ResponseBody
    public User test3(@RequestBody User user) {
        Integer id = user.getuId();
        User resUser = userService.findById(id);
        System.out.println(resUser.toString());
        return resUser;
    }
    @RequestMapping("/list")
    @ResponseBody
    public List<User> list(@RequestBody Map<String, String> params) {
        List<User> users = userService.list(params);
        return users;
    }

    @RequestMapping("/find")
    @ResponseBody
    public User find(@RequestBody Map<String, String> params) {
        Integer id = Integer.parseInt(params.get("id"));
        User user = userService.findById(id);
        return user;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public BaseRestResult delete(@RequestBody Map<String, String> params) {
        Integer id = Integer.parseInt(params.get("id"));
        return userService.deleteById(id);
    }
    @RequestMapping("/save")
    @ResponseBody
    public User save(@RequestBody Map<String, String> params) {
        return userService.save(params);
    }


    @RequestMapping("/update")
    @ResponseBody
    public User update(@RequestBody Map<String, String> params) {
        return userService.updateById(params);
    }

    @RequestMapping("/getUserByRedis")
    @ResponseBody
    public User getUserByRedis(@RequestBody Map<String, String> params) {
        String id = params.get("id");
        User user = userService.getUserByRedis(id);
        return user;
    }


    @RequestMapping("/saveSplitTable")
    @ResponseBody
    public BaseRestResult saveSplitTable(@RequestBody Map<String, String> params) {
        try {
            userService.saveSplitTable(params);
        } catch (Exception e) {
            return  new BaseRestResult(0,"失敗");
        }
        return  new BaseRestResult(1,"成功");
    }

    @RequestMapping("/findUserByParam")
    @ResponseBody
    public List<User> findUserByParam(@RequestBody Map<String, String> params) {
        List<User> list = new ArrayList<>();
        try {
            list = userService.findUserByParam(params);
        } catch (Exception e) {
            return  list ;
        }
        return  list;
    }
}