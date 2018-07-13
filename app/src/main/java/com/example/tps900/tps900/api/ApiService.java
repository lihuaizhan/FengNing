package com.example.tps900.tps900.api;

import com.example.tps900.tps900.MenberBean.ReQueryMemberInfo;
import com.example.tps900.tps900.Utlis.Updata_Bean;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * 项目名称：PDAXianShang
 * 类名称：
 * 类描述：生名所有接口的类  并返回每个接口的call对象
 * 创建人：zxh
 * 创建时间：2017/11/12 14:20
 * 修改人：zxh
 * 修改时间：2017/11/12 14:20
 * 修改备注：
 */

/**
 * 接口管理类
 * 声明接口的请求方式 格式:get/post +请求的方法名
 *
 * @author zxh
 * @Query 拼接参数的关键字
 */
public interface ApiService {
    /**
     * 获取更新apk
     *
     * @param signature
     * @return
     */
    @GET("/versionUpdate/vsiname?")
    Call<Updata_Bean> getUpdataAPP(@Query("names") String signature);

    /**
     * @param OpenId    登陆开放平台生成的OpenId
     * @param Signature 签名=md5(OpenId+Secret+Timestamp+data),格式:32位md5大写加密
     * @param TimeStamp 发起请求的时间戳,查看标准timestamp
     * @return
     */
    @Headers("xiangyao:xiangyao")
    @GET("OpenApi/Get_MemberInfo")
    Observable<String> Get_MemberInfo(@Query("openId") String OpenId, @Query("signature") String Signature, @Query("timestamp") long TimeStamp);

    /**
     * @param OpenId    登陆开放平台生成的OpenId
     * @param Signature 签名=md5(OpenId+Secret+Timestamp+data),格式:32位md5大写加密
     * @param TimeStamp 发起请求的时间戳,查看标准timestamp
     * @return
     */
    @GET("/OpenApi/Consume?")
    Call<ReQueryMemberInfo> MemberConsume(@Query("OpenId") String OpenId, @Query("Signature") String Signature, @Query("TimeStamp") String TimeStamp);


    /**
     * @param OpenId    登陆开放平台生成的OpenId
     * @param Signature 签名=md5(OpenId+Secret+Timestamp+data),格式:32位md5大写加密
     * @param TimeStamp 发起请求的时间戳,查看标准timestamp
     * @return
     */
    @GET("/OpenApi/ReturnGoods?")
    Call<ReQueryMemberInfo> ReturnGoods(@Query("OpenId") String OpenId, @Query("Signature") String Signature, @Query("TimeStamp") String TimeStamp);

}
