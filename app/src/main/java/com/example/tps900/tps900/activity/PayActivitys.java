package com.example.tps900.tps900.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.tps900.tps900.Bean.DetailBean;
import com.example.tps900.tps900.Bean.PayBean;

import com.example.tps900.tps900.Bean.SETTABLEINFOBean;
import com.example.tps900.tps900.Bean.VipApilyBean;
import com.example.tps900.tps900.Bean.VipLoginBean;
import com.example.tps900.tps900.R;
import com.example.tps900.tps900.Utlis.Constant;
import com.example.tps900.tps900.Utlis.Dailog;
import com.example.tps900.tps900.Utlis.DialogUtil;
import com.example.tps900.tps900.Utlis.LogUtil;
import com.example.tps900.tps900.Utlis.MessageEvent_Food;
import com.example.tps900.tps900.Utlis.ThreadPoolUtils;
import com.example.tps900.tps900.Utlis.TimeUtils;
import com.example.tps900.tps900.Utlis.TpsN900PrintUtils;
import com.example.tps900.tps900.Utlis.UpdataDialog;
import com.example.tps900.tps900.Webservice.GsWebServiceUtils;
import com.example.tps900.tps900.adapters.PayAdapter;
import com.godfery.Utils.ToastUtils;
import com.godfery.Zxing.CaptureActivity;
import com.godfery.pay.HttpUtils;
import com.google.gson.Gson;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.example.tps900.tps900.Utlis.Dailog.DismissDialog;
import static com.example.tps900.tps900.Utlis.Dailog.ErrDialog2;
import static com.example.tps900.tps900.Utlis.MessageConstanse.DropOutPayActivity;
import static com.example.tps900.tps900.Utlis.MessageConstanse.SubmitGoodsInfo;
import static com.example.tps900.tps900.Utlis.OtherUtils.IsMoneyTrue;
import static com.example.tps900.tps900.Utlis.OtherUtils.sendMessageInfo;


public class PayActivitys extends BaseActivity implements BaseActivity.CardCodeInterface {

    public static final String TAG = "PayActivitys";
    private TextView shouldMoney_tv;
    private CheckBox vipFlag;
    private ListView lv_payType;
    private TextView actualMoney;
    private ArrayList<PayBean.DtBean> payList;
    private PayAdapter adapter;
    private ArrayList<PayBean.DtBean> list_delete;
    private Button btn_confirm_order;
    private double payType;
    private String money;
    private Button btn_cancel_order;
    private String codeNum = "", payType_str;
    private double cardMoney;
    private final int SubmitOrder = 3;
    private final int Food_ReturnPay = 4;
    //通用失败
    private final int Failed = 5;
    //清空
    private final int Emptied = 6;
    private List<SETTABLEINFOBean.GoodlsBean> goodlsBeanList;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SubmitOrder:
                    if (!list_delete.isEmpty()) {
                        //如果支付集合不为空
                        if (list_delete.size() == 1) {
                            final PayBean.DtBean payBean = list_delete.get(0);
                            payType = payBean.getNPAYMENTTYPEX();
                            //如果是现金直接提交销售数据，微信或支付宝则打开扫描头
                            if (1.0 == payType) {
                                payType_str = "现金";
                                DialogUtil.showPayDialog(PayActivitys.this);
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        getOrderIDAndsubmit(payBean, Constant.BEANS, null, null);
                                    }
                                }).start();

                            } else if (3.0 == payType) {
                                HttpUtils.showProgressDialog(progressDialog);
                                payType_str = "一卡通";
                                DeviceN900(PayActivitys.this);
                            } else { //点击确认支付按钮，打开扫描头
                                Intent intent = new Intent(PayActivitys.this, CaptureActivity.class);
                                PayActivitys.this.startActivityForResult(intent, 1001);
                            }

                        } else if (list_delete.size() > 1) {
                            ToastUtils.show(PayActivitys.this, "不支持混合支付请重新选择");
                            return;
                        }
                    } else {
                        ToastUtils.show(PayActivitys.this, "请选择支付方式");
                        return;
                    }
                    break;
                //通用失败原因
                case Failed:
                    DialogUtil.cancelDownloadDialog();
                    HttpUtils.exitProgressDialog(progressDialog);
                    String msgInfo = msg.obj.toString();
                    ErrDialog2(PayActivitys.this, msgInfo, "");
                    break;
                case Emptied://清除数据
                    DialogUtil.cancelDownloadDialog();
                    HttpUtils.exitProgressDialog(progressDialog);
                    Intent intent = new Intent();
                    intent.putExtra("flag", true);
                    setResult(RESULT_OK, intent);
                    PayActivitys.this.finish();
                    break;
                case Food_ReturnPay:
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("goodsInStockList", (Serializable) goodlsBeanList);
                    openActivity(InStockActivity.class, bundle);
                    break;
                default:
                    break;
            }
        }
    };

    private double countmoney;

    /**
     * 会员积分
     */
    private Boolean VIPflag = false;
    UpdataDialog updataDialog;
    private Button pop_cancel;
    private Button pop_anfirm;
    private EditText vip_name;
    private EditText vip_pass;
    private ImageView vipScan;
    private ImageView vipDelete;
    private String vipName;
    private String vipPass;
    private String vipCardID;
    private String vipEnablePoint;
    private String vipCall;
    private String billNumber;
    private VipLoginBean vipLoginBean;
    private Boolean vipErrPay;

    /**
     * 设置布局
     *
     * @return
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_pay;
    }

    /**
     * 初始化控件
     */
    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        Bundle bundle = getIntent().getExtras();
        money = bundle.get("actualMoney").toString();
        countmoney = Double.valueOf(money);
        payList = new ArrayList<>();
        getResultFromServer();
        findView();
    }

    /**
     * 操作数据
     */
    @Override
    public void initData() {
        list_delete = new ArrayList<>();
        setCardInterface(this);
    }

    private void submitOneCardInfo(String cardNo) {

        PayActivitys.this.runOnUiThread(new Runnable() {
            @Override

            public void run() {
                HttpUtils.showProgressDialog(progressDialog);
            }
        });

        String oneCardInfo = GsWebServiceUtils.getOneCardinfo(cardNo);

        //如果一卡通信息不为空
        if (oneCardInfo != null) {
            try {
                JSONObject jsonObject = new JSONObject(oneCardInfo);
                boolean isSuccess = jsonObject.getBoolean("isSuccess");
                if (isSuccess) {
                    //如果获取一卡通信息成功
                    JSONArray dt = jsonObject.getJSONArray("dt");
                    JSONObject info = dt.getJSONObject(0);

                    String sprintno = info.getString("SPRINTNO");
                    final double balance = info.getDouble("BALANCE");

                    if (balance > Double.parseDouble(money) || balance == Double.parseDouble(money)) {
                        PayBean.DtBean payBean = list_delete.get(0);
                        cardMoney = balance - Double.parseDouble(money);
                        getOrderIDAndsubmit(payBean, Constant.BEANS, cardNo, null);

                    } else {
                        PayActivitys.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Dailog.ErrDialog(PayActivitys.this, "", "卡内余额不足!" + "\n" + "剩余金额:" + balance);
                                ToastUtils.show(PayActivitys.this, "卡内余额不足!");
                                DialogUtil.cancelDownloadDialog();
                            }
                        });
                    }


                } else {
                    //如果获取一卡通信息失败
                    PayActivitys.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            HttpUtils.exitProgressDialog(progressDialog);
                            ToastUtils.show(PayActivitys.this, "获取一卡通信息失败!");
                        }
                    });

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            //如果一卡通信息为空
            PayActivitys.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.show(PayActivitys.this, "获取一卡通信息失败!");
                    HttpUtils.exitProgressDialog(progressDialog);
                }
            });


        }
    }

    private void getResultFromServer() {
        String payType = GsWebServiceUtils.getPayType(Constant.ZONEID);
        LogUtil.i(TAG, "Constant.ZONEID---->" + Constant.ZONEID);
        LogUtil.i(TAG, "payType----->" + payType);

        if (null != payType) {
            PayBean paybean = JSON.parseObject(payType, PayBean.class);
            if (paybean.isSuccess && paybean.dt.size() > 0) {
                for (int i = 0; i < paybean.dt.size(); i++) {
                    PayBean.DtBean dtBean = paybean.dt.get(i);
                    if (dtBean.getNPAYMENTTYPEX() == 1 || dtBean.getNPAYMENTTYPEX() == 2
                            || dtBean.getNPAYMENTTYPEX() == 3
                            || dtBean.getNPAYMENTTYPEX() == 5
                            || dtBean.getNPAYMENTTYPEX() == 8
                            || dtBean.getNPAYMENTTYPEX() == 11
                            ) {
                        payList.add(dtBean);
                    }

                }
            } else {
                ToastUtils.show(this, "获取支付方式失败!");
                LogUtil.i(TAG, "返回结果为null");
            }
        }
    }

    private void findView() {
        goodlsBeanList = new ArrayList<>();
        shouldMoney_tv = (TextView) findViewById(R.id.shouldMoney_tv);
        shouldMoney_tv.setText(money);
        //会员积分
        vipFlag = (CheckBox) findViewById(R.id.vip_flag);
        vipFlag.setChecked(VIPflag);
        vipFlag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    VIPflag = true;

                } else {
                    VIPflag = false;

                }
            }
        });
//--------------------------------------------
        lv_payType = (ListView) findViewById(R.id.lv_payType);
        actualMoney = (TextView) findViewById(R.id.actualMoney);
        actualMoney.setText(money);
        btn_confirm_order = (Button) findViewById(R.id.btn_confirm_order);
        btn_cancel_order = (Button) findViewById(R.id.btn_cancel_order);
        btn_cancel_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("flag", false);
                setResult(RESULT_OK, intent);
                PayActivitys.this.finish();
            }
        });

        btn_confirm_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessageInfo(handler, SubmitOrder);
            }
        });

        adapter = new PayAdapter(payList, this);

        lv_payType.setAdapter(adapter);
//-------------------------------------
        lv_payType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PayBean.DtBean payBean = payList.get(position);
                list_delete.clear();
                adapter.setSelectedIndex(position);
                list_delete.add(payBean);
                adapter.notifyDataSetChanged();
                if (VIPflag == false) {
                    sendMessageInfo(handler, SubmitOrder);
                } else {
                    updataDialog = new UpdataDialog(PayActivitys.this, R.layout.updata_pop,
                            new int[]{});
                    updataDialog.show();
                    pop_cancel = (Button) updataDialog.findViewById(R.id.pop_cancel);
                    pop_anfirm = (Button) updataDialog.findViewById(R.id.pop_anfirm);
                    vip_name = (EditText) updataDialog.findViewById(R.id.vip_name);
                    vip_pass = (EditText) updataDialog.findViewById(R.id.vip_pass);
                    vipScan = (ImageView) updataDialog.findViewById(R.id.vip_scan);
                    vipDelete = (ImageView) updataDialog.findViewById(R.id.vip_delete);
                    //清除账号
                    vipDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            vip_name.setText("");
                            vip_pass.setText("");
                        }
                    });
                    //扫描会员二维码
                    vipScan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(PayActivitys.this, CaptureActivity.class);
                            startActivityForResult(intent, 1003);
                        }
                    });
                    pop_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            updataDialog.dismiss();
                        }
                    });
                    pop_anfirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (vip_name.getText().toString().trim().equals("") || vip_name.getText().toString().trim() == null) {
                                Toast.makeText(PayActivitys.this, "请输入会员账号", Toast.LENGTH_SHORT).show();
                                return;
                            }
//                        if(vip_pass.getText().toString().trim().equals("")||vip_pass.getText().toString().trim() == null)
//                        {
//                            Toast.makeText(PayActivitys.this,"请输入密码",Toast.LENGTH_SHORT).show();
//                            return;
//                        }
                            String s = GsWebServiceUtils.queryVIP(vip_name.getText().toString().trim(), vip_pass.getText().toString().trim());
                            if (!"err".equals(s)) {
                                vipLoginBean = new Gson().fromJson(s, VipLoginBean.class);
                                if (vipLoginBean.isSucceed() == false) {
                                    Toast.makeText(PayActivitys.this, vipLoginBean.getMsg(), Toast.LENGTH_SHORT).show();
                                    return;
                                } else {
                                    vipEnablePoint = vipLoginBean.getDiscountModel().getEnablePoint();
                                    vipName = vipLoginBean.getDiscountModel().getTrueName();
                                    vipPass = vipLoginBean.getDiscountModel().getPwd();
                                    vipCall = vipLoginBean.getDiscountModel().getMobile();
                                    vipCardID = vipLoginBean.getDiscountModel().getCardId();
                                    sendMessageInfo(handler, SubmitOrder);
                                }


                            } else {
                                Toast.makeText(PayActivitys.this, "支付失败", Toast.LENGTH_SHORT).show();
                                return;
                            }


                        }
                    });

                }


            }
        });
//----------------------------------------
    }

    private void submitData(String detailID, ArrayList<DetailBean.DtBean> list, double money, double payType, String cardNO, String tradeNo) throws Exception {
        //会员支付
        if (VIPflag == true) {
            String vipApily = "{\"cardId\":" + "\"" + vipCardID + "\"" + ",\"password\":" + "\"" + vipPass + "\"" + ",\"userAccount\":" + "\"" + "10000" + "\"" + ",\"totalMoney\":" + "\"" + money + "\"" + ",\"totalPaid\":" + "\"" + money + "\"" + ",\"paidMoney\":" + "\"" + money + "\"" + ",\"paidPoint\":" + "\"" + 0 + "\"" + ",\"paidValue\":" + "\"" + 0 + "\"" + ",\"paidCard\":" + "\"" + 0 + "\"" + ",\"paidOther\":" + "\"" + 0 + "\"" + ",\"meno\":" + "\"" + payType_str + "\"" + "}";
            String s = GsWebServiceUtils.VIPApily(vipApily);
            if (!"err".equals(s)) {
                VipApilyBean vipApilyBean = new Gson().fromJson(s, VipApilyBean.class);
                if (vipApilyBean.isSucceed() == true) {
                    billNumber = vipApilyBean.getBillNumber();
                    vipErrPay = true;
                } else {
                    Toast.makeText(PayActivitys.this, vipApilyBean.getMsg(), Toast.LENGTH_SHORT).show();
                    vipErrPay = false;
                    return;
                }

            } else {
                Toast.makeText(PayActivitys.this, "支付失败", Toast.LENGTH_SHORT).show();
                vipErrPay = false;
                return;
            }
        }
        //------------------------------------------

        String json = "{\"NDEALID\"" + ":" + detailID + ",\"NGZONEID\"" + ":" + Constant.ZONEID + ",\"NTERMINALID\"" + ":" + Constant.TERMINALID + ",\"NEMPID\"" + ":" + Constant.EMPID + ",\"DDATE\"" + ":\"" + TimeUtils.time() + "\"" + ",\"NPAYMENT\"" + ":" + payType + ",\"NAMOUNT\"" + ":" + money + ",\"SCARD\"" + ":" + cardNO + ",\"TRANDE_NO\"" + ":\"" + tradeNo + "\"" + ",\"CommInfos\":";//订单明细信息

        if (list.size() == 1) {
            DetailBean.DtBean detailBean = list.get(0);
            json = json + "[{\"NCOMMID\"" + ":" + detailBean.getNCOMMID() + ",\"NPCOMMID\"" + ":" + detailBean.getNPCOMMID() + ",\"PNUMBER\"" + ":" + detailBean.getPNUMBER() + ",\"NNUMBER\"" + ":" + detailBean.getGoodsCount() + ",\"NPRICE\"" + ":" + detailBean.getPRICE() + "}]}";
        } else if (list.size() == 2) {

            json = json + "[{\"NCOMMID\"" + ":" + list.get(0).getNCOMMID() + ",\"NPCOMMID\"" + ":" + list.get(0).getNPCOMMID() + ",\"PNUMBER\"" + ":" + list.get(0).getPNUMBER() + ",\"NNUMBER\"" + ":" + list.get(0).getGoodsCount() + ",\"NPRICE\"" + ":" + list.get(0).getPRICE() + "},{" + "\"NCOMMID\"" + ":" + list.get(1).getNCOMMID() + ",\"NPCOMMID\"" + ":" + list.get(1).getNPCOMMID() + ",\"PNUMBER\"" + ":" + list.get(1).getPNUMBER() + ",\"NNUMBER\"" + ":" + list.get(1).getGoodsCount() + ",\"NPRICE\"" + ":" + list.get(1).getPRICE() + "}]}";
        } else {

            for (int i = 0; i < list.size(); i++) {

                if (i == 0) {
                    json = json + "[{\"NCOMMID\"" + ":" + list.get(0).getNCOMMID() + ",\"NPCOMMID\"" + ":" + list.get(0).getNPCOMMID() + ",\"PNUMBER\"" + ":" + list.get(0).getPNUMBER() + ",\"NNUMBER\"" + ":" + list.get(0).getGoodsCount() + ",\"NPRICE\"" + ":" + list.get(0).getPRICE() + "}";
                } else if (i == list.size() - 1) {
                    json = json + ",{\"NCOMMID\"" + ":" + list.get(list.size() - 1).getNCOMMID() + ",\"NPCOMMID\"" + ":" + list.get(list.size() - 1).getNPCOMMID() + ",\"PNUMBER\"" + ":" + list.get(list.size() - 1).getPNUMBER() + ",\"NNUMBER\"" + ":" + list.get(list.size() - 1).getGoodsCount() + ",\"NPRICE\"" + ":" + list.get(list.size() - 1).getPRICE() + "}]}";
                } else {
                    json = json + ",{\"NCOMMID\"" + ":" + list.get(i).getNCOMMID() + ",\"NPCOMMID\"" + ":" + list.get(i).getNPCOMMID() + ",\"PNUMBER\"" + ":" + list.get(i).getPNUMBER() + ",\"NNUMBER\"" + ":" + list.get(i).getGoodsCount() + ",\"NPRICE\"" + ":" + list.get(i).getPRICE() + "}";
                }
            }
        }

        LogUtil.i(TAG, "json----->" + json);
        String result = GsWebServiceUtils.submitInfo(json);
        DismissDialog();
        LogUtil.i(TAG, "result----->" + result);
        if (!"err".equals(result)) {
            SETTABLEINFOBean settableinfoBean = JSON.parseObject(result, SETTABLEINFOBean.class);
            if (settableinfoBean.isSuccess()) {
                double formatMoney = Double.parseDouble(new DecimalFormat("#0.00").format(money));
                double CardMoney = Double.parseDouble(new DecimalFormat("#0.00").format(cardMoney));
//                RePrintBean.RePrintGoodBean rePrintGoodBean =new RePrintBean.RePrintGoodBean();
//                rePrintGoodBean.setCardMoney(CardMoney);
//                rePrintGoodBean.setCardNO(cardNO);
//                rePrintGoodBean.setPayType_str(payType_str);
//                rePrintGoodBean.setList(list);
//                rePrintGoodBean.setMoney(money);
//                rePrintGoodBean.setDetailID(detailID);
//                Constant.RePrintBean.setRePrintGoodBeanList(rePrintGoodBean);
                if ("一卡通".equals(payType_str)) {
                    if (VIPflag == true) {
                        TpsN900PrintUtils.TpsN900OneCardPrintVip(PayActivitys.this, list, money, detailID, payType_str, cardNO, cardMoney, billNumber);

                    } else {
                        TpsN900PrintUtils.TpsN900OneCardPrint(PayActivitys.this, list, money, detailID, payType_str, cardNO, cardMoney);

                    }

                } else {
                    if (VIPflag == true) {
                        TpsN900PrintUtils.TpsN900PrintVip(PayActivitys.this, list, money, detailID, payType_str, billNumber);

                    } else {
                        TpsN900PrintUtils.TpsN900Print(PayActivitys.this, list, money, detailID, payType_str);

                    }
                }
                sendMessageInfo(handler, Emptied);
            } else {
                sendMessageInfo(handler, Failed, settableinfoBean.getError() + "\n提交商品销售数据失败!");
                goodlsBeanList.clear();
                if (settableinfoBean.getGoodls() != null) {
                    DetailBean.DtBean dtbean = null;
                    for (int j = 0; j < settableinfoBean.getGoodls().size(); j++) {
                        SETTABLEINFOBean.GoodlsBean goodisBean = settableinfoBean.getGoodls().get(j);
                        boolean flag = false;
                        for (int i = 0; i < Constant.BEANS.size(); i++) {
                            dtbean = Constant.BEANS.get(i);
                            boolean isGood = (dtbean.getNCOMMID().equals(goodisBean.getGoodsId()))
                                    && (dtbean.getVCOMMYNAME().equals(goodisBean.getGoodsName()));
                            if (isGood) {
                                flag = true;
                            }
                        }
                        if (flag) {
                            if (!goodisBean.IsSale && goodisBean.IsStock) {
                                boolean isgood = (dtbean.getNCOMMID().equals(goodisBean.getGoodsId())) && (dtbean.getVCOMMYNAME().equals(goodisBean.getGoodsName()));
                                if (isgood) {
                                    dtbean.setStockNums(goodisBean.getStockNums());
                                    dtbean.setSale(false);
                                    goodlsBeanList.add(goodisBean);
                                }
                            } else {
                                dtbean.setStockNums(goodisBean.getStockNums());
                                dtbean.setSale(true);
                            }
                        }

                    }
                    if (5 == payType || 8 == payType || 2 == payType || 3 == payType) {
                        dropOutPayActivity();
                    } else {
                        sendMessageInfo(handler, Food_ReturnPay);
                    }
                }


            }
        } else {
            if (vipErrPay == false) {

                String vipErrPay = "{\"userAccount\":" + "\"" + "10000" + "\"" + ",\"billNumber\":" + "\"" + billNumber + "\"" + ",\"meno\":" + "\"" + "支付异常" + "\"" +  "}";
                String s = GsWebServiceUtils.VIPPayErr(vipErrPay);
                if(!"err".equals(s))
                {
                    VipApilyBean vipApilyBean = new Gson().fromJson(s, VipApilyBean.class);
                    if (vipApilyBean.isSucceed() == true) {

                        Toast.makeText(PayActivitys.this, vipApilyBean.getMsg(), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(PayActivitys.this, vipApilyBean.getMsg(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                else{
                    Toast.makeText(PayActivitys.this, "会员支付撤销失败", Toast.LENGTH_SHORT).show();

                }
            }
            sendMessageInfo(handler, Failed, "提交销售数据失败!\n请检查网络/配置信息!");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case 1001:
                if (null != data) {
                    //扫描成功
                    final String result = data.getStringExtra("result");
                    LogUtil.i(TAG, "barCode----->" + result);
                    if (!TextUtils.isEmpty(result)) {
                        if (8.0 == payType) {
                            if (result.length() == 18 && (result.substring(0, 2).equals("10") || result.substring(0, 2).equals("12") || result.substring(0, 2).equals("13") || result.substring(0, 2).equals("14") || result.substring(0, 2).equals("15"))) {
                                DialogUtil.showPayDialog(PayActivitys.this);
                                payType_str = "微信";
                                //微信
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        PayBean.DtBean payBean = list_delete.get(0);
                                        getOrderIDAndsubmit(payBean, Constant.BEANS, result, null);

                                    }
                                }).start();

                            } else {
                                ErrDialog2(PayActivitys.this, "支付错误" + "\n" + "支付方式不匹配", "");
                            }

                        } else if (5.0 == payType) {
                            if (result.length() == 18 && result.substring(0, 2).equals("28")) {
                                DialogUtil.showPayDialog(PayActivitys.this);
                                payType_str = "支付宝";
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {

                                        PayBean.DtBean payBean = list_delete.get(0);
                                        getOrderIDAndsubmit(payBean, Constant.BEANS, result, null);

                                    }
                                }).start();
                            } else {
                                ErrDialog2(PayActivitys.this, "支付错误" + "\n" + "支付方式不匹配", "");
                            }

                        }
                    } else {
                        Log.e(TAG, "编码为空");
                    }

                } else {
                    //扫描失败
                    ToastUtils.show(this, "未扫描到数据!");
                }

                break;
            case 1003:
                if (null != data) {
                    //扫描成功
                    String result = data.getStringExtra("result");
                    vip_name.setText(result);
                } else {
                    ToastUtils.show(this, "未扫描到数据!");
                    return;
                }
                break;
            default:
                break;

        }

    }

    public void getOrderIDAndsubmit(PayBean.DtBean payBean, ArrayList<DetailBean.DtBean> list, String cardNo, String tradeNO) {

        String orderNo = GsWebServiceUtils.getOrderNo();
        LogUtil.i(TAG, "orderNo--->" + orderNo);

        if (null != orderNo) {
            try {
                JSONObject jsonObject = new JSONObject(orderNo);
                boolean isSuccess = jsonObject.getBoolean("isSuccess");
                if (isSuccess) {
                    String dealId = jsonObject.getString("NDEALID");
                    //提交销售信息
                    ThreadPoolUtils.runTaskInUIThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                double douMoney = Double.parseDouble(money);

                                if (IsMoneyTrue(douMoney)) {
                                    submitData(dealId, list, douMoney, payBean.getNIDX(), cardNo, tradeNO);
                                } else {
                                    sendMessageInfo(handler, Failed, "付款金额与实际金额不符请退出此界面刷新!");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } else {
                    PayActivitys.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.show(PayActivitys.this, "获取单号失败!");
                            DialogUtil.cancelDownloadDialog();
                        }
                    });

                    return;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            PayActivitys.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.show(PayActivitys.this, "获取单号失败!");
                    DialogUtil.cancelDownloadDialog();
                }
            });

            return;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Intent intent = new Intent();
            intent.putExtra("flag", true);
            setResult(RESULT_OK, intent);
            PayActivitys.this.finish();
            Constant.BEANS.clear();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 读取卡号成功回调
     *
     * @param cardNo 返回卡号
     */
    @Override
    public void onReadCardOk(String cardNo) {
        ThreadPoolUtils.runTaskInUIThread(new Runnable() {
            @Override
            public void run() {
                submitOneCardInfo(cardNo);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent_Food messageEvent) {
        String messageEventMsg = messageEvent.getMsg();
        switch (messageEventMsg) {
            case SubmitGoodsInfo:
                submitGoodsInfo();
                break;
            case DropOutPayActivity:
                dropOutPayActivity();
                break;
            default:
                break;
        }

    }

    /**
     * 提交可售信息商品
     */
    public void submitGoodsInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                money = "";
                double countMoney = 0;
                PayBean.DtBean payBean = list_delete.get(0);
                for (int i = 0; i < Constant.BEANS.size(); i++) {
                    DetailBean.DtBean dtBean = Constant.BEANS.get(i);
                    if (!dtBean.IsSale && dtBean.getStockNums() == 0.0 || dtBean.getStockNums() < 0.0) {
                        Constant.BEANS.remove(i);
                    } else if (!dtBean.IsSale && dtBean.getStockNums() > 0.0) {
                        dtBean.setGoodsCount((int) dtBean.getStockNums());
                    }
                }
                for (int i = 0; i < Constant.BEANS.size(); i++) {
                    DetailBean.DtBean dtBean = Constant.BEANS.get(i);
                    if (!dtBean.IsSale && dtBean.getStockNums() > 0.0) {
                        dtBean.setGoodsCount((int) dtBean.getStockNums());
                        countMoney += dtBean.getGoodsCount() * dtBean.getPRICE();
                    } else {
                        countMoney += dtBean.getGoodsCount() * dtBean.getPRICE();
                    }
                }

                money = String.valueOf(countMoney);
                if (Constant.BEANS.size() > 0) {
                    ThreadPoolUtils.runTaskInUIThread(new Runnable() {
                        @Override
                        public void run() {
                            HttpUtils.showProgressDialog(progressDialog);
                        }
                    });
                    getOrderIDAndsubmit(payBean, Constant.BEANS, null, null);
                } else {
                    ThreadPoolUtils.runTaskInUIThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.show(PayActivitys.this, "商品库存不足,请选择其他商品!");
                            Intent intent = new Intent();
                            intent.putExtra("flag", true);
                            setResult(RESULT_OK, intent);
                            PayActivitys.this.finish();
                        }
                    });
                }
            }
        }).start();
    }

    /**
     * 退出Activity处理信息
     */
    public void dropOutPayActivity() {
        for (int i = 0; i < Constant.BEANS.size(); i++) {
            DetailBean.DtBean dtBean = Constant.BEANS.get(i);
            if (!dtBean.IsSale && (dtBean.getStockNums() == 0.0 || dtBean.getStockNums() < 0.0)) {
                Constant.BEANS.remove(i);
            } else if (!dtBean.IsSale && dtBean.getStockNums() > 0.0) {
                dtBean.setGoodsCount((int) dtBean.getStockNums());
            }
        }
        if (Constant.BEANS.size() > 0) {
            Intent intent = new Intent();
            intent.putExtra("flag", false);
            setResult(RESULT_OK, intent);
            PayActivitys.this.finish();
        } else {
            ToastUtils.show(PayActivitys.this, "商品库存不足,请选择其他商品!");
            Intent intent = new Intent();
            intent.putExtra("flag", true);
            setResult(RESULT_OK, intent);
            PayActivitys.this.finish();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}


