package com.example.demo.thread;

import com.example.demo.config.BeanContext;
import com.example.demo.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

public class RedisPushObjThread implements Runnable {
    private TransferQueue<Map<String,Object>> queue = new LinkedTransferQueue<>();

    private static Logger logger = LoggerFactory.getLogger(RedisPushObjThread.class);

    private static RedisPushObjThread redisPushObjThread = null;

    private RedisUtils redisUtils;

    private boolean isRunning = true;

    public RedisPushObjThread() {
        //new的时候注入需要的bean  多线程中@Autowired不可用，需要自己获取bean对象
        this.redisUtils = BeanContext.getBean(RedisUtils.class);
    }

    @Override
    public void run() {
        while(true){
            synchronized (queue) {
                try {
                    if (!isQueueEmpty()) {
                        Map<String, Object> map = this.takeFromTq();
                        if (map != null) {
                            try {
                                for (Iterator<String> it = map.keySet().iterator(); it.hasNext(); ) {
                                    String key = it.next();
                                    redisUtils.set(key, map.get(key));
                                }
                            } catch (Exception e) {
                                logger.error("setObjCache出错！", e);
                            }
                            map.clear();
                            map = null;
                        }
                    }
                    if (isQueueEmpty()) {
                        waitTq();
                    }
                } catch (InterruptedException e) {
                    logger.error("保存redis出错！", e);
                }
            }
        }
    }

    public static RedisPushObjThread getInstance(){
        if(redisPushObjThread == null){
            redisPushObjThread = new RedisPushObjThread();
        }
        return redisPushObjThread;
    }

    public void addToTq(Map<String,Object> map) throws InterruptedException{
        if(queue != null){
            queue.put(map);
            synchronized(queue) {
                if(!isRunning) {
                    isRunning = true;
                    queue.notifyAll();
                }
            }
        }
    }

    private void waitTq() throws InterruptedException{
        if(isQueueEmpty()){
            synchronized(queue) {
                if(isRunning) {
                    isRunning = false;
                    queue.wait();
                }
            }
        }
    }

    private Map<String,Object> takeFromTq() throws InterruptedException{
        if(!isQueueEmpty()){
            return queue.take();
        }
        return null;
    }

    private boolean isQueueEmpty(){
        return queue == null || (queue != null && queue.size() == 0);
    }


    public static void main(String[] args) {
        RedisPushObjThread t = RedisPushObjThread.getInstance();
        logger.info("start................");
        Map<String,Object> map = new HashMap<>();
        map.put("t1","test1");
        map.put("2","test2");
        try {
            t.addToTq(map);
            logger.info("queue start................");
            t.run();
            logger.info("thread start................");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
