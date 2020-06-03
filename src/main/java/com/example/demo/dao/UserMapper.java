package com.example.demo.dao;

import com.example.demo.bean.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

    List<User> queryUsers(Map<String,String> params);

    User findUserById(Integer id);

    int save(User user);

    int updateById(User user);

    int deleteById(Integer id);
}
