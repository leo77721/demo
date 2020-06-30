package com.example.demo.thread;

import com.sun.org.apache.bcel.internal.generic.NEW;

import java.util.concurrent.*;

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
