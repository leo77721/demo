package com.example.demo.dao;

import com.example.demo.bean.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

    List<User> queryUsers(Map<String,String> params);

    User findUserById(Map<String,Object> params);

    int save(User user);

    int updateById(User user);

    int deleteById(Integer id);

    void createTable(Map<String,Object> params);

    int existTable(Map<String,Object> params);

    int saveSplitTable(Map<String,Object> params);

    List<User> findUserByParam(Map<String,Object> params);

}
