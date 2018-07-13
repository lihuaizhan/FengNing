package com.example.tps900.tps900.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tps900.tps900.Bean.ReturnBean;
import com.example.tps900.tps900.R;
import com.example.tps900.tps900.Utlis.Constant;
import com.example.tps900.tps900.Utlis.DialogUtil;
import com.example.tps900.tps900.Utlis.LogUtil;
import com.example.tps900.tps900.Utlis.ShowInfo;
import com.example.tps900.tps900.Utlis.TimeUtils;
import com.example.tps900.tps900.Utlis.TpsN900PrintUtils;
import com.example.tps900.tps900.Webservice.GsWebServiceUtils;
import com.example.tps900.tps900.adapters.ReturnAdapter;
import com.godfery.Utils.ToastUtils;
import com.godfery.keyboard.KeyboardUtil;
import com.godfery.Zxing.CaptureActivity;
import com.telpo.tps550.api.printer.UsbThermalPrinter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReturnActivity extends AppCompatActivity {

    private ImageButton img_back;
    private Button btn_scan;
    private EditText et_returnGoods;
    private Button btn_search;
    public static final String TAG = "ReturnActivity";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case 1001:
                    String result = (String) msg.obj;
                    LogUtil.i(TAG, "result----->" + result);
                    parseJson(result);
                    break;
                default:
                    break;
            }

        }
    };
    private Button cancel_return;
    private Button confirm_return;
    private ArrayList<ReturnBean> list;
    private ListView lv_goodsInfo_return;
    private ArrayList<ReturnBean> list_delete;
    private ReturnAdapter adapter;
    private TextView tv_orderCode;
    private TextView tv_orderTime;
    private Button confirm_return1;
    private String ndealid;
    private String ddate;
    private int npayment;
    private String smoneynamecn;
    private TextView payType_tv_return;
    private TextView returnMoney_tv_return;
    private Button btn_clear;
    private int nrdealid;
    private UsbThermalPrinter mUsbThermalPrinter;
    private String orderNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_return);
        mUsbThermalPrinter = new UsbThermalPrinter(this);
        list = new ArrayList<>();
        list_delete = new ArrayList<>();

        findView();

    }

    private void findView() {
        //返回键
        img_back = (ImageButton) findViewById(R.id.img_back);
        //扫描按钮
        btn_scan = (Button) findViewById(R.id.btn_scan);
        //退货输入框
        et_returnGoods = (EditText) findViewById(R.id.et_returnGoods);


        et_returnGoods.setInputType(InputType.TYPE_NULL);

        et_returnGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int inputType = et_returnGoods.getInputType();
                et_returnGoods.setInputType(InputType.TYPE_NULL);
                KeyboardUtil keyboard = new KeyboardUtil(getApplicationContext(), ReturnActivity.this, et_returnGoods, R.id.schemas_key_keyboard_view);

                keyboard.showKeyboard();
                et_returnGoods.setInputType(inputType);
            }
        });


        //查询按钮
        btn_search = (Button) findViewById(R.id.btn_search);

        //取消按钮
        cancel_return = (Button) findViewById(R.id.cancel_return);
        //确认按钮
        confirm_return = (Button) findViewById(R.id.confirm_return);
        lv_goodsInfo_return = (ListView) findViewById(R.id.lv_goodsInfo_return);
        //订单号
        tv_orderCode = (TextView) findViewById(R.id.tv_orderCode);
        //订单日期
        tv_orderTime = (TextView) findViewById(R.id.tv_orderTime);

        payType_tv_return = (TextView) findViewById(R.id.payType_tv_return);
        returnMoney_tv_return = (TextView) findViewById(R.id.returnMoney_tv_return);

        btn_clear = (Button) findViewById(R.id.btn_clear);

        setListener();

    }

    private void setListener() {
        //返回按钮点击事件
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReturnActivity.this.startActivity(new Intent(ReturnActivity.this, MainActivity.class));
                ReturnActivity.this.finish();
            }
        });
        //扫描按妞点击事件
        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClearData();
                Intent intent = new Intent(ReturnActivity.this, CaptureActivity.class);
                ReturnActivity.this.startActivityForResult(intent, 1002);
            }
        });

        //搜索按钮点击事件
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClearData();
                orderNo = et_returnGoods.getText().toString().trim();
                if (!TextUtils.isEmpty(orderNo)) {
                    //弹出进度条
                    DialogUtil.showDownloadDialog(ReturnActivity.this);
                    getReturnInfo(orderNo);
                } else {
                    ToastUtils.show(ReturnActivity.this, "请输入退货单号!");
                    return;
                }
            }
        });

        //取消按钮
        cancel_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReturnActivity.this.startActivity(new Intent(ReturnActivity.this, MainActivity.class));
                ReturnActivity.this.finish();
            }
        });

        confirm_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i = 0; i < list.size(); i++) {
                    int rcannumber = list.get(i).getRCANNUMBER();
                    if (rcannumber > 0) {
                        list_delete.add(list.get(i));
                    }
                }

                //退货结合非null
                if (null != list_delete) {
                    if (!list_delete.isEmpty()) {
                        DialogUtil.showReturnDialog(ReturnActivity.this);
                        //退货集合中有客户要退货的商品
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                getDealID();
                            }
                        }).start();
                    } else {
                        //没有商品
                        ToastUtils.show(getApplicationContext(), "请您添加要退货的数量!");
                        return;
                    }
                }
            }
        });


        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_returnGoods.setText(null);
            }
        });

    }

    //获取退款单号
    private void getDealID() {
        String dealId = GsWebServiceUtils.getOrderNo();
        LogUtil.i(TAG, "dealId--->" + dealId);

        if (null != dealId) {
            try {
                JSONObject jsonObject = new JSONObject(dealId);
                boolean isSuccess = jsonObject.getBoolean("isSuccess");
                if (isSuccess) {
                    //获取订单号成功
                    int ndealid = jsonObject.getInt("NDEALID");
                    String shouldMOney = returnMoney_tv_return.getText().toString().trim();
                    if (Double.parseDouble(shouldMOney) > 0.00) {
                        //获取玩退款单号后，提交退款信息
                        submitReturnGoodsInfo(list_delete, Double.parseDouble(orderNo), ndealid, npayment, Double.parseDouble(shouldMOney), -1);
                    } else {
                        ReturnActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.show(getApplicationContext(), "金额不能小于或等于0!");
                                DialogUtil.cancelDownloadDialog();
                            }
                        });
                    }

                } else {
                    ReturnActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogUtil.cancelDownloadDialog();
                            ShowInfo showInfo = new ShowInfo(ReturnActivity.this);
                            showInfo.infoDialog(ReturnActivity.this, "订单编号信息", "获取订单号失败");
                        }
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            ReturnActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    DialogUtil.cancelDownloadDialog();
                    ShowInfo showInfo = new ShowInfo(ReturnActivity.this);
                    showInfo.infoDialog(ReturnActivity.this, "订单编号信息", "获取订单号失败");
                }
            });
        }

    }

    private void submitReturnGoodsInfo(ArrayList<ReturnBean> list, double rDealId, int detailId, int payType, double money, double cardNo) {
        //原单号                              //退货单号
        String json = "{\"NRDEALID\"" + ":" + rDealId + ",\"NDEALID\"" + ":" + detailId + ",\"NGZONEID\"" + ":" + Constant.ZONEID + ",\"NTERMINALID\"" + ":" + Constant.TERMINALID + ",\"NEMPID\"" + ":" + Constant.EMPID + ",\"DDATE\"" + ":\"" + TimeUtils.time() + "\"" + ",\"NPAYMENT\"" + ":" + payType + ",\"NAMOUNT\"" + ":" + money + ",\"CommInfos\":";//订单明细信息

        if (list.size() == 1) {
            ReturnBean returnBean = list.get(0);
            json = json + "[{\"NCOMMID\"" + ":" + returnBean.getNCOMMID() + ",\"NPCOMMID\"" + ":" + returnBean.getNPCOMMID() + ",\"NPNUMBER\"" + ":" + returnBean.getPNUMBER() + ",\"RNUMBER\"" + ":" + returnBean.getRCANNUMBER() + ",\"NPRICE\"" + ":" + returnBean.getNPRICE() + "}]}";
        } else if (list.size() == 2) {

            json = json + "[{\"NCOMMID\"" + ":" + list.get(0).getNCOMMID() + ",\"NPCOMMID\"" + ":" + list.get(0).getNPCOMMID() + ",\"NPNUMBER\"" + ":" + list.get(0).getPNUMBER() + ",\"RNUMBER\"" + ":" + list.get(0).getRCANNUMBER() + ",\"NPRICE\"" + ":" + list.get(0).getNPRICE() + "},{" + "\"NCOMMID\"" + ":" + list.get(1).getNCOMMID() + ",\"NPCOMMID\"" + ":" + list.get(1).getNPCOMMID() + ",\"NPNUMBER\"" + ":" + list.get(1).getPNUMBER() + ",\"RNUMBER\"" + ":" + list.get(1).getRCANNUMBER() + ",\"NPRICE\"" + ":" + list.get(1).getNPRICE() + "}]}";
        } else {

            for (int i = 0; i < list.size(); i++) {

                if (i == 0) {
                    json = json + "[{\"NCOMMID\"" + ":" + list.get(0).getNCOMMID() + ",\"NPCOMMID\"" + ":" + list.get(0).getNPCOMMID() + ",\"NPNUMBER\"" + ":" + list.get(0).getPNUMBER() + ",\"RNUMBER\"" + ":" + list.get(0).getRCANNUMBER() + ",\"NPRICE\"" + ":" + list.get(0).getNPRICE() + "}";
                } else if (i == list.size() - 1) {
                    json = json + ",{\"NCOMMID\"" + ":" + list.get(list.size() - 1).getNCOMMID() + ",\"NPCOMMID\"" + ":" + list.get(list.size() - 1).getNPCOMMID() + ",\"NPNUMBER\"" + ":" + list.get(list.size() - 1).getPNUMBER() + ",\"RNUMBER\"" + ":" + list.get(list.size() - 1).getRCANNUMBER() + ",\"NPRICE\"" + ":" + list.get(list.size() - 1).getNPRICE() + "}]}";
                } else {
                    json = json + ",{\"NCOMMID\"" + ":" + list.get(i).getNCOMMID() + ",\"NPCOMMID\"" + ":" + list.get(i).getNPCOMMID() + ",\"NPNUMBER\"" + ":" + list.get(i).getPNUMBER() + ",\"RNUMBER\"" + ":" + list.get(i).getRCANNUMBER() + ",\"NPRICE\"" + ":" + list.get(i).getNPRICE() + "}";
                }
            }
        }

        LogUtil.i(TAG, "json----->" + json);

        GsWebServiceUtils gs = new GsWebServiceUtils();
        String result = GsWebServiceUtils.submitReturnInfo(json);
        LogUtil.i(TAG, "result退货----->" + result);

        if (!result.equals("err")) {


            try {
                JSONObject jsonObject = new JSONObject(result);
                boolean isSuccess = jsonObject.getBoolean("isSuccess");
                if (isSuccess) {
                    //如果成功的话
                    ReturnActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            et_returnGoods.setText(null);
                        }
                    });

                    try {
                        String returnMoney = returnMoney_tv_return.getText().toString().trim();
                        String reTurnPayType = payType_tv_return.getText().toString().trim();
                        TpsN900PrintUtils.TpsN900ReturnPrint(this, list_delete, returnMoney, orderNo, String.valueOf(detailId), reTurnPayType);
                        ReturnActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DialogUtil.cancelDownloadDialog();
                            }
                        });
                        ReturnActivity.this.startActivity(new Intent(ReturnActivity.this, MainActivity.class));
                        ReturnActivity.this.finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    //如果失败
                    ReturnActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ShowInfo showInfo = new ShowInfo(ReturnActivity.this);
                            showInfo.infoDialog(ReturnActivity.this, "退款信息", "提交退货信息失败!");
                            DialogUtil.cancelDownloadDialog();
                        }
                    });
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            ReturnActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ShowInfo showInfo = new ShowInfo(ReturnActivity.this);
                    showInfo.infoDialog(ReturnActivity.this, "退款信息", "提交退货信息失败!");
                    DialogUtil.cancelDownloadDialog();
                }
            });
            LogUtil.i(TAG, "返回值为null");
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1002:
                if (null != data) {
                    //扫描成功
                    String result = data.getStringExtra("result");
                    orderNo = result;
                    et_returnGoods.setText(result);
                    getReturnInfo(result);

                } else {
                    ToastUtils.show(this, "未扫描到数据!");
                    return;
                }

                break;
            default:
                break;
        }
    }

    //解析访问退货接口获得的字符串
    private void parseJson(String result) {
        DialogUtil.cancelDownloadDialog();

        try {
            list.clear();
            JSONObject jsonObject_head = new JSONObject(result);
            boolean isSuccess = jsonObject_head.getBoolean("isSuccess");

            if (isSuccess) {
                JSONObject jsonObject = jsonObject_head.getJSONObject("di");
                //访问成功
                //订单ID
                ndealid = jsonObject.getString("NDEALID");
                nrdealid = jsonObject.getInt("NRDEALID");
                int ngzoneid = jsonObject.getInt("NGZONEID");
                int nterminalid = jsonObject.getInt("NTERMINALID");
                int nempid = jsonObject.getInt("NEMPID");
                ddate = jsonObject.getString("DDATE");
                npayment = jsonObject.getInt("NPAYMENT");
                double namount = jsonObject.getDouble("NAMOUNT");
                String smoneynamecn = jsonObject.getString("SMONEYNAMECN");

                tv_orderCode.setText(String.valueOf(ndealid));
                tv_orderTime.setText(ddate);
                payType_tv_return.setText(smoneynamecn);

                JSONArray commInfos = jsonObject.getJSONArray("CommInfos");
                for (int i = 0; i < commInfos.length(); i++) {

                    ReturnBean returnBean = new ReturnBean();
                    JSONObject json_child = commInfos.getJSONObject(i);
                    int ncommid = json_child.getInt("NCOMMID");
                    int npcommid = json_child.getInt("NPCOMMID");
                    int pnumber = json_child.getInt("NPNUMBER");
                    int nnumber = json_child.getInt("NNUMBER");
                    double nprice = json_child.getDouble("NPRICE");
                    int rnumber = json_child.getInt("RNUMBER");
                    int rcannumber = json_child.getInt("RCANNUMBER");
                    String vcommyname = json_child.getString("VCOMMYNAME");
                    returnBean.setNCOMMID(ncommid);
                    returnBean.setNPCOMMID(npcommid);
                    returnBean.setPNUMBER(pnumber);
                    returnBean.setNNUMBER(nnumber);
                    returnBean.setNPRICE(nprice);
                    returnBean.setRNUMBER(rnumber);
                    returnBean.setRCANNUMBER(0);
                    returnBean.setRETURNNUMBER(rcannumber);
                    returnBean.setVCOMMYNAME(vcommyname);

                    list.add(returnBean);

                }
                //如果每个bean对象的可退数量都小于或等于0，则提示用户，该订单已经退完货
                boolean flag = false;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getRETURNNUMBER() > 0) {
                        flag = true;
                        break;
                    } else {
                        flag = false;
                    }
                }

                if (!flag) {
                    ShowInfo showInfo = new ShowInfo(ReturnActivity.this);
                    showInfo.infoDialog(ReturnActivity.this, "退货信息", "该订单已经退完货");
                    list.clear();
                    adapter = new ReturnAdapter(list, this, returnMoney_tv_return);
                    lv_goodsInfo_return.setAdapter(adapter);
                } else {
                    adapter = new ReturnAdapter(list, this, returnMoney_tv_return);
                    lv_goodsInfo_return.setAdapter(adapter);
                }
            } else {
                et_returnGoods.setText("");
                ToastUtils.show(this, "获取退货订单信息失败!");
                LogUtil.i(TAG, "获取退货订单信息失败，返回值为FALSE");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            et_returnGoods.setText("");
            ToastUtils.show(this, "获取退货订单信息失败!");
            LogUtil.i(TAG, "获取退货订单信息失败，返回值为FALSE");
        }
    }

    public void getReturnInfo(final String scanResult) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                GsWebServiceUtils gs = new GsWebServiceUtils();
                LogUtil.i(TAG, "Constant.ZONEID----->" + Constant.ZONEID);
                String result = GsWebServiceUtils.getReturnInfo(String.valueOf(Constant.ZONEID), scanResult);
                if (null != result) {
                    Message msg = Message.obtain();
                    msg.obj = result;
                    msg.what = 1001;
                    handler.sendMessage(msg);
                } else {
                    ReturnActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.show(ReturnActivity.this, "获取退货订单信息失败!");
                            DialogUtil.cancelDownloadDialog();
                        }
                    });
                }
            }
        });

        thread.start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        ReturnActivity.this.startActivity(new Intent(ReturnActivity.this, MainActivity.class));
        ReturnActivity.this.finish();
        return super.onKeyDown(keyCode, event);
    }

    /**
     * @author zxh
     * created at 2018-06-07 15:01
     * 方法名: 清空数据
     * 方法说明:
     */
    public void ClearData() {

        if (TextUtils.isEmpty(returnMoney_tv_return.getText().toString())) {

        } else {
            returnMoney_tv_return.setText("0");
        }
        if (null != list) {
            if (!list.isEmpty()) {
                list.clear();
                adapter.notifyDataSetChanged();
                payType_tv_return.setText("");
                tv_orderCode.setText("");
                tv_orderTime.setText("");
            }
        }
    }
}
