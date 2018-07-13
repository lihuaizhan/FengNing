package com.example.tps900.tps900.Utlis;

import android.os.Handler;

/**
 * 项目名称：TVM_ST
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2017/12/15 14:48
 * 修改人：zxh
 * 修改时间：2017/12/15 14:48
 * 修改备注：
 */

public class ThreadPoolUtils {
    //在非UI线程中执行
    public static void runTaskInThread(Runnable task){
        ThreadPoolFactory.getCommonThreadPool().execute(task);
    }

    //在UI线程中执行
    private static Handler handler = new Handler();
    public static void runTaskInUIThread(Runnable task){
        handler.post(task);
    }
}
