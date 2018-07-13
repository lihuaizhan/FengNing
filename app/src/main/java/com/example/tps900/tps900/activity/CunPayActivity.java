package com.example.tps900.tps900.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.tps900.tps900.Bean.Alipay_Entity;
import com.example.tps900.tps900.Bean.Alipay_trade_query_response;
import com.example.tps900.tps900.Bean.WeChat_Entity;
import com.example.tps900.tps900.R;
import com.example.tps900.tps900.Utlis.AliAndWePayMent;
import com.example.tps900.tps900.Utlis.Dailog;
import com.example.tps900.tps900.Utlis.DialogUtil;
import com.example.tps900.tps900.Utlis.LogUtil;
import com.example.tps900.tps900.Utlis.TpsN900PrintUtils;
import com.example.tps900.tps900.Webservice.GsWebServiceUtils;
import com.godfery.Utils.ToastUtils;
import com.godfery.pay.ErrUtils;
import com.godfery.pay.HttpUtils;
import com.godfery.Zxing.CaptureActivity;
import com.loopj.android.http.AsyncHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

import static com.example.tps900.tps900.Utlis.OtherUtils.CheckMoney;
import static com.example.tps900.tps900.Utlis.OtherUtils.getNum;
import static com.example.tps900.tps900.Utlis.OtherUtils.getTime;
import static com.example.tps900.tps900.Utlis.OtherUtils.sendMessageInfo;
import static com.godfery.pay.OtherUtils.NormalDialogStyleOne;

public class CunPayActivity extends BaseActivity implements BaseActivity.CardCodeInterface {
    //金额输入框

    EditText mainEditextPay;
    //支付宝支付

    ImageView mianImgAlipay;
    //微信支付

    ImageView mianImgWechat;
    //一卡通支付

    ImageView mian_img_onecard;

    LinearLayout settingBack;
    private String PayCode = "";
    public String codeNum = "";
    public final String TAG = "一卡通";
    public double balance = 0.00;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0://金额有误
                    HttpUtils.exitProgressDialog(progressDialog);
                    if (PayMentData.length() > 50) {
                        mainEditextPay.setText("");
                        money = "";
                        // TODO 去掉转义字符
                        PayMentData = PayMentData.replace("\\", "");
                        PayMentData = PayMentData.substring(2, PayMentData.length() - 2);
                        if ("支付宝".equals(PayCode)) {
                            Alipay_Entity alipay_entity = JSON.parseObject(PayMentData, Alipay_Entity.class);
                            Alipay_trade_query_response ar = alipay_entity.getAlipay_trade_query_response();
                            if ("Success".equals(ar.msg) && "TRADE_SUCCESS".equals(ar.trade_status)) {
                                NormalDialogStyleOne(CunPayActivity.this, "支付成功！！！");
                                TpsN900PrintUtils.TpsN900PayPrint(CunPayActivity.this,
                                        "\n" + "--------------------------" +
                                                "\n" +
                                                "\n支付时间:" + getTime() +
                                                "\n" +
                                                "\n" + "支付类型:" + PayCode +
                                                "\n" +
                                                "\n" + "支付金额:" + ar.getInvoice_amount() +
                                                "\n" +
                                                "\n" + "--------------------------" +
                                                "\n" +
                                                "\n" + "商户订单号: 支持商家扫码退款", ar.getTrade_no()
                                );
                            } else {
                                NormalDialogStyleOne(CunPayActivity.this, "支付失败！！！");
                            }

                        } else if ("微信".equals(PayCode)) {
                            WeChat_Entity weChat_entity = JSON.parseObject(PayMentData, WeChat_Entity.class);
                            if ("SUCCESS".equals(weChat_entity.result_code)
                                    && "SUCCESS".equals(weChat_entity.return_code)
                                    && "SUCCESS".equals(weChat_entity.trade_state)) {
                                NormalDialogStyleOne(CunPayActivity.this, "支付成功！！！");
                                double weMoney = Double.parseDouble(weChat_entity.getCash_fee());

                                TpsN900PrintUtils.TpsN900PayPrint(CunPayActivity.this,
                                        "\n" + "--------------------------" +
                                                "\n" +
                                                "\n支付时间:" + getTime() +
                                                "\n" +
                                                "\n" + "支付类型:" + PayCode +
                                                "\n" +
                                                "\n" + "支付金额:" + weMoney / 100 +
                                                "\n" +
                                                "\n" + "--------------------------" +
                                                "\n" +
                                                "\n" + "商户订单号: 支持商家扫码退款", weChat_entity.getTransaction_id()
                                );
                            } else {
                                NormalDialogStyleOne(CunPayActivity.this, "支付失败！！！");
                            }

                        }

                    } else {
                        NormalDialogStyleOne(CunPayActivity.this, "支付失败！！！");
                    }
                    break;
                case 1:
                    if (!TextUtils.isEmpty(qrcode) && !TextUtils.isEmpty(money)) {
                        HttpUtils.showProgressDialog(progressDialog);
                        PayMnet(getNum(), qrcode, money);
                    } else {
                        ToastUtils.show(CunPayActivity.this, "错了");
                    }
                    break;
                case 3:
                    mainEditextPay.setText("");
                    break;
                case 4:
                    NormalDialogStyleOne(CunPayActivity.this, "支付失败！！！");
                    break;
                default:
            }
        }
    };

    private String qrcode;//二维码
    private String PayMentData;
    private String money;
    private AsyncHttpClient httpClient;
    //获取一卡通信息
    private ProBroadcastReceiver Pro = null;


    /**
     * 设置布局
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_payment;
    }

    @Override
    public void initView() {
        mainEditextPay = (EditText) findViewById(R.id.main_editext_pay);
        mianImgAlipay = (ImageView) findViewById(R.id.mian_img_alipay);
        mianImgWechat = (ImageView) findViewById(R.id.mian_img_wechat);
        mian_img_onecard = (ImageView) findViewById(R.id.mian_img_onecard);
        settingBack = (LinearLayout) findViewById(R.id.setting_lv_back);
        httpClient = new AsyncHttpClient(); // 联网获取数据的方法
        setCardInterface(this);
//        RefundPay("支付宝", "2017121321001004225314224133", "PFS00012818", "0.01");//10533
    }

    /**
     * 操作数据
     */
    @Override
    public void initData() {
        //获取一卡通信息广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("WebService");
        intentFilter.addAction("Code");
        if (Pro == null) {
            Pro = new ProBroadcastReceiver();
        }
        this.registerReceiver(Pro, intentFilter);
    }

   public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.mian_img_alipay) {
            money = mainEditextPay.getText().toString().trim();
            if (CheckMoney(money)) {
                if (!TextUtils.isEmpty(money) && Double.valueOf(money) > 0) {
                    PayCode = "支付宝";
                    openActivity(CaptureActivity.class, 100);
                } else {
                    ToastUtils.show(CunPayActivity.this, "金额有误！！！！");
                    mainEditextPay.setText("");
                }
            } else {
                ToastUtils.show(CunPayActivity.this, "金额有误！！！！");
                mainEditextPay.setText("");
            }
        } else if (id == R.id.mian_img_wechat) {
            money = mainEditextPay.getText().toString().trim();
            if (CheckMoney(money)) {
                if (!TextUtils.isEmpty(money) && Double.valueOf(money) > 0) {
                    PayCode = "微信";
                    openActivity(CaptureActivity.class, 100);
                } else {
                    ToastUtils.show(CunPayActivity.this, "金额有误！！！！");
                    mainEditextPay.setText("");
                }
            } else {
                ToastUtils.show(CunPayActivity.this, "金额有误！！！！");
                mainEditextPay.setText("");
            }
        } else if (id == R.id.setting_lv_back) {
            CunPayActivity.this.startActivity(new Intent(this, MainActivity.class));
            finish();

        } else if (id == R.id.mian_img_onecard) {
            money = mainEditextPay.getText().toString().trim();
            if (CheckMoney(money)) {
                if (!TextUtils.isEmpty(money) && Double.valueOf(money) > 0) {
                    HttpUtils.showProgressDialog(progressDialog);
                    DeviceN900(CunPayActivity.this);
                } else {
                    ToastUtils.show(CunPayActivity.this, "金额有误！！！！");
                    mainEditextPay.setText("");
                }
            } else {
                ToastUtils.show(CunPayActivity.this, "金额有误！！！！");
                mainEditextPay.setText("");
            }
        }

    }

    /**
     * 获取一卡通信息
     *
     * @param cardNo
     */
    private void submitOneCardInfo(String cardNo) {

        CunPayActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogUtil.showPayDialog(CunPayActivity.this);
            }
        });
        GsWebServiceUtils gs = new GsWebServiceUtils();
        String oneCardInfo = GsWebServiceUtils.GetCardInfo(cardNo);
        Log.e(TAG, "调用一卡通信息接口返回数据为：" + oneCardInfo);
        //如果一卡通信息不为空
        if (oneCardInfo != null) {
            try {
                JSONObject jsonObject = new JSONObject(oneCardInfo);
                boolean isSuccess = jsonObject.getBoolean("flag");
                if (isSuccess) {
                    //如果获取一卡通信息成功
                    //可用金额
                    final double amount = jsonObject.getDouble("amount");
                    if (amount > Double.parseDouble(money)) {
                        balance = amount - Double.parseDouble(money);
                        OneCardConsume(cardNo, Double.parseDouble(money), balance);
                    } else {
                        CunPayActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Dailog.ErrDialog(CunPayActivity.this, "", "卡内余额不足!" + "\n" + "剩余金额:" + amount);
                                ToastUtils.show(CunPayActivity.this, "卡内余额不足!");
                                DialogUtil.cancelDownloadDialog();
                            }
                        });
                    }
                } else {
                    final String erro = jsonObject.getString("erro");
                    //如果获取一卡通信息失败
                    CunPayActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.show(CunPayActivity.this, erro);
                            DialogUtil.cancelDownloadDialog();
                        }
                    });
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            //如果一卡通信息为空
            CunPayActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.show(CunPayActivity.this, "获取一卡通信息失败!");
                    DialogUtil.cancelDownloadDialog();
                }
            });


        }
    }

    /**
     * 一卡通消费
     *
     * @param cardNum 一卡通物理号
     * @param money   消费金额
     * @param balance 余额
     */
    public void OneCardConsume(final String cardNum, final double money, final double balance) {
        GsWebServiceUtils gs = new GsWebServiceUtils();
        String result = GsWebServiceUtils.GetCardConsumption(cardNum, money);
        LogUtil.i(TAG, "一卡通消费返回数据--->" + result);
        if (null != result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                boolean isSuccess = jsonObject.getBoolean("flag");
                if (isSuccess) {
                    final int trandno = jsonObject.getInt("trandno");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogUtil.cancelDownloadDialog();
                            Toast.makeText(CunPayActivity.this, "一卡通支付成功！", Toast.LENGTH_LONG).show();
                            //清空输入框
                            mainEditextPay.setText("");
                            DecimalFormat df = new DecimalFormat("###0.00");
                            //打印
                            TpsN900PrintUtils.TpsN900PayPrint(CunPayActivity.this,
                                    "\n" + "--------------------------" +
                                            "\n" +
                                            "\n" + "一卡通物理号:" + cardNum +
                                            "\n" +
                                            "\n" + "支付时间:" + getTime() +
                                            "\n" +
                                            "\n" + "支付类型:" + "一卡通支付" +
                                            "\n" +
                                            "\n" + "支付金额:" + df.format(money) +
                                            "\n" +
                                            "\n" + "一卡通余额:" + df.format(balance) +
                                            "\n" +
                                            "\n" + "--------------------------" +
                                            "\n" +
                                            "\n" + "商户订单号:", trandno + "");
                        }
                    });
                } else {
                    final String erro = jsonObject.getString("erro");
                    CunPayActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.show(CunPayActivity.this, erro);
                            DialogUtil.cancelDownloadDialog();
                        }
                    });

                    return;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            CunPayActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.show(CunPayActivity.this, "一卡通支付失败!");
                    DialogUtil.cancelDownloadDialog();
                }
            });

            return;
        }
    }

    @Override
    public void onReadCardOk(final String cardNo) {
        DialogUtil.cancelDownloadDialog();
        new Thread(new Runnable() {
            @Override
            public void run() {
                submitOneCardInfo(cardNo);
            }
        }).start();
    }


    public class ProBroadcastReceiver extends BroadcastReceiver {
        public ProBroadcastReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case "Code":
                    DeviceN900(CunPayActivity.this);
                    break;
                default:
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            qrcode = data.getStringExtra("result");
            if ("支付宝".equals(PayCode)) {
                String payNumber = qrcode.substring(0, 2);
                if ("28".equals(payNumber)) {//支付宝
                    sendMessageInfo(handler, 1);
                } else if ("10".equals(payNumber) || "11".equals(payNumber) || "12".equals(payNumber) || "13".equals(payNumber) || "14".equals(payNumber) || "15".equals(payNumber)) {

                    ToastUtils.show(this, "付款码有误");
                } else {
                    ToastUtils.show(this, "付款码有误");
                }
            } else if ("微信".equals(PayCode)) {
                String payNumber = qrcode.substring(0, 2);
                if ("28".equals(payNumber)) {//支付宝
                    ToastUtils.show(this, "付款码有误");
                } else if ("10".equals(payNumber) || "11".equals(payNumber) || "12".equals(payNumber) || "13".equals(payNumber) || "14".equals(payNumber) || "15".equals(payNumber)) {
                    sendMessageInfo(handler, 1);

                } else {
                    ToastUtils.show(this, "付款码有误");
                }
            }

            return;
        } else {
            ToastUtils.show(CunPayActivity.this, "扫描结果为空");

        }
    }

    public void PayMnet(final String random, final String payResult, final String editMoney) {
        if ("微信".equals(PayCode)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    PayMentData = AliAndWePayMent.PayMentAndRefund("Payment", random, "", payResult, editMoney + "", "", "", "" + "");

                    if ("-1".equals(PayMentData) || TextUtils.isEmpty(PayMentData)) {
                        if (!TextUtils.isEmpty(ErrUtils.ParameterErr)) {
                            Log.e("StringPayMent", ErrUtils.ParameterErr);
                            HttpUtils.exitProgressDialog(progressDialog);
                        } else if (!TextUtils.isEmpty(ErrUtils.PaymentCodeErr)) {
                            Log.e("StringPayMent", ErrUtils.PaymentCodeErr);
                            HttpUtils.exitProgressDialog(progressDialog);
                        }
                    } else {
                        sendMessageInfo(handler, 0);

                    }


                }
            }).start();

        } else if ("支付宝".equals(PayCode)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    PayMentData = AliAndWePayMent.PayMentAndRefund("Payment", random, "", payResult, editMoney + "", "", "", "" + "");
                    if (PayMentData == null) {
                        HttpUtils.exitProgressDialog(progressDialog);
                        handler.sendEmptyMessageDelayed(4, 10);
                    } else {
                        if ("-1".equals(PayMentData) || TextUtils.isEmpty(PayMentData)) {
                            if (!TextUtils.isEmpty(ErrUtils.ParameterErr)) {
                                Log.e("StringPayMent", ErrUtils.ParameterErr);
                                HttpUtils.exitProgressDialog(progressDialog);
                            } else if (!TextUtils.isEmpty(ErrUtils.PaymentCodeErr)) {
                                Log.e("StringPayMent", ErrUtils.PaymentCodeErr);
                                HttpUtils.exitProgressDialog(progressDialog);
                            }
                        } else {
                            sendMessageInfo(handler, 0);
                        }
                    }


                }
            }).start();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Pro != null) {
            unregisterReceiver(Pro);
            Pro = null;
        }

    }


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
                            HttpUtils.exitProgressDialog(progressDialog);
                        } else if (!TextUtils.isEmpty(ErrUtils.PaymentCodeErr)) {
                            Log.e("StringPayMent", ErrUtils.PaymentCodeErr);
                            HttpUtils.exitProgressDialog(progressDialog);
                        }
                    } else {
                        //支付宝支付与微信支付完之后清除
                        handler.sendEmptyMessageDelayed(0, 10);

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
                            HttpUtils.exitProgressDialog(progressDialog);
                        } else if (!TextUtils.isEmpty(ErrUtils.PaymentCodeErr)) {
                            Log.e("StringPayMent", ErrUtils.PaymentCodeErr);
                            HttpUtils.exitProgressDialog(progressDialog);
                        }
                    } else {
                        //支付宝支付与微信支付完之后清除
                        handler.sendEmptyMessageDelayed(0, 10);
                    }

                }
            }).start();

        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            openActivityAndCloseThis(MainActivity.class);
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }
}
