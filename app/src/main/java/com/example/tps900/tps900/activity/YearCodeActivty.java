package com.example.tps900.tps900.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.tps900.tps900.Bean.GetTicketCode;
import com.example.tps900.tps900.Bean.GetUpadateTicketBean;
import com.example.tps900.tps900.R;
import com.example.tps900.tps900.Utlis.GuiNumberKeyBoard2;
import com.example.tps900.tps900.Utlis.OtherUtils;
import com.example.tps900.tps900.Utlis.ToastUtils;
import com.example.tps900.tps900.Webservice.GsWebServiceUtils;
import com.example.tps900.tps900.image.ScaleView;
import com.godfery.pay.HttpUtils;

import java.util.Timer;
import java.util.TimerTask;

import static com.example.tps900.tps900.Utlis.Dailog.NormalDialogStyleOne;
import static com.example.tps900.tps900.Utlis.OtherUtils.sendMessageInfo;

/**
 * 项目名称：TPS900
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2017/4/27 9:45
 * 修改人：zxh
 * 修改时间：2017/4/27 9:45
 * 修改备注：
 */

public class YearCodeActivty extends BaseActivity implements View.OnFocusChangeListener, View.OnTouchListener, BaseActivity.CardCodeInterface {

    TextView TTicketName;

    TextView TState;

    EditText ECode;

    Button BQuery;

    TextView TStartTime;

    TextView TEndTime;

    TextView TTicketPersonNum;

    EditText EPracticalPersonNum;

    ImageView ImgTicketImgDelete;

    ImageView ImgCodeImgDelete;

    ScaleView yearImage;
    private Timer mTimer;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 3://异常
                    HttpUtils.exitProgressDialog(progressDialog);
                    NormalDialogStyleOne(YearCodeActivty.this, "核销异常", "请检查网络或配置或重试 ", "");
                    break;
                case 4:
                    HttpUtils.showProgressDialog(progressDialog);
                    Ticket_Verifi(ECode.getText().toString());

                    if ("已核销".equals(TState.getText().toString())) {
                        HttpUtils.exitProgressDialog(progressDialog);
                        exeitTimer();
                        timerTask();
                    } else if ("核销失败".equals(TState.getText().toString())) {
                        HttpUtils.exitProgressDialog(progressDialog);
                        exeitTimer();
                        timerTask();
                    }
                    break;
                case 5:
                    HttpUtils.exitProgressDialog(progressDialog);
                    NormalDialogStyleOne(YearCodeActivty.this, "查询门票信息异常\n" + getTicketCode.SHOWMSG, "", "");
                    break;
                case 6:
                    HttpUtils.exitProgressDialog(progressDialog);
                    TState.setText("核销失败");
                    if ("核销失败".equals(TState.getText().toString())) {
                        exeitTimer();
                        timerTask();
                    }
                    NormalDialogStyleOne(YearCodeActivty.this, "核销异常" + getUpadateTicketBean.MSG, "", "");
                    break;
                case 7:
                    HttpUtils.exitProgressDialog(progressDialog);
                    TTicketName.setText("");
                    TStartTime.setText("");
                    TEndTime.setText("");
                    TTicketPersonNum.setText("");
                    EPracticalPersonNum.setText("");
                    ECode.setText("");
                    TState.setText("");
                    Drawable drawable = getResources().getDrawable(R.mipmap.touxiangyonghu_03);
                    Bitmap bitmap = OtherUtils.drawableToBitmap(drawable);
                    Drawable drawable2 = new BitmapDrawable(bitmap);
                    Log.e("draw", drawable2 + "");
                    yearImage.setBackground(drawable2);
                    break;
                case 8:
                    Ticket_H(ECode.getText().toString(), Integer.valueOf(EPracticalPersonNum.getText().toString()));
                    if ("已核销".equals(TState.getText().toString())) {
                        HttpUtils.exitProgressDialog(progressDialog);
                        exeitTimer();
                        timerTask();
                    } else if ("核销失败".equals(TState.getText().toString())) {
                        HttpUtils.exitProgressDialog(progressDialog);
                        exeitTimer();
                        timerTask();
                    }
                    break;
                case 10:
                    HttpUtils.exitProgressDialog(progressDialog);
                    getTicketName = getTicketCode.getTICKETNAME();
                    startDateTime = getTicketCode.getSTARTDATETIME();
                    endDateTime = getTicketCode.getENDDATETIME();
                    TTicketName.setText(getTicketName);
                    TStartTime.setText(startDateTime);
                    TEndTime.setText(endDateTime);

                    TTicketPersonNum.setText(getTicketCode.getINPARKTIME() + "");
                    EPracticalPersonNum.setText(getTicketCode.getINPARKTIME() + "");
                    //如果是一人的话就进行核销
                    inParkTime = EPracticalPersonNum.getText().toString();
                    if (!TextUtils.isEmpty(inParkTime)) {
                        if ("1".equals(EPracticalPersonNum.getText().toString())) {
                            Ticket_H(getTicketCode.BARCODE, Integer.valueOf(EPracticalPersonNum.getText().toString()));
                        } else {
                            TState.setText("未核销");
                            TState.setTextColor(Color.BLACK);
                        }
                    }
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
                    break;
                case 11:
                    HttpUtils.exitProgressDialog(progressDialog);
                    TTicketName.setText(getTicketName);
                    TState.setText("已核销");
                    TState.setTextColor(Color.RED);
                    TStartTime.setText(startDateTime);
                    TEndTime.setText(endDateTime);
                    TTicketPersonNum.setText(inParkTime);
                    EPracticalPersonNum.setText(inParkTime);
                    if ("已核销".equals(TState.getText().toString())) {
                        exeitTimer();
                        timerTask();
                    } else if ("核销失败".equals(TState.getText().toString())) {
                        exeitTimer();
                        timerTask();
                    }
                    break;
                default:
            }
        }
    };
    public GuiNumberKeyBoard2 keyboard;
    private int sdkInt;
    private GetTicketCode getTicketCode;
    private String getTicketName;
    private String startDateTime;
    private String endDateTime;
    private String inParkTime;
    private GetUpadateTicketBean getUpadateTicketBean;
    private ProBroadcastReceiver pbd;


    /**
     * 设置布局
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_yearcode_verification;
    }

    @Override
    public void initData() {
        keyboard = new GuiNumberKeyBoard2(YearCodeActivty.this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("Intent");
        intentFilter.addAction("WebService");
        intentFilter.addAction("Code");
        if (pbd == null) {
            pbd = new ProBroadcastReceiver();
        }
        this.registerReceiver(pbd, intentFilter);
        Drawable drawable = getResources().getDrawable(R.mipmap.touxiangyonghu_03);
        Bitmap bitmap = OtherUtils.drawableToBitmap(drawable);
        Drawable drawable2 = new BitmapDrawable(bitmap);
        yearImage.setBackground(drawable2);
    }

    @Override
    public void initView() {
        TTicketName = (TextView) findViewById(R.id.T_ticket_name);
        TState = (TextView) findViewById(R.id.T_State);
        ECode = (EditText) findViewById(R.id.E_code);
        BQuery = (Button) findViewById(R.id.B_Query);
        TStartTime = (TextView) findViewById(R.id.T_start_time);
        TEndTime = (TextView) findViewById(R.id.T_end_time);
        TTicketPersonNum = (TextView) findViewById(R.id.T_ticket_personNum);
        EPracticalPersonNum = (EditText) findViewById(R.id.E_practical_personNum);
        ImgTicketImgDelete = (ImageView) findViewById(R.id.Img_ticket_img_delete);
        ImgCodeImgDelete = (ImageView) findViewById(R.id.Img_code_img_delete);
        yearImage = (ScaleView) findViewById(R.id.year_image);
        ECode.setOnFocusChangeListener(this);
        ECode.setOnTouchListener(this);
        EPracticalPersonNum.setOnFocusChangeListener(this);
        EPracticalPersonNum.setOnTouchListener(this);
        addTextChangedListener(ECode);
        addTextChangedListener(EPracticalPersonNum);
        setCardInterface(this);
        sdkInt = Build.VERSION.SDK_INT;
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.E_code) {
            keyboard = new GuiNumberKeyBoard2(YearCodeActivty.this, ECode);
        } else if (id == R.id.B_Query) {
            if ("0".equals(ECode.getText().toString()) || TextUtils.isEmpty(ECode.getText().toString())) {
                ToastUtils.show(YearCodeActivty.this, "请输入正确卡号");
            } else {
                sendMessageInfo(handler, 4);
            }
        } else if (id == R.id.E_practical_personNum) {
            keyboard = new GuiNumberKeyBoard2(YearCodeActivty.this, EPracticalPersonNum);

        } else if (id == R.id.Img_ticket_img_delete) {
            EPracticalPersonNum.setText("");
        } else if (id == R.id.Img_code_img_delete) {
            ECode.setText("");
        }
    }


    //查询
    public void Ticket_Verifi(final String Code) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String GetTicketCheck = GsWebServiceUtils.GetTicketCheck(Code, "MEMBER");
                Log.e("GetTicketCheck门票信息", GetTicketCheck);
                if ("err".equals(GetTicketCheck)) {
                    sendMessageInfo(handler, 3);
                } else {
                    getTicketCode = JSON.parseObject(GetTicketCheck, GetTicketCode.class);
                    if (getTicketCode.FLAG&&getTicketCode.TICKETNAME!=null) {
                        sendMessageInfo(handler, 10);
                    } else {
                        sendMessageInfo(handler, 5);
                    }
                }

            }
        }).start();
    }

    //核销
    public void Ticket_H(final String barcode, final int count) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String ticket_H = GsWebServiceUtils.GetUpdateTicket(barcode, count, "MEMBER");
                Log.e("ticket_H门票信息", ticket_H);
                if ("err".equals(ticket_H)) {
                    sendMessageInfo(handler, 3);
                } else {
                    getUpadateTicketBean = JSON.parseObject(ticket_H, GetUpadateTicketBean.class);
                    if (getUpadateTicketBean.FLAG ) {
                        sendMessageInfo(handler, 11);
                    } else {
                        sendMessageInfo(handler, 6);
                    }
                }

            }
        }).start();
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
                if (id == R.id.E_code) {
                    keyboard = new GuiNumberKeyBoard2(YearCodeActivty.this, ECode);
                } else if (id == R.id.E_practical_personNum) {
                    keyboard = new GuiNumberKeyBoard2(YearCodeActivty.this, EPracticalPersonNum);
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
        GuiNumberKeyBoard2.getCursorIndex(et, inType, v, sdkInt);
        int id = v.getId();
        if (id == R.id.E_code) {
            ECode.onTouchEvent(event);
            ECode.setInputType(inType);
        } else if (id == R.id.E_practical_personNum) {
            EPracticalPersonNum.onTouchEvent(event);
            EPracticalPersonNum.setInputType(inType);
        }
        return true;
    }//隐藏系统键盘

    @Override
    public void onReadCardOk(String cardNo) {
        ECode.setText(cardNo);
        sendMessageInfo(handler, 4);
    }


    public class ProBroadcastReceiver extends BroadcastReceiver {
        public ProBroadcastReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case "Code":
                    HttpUtils.showProgressDialog(progressDialog);
                    DeviceN900(context);
                    break;
                case "Intent":
                    openActivityAndCloseThis(MainActivity.class);
                    break;
                case "WebService"://访问接口
                    HttpUtils.showProgressDialog(progressDialog);
                    if ("".equals(TState.getText().toString().trim())) {
                        HttpUtils.exitProgressDialog(progressDialog);
                        ToastUtils.show(YearCodeActivty.this, "请先刷卡");
                        return;
                    } else if ("已核销".equals(TState.getText().toString().trim())) {
                        HttpUtils.exitProgressDialog(progressDialog);
                        ToastUtils.show(YearCodeActivty.this, "请先刷卡");
                        return;
                    } else {
                        if ("0".equals(EPracticalPersonNum.getText().toString().trim()) ||
                                "".equals(EPracticalPersonNum.getText().toString().trim()) ||
                                Integer.valueOf(EPracticalPersonNum.getText().toString().trim()) > Integer.valueOf(inParkTime)) {
                            HttpUtils.exitProgressDialog(progressDialog);
                            ToastUtils.show(YearCodeActivty.this, "请输入正确人数");
                        } else {
                            sendMessageInfo(handler, 8);
                        }
                    }
                    break;
                default:
                    break;

            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        YearCodeActivty.this.unregisterReceiver(pbd);
    }

    private void timerTask() {

        mTimer = new Timer();
        TimerTask timerTask01 = new TimerTask() {
            @Override
            public void run() {
                sendMessageInfo(handler, 7);
            }
        };
        mTimer.schedule(timerTask01, 3000);
    }

    public void exeitTimer() {
        if (mTimer != null) {
            mTimer.cancel();// 退出之前的mTimer
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        openActivityAndCloseThis(MainActivity.class);
        return super.onKeyDown(keyCode, event);
    }

}
