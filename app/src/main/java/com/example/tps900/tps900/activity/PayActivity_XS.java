package com.example.tps900.tps900.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.tps900.tps900.Bean.Alipay_Entity;
import com.example.tps900.tps900.Bean.Alipay_trade_query_response;
import com.example.tps900.tps900.Bean.GetLevelInfo_Bean;
import com.example.tps900.tps900.Bean.GetVipInfoTogether_been;
import com.example.tps900.tps900.Bean.PayTypeBean;
import com.example.tps900.tps900.Bean.WeChat_Entity;
import com.example.tps900.tps900.R;
import com.example.tps900.tps900.Utlis.AliAndWePayMent;
import com.example.tps900.tps900.Utlis.Constant;
import com.example.tps900.tps900.Utlis.DialogUtil;
import com.example.tps900.tps900.Utlis.LogUtil;
import com.example.tps900.tps900.Utlis.OtherUtils;
import com.example.tps900.tps900.WEBSERVICE_Utils.Constants;
import com.example.tps900.tps900.WEBSERVICE_Utils.GsWebServiceUtils;
import com.example.tps900.tps900.WEBSERVICE_Utils.Login_Variate;
import com.example.tps900.tps900.WEBSERVICE_Utils.WebServiceJson;
import com.example.tps900.tps900.adapters.PayAdapter_XS;
import com.example.tps900.tps900.sql.PdaDaoUtils;
import com.godfery.Utils.ToastUtils;
import com.godfery.pay.ErrUtils;
import com.godfery.Zxing.CaptureActivity;
import com.telpo.tps550.api.TelpoException;
import com.telpo.tps550.api.nfc.Nfc;
import com.telpo.tps550.api.util.StringUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.example.tps900.tps900.Utlis.OtherUtils.getNum;
import static com.example.tps900.tps900.Utlis.OtherUtils.sendMessageInfo;
import static com.example.tps900.tps900.WEBSERVICE_Utils.JsonUtils.JsonOrderID;
import static com.godfery.pay.OtherUtils.NormalDialogStyleOne;


public class PayActivity_XS extends AppCompatActivity {

    public static final String TAG = "PayActivity_XS";

    private final int CHECK_NFC_TIMEOUT = 1;
    private final int SHOW_NFC_DATA = 2;
    private Nfc nfc;
    private ReadThread readThread;

    private TextView shouldMoney_tv;
    private ListView lv_payType;
    private TextView actualMoney;
    //    private ArrayList<PayBean> payList;
    private PayAdapter_XS adapter;
    private ArrayList<PayTypeBean> list_delete = new ArrayList<>();
    private Button btn_confirm_order;
    private String payType;
    private String money;
    private Button btn_cancel_order;
    private String codeNum = "";
    private String payType_str;
    private String OrderID;
    private String PayMentData;
    List<PayTypeBean> PayType;
    private String PayCode;
    private final int SubmitOrder = 6;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CHECK_NFC_TIMEOUT: {
                    ToastUtils.show(PayActivity_XS.this, "未检测到卡片超时 !");
                }
                break;
                case SHOW_NFC_DATA: {
                    byte[] uid_data = (byte[]) msg.obj;
                    if (uid_data[0] == 0x41) {
                        // TYPE A类（CPU, M1）
                        byte[] atqa = new byte[2];
                        byte[] sak = new byte[1];
                        byte[] uid = new byte[uid_data[5]];
                        String type = null;
                        System.arraycopy(uid_data, 2, atqa, 0, 2);
                        System.arraycopy(uid_data, 4, sak, 0, 1);
                        System.arraycopy(uid_data, 6, uid, 0, uid_data[5]);
                        codeNum = OtherUtils.rfidToHand(StringUtil.toHexString(uid));
                        if (codeNum.startsWith("0")) {
                            codeNum = codeNum.substring(1);
                        }
                        LogUtil.i(TAG, "codeNum--->" + codeNum);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                getVipInfoPayMent(codeNum);
                            }
                        }).start();
                    } else {
                        ToastUtils.show(PayActivity_XS.this, "unknow type card!!");
                    }
                }
                break;
                case 0://金额有误
                    if (PayMentData.length() > 50) {
                        // TODO 去掉转义字符
                        PayMentData = PayMentData.replace("\\", "");
                        PayMentData = PayMentData.substring(2, PayMentData.length() - 2);
                        if ("支付宝".equals(PayCode)) {
                            Alipay_Entity alipay_entity = JSON.parseObject(PayMentData, Alipay_Entity.class);
                            Alipay_trade_query_response ar = alipay_entity.getAlipay_trade_query_response();
                            if ("Success".equals(ar.msg) && "TRADE_SUCCESS".equals(ar.trade_status)) {

                                PayTypeBean payBean = list_delete.get(0);
                                Log.e("啦啦啦啦啦", ar.getTrade_no());
                                String creatandPayOders = WebServiceJson.CreatandPayOders(PayActivity_XS.this, "", payBean, Constant.BEANS_XS, money, OtherUtils.Json_Time(OtherUtils.time_1()), ar.getTrade_no(), OrderID, "", 0.0);
                                Log.e("啦啦啦啦啦", creatandPayOders);
                                if ("OK".equals(creatandPayOders)) {
                                    Intent intent = new Intent();
                                    intent.putExtra("flag", true);
                                    setResult(RESULT_OK, intent);
                                    PayActivity_XS.this.finish();
                                } else {
                                    Intent intent = new Intent();
                                    intent.putExtra("flag", false);
                                    setResult(RESULT_OK, intent);
                                    RefundPay("支付宝", ar.getTrade_no(), OrderID, money);
                                    PayActivity_XS.this.finish();
                                }
                            } else {
                                NormalDialogStyleOne(PayActivity_XS.this, "支付失败！！！");
                            }

                        } else if ("微信".equals(PayCode)) {
                            WeChat_Entity weChat_entity = JSON.parseObject(PayMentData, WeChat_Entity.class);
                            if ("SUCCESS".equals(weChat_entity.result_code)
                                    && "SUCCESS".equals(weChat_entity.return_code)
                                    && "SUCCESS".equals(weChat_entity.trade_state)) {
                                PayTypeBean payBean = list_delete.get(0);
                                Log.e("啦啦啦啦啦", weChat_entity.getOut_trade_no());
                                String creatandPayOders = WebServiceJson.CreatandPayOders(PayActivity_XS.this, "", payBean, Constant.BEANS_XS, money, OtherUtils.Json_Time(OtherUtils.time_1()), weChat_entity.getOut_trade_no(), OrderID, "", 0.0);
                                Log.e("啦啦啦啦啦", creatandPayOders);
                                if ("OK".equals(creatandPayOders)) {

                                    Intent intent = new Intent();
                                    intent.putExtra("flag", true);
                                    setResult(RESULT_OK, intent);
                                    PayActivity_XS.this.finish();
                                } else {

                                    Intent intent = new Intent();
                                    intent.putExtra("flag", false);
                                    setResult(RESULT_OK, intent);
                                    RefundPay("微信", weChat_entity.getOut_trade_no(), OrderID, money);
                                    PayActivity_XS.this.finish();
                                }
                            } else {
                                NormalDialogStyleOne(PayActivity_XS.this, "支付失败！！！");
                            }

                        }

                    } else {
                        NormalDialogStyleOne(PayActivity_XS.this, "支付失败！！！");
                    }
                    break;
                case SubmitOrder:
                    if (!list_delete.isEmpty()) {
                        //如果支付集合不为空
                        if (list_delete.size() == 1) {
                            final PayTypeBean payBean = list_delete.get(0);
                            payType = payBean.getTypeCode();
                            //如果是现金直接提交销售数据，微信或支付宝则打开扫描头
                            if ("003".equals(payType)) {
                                payType_str = "现金";
                                DialogUtil.showPayDialog(PayActivity_XS.this);
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        OrderID = JsonOrderID();
                                        String creatandPayOders = WebServiceJson.CreatandPayOders(PayActivity_XS.this, "", payBean, Constant.BEANS_XS, money, OtherUtils.Json_Time(OtherUtils.time_1()), "", OrderID, "", 0.0);
                                        if ("OK".equals(creatandPayOders)) {
                                            Intent intent = new Intent();
                                            intent.putExtra("flag", true);
                                            setResult(RESULT_OK, intent);
                                            PayActivity_XS.this.finish();
                                        } else {
                                            Intent intent = new Intent();
                                            intent.putExtra("flag", false);
                                            setResult(RESULT_OK, intent);
                                            PayActivity_XS.this.finish();
                                        }
                                    }
                                }).start();

                            } else if ("005".equals(payType)) {
                                DialogUtil.showOneCardDialog(PayActivity_XS.this);
                                payType_str = "一卡通";
                                DeviceN900();

                            } else { //点击确认支付按钮，打开扫描头
                                Intent intent = new Intent(PayActivity_XS.this, CaptureActivity.class);
                                PayActivity_XS.this.startActivityForResult(intent, 1001);
                            }

                        } else if (list_delete.size() > 1) {
                            ToastUtils.show(PayActivity_XS.this, "不支持混合支付请重新选择");
                            return;
                        }
                    } else {
                        ToastUtils.show(PayActivity_XS.this, "请选择支付方式");
                        return;
                    }
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        Intent intent = getIntent();
        money = intent.getStringExtra("actualMoney");
        findView();
    }

    private void findView() {
        PayType = new ArrayList<>();
        for (int i = 0; i < Login_Variate.PayType.size(); i++) {
            PayTypeBean paytype = new PayTypeBean();
            paytype.setTypeCode(Login_Variate.PayType.get(i).getTypeCode());
            paytype.setTypeName(Login_Variate.PayType.get(i).getTypeName());
            PayType.add(paytype);
        }

        shouldMoney_tv = (TextView) findViewById(R.id.shouldMoney_tv);
        shouldMoney_tv.setText(money);
        lv_payType = (ListView) findViewById(R.id.lv_payType);
        actualMoney = (TextView) findViewById(R.id.actualMoney);
        actualMoney.setText(money);
        btn_confirm_order = (Button) findViewById(R.id.btn_confirm_order);
        btn_cancel_order = (Button) findViewById(R.id.btn_cancel_order);
        btn_cancel_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter = new PayAdapter_XS(PayType, PayActivity_XS.this);
                adapter.notifyDataSetChanged();
                lv_payType.setAdapter(adapter);
                PayActivity_XS.this.finish();
            }
        });

        btn_confirm_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!list_delete.isEmpty()) {
                    //如果支付集合不为空
                    if (list_delete.size() == 1) {
                        final PayTypeBean payBean = list_delete.get(0);
                        payType = payBean.getTypeCode();
                        //如果是现金直接提交销售数据，微信或支付宝则打开扫描头
                        if ("003".equals(payType)) {
                            payType_str = "现金";
                            DialogUtil.showPayDialog(PayActivity_XS.this);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    OrderID = JsonOrderID();
                                    String creatandPayOders = WebServiceJson.CreatandPayOders(PayActivity_XS.this, "", payBean, Constant.BEANS_XS, money, OtherUtils.Json_Time(OtherUtils.time_1()), "", OrderID, "", 0.0);
                                    if ("OK".equals(creatandPayOders)) {
                                        Intent intent = new Intent();
                                        intent.putExtra("flag", true);
                                        setResult(RESULT_OK, intent);
                                        PayActivity_XS.this.finish();
                                    } else {
                                        Intent intent = new Intent();
                                        intent.putExtra("flag", false);
                                        setResult(RESULT_OK, intent);
                                        PayActivity_XS.this.finish();
                                    }
                                }
                            }).start();

                        } else if ("005".equals(payType)) {
                            DialogUtil.showOneCardDialog(PayActivity_XS.this);
                            payType_str = "一卡通";
                            DeviceN900();

                        } else { //点击确认支付按钮，打开扫描头
                            Intent intent = new Intent(PayActivity_XS.this, CaptureActivity.class);
                            PayActivity_XS.this.startActivityForResult(intent, 1001);
                        }

                    } else if (list_delete.size() > 1) {
                        ToastUtils.show(PayActivity_XS.this, "不支持混合支付请重新选择");
                        return;
                    }
                } else {
                    ToastUtils.show(PayActivity_XS.this, "请选择支付方式");
                    return;
                }
            }
        });

        adapter = new PayAdapter_XS(PayType, PayActivity_XS.this);
        lv_payType.setAdapter(adapter);

        lv_payType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PayTypeBean payBean = PayType.get(position);
                list_delete.clear();
                adapter.setSelectedIndex(position);
//                if (payBean.isChecked()) {
//                    payBean.setChecked(false);
//                    list_delete.remove(payBean);
//                } else {
//                    payBean.setChecked(true);
                    list_delete.add(payBean);
//                }
                sendMessageInfo(handler,SubmitOrder);
                adapter.notifyDataSetChanged();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case 1001:
                if (null != data) {
                    //扫描成功
                    final String result = data.getStringExtra("result");
                    LogUtil.i(TAG, "barCode----->" + result);
                    if ("001".equals(payType)) {
                        DialogUtil.showPayDialog(PayActivity_XS.this);
                        payType_str = "微信";
                        PayCode = "微信";
                        //微信
                        OrderID = JsonOrderID();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                PayMnet(getNum(), result, money);
                            }
                        }).start();

                    } else if ("002".equals(payType)) {

                        DialogUtil.showPayDialog(PayActivity_XS.this);
                        OrderID = JsonOrderID();
                        payType_str = "支付宝";
                        PayCode = "支付宝";
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                PayMnet(getNum(), result, money);

                            }
                        }).start();
                    }

                } else {
                    //扫描失败
                    ToastUtils.show(this, "未扫描到数据!");
                }

                break;
            default:
                break;
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            PayActivity_XS.this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * @author zxh
     * created at 2017/9/27 10:58
     * 方法名: 打开NFC设备
     * 方法说明:
     */
    public void DeviceN900() {

        //打开nfc设备
        nfc = new Nfc(this);
        try {
            nfc.close();
            nfc.open();
        } catch (TelpoException e) {
            e.printStackTrace();
        }
        readThread = new ReadThread();
        readThread.start();
    }


    private class ReadThread extends Thread {
        byte[] nfcData = null;

        @Override
        public void run() {
            try {
                nfcData = nfc.activate(3 * 1000); // 10s
                if (null != nfcData) {
                    PayActivity_XS.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogUtil.cancelDownloadDialog();
                        }
                    });
                    handler.sendMessage(handler.obtainMessage(SHOW_NFC_DATA, nfcData));
                } else {
                    PayActivity_XS.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogUtil.cancelDownloadDialog();
                        }
                    });
                    Log.d("GetNFC----------->", "Check MagneticCard timeout...");
                    handler.sendMessage(handler.obtainMessage(CHECK_NFC_TIMEOUT, null));
                }
            } catch (TelpoException e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * @author zxh
     * created at 2017/9/27 10:04
     * 方法名: 支付方法
     * 方法说明:传入随机数,付款码,支付金额
     */
    public void PayMnet(final String random, final String payResult, final String editMoney) {
        if ("微信".equals(PayCode)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    PayMentData = AliAndWePayMent.PayMentAndRefund("Payment", random, "", payResult, editMoney + "", "", "", "" + "");

                    if ("-1".equals(PayMentData) || TextUtils.isEmpty(PayMentData)) {
                        if (!TextUtils.isEmpty(ErrUtils.ParameterErr)) {
                            Log.e("StringPayMent", ErrUtils.ParameterErr);

                        } else if (!TextUtils.isEmpty(ErrUtils.PaymentCodeErr)) {
                            Log.e("StringPayMent", ErrUtils.PaymentCodeErr);

                        }
                    } else {
                        handler.sendEmptyMessageDelayed(0, 10);

                    }


                }
            }).start();

        } else if ("支付宝".equals(PayCode)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    PayMentData = AliAndWePayMent.PayMentAndRefund("Payment", random, "", payResult, editMoney + "", "", "", "" + "");
                    if ("-1".equals(PayMentData) || TextUtils.isEmpty(PayMentData)) {
                        if (!TextUtils.isEmpty(ErrUtils.ParameterErr)) {
                            Log.e("StringPayMent", ErrUtils.ParameterErr);

                        } else if (!TextUtils.isEmpty(ErrUtils.PaymentCodeErr)) {
                            Log.e("StringPayMent", ErrUtils.PaymentCodeErr);

                        }
                    } else {
                        handler.sendEmptyMessageDelayed(0, 10);
                    }


                }
            }).start();

        }
    }

    /**
     * @author zxh
     * created at 2017/9/27 10:05
     * 方法名: 退款类型,第三方交易流水号,退款单据,退款金额
     * 方法说明:
     */
    public void RefundPay(final String refundPay, final String Refund_PayID, final String Refund_OderID, final String Refund_Money) {
        if ("微信".equals(refundPay)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    PayMentData = AliAndWePayMent.PayMentAndRefund("Refund", "", "R_WaChat", "", "" + "", Refund_PayID, Refund_OderID, Refund_Money + "");
                    Log.e("支付结果是--------", PayMentData);
                    if ("-1".equals(PayMentData) || TextUtils.isEmpty(PayMentData)) {
                        if (!TextUtils.isEmpty(ErrUtils.ParameterErr)) {
                            Log.e("StringPayMent", ErrUtils.ParameterErr);
                        } else if (!TextUtils.isEmpty(ErrUtils.PaymentCodeErr)) {
                            Log.e("StringPayMent", ErrUtils.PaymentCodeErr);
                        }
                    } else {
                        //   handler.sendEmptyMessageDelayed(0, 10);//支付宝支付与微信支付完之后清除

                    }
                }
            }).start();

        } else if ("支付宝".equals(refundPay)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    PayMentData = AliAndWePayMent.PayMentAndRefund("Refund", "", "R_Alipay", "", "" + "", Refund_PayID, Refund_OderID, Refund_Money + "");
                    Log.e("支付结果是--------", PayMentData);
                    if ("-1".equals(PayMentData) || TextUtils.isEmpty(PayMentData)) {
                        if (!TextUtils.isEmpty(ErrUtils.ParameterErr)) {
                            Log.e("StringPayMent", ErrUtils.ParameterErr);
                        } else if (!TextUtils.isEmpty(ErrUtils.PaymentCodeErr)) {
                            Log.e("StringPayMent", ErrUtils.PaymentCodeErr);
                        }
                    } else {
                        // handler.sendEmptyMessageDelayed(0, 10);//支付宝支付与微信支付完之后清除
                    }

                }
            }).start();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter = new PayAdapter_XS(PayType, PayActivity_XS.this);
        adapter.notifyDataSetChanged();
    }

    /**
     * @author zxh
     * created at 2017/10/13 13:53
     * 方法名: 会员卡支付
     * 方法说明:先查询会员信息 查询数据库卡级信息折扣率  判断卡余额与支付金额最后进行创建订单并支付订单
     */
    public void getVipInfoPayMent(String codeNum) {
        //获取折扣率信息
        String getVipInfoTogether = GsWebServiceUtils.GetVipInfoTogether(codeNum);
        OrderID = JsonOrderID();
        final GetVipInfoTogether_been getVipInfoTogether_been = JSON.parseObject(getVipInfoTogether, GetVipInfoTogether_been.class);
        if (getVipInfoTogether_been.Success) {

            if (getVipInfoTogether_been.Data != null && getVipInfoTogether_been.Data.size() != 0) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("lala", "卡名称" + getVipInfoTogether_been.getData().get(0).getVipInfo().getCardLevelName());
                        Log.e("lala", "卡余额" + getVipInfoTogether_been.getData().get(0).getBalance());
                        //卡余额
                        double Blance = Double.valueOf(getVipInfoTogether_been.getData().get(0).getBalance());
                        //查询卡级折扣率
                        ArrayList<GetLevelInfo_Bean.DataBean> query_levelInfo = PdaDaoUtils.getQuery_LevelInfo("select * from LevelInfo where LevelId = '" + getVipInfoTogether_been.getData().get(0).getVipInfo().getCardLevelId() + "' ");
                        double discountAmt = 0.0;
                        if (query_levelInfo != null && query_levelInfo.size() != 0) {
                            Constants.goodsDepositRate = query_levelInfo.get(0).getDEPOSITRATE();
                            Log.e("lala", "折扣率" + query_levelInfo.get(0).getDEPOSITRATE());
                            //得到折扣总金额
                            for (int i = 0; i < Constant.BEANS_XS.size(); i++) {
                                double v = Double.valueOf(Constant.BEANS_XS.get(i).getProductPrice()) * Constant.BEANS_XS.get(i).getTicketnumber() * Constants.goodsDepositRate;
                                String format = new DecimalFormat("0.00").format(v);
                                discountAmt += Double.parseDouble(format);
                            }
                            if (Blance >= discountAmt) {
                                Log.e("lala", "卡余额" + Blance);
                                PayTypeBean payBean = list_delete.get(0);
                                String creatandPayOders = WebServiceJson.CreatandPayOders(PayActivity_XS.this, "", payBean, Constant.BEANS_XS, money, OtherUtils.Json_Time(OtherUtils.time_1()), "", OrderID, getVipInfoTogether_been.getData().get(0).getVipInfo().getVipId(), Blance);
                                if ("OK".equals(creatandPayOders)) {
//                                    Constants.goodsDepositRate = 1.0;
                                    discountAmt = 0.0;
                                    Intent intent = new Intent();
                                    intent.putExtra("flag", true);
                                    setResult(RESULT_OK, intent);
                                    PayActivity_XS.this.finish();
                                } else {
                                    Intent intent = new Intent();
                                    intent.putExtra("flag", false);
                                    setResult(RESULT_OK, intent);
                                    PayActivity_XS.this.finish();
                                }
                            } else {
                                final double finalDiscountAmt = discountAmt;
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        NormalDialogStyleOne(PayActivity_XS.this, "卡余额不足,卡余额为" + Double.valueOf(getVipInfoTogether_been.getData().get(0).getBalance()) + "元" + "\n折扣率为" + Constants.goodsDepositRate * 100 + "%" + "\n打折后总额" + finalDiscountAmt + "\n请先充值");
                                    }
                                });
                            }
                        }
//
                    }
                });
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        NormalDialogStyleOne(PayActivity_XS.this, "会员卡信息为空！！！");
                    }
                });
            }
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    NormalDialogStyleOne(PayActivity_XS.this, "获取会员卡信息失败！！！");
                }
            });
        }
    }
}
