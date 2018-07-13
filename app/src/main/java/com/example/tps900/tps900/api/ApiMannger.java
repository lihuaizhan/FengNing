package com.example.tps900.tps900.api;

import com.example.tps900.tps900.Utlis.Constant;
import com.example.tps900.tps900.Utlis.HttpLogIntercepter;
import com.example.tps900.tps900.Utlis.PDAN900CardUtils.HttpLogInrerrator;
import com.example.tps900.tps900.Utlis.PDAN900CardUtils.UrlFixer;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 项目名称：PDAXianShang
 * 类名称：
 * 类描述：ApiMannger 主要是提供一个实例化Apiservice对象的一个类
 * 创建人：zxh
 * 创建时间：2017/11/12 14:35
 * 修改人：zxh
 * 修改时间：2017/11/12 14:35
 * 修改备注：
 *
 * @author zxh
 */

public class ApiMannger {
    /**
     * 1 获取retrofit对象
     * 2 设置主域名
     * 3 执行
     * 4 获取APi接口实现类的实例对象
     * 返回ApiService对象
     *
     * @return
     */
    public static ApiService getApiService() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new HttpLoggingInterceptor(new HttpLogIntercepter()));
        OkHttpClient okHttpClient = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + Constant.UPdate_IP_ADDRESS)
                .addConverterFactory(GsonConverterFactory.create())

                .client(okHttpClient)
                .build();
        return retrofit.create(ApiService.class);

    }


    public static ApiService getMemberApiService() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new HttpLogInrerrator());
        builder.addInterceptor(new UrlFixer());
        OkHttpClient okHttpClient = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://openapi.1card1.cn/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava

                .client(okHttpClient)
                .build();
        return retrofit.create(ApiService.class);

    }
}
