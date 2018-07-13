package com.example.tps900.tps900.Bean;

/**
 * Created by zxh on 2016/12/17.
 * @author zxh
 */
//支付宝支付解析返回结果
public class Alipay_Entity {

    public static Alipay_trade_query_response alipay_trade_query_response;
    private String sign;

    public Alipay_trade_query_response getAlipay_trade_query_response() {
        return alipay_trade_query_response;
    }

    public void setAlipay_trade_query_response(Alipay_trade_query_response alipay_trade_query_response) {
        Alipay_Entity.alipay_trade_query_response = alipay_trade_query_response;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
