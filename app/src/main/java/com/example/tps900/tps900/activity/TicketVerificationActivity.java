package com.example.tps900.tps900.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.tps900.tps900.Bean.ConsumeTicketsBean;
import com.example.tps900.tps900.Bean.GetOverifByEcodeBean;
import com.example.tps900.tps900.Bean.GetTicketCode;
import com.example.tps900.tps900.Bean.GetUpadateTicketBean;
import com.example.tps900.tps900.Bean.TicketPrintBean;
import com.example.tps900.tps900.R;
import com.example.tps900.tps900.Utlis.Constant;
import com.example.tps900.tps900.Utlis.OtherUtils;
import com.example.tps900.tps900.Utlis.ThreadPoolUtils;
import com.example.tps900.tps900.Utlis.TpsN900PrintUtils;
import com.example.tps900.tps900.Webservice.GsWebServiceUtils;
import com.godfery.Utils.ToastUtils;
import com.godfery.keyboard.KeyboardUtil;
import com.godfery.pay.HttpUtils;
import com.godfery.Zxing.CaptureActivity;

import java.util.Timer;
import java.util.TimerTask;

import static com.example.tps900.tps900.Utlis.Dailog.ErrDialog2;
import static com.example.tps900.tps900.Utlis.OtherUtils.Bitmap2Bytes;
import static com.example.tps900.tps900.Utlis.OtherUtils.getTime;
import static com.example.tps900.tps900.Utlis.OtherUtils.sendMessageInfo;
import static com.example.tps900.tps900.Webservice.GsWebServiceUtils.ConsumeTickets;
import static com.example.tps900.tps900.Webservice.GsWebServiceUtils.GetOverifByEcode;
import static com.godfery.keyboard.CustomEditLintener.EditListener;

/**
 * 项目名称：TPS900
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2017/4/17 13:24
 * 修改人：zxh
 * 修改时间：2017/4/17 13:24
 * 修改备注：
 *
 * @author zxh
 */

public class TicketVerificationActivity extends BaseActivity implements BaseActivity.CardCodeInterface {
    //扫描
    private final int Scanning = 1;
    //线上核销
    private final int Onlineverification = 2;
    //线下核销
    private final int Offlineverification = 3;
    //通用失败
    private final int Failed = 4;
    //清空
    private final int Emptied = 5;
    //更新入园次数
    private final int UpdataText = 6;
    TextView ticketName;
    TextView startTime;
    LinearLayout ticketLvBacket;
    TextView endTime;
    TextView ticketPersonNum;
    EditText practicalPersonNum;
    RadioButton ticketConfirm;
    RadioButton ticketCancel;
    ImageView ticketImgDelete;
    TextView ticketState;
    TextView ticketType;
    EditText ticketEtTicketCode;
    ImageView ticketImgDeleteCode;
    Button ticketQueryTicket;
    Button ticketBtnBor;
    Button ticketBtnComit;
    private Timer mTimer;
    private GetTicketCode getTicketCode;
    private GetUpadateTicketBean getUpadateTicketBean;
    //二维码,门票名称,开始日期,结束日期,可入景区数,可入人数
    private String barcode, getTicketName, startDateTime, inParkTime, endDateTime, personNum;
    private String barcode2 = "";
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                //扫描
                case Scanning:
                    Intent intent = new Intent(TicketVerificationActivity.this, CaptureActivity.class);
                    startActivityForResult(intent, 100);
                    break;
                //线下核销
                case Offlineverification:
                    Ticket_H(barcode2, Integer.valueOf(practicalPersonNum.getText().toString()));
                    timerTask();
                    break;
                //线上核销
                case Onlineverification:
                    ConTickets(barcode, practicalPersonNum.getText().toString().trim());
                    timerTask();
                    break;
                //通用失败原因
                case Failed:
                    String msgInfo = msg.obj.toString();
                    ErrDialog2(TicketVerificationActivity.this, msgInfo, "");
                    timerTask();
                    break;
                case Emptied://清除数据
                    ticketName.setText("");
                    ticketState.setText("");
                    startTime.setText("");
                    endTime.setText("");
                    ticketPersonNum.setText("");
                    practicalPersonNum.setText("");
                    ticketType.setText("");
                    ticketEtTicketCode.setText("");
                    yearCode_img_lv.setVisibility(View.GONE);
                    break;
                case UpdataText:
                    practicalPersonNum.setText(inParkTime);
                    break;
                default:
                    break;
            }
        }
    };
    private String TAG = "TicketVerActivity";
    private GetOverifByEcodeBean getobe;
    private ProgressDialog progressDialog;
    //通用错误
    private String msgErr;
    private ConsumeTicketsBean ticketsBean;
    private byte[] buff = new byte[125 * 250];
    private ImageView yearImage;
    private Drawable drawable;
    private Bitmap bitmap;
    private BitmapDrawable drawable2;
    private LinearLayout yearCode_img_lv;
    private Button ticket_btn_code;
    private String type = "";

    /**
     * 设置布局
     *
     * @return
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_ticket_verification;
    }

    /**
     * @author zxh
     * created at 2017/6/7 15:56
     * 方法名: initView
     * 方法说明: 初始化操作
     */
    @Override
    public void initView() {
        setCardInterface(this);
        startTime = (TextView) findViewById(R.id.start_time);
        endTime = (TextView) findViewById(R.id.end_time);
        ticketPersonNum = (TextView) findViewById(R.id.ticket_personNum);
        practicalPersonNum = (EditText) findViewById(R.id.practical_personNum);
        ticketConfirm = (RadioButton) findViewById(R.id.ticket_confirm);
        ticketCancel = (RadioButton) findViewById(R.id.ticket_cancel);
        ticketImgDelete = (ImageView) findViewById(R.id.ticket_img_delete);
        ticketState = (TextView) findViewById(R.id.ticket_State);
        ticketType = (TextView) findViewById(R.id.ticket_type);
        ticketEtTicketCode = (EditText) findViewById(R.id.ticket_et_ticketCode);
        ticketImgDeleteCode = (ImageView) findViewById(R.id.ticket_img_deleteCode);
        ticketQueryTicket = (Button) findViewById(R.id.ticket_QueryTicket);
        ticket_btn_code = (Button) findViewById(R.id.ticket_btn_code);
        ticketBtnBor = (Button) findViewById(R.id.ticket_btn_bor);
        ticketBtnComit = (Button) findViewById(R.id.ticket_btn_comit);
        ticketName = (TextView) findViewById(R.id.ticket_name);
        ticketLvBacket = (LinearLayout) findViewById(R.id.ticket_lv_back);//
        yearCode_img_lv = (LinearLayout) findViewById(R.id.yearCode_img_lv);//yearCode_img_lv
        progressDialog = new ProgressDialog(TicketVerificationActivity.this);
        yearImage = (ImageView) findViewById(R.id.ticket_img_web);
        drawable = getResources().getDrawable(R.mipmap.touxiangyonghu_03);
        bitmap = OtherUtils.drawableToBitmap(drawable);
        drawable2 = new BitmapDrawable(bitmap);
        yearImage.setBackground(drawable2);
        yearCode_img_lv.setVisibility(View.GONE);
    }

    /**
     * @author zxh
     * created at 2017/6/7 16:15
     * 方法名: initData
     * 方法说明: 一些数据操作
     */
    @Override
    public void initData() {
        EditListener(practicalPersonNum);
        EditListener(ticketEtTicketCode);
        practicalPersonNum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                practicalPersonNum.setHint("");
                ticketEtTicketCode.setHint("请输入门票串码");
                new KeyboardUtil(getApplicationContext(), TicketVerificationActivity.this, practicalPersonNum, R.id.schemas_key_keyboard_view).showKeyboard();
            }
        });
        ticketEtTicketCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                ticketEtTicketCode.setHint("");
                practicalPersonNum.setHint("请输入园次数");
                new KeyboardUtil(getApplicationContext(), TicketVerificationActivity.this, ticketEtTicketCode, R.id.schemas_key_keyboard_view).showKeyboard();

            }
        });
        practicalPersonNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String ticketNum = s.toString().trim();
                String state = ticketState.getText().toString().trim();
                if (!TextUtils.isEmpty(ticketNum) && TextUtils.isEmpty(state) && Integer.valueOf(inParkTime) != 0) {
                    if (Integer.valueOf(ticketNum) > Integer.valueOf(inParkTime) || Integer.valueOf(ticketNum) == 0) {
                        ToastUtils.show(TicketVerificationActivity.this, "请输入正确入园次数");
                        sendMessageInfo(handler, UpdataText);
                    }
                }

            }
        });
        ticketQueryTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessageInfo(handler,Emptied);
                String ticketCode = ticketEtTicketCode.getText().toString().trim();
                Log.e("门票串码---1", ticketCode + "");
                if (TextUtils.isEmpty(ticketCode)) {
                    ToastUtils.show(TicketVerificationActivity.this, "请输入门票串码");
                } else {
                    barcode = ticketCode;
                    btn_QueryticketInfo(barcode);
                }
            }
        });
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.ticket_et_ticketCode) {
            ticketEtTicketCode.setHint("");
            practicalPersonNum.setHint("请输入园次数");
            new KeyboardUtil(getApplicationContext(), TicketVerificationActivity.this, ticketEtTicketCode, R.id.schemas_key_keyboard_view).showKeyboard();

        } else if (id == R.id.practical_personNum) {
            practicalPersonNum.setHint("");
            ticketEtTicketCode.setHint("请输入门票串码");
            new KeyboardUtil(getApplicationContext(), TicketVerificationActivity.this, practicalPersonNum, R.id.schemas_key_keyboard_view).showKeyboard();

        } else if (id == R.id.ticket_img_delete) {
            practicalPersonNum.setText("");
        } else if (id == R.id.ticket_img_deleteCode) {
            ticketEtTicketCode.setText("");
        } else if (id == R.id.ticket_QueryTicket) {
            String ticketCode = ticketEtTicketCode.getText().toString().trim();
            Log.e("门票串码---1", ticketCode + "");
            if (TextUtils.isEmpty(ticketCode)) {
                ToastUtils.show(this, "请输入门票串码");
            } else {
                barcode = ticketCode;
                btn_QueryticketInfo(barcode);
            }
        } else if (id == R.id.ticket_btn_bor) {
            sendMessageInfo(handler, Scanning);
        } else if (id == R.id.ticket_btn_comit) {
            ThreadPoolUtils.runTaskInThread(new Runnable() {
                @Override
                public void run() {
                    btn_comit();
                }
            });
        } else if (id == R.id.ticket_lv_back) {
            TicketVerificationActivity.this.startActivity(new Intent(this, MainActivity.class));
            finish();
        } else if (id == R.id.ticket_img_web) {
            if (getTicketCode.MEMBER_PHOTO != null) {
                bitmap = OtherUtils.stringtoBitmap(getTicketCode.MEMBER_PHOTO);
                buff = Bitmap2Bytes(bitmap);
                Intent intent = new Intent(TicketVerificationActivity.this, ImgShowActivity.class);
                intent.putExtra("bitmap", buff);
                TicketVerificationActivity.this.startActivity(intent);
            }else {
                drawable = getResources().getDrawable(R.mipmap.touxiangyonghu_03);
                bitmap = OtherUtils.drawableToBitmap(drawable);
                buff = Bitmap2Bytes(bitmap);
                Intent intent = new Intent(TicketVerificationActivity.this, ImgShowActivity.class);
                intent.putExtra("bitmap", buff);
                TicketVerificationActivity.this.startActivity(intent);
            }

        } else if (id == R.id.ticket_btn_code) {
            HttpUtils.showProgressDialog(progressDialog);
            ThreadPoolUtils.runTaskInThread(new Runnable() {
                @Override
                public void run() {
                    DeviceN900(TicketVerificationActivity.this);
                }
            });
        }
    }

    /**
     * @author zxh
     * created at 2017/6/7 15:54
     * 方法名: Ticket_Verifi 线下门票查询
     * 方法说明: 传入二维码  查看当前票是否有效
     * "TICKET"
     */
    public void Ticket_Verifi(final String barcode, String Type) {
        String GetTicketCheck = GsWebServiceUtils.GetTicketCheck(barcode, Type);
        HttpUtils.exitProgressDialog(progressDialog);
        Log.e("GetTicketCheck门票信息", GetTicketCheck);
        if (GetTicketCheck.equals("err")) {
            sendMessageInfo(handler, Failed, "请求数据失败,请检查网络或者检查配置");
        } else {
            getTicketCode = JSON.parseObject(GetTicketCheck, GetTicketCode.class);
            if (getTicketCode.FLAG && getTicketCode.TICKETNAME != null) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        inParkTime = String.valueOf(getTicketCode.getINPARKTIME());
                        barcode2 = getTicketCode.getBARCODE();
                        getTicketName = getTicketCode.getTICKETNAME();
                        startDateTime = getTicketCode.getSTARTDATETIME();
                        endDateTime = getTicketCode.getENDDATETIME();
                        ticketName.setText(getTicketName);
                        startTime.setText(startDateTime);
                        ticketType.setText("线下票");
                        endTime.setText(endDateTime);
                        ticketPersonNum.setText(String.valueOf(getTicketCode.getINPARKTIME()));
                        practicalPersonNum.setText(String.valueOf(getTicketCode.getINPARKTIME()));
                        Log.e("barcode", barcode);
                        if (getTicketCode.MEMBER_PHOTO == null || TextUtils.isEmpty(getTicketCode.MEMBER_PHOTO)) {
                            drawable = getResources().getDrawable(R.mipmap.touxiangyonghu_03);
                            bitmap = OtherUtils.drawableToBitmap(drawable);
                            drawable2 = new BitmapDrawable(bitmap);
                            yearImage.setBackground(drawable2);
                        } else {
                            yearCode_img_lv.setVisibility(View.VISIBLE);
                            bitmap = OtherUtils.stringtoBitmap(getTicketCode.MEMBER_PHOTO);
                            drawable2 = new BitmapDrawable(bitmap);
                            yearImage.setBackground(drawable2);
                        }
                        //可入人数为1并且为有效票
                        if (1 == getTicketCode.INPARKTIME && "有效票".equals(getTicketCode.SHOWMSG)) {
                            HttpUtils.showProgressDialog(progressDialog);
                            ThreadPoolUtils.runTaskInThread(new Runnable() {
                                @Override
                                public void run() {
                                        Ticket_H(barcode2, Integer.valueOf(practicalPersonNum.getText().toString()));
                                }
                            });
                        } else if (getTicketCode.INPARKTIME > 1) {
                            ticketState.setText("未核销");
                            ticketState.setTextColor(Color.BLACK);
                        } else if (0 == getTicketCode.INPARKTIME) {
                            ticketState.setText(getTicketCode.SHOWMSG);
                            ticketState.setTextColor(Color.RED);
                            timerTask();
                        }
                    }
                });


            } else {
                //线下查询票失败
                sendMessageInfo(handler, Failed, getTicketCode.SHOWMSG + "\n查询票信息失败");

            }
        }

    }

    /**
     * @author zxh
     * created at 2017/6/7 15:54
     * 方法名: Ticket_H 线下核销
     * 方法说明: 传入二维码 和 入园人数进行核销
     */
    public void Ticket_H(final String barcode, final int count) {
        ThreadPoolUtils.runTaskInThread(new Runnable() {
            @Override
            public void run() {
                String ticket_H = GsWebServiceUtils.GetUpdateTicket(barcode, count, "TICKET");
                HttpUtils.exitProgressDialog(progressDialog);
                Log.e("ticket_H门票信息", ticket_H);
                if (ticket_H.equals("err")) {
                    sendMessageInfo(handler, Failed, "请求数据失败,请检查网络或者检查配置");
                } else {
                    getUpadateTicketBean = JSON.parseObject(ticket_H, GetUpadateTicketBean.class);
                    if (getUpadateTicketBean.FLAG) {
                        ThreadPoolUtils.runTaskInUIThread(new Runnable() {
                            @Override
                            public void run() {
                                ticketName.setText(getTicketName);
                                ticketState.setText("已核销");
                                ticketState.setTextColor(Color.RED);
                                startTime.setText(startDateTime);
                                endTime.setText(endDateTime);
                                ticketPersonNum.setText(inParkTime);
                                practicalPersonNum.setText(String.valueOf(getUpadateTicketBean.DATA));
                                ticketType.setText("线下票");
                                String time = getTime();
                                TpsN900PrintUtils.TpsN900Print_ticket(TicketVerificationActivity.this,"门票核销",
                                        "\n门票名称：" + getTicketCode.getTICKETNAME() +
                                                "\n门票价格：" + getTicketCode.getTICKETPRICE() + "元" +
                                                "\n入园开始日期：" + getTicketCode.getSTARTDATETIME() +
                                                "\n入园结束日期：" + getTicketCode.getENDDATETIME() +
                                                "\n门票核销日期：" + time +
                                                "\n可入园次数：" + getTicketCode.getINPARKTIME() + "次"
                                                + "\n实际入园次数：" + count
                                                + "\n门票二维码："
                                        , barcode, "温馨提示:此票只作为核销凭证,不可视为报销凭证");
                                Constant.TicketPintList.clear();
                                Constant.TicketPintList.add(new TicketPrintBean(
                                        time,
                                        getTicketCode.getTICKETNAME(),
                                        getTicketCode.getTICKETPRICE(),
                                        String.valueOf(getTicketCode.getINPARKTIME()),
                                        String.valueOf(count),
                                        getTicketCode.getSTARTDATETIME(),
                                        getTicketCode.getENDDATETIME(),barcode));
                                if (ticketState.getText().toString().equals("已核销")) {
                                    timerTask();
                                } else if (ticketState.getText().toString().equals("核销失败")) {
                                    timerTask();
                                }
                            }
                        });
                    } else {
                        ThreadPoolUtils.runTaskInUIThread(new Runnable() {
                            @Override
                            public void run() {
                                ticketState.setText("核销失败");
                                ticketState.setTextColor(Color.RED);
                            }
                        });
                        //核销失败
                        sendMessageInfo(handler, Failed, "核销失败" + getUpadateTicketBean.MSG + "\n请检查网络或者检查配置");
                    }
                }
            }
        });
    }

    /**
     * @author zxh
     * created at 2017/6/7 15:53
     * 方法名: GetOByEcode 大平台门票查询
     * 方法说明:
     */
    public void GetOByEcode(final String Ecode, final String OrderNo) {
        try {
            String f00001409 = GetOverifByEcode(Ecode, OrderNo);
            Log.e("线上查询信息", f00001409);
            HttpUtils.exitProgressDialog(progressDialog);
            if (TextUtils.isEmpty(f00001409) || f00001409.equals("err")) {
                sendMessageInfo(handler, Failed, "请求数据失败,请检查网络或者检查配置");
            } else if (!TextUtils.isEmpty(f00001409) && !f00001409.equals("err")) {
                getobe = JSON.parseObject(f00001409, GetOverifByEcodeBean.class);
                if (getobe.Success && getobe.Data != null) {
                    ThreadPoolUtils.runTaskInUIThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.e(TAG, getobe.Message + "正确");
                            barcode2 = Ecode;
                            inParkTime = getobe.Data.ProductCount;
                            ticketName.setText(getobe.Data.ProductName);
                            startTime.setText(getobe.Data.PlayStartDate);
                            endTime.setText(getobe.Data.PlayEndDate);
                            ticketPersonNum.setText(getobe.Data.ProductCount);
                            practicalPersonNum.setText(getobe.Data.ProductCount);
                            ticketEtTicketCode.setText(Ecode);
                            ticketType.setText("线上票");
                            if (inParkTime.equals("1")) {
                                HttpUtils.showProgressDialog(progressDialog);
                                ThreadPoolUtils.runTaskInThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ConTickets(Ecode, inParkTime);
                                    }
                                });
                            } else {
                                ticketState.setText("未核销");
                                ticketState.setTextColor(Color.BLACK);
                            }
                        }
                    });
                } else {
                    //线上查询失败
                    sendMessageInfo(handler, Failed, getobe.Message + "\n查询票信息失败");
                }
            }
        } catch (Exception e) {

        }
    }

    /**
     * @author zxh
     * created at 2017/6/7 15:53
     * 方法名: ConTickets 大平台门票核销
     * 方法说明: 传入二维码和人数进行核销
     */
    public void ConTickets(final String ECode, final String VerifNum) {
        ThreadPoolUtils.runTaskInThread(new Runnable() {
            @Override
            public void run() {
                try {
                    String consumeTickets = ConsumeTickets(ECode, VerifNum);
                    HttpUtils.exitProgressDialog(progressDialog);
                    if (!TextUtils.isEmpty(consumeTickets)) {
                        if (consumeTickets.equals("err")) {
                            //网络原因或者接口中断
                            sendMessageInfo(handler, Failed, "请求数据失败,请检查网络或者检查配置");
                        } else {
                            ticketsBean = JSON.parseObject(consumeTickets, ConsumeTicketsBean.class);
                            if (ticketsBean.Success) {
                                Log.e(TAG, ticketsBean.Message + "正确");
                                ThreadPoolUtils.runTaskInUIThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ticketType.setText("线上票");
                                        ticketState.setText("已核销");
                                        ticketState.setTextColor(Color.RED);
                                        String time = getTime();
                                        TpsN900PrintUtils.TpsN900Print_ticket(TicketVerificationActivity.this,"门票核销",
                                                "\n门票名称：" + getobe.Data.ProductName +
                                                        "\n门票价格：" + getobe.Data.PrintPrice + "元" +
                                                        "\n入园开始日期：" + getobe.Data.PlayStartDate +
                                                        "\n入园结束日期：" + getobe.Data.PlayEndDate +
                                                        "\n门票核销日期：" + time +
                                                        "\n可入园次数：" + getobe.Data.ProductCount + "次"
                                                        + "\n实际入园次数：" + VerifNum
                                                        + "\n门票二维码："
                                                , ECode, "温馨提示:此票只作为核销凭证,不可视为报销凭证");
                                        Constant.TicketPintList.clear();
                                        Constant.TicketPintList.add(new TicketPrintBean(
                                                time,
                                                getobe.Data.ProductName,
                                                getobe.Data.PrintPrice,
                                                getobe.Data.ProductCount,
                                                VerifNum,
                                                getobe.Data.PlayStartDate,
                                                getobe.Data.PlayEndDate,barcode));
                                        if (ticketState.getText().toString().equals("已核销")) {
                                            timerTask();
                                        } else if (ticketState.getText().toString().equals("核销失败")) {
                                            timerTask();
                                        }
                                    }
                                });
                            } else {
                                Log.e(TAG, "失败了" + ticketsBean.Message);
                                //核销失败
                                sendMessageInfo(handler, Failed, ticketsBean.Message + "\n核销失败");
                            }
                        }
                    }
                    Log.e(TAG, "核销信息" + consumeTickets);
                } catch (Exception e) {

                }
            }
        });
    }

    /**
     * @author zxh
     * created at 2017/6/7 15:52
     * 方法名: 扫描核销方法
     * 方法说明: 扫描到结果进行查询核销
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (data != null) {
            barcode = data.getStringExtra("result");
            if (!TextUtils.isEmpty(barcode)) {
                btn_QueryticketInfo(barcode);
            }
            return;
        } else {
            Toast.makeText(TicketVerificationActivity.this, "扫描结果为空", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * @author zxh
     * created at 2017/6/7 15:50
     * 方法名: timerTask
     * 方法说明:  定时消除字体
     */
    private void timerTask() {
        if (mTimer != null) {
            mTimer.cancel();// 退出之前的mTimer
        }
        mTimer = new Timer();
        TimerTask timerTask01 = new TimerTask() {
            @Override
            public void run() {
                //清空页面
                sendMessageInfo(handler, Emptied);
            }
        };
        mTimer.schedule(timerTask01, 3000);
    }

    //提交数据
    public void btn_comit() {
        TicketVerificationActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (TextUtils.isEmpty(ticketState.getText().toString().trim())) {
                    ToastUtils.show(TicketVerificationActivity.this, "请先扫描门票");
                    return;
                } else if (ticketState.getText().toString().trim().equals("已核销")) {
                    ToastUtils.show(TicketVerificationActivity.this, "请先扫描门票");
                    return;
                } else {
                    String ticketNum = practicalPersonNum.getText().toString().trim();
                    if (TextUtils.isEmpty(ticketNum)) {
                        ToastUtils.show(TicketVerificationActivity.this, "请输入入园次数");
                        practicalPersonNum.setText(inParkTime);
                    } else if (Integer.valueOf(ticketNum) > Integer.valueOf(inParkTime) || Integer.valueOf(ticketNum) == 0) {
                        ToastUtils.show(TicketVerificationActivity.this, "请输入正确入园次数");
                        practicalPersonNum.setText(inParkTime);
                    } else {
                        if (barcode.length() >= 24) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    HttpUtils.showProgressDialog(progressDialog);
                                }
                            });
                            //线下核销
                            sendMessageInfo(handler, Offlineverification);
                        } else if (barcode.length() < 24 && barcode.length() > 10) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    HttpUtils.showProgressDialog(progressDialog);
                                }
                            });
                            //线上核销
                            sendMessageInfo(handler, Onlineverification);
                        } else if (barcode.length() < 24 && barcode.length() <= 10) {//线下票
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    HttpUtils.showProgressDialog(progressDialog);
                                }
                            });
                            //线下核销
                            sendMessageInfo(handler, Offlineverification);

                        }
                    }
                }
            }
        });
    }

    /**
     * 根据串码判断是线上票还是线下票
     *
     * @param barcode
     */
    public void btn_QueryticketInfo(final String barcode) {
        //根据主页面传递过来的二维码判断是否是有效票
        if (!TextUtils.isEmpty(barcode)) {
            if (barcode.length() >= 24) {//大于24位的就是线下票
                HttpUtils.showProgressDialog(progressDialog);
                ThreadPoolUtils.runTaskInThread(new Runnable() {
                    @Override
                    public void run() {
                        Ticket_Verifi(barcode, "TICKET");
                    }
                });
            } else if (barcode.length() < 24 && barcode.length() > 10) {//小于24位的就是线上票
                HttpUtils.showProgressDialog(progressDialog);
                ThreadPoolUtils.runTaskInThread(new Runnable() {
                    @Override
                    public void run() {
                        GetOByEcode(barcode, "");
                        if (ticketState.getText().toString().equals("已核销")) {
                            timerTask();
                        } else if (ticketState.getText().toString().equals("核销失败")) {
                            timerTask();
                        }
                    }
                });

            } else if (barcode.length() < 24 && barcode.length() <= 10) {//线下票
                HttpUtils.showProgressDialog(progressDialog);
                ThreadPoolUtils.runTaskInThread(new Runnable() {
                    @Override
                    public void run() {
                        Ticket_Verifi(barcode, "MEMBER");
                    }
                });

            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            openActivityAndCloseThis(MainActivity.class);
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
        HttpUtils.exitProgressDialog(progressDialog);
        barcode = cardNo;
        btn_QueryticketInfo(cardNo);
    }

}
