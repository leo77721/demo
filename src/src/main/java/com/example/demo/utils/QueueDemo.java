/*
 * Copyright 2020 tu.cn All right reserved. This software is the
 * confidential and proprietary information of tu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tu.cn
 */
package com.example.demo.utils;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 * @date 2020/12/4 13:27
 */
public class QueueDemo {
    public static void main(String[] args) {
        final CountDownLatch latch = new CountDownLatch(2);
        Queue<String> queue = new LinkedList<String>();
        for (int i = 0; i < 200; i++) {
            queue.offer("第  " + (i + 1) + "  条数据");
        }
        long start = System.currentTimeMillis();

        ExecutorService sysPool = new ThreadPoolExecutor(3, 100, 60L, TimeUnit.SECONDS,new SynchronousQueue<Runnable>()) ;

        for(int i = 0 ;i<5; i++){
            sysPool.execute(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        if (queue.size() > 0) {
                            System.out.println(Thread.currentThread().getName() + "从队列中获取信息:" + queue.poll());
                        } else {
                            break;
                        }
                    }
                    latch.countDown();
                }
            });
        }

        /*sysPool.execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (queue.size() > 0) {
                        System.out.println(Thread.currentThread().getName() + "从队列中获取信息:" + queue.poll());
                    } else {
                        break;
                    }
                }
                latch.countDown();
            }
        });*/

        /*new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (queue.size() > 0) {
                        System.out.println(Thread.currentThread().getName() + "从队列中获取信息:" + queue.poll());
                    } else {
                        break;
                    }
                }
                latch.countDown();
            }
        }, "队列线程-1").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (queue.size() > 0) {
                        System.out.println(Thread.currentThread().getName() + "从队列中获取信息:" + queue.poll());
                    } else {
                        break;
                    }
                }
                latch.countDown();
            }
        }, "队列线程-2").start();*/
        long end = System.currentTimeMillis();
        try {
            latch.await();
            System.out.println(end - start);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}