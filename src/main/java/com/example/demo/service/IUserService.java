package com.example.demo.service;

import com.example.demo.bean.User;
import com.example.demo.utils.BaseRestResult;

import java.util.List;
import java.util.Map;

public interface IUserService {

    public List<User> list(Map<String, String> params);

    public User findById(Integer id);

    public User save(Map<String, String> params);

    public User updateById(Map<String, String> params);

    public BaseRestResult deleteById(Integer id);

    public User getUserByRedis(String key);
}
