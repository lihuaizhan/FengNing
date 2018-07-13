package com.example.tps900.tps900.Utlis;

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

public class ThreadPoolFactory {
    private static ThreadPoolProxy commonThreadPool;

    public static final int Common_CORE_POOL_SIZE = 5;
    public static final int Common_MAX_POOL_SIZE = 5;
    public static final int Common_KEEP_LIVE_TIME = 1; //存活时间

    //单例模式
    public static ThreadPoolProxy getCommonThreadPool() {
        if (commonThreadPool == null) {
            synchronized (ThreadPoolFactory.class) {
                if (commonThreadPool == null) {
                    commonThreadPool = new ThreadPoolProxy(Common_CORE_POOL_SIZE, Common_MAX_POOL_SIZE, Common_KEEP_LIVE_TIME);
                }
            }
        }
        return commonThreadPool; //返回所要的线程池
    }
}
