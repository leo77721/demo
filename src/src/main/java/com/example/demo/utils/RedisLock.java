package com.example.demo.utils;

import com.example.demo.config.BeanContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Created by miracle on 2016/9/22.
 */
public class RedisLock implements Lock {

    private static StringRedisTemplate redisStringTemplate;

    private static final Logger logger = LoggerFactory.getLogger(RedisLock.class);

    // lock flag stored in redis
    private static final String LOCKED = "TRUE";

    // timeout(ms)
    private static final long TIME_OUT = 30000;

    // lock expire time(s)
    public static final int EXPIRE = 60;

    // private Jedis jedis;
    private String key;

    // state flag
    private volatile boolean locked = false;

    private static ConcurrentMap<String, RedisLock> map = new ConcurrentHashMap<>();

    public RedisLock(String key) {
        this.key = "_LOCK_" + key;
    }

    private StringRedisTemplate getStringRedisTemplate(){
        if(redisStringTemplate == null) {
            redisStringTemplate = (StringRedisTemplate) BeanContext.getBean("redisStringTemplate");;
        }
        return redisStringTemplate;
    }

    public static RedisLock getInstance(String key) {
        return map.getOrDefault(key, new RedisLock(key));
    }

    public void lock(long timeout) {
        long nano = System.nanoTime();
        timeout *= 1000000;
        final Random r = new Random();
        try {
            while ((System.nanoTime() - nano) < timeout) {
                if (getStringRedisTemplate().getConnectionFactory().getConnection().setNX(key.getBytes(), LOCKED.getBytes())) {
                    getStringRedisTemplate().expire(key, EXPIRE, TimeUnit.SECONDS);
                    locked = true;
                    logger.debug("add RedisLock[" + key + "].");
                    break;
                }
                Thread.sleep(3, r.nextInt(500));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void unlock() {
        if (locked) {
            logger.debug("release RedisLock[" + key + "].");
            getStringRedisTemplate().delete(key);
        }
        getStringRedisTemplate().getConnectionFactory().getConnection().close();
    }

    @Override
    public void lock() {
        lock(TIME_OUT);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public Condition newCondition() {
        return null;
    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }
}
