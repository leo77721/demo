package com.example.demo.service.impl;

import ch.qos.logback.core.util.FileUtil;
import com.example.demo.bean.User;
import com.example.demo.dao.UserMapper;
import com.example.demo.service.IUserService;
import com.example.demo.thread.RedisPushObjThread;
import com.example.demo.utils.BaseRestResult;
import com.example.demo.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements IUserService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private RedisUtils redisUtils;
    @Resource
    private UserMapper mapper;

    @Override
    @Cacheable(value = "user-data", key = "searchResult", unless = "#result == null || #result.size() == 0")
    public List<User> list(Map<String, String> params) {
        return mapper.queryUsers(params);
    }

    @Override
    @Cacheable(value = "user-data", key = "'id_'+#id", unless = "#result == null ")
    public User findById(Integer id) {
        Map map = new HashMap();
        map.put("id", id);
        map.put("state", true);
        return mapper.findUserById(map);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
    @CachePut(value = "user-data", key = "'id_'+#result.uId")
    public User save(Map<String, String> params) {
        User user = new User();
        user.setuName(params.get("name"));
        user.setuAge(Integer.parseInt(params.get("age")));
        user.setState(true);
        mapper.save(user);

        RedisPushObjThread thread = RedisPushObjThread.getInstance();
        Map<String, Object> map = new HashMap<>();
        map.put("t1", "test1");
        map.put("t2", "test2");
        try {
            thread.addToTq(map);
            logger.info("queue start................");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    @CachePut(value = "user-data", key = "'id_'+#params.get('id')")
    public User updateById(Map<String, String> params) {
        User user = new User();
        user.setuId(Integer.parseInt(params.get("id")));
        user.setuName(params.get("name"));
        user.setuAge(Integer.parseInt(params.get("age")));
        user.setState(Boolean.parseBoolean(params.get("state")));
        mapper.updateById(user);
        return user;
    }

    @Override
    @CacheEvict(value = "user-data", key = "'id_'+#id")
    public BaseRestResult deleteById(Integer id) {
        //FileUtil.class.getClassLoader().getResource("yourfile.txt").getPath();
        int i = mapper.deleteById(id);
        if (i > 0) {
            return new BaseRestResult(0, "success");
        } else {
            return new BaseRestResult(1, "fail");
        }
    }
    /**
     * 缓存击穿，同步块 + 校验
     * 第一种方式
     * 缺点 阻塞其他线程
     *
     * 其他方式 互斥锁   redis.setnx()
     */
    public static volatile Object lockObj = new Object();
    @Override
    public User getUserByRedis(String key) {
        User value = (User) redisUtils.get("user-data::id_" + key);
        if (value == null) {
            synchronized (lockObj) {
                value = (User) redisUtils.get("user-data::id_" + key);
                if (value == null) {
                    value = this.findById(Integer.parseInt(key));
                    redisUtils.set("user-data::id_" + key, value, 1000);
                }
            }
        }
        return value;
    }
}