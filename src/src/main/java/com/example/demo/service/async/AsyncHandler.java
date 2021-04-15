package com.example.demo.service.async;

import com.example.demo.thread.RedisPushObjThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.Future;

/**
 * spring boot @Async 通常单独类调用
 * service 自己调用异步方法不可用
 */
@Component
public class AsyncHandler {

    private static Logger logger = LoggerFactory.getLogger(AsyncHandler.class);

    @Async
    public Future transferImageQueue(Map<String,Object> map) {
        try {
            RedisPushObjThread.getInstance().addToTq(map);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
        return new AsyncResult<>("success");
    }
}
