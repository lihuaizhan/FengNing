package com.example.tps900.tps900.activity.newfood;

import android.app.ProgressDialog;
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
import com.example.tps900.tps900.Utlis.ThreadPoolUtils;
import com.example.tps900.tps900.Utlis.TimeUtils;
import com.example.tps900.tps900.Utlis.TpsN900PrintUtils;
import com.example.tps900.tps900.Webservice.GsWebServiceUtils;
import com.example.tps900.tps900.activity.BaseActivity;
import com.example.tps900.tps900.activity.InStockActivity;
import com.example.tps900.tps900.adapters.Pay_foodAdapter;
import com.godfery.Utils.ToastUtils;
import com.godfery.pay.HttpUtils;
import com.godfery.Zxing.CaptureActivity;

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
import static com.example.tps900.tps900.Utlis.OtherUtils.sendMessageInfo;
import static com.example.tps900.tps900.Utlis.OtherUtils.sub;
import static com.example.tps900.tps900.Webservice.GsWebServiceUtils.getTableInfo;
import static com.example.tps900.tps900.Webservice.GsWebServiceUtils.submitFoodInfo;


public class New_Pay_foodActivity extends BaseActivity implements BaseActivity.CardCodeInterface {

    public static final String TAG = "PayActivitys";

    private TextView shouldMoney_tv;
    private ListView lv_payType;
    private TextView actualMoney;
    private ArrayList<PayBean.DtBean> payList;
    private Pay_foodAdapter adapter;
    private ArrayList<PayBean.DtBean> list_delete = new ArrayList<>();
    private Button btn_confirm_order;
    private double payType;
    private String foodmoney;
    private Button btn_cancel_order;
    private String codeNum = "";
    private String payType_str;

    //通用失败
    private final int Failed = 1;
    //清空
    private final int Emptied = 2;
    private final int Food_ReturnPay = 3;
    private List<SETTABLEINFOBean.GoodlsBean> goodlsBeanList;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                //通用失败原因
                case Failed:
                    HttpUtils.exitProgressDialog(progressDialog);
                    DismissDialog();
                    String msgInfo = msg.obj.toString();
                    if (msgInfo.equals("取消订单")) {
                        ErrDialog2(New_Pay_foodActivity.this, "取消订单将清空所有菜品", "取消订单");
                    } else if (msgInfo.equals("选桌台")) {
                        ErrDialog2(New_Pay_foodActivity.this, "桌台已占用,点击确定其他桌台!!!", "选桌台");
                    } else {

                        ErrDialog2(New_Pay_foodActivity.this, msgInfo, "");
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
                    New_Pay_foodActivity.this.finish();
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
                                        DeviceN900(New_Pay_foodActivity.this);
                                    } else { //点击确认支付按钮，打开扫描头
                                        intent = new Intent(New_Pay_foodActivity.this, CaptureActivity.class);
                                        New_Pay_foodActivity.this.startActivityForResult(intent, 1001);
                                    }
                                }
                            } else if (list_delete.size() > 1) {
                                ToastUtils.show(New_Pay_foodActivity.this, "不支持混合支付请重新选择");
                                return;
                            }
                        } else {
                            ToastUtils.show(New_Pay_foodActivity.this, "请选择支付方式");
                            return;
                        }
                    } else {
                        ToastUtils.show(New_Pay_foodActivity.this, "请输入用餐人数");
                        return;
                    }
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


    /**
     * 设置布局
     *
     * @return
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_pay_food_1;
    }

    /**
     * 初始化控件
     */
    @Override
    public void initView() {
        findView();
        getResultFromServer();
        initData();
    }

    private void findView() {
        EventBus.getDefault().register(this);
        Bundle bundle = getIntent().getExtras();
        foodmoney = Constant.FoodMoney;
        tableNo = bundle.get("tableNo").toString();
        countmoney = Double.valueOf(foodmoney);
        payList = new ArrayList<>();
        goodlsBeanList = new ArrayList<>();
        progressDialog = new ProgressDialog(New_Pay_foodActivity.this);
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

    @Override
    public void initData() {
        adapter = new Pay_foodAdapter(payList, this);
        lv_payType.setAdapter(adapter);
        lv_payType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PayBean.DtBean payBean = payList.get(position);
                list_delete.clear();
                adapter.setSelectedIndex(position);
                list_delete.add(payBean);
                sendMessageInfo(handler, SubmitOrder);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void submitOneCardInfo(String cardNo) {

        New_Pay_foodActivity.this.runOnUiThread(new Runnable() {
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
                        New_Pay_foodActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Dailog.ErrDialog(New_Pay_foodActivity.this, "", "卡内余额不足!" + "\n" + "剩余金额:" + balance);
                                ToastUtils.show(New_Pay_foodActivity.this, "卡内余额不足!");
                                HttpUtils.exitProgressDialog(progressDialog);
                            }
                        });
                    }


                } else {
                    //如果获取一卡通信息失败
                    New_Pay_foodActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.show(New_Pay_foodActivity.this, "获取一卡通信息失败!");
                            DialogUtil.cancelDownloadDialog();
                        }
                    });

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            //如果一卡通信息为空
            New_Pay_foodActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.show(New_Pay_foodActivity.this, "获取一卡通信息失败!");
                    DialogUtil.cancelDownloadDialog();
                }
            });


        }
    }

    private void getResultFromServer() {
        String payType = GsWebServiceUtils.getPayType(Constant.Food_ZONEID);
        LogUtil.i(TAG, "Constant.Food_ZONEID---->" + Constant.Food_ZONEID);
        LogUtil.i(TAG, "payType----->" + payType);
        if (!"err".equals(payType)) {
            PayBean paybean = JSON.parseObject(payType, PayBean.class);
            if (paybean.isSuccess && paybean.dt.size() > 0) {
                PayBean.DtBean paybean_1 = new PayBean.DtBean();
                paybean_1.setNIDX(-1);
                paybean_1.setSMONEYNAMECNX("挂单");
                paybean_1.setNPAYMENTTYPEX(-1);
                payList.add(paybean_1);
                for (int i = 0; i < paybean.dt.size(); i++) {
                    PayBean.DtBean dtBean = paybean.dt.get(i);
                    if (dtBean.getNPAYMENTTYPEX() == 2 || dtBean.getNPAYMENTTYPEX() == 3 || dtBean.getNPAYMENTTYPEX() == 5 || dtBean.getNPAYMENTTYPEX() == 8 || dtBean.getNPAYMENTTYPEX() == 1) {
                        payList.add(dtBean);
                    }

                }

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
                New_Pay_foodActivity.this.finish();
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
     * @param paytype
     * @param cardNO
     * @param tradeNo
     * @throws Exception
     */
    private void submitData(String detailID, ArrayList<DetailBean.DtBean> list, double money, double paytype, String cardNO, String tradeNo) throws Exception {
        String json = "";
        if (paytype == -1) {
            json = "{\"NDEALID\"" + ":" + detailID + ",\"NGZONEID\"" + ":" + Constant.Food_ZONEID + ",\"NTERMINALID\"" + ":" + Constant.Food_TERMINALID + ",\"NEMPID\"" + ":" + Constant.EMPID + ",\"DDATE\"" + ":\"" + TimeUtils.time() + "\"" + ",\"NPAYMENT\"" + ":" + "0" + ",\"NAMOUNT\"" + ":" + countmoney + ",\"SCARD\"" + ":" + cardNO + ",\"TRANDE_NO\"" + ":\"" + tradeNo + "\"" + ",\"CommInfos\":";//订单明细信息

        } else {
            json = "{\"NDEALID\"" + ":" + detailID + ",\"NGZONEID\"" + ":" + Constant.Food_ZONEID + ",\"NTERMINALID\"" + ":" + Constant.Food_TERMINALID + ",\"NEMPID\"" + ":" + Constant.EMPID + ",\"DDATE\"" + ":\"" + TimeUtils.time() + "\"" + ",\"NPAYMENT\"" + ":" + paytype + ",\"NAMOUNT\"" + ":" + countmoney + ",\"SCARD\"" + ":" + cardNO + ",\"TRANDE_NO\"" + ":\"" + tradeNo + "\"" + ",\"CommInfos\":";//订单明细信息

        }
        if (list.size() == 1) {
            DetailBean.DtBean detailBean = list.get(0);
            json = json + "[{\"NCOMMID\"" + ":" + detailBean.getNCOMMID() + ",\"NPCOMMID\"" + ":" + detailBean.getNPCOMMID() + ",\"PNUMBER\"" + ":" + detailBean.getPNUMBER() + ",\"NNUMBER\"" + ":" + detailBean.getGoodsCount() + ",\"NPRICE\"" + ":" + detailBean.getPRICE() + "}] }";
        } else if (list.size() == 2) {

            json = json + "[{\"NCOMMID\"" + ":" + list.get(0).getNCOMMID() + ",\"NPCOMMID\"" + ":" + list.get(0).getNPCOMMID() + ",\"PNUMBER\"" + ":" + list.get(0).getPNUMBER() + ",\"NNUMBER\"" + ":" + list.get(0).getGoodsCount() + ",\"NPRICE\"" + ":" + list.get(0).getPRICE() + "},{" + "\"NCOMMID\"" + ":" + list.get(1).getNCOMMID() + ",\"NPCOMMID\"" + ":" + list.get(1).getNPCOMMID() + ",\"PNUMBER\"" + ":" + list.get(1).getPNUMBER() + ",\"NNUMBER\"" + ":" + list.get(1).getGoodsCount() + ",\"NPRICE\"" + ":" + list.get(1).getPRICE() + "}] }";
        } else {

            for (int i = 0; i < list.size(); i++) {

                if (i == 0) {
                    json = json + "[{\"NCOMMID\"" + ":" + list.get(0).getNCOMMID() + ",\"NPCOMMID\"" + ":" + list.get(0).getNPCOMMID() + ",\"PNUMBER\"" + ":" + list.get(0).getPNUMBER() + ",\"NNUMBER\"" + ":" + list.get(0).getGoodsCount() + ",\"NPRICE\"" + ":" + list.get(0).getPRICE() + "}";
                } else if (i == list.size() - 1) {
                    json = json + ",{\"NCOMMID\"" + ":" + list.get(list.size() - 1).getNCOMMID() + ",\"NPCOMMID\"" + ":" + list.get(list.size() - 1).getNPCOMMID() + ",\"PNUMBER\"" + ":" + list.get(list.size() - 1).getPNUMBER() + ",\"NNUMBER\"" + ":" + list.get(list.size() - 1).getGoodsCount() + ",\"NPRICE\"" + ":" + list.get(list.size() - 1).getPRICE() + "}] }";
                } else {
                    json = json + ",{\"NCOMMID\"" + ":" + list.get(i).getNCOMMID() + ",\"NPCOMMID\"" + ":" + list.get(i).getNPCOMMID() + ",\"PNUMBER\"" + ":" + list.get(i).getPNUMBER() + ",\"NNUMBER\"" + ":" + list.get(i).getGoodsCount() + ",\"NPRICE\"" + ":" + list.get(i).getPRICE() + "}";
                }
            }
        }
        Log.e("餐饮清单", "json----->" + json);
        String result = "";
        if (payType == -1) {
            result = GsWebServiceUtils.submitFoodInfo_Table(json);
            HttpUtils.exitProgressDialog(progressDialog);
            Log.e("挂单信息结果", result);
        } else {
            result = submitFoodInfo(json);
            HttpUtils.exitProgressDialog(progressDialog);
            Log.e("餐饮信息结果", result);
        }
        if (!result.equals("err")) {

            SETTABLEINFOBean settableinfoBean = JSON.parseObject(result, SETTABLEINFOBean.class);
            if (settableinfoBean.isSuccess()) {
                double formatMoney = Double.parseDouble(new DecimalFormat("#0.00").format(money));
                double CardMoney = Double.parseDouble(new DecimalFormat("#0.00").format(cardMoney));
                if ("一卡通".equals(payType_str)) {
                    TpsN900PrintUtils.TpsN900OneCardPrint_Food_2(New_Pay_foodActivity.this, list, countmoney, detailID, payType_str, cardNO, CardMoney, tableNo, Constant.TablePeople);
                } else {
                    TpsN900PrintUtils.TpsN900Print_Food_2(New_Pay_foodActivity.this, list, countmoney, detailID, payType_str, tableNo, Constant.TablePeople);
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
                            dtbean = Constant.BEANS.get(j);
                            boolean isGood = (dtbean.getNCOMMID().equals(goodisBean.getGoodsId()))
                                    && (dtbean.getVCOMMYNAME().equals(goodisBean.getGoodsName()));
                            if (isGood) {
                                flag = true;
                            }
                        }
                        if (flag){
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
            sendMessageInfo(handler, Failed, "提交商品销售数据失败\n请检查网络/配置信息!");
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
                        HttpUtils.showProgressDialog(progressDialog);
                        payType_str = "微信";
                        //微信
                        ThreadPoolUtils.runTaskInThread(new Runnable() {
                            @Override
                            public void run() {
                                PayBean.DtBean payBean = list_delete.get(0);
                                getOrderIDAndsubmit(payBean, Constant.BEANS, result, null);
                            }
                        });

                    } else if (5.0 == payType) {
                        HttpUtils.showProgressDialog(progressDialog);
                        payType_str = "支付宝";
                        ThreadPoolUtils.runTaskInThread(new Runnable() {
                            @Override
                            public void run() {
                                PayBean.DtBean payBean = list_delete.get(0);
                                getOrderIDAndsubmit(payBean, Constant.BEANS, result, null);
                            }
                        });
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
//                        GetTableInfo();
//                        if (status.equals("OK")) {
                        submitData(dealId, list, douMoney, payBean.getNIDX(), cardNo, tradeNO);
//                        }
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
            New_Pay_foodActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.show(New_Pay_foodActivity.this, "获取单号失败!");
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
            New_Pay_foodActivity.this.finish();
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                submitOneCardInfo(cardNo);
            }
        }).start();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent_Food messageEvent) {
        String msg = messageEvent.getMsg();
        if (msg.equals("消失")) {
            finish();
        }

        switch (msg) {
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
                countmoney = 0.0;
                double countMoney = 0;
                PayBean.DtBean payBean = list_delete.get(0);
                for (int i = 0; i < Constant.BEANS.size(); i++) {
                    DetailBean.DtBean dtBean = Constant.BEANS.get(i);
                    if (!dtBean.IsSale && (dtBean.getStockNums() == 0.0 || dtBean.getStockNums() < 0.0)) {
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

                countmoney = countMoney;
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
                            ToastUtils.show(New_Pay_foodActivity.this, "商品库存不足,请选择其他商品!");
                            Intent intent = new Intent();
                            intent.putExtra("flag", true);
                            setResult(RESULT_OK, intent);
                            New_Pay_foodActivity.this.finish();
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
            if (!dtBean.IsSale && dtBean.getStockNums() == 0.0) {
                Constant.BEANS.remove(i);
            } else if (!dtBean.IsSale && dtBean.getStockNums() > 0.0) {
                dtBean.setGoodsCount((int) dtBean.getStockNums());
            }
        }
        if (Constant.BEANS.size() > 0) {
            Intent intent = new Intent();
            intent.putExtra("flag", false);
            setResult(RESULT_OK, intent);
            New_Pay_foodActivity.this.finish();
        } else {
            ToastUtils.show(New_Pay_foodActivity.this, "商品库存不足,请选择其他商品!");
            Intent intent = new Intent();
            intent.putExtra("flag", true);
            setResult(RESULT_OK, intent);
            New_Pay_foodActivity.this.finish();
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
