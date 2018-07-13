package com.example.tps900.tps900.Utlis.PDAN900CardUtils;

import android.util.Log;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 项目名称：PDA_FengNing
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2018-06-23 23:41
 * 修改人：zxh
 * 修改时间：2018-06-23 23:41
 * 修改备注：
 */

public class HttpLogInrerrator implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl url = request.url();

        Log.i("xiangyao", "intercept: " + url.toString());


        return chain.proceed(request);
    }
}
