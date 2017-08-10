package com.shiro.common.threadPool;

import java.util.concurrent.*;

/**
 * Executors 返回的线程池对象的弊端如下:
 * 1)FixedThreadPool 和 SingleThreadPool:
 * 允许的请求队列长度为 Integer.MAX_VALUE，可能会堆积大量的请求，从而导致 OOM。
 * 2)CachedThreadPool 和 ScheduledThreadPool:
 * 允许的创建线程数量为 Integer.MAX_VALUE，可能会创建大量的线程，从而导致 OOM。
 *
 * Created by wuyiming on 2017/7/18.
 */
public class APIThreadPool {

    /**
     * 构造一个缓冲功能的线程池，
     * 配置corePoolSize=0，maximumPoolSize=Integer.MAX_VALUE，keepAliveTime=60s,
     * 以及一个无容量的阻塞队列 SynchronousQueue，因此任务提交之后，将会创建新的线程执行，线程空闲超过60s将会销毁
     * @return
     */
    private ExecutorService newCachedThreadPool = new ThreadPoolExecutor(
            0,
            Integer.MAX_VALUE,
            60L, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>());

    private static APIThreadPool apiThreadPool;

    private static APIThreadPool init(){
        return new APIThreadPool();
    }

    public static ExecutorService getNewCachedThreadPool(){
        if (apiThreadPool == null){
            apiThreadPool = init();
        }
        return apiThreadPool.newCachedThreadPool;
    }

    /**
     * 构造一个固定线程数目的线程池，配置的corePoolSize与maximumPoolSize大小相同，
     * 同时使用了一个无界LinkedBlockingQueue存放阻塞任务，因此多余的任务将存在再阻塞队列，
     * 不会由RejectedExecutionHandler处理
     * @param size
     * @return
     */
    public static ExecutorService newFixedThreadPool(int size){
        return new ThreadPoolExecutor(size, size,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
    }
}
