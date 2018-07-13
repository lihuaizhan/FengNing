package com.example.tps900.tps900.WEBSERVICE_Utils;

import android.content.Context;
import android.util.Log;

import com.example.tps900.tps900.Bean.GetTicket_Bean;
import com.example.tps900.tps900.Bean.PayOrder_Bean;
import com.example.tps900.tps900.Bean.PayTypeBean;
import com.example.tps900.tps900.Utlis.Constant;
import com.example.tps900.tps900.Utlis.OtherUtils;
import com.example.tps900.tps900.Bean.Alipay_Entity;
import com.example.tps900.tps900.Bean.Alipay_trade_query_response;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * 项目名称：GTJTPS613
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2017/3/16 8:54
 * 修改人：zxh
 * 修改时间：2017/3/16 8:54
 * 修改备注：
 */

public class WebServiceJson implements Serializable {
    public static String TAG="WebServiceJson";
    private static Alipay_trade_query_response tradeQueryResponse1;
    private static Alipay_Entity parseObject;
    /**
     * @param mActivity
     * @param mores
     * @param price1       价格
     * @param payType_sign 支付编码  例如 001微信
     * @param sign         根据名称获取价格
     * @param intent
     */
    /**
     * @param mores     list数组循环所有门票信息

     * @param playtime  游玩日期
     */
//
    public static String CreatandPayOders(Context context,String flag, PayTypeBean payBean, ArrayList<GetTicket_Bean.DataBean> mores,
                                          String money, String playtime, String ThirdNo, String OrderID, String vipid,double vipBlance) {

        String payOder = null;
        String Detailes = "";//创建订单的json
        String Detailes2  ;//提交订单的json
        if ("1".equals(flag)){

            if (mores.size() == 1) {

                double v = Double.valueOf(mores.get(0).getProductPrice()) * mores.get(0).getTicketnumber() * Constants.goodsDepositRate;
                String format = new DecimalFormat("0.00").format(v);
                double discountAmt = Double.parseDouble(format);

                Detailes = "[{\"ProductId\"" + ":\"" + mores.get(0).getProductId() + "\""
                        + ",\"QuoteId\"" + ":\"" + mores.get(0).getQuoteId() + "\"" +
                        ",\"ProductName\"" + ":\"" + mores.get(0).getProductName() + "\"" +
                        ",\"productPrice\"" + ":" + mores.get(0).getProductPrice() +
                        ",\"PlayDate\"" + ":" + playtime +
                        ",\"ProductNum\"" + ":" + mores.get(0).getTicketnumber() +
                        ",\"DiscountRate\"" + ":" + Constants.goodsDepositRate +
                        ",\"DiscountAmt\"" + ":" + discountAmt + "}]";

            }else if(mores.size() == 2){

                for(int i = 0;i<mores.size();i++){

                    double v = Double.valueOf(mores.get(i).getProductPrice()) * mores.get(i).getTicketnumber() * Constants.goodsDepositRate;
                    String format = new DecimalFormat("0.00").format(v);
                    double discountAmt = Double.parseDouble(format);
                    if(i == 0){
                        Detailes += "[{\"ProductId\"" + ":\"" + mores.get(i).getProductId() + "\""
                                + ",\"QuoteId\"" + ":\"" + mores.get(i).getQuoteId() + "\"" +
                                ",\"ProductName\"" + ":\"" + mores.get(i).getProductName() + "\"" +
                                ",\"productPrice\"" + ":" +mores.get(i).getProductPrice()  +
                                ",\"PlayDate\"" + ":" + playtime +
                                ",\"ProductNum\"" + ":" + mores.get(i).getTicketnumber() +
                                ",\"DiscountRate\"" + ":" + Constants.goodsDepositRate +
                                ",\"DiscountAmt\"" + ":" + discountAmt + "}";
                    }else{

                        Detailes +=",{\"ProductId\"" + ":\"" + mores.get(i).getProductId() + "\""
                                + ",\"QuoteId\"" + ":\"" + mores.get(i).getQuoteId() + "\"" +
                                ",\"ProductName\"" + ":\"" + mores.get(i).getProductName() + "\"" +
                                ",\"productPrice\"" + ":" +mores.get(i).getProductPrice()  +
                                ",\"PlayDate\"" + ":" + playtime +
                                ",\"ProductNum\"" + ":" + mores.get(i).getTicketnumber() +
                                ",\"DiscountRate\"" + ":" + Constants.goodsDepositRate +
                                ",\"DiscountAmt\"" + ":" + discountAmt + "}]";

                    }
                }
            }else{

                for(int i = 0;i<mores.size();i++){

                    double v = Double.valueOf(mores.get(i).getProductPrice()) * mores.get(i).getTicketnumber() * Constants.goodsDepositRate;
                    String format = new DecimalFormat("0.00").format(v);
                    double discountAmt = Double.parseDouble(format);

                    if(i == 0){
                        Detailes += "[{\"ProductId\"" + ":\"" + mores.get(i).getProductId() + "\""
                                + ",\"QuoteId\"" + ":\"" + mores.get(i).getQuoteId() + "\"" +
                                ",\"ProductName\"" + ":\"" + mores.get(i).getProductName() + "\"" +
                                ",\"productPrice\"" + ":" +mores.get(i).getProductPrice()  +
                                ",\"PlayDate\"" + ":" + playtime +
                                ",\"ProductNum\"" + ":" + mores.get(i).getTicketnumber() +
                                ",\"DiscountRate\"" + ":" + Constants.goodsDepositRate +
                                ",\"DiscountAmt\"" + ":" + discountAmt + "}";

                    }else if(i == mores.size() - 1){

                        Detailes +=",{\"ProductId\"" + ":\"" + mores.get(i).getProductId() + "\""
                                + ",\"QuoteId\"" + ":\"" + mores.get(i).getQuoteId() + "\"" +
                                ",\"ProductName\"" + ":\"" + mores.get(i).getProductName() + "\"" +
                                ",\"productPrice\"" + ":" +mores.get(i).getProductPrice()  +
                                ",\"PlayDate\"" + ":" + playtime +
                                ",\"ProductNum\"" + ":" + mores.get(i).getTicketnumber() +
                                ",\"DiscountRate\"" + ":" + Constants.goodsDepositRate +
                                ",\"DiscountAmt\"" + ":" + discountAmt + "}]";

                    }else{

                        Detailes += ",{\"ProductId\"" + ":\"" + mores.get(i).getProductId() + "\""
                                + ",\"QuoteId\"" + ":\"" + mores.get(i).getQuoteId() + "\"" +
                                ",\"ProductName\"" + ":\"" + mores.get(i).getProductName() + "\"" +
                                ",\"productPrice\"" + ":" + mores.get(i).getProductPrice()  +
                                ",\"PlayDate\"" + ":" + playtime +
                                ",\"ProductNum\"" + ":" + mores.get(i).getTicketnumber() +
                                ",\"DiscountRate\"" + ":" + Constants.goodsDepositRate +
                                ",\"DiscountAmt\"" + ":" + discountAmt + "}";


                    }
                }


            }


        }else {
            if (mores.size() != 0) {

                for (int i = 0; i < mores.size(); i++) {
//                double discountRate = 1;
                    double v = Double.valueOf(mores.get(i).getProductPrice()) * 1 * Constants.goodsDepositRate;
                    String format = new DecimalFormat("0.00").format(v);
                    double discountAmt = Double.parseDouble(format);


                    for (int j = 0; j < mores.get(i).getTicketnumber(); j++) {
                        if (mores.size() == 1 && mores.get(i).getTicketnumber() == 1) {
                            Detailes = "[{\"ProductId\"" + ":\"" + mores.get(i).getProductId() + "\""
                                    + ",\"QuoteId\"" + ":\"" + mores.get(i).getQuoteId() + "\"" +
                                    ",\"ProductName\"" + ":\"" + mores.get(i).getProductName() + "\"" +
                                    ",\"productPrice\"" + ":" +mores.get(i).getProductPrice()  +
                                    ",\"PlayDate\"" + ":" + playtime +
                                    ",\"ProductNum\"" + ":" + 1 +
                                    ",\"DiscountRate\"" + ":" + Constants.goodsDepositRate +
                                    ",\"DiscountAmt\"" + ":" + discountAmt + "}]";
                        } else if (i == 0 && j == 0) {
                            Detailes = "[{\"ProductId\"" + ":\"" + mores.get(i).getProductId() + "\""
                                    + ",\"QuoteId\"" + ":\"" + mores.get(i).getQuoteId() + "\"" +
                                    ",\"ProductName\"" + ":\"" + mores.get(i).getProductName() + "\"" +
                                    ",\"productPrice\"" + ":" + mores.get(i).getProductPrice() +
                                    ",\"PlayDate\"" + ":" + playtime +
                                    ",\"ProductNum\"" + ":" + 1 +
                                    ",\"DiscountRate\"" + ":" + Constants.goodsDepositRate +
                                    ",\"DiscountAmt\"" + ":" + discountAmt + "},";
                        } else if (i == mores.size() - 1 && j == mores.get(i).getTicketnumber() - 1) {
                            Detailes = Detailes + "{\"ProductId\"" + ":\"" + mores.get(i).getProductId() + "\""
                                    + ",\"QuoteId\"" + ":\"" + mores.get(i).getQuoteId() + "\"" +
                                    ",\"ProductName\"" + ":\"" + mores.get(i).getProductName() + "\"" +
                                    ",\"productPrice\"" + ":" +mores.get(i).getProductPrice()  +
                                    ",\"PlayDate\"" + ":" + playtime +
                                    ",\"ProductNum\"" + ":" + 1 +
                                    ",\"DiscountRate\"" + ":" + Constants.goodsDepositRate +
                                    ",\"DiscountAmt\"" + ":" + discountAmt + "}]";
                        } else {
                            Detailes = Detailes + "{\"ProductId\"" + ":\"" + mores.get(i).getProductId() + "\""
                                    + ",\"QuoteId\"" + ":\"" + mores.get(i).getQuoteId() + "\"" +
                                    ",\"ProductName\"" + ":\"" + mores.get(i).getProductName() + "\"" +
                                    ",\"productPrice\"" + ":" + mores.get(i).getProductPrice()  +
                                    ",\"PlayDate\"" + ":" + playtime +
                                    ",\"ProductNum\"" + ":" + 1 +
                                    ",\"DiscountRate\"" + ":" + Constants.goodsDepositRate +
                                    ",\"DiscountAmt\"" + ":" + discountAmt + "},";
                        }
                    }
                }

                /**
                 *获取到票之后去大平台提交订单
                 * 最后返回订单ID PFS00000658
                 */
                Detailes2 = "[{\"PayType\"" + ":\"" +payBean.getTypeCode()+ "\""
                        + ",\"ThirdNo\":\"" + ThirdNo + "\"" +
                        ",\"CardNo\""+":\"" +vipid + "\"" +
                        ",\"PayAmt\":" + Double.valueOf(money)*Constants.goodsDepositRate+
                        "}]";
                String creatOder_code = JsonUtils.CreateOrder(Detailes, "张", "152", "12121212121", Login_Variate.EmployeeCode,
                        OrderID,vipid,payBean.getTypeCode());
                if ("false".equals(creatOder_code)) {
                    Log.e(TAG, "创建订单失败");
                    //退款
                    payOder = "CreateOrderErr";
                } else {
                    System.out.println("Detailes2---->"+Detailes2);

                    payOder = PayOder(  context,   payBean,mores , Detailes2, creatOder_code, money ,creatOder_code,Double.valueOf(money)*Constants.goodsDepositRate,vipBlance);//提交订单
                    if ("PayOderErr".equals(payOder)) {
                        payOder = "PayOderErr";
                    }
                }

            } else {
                Log.e(TAG, "请添加门票");
            }
        }

        return payOder;
    }
//
//
    public static String PayOder(final Context context, PayTypeBean payBean, List<GetTicket_Bean.DataBean> mores, String Detailes, String creatOder_code, String price1, String OderID , final double Paymoney, final double Y_money) {
        final List<GetTicket_Bean.DataBean> PrintList=new ArrayList<>();
        String payOrder = "";
        List<PayOrder_Bean.DataBean> dataBeen = JsonUtils.PayOrder_Json(Detailes, creatOder_code, OderID );
        if (dataBeen == null) {
            Log.e(TAG, "失败了" + dataBeen);
            payOrder = "PayOderErr";
            return payOrder;
        } else {
            payOrder = "OK";

            for (int i = 0; i <dataBeen.size() ; i++) {
                for (int j = 0; j <mores.size() ; j++) {
                    if (dataBeen.get(i).getProductId()!=null&&dataBeen.get(i).getProductId().equals(mores.get(j).getProductId())){
                        GetTicket_Bean.DataBean PrintdataBean=new GetTicket_Bean.DataBean();
                        PrintdataBean.setOrderID(dataBeen.get(i).getOrderId());
                        PrintdataBean.setProductName(mores.get(j).getProductName());
                        PrintdataBean.setProductPrice(mores.get(j).getProductPrice());
                        PrintdataBean.setTicketnumber(mores.get(j).getTicketnumber());
                        PrintdataBean.setEcode(dataBeen.get(i).getEcode());
                        PrintdataBean.setSTime(dataBeen.get(i).getStartPlayDate());
                        PrintdataBean.setETime(dataBeen.get(i).getEndPlayDate());
                        PrintdataBean.setPay_Code(payBean.getTypeName());
                        PrintList.add(PrintdataBean);
                    }
                }

            }
            Constant.XS_PrintList=PrintList;
          new Thread(new Runnable() {
              @Override
              public void run() {
                  Constant.Printpaymoney=Paymoney;
                  Constant.PrintYMoney=Y_money;
                  OtherUtils.Ticket_S_Print(context,PrintList,Paymoney,Y_money);
              }
          }).start();
            return payOrder;
        }

    }
}
