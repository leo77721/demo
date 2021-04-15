package com.example.demo.service;

import com.example.demo.bean.User;
import com.example.demo.utils.BaseRestResult;

import java.util.List;
import java.util.Map;

public interface IUserService {

    List<User> list(Map<String, String> params);

    User findById(Integer id);

    User save(Map<String, String> params);

    User updateById(Map<String, String> params);

    BaseRestResult deleteById(Integer id);

    User getUserByRedis(String key);

    void saveSplitTable(Map<String, String> params) throws Exception;

    List<User> findUserByParam(Map<String, String> params) throws Exception;
}
