package com.example.tps900.tps900.activity;

import android.app.ProgressDialog;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.tps900.tps900.Bean.DetailBean;
import com.example.tps900.tps900.Bean.PayBean;
import com.example.tps900.tps900.Bean.SETTABLEINFOBean;
import com.example.tps900.tps900.Bean.TableInfoBean;
import com.example.tps900.tps900.InterFace.MessageEvent;
import com.example.tps900.tps900.R;
import com.example.tps900.tps900.Utlis.Constant;
import com.example.tps900.tps900.Utlis.Dailog;
import com.example.tps900.tps900.Utlis.DialogUtil;
import com.example.tps900.tps900.Utlis.LogUtil;
import com.example.tps900.tps900.Utlis.MessageEvent_Food;
import com.example.tps900.tps900.Utlis.OtherUtils;
import com.example.tps900.tps900.Utlis.TimeUtils;
import com.example.tps900.tps900.Utlis.TpsN900PrintUtils;
import com.example.tps900.tps900.Webservice.GsWebServiceUtils;
import com.example.tps900.tps900.adapters.Pay_foodAdapter;
import com.godfery.Utils.ToastUtils;
import com.godfery.pay.HttpUtils;
import com.godfery.Zxing.CaptureActivity;
import com.telpo.tps550.api.TelpoException;
import com.telpo.tps550.api.nfc.Nfc;
import com.telpo.tps550.api.util.StringUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.example.tps900.tps900.Utlis.Dailog.ErrDialog2;
import static com.example.tps900.tps900.Utlis.OtherUtils.add;
import static com.example.tps900.tps900.Utlis.OtherUtils.sendMessageInfo;
import static com.example.tps900.tps900.Utlis.OtherUtils.sub;
import static com.example.tps900.tps900.Webservice.GsWebServiceUtils.getTableInfo;
import static com.example.tps900.tps900.Webservice.GsWebServiceUtils.submitFoodInfo;


public class Pay_foodActivity extends AppCompatActivity {

    public static final String TAG = "PayActivitys";

    private final int CHECK_NFC_TIMEOUT = 1;
    private final int SHOW_NFC_DATA = 2;
    private Nfc nfc;
    private ReadThread readThread;

    private TextView shouldMoney_tv;
    private ListView lv_payType;
    private TextView actualMoney;
    private ArrayList<PayBean.DtBean> payList;
    private Pay_foodAdapter adapter;
    private ArrayList<PayBean.DtBean> list_delete = new ArrayList<>();
    private Button btn_confirm_order;
    private double payType;
    private String tablemoney, foodmoney;
    private Button btn_cancel_order;
    private String codeNum = "";
    private String payType_str;

    //通用失败
    private final int Failed = 4;
    //清空
    private final int Emptied = 5;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CHECK_NFC_TIMEOUT: {
                    ToastUtils.show(Pay_foodActivity.this, "未检测到卡片超时 !");
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
                                submitOneCardInfo(codeNum);
                            }
                        }).start();

                    } else {
                        ToastUtils.show(Pay_foodActivity.this, "unknow type card!!");
                    }
                }
                break;

                //通用失败原因
                case Failed:
                    HttpUtils.exitProgressDialog(progressDialog);
                    String msgInfo = msg.obj.toString();
                    if (msgInfo.equals("取消订单")) {
                        ErrDialog2(Pay_foodActivity.this, "取消订单将清空所有菜品", "取消订单");
                    } else if (msgInfo.equals("选桌台")) {
                        ErrDialog2(Pay_foodActivity.this, "桌台已占用,点击确定其他桌台!!!", "选桌台");
                    } else {
                        ErrDialog2(Pay_foodActivity.this, msgInfo, "");
                    }

                    break;
                case Emptied://清除数据
                    HttpUtils.exitProgressDialog(progressDialog);
                    //清空餐饮界面
                    Intent intent = new Intent();
                    intent.putExtra("flag", true);
                    EventBus.getDefault().post(new MessageEvent(1002, -1, intent));
                    //选桌页面消失
                    EventBus.getDefault().post(new MessageEvent_Food("选桌消失"));
                    Pay_foodActivity.this.finish();
                    break;
                case SubmitOrder:
                    if (!TextUtils.isEmpty(pay_food_PeopleNum.getText().toString().trim())) {
                        if (!list_delete.isEmpty()) {
                            //如果支付集合不为空
                            if (list_delete.size() == 1) {
                                final PayBean.DtBean payBean = list_delete.get(0);
                                payType = payBean.getNPAYMENTTYPEX();
                                //如果是现金直接提交销售数据，微信或支付宝则打开扫描头
                                if (-1 == payType) {
                                    payType_str = "挂单";
                                    HttpUtils.showProgressDialog(progressDialog);
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            getOrderIDAndsubmit(payBean, Constant.BEANS, null, null);
                                        }
                                    }).start();
                                } else {
                                    if (1.0 == payType) {
                                        payType_str = "现金";
                                        HttpUtils.showProgressDialog(progressDialog);
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                getOrderIDAndsubmit(payBean, Constant.BEANS, null, null);
                                            }
                                        }).start();

                                    } else if (3.0 == payType) {
                                        HttpUtils.showProgressDialog(progressDialog);
                                        payType_str = "一卡通";
                                        DeviceN900();
                                    } else { //点击确认支付按钮，打开扫描头
                                        intent = new Intent(Pay_foodActivity.this, CaptureActivity.class);
                                        Pay_foodActivity.this.startActivityForResult(intent, 1001);
                                    }
                                }
                            } else if (list_delete.size() > 1) {
                                ToastUtils.show(Pay_foodActivity.this, "不支持混合支付请重新选择");
                                return;
                            }
                        } else {
                            ToastUtils.show(Pay_foodActivity.this, "请选择支付方式");
                            return;
                        }
                    } else {
                        ToastUtils.show(Pay_foodActivity.this, "请输入用餐人数");
                        return;
                    }
                    break;
                default:
                    break;
            }
        }
    };
    private double cardMoney;
    private double countmoney;
    private TextView pay_food_DesktopNum;
    private TextView pay_food_PeopleNum;
    private String tableNo;
    private String peopleNum;
    private String tableName;
    private String status;
    private LinearLayout food_pay_back;
    private ProgressDialog progressDialog;
    private final int SubmitOrder = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_food_1);
        findView();
        getResultFromServer();
        initData();

    }

    private void findView() {
        EventBus.getDefault().register(this);
        Bundle bundle = getIntent().getExtras();
        tablemoney = bundle.get("tableMoney").toString();
        foodmoney = Constant.FoodMoney;
        tableNo = bundle.get("tableNo").toString();
        tableName = bundle.get("tableName").toString();
        countmoney = add(Double.valueOf(tablemoney), Double.valueOf(foodmoney));
        payList = new ArrayList<>();
        progressDialog = new ProgressDialog(Pay_foodActivity.this);
        shouldMoney_tv = (TextView) findViewById(R.id.shouldMoney_tv);
        shouldMoney_tv.setText(new DecimalFormat("#0.00").format(countmoney));
        lv_payType = (ListView) findViewById(R.id.lv_payType);
        actualMoney = (TextView) findViewById(R.id.actualMoney);
        pay_food_DesktopNum = (TextView) findViewById(R.id.pay_food_DesktopNum);
        pay_food_DesktopNum.setText(tableNo);
        pay_food_PeopleNum = (TextView) findViewById(R.id.pay_food_PeopleNum);
        food_pay_back = (LinearLayout) findViewById(R.id.food_pay_back);
        pay_food_PeopleNum.setText(Constant.TablePeople);
        actualMoney.setText(new DecimalFormat("#0.00").format(countmoney));
        btn_confirm_order = (Button) findViewById(R.id.btn_confirm_order);
        btn_cancel_order = (Button) findViewById(R.id.btn_cancel_order);

    }

    public void initData() {
        adapter = new Pay_foodAdapter(payList, this);
        lv_payType.setAdapter(adapter);
        lv_payType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PayBean.DtBean payBean = payList.get(position);
                list_delete.clear();
                adapter.setSelectedIndex(position);
//                if (payBean.isChecked() == true) {
//                    payBean.setChecked(false);
//                    list_delete.remove(payBean);
//                } else {
//                    payBean.setChecked(true);
//                    list_delete.add(payBean);
//                }
                list_delete.add(payBean);
                sendMessageInfo(handler, SubmitOrder);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void submitOneCardInfo(String cardNo) {

        Pay_foodActivity.this.runOnUiThread(new Runnable() {
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

                    if (balance >= countmoney) {
                        PayBean.DtBean payBean = list_delete.get(0);
                        cardMoney = sub(balance, countmoney);
                        getOrderIDAndsubmit(payBean, Constant.BEANS, cardNo, null);

                    } else {
                        Pay_foodActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Dailog.ErrDialog(Pay_foodActivity.this, "", "卡内余额不足!" + "\n" + "剩余金额:" + balance);
                                ToastUtils.show(Pay_foodActivity.this, "卡内余额不足!");
                                HttpUtils.exitProgressDialog(progressDialog);
                            }
                        });
                    }


                } else {
                    //如果获取一卡通信息失败
                    Pay_foodActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.show(Pay_foodActivity.this, "获取一卡通信息失败!");
                            DialogUtil.cancelDownloadDialog();
                        }
                    });

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            //如果一卡通信息为空
            Pay_foodActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.show(Pay_foodActivity.this, "获取一卡通信息失败!");
                    DialogUtil.cancelDownloadDialog();
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
                PayBean.DtBean paybean_1 = new PayBean.DtBean();
                paybean_1.setNIDX(-1);
                paybean_1.setSMONEYNAMECNX("挂单");
                paybean_1.setNPAYMENTTYPEX(-1);
                payList.add(paybean_1);
                payList.addAll(paybean.dt);

            } else {
                ToastUtils.show(this, "获取支付方式失败!");
                LogUtil.i(TAG, "返回结果为null");
            }
        }
    }

    public void PayFoodOnClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_confirm_order:
                sendMessageInfo(handler, SubmitOrder);
                break;
            case R.id.btn_cancel_order:
                sendMessageInfo(handler, Failed, "取消订单");
                break;
            case R.id.food_pay_back:
                Intent intent = new Intent();
                intent.putExtra("flag", false);
                EventBus.getDefault().post(new MessageEvent(1002, -1, intent));
                Pay_foodActivity.this.finish();
                break;
            default:
                break;

        }
    }

    /**
     * 提交销售数据
     *
     * @param detailID
     * @param list
     * @param money
     * @param payType
     * @param cardNO
     * @param tradeNo
     * @param TABLENO
     * @param PEOPLECOUNT
     * @throws Exception
     */
    private void submitData(String detailID, ArrayList<DetailBean.DtBean> list, double money, double payType, String cardNO, String tradeNo, String TABLENO, int PEOPLECOUNT) throws Exception {
        String json = "";
        if (payType == -1) {
            json = "{\"NDEALID\"" + ":" + detailID + ",\"NGZONEID\"" + ":" + Constant.Food_ZONEID + ",\"NTERMINALID\"" + ":" + Constant.Food_TERMINALID + ",\"NEMPID\"" + ":" + Constant.EMPID + ",\"DDATE\"" + ":\"" + TimeUtils.time() + "\"" + ",\"NPAYMENT\"" + ":" + "0" + ",\"NAMOUNT\"" + ":" + countmoney + ",\"SCARD\"" + ":" + cardNO + ",\"TRANDE_NO\"" + ":\"" + tradeNo + "\"" + ",\"CommInfos\":";//订单明细信息

        } else {
            json = "{\"NDEALID\"" + ":" + detailID + ",\"NGZONEID\"" + ":" + Constant.Food_ZONEID + ",\"NTERMINALID\"" + ":" + Constant.Food_TERMINALID + ",\"NEMPID\"" + ":" + Constant.EMPID + ",\"DDATE\"" + ":\"" + TimeUtils.time() + "\"" + ",\"NPAYMENT\"" + ":" + payType + ",\"NAMOUNT\"" + ":" + countmoney + ",\"SCARD\"" + ":" + cardNO + ",\"TRANDE_NO\"" + ":\"" + tradeNo + "\"" + ",\"CommInfos\":";//订单明细信息

        }
        if (list.size() == 1) {
            DetailBean.DtBean detailBean = list.get(0);
            json = json + "[{\"NCOMMID\"" + ":" + detailBean.getNCOMMID() + ",\"NPCOMMID\"" + ":" + detailBean.getNPCOMMID() + ",\"PNUMBER\"" + ":" + detailBean.getPNUMBER() + ",\"NNUMBER\"" + ":" + detailBean.getGoodsCount() + ",\"NPRICE\"" + ":" + detailBean.getPRICE() + "}]" + ",\"TABLENO\"" + ":\"" + TABLENO + "\"" + ",\"PEOPLECOUNT\"" + ":" + PEOPLECOUNT + "}";
        } else if (list.size() == 2) {

            json = json + "[{\"NCOMMID\"" + ":" + list.get(0).getNCOMMID() + ",\"NPCOMMID\"" + ":" + list.get(0).getNPCOMMID() + ",\"PNUMBER\"" + ":" + list.get(0).getPNUMBER() + ",\"NNUMBER\"" + ":" + list.get(0).getGoodsCount() + ",\"NPRICE\"" + ":" + list.get(0).getPRICE() + "},{" + "\"NCOMMID\"" + ":" + list.get(1).getNCOMMID() + ",\"NPCOMMID\"" + ":" + list.get(1).getNPCOMMID() + ",\"PNUMBER\"" + ":" + list.get(1).getPNUMBER() + ",\"NNUMBER\"" + ":" + list.get(1).getGoodsCount() + ",\"NPRICE\"" + ":" + list.get(1).getPRICE() + "}]" + ",\"TABLENO\"" + ":\"" + TABLENO + "\"" + ",\"PEOPLECOUNT\"" + ":" + PEOPLECOUNT + "}";
        } else {

            for (int i = 0; i < list.size(); i++) {

                if (i == 0) {
                    json = json + "[{\"NCOMMID\"" + ":" + list.get(0).getNCOMMID() + ",\"NPCOMMID\"" + ":" + list.get(0).getNPCOMMID() + ",\"PNUMBER\"" + ":" + list.get(0).getPNUMBER() + ",\"NNUMBER\"" + ":" + list.get(0).getGoodsCount() + ",\"NPRICE\"" + ":" + list.get(0).getPRICE() + "}";
                } else if (i == list.size() - 1) {
                    json = json + ",{\"NCOMMID\"" + ":" + list.get(list.size() - 1).getNCOMMID() + ",\"NPCOMMID\"" + ":" + list.get(list.size() - 1).getNPCOMMID() + ",\"PNUMBER\"" + ":" + list.get(list.size() - 1).getPNUMBER() + ",\"NNUMBER\"" + ":" + list.get(list.size() - 1).getGoodsCount() + ",\"NPRICE\"" + ":" + list.get(list.size() - 1).getPRICE() + "}]" + ",\"TABLENO\"" + ":\"" + TABLENO + "\"" + ",\"PEOPLECOUNT\"" + ":" + PEOPLECOUNT + "}";
                } else {
                    json = json + ",{\"NCOMMID\"" + ":" + list.get(i).getNCOMMID() + ",\"NPCOMMID\"" + ":" + list.get(i).getNPCOMMID() + ",\"PNUMBER\"" + ":" + list.get(i).getPNUMBER() + ",\"NNUMBER\"" + ":" + list.get(i).getGoodsCount() + ",\"NPRICE\"" + ":" + list.get(i).getPRICE() + "}";
                }
            }
        }
        Log.e("餐饮清单", "json----->" + json);
        String result = "";
        if (payType == -1) {
            result = GsWebServiceUtils.submitFoodInfo_Table(json);
            Log.e("挂单信息结果", result);
        } else {
            result = submitFoodInfo(json);
            Log.e("餐饮信息结果", result);
        }
        if (!result.equals("err")) {
            SETTABLEINFOBean settableinfoBean = JSON.parseObject(result, SETTABLEINFOBean.class);
            if (settableinfoBean.isSuccess()) {
                double formatMoney = Double.parseDouble(new DecimalFormat("#0.00").format(money));
                double CardMoney = Double.parseDouble(new DecimalFormat("#0.00").format(cardMoney));
                DetailBean.DtBean dtBean = new DetailBean.DtBean();
                dtBean.setPRICE(Double.parseDouble(tablemoney));
                dtBean.setNCOMMID(TABLENO);
                dtBean.setVCOMMYNAME("桌台费");
                dtBean.setGoodsCount(1);
                list.add(dtBean);
                if ("一卡通".equals(payType_str)) {
                    TpsN900PrintUtils.TpsN900OneCardPrint_Food(Pay_foodActivity.this, list, countmoney, detailID, payType_str, cardNO, CardMoney, tableNo, tableName);
                } else {
                    TpsN900PrintUtils.TpsN900Print_Food(Pay_foodActivity.this, list, countmoney, detailID, payType_str, tableNo, tableName);
                }
                sendMessageInfo(handler, Emptied);
            } else {
                sendMessageInfo(handler, Failed, settableinfoBean.getError() + "\n提交销售数据失败!");
            }
        } else {
            sendMessageInfo(handler, Failed, "提交销售数据失败!");
            return;
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
                    if (8.0 == payType) {
                        if (result.length() == 18 && (result.substring(0, 2).equals("10") || result.substring(0, 2).equals("12") || result.substring(0, 2).equals("13") || result.substring(0, 2).equals("14") || result.substring(0, 2).equals("15"))) {
                            DialogUtil.showPayDialog(Pay_foodActivity.this);
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
                            Dailog.ErrDialog2(Pay_foodActivity.this, "支付错误" + "\n" + "支付方式不匹配", "");
                        }

                    } else if (5.0 == payType) {
                        if (result.length() == 18 && result.substring(0, 2).equals("28")) {
                            DialogUtil.showPayDialog(Pay_foodActivity.this);
                            payType_str = "支付宝";
                            new Thread(new Runnable() {
                                @Override
                                public void run() {

                                    PayBean.DtBean payBean = list_delete.get(0);
                                    getOrderIDAndsubmit(payBean, Constant.BEANS, result, null);

                                }
                            }).start();
                        } else {
                            Dailog.ErrDialog2(Pay_foodActivity.this, "支付错误" + "\n" + "支付方式不匹配", "");
                        }

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

    /**
     * 获取订单详情
     *
     * @param payBean
     * @param list
     * @param cardNo
     * @param tradeNO
     */
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
                    try {
                        double douMoney = countmoney;
                        GetTableInfo();
                        if (status.equals("OK")) {
                            submitData(dealId, list, douMoney, payBean.getNIDX(), cardNo, tradeNO, tableNo, Integer.parseInt(Constant.TablePeople));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    sendMessageInfo(handler, Failed, "获取单号失败!");
                    return;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            Pay_foodActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.show(Pay_foodActivity.this, "获取单号失败!");
                    HttpUtils.exitProgressDialog(progressDialog);
                }
            });

            return;
        }
    }

    /**
     * 获取桌台信息
     */
    public void GetTableInfo() {
        status = "";
        String tableInfo = getTableInfo(1, tableNo);
        Log.e("桌台信息", tableInfo);
        if ("err".equals(tableInfo)) {
            sendMessageInfo(handler, Failed, "请求桌台信息失败请检查网络或配置信息!!!");
        } else {
            TableInfoBean tableInfoBean = JSON.parseObject(tableInfo, TableInfoBean.class);
            if (tableInfoBean.isSuccess && (tableInfoBean.dt != null && tableInfoBean.dt.size() > 0)) {
                if (tableInfoBean.dt.get(0).ISEnable == 0) {
                    status = "OK";
                } else {
                    sendMessageInfo(handler, Failed, "选桌台");

                }
            } else {
                sendMessageInfo(handler, Failed, "获取桌台信息失败" + "\n" + tableInfoBean.error);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Intent intent = new Intent();
            intent.putExtra("flag", false);
            EventBus.getDefault().post(new MessageEvent(1002, -1, intent));
            Pay_foodActivity.this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


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
                    Pay_foodActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            DialogUtil.cancelDownloadDialog();
                            HttpUtils.exitProgressDialog(progressDialog);
                        }
                    });
                    handler.sendMessage(handler.obtainMessage(SHOW_NFC_DATA, nfcData));
                } else {
                    Pay_foodActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            DialogUtil.cancelDownloadDialog();
                            HttpUtils.exitProgressDialog(progressDialog);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent_Food messageEvent) {
        String msg = messageEvent.getMsg();
        if (msg.equals("消失")) {
            finish();
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
