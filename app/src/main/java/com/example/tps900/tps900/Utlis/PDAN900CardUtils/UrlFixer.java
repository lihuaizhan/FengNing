package com.example.tps900.tps900.Utlis.PDAN900CardUtils;

import com.sz1card1.sdk.SdkXmlParse;

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
 * 创建时间：2018-06-24 0:47
 * 修改人：zxh
 * 修改时间：2018-06-24 0:47
 * 修改备注：
 */

public class UrlFixer implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response;
        //获取request的创建者builder
        Request.Builder builder = request.newBuilder();
        String header = request.header("xiangyao");
        HttpUrl newBaseUrl;
        String bb = new SdkXmlParse().getXmlController("Get_MemberInfo");
        String aa = "http://openapi.1card1.cn/" + bb + "/Get_MemberInfo";

        if (header != null) {
            newBaseUrl = HttpUrl.parse(aa);
            //从request中获取原有的HttpUrl实例oldHttpUrl
            HttpUrl oldHttpUrl = request.url();
            //重建新的HttpUrl，修改需要修改的url部分

            HttpUrl newFullUrl = oldHttpUrl
                    .newBuilder()
                    .scheme(newBaseUrl.scheme())
                    .host(newBaseUrl.host())
                    .port(newBaseUrl.port())
                    .build();
            response = chain.proceed(builder.url(newFullUrl).build());
            return response;
        } else {
            response = chain.proceed(request);
        }
        return response;
    }
}
