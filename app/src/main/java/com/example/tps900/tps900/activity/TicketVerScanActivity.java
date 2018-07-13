package com.example.tps900.tps900.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.example.tps900.tps900.Bean.TicketScanBean;
import com.example.tps900.tps900.R;
import com.example.tps900.tps900.Utlis.MessageConstanse;
import com.example.tps900.tps900.Utlis.MessageEvent_Food;
import com.example.tps900.tps900.Utlis.ThreadPoolUtils;
import com.example.tps900.tps900.Utlis.TpsN900PrintUtils;
import com.example.tps900.tps900.Webservice.GsWebServiceUtils;
import com.example.tps900.tps900.adapters.TicketScanAdapter;
import com.example.tps900.tps900.sql.Sqls;
import com.godfery.Sqlite.DBUtils;
import com.godfery.Utils.ToastUtils;
import com.godfery.keyboard.KeyboardUtil;
import com.godfery.pay.HttpUtils;
import com.godfery.Zxing.CaptureActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import static com.example.tps900.tps900.Utlis.Constant.context;
import static com.example.tps900.tps900.Utlis.Dailog.ErrDialog2;
import static com.example.tps900.tps900.Utlis.OtherUtils.sendMessageInfo;
import static com.example.tps900.tps900.Webservice.GsWebServiceUtils.ConsumeTickets;
import static com.example.tps900.tps900.Webservice.GsWebServiceUtils.GetOverifByEcode;
import static com.example.tps900.tps900.sql.Sqls.deleteWriteOffInfo;
import static com.example.tps900.tps900.sql.Sqls.updataWriteOffInfo;
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

public class TicketVerScanActivity extends BaseActivity {
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
    //打开进度条
    private final int OpenProgressBar = 6;
    //关闭进度条
    private final int CloseProgressBar = 7;
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
    LinearLayout ticketBtnBor;
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
                    Intent intent = new Intent(TicketVerScanActivity.this, CaptureActivity.class);
                    startActivityForResult(intent, 100);
                    break;
                //线下核销
                case Offlineverification:
                    Ticket_H(barcode2, Integer.valueOf(personNum));
                    break;
                //线上核销
                case Onlineverification:
                    ConTickets(barcode, practicalPersonNum.getText().toString().trim());
                    break;
                //通用失败原因
                case Failed:
                    HttpUtils.exitProgressDialog(progressDialog);
                    String msgInfo = msg.obj.toString();
                    ErrDialog2(TicketVerScanActivity.this, msgInfo, "");
                    List<TicketScanBean> ticketScanBeenList = Sqls.queryWriteOffInfo("select * from WriteOffInfo where Ticketstatus ='" + 0 + "'");
                    parkNamesList.clear();
                    WriteOff(ticketScanBeenList);
                    break;
                case Emptied://清除数据
                    if (parkNamesList.size() == 0) {
                        parkNamesListNum = 0;
                    }
                    break;
                case OpenProgressBar:
                    HttpUtils.showProgressDialog(progressDialog);
                    break;
                case CloseProgressBar:
                    HttpUtils.exitProgressDialog(progressDialog);
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
    private RecyclerView mRecyclerView;

    private TicketScanAdapter ticketScanAdapter;
    private int scanNum;
    private int parkNamesListNum;
    private TicketScanBean ticketscanBean;
    //子list
    private List<TicketScanBean.TicketBean> ticketlist;
    //外层list
    private List<TicketScanBean> parkNamesList;
    private List<TicketScanBean> newPintList;
    private Button ticketClearTicket;
    private int printPage = 0;
    private List<TicketScanBean> oldPrintList;

    /**
     * 设置布局
     *
     * @return
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_ticket_verscan;
    }

    /**
     * @author zxh
     * created at 2017/6/7 15:56
     * 方法名: initView
     * 方法说明: 初始化操作
     */
    @Override
    public void initView() {
        oldPrintList = new ArrayList<>();
        newPintList = new ArrayList<>();
        EventBus.getDefault().register(this);
        parkNamesList = new ArrayList<>();
        ticketlist = new ArrayList<>();
        String sql = "delete from Ecode";
        String sql_1 = "delete from WriteOffInfo";
        DBUtils.execSQL(sql_1);
        DBUtils.execSQL(sql);
        ticketScanAdapter = new TicketScanAdapter(R.layout.android_data_item, parkNamesList, TicketVerScanActivity.this, TicketVerScanActivity.this);
        List<TicketScanBean> ticketScanBeen = Sqls.queryWriteOffInfo("select * from WriteOffInfo");
        WriteOff(ticketScanBeen);

        ticketConfirm = (RadioButton) findViewById(R.id.ticket_confirm);
        ticketCancel = (RadioButton) findViewById(R.id.ticket_cancel);
        ticketImgDelete = (ImageView) findViewById(R.id.ticket_img_delete);
        ticketEtTicketCode = (EditText) findViewById(R.id.ticket_et_ticketCode);
        ticketImgDeleteCode = (ImageView) findViewById(R.id.ticket_img_deleteCode);
        ticketQueryTicket = (Button) findViewById(R.id.ticket_QueryTicket);
        ticketClearTicket = (Button) findViewById(R.id.ticket_clearTicket);
        ticketBtnBor = (LinearLayout) findViewById(R.id.ticket_btn_bor);
        ticketBtnComit = (Button) findViewById(R.id.ticket_btn_comit);
        ticketName = (TextView) findViewById(R.id.ticket_name);
        ticketLvBacket = (LinearLayout) findViewById(R.id.ticket_lv_back);
        mRecyclerView = (RecyclerView) findViewById(R.id.activty_verScanrlv);
        progressDialog = new ProgressDialog(TicketVerScanActivity.this);
        //显示布局风格
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(ticketScanAdapter);

    }

    /**
     * @author zxh
     * created at 2017/6/7 16:15
     * 方法名: initData
     * 方法说明: 一些数据操作
     */
    @Override
    public void initData() {
        EditListener(ticketEtTicketCode);
        ticketEtTicketCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                ticketEtTicketCode.setHint("");
                new KeyboardUtil(getApplicationContext(), TicketVerScanActivity.this, ticketEtTicketCode, R.id.schemas_key_keyboard_view).showKeyboard();

            }
        });
        ticketEtTicketCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    ticketImgDeleteCode.setVisibility(View.GONE);
                } else {
                    ticketImgDeleteCode.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.ticket_et_ticketCode) {
//            closeSoftKeyBoard(TicketVerScanActivity.this);
            new KeyboardUtil(getApplicationContext(), TicketVerScanActivity.this, ticketEtTicketCode, R.id.schemas_key_keyboard_view).showKeyboard();
        } else if (id == R.id.ticket_img_deleteCode) {
            ticketEtTicketCode.setText("");
        } else if (id == R.id.ticket_QueryTicket) {
            String ticketCode = ticketEtTicketCode.getText().toString().trim();
            Log.e("门票串码---1", ticketCode + "");
            if (TextUtils.isEmpty(ticketCode)) {
                ToastUtils.show(this, "请输入门票串码");
            } else {
                barcode = ticketCode;
                ThreadPoolUtils.runTaskInThread(new Runnable() {
                    @Override
                    public void run() {
                        btn_QueryticketInfo(barcode);
                    }
                });
            }
        } else if (id == R.id.ticket_btn_bor) {
            sendMessageInfo(handler, Scanning);
//            ThreadPoolUtils.runTaskInThread(new Runnable() {
//                @Override
//                public void run() {
//                    Intent intent = new Intent(TicketVerScanActivity.this, CaptureActivity.class);
//                    startActivityForResult(intent, 100);
//                }
//            });
        } else if (id == R.id.ticket_btn_comit) {
            ThreadPoolUtils.runTaskInThread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < parkNamesList.size(); i++) {
                        List<TicketScanBean.TicketBean> ticketdata = parkNamesList.get(i).getTicketdata();
                        for (int j = 0; j < ticketdata.size(); j++) {
                            if (!TextUtils.isEmpty(ticketdata.get(j).getReallyPeople())) {
                                int rally = Integer.parseInt(ticketdata.get(j).getReallyPeople());
                                int sdpeople = Integer.parseInt(ticketdata.get(j).getShouldPeople());
                                if (rally <= sdpeople) {
                                    btn_comit(ticketdata.get(j).getTicketCode(), Integer.parseInt(ticketdata.get(j).getReallyPeople()));
                                    try {
                                        Thread.sleep(100);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ToastUtils.show(context, "请输入正确人数!");
                                        }
                                    });
                                }

                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastUtils.show(context, "请输入实到人数!");
                                    }
                                });
                            }

                        }
                    }
                    Log.d("TicketVerScanActivity", "打印中" + oldPrintList.size());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            HttpUtils.exitProgressDialog(progressDialog);
                            if (oldPrintList.size() > 0) {
                                Log.d("TicketVerScanActivity", "已打印完成" + oldPrintList.size());
                                for (int i = 0; i < oldPrintList.size(); i++) {
                                    String name = oldPrintList.get(i).getTicketName();
                                    for (int j = 0; j < oldPrintList.get(i).getTicketdata().size(); j++) {
                                        TicketScanBean.TicketBean ticketBean = oldPrintList.get(i).getTicketdata().get(j);
                                        PintList(name, ticketBean);
                                    }

                                }
                                TpsN900PrintUtils.TpsN900Print_TicketAll(TicketVerScanActivity.this, newPintList);
                                parkNamesList.clear();
                                ticketEtTicketCode.setText("");
                                oldPrintList.clear();
                                newPintList.clear();
                                List<TicketScanBean> ticketScanBeen = Sqls.queryWriteOffInfo("select * from WriteOffInfo");
                                WriteOff(ticketScanBeen);
                                ticketScanAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
            });

        } else if (id == R.id.ticket_lv_back) {
            if (parkNamesList.size() > 0) {
                ErrDialog2(TicketVerScanActivity.this, "返回将清空当前所有数据", MessageConstanse.WRITE_OFF_CLEARING);
            } else {
                TicketVerScanActivity.this.startActivity(new Intent(this, MainActivity.class));
                finish();
            }

        } else if (id == R.id.ticket_clearTicket) {
            if (parkNamesList.size() > 0) {
                ErrDialog2(TicketVerScanActivity.this, "清空将删除所有数据", MessageConstanse.DELETE_ALL_TICKET_INFO);
            }
        }
    }

    /**
     * @author zxh
     * created at 2017/6/7 15:54
     * 方法名: Ticket_Verifi 线下门票查询
     * 方法说明: 传入二维码  查看当前票是否有效
     */
    public void Ticket_Verifi(final String barcode) {
        String GetTicketCheck = GsWebServiceUtils.GetTicketCheck(barcode, "TICKET");
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
                        //每次成功一条要将二维码从二维码表中删除
                        Sqls.deleteEcode(barcode);
                        //可入人数为1并且为有效票
                        if (getTicketCode.INPARKTIME >= 1) {
                            queryTicketInfoSort("",
                                    getTicketCode.getTICKETNAME(),
                                    getTicketCode.getTICKETPRICE(),
                                    getTicketCode.getBARCODE(),
                                    getTicketCode.getSTARTDATETIME(),
                                    getTicketCode.getENDDATETIME(),
                                    String.valueOf(getTicketCode.getINPARKTIME())
                            );
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ErrDialog2(TicketVerScanActivity.this, getTicketCode.SHOWMSG, "");
                                }
                            });
                        }
                    }
                });
            } else {
                Sqls.deleteEcode(barcode);
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
        String ticket_H = GsWebServiceUtils.GetUpdateTicket(barcode, count, "TICKET");
        Log.e("ticket_H门票信息", ticket_H);
        if (ticket_H.equals("err")) {
            sendMessageInfo(handler, Failed, "请求数据失败,请检查网络或者检查配置");
        } else {
            getUpadateTicketBean = JSON.parseObject(ticket_H, GetUpadateTicketBean.class);
            if (getUpadateTicketBean.FLAG) {
                Log.d("TicketVerScanActivity", "核销成功");
                updataWriteOffInfo("修改状态", barcode, "");
                updataWriteOffInfo("修改人数", barcode, String.valueOf(count));
                List<TicketScanBean> ticketScanBeenList = Sqls.queryWriteOffInfo("select * from WriteOffInfo where TicketEcode ='" + barcode + "'");
                deleteWriteOffInfo(barcode);
//                List<TicketScanBean.TicketBean> ticketdata = ticketScanBeenList.get(0).getTicketdata();
//                TicketScanBean.TicketBean ticketbean = new TicketScanBean.TicketBean();
//                ticketbean.setTicketPrice(ticketdata.get(0).getTicketPrice());
//                ticketbean.setTicketStartTime(ticketdata.get(0).getTicketStartTime());
//                ticketbean.setTicketEndTime(ticketdata.get(0).getTicketEndTime());
//                ticketbean.setReallyPeople(String.valueOf(count));
//                ticketbean.setShouldPeople(String.valueOf(count));
//                ticketbean.setStatus(1);
//                ticketbean.setTicketCode(barcode);
//                //临时外部bean
//                TicketScanBean outerLayerBean = new TicketScanBean();
//                outerLayerBean.setTicketName(ticketScanBeenList.get);
//                outerLayerBean.setTicketdata(sqlticketlist);
//                sqlParkBeanList.add(outerLayerBean);
                oldPrintList.add(ticketScanBeenList.get(0));
//                ThreadPoolUtils.runTaskInUIThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        for (int i = 0; i < ticketScanBeenList.size(); i++) {
//                            String name = ticketScanBeenList.get(i).getTicketName();
//                            for (int j = 0; j < ticketScanBeenList.get(i).getTicketdata().size(); j++) {
//                                TicketScanBean.TicketBean ticketBean = ticketScanBeenList.get(i).getTicketdata().get(j);
//                                TpsN900PrintUtils.TpsN900Print_ticket(TicketVerScanActivity.this,
//                                        "\n门票名称：" + name +
//                                                "\n门票价格：" + ticketBean.getTicketPrice() + "元" +
//                                                "\n入园开始日期：" + ticketBean.getTicketStartTime() +
//                                                "\n入园结束日期：" + ticketBean.getTicketEndTime() +
//                                                "\n门票核销日期：" + getTime() +
//                                                "\n可入园次数：" + ticketBean.getShouldPeople() + "次"
//                                                + "\n实际入园次数：" + ticketBean.getReallyPeople()
//                                                + "\n门票二维码："
//                                        , ticketBean.getTicketCode(), "温馨提示:此票只作为核销凭证,不可视为报销凭证");
//                                List<TicketScanBean> ticketScanBeenList = Sqls.queryWriteOffInfo("select * from WriteOffInfo where Ticketstatus ='" + 0 + "'");
//                                parkNamesList.clear();
//                                WriteOff(ticketScanBeenList);
//                                ticketScanAdapter.notifyDataSetChanged();
//                            }
//                        }
//
//
//                    }
//
//                });

            } else {
                //核销失败
                sendMessageInfo(handler, Failed, "核销失败" + getUpadateTicketBean.MSG + "\n请检查网络或者检查配置");
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
        String f00001409 = GetOverifByEcode(Ecode, OrderNo);
        HttpUtils.exitProgressDialog(progressDialog);
        if (TextUtils.isEmpty(f00001409) || f00001409.equals("err")) {
            sendMessageInfo(handler, Failed, "请求数据失败,请检查网络或者检查配置");
        } else if (!TextUtils.isEmpty(f00001409) && !f00001409.equals("err")) {
            getobe = JSON.parseObject(f00001409, GetOverifByEcodeBean.class);
            if (getobe.Success && getobe.Data != null) {
                //每次成功一条要将二维码从二维码表中删除
                Sqls.deleteEcode(Ecode);
                queryTicketInfoSort("",
                        getobe.Data.ProductName,
                        getobe.Data.PrintPrice,
                        getobe.Data.Ecode,
                        getobe.Data.PlayStartDate,
                        getobe.Data.PlayEndDate,
                        getobe.Data.ProductCount
                );
            } else {
                Sqls.deleteEcode(barcode);
                //线上查询失败
                sendMessageInfo(handler, Failed, getobe.Message + "\n查询票信息失败");
            }
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
            //核销结果
            String consumeTickets = ConsumeTickets(ECode, VerifNum);
            if (!TextUtils.isEmpty(consumeTickets)) {
                if (consumeTickets.equals("err")) {
                    //网络原因或者接口中断
                    sendMessageInfo(handler, Failed, "请求数据失败,请检查网络或者检查配置");
                } else {
                    ticketsBean = JSON.parseObject(consumeTickets, ConsumeTicketsBean.class);
                    if (ticketsBean.Success) {
                        //将核销表中的状态改为已核销
                        updataWriteOffInfo("修改状态", ECode, "");
                        //根据二维码查询表中的已核销数据并记录于打印List中
                        List<TicketScanBean> ticketScanBeenList = Sqls.queryWriteOffInfo("select * from WriteOffInfo where TicketEcode ='" + ECode + "'");
                        oldPrintList.add(ticketScanBeenList.get(0));
                        //根据二维码删除表中数据
                        deleteWriteOffInfo(ECode);
                    } else {
                        //核销失败提示信息
                        sendMessageInfo(handler, Failed, ticketsBean.Message + "\n核销失败");
                    }
                }
            }
        } catch (Exception e) {

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
            if (!TextUtils.isEmpty(barcode) && barcode.length() == 12 || barcode.length() == 24) {
                Sqls.InsertEcodeTablet(barcode);
                ThreadPoolUtils.runTaskInThread(new Runnable() {
                    @Override
                    public void run() {
                        btn_QueryticketInfo(barcode);
                    }
                });
            } else {
                ToastUtils.show(TicketVerScanActivity.this, "请扫描正确串码");
            }
            return;
        }
        ToastUtils.show(TicketVerScanActivity.this, "扫描结果为空");
    }

    //提交数据
    public void btn_comit(final String Ecode, final int Num) {
        if (TextUtils.isEmpty(Ecode)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.show(TicketVerScanActivity.this, "请先扫描门票");
                }
            });
            return;
        } else {
            if (Num == 0) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        ToastUtils.show(TicketVerScanActivity.this, "请输入入园次数");
//                    }
//                });
            } else {
                if (Ecode.length() > 12) {
//                    updataWriteOffInfo("修改状态", Ecode, String.valueOf(Num));
                    sendMessageInfo(handler, OpenProgressBar);
                    Ticket_H(Ecode, Num);
                } else if (Ecode.length() <= 12 && Ecode.length() > 0) {
//                    updataWriteOffInfo("修改状态", Ecode, String.valueOf(Num));
                    sendMessageInfo(handler, OpenProgressBar);
                    ConTickets(Ecode, String.valueOf(Num));
                }
            }
        }
    }

    /**
     * 根据串码判断是线上票还是线下票
     * 小于24位的就是线上票
     * 大于24位的就是线下票
     *
     * @param barcode
     */
    public void btn_QueryticketInfo(final String barcode) {
        if (!TextUtils.isEmpty(barcode)) {
            if (barcode.length() >= 24) {
                sendMessageInfo(handler, OpenProgressBar);
                Ticket_Verifi(barcode);
            } else if (barcode.length() < 24 && barcode.length() > 0) {
                sendMessageInfo(handler, OpenProgressBar);
                GetOByEcode(barcode, "");
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return false;

    }

    /**
     * 将查询到的门票信息分类并加载核销日志表中
     *
     * @param ticketName           门票名称
     * @param ticketPrice          门票价格
     * @param ecode                门票编码
     * @param startDateTime        开始日期
     * @param endDateTime          结束日期
     * @param NumberOfVerification 可核销数量
     */
    public void queryTicketInfoSort(String Type, String ticketName, String ticketPrice, String ecode, String startDateTime, String endDateTime, String NumberOfVerification) {
        //存入SQL中的外部list
        List<TicketScanBean> sqlParkBeanList = new ArrayList<>();
        //存入SQL中的内部list
        List<TicketScanBean.TicketBean> sqlticketlist = new ArrayList<>();
        //临时内部bean
        TicketScanBean.TicketBean ticketbean = new TicketScanBean.TicketBean();
        ticketbean.setTicketPrice(ticketPrice);
        ticketbean.setTicketStartTime(startDateTime);
        ticketbean.setTicketEndTime(endDateTime);
        ticketbean.setReallyPeople(NumberOfVerification);
        ticketbean.setShouldPeople(NumberOfVerification);
        ticketbean.setStatus(0);
        ticketbean.setTicketCode(ecode);
        sqlticketlist.add(ticketbean);
        //临时外部bean
        TicketScanBean outerLayerBean = new TicketScanBean();
        outerLayerBean.setTicketName(ticketName);
        outerLayerBean.setTicketdata(sqlticketlist);
        sqlParkBeanList.add(outerLayerBean);
        /**
         * //TODO 有任何新增标记则添加于数据库中
         * 判断外部list的大小是否为0,如果是则新增一条外部bean和内部bean
         * 如果外部list的大小大于0,则判断门票名称是否相等,如果相等则判断字list是否一样如果不一样则加反之不加
         * 如果外部list的大小大于0且名称不一样则新增一条记录
         */
        if (parkNamesList.size() > 0) {
            boolean parklistFlag = false;
            int ticket = -1;
            for (int i = 0; i < parkNamesList.size(); i++) {
                if (outerLayerBean.getTicketName().equals(parkNamesList.get(i).getTicketName())) {
                    parklistFlag = true;
                    ticket = i;
                }
            }
            if (!parklistFlag) {
                ticketscanBean = new TicketScanBean();
                ticketscanBean.setTicketName(ticketName);
                ticketscanBean.setTicketdata(sqlticketlist);
                parkNamesList.add(ticketscanBean);
                if (Type.equals("")) {
                    Sqls.InsertWriteOffInfo(sqlParkBeanList);
                }
            } else {
                boolean flag = false;
                List<TicketScanBean.TicketBean> ticketdata = parkNamesList.get(ticket).getTicketdata();
                for (int j = 0; j < ticketdata.size(); j++) {
                    if (ticketbean.getTicketCode().equals(ticketdata.get(j).getTicketCode())) {
                        flag = true;
                    }
                }
                if (!flag) {
                    parkNamesList.get(ticket).getTicketdata().add(ticketbean);
                    if (Type.equals("")) {
                        Sqls.InsertWriteOffInfo(sqlParkBeanList);
                    }
                }
            }
        } else {
            ticketscanBean = new TicketScanBean();
            ticketscanBean.setTicketName(ticketName);
            ticketscanBean.setTicketdata(sqlticketlist);
            parkNamesList.add(ticketscanBean);
            if (Type.equals("")) {
                Sqls.InsertWriteOffInfo(sqlParkBeanList);
            }
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //刷新适配器
                ticketScanAdapter.notifyDataSetChanged();
            }
        });
    }

    public void WriteOff(List<TicketScanBean> ticketScanBeenList) {
        for (int i = 0; i < ticketScanBeenList.size(); i++) {
            TicketScanBean ticketscanBean = ticketScanBeenList.get(i);
            String name = ticketscanBean.getTicketName();
            List<TicketScanBean.TicketBean> ticketdata = ticketscanBean.getTicketdata();
            for (int j = 0; j < ticketdata.size(); j++) {
                TicketScanBean.TicketBean ticketBean = ticketdata.get(j);
                queryTicketInfoSort("1", name, ticketBean.getTicketPrice(), ticketBean.getTicketCode(), ticketBean.getTicketStartTime(), ticketBean.getTicketEndTime(), ticketBean.getShouldPeople());
            }

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent_Food messageEvent) {
        String messageEventMsg = messageEvent.getMsg();
        if (messageEventMsg.equals(MessageConstanse.WRITE_OFF_CLEARING)) {
            TicketVerScanActivity.this.startActivity(new Intent(this, MainActivity.class));
            finish();
        } else if (messageEventMsg.equals(MessageConstanse.DELETE_TICKET_INFO)) {
            parkNamesList.clear();
            List<TicketScanBean> ticketScanBeen = Sqls.queryWriteOffInfo("select * from WriteOffInfo");
            WriteOff(ticketScanBeen);
            ticketScanAdapter.notifyDataSetChanged();
        } else if (messageEventMsg.equals(MessageConstanse.DELETE_ALL_TICKET_INFO)) {
            parkNamesList.clear();
            ticketEtTicketCode.setText("");
            ticketScanAdapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    public void PintList(String ticketName, TicketScanBean.TicketBean ticketBean) {
        List<TicketScanBean.TicketBean> sqlticketlist = new ArrayList<>();
        sqlticketlist.add(ticketBean);
        //临时外部bean
        TicketScanBean outerLayerBean = new TicketScanBean();
        outerLayerBean.setTicketName(ticketName);
        outerLayerBean.setTicketdata(sqlticketlist);
        if (newPintList.size() > 0) {
            boolean parklistFlag = false;
            int ticket = -1;
            for (int i = 0; i < newPintList.size(); i++) {
                if (outerLayerBean.getTicketName().equals(newPintList.get(i).getTicketName())) {
                    parklistFlag = true;
                    ticket = i;
                }
            }
            if (!parklistFlag) {
                newPintList.add(outerLayerBean);
            } else {
                boolean flag = false;
                List<TicketScanBean.TicketBean> ticketdata = newPintList.get(ticket).getTicketdata();
                for (int j = 0; j < ticketdata.size(); j++) {
                    if (ticketBean.getTicketCode().equals(ticketdata.get(j).getTicketCode())) {
                        flag = true;
                    }
                }
                if (!flag) {
                    newPintList.get(ticket).getTicketdata().add(ticketBean);
                }
            }
        } else {
            newPintList.add(outerLayerBean);
        }
    }


}
