package com.example.tps900.tps900.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.tps900.tps900.Bean.Alipay_Entity;
import com.example.tps900.tps900.Bean.Alipay_trade_query_response;
import com.example.tps900.tps900.Bean.TeamOrderForTicketsBean;
import com.example.tps900.tps900.Bean.WeChat_Entity;
import com.example.tps900.tps900.R;
import com.example.tps900.tps900.Utlis.AliAndWePayMent;
import com.example.tps900.tps900.Utlis.Constant;
import com.example.tps900.tps900.Utlis.Dailog;
import com.example.tps900.tps900.Utlis.ThreadPoolUtils;
import com.example.tps900.tps900.Webservice.GsWebServiceUtils;
import com.godfery.Utils.ToastUtils;
import com.godfery.pay.ErrUtils;
import com.godfery.pay.HttpUtils;
import com.godfery.Zxing.CaptureActivity;

import java.util.List;

import static com.example.tps900.tps900.Utlis.Dailog.DismissDialog;
import static com.example.tps900.tps900.Utlis.Dailog.ErrDialog2;
import static com.example.tps900.tps900.Utlis.OtherUtils.getNum;
import static com.example.tps900.tps900.Utlis.OtherUtils.sendMessageInfo;
import static com.example.tps900.tps900.Utlis.OtherUtils.setConstants;
import static com.godfery.pay.OtherUtils.NormalDialogStyleOne;


public class Team_PayActivity extends Activity implements View.OnClickListener {

    public static final String TAG = "Team_PayActivity";
    private Intent intent;
    private String money;
    private String OrderID = "";
    private String PayMentData = "";
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0://金额有误
                    Log.e("StringPayMent", PayMentData);
                    HttpUtils.exitProgressDialog(progressDialog);
                    if (PayMentData.length() > 50) {
                        money = "";
                        // TODO 去掉转义字符
                        PayMentData = PayMentData.replace("\\", "");
                        PayMentData = PayMentData.substring(2, PayMentData.length() - 2);
                        if ("支付宝".equals(PayCode)) {
                            Alipay_Entity alipay_entity = JSON.parseObject(PayMentData, Alipay_Entity.class);
                            Alipay_trade_query_response ar = alipay_entity.getAlipay_trade_query_response();
                            if ("Success".equals(ar.getMsg()) && "TRADE_SUCCESS".equals(ar.getTrade_status())) {
                                refund_PayID = ar.getTrade_no();
                                payForALiPay(ar.getTrade_no(), true);
                            } else {
                                NormalDialogStyleOne(Team_PayActivity.this, "支付宝支付失败\n" + ar.getMsg());
                            }

                        } else if ("微信".equals(PayCode)) {
                            WeChat_Entity weChat_entity = JSON.parseObject(PayMentData, WeChat_Entity.class);
                            if ("SUCCESS".equals(weChat_entity.getResult_code())
                                    && "SUCCESS".equals(weChat_entity.getReturn_code())
                                    && "SUCCESS".equals(weChat_entity.getTrade_state())) {
                                refund_PayID = weChat_entity.getTransaction_id();
                                payForWeChat(weChat_entity.getTransaction_id(), true);
                            } else {
                                NormalDialogStyleOne(Team_PayActivity.this, "微信支付失败\n" + weChat_entity.getReturn_msg());
                            }

                        }

                    } else {
                        NormalDialogStyleOne(Team_PayActivity.this, "支付失败！！！");
                    }


                    break;

                case 2:
                    Dailog.ErrDialog2(Team_PayActivity.this, "当前无网络\n点击确定设置网络", "1");
                    break;

                default:
                    break;
            }
        }
    };
    private IntenterBoradCastReceiver receiver;
    private String PayCode = "";
    ProgressDialog progressDialog;
    private String refund_PayID = "";
    private String refund_OderID = "";
    private String refund_Money = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teampay);
        initView();
    }

    /**
     * 初始化控件
     */
    public void initView() {
        progressDialog = new ProgressDialog(this);
        intent = getIntent();
        money = intent.getStringExtra("money");
        //现金
        ImageView rb_cash_team = (ImageView) findViewById(R.id.rb_cash_team);
        //微信
        ImageView rb_weChat_team = (ImageView) findViewById(R.id.rb_weChat_team);
        //支付宝
        ImageView rb_aLiPay_team = (ImageView) findViewById(R.id.rb_aLiPay_team);

        TextView tv_orderAmt_team = (TextView) findViewById(R.id.tv_orderAmt_team);
        tv_orderAmt_team.setText(money);

        rb_cash_team.setOnClickListener(this);
        rb_weChat_team.setOnClickListener(this);
        rb_aLiPay_team.setOnClickListener(this);


    }

    /**
     * 操作数据
     */
    public void initData() {
        setConstants(this);

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.rb_cash_team) {
            String payTime = intent.getStringExtra("payTime");
            String thirdNo = intent.getStringExtra("thirdNo");
            List resultList = (List) intent.getSerializableExtra("resultList");
            ThreadPoolUtils.runTaskInThread(new Runnable() {
                @Override
                public void run() {
                    final String result = GsWebServiceUtils.requestTeamOrderForTickets(resultList, payTime, thirdNo, money);
                    Log.e("团队核销返回信息..", result);
                    if (null != result) {
                        //如果不为null
                        TeamOrderForTicketsBean teamOrderForTicketsBean = JSON.parseObject(result, TeamOrderForTicketsBean.class);
                        if (teamOrderForTicketsBean.Success) {
                            ThreadPoolUtils.runTaskInUIThread(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtils.show(Team_PayActivity.this, "支付成功!");
                                    Intent intent = new Intent();
                                    intent.putExtra("isTrue", teamOrderForTicketsBean.Success);
                                    intent.putExtra("flag", "cash");
                                    setResult(RESULT_OK, intent);
                                    finish();
                                }
                            });
                        } else {
                            ThreadPoolUtils.runTaskInUIThread(new Runnable() {
                                @Override
                                public void run() {
                                    ErrDialog2(Team_PayActivity.this, "带团订单核销失败!", "");
                                }
                            });
                        }

                    } else {
                        ThreadPoolUtils.runTaskInUIThread(new Runnable() {
                            @Override
                            public void run() {
                                ErrDialog2(Team_PayActivity.this, "带团订单核销失败! ", "");
                            }
                        });
                    }
                }
            });
        } else if (id == R.id.rb_weChat_team) {
            //跳转到扫描界面
            Constant.FLAG = false;
            PayCode = "微信";
            Intent intent = new Intent(Team_PayActivity.this, CaptureActivity.class);
            startActivityForResult(intent, 100);
        } else if (id == R.id.rb_aLiPay_team) {

            //跳转到扫描
            Constant.FLAG = false;
            PayCode = "支付宝";
            intent = new Intent(Team_PayActivity.this, CaptureActivity.class);
            startActivityForResult(intent, 100);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //扫描成功
        if (data != null) {
            String result = data.getStringExtra("result");
            if ("支付宝".equals(PayCode)) {
                if (null != result) {
                    PayMnet(getNum(), result, money);
                } else {
                    //未扫描到数据
                    ToastUtils.show(this, "未扫描到数据!");
                    return;
                }
            } else if ("微信".equals(PayCode)) {
                if (null != result) {
                    PayMnet(getNum(), result, money);

                } else {
                    ToastUtils.show(this, "未扫描到数据");
                    return;
                }
            }
        } else {
            ToastUtils.show(this, "未扫描到数据");
        }


    }

    private void payForALiPay(final String thirdNo, boolean isTrue) {
        //支付宝支付成功后将获取的交易流水号带到前台界面
        ToastUtils.show(this, "支付成功");
        Intent intent = new Intent();
        intent.putExtra("flag", "aLiPay");
        intent.putExtra("isTrue", isTrue);
        intent.putExtra("thirdNo", thirdNo);
        intent.putExtra("refund_PayID", refund_PayID);
        intent.putExtra("refund_OderID", refund_OderID);
        intent.putExtra("refund_Money", refund_Money);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void payForWeChat(final String thirdNo, boolean isTrue) {
        ToastUtils.show(Team_PayActivity.this, "支付成功");
        Intent intent = new Intent();
        intent.putExtra("flag", "weChat");
        intent.putExtra("isTrue", isTrue);
        intent.putExtra("thirdNo", thirdNo);//交易流水号
        intent.putExtra("refund_PayID", refund_PayID);
        intent.putExtra("refund_OderID", refund_OderID);
        intent.putExtra("refund_Money", refund_Money);
        setResult(RESULT_OK, intent);
        finish();


    }

    public void PayMnet(final String random, final String payResult, final String editMoney) {
        refund_OderID = random;
        refund_Money = editMoney;
        if ("微信".equals(PayCode)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    PayMentData = AliAndWePayMent.PayMentAndRefund("Payment", random, "", payResult, editMoney + "", "", "", "" + "");
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
                        handler.sendEmptyMessageDelayed(0, 10);//支付宝支付与微信支付完之后清除
                        sendMessageInfo(handler, 0, "支付");

                    }
                }
            }).start();

        } else if ("支付宝".equals(PayCode)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    PayMentData = AliAndWePayMent.PayMentAndRefund("Payment", random, "", payResult, editMoney + "", "", "", "" + "");
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
                        sendMessageInfo(handler, 0, "支付");
                    }

                }
            }).start();

        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Log.e("啦啦啦啦------------>", "退出了---------------");
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
    }

    //监听网络状态变化的广播接收器
    public class IntenterBoradCastReceiver extends BroadcastReceiver {

        private ConnectivityManager mConnectivityManager;
        private NetworkInfo netInfo;

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            String action = intent.getAction();
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                netInfo = mConnectivityManager.getActiveNetworkInfo();
                if (netInfo != null && netInfo.isAvailable()) {
                    DismissDialog();
                    /////////////网络连接
                    String name = netInfo.getTypeName();

                    if (netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                        /////WiFi网络
                    } else if (netInfo.getType() == ConnectivityManager.TYPE_ETHERNET) {
                        /////有线网络
                    } else if (netInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                        /////////3g网络
                    }
                } else {
                    ////////网络断开
                    ToastUtils.show(Team_PayActivity.this, "无网络");
                    handler.sendEmptyMessageDelayed(2, 10);//但前无网络
                }
            }

        }
    }

}
