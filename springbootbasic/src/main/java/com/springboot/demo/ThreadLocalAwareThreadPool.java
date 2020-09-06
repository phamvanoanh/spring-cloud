package com.springboot.demo;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadLocalAwareThreadPool extends ThreadPoolExecutor {

    public ThreadLocalAwareThreadPool(
            int corePoolSize, int maximumPoolSize,
            long keepAliveTime, TimeUnit unit,
            BlockingQueue<Runnable> workQueue) {

        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    protected void afterExecute(Runnable runnable, Throwable throwable) {
        /* Call remove method for each ThreadLocal object of given thread*/
    }
}