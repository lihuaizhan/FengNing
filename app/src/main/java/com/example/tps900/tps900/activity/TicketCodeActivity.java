package com.example.tps900.tps900.activity;

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

import com.alibaba.fastjson.JSON;
import com.example.tps900.tps900.Bean.ConsumeTicketsBean;
import com.example.tps900.tps900.Bean.GetOverifByEcodeBean;
import com.example.tps900.tps900.Bean.GetTicketCode;
import com.example.tps900.tps900.Bean.GetUpadateTicketBean;
import com.example.tps900.tps900.R;
import com.example.tps900.tps900.Utlis.OtherUtils;
import com.example.tps900.tps900.Utlis.TpsN900PrintUtils;
import com.example.tps900.tps900.Webservice.GsWebServiceUtils;
import com.godfery.Utils.ToastUtils;
import com.godfery.keyboard.KeyboardUtil;
import com.godfery.pay.HttpUtils;
import com.godfery.Zxing.CaptureActivity;
import com.telpo.tps550.api.TelpoException;
import com.telpo.tps550.api.nfc.Nfc;
import com.telpo.tps550.api.util.StringUtil;

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
 */

public class TicketCodeActivity extends BaseActivity {


    TextView ticketName;

    TextView startTime;
    LinearLayout ticketLvBacket;

    TextView endTime;

    TextView ticketPersonNum;

    EditText practicalPersonNum;

    ImageView ticketImgDelete;
    TextView ticketState;

    TextView ticketType;

    EditText ticketEtTicketCode;

    ImageView ticketImgDeleteCode;

    Button ticketQueryTicket;

    Button ticketBtnBor;

    Button ticketBtnComit;

    RadioButton ticketTicRadio;

    RadioButton ticketCodeRadio;

    Button ticketGetCard;

    ImageView yearImage;
    private Timer mTimer;
    private GetTicketCode getTicketCode;
    private GetUpadateTicketBean getUpadateTicketBean;
    //二维码,门票名称,开始日期,结束日期,可入景区数,可入人数
    private String barcode, getTicketName, startDateTime, endDateTime, inParkTime, personNum;
    private String barcode2 = "";
    private Nfc nfc;
    private ReadThread readThread;
    private final int CHECK_NFC_TIMEOUT = 1001;
    private final int SHOW_NFC_DATA = 1002;
    private String codeNum;
    private byte[] buff = new byte[125 * 250];
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CHECK_NFC_TIMEOUT: {
                    HttpUtils.exitProgressDialog(progressDialog);
                    ToastUtils.show(TicketCodeActivity.this, "未检测到卡片超时 !");
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
                        ticketEtTicketCode.setText(codeNum);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Ticket_Verifi(codeNum);
                            }
                        }).start();
                    } else {
                        ToastUtils.show(TicketCodeActivity.this, "unknow type card!!");
                    }
                }
                break;
                case 0://核销
                    Ticket_H(barcode2, Integer.valueOf(personNum));
                    try {
                        if (mTimer != null) {
                            mTimer.cancel();
                        }
                        timerTask();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 1:
                    Intent intent = new Intent(TicketCodeActivity.this, CaptureActivity.class);
                    startActivityForResult(intent, 100);
                    break;
                case 2:
                    Ticket_Verifi(barcode);
                    if ("已核销".equals(ticketState.getText().toString())) {
                        try {
                            if (mTimer != null) {
                                mTimer.cancel();
                            }
                            timerTask();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if ("核销失败".equals(ticketState.getText().toString())) {
                        try {
                            if (mTimer != null) {
                                mTimer.cancel();// 退出之前的mTimer
                            }
                            timerTask();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 3:
                    ticketState.setText("核销失败");
                    ticketState.setTextColor(Color.RED);
                    ErrDialog2(TicketCodeActivity.this, "核销失败" + getUpadateTicketBean.MSG + "\n请检查网络或者检查配置", "");
                    break;
                case 4:
                    ErrDialog2(TicketCodeActivity.this, "请求数据失败 请检查网络或者检查配置", "");
                    break;
                case 5:
                    ErrDialog2(TicketCodeActivity.this, getTicketCode.SHOWMSG + "\n查询票信息失败 ", "");
                    break;
                case 6:

                    ErrDialog2(TicketCodeActivity.this, "请求数据失败 请检查网络", "");
                    break;
                case 7://清除数据
                    ticketName.setText("");
                    ticketState.setText("");
                    startTime.setText("");
                    endTime.setText("");
                    ticketPersonNum.setText("");
                    practicalPersonNum.setText("");
                    ticketType.setText("");
                    ticketEtTicketCode.setText("");
                    Drawable drawable = getResources().getDrawable(R.mipmap.touxiangyonghu_03);
                    Bitmap bitmap = OtherUtils.drawableToBitmap(drawable);
                    Drawable drawable2 = new BitmapDrawable(bitmap);
                    Log.e("draw", drawable2 + "");
                    yearImage.setBackground(drawable2);
                    break;
                case 8://大平台查询票信息
                    GetOByEcode(barcode, "");
                    if ("已核销".equals(ticketState.getText().toString())) {
                        try {
                            if (mTimer != null) {
                                mTimer.cancel();// 退出之前的mTimer
                            }
                            timerTask();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if ("核销失败".equals(ticketState.getText().toString())) {
                        try {
                            if (mTimer != null) {
                                mTimer.cancel();// 退出之前的mTimer
                            }
                            timerTask();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 9://大平台核销票信息
                    ConTickets(barcode, practicalPersonNum.getText().toString().trim());
                    try {
                        if (mTimer != null) {
                            mTimer.cancel();// 退出之前的mTimer
                        }
                        timerTask();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 10:
                    String errInfo = msg.obj.toString();
                    ErrDialog2(TicketCodeActivity.this, errInfo, "");
                    break;
                case IsNet:
                    ErrDialog2(TicketCodeActivity.this, "当前无网络\n点击确定设置网络", "1");
                    break;
                default:
            }
        }
    };
    private String TAG = "TicketVerActivity";
    private GetOverifByEcodeBean getobe;
    private Drawable drawable;
    private Bitmap bitmap;
    private BitmapDrawable drawable2;
    private final int IsNet = 11;
    private ConsumeTicketsBean ticketsBean;


    /**
     * 设置布局
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_ticketcode_verification;
    }

    /**
     * @author zxh
     * created at 2017/6/7 15:56
     * 方法名: initView
     * 方法说明: 初始化操作
     */
    @Override
    public void initView() {
        ticketLvBacket = (LinearLayout) findViewById(R.id.ticket_lv_back);
        ticketState = (TextView) findViewById(R.id.ticket_State);
        ticketName = (TextView) findViewById(R.id.ticket_name);
        startTime = (TextView) findViewById(R.id.start_time);
        endTime = (TextView) findViewById(R.id.end_time);
        ticketPersonNum = (TextView) findViewById(R.id.ticket_personNum);
        practicalPersonNum = (EditText) findViewById(R.id.practical_personNum);
        ticketImgDelete = (ImageView) findViewById(R.id.ticket_img_delete);
        ticketType = (TextView) findViewById(R.id.ticket_type);
        ticketEtTicketCode = (EditText) findViewById(R.id.ticket_et_ticketCode);
        ticketImgDeleteCode = (ImageView) findViewById(R.id.ticket_img_deleteCode);
        ticketQueryTicket = (Button) findViewById(R.id.ticket_QueryTicket);
        ticketBtnBor = (Button) findViewById(R.id.ticket_btn_bor);
        ticketBtnComit = (Button) findViewById(R.id.ticket_btn_comit);
        ticketTicRadio = (RadioButton) findViewById(R.id.ticket_tic_radio);
        ticketCodeRadio = (RadioButton) findViewById(R.id.ticket_code_radio);
        ticketGetCard = (Button) findViewById(R.id.ticket_getCard);
        yearImage = (ImageView) findViewById(R.id.ticket_img_web);
        drawable = getResources().getDrawable(R.mipmap.touxiangyonghu_03);
        bitmap = OtherUtils.drawableToBitmap(drawable);
        drawable2 = new BitmapDrawable(bitmap);
        yearImage.setBackground(drawable2);
        getTicketCode = new GetTicketCode();
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
        practicalPersonNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                practicalPersonNum.setSelection(s.length());
                personNum = practicalPersonNum.getText().toString();
            }
        });//默认光标在字体最后
    }


    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.ticket_et_ticketCode) {
            ticketEtTicketCode.setHint("");
            practicalPersonNum.setHint("请输入实到人数");
            new KeyboardUtil(getApplicationContext(), TicketCodeActivity.this, ticketEtTicketCode, R.id.schemas_key_keyboard_view).showKeyboard();

        } else if (id == R.id.practical_personNum) {
            practicalPersonNum.setHint("");
            ticketEtTicketCode.setHint("请输入门票串码");
            new KeyboardUtil(getApplicationContext(), TicketCodeActivity.this, practicalPersonNum, R.id.schemas_key_keyboard_view).showKeyboard();

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
            if (ticketCodeRadio.isChecked()) {
                ToastUtils.show(TicketCodeActivity.this, "请先选择门票核销");
            } else {
                sendMessageInfo(handler, 1, "");
            }
        } else if (id == R.id.ticket_btn_comit) {
            btn_comit();
        } else if (id == R.id.ticket_lv_back) {
            openActivityAndCloseThis(MainActivity.class);
        } else if (id == R.id.ticket_getCard) {
            if (ticketCodeRadio.isChecked()) {
                HttpUtils.showProgressDialog(progressDialog);
                DeviceN900();
            } else {
                ToastUtils.show(TicketCodeActivity.this, "请先选择次卡核销");
            }
        } else if (id == R.id.ticket_img_web) {
            if (getTicketCode.MEMBER_PHOTO == null) {
                Drawable drawable = getResources().getDrawable(R.mipmap.touxiangyonghu_03);
                Bitmap bitmap = OtherUtils.drawableToBitmap(drawable);
                buff = Bitmap2Bytes(bitmap);
                Intent intent = new Intent(TicketCodeActivity.this, ImgShowActivity.class);
                intent.putExtra("bitmap", buff);
                TicketCodeActivity.this.startActivity(intent);
            }
        } else if (id == R.id.ticket_tic_radio) {
            ticketEtTicketCode.setHint("请输入门票串码");
        } else if (id == R.id.ticket_code_radio) {
            ticketEtTicketCode.setHint("请刷卡或请输入卡号");

        }

    }

    /**
     * @author zxh
     * created at 2017/6/7 15:54
     * 方法名: Ticket_Verifi 线下门票查询
     * 方法说明: 传入二维码  查看当前票是否有效
     */
    public void Ticket_Verifi(final String barcode) {
        String Type = "";
        if (ticketCodeRadio.isChecked()) {
            Type = "MEMBER";
        } else {
            Type = "TICKET";
        }
        String GetTicketCheck = GsWebServiceUtils.GetTicketCheck(barcode, Type);
        HttpUtils.exitProgressDialog(progressDialog);
        Log.e("GetTicketCheck门票信息", GetTicketCheck);
        if ("err".equals(GetTicketCheck)) {
            sendMessageInfo(handler, 6, "");
        } else {
            getTicketCode = JSON.parseObject(GetTicketCheck, GetTicketCode.class);
            if (getTicketCode.FLAG && getTicketCode.TICKETNAME != null) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getTicketName = getTicketCode.getTICKETNAME();
                        startDateTime = getTicketCode.getSTARTDATETIME();
                        endDateTime = getTicketCode.getENDDATETIME();
                        startTime.setText(startDateTime);
                        ticketEtTicketCode.setText(barcode);
                        ticketName.setText(getTicketName);
                        ticketType.setText("线下票");
                        endTime.setText(endDateTime);
                        ticketPersonNum.setText(getTicketCode.getINPARKTIME() + "");
                        practicalPersonNum.setText(getTicketCode.getINPARKTIME() + "");
                        if (getTicketCode.MEMBER_PHOTO == null || "".equals(getTicketCode.MEMBER_PHOTO) || TextUtils.isEmpty(getTicketCode.MEMBER_PHOTO)) {
                            drawable = getResources().getDrawable(R.mipmap.touxiangyonghu_03);
                            bitmap = OtherUtils.drawableToBitmap(drawable);
                            drawable2 = new BitmapDrawable(bitmap);
                            yearImage.setBackground(drawable2);
                        } else {
                            bitmap = OtherUtils.stringtoBitmap(getTicketCode.MEMBER_PHOTO);
                            drawable2 = new BitmapDrawable(bitmap);
                            yearImage.setBackground(drawable2);
                        }
                        inParkTime = practicalPersonNum.getText().toString();
                        barcode2 = getTicketCode.getBARCODE();
                        Log.e("barcode", barcode);
                        //如果是一人的话就进行核销
                        Log.d("TicketVerificationActiv", "getTicketCode.inParkTime:" + getTicketCode.INPARKTIME);
                        if (1 == getTicketCode.INPARKTIME && "有效票".equals(getTicketCode.SHOWMSG)) {
                            HttpUtils.showProgressDialog(progressDialog);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Ticket_H(barcode2, Integer.valueOf(practicalPersonNum.getText().toString()));
                                }
                            }).start();
                        } else if (getTicketCode.INPARKTIME > 1) {
                            ticketState.setText("未核销");
                            ticketState.setTextColor(Color.BLACK);
                        } else if (0 == getTicketCode.INPARKTIME) {
                            ticketState.setText(getTicketCode.SHOWMSG);
                            ticketState.setTextColor(Color.BLACK);
                        }
                    }
                });


            } else {
                sendMessageInfo(handler, 5, "");
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
        String Type = "";
        if (ticketCodeRadio.isChecked()) {
            Type = "MEMBER";
        } else {
            Type = "TICKET";
        }
        String ticket_H = GsWebServiceUtils.GetUpdateTicket(barcode, count, Type);
        HttpUtils.exitProgressDialog(progressDialog);
        Log.e("ticket_H门票信息", ticket_H);
        if ("err".equals(ticket_H)) {
            HttpUtils.exitProgressDialog(progressDialog);
            sendMessageInfo(handler, 4, "");
        } else {

            getUpadateTicketBean = JSON.parseObject(ticket_H, GetUpadateTicketBean.class);
            if (getUpadateTicketBean.FLAG) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ticketName.setText(getTicketName);
                        ticketState.setText("已核销");
                        ticketState.setTextColor(Color.RED);
                        startTime.setText(startDateTime);
                        endTime.setText(endDateTime);
                        ticketPersonNum.setText(inParkTime);
                        practicalPersonNum.setText(getUpadateTicketBean.DATA + "");
                        TpsN900PrintUtils.TpsN900Print_ticket(TicketCodeActivity.this,"门票核销",
                                "\n门票名称：" + getTicketCode.getTICKETNAME() +
                                        "\n门票价格：" + getTicketCode.getTICKETPRICE() + "元" +
                                        "\n入园开始日期：" + getTicketCode.getSTARTDATETIME() +
                                        "\n入园结束日期：" + getTicketCode.getENDDATETIME() +
                                        "\n门票核销日期：" + getTime() +
                                        "\n可入园次数：" + getTicketCode.getINPARKTIME() + "次"
                                        + "\n实际入园次数：" + count
                                        + "\n门票二维码："
                                , barcode, "温馨提示:此票只作为核销凭证,不可视为报销凭证");

                        if ("已核销".equals(ticketState.getText().toString())) {
                            try {
                                if (mTimer != null) {
                                    mTimer.cancel();
                                }
                                timerTask();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if ("核销失败".equals(ticketState.getText().toString())) {
                            try {
                                if (mTimer != null) {
                                    mTimer.cancel();// 退出之前的mTimer
                                }
                                timerTask();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }


                });


            } else {
                sendMessageInfo(handler, 3, "");
            }
        }
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
            HttpUtils.exitProgressDialog(progressDialog);
            if (TextUtils.isEmpty(f00001409) || "err".equals(f00001409)) {
                sendMessageInfo(handler, 4, "");
            } else if (!TextUtils.isEmpty(f00001409) && !"err".equals(f00001409)) {
                getobe = JSON.parseObject(f00001409, GetOverifByEcodeBean.class);
                Log.e("线上核销查询结果", f00001409);
                if (getobe.Success && getobe.Data != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.e(TAG, getobe.Message + "正确");
                            ticketName.setText(getobe.Data.ProductName);
                            startTime.setText(getobe.Data.PlayStartDate);
                            endTime.setText(getobe.Data.PlayEndDate);
                            ticketPersonNum.setText(getobe.Data.ProductCount + "");
                            practicalPersonNum.setText(getobe.Data.ProductCount + "");
                            ticketEtTicketCode.setText(Ecode);
                            inParkTime = practicalPersonNum.getText().toString();
                            ticketType.setText("线上票");
                            if ("1".equals(inParkTime)) {
                                HttpUtils.showProgressDialog(progressDialog);
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ConTickets(Ecode, inParkTime);
                                    }
                                }).start();
                            } else {
                                ticketState.setText("未核销");
                                ticketState.setTextColor(Color.BLACK);
                            }
                        }
                    });
                } else {
                    sendMessageInfo(handler, 10, "未查询到门票信息");
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
        try {
            String consumeTickets = ConsumeTickets(ECode, VerifNum);
            HttpUtils.exitProgressDialog(progressDialog);
            if (!TextUtils.isEmpty(consumeTickets)) {
                if ("err".equals(consumeTickets)) {
                    Log.d("门票核销查询", consumeTickets);
                    sendMessageInfo(handler, 4, "");
                } else {
                    Log.d("TicketVerificationActiv", "核销接口返回数据" + consumeTickets);
                    ticketsBean = JSON.parseObject(consumeTickets, ConsumeTicketsBean.class);
                    if (ticketsBean.Success) {
                        Log.e(TAG, ticketsBean.Message + "正确");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ticketState.setText("已核销");
                                ticketState.setTextColor(Color.RED);
                                TpsN900PrintUtils.TpsN900Print_ticket(TicketCodeActivity.this,"门票核销",
                                        "\n门票名称：" + getobe.Data.ProductName +
                                                "\n门票价格：" + getobe.Data.PrintPrice + "元" +
                                                "\n入园开始日期：" + getobe.Data.PlayStartDate +
                                                "\n入园结束日期：" + getobe.Data.PlayEndDate +
                                                "\n门票核销日期：" + getTime() +
                                                "\n可入园人数：" + getobe.Data.ProductCount + "人"
                                                + "\n实际入园人数：" + VerifNum
                                                + "\n门票二维码："
                                        , ECode, "温馨提示:此票只作为核销凭证,不可视为报销凭证");

                                if ("已核销".equals(ticketState.getText().toString())) {
                                    try {
                                        if (mTimer != null) {
                                            mTimer.cancel();
                                        }
                                        timerTask();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else if ("核销失败".equals(ticketState.getText().toString())) {
                                    try {
                                        if (mTimer != null) {
                                            mTimer.cancel();// 退出之前的mTimer
                                        }
                                        timerTask();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                    } else {
                        sendMessageInfo(handler, 10, ticketsBean.Message + "\n门票已核销");
                    }
                }
            }
            Log.e(TAG, "核销信息" + consumeTickets);
        } catch (Exception e) {
            sendMessageInfo(handler, 10, "接口返回数据异常");
        }
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
            sendMessageInfo(handler, 12, "");
            ToastUtils.show(TicketCodeActivity.this, "扫描结果为空");
        }
    }

    /**
     * @author zxh
     * created at 2017/6/7 15:50
     * 方法名: timerTask
     * 方法说明:  定时消除字体
     */
    private void timerTask() {
        mTimer = new Timer();
        TimerTask timerTask01 = new TimerTask() {
            @Override
            public void run() {
                sendMessageInfo(handler, 7, "");
            }
        };
        mTimer.schedule(timerTask01, 3000);
    }

    //提交数据
    public void btn_comit() {
        TicketCodeActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if ("".equals(ticketState.getText().toString().trim())) {
                    ToastUtils.show(TicketCodeActivity.this, "请先扫描门票");
                    return;
                } else if ("已核销".equals(ticketState.getText().toString().trim())) {
                    ToastUtils.show(TicketCodeActivity.this, "请先扫描门票");
                    return;
                } else {
                    if ("0".equals(practicalPersonNum.getText().toString().trim()) ||
                            "".equals(practicalPersonNum.getText().toString().trim()) ||
                            Integer.valueOf(practicalPersonNum.getText().toString().trim()) > Integer.valueOf(inParkTime)) {

                        ToastUtils.show(TicketCodeActivity.this, "请输入正确人数");
                    } else {
                        if (barcode.length() >= 24) {
                            sendMessageInfo(handler, 0);
                        } else if (barcode.length() < 24 && barcode.length() > 0) {
                            sendMessageInfo(handler, 9);
                        }
                    }

                }
            }
        });

    }

    /**
     * 查询
     *
     * @param barcode
     */
    public void btn_QueryticketInfo(final String barcode) {
        //根据主页面传递过来的二维码判断是否是有效票
        if (!TextUtils.isEmpty(barcode)) {
            if (barcode.length() >= 24 || ticketCodeRadio.isChecked()) {//大于24位的就是线下票
                HttpUtils.showProgressDialog(progressDialog);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Ticket_Verifi(barcode);
                    }
                }).start();
            } else if (barcode.length() < 24 && barcode.length() > 0) {//小于24位的就是线上票
                HttpUtils.showProgressDialog(progressDialog);
                Log.e("门票串码---2", barcode + "");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        GetOByEcode(barcode, "");
                        if ("已核销".equals(ticketState.getText().toString())) {
                            try {
                                if (mTimer != null) {
                                    mTimer.cancel();// 退出之前的mTimer
                                }
                                timerTask();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if ("核销失败".equals(ticketState.getText().toString())) {
                            try {
                                if (mTimer != null) {
                                    mTimer.cancel();// 退出之前的mTimer
                                }
                                timerTask();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        }
    }


    public void DeviceN900() {
        //打开nfc设备
        nfc = new Nfc(this);
        try {
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
                    handler.sendMessage(handler.obtainMessage(SHOW_NFC_DATA, nfcData));
                } else {
                    Log.d("GetNFC----------->", "Check MagneticCard timeout...");
                    handler.sendMessage(handler.obtainMessage(CHECK_NFC_TIMEOUT, null));
                }
            } catch (TelpoException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        openActivityAndCloseThis(MainActivity.class);
        return super.onKeyDown(keyCode, event);
    }
}
