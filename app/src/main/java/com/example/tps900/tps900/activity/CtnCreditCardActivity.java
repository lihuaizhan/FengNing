package com.example.tps900.tps900.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.tps900.tps900.Bean.Project_feeBean;
import com.example.tps900.tps900.R;
import com.example.tps900.tps900.Utlis.Constant;
import com.example.tps900.tps900.Utlis.DialogUtil;
import com.example.tps900.tps900.Utlis.MessageConstanse;
import com.example.tps900.tps900.Utlis.MessageEvent_Food;
import com.example.tps900.tps900.Utlis.OtherUtils;
import com.example.tps900.tps900.Utlis.ThreadPoolUtils;
import com.example.tps900.tps900.Utlis.ToastUtils;
import com.example.tps900.tps900.Webservice.GsWebServiceUtils;
import com.telpo.tps550.api.TelpoException;
import com.telpo.tps550.api.nfc.Nfc;
import com.telpo.tps550.api.util.StringUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.Calendar;

import static com.example.tps900.tps900.Utlis.Dailog.DismissDialog;
import static com.example.tps900.tps900.Utlis.Dailog.ErrDialog2;
import static com.example.tps900.tps900.Utlis.DialogUtil.alertDialog;
import static com.example.tps900.tps900.Utlis.DialogUtil.cancelDownloadDialog;
import static com.example.tps900.tps900.Utlis.DialogUtil.showOneCardDialog;
import static com.example.tps900.tps900.Utlis.OtherUtils.Print;
import static com.example.tps900.tps900.Utlis.OtherUtils.sendMessageInfo;

/**
 * 项目名称：TPS900
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2017/4/27 12:11
 * 修改人：zxh
 * 修改时间：2017/4/27 12:11
 * 修改备注：
 */

public class CtnCreditCardActivity extends Activity {

    TextView pfEPrice;

    TextView pfTTicketName;

    EditText pfETicketNum;
    TextView pfTCountPrice;
    RelativeLayout pfLvBack;
    Button pfImgOnecard;

    private final int CHECK_NFC_TIMEOUT = 1;
    private final int SHOW_NFC_DATA = 2;
    //通用失败
    private final int Failed = 3;
    //支付成功
    private final int PaymentSuccessful = 4;
    //支付失败
    private final int PaymentFailed = 5;
    //刷卡
    private final int CreditCard = 6;
    //连续刷卡
    private final int ContinuousCreditCard = 7;
    //更改数量
    private final int UpdataTicketNum = 8;
    //点击按钮去支付
    private final int OneCardPayMent = 9;
    //门票数量变更
    private final int TicketChange = 10;
    int flag = 0;
    private Nfc nfc;
    private ReadThread readThread;
    public String ticketName = "", ticketPrice = "", nticketid = "", codeNum = "";
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CHECK_NFC_TIMEOUT:
                    cancelDownloadDialog();
                    ToastUtils.show(CtnCreditCardActivity.this, "未检测到卡片超时 !");

                    break;
                case SHOW_NFC_DATA:
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
                        Long result = Long.parseLong(codeNum);
                        oldCode = result;
                        DismissDialog();
                        String num = pfETicketNum.getText().toString().trim();
                        if (TextUtils.isEmpty(num)) {
                            sendMessageInfo(handler, Failed, "请输入门票数量!");
                        } else {
                            int ticketNum = Integer.parseInt(num);
                            if (ticketNum == 0) {
                                sendMessageInfo(handler, Failed, "请输入门票数量!");
                            } else {
                                Number = ticketNum;
                                if (IsCresitCard) {
                                    cancelDownloadDialog();
                                    newCode = oldCode;
                                    Log.e("CtnCreditCardActivity", codeNum);
                                    ToastUtils.show(CtnCreditCardActivity.this, String.valueOf(result));
                                    paymentCode("一卡通", "一卡通", 6, String.valueOf(result));
                                    Constant.Code = codeNum;
                                } else {
                                    if (newCode != oldCode && alertDialog == null) {
                                        newCode = oldCode;
                                        Log.e("CtnCreditCardActivity", codeNum);
                                        ToastUtils.show(CtnCreditCardActivity.this, String.valueOf(result));
                                        paymentCode("一卡通", "一卡通", 6, String.valueOf(result));
                                        Constant.Code = codeNum;
                                    } else if (alertDialog == null) {
                                        ErrDialog2(CtnCreditCardActivity.this, "此卡上笔已刷过,确认支付吗?", MessageConstanse.CONFIRM_PAYMENT);
                                    }

                                }
                            }
                        }

                    } else {
                        ToastUtils.show(CtnCreditCardActivity.this, "unknow type card!!");
                    }

                    break;
                case PaymentSuccessful:
                    cancelDownloadDialog();
                    ToastUtils.show(CtnCreditCardActivity.this, "支付成功");
                    try {//总价- 单价*数量=剩余
                        Constant.PrintBean = project_feeBean;
                        Constant.Price = CountMoney;
                        Print(CtnCreditCardActivity.this, OtherUtils.doubleprice(String.valueOf(CountMoney)), project_feeBean);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Constant.Code = "";
                    break;
                case CreditCard:
                    IsCresitCard = true;
                    showOneCardDialog(CtnCreditCardActivity.this);
                    DeviceN900();
                    break;
                case Failed:
                    cancelDownloadDialog();
                    String msgInfo = msg.obj.toString();
                    ErrDialog2(CtnCreditCardActivity.this, msgInfo, "");
                    break;
                case UpdataTicketNum:
                    pfETicketNum.setText("");
                    break;
                case OneCardPayMent:
                    CountMoney = 1 * Double.parseDouble(ticketPrice);
                    pfETicketNum.setText("1");
                    pfTCountPrice.setText("¥ " + new DecimalFormat("#0.00").format(CountMoney));
                    String num = pfETicketNum.getText().toString().trim();
                    if (TextUtils.isEmpty(num)) {
                        sendMessageInfo(handler, Failed, "请输入门票数量!");
                    } else {
                        int ticketNum = Integer.parseInt(num);
                        if (ticketNum == 0) {
                            sendMessageInfo(handler, Failed, "请输入门票数量!");
                        } else {
                            if (Constant.IsCtnCredirCard.equals("否")) {
                                sendMessageInfo(handler, CreditCard);
                            } else {
                                sendMessageInfo(handler, Failed, "已开启连续刷卡,请在设置中关闭!");
                            }
                        }
                    }

                    break;
                case TicketChange:
                    msgInfo = msg.obj.toString();
                    if (!TextUtils.isEmpty(msgInfo)) {
                        int ticketNum = Integer.parseInt(msgInfo);
                        double ticketprice = Double.parseDouble(ticketPrice);
                        CountMoney = ticketNum * ticketprice;
                        pfTCountPrice.setText("¥ " + new DecimalFormat("#0.00").format(CountMoney));
                    } else {
                        CountMoney = 1 * Double.parseDouble(ticketPrice);
                        pfTCountPrice.setText("¥ " + new DecimalFormat("#0.00").format(CountMoney));
                    }

                    break;
                default:
                    break;
            }
        }
    };
    private long Number = 0;
    private Project_feeBean project_feeBean;
    private String TAG = "ProjectFeeActivity";
    private boolean IsCresitCard = false;
    public static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;
    private long newCode = 0;
    private long oldCode = 0;
    private double CountMoney;
    private TextView ctnCreditCard_tv;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projectcard_fee);
        initView();
        initData();
    }

    public void initView() {
        EventBus.getDefault().register(this);
        pfEPrice = (TextView) findViewById(R.id.pf_E_price);
        pfTTicketName = (TextView) findViewById(R.id.pf_T_ticketName);
        ctnCreditCard_tv = (TextView) findViewById(R.id.ctnCreditCard_tv);
        pfETicketNum = (EditText) findViewById(R.id.pf_E_ticketNum);
        pfTCountPrice = (TextView) findViewById(R.id.pf_T_countPrice);
        pfLvBack = (RelativeLayout) findViewById(R.id.pf_lv_back);
        pfImgOnecard = (Button) findViewById(R.id.pf_img_onecard);
        Constant.context = CtnCreditCardActivity.this;
        isPayCode();
        if (Constant.IsCtnCredirCard.equals("是")) {
            DeviceN900();
            ctnCreditCard_tv.setText("已开启连续刷卡,如需关闭请在设置中勾选");
            pfImgOnecard.setText("一卡通支付\n(连续)");
        } else {
            ctnCreditCard_tv.setText("");
            pfImgOnecard.setText("一卡通支付");
        }

    }

    public void initData() {
        ticketName = getIntent().getStringExtra("ticketname");
        ticketPrice = getIntent().getStringExtra("ticketprice");
        nticketid = getIntent().getStringExtra("nticketid");
        pfTTicketName.setText(ticketName);
        pfEPrice.setText("¥ " + ticketPrice);
        pfETicketNum.setText("1");
        pfTCountPrice.setText("¥ " + ticketPrice);
        pfETicketNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String num = s.toString().trim();
                sendMessageInfo(handler, TicketChange, num);

            }
        });
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.pf_lv_back) {
            Intent intent = new Intent(CtnCreditCardActivity.this, GetSaleTicketActivity.class);
            CtnCreditCardActivity.this.startActivity(intent);
            CtnCreditCardActivity.this.finish();
        } else if (id == R.id.pf_img_onecard) {
            sendMessageInfo(handler, OneCardPayMent);
        } else if (id == R.id.pf_E_ticketNum) {
            sendMessageInfo(handler, UpdataTicketNum);
        }
    }

    //连接设备执行刷卡
    public void DeviceN900() {
        //打开nfc设备
        if (nfc == null) {
            nfc = new Nfc(this);
        }
        try {
            nfc.close();
            nfc.open();
        } catch (TelpoException e) {
            e.printStackTrace();
        }
        if (readThread == null) {
            readThread = new ReadThread();
            readThread.start();
        }
    }

    /**
     * @param PrintPayName 打印名称
     * @param payName      支付名称
     * @param nincometype  类型
     * @param payCode      付款码
     */
    public void paymentCode(final String PrintPayName, final String payName, final int nincometype, final String payCode) {
        try {
            DialogUtil.showPayDialog(CtnCreditCardActivity.this);
            ThreadPoolUtils.runTaskInThread(new Runnable() {
                @Override
                public void run() {
                    String result = GsWebServiceUtils.GetSaleTicketInfo(
                            new DecimalFormat("#0.00").format(CountMoney), nticketid, ticketPrice, String.valueOf(Number)
                            , payName, new DecimalFormat("#0.00").format(CountMoney), Constant.emp_id, Constant.ter_id, nincometype, payCode);

                    Log.e("付款结果----------------》", result);
                    Constant.Code = "";
                    if ("err".equals(result)) {
                        sendMessageInfo(handler, Failed, "获取数据异常请查网络\n或者检查一下配置!!!");
                    } else {
                        project_feeBean = JSON.parseObject(result, Project_feeBean.class);
                        if (project_feeBean.flag == true) {
                            project_feeBean.setPayName(PrintPayName);
                            sendMessageInfo(handler, PaymentSuccessful);
                        } else {
                            sendMessageInfo(handler, Failed, "支付错误" + "\n" + project_feeBean.erro);
                        }
                    }
                }
            });

        } catch (Exception e) {
            cancelDownloadDialog();
        }
    }

    /**
     * 判断付款方式
     */
    public void isPayCode() {
        if (Constant.OneCard == 0) {
//            pfImgOnecard.setBackgroundResource(R.drawable.d_pay_onecard);
            pfImgOnecard.setBackgroundResource(R.color.oneCard_dn);
            pfImgOnecard.setEnabled(false);
        } else {
//            pfImgOnecard.setBackgroundResource(R.drawable.pay_onecard_selector);
            pfImgOnecard.setBackgroundResource(R.color.oneCard_up);
            pfImgOnecard.setEnabled(true);
        }
    }

    /**
     * 打开nfc
     */
    public void openNfc() {
        try {
            if (nfc == null) {
                nfc.open();
            }

        } catch (TelpoException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭nfc
     */
    public void closeNfc() {
        try {
            if (nfc != null) {
                nfc.close();
            }
        } catch (TelpoException e) {
            e.printStackTrace();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent_Food messageEvent) {
        String messageEventMsg = messageEvent.getMsg();
        switch (messageEventMsg) {
            case MessageConstanse.CONFIRM_PAYMENT:
                paymentCode("一卡通", "一卡通", 6, String.valueOf(oldCode));
                Constant.Code = codeNum;
                break;
            case MessageConstanse.CANCEL_PAYMENT:
                break;
            default:
                break;
        }


    }

    private class ReadThread extends Thread {
        byte[] nfcData = null;

        @Override
        public void run() {

            if (IsCresitCard) {
                try {
                    nfcData = nfc.activate(3 * 1000); // 10s
                    if (null != nfcData) {
                        long currentTime = Calendar.getInstance().getTimeInMillis();
                        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
                            lastClickTime = currentTime;
                            handler.sendMessage(handler.obtainMessage(SHOW_NFC_DATA, nfcData));
                        }
                    } else {
                        handler.sendMessage(handler.obtainMessage(CHECK_NFC_TIMEOUT, null));
                        Log.d("GetNFC----------->", "Check MagneticCard timeout...");
                    }
                } catch (TelpoException e) {

                }

            } else {
                while (!IsCresitCard) {
                    try {
                        nfcData = nfc.activate(3 * 1000); // 10s
                        if (null != nfcData) {
                            long currentTime = Calendar.getInstance().getTimeInMillis();
                            if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
                                lastClickTime = currentTime;
                                handler.sendMessage(handler.obtainMessage(SHOW_NFC_DATA, nfcData));
                                sleep(2000);
                            }
                        } else {
                            Log.d("GetNFC----------->", "Check MagneticCard timeout...");
                        }
                    } catch (Exception e) {

                    }
                }
            }

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(CtnCreditCardActivity.this, GetSaleTicketActivity.class);
            CtnCreditCardActivity.this.startActivity(intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeNfc();
        IsCresitCard = true;
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


}
