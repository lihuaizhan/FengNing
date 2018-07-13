package com.example.tps900.tps900.Utlis;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 项目名称：TVM_ST
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2017/12/15 14:46
 * 修改人：zxh
 * 修改时间：2017/12/15 14:46
 * 修改备注：
 */

/**
 corePoolSize： 线程池维护线程的最少数量
 maximumPoolSize：线程池维护线程的最大数量
 keepAliveTime： 线程池维护线程所允许的空闲时间
 unit： 线程池维护线程所允许的空闲时间的单位
 workQueue： 线程池所使用的缓冲队列
 handler： 线程池对拒绝任务的处理策略
 */
public class ThreadPoolProxy {
    private ThreadPoolExecutor mExecutor;
    private int corePoolSize;
    private int maximumPoolSize;
    private long keepAliveTime;

    public ThreadPoolProxy(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.keepAliveTime = keepAliveTime;
    }
    //双重锁机制
    public ThreadPoolExecutor initThreadPoolExecutor() {
        if (mExecutor == null) {
            synchronized (ThreadPoolProxy.class) {
                if (mExecutor == null) {
                    BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<Runnable>();
                    ThreadFactory threadFactory = Executors.defaultThreadFactory();
                    RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();

                    mExecutor = new ThreadPoolExecutor(corePoolSize,
                            maximumPoolSize,
                            keepAliveTime,
                            TimeUnit.SECONDS,
                            workQueue,
                            threadFactory,
                            handler);
                }
            }
        }
        return mExecutor;
    }
    //试行任务
    public void execute(Runnable task) {
        initThreadPoolExecutor();
        mExecutor.execute(task);
    }

    //移除任务
    public void remove(Runnable task) {
        initThreadPoolExecutor();
        mExecutor.remove(task);
    }

    //提交任务(Future+execute(Runnable task)
    public Future<?> submit(Runnable task) {
        initThreadPoolExecutor();
        return mExecutor.submit(task);
    }
}
