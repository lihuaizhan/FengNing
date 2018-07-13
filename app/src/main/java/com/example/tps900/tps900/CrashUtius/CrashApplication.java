package com.example.tps900.tps900.CrashUtius;

import android.app.Application;

/**
 * 项目名称：TPS613_WesternBrigade
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2017/3/8 15:52
 * 修改人：zxh
 * 修改时间：2017/3/8 15:52
 * 修改备注：
 */

public class CrashApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
    }
}
