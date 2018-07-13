package com.example.tps900.tps900.Utlis;


import android.util.Log;

import com.godfery.pay.ErrUtils;
import com.godfery.pay.PayConstanse;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.util.TextUtils;

/**
 * Created by zxh on 2016/11/16.
 *
 * @author zxh
 */
public class AliAndWePayMent {
    public static SyncHttpClient httpClient;
    public static String WeChatData = "";
    public static String RefundWeChatData = "";
    public static String AlipayState = "";
    public static String RefundAlipayData = "";

    /**
     * @author zxh 修改人: 时间 : 2017年5月8日15:21:50 方法说明:支付宝支付
     */
    public static String PayInfo(String PaymentCode, String Money, final String Number) {

        httpClient = new SyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("auth_code", PaymentCode);
        params.put("total_amount", Money);
        params.put("out_trade_no", Number);
        params.put("subject", PayConstanse.subject);
        params.put("terminal_id", PayConstanse.terminal_id);
        params.put("goods_detail", PayConstanse.goods_detail);
        params.put("appId", PayConstanse.appId);
        params.put("charset", PayConstanse.charset);
        params.put("AlipayName", PayConstanse.AlipayName);
        params.put("Pid", PayConstanse.Pid);
        httpClient.post(PayConstanse.AlipayUrl, params, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, Header[] arg1, String data) {
                Log.e("payinfo----1- -->", data);
                if (!TextUtils.isEmpty(data)) {
                    AlipayState = getPayInfo(Number);

                }
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
                AlipayState = arg2 + arg3;
            }
        });
        return AlipayState;
    }


    public static String getPayInfo(String OderID) {
        httpClient = new SyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("out_trade_no", OderID);
        params.put("temp", PayConstanse.temp);
        params.put("appId", PayConstanse.appId);
        params.put("charset", PayConstanse.charset);
        params.put("AlipayName", PayConstanse.AlipayName);
        httpClient.post(PayConstanse.AlipayUrl, params, new TextHttpResponseHandler() {

            @Override
            public void onSuccess(int arg0, Header[] arg1, String data) {
                Log.e("payinfo----2 -->", data);
                if (!TextUtils.isEmpty(data)) {
                    AlipayState = data;
                }
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, String arg2, Throwable throwable) {
                AlipayState = arg2 + throwable.getMessage().toString().trim();
            }
        });
        return AlipayState;
    }

    /**
     * @author zxh 修改人: 时间 :2017年5月8日16:40:14 方法说明: 支付宝退款
     */
    public static String RefundPayInfo(final String Refund_OderID, String Refund_PayID, String Refund_Money) {
        httpClient = new SyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("trade_no", Refund_PayID);
        params.put("refund_amount", Refund_Money);
        params.put("temp", "1");
        params.put("appId", PayConstanse.appId);
        params.put("charset", "utf-8");
        params.put("AlipayName", PayConstanse.AlipayName);
        params.put("terminal_id", "pos001");
        httpClient.post(PayConstanse.AlipayUrl, params, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, Header[] arg1, String data) {

                if (!TextUtils.isEmpty(data)) {
                    RefundAlipayData = getRefundPayInfo(Refund_OderID);
                    Log.e("支付宝退款记录", RefundAlipayData);

                }

            }

            @Override
            public void onFailure(int arg0, Header[] arg1, String arg2, Throwable throwable) {
                if (throwable.getMessage() != null) {
                    {
                        RefundAlipayData = arg2 + throwable.getMessage().toString().trim();
                    }
                }
                Log.e("退款失败返回结果------------", RefundAlipayData);
            }
        });

        return RefundAlipayData;
    }

    /**
     * @author zxh 修改人: 时间 :2017年5月8日16:40:14 方法说明: 查询支付宝退款结果
     */
    public static String getRefundPayInfo(String Refund_OderID) {
        httpClient = new SyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("out_trade_no", Refund_OderID);
        params.put("temp", "2");
        params.put("appId", PayConstanse.appId);
        params.put("charset", "utf-8");
        params.put("AlipayName", PayConstanse.AlipayName);
        httpClient.post(PayConstanse.AlipayUrl, params, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, Header[] arg1, String data) {

                if (!TextUtils.isEmpty(data)) {
                    RefundAlipayData = data;

                    Log.e("退款返回结果------------", RefundAlipayData);

                }

            }

            @Override
            public void onFailure(int arg0, Header[] arg1, String arg2, Throwable throwable) {
                RefundAlipayData = arg2 + throwable.getMessage().toString().trim();
                Log.e("退款失败返回结果------------", RefundAlipayData);

            }
        });


        return RefundAlipayData;
    }

    /**
     * @author zxh 修改人: 时间 : 2017年5月8日15:21:50 方法说明:微信支付
     */
    public static String WeChat_PayInfo(String PaymentCode, String Money, final String Number) {
        double money = Double.parseDouble(Money);
        double money2 = money * 100;
        int i = (int) money2;
        Money = "" + i;
        httpClient = new SyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("body", "消费");
        params.put("total_fee", Money);
        params.put("auth_code", PaymentCode);
        params.put("out_trade_no", Number);
        params.put("APPID", PayConstanse.APPID);
        params.put("MCHID", PayConstanse.MCHID);
        params.put("sub_mch_id", PayConstanse.sub_mch_id);
        params.put("KEY", PayConstanse.KEY);
        params.put("APPSECRET", PayConstanse.APPSECRET);
        params.put("SSLCERT_PATH", PayConstanse.SSLCERT_PATH);
        params.put("SSLCERT_PASSWORD", PayConstanse.SSLCERT_PASSWORD);
        params.put("device_info", PayConstanse.device_info);
        Log.e("微信参数--------", PayConstanse.WeChatUrl + params.toString());
        httpClient.post(PayConstanse.WeChatUrl, params, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, Header[] arg1, String data) {
                if (!TextUtils.isEmpty(data)) {
                    WeChatData = getWeChat_PayInfo(Number);
                    Log.e("微信请求---", WeChatData);
                }
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
                WeChatData = arg2 + "       " + arg3;
            }
        });
        return WeChatData;
    }

    /**
     * @author zxh 修改人: 时间 : 2017年5月8日15:21:50 方法说明:微信支付结果查询
     */
    public static String getWeChat_PayInfo(String out_trade_no) {
        httpClient = new SyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("out_trade_no", out_trade_no);
        params.put("temp", "2");
        params.put("APPID", PayConstanse.APPID);
        params.put("MCHID", PayConstanse.MCHID);
        params.put("sub_mch_id", PayConstanse.sub_mch_id);
        params.put("KEY", PayConstanse.KEY);
        params.put("APPSECRET", PayConstanse.APPSECRET);
        params.put("SSLCERT_PATH", PayConstanse.SSLCERT_PATH);
        params.put("SSLCERT_PASSWORD", PayConstanse.SSLCERT_PASSWORD);
        Log.e("微信c查询--------", params.toString());
        httpClient.post(PayConstanse.WeChatUrl, params, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, Header[] arg1, String data) {

                if (!TextUtils.isEmpty(data)) {
                    WeChatData = data;
                }
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
                WeChatData = arg2 + "       " + arg3;
                Log.e("微信失败---", WeChatData);

            }
        });
        return WeChatData;
    }

    /**
     * @author zxh 修改人: 时间 : 2017年5月8日15:21:50 方法说明:微信退款
     * 1是交易查询
     */
    public static String Refund_WeChat_PayInfo(String Refund_PayID, final String Refund_OderID, String Refund_Money) {
        httpClient = new SyncHttpClient();
        double money = Double.parseDouble(Refund_Money);
        double money2 = money * 100;
        int i = (int) money2;
        Refund_Money = "" + i;
        RequestParams params = new RequestParams();
        params.put("transaction_id", Refund_PayID);
        params.put("out_trade_no", Refund_OderID);
        params.put("total_fee", Refund_Money);
        params.put("refund_fee", Refund_Money);
        params.put("out_refund_no", Refund_OderID);
        params.put("temp", "1");
        params.put("APPID", PayConstanse.APPID);
        params.put("MCHID", PayConstanse.MCHID);
        params.put("sub_mch_id", PayConstanse.sub_mch_id);
        params.put("KEY", PayConstanse.KEY);
        params.put("APPSECRET", PayConstanse.APPSECRET);
        params.put("SSLCERT_PATH", PayConstanse.SSLCERT_PATH);
        params.put("SSLCERT_PASSWORD", PayConstanse.SSLCERT_PASSWORD);
        params.put("device_info", PayConstanse.device_info);
        Log.e("params", params.toString());
        httpClient.post(PayConstanse.WeChatUrl, params, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, Header[] arg1, String data) {
                if (!TextUtils.isEmpty(data)) {
                    RefundWeChatData = getRefund_WeChat_PayInfo(Refund_OderID);
                }
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
                RefundWeChatData = arg2 + "   " + arg3;
            }
        });
        return RefundWeChatData;
    }

    /**
     * @author zxh 修改人: 时间 : 2017年5月8日16:56:03 方法说明:微信退款结果查询
     * 微信退款成功判断
     * return_code   result_code   refund_status_$n
     * 5是退款查询
     */
    public static String getRefund_WeChat_PayInfo(final String weChat_refund) {
        httpClient = new SyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("out_trade_no", weChat_refund);
        params.put("temp", "5");
        params.put("APPID", PayConstanse.APPID);
        params.put("MCHID", PayConstanse.MCHID);
        params.put("sub_mch_id", PayConstanse.sub_mch_id);
        params.put("KEY", PayConstanse.KEY);
        params.put("APPSECRET", PayConstanse.APPSECRET);
        params.put("SSLCERT_PATH", PayConstanse.SSLCERT_PATH);
        params.put("SSLCERT_PASSWORD", PayConstanse.SSLCERT_PASSWORD);
        Log.e("params2", params.toString());
        httpClient.post(PayConstanse.WeChatUrl, params, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, Header[] arg1, String data) {

                if (!TextUtils.isEmpty(data)) {
                    RefundWeChatData = data;
                }
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, String data, Throwable throwable) {
                RefundWeChatData = data + "   " + throwable;

            }
        });
        return RefundWeChatData;
    }


    /**
     * @param RefundAndPayment      付款还是退款 例如:Payment付款 Refund 退款
     * @param Number                如果是付款的话 填 1或者是2  例如 :1是选择联网获取随机数  2是自定义随机数 不填则是""
     * @param R_AlipayAndR_WaChat   如果是退款的话 例如 :R_Alipay支付宝退款 R_WaChat 微信退款
     * @param PaymentCode           付款码 退款填 ""
     * @param Money                 付款金额  退款填 ""
     * @param Refund_PayID          退款交易单号  如果是付款则填""
     * @param Refund_OderID         退款的单据号 (也就是随机数) 如果是付款则填""
     * @param Refund_Money          退款的金额 如果是付款则填""
     * @param //MachineId           设备ID
     * @param ///Scenicspot_code    景区编码
     * @param //Scenicspot_Password 景区秘钥
     * @param //url                 服务器地址
     * @return 返回成功或者失败的结果
     * @author zxh 修改人: 时间 :2017年5月9日17:45:30 方法说明: 微信支付又或者支付宝支付 或者是微信退款或者是支付宝退款
     * 参数说明:
     */
    public static String PayMentAndRefund(String RefundAndPayment,
                                          String Number, String R_AlipayAndR_WaChat, String PaymentCode, String Money,
                                          String Refund_PayID, final String Refund_OderID, String Refund_Money
    ) {
        String payData = "";
        if ("Payment".equals(RefundAndPayment)) {
            if (PaymentCode.length() == 18) {
                String payNumber = PaymentCode.substring(0, 2);
                if ("28".equals(payNumber)) {//支付宝
                    payData = PayInfo(PaymentCode, Money, Number);
                    return payData;
                } else if ("10".equals(payNumber) || "11".equals(payNumber) || "12".equals(payNumber) || "13".equals(payNumber) || "14".equals(payNumber) || "15".equals(payNumber)) {
                    //微信支付
                    payData = WeChat_PayInfo(PaymentCode, Money, Number);
                    return payData;
                } else {
                    ErrUtils.PaymentCodeErr = "付款码有误";
                    return "-1";
                }
            } else {
                ErrUtils.PaymentCodeErr = "付款码长度有误";
                return "-1";
            }
        } else if ("Refund".equals(RefundAndPayment)) {
            if ("R_Alipay".equals(R_AlipayAndR_WaChat)) {
                //支付宝退款
                payData = AliAndWePayMent.RefundPayInfo(Refund_OderID, Refund_PayID, Refund_Money);
                return payData;
            } else if ("R_WaChat".equals(R_AlipayAndR_WaChat)) {
                //微信退款
                //注意金额要乘以100
                payData = Refund_WeChat_PayInfo(Refund_PayID, Refund_OderID, Refund_Money);
                return payData;
            } else {
                ErrUtils.ParameterErr = "微信或者是支付宝的退款参数有误";
                return "-1";
            }
        } else {
            ErrUtils.ParameterErr = "退款或者是付款的参数有误";
            return "-1";
        }
    }
}
