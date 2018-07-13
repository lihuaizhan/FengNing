package com.example.tps900.tps900.Utlis;


import android.util.Log;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 项目名称：MyApplication
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2017/11/18 18:06
 * 修改人：zxh
 * 修改时间：2017/11/18 18:06
 * 修改备注：
 * @author zxh
 */

public class HttpLogIntercepter implements HttpLoggingInterceptor.Logger {
    @Override
    public void log(String message) {
        Log.i("zxh",message);
    }
}
