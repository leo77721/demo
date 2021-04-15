package com.example.demo.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SystemThreadRunner {
    //
    //private static ExecutorService sysPool = Executors.newCachedThreadPool();
    private static ExecutorService sysPool = new ThreadPoolExecutor(3, 100, 60L, TimeUnit.SECONDS,new SynchronousQueue<Runnable>()) ;

    public static void run() {
        sysPool.execute(RedisPushObjThread.getInstance());
    }

    public static ExecutorService getSystemThreadPool(){
        return sysPool;
    }
}
