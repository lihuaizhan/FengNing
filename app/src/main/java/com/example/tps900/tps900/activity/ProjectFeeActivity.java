package com.example.tps900.tps900.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.tps900.tps900.Bean.Project_feeBean;
import com.example.tps900.tps900.R;
import com.example.tps900.tps900.Utlis.Constant;
import com.example.tps900.tps900.Utlis.Dailog;
import com.example.tps900.tps900.Utlis.DialogUtil;
import com.example.tps900.tps900.Utlis.GuiNumberKeyBoard_PF;
import com.example.tps900.tps900.Utlis.OtherUtils;
import com.example.tps900.tps900.Utlis.ToastUtils;
import com.example.tps900.tps900.Webservice.GsWebServiceUtils;
import com.godfery.Zxing.CaptureActivity;
import com.godfery.pay.HttpUtils;

import java.text.DecimalFormat;
import java.text.ParseException;

import static com.example.tps900.tps900.R.id.pf_E_price;
import static com.example.tps900.tps900.Utlis.OtherUtils.Print;
import static com.example.tps900.tps900.Utlis.OtherUtils.doubleprice;
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

public class ProjectFeeActivity extends BaseActivity implements View.OnFocusChangeListener, View.OnTouchListener ,BaseActivity.CardCodeInterface{

    EditText pfEPrice;

    TextView pfTTicketName;

    EditText pfETicketNum;

    EditText pfEOneCard;

    TextView pfTCountPrice;


    ImageView pfIPriceD;

    ImageView pfINumD;

    ImageView pfICodeD;

    RelativeLayout pfLvBack;

    ImageButton pfImgCash;

    ImageButton pfImgWechat;

    ImageButton pfImgAlipay;

    ImageButton pfImgOnecard;
    private final int SHOW_NFC_DATA = 2;
    private final int CHECK_NFC_TIMEOUT = 1;
    int flag = 0;
    private GuiNumberKeyBoard_PF keyboard;
    public String ticketName = "", ticketPrice = "", nticketid = "", codeNum = "";

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case 3:
                    HttpUtils.showProgressDialog(progressDialog);
                    DeviceN900(ProjectFeeActivity.this);
                    break;
                case 4:
//                    DialogUtil.cancelDownloadDialog();
                    HttpUtils.exitProgressDialog(progressDialog);
//                    NormalDialogStyleOne(ProjectFeeActivity.this, "支付错误" + "\n" + project_feeBean.erro, " ", "");
                    Dailog.ErrDialog2(ProjectFeeActivity.this,  "支付错误" + "\n" + project_feeBean.erro, "");
                    break;
                case 5:
//                    DialogUtil.cancelDownloadDialog();
                    HttpUtils.exitProgressDialog(progressDialog);
                    ToastUtils.show(ProjectFeeActivity.this, "支付成功");
                    try {//总价- 单价*数量=剩余
                        Constant.PrintBean = project_feeBean;
                        Constant.Price = price;
                        Print(ProjectFeeActivity.this, OtherUtils.doubleprice(String.valueOf(price)), project_feeBean);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Constant.Code = "";
                    Intent intent = new Intent(ProjectFeeActivity.this, GetSaleTicketActivity.class);
                    ProjectFeeActivity.this.startActivity(intent);
                    ProjectFeeActivity.this.finish();
                    break;
                case 6:
                    HttpUtils.exitProgressDialog(progressDialog);
                    Dailog.ErrDialog2(ProjectFeeActivity.this, "获取数据异常请查网络\n或者检查一下配置!!!", "");
                    break;
                case 7://判断是支付宝或者是微信进行扣费
                    if (!TextUtils.isEmpty(AlipayOrWeChat)) {
                        if (AlipayOrWeChat.equals("微信")) {
                            if (qrcode.length() == 18 && (qrcode.substring(0, 2).equals("10") || qrcode.substring(0, 2).equals("12") || qrcode.substring(0, 2).equals("13") || qrcode.substring(0, 2).equals("14") || qrcode.substring(0, 2).equals("15"))) {
                                Payment("", "微信", 5, qrcode, "微信");
                            } else {
                                Dailog.ErrDialog2(ProjectFeeActivity.this, "支付错误" + "\n" + "支付方式不匹配", "");
                            }

                        } else if (AlipayOrWeChat.equals("支付宝")) {
                            if (qrcode.length() == 18 && qrcode.substring(0, 2).equals("28")) {
                                Payment("", "支付宝", 5, qrcode, "支付宝");
                            } else {
                                Dailog.ErrDialog2(ProjectFeeActivity.this, "支付错误" + "\n" + "支付方式不匹配", "");
                            }

                        }
                        AlipayOrWeChat = "";
                    } else {
                        Log.e(TAG, "编码为空");
                    }

                    break;
                default:
                    break;
            }
        }
    };
    private int sdkInt;
    private double price = 0.00;
    private long Number = 0;
    private ProBroadcastReceiver Pro;
    private Project_feeBean project_feeBean;
    private boolean wife = false;
    private String AlipayOrWeChat = "";
    private String qrcode;
    private String TAG = "ProjectFeeActivity";

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_project_fee);
//
//
//        sdkInt = Build.VERSION.SDK_INT;
//        wife = NetState.isNetworkAvailable(ProjectFeeActivity.this);
//        if (wife) {
//
//
//        } else {
//            ToastUtils.show(ProjectFeeActivity.this, "网络异常");
//            return;
//        }
//
//    }

    /**
     * 设置布局
     *
     * @return
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_project_fee;
    }

    @Override
    public void initView() {
        setCardInterface(this);
        sdkInt = Build.VERSION.SDK_INT;
        keyboard = new GuiNumberKeyBoard_PF(ProjectFeeActivity.this);
        pfEPrice = (EditText) findViewById(pf_E_price);
        pfTTicketName = (TextView) findViewById(R.id.pf_T_ticketName);
        pfETicketNum = (EditText) findViewById(R.id.pf_E_ticketNum);
        pfEOneCard = (EditText) findViewById(R.id.pf_E_oneCard);
        pfTCountPrice = (TextView) findViewById(R.id.pf_T_countPrice);
        pfIPriceD = (ImageView) findViewById(R.id.pf_I_priceD);
        pfINumD = (ImageView) findViewById(R.id.pf_I_numD);
        pfICodeD = (ImageView) findViewById(R.id.pf_I_codeD);
        pfLvBack = (RelativeLayout) findViewById(R.id.pf_lv_back);
        pfImgCash = (ImageButton) findViewById(R.id.pf_img_cash);
        pfImgWechat = (ImageButton) findViewById(R.id.pf_img_wechat);
        pfImgAlipay = (ImageButton) findViewById(R.id.pf_img_alipay);
        pfImgOnecard = (ImageButton) findViewById(R.id.pf_img_onecard);
        Constant.context = ProjectFeeActivity.this;


        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("Intent");
        intentFilter.addAction("WebService");
        intentFilter.addAction("Code");
        if (Pro == null) {
            Pro = new ProBroadcastReceiver();
        }
        ProjectFeeActivity.this.registerReceiver(Pro, intentFilter);
        isPayCode();
    }

    @Override
    public void initData() {
        try {
            pfEOneCard.setOnFocusChangeListener(this);
            pfEOneCard.setOnTouchListener(this);

            pfEPrice.setOnFocusChangeListener(this);
            pfEPrice.setOnTouchListener(this);

            pfETicketNum.setOnFocusChangeListener(this);
            pfETicketNum.setOnTouchListener(this);

            pfEPrice.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void afterTextChanged(Editable s) {
                    try {

                        if (s.length() <= 10) {
                            pfEPrice.setSelection(s.length()); // 将光标移至文字末尾
                        }
                        if (TextUtils.isEmpty(pfETicketNum.getText().toString())) {
                            price = 0.00;
                            pfTCountPrice.setText("0.00" + "元");
                        } else if (pfEPrice.getText().toString().equals("0") || pfEPrice.getText().toString().equals("")) {
                            pfTCountPrice.setText("0.00" + "元");
                        } else if (pfEPrice.getText().toString().length() > 4) {
                            // com.godfery.Utils.ToastUtils.show(ProjectFeeActivity.this, "金额超限!!!\n请重新输入");
                            pfEPrice.setText("");
                        } else {

                            boolean priceB = OtherUtils.isNumber(pfEPrice.getText().toString());
                            if (priceB) {
                                price = doubleprice(pfEPrice.getText().toString());
                                Number = Long.valueOf(pfETicketNum.getText().toString());
                                price = price * Number;
                                try {
                                    pfTCountPrice.setText(doubleprice(String.valueOf(price)) + "元");
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                ToastUtils.show(ProjectFeeActivity.this, "金额错误");
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });//默认光标在字体最后
            pfETicketNum.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() <= 10) {
                        pfETicketNum.setSelection(s.length());
                    }
                    if (TextUtils.isEmpty(pfETicketNum.getText().toString())) {
                        pfTCountPrice.setText("0.00" + "元");
                    } else if (pfETicketNum.getText().toString().equals("0")) {
                        pfTCountPrice.setText("0.00" + "元");
                    } else if (pfETicketNum.getText().toString().length() > 5) {
                        com.godfery.Utils.ToastUtils.show(ProjectFeeActivity.this, "人数超限!!!\n请重新输入");
                        pfETicketNum.setText("");
                    } else {
                        if (TextUtils.isEmpty(pfEPrice.getText().toString())) {
                            price = 0.00;
                        } else {
                            price = Double.parseDouble(pfEPrice.getText().toString());

                        }
                        Number = Long.valueOf(pfETicketNum.getText().toString());
                        price = price * Number;
                        pfTCountPrice.setText(new DecimalFormat("#0.00").format(price) + "元");

                    }
                }
            });//默认光标在字体最后
        } catch (Exception e) {

        }
        ticketName = getIntent().getStringExtra("ticketname");
        ticketPrice = getIntent().getStringExtra("ticketprice");
        nticketid = getIntent().getStringExtra("nticketid");
        pfTTicketName.setText(ticketName);
        pfEPrice.setText(ticketPrice + "");
        pfETicketNum.setText("1");
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.pf_I_priceD) {
            pfEPrice.setText("");
        } else if (id == R.id.pf_I_numD) {
            pfETicketNum.setText("");
        } else if (id == R.id.pf_I_codeD) {
            pfEOneCard.setText("");
        } else if (id == R.id.pf_E_oneCard) {
            keyboard = new GuiNumberKeyBoard_PF(ProjectFeeActivity.this, pfEOneCard);

        } else if (id == pf_E_price) {
            keyboard = new GuiNumberKeyBoard_PF(ProjectFeeActivity.this, pfEPrice);

        } else if (id == R.id.pf_E_ticketNum) {
            keyboard = new GuiNumberKeyBoard_PF(ProjectFeeActivity.this, pfETicketNum);

        } else if (id == R.id.pf_lv_back) {
            Intent intent = new Intent(ProjectFeeActivity.this, GetSaleTicketActivity.class);
            ProjectFeeActivity.this.startActivity(intent);
            ProjectFeeActivity.this.finish();
        } else if (id == R.id.pf_img_cash) {
            if (!TextUtils.isEmpty(pfEPrice.getText().toString().trim())) {
                String trim3 = pfETicketNum.getText().toString().trim();
                if (!TextUtils.isEmpty(trim3)) {
                    Payment("", "人民币", 7, "", "人民币");
                } else {
                    Toast.makeText(this, "请输入门票数量", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "请输入门票价格", Toast.LENGTH_SHORT).show();
            }

        } else if (id == R.id.pf_img_wechat) {
            if (!TextUtils.isEmpty(pfEPrice.getText().toString().trim())) {
                String trim3 = pfETicketNum.getText().toString().trim();
                if (!TextUtils.isEmpty(trim3)) {
                    AlipayOrWeChat = "微信";
                    Intent intent = new Intent(ProjectFeeActivity.this, CaptureActivity.class);
                    startActivityForResult(intent, 100);
                } else {
                    Toast.makeText(this, "请输入门票数量", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "请输入门票价格", Toast.LENGTH_SHORT).show();
            }

        } else if (id == R.id.pf_img_alipay) {

            String trim3 = pfETicketNum.getText().toString().trim();
            if (!TextUtils.isEmpty(pfEPrice.getText().toString().trim())) {
                if (!TextUtils.isEmpty(trim3)) {
                    AlipayOrWeChat = "支付宝";
                    Intent intent = new Intent(ProjectFeeActivity.this, CaptureActivity.class);
                    startActivityForResult(intent, 100);
                } else {
                    Toast.makeText(this, "请输入门票数量", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "请输入门票价格", Toast.LENGTH_SHORT).show();
            }

        } else if (id == R.id.pf_img_onecard) {
            sendMessageInfo(handler,3);
        }
    }

    /**
     * 读取卡号成功回调
     *
     * @param cardNo 返回卡号
     */
    @Override
    public void onReadCardOk(String cardNo) {
        Payment("code", "一卡通", 6, cardNo + "", "一卡通");
        Constant.Code = codeNum;
    }



    public class ProBroadcastReceiver extends BroadcastReceiver {
        public ProBroadcastReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case "Code":
                    sendMessageInfo(handler,3);
                    break;
                case "Intent":
                    intent = new Intent(ProjectFeeActivity.this, GetSaleTicketActivity.class);
                    ProjectFeeActivity.this.startActivity(intent);
                    finish();
                    break;
                default:
                    break;
            }

        }
    }

    @Override//扫描核销方法
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            qrcode = data.getStringExtra("result");
            Message message = new Message();
            message.what = 7;
            handler.sendMessage(message);
        } else {
            com.godfery.Utils.ToastUtils.show(ProjectFeeActivity.this, "扫描结果为空");
        }
    }


    public void Payment(String card, String payName, final int nincometype, final String payNumber, String PrintPayName) {
        if (card.equals("")) {
            if (!TextUtils.isEmpty(payNumber)) {
                paymentCode(PrintPayName, payName, nincometype, payNumber);
            } else {
                if (nincometype == 7) {
                    paymentCode(PrintPayName, payName, nincometype, "");
                }

            }

        } else {
            paymentCode(PrintPayName, payName, nincometype, payNumber);
        }


    }

    public void paymentCode(final String PrintPayName, final String payName, final int nincometype, final String payNumber) {
        try {
            ProjectFeeActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    HttpUtils.showProgressDialog(progressDialog);
//                    DialogUtil.showPayDialog(ProjectFeeActivity.this);
                }
            });

            new Thread(new Runnable() {
                @Override
                public void run() {
                    String result = null;
                    result = GsWebServiceUtils.GetSaleTicketInfo(
                            new DecimalFormat("#0.00").format(price), nticketid, pfEPrice.getText().toString(), String.valueOf(Number)
                            , payName, new DecimalFormat("#0.00").format(price), Constant.emp_id, Constant.ter_id, nincometype, payNumber);

                    Log.e("付款结果----------------》", result);
                    Constant.Code = "";
                    if (result.equals("err")) {
                        Message msg = Message.obtain();
                        msg.what = 6;
                        handler.sendMessage(msg);
                    } else {
                        project_feeBean = JSON.parseObject(result, Project_feeBean.class);
                        if (project_feeBean.flag == true) {
                            project_feeBean.setPayName(PrintPayName);
                            Message msg = Message.obtain();
                            msg.what = 5;
                            handler.sendMessage(msg);

                            return;
                        } else {
                            Message msg = Message.obtain();
                            msg.what = 4;
                            handler.sendMessage(msg);

                        }
                    }
                }
            }).start();
        } catch (Exception e) {
            ProjectFeeActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    DialogUtil.cancelDownloadDialog();
                }
            });
        }
    }

    //判断有几种付款方式
    public void isPayCode() {

        if (Constant.WeChat == 0) {
            pfImgWechat.setBackgroundResource(R.drawable.d_pay_wechat);
            pfImgWechat.setEnabled(false);
        } else {
            pfImgWechat.setBackgroundResource(R.drawable.pay_wechat_selector);
            pfImgWechat.setEnabled(true);
        }
        if (Constant.Alipay == 0) {
            pfImgAlipay.setBackgroundResource(R.drawable.d_pay_alipay);
            pfImgAlipay.setEnabled(false);
        } else {
            pfImgAlipay.setBackgroundResource(R.drawable.pay_alipay_selector);
            pfImgAlipay.setEnabled(true);
        }
        if (Constant.CaSh == 0) {
            pfImgCash.setBackgroundResource(R.drawable.d_pay_cash);
            pfImgCash.setEnabled(false);
        } else {
            pfImgCash.setBackgroundResource(R.drawable.pay_cash_selector);
            pfImgCash.setEnabled(true);
        }
        if (Constant.OneCard == 0) {
            pfImgOnecard.setBackgroundResource(R.drawable.d_pay_onecard);
            pfImgOnecard.setEnabled(false);
        } else {
            pfImgOnecard.setBackgroundResource(R.drawable.pay_onecard_selector);
            pfImgOnecard.setEnabled(true);
        }
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        try {
            EditText et = (EditText) view;
            if (!b) {// 失去焦点
                et.setHint(et.getTag().toString());
            } else {
                String hint = et.getHint().toString();
                et.setTag(hint);//保存预设字
                et.setHint(null);
                int id = view.getId();
                if (id == R.id.pf_E_oneCard) {
                    keyboard = new GuiNumberKeyBoard_PF(ProjectFeeActivity.this, pfEOneCard);
                } else if (id == pf_E_price) {
                    keyboard = new GuiNumberKeyBoard_PF(ProjectFeeActivity.this, pfEPrice);
                } else if (id == R.id.pf_E_ticketNum) {
                    keyboard = new GuiNumberKeyBoard_PF(ProjectFeeActivity.this, pfETicketNum);
                }
            }
        } catch (Exception e) {
        }

    }//获取光标

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //隐藏输入法，显示光标
        EditText et = (EditText) v;
        int inType = et.getInputType(); // back1 up the input type
        keyboard.getCursorIndex(et, inType, v, sdkInt);
        int id = v.getId();
        if (id == R.id.pf_E_oneCard) {
            pfEOneCard.onTouchEvent(event);
            pfEOneCard.setInputType(inType);
        } else if (id == pf_E_price) {
            pfEPrice.onTouchEvent(event);
            pfEPrice.setInputType(inType);
        } else if (id == R.id.pf_E_ticketNum) {
            pfETicketNum.onTouchEvent(event);
            pfETicketNum.setInputType(inType);
        }
        return true;
    }//隐藏系统键盘

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent = new Intent(ProjectFeeActivity.this, GetSaleTicketActivity.class);
        ProjectFeeActivity.this.startActivity(intent);
        finish();
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Pro != null) {
            unregisterReceiver(Pro);
            Pro = null;
        }
    }
}
