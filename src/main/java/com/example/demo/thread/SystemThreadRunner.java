package com.example.demo.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SystemThreadRunner {
    private static ExecutorService sysPool = Executors.newCachedThreadPool();

    public static void run() {
        sysPool.execute(RedisPushObjThread.getInstance());
    }

    public static ExecutorService getSystemThreadPool(){
        return sysPool;
    }
}
