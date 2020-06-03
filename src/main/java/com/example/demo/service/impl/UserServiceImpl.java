package com.example.demo.service.impl;

import com.example.demo.bean.User;
import com.example.demo.dao.UserMapper;
import com.example.demo.service.IUserService;
import com.example.demo.utils.BaseRestResult;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements IUserService {
    @Resource
    private UserMapper mapper;

    @Override
    @Cacheable(value = "user-data")
    public List<User> list(Map<String, String> params){
        return mapper.queryUsers(params);
    }

    @Override
    @Cacheable(value = "user-data",key = "'id_'+#id")
    public User findById(Integer id) {
        return mapper.findUserById(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
    @CachePut(value = "user-data", key = "'id_'+#result.uId")
    public User save(Map<String, String> params) {
        User user = new User();
        user.setuName(params.get("name"));
        user.setuAge(Integer.parseInt(params.get("age")));
        mapper.save(user);
        return user;
    }

    @Override
    @CachePut(value = "user-data", key = "'id_'+#params.get('id')")
    public User updateById(Map<String, String> params) {
        User user = new User();
        user.setuId(Integer.parseInt(params.get("id")));
        user.setuName(params.get("name"));
        user.setuAge(Integer.parseInt(params.get("age")));
        mapper.updateById(user);
        return user;
    }

    @Override
    @CacheEvict(value = "user-data", key = "'id_'+#id")
    public BaseRestResult deleteById(Integer id) {
        int i = mapper.deleteById(id);
        if (i > 0) {
            return new BaseRestResult(0, "success");
        } else {
            return new BaseRestResult(1, "fail");
        }
    }
}