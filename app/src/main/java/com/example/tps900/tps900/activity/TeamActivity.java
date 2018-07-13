package com.example.tps900.tps900.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.tps900.tps900.Bean.ReAliPayInfoBean;
import com.example.tps900.tps900.Bean.ReturnWechatBean;
import com.example.tps900.tps900.Bean.TeamBean_1;
import com.example.tps900.tps900.R;
import com.example.tps900.tps900.Utlis.AliAndWePayMent;
import com.example.tps900.tps900.Utlis.Constant;
import com.example.tps900.tps900.Utlis.MatchUtils;
import com.example.tps900.tps900.Utlis.ThreadPoolUtils;
import com.example.tps900.tps900.Utlis.TpsN900PrintUtils;
import com.example.tps900.tps900.Webservice.GsWebServiceUtils;
import com.example.tps900.tps900.adapters.TeamAdapter;
import com.godfery.Utils.ToastUtils;
import com.godfery.pay.ErrUtils;
import com.godfery.pay.HttpUtils;
import com.godfery.Zxing.CaptureActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.example.tps900.tps900.Utlis.Dailog.DismissDialog;
import static com.example.tps900.tps900.Utlis.Dailog.ErrDialog2;
import static com.example.tps900.tps900.Utlis.OtherUtils.getTime;
import static com.example.tps900.tps900.Utlis.OtherUtils.sendMessageInfo;
import static com.example.tps900.tps900.Webservice.GsWebServiceUtils.result;
import static com.telpo.tps550.api.reader.ReaderMonitor.TAG;

/**
 * 项目名称：Galasys_PM
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2017/6/1 9:56
 * 修改人：zxh
 * 修改时间：2017/6/1 9:56
 * 修改备注：
 */

public class TeamActivity extends Activity {


    LinearLayout teamBack;

    EditText editIdTeam;

    Button btnConfirmTeam;

    ListView lvInfoTeam;

    RadioButton rbPayTeamOrder;

    RadioButton rbScanTeamOrder;

    RadioButton rbCancelTeamOrder;
    private View topView;
    private List<TeamBean_1.DataBean.TeamOrdersBean> teamBeanList_1;//团队teambeanlist
    private TeamBean_1.DataBean.TeamOrdersBean teamBean_1;
    private String qrcode;//扫描到的二维码
    private String PayCode;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Log.e("StringPayMent_return", PayMentData);
                    String type = msg.obj.toString();
                    HttpUtils.exitProgressDialog(progressDialog);
                    if (type.equals("退款")) {
                        if (PayMentData.length() > 50) {
                            money = "";
                            // TODO 去掉转义字符
                            PayMentData = PayMentData.replace("\\", "");
                            PayMentData = PayMentData.substring(2, PayMentData.length() - 2);
                            Log.e("退款返回字段", PayMentData);
                            if ("支付宝".equals(PayCode)) {
                                ReAliPayInfoBean alipay_entity = JSON.parseObject(PayMentData, ReAliPayInfoBean.class);
                                ReAliPayInfoBean.AlipayTradeQueryResponseBean queryResponseBean = alipay_entity.alipay_trade_query_response;
                                if ("Success".equals(queryResponseBean.getMsg()) && "10000".equals(queryResponseBean.getCode())) {
                                    ToastUtils.show(TeamActivity.this, "退款成功!");
                                } else {
                                    DismissDialog();
                                    ErrDialog2(TeamActivity.this, "支付宝退款失败\n" + queryResponseBean.getMsg(), "");
                                }

                            } else if ("微信".equals(PayCode)) {
                                ReturnWechatBean returnWechatBean=JSON.parseObject(PayMentData,ReturnWechatBean.class);
                                if ("SUCCESS".equals(returnWechatBean.getResult_code())
                                        && "SUCCESS".equals(returnWechatBean.getReturn_code())
                                        && "OK".equals(returnWechatBean.getReturn_msg())) {
                                    ToastUtils.show(TeamActivity.this, "退款成功!");
                                } else {
                                    DismissDialog();
                                    ErrDialog2(TeamActivity.this, "微信退款失败\n" + returnWechatBean.getReturn_msg(), "");
                                }

                            }

                        } else {
                            DismissDialog();
                            ErrDialog2(TeamActivity.this, "退款失败！！！", "");
                        }
                    }
                    break;
                case 1:
                    String orderNO = editIdTeam.getText().toString().trim();
                    if (!TextUtils.isEmpty(orderNO)) {
                        Query(orderNO);
                    } else {
                        ToastUtils.show(TeamActivity.this, "请输入正确的订单号");
                    }

                    break;
                case 2:
                    ErrDialog2(TeamActivity.this, "当前无网络\n点击确定设置网络", "1");
                    break;
                case 3:
                    break;
                default:
                    break;

            }
        }
    };
    private TextView orderNum_team;
    private TextView playTime_team;
    private TextView payType_team;
    private TextView shouldMoney_team;
    private TextView tv_ISPrint;
    private TextView tv_PayTyPE;
    private TextView tv_lxs_Team;
    private TeamAdapter teamAdapter1;
    private String payTime;
    private String money;
    private IntentBroadcastReceiver ibr;
    private ProgressDialog progressDialog;
    private String barcode;
    private String PayMentData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_team);

        //防止底部标题栏被顶到上部
        initView();
    }

    private void initView() {
        RefundPay("支付宝","2018042321001004740268938001","253214032","1");
        teamBack = (LinearLayout) findViewById(R.id.team_back);
        editIdTeam = (EditText) findViewById(R.id.edit_Id_Team);
        btnConfirmTeam = (Button) findViewById(R.id.btn_confirm_Team);
        lvInfoTeam = (ListView) findViewById(R.id.lv_info_team);
        rbPayTeamOrder = (RadioButton) findViewById(R.id.rb_pay_teamOrder);
        rbScanTeamOrder = (RadioButton) findViewById(R.id.rb_scan_teamOrder);
        rbCancelTeamOrder = (RadioButton) findViewById(R.id.rb_cancel_teamOrder);
        //ListView头布局各个控件
        topView = View.inflate(getApplicationContext(), R.layout.item_top_teamorder, null);
        //ListView头布局各个控件
        topView = View.inflate(getApplicationContext(), R.layout.item_top_teamorder, null);
        //订单号
        orderNum_team = (TextView) topView.findViewById(R.id.text_OrderNum_Team);
        //游玩日期
        playTime_team = (TextView) topView.findViewById(R.id.text_playTime_team);
        //支付类型
        payType_team = (TextView) topView.findViewById(R.id.text_PayType_Team);
        //应到人数
        tv_PayTyPE = (TextView) topView.findViewById(R.id.text_PayTYE_team);
        //旅行社
        tv_lxs_Team = (TextView) topView.findViewById(R.id.text_lxs_Team);
        //是否打印
        tv_ISPrint = (TextView) topView.findViewById(R.id.text_ISPrint_Team);
        //应付总金额
        shouldMoney_team = (TextView) topView.findViewById(R.id.text_ShouldMoney_Team);
        progressDialog = new ProgressDialog(this); // 获取数据显示dialog
        //注册广播 添加动作
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("updata");
        if (ibr == null) {
            ibr = new IntentBroadcastReceiver();
        }
        this.registerReceiver(ibr, intentFilter);
        lvInfoTeam.addHeaderView(topView);
        teamBeanList_1 = new ArrayList<>();
        teamAdapter1 = new TeamAdapter(teamBeanList_1, this);
        teamAdapter1.setTeamBean1(teamBeanList_1);
        lvInfoTeam.setAdapter(teamAdapter1);
    }

    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.team_back) {
            TeamActivity.this.startActivity(new Intent(this, MainActivity.class));
            finish();

        } else if (i == R.id.btn_confirm_Team) {
            if (!TextUtils.isEmpty(editIdTeam.getText().toString())) {
                final String orderNO = editIdTeam.getText().toString().trim();
                if (!TextUtils.isEmpty(orderNO)) {
                    HttpUtils.showProgressDialog(progressDialog);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Query(orderNO);
                        }
                    }).start();
                } else {
                    ToastUtils.show(TeamActivity.this, "请输入正确的订单号");
                }

            }


        } else if (i == R.id.rb_pay_teamOrder) {
            hexiao();
        } else if (i == R.id.rb_scan_teamOrder) {//扫描
            Intent intent = new Intent(TeamActivity.this, CaptureActivity.class);
            startActivityForResult(intent, 100);

        } else if (i == R.id.rb_cancel_teamOrder) {
            deleteData();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100:
                if (data != null) {
                    qrcode = data.getStringExtra("result");
                    editIdTeam.setText(qrcode);
                    HttpUtils.showProgressDialog(progressDialog);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Query(qrcode);
                        }
                    }).start();
                    return;
                } else {
                    ToastUtils.show(this, "扫描结果为空");
                }
                break;
            case 1002:
                if (-1 == resultCode) {
                    String flag = data.getStringExtra("flag");
                    Log.i(TAG, "flag------>" + flag);
                    if ("cash".equals(flag)) {
                        //成功
                        boolean isTrue = data.getBooleanExtra("isTrue", false);
                        System.out.println("走了1");
                        if (isTrue) {
                            System.out.println("走了2");
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                boolean success = jsonObject.getBoolean("Success");
                                //成功
                                System.out.println("走了3");
                                if (success) {//提示兑票成功
                                    System.out.println("走了4");
                                    TpsN900PrintUtils.PrintTeamTicket(TeamActivity.this, teamBeanList_1, shouldMoney_team.getText().toString().trim());
                                    deleteData();
                                } else {
                                    //失败
                                    Log.i(TAG, "带团兑票失败!");
                                    return;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
//                                }
                        }
                    } else if ("weChat".equals(flag)) {
                        //微信支付成功,核销
                        Constant.FLAG = data.getBooleanExtra("isTrue", false);
                        String thirdNo = data.getStringExtra("thirdNo");
                        String Refund_PayID = data.getStringExtra("refund_PayID");
                        String Refund_OderID = data.getStringExtra("refund_OderID");
                        String Refund_Money = data.getStringExtra("refund_Money");
                        Log.i(TAG, "微信支付是否成功-----》" + Constant.FLAG);
                        if (Constant.FLAG) {
                            //代表支付成功
                            String result = GsWebServiceUtils.requestTeamOrderForTickets(teamBeanList_1, payTime, thirdNo, money);
                            if (null != result) {
                                Log.i(TAG, "微信扫描成功后核销结果------->" + result);
                                try {
                                    JSONObject jsonObject = new JSONObject(result);
                                    boolean success = jsonObject.getBoolean("Success");
                                    if (success) {
                                        TpsN900PrintUtils.PrintTeamTicket(TeamActivity.this, teamBeanList_1, shouldMoney_team.getText().toString().trim());

                                        deleteData();

                                    } else {
                                        PayCode = "微信";
                                        RefundPay(PayCode, Refund_PayID, Refund_OderID, Refund_Money);
                                        //核销失败
                                        ToastUtils.show(this, "带团订单核销失败!执行退款中!");
                                        Log.i(TAG, "nnnnnnnnnnnnnn");
                                        return;
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                ToastUtils.show(this, "带团订单核销失败!");
                                Log.i(TAG, "mmmmmmmmmm");
                                return;
                            }

                        } else {
                            //失败
                            ToastUtils.show(this, "微信支付失败!");
                            return;
                        }
                    } else if ("aLiPay".equals(flag)) {
                        Constant.FLAG = data.getBooleanExtra("isTrue", false);
                        String thirdNo = data.getStringExtra("thirdNo");
                        String Refund_PayID = data.getStringExtra("refund_PayID");
                        String Refund_OderID = data.getStringExtra("refund_OderID");
                        String Refund_Money = data.getStringExtra("refund_Money");
                        Log.i(TAG, "支付宝支付是否成功-----》" + Constant.FLAG);
                        if (Constant.FLAG) {
                            //代表支付成功
                            String result = GsWebServiceUtils.requestTeamOrderForTickets(teamBeanList_1, payTime, thirdNo, money);
                            if (null != result) {
                                Log.i(TAG, "支付宝扫描成功后核销结果------->" + result);
                                try {
                                    JSONObject jsonObject = new JSONObject(result);
                                    boolean success = jsonObject.getBoolean("Success");
                                    if (success) {
                                        TpsN900PrintUtils.PrintTeamTicket(TeamActivity.this, teamBeanList_1, shouldMoney_team.getText().toString().trim());

                                        deleteData();

                                    } else {
                                        PayCode = "支付宝";
                                        RefundPay(PayCode, Refund_PayID, Refund_OderID, Refund_Money);
                                        //核销失败
                                        ToastUtils.show(this, "带团订单核销失败!执行退款中!");
                                        Log.i(TAG, "nnnnnnnnnnnnnn");
                                        return;
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                ToastUtils.show(this, "带团订单核销失败!");
                                Log.i(TAG, "hhhhhhhhhhhhh");
                                return;
                            }

                        } else {
                            //失败
                            ToastUtils.show(this, "支付宝支付失败!");
                            return;
                        }


                    }
                    break;

                }
            default:
                break;
        }

    }


    /**
     * TODO 查询输入的订单号
     *
     * @param OrderNo
     */
    public void Query(String OrderNo) {
        if (null != OrderNo && !OrderNo.isEmpty()) {
            //如果非空,检验得到字符串格式
            if (MatchUtils.isMobileNO(OrderNo) || MatchUtils.isValidCardNO(OrderNo) || MatchUtils.isValisTicketNo(OrderNo)) {
                //如果不为空且格式有效
                String result = GsWebServiceUtils.GetTeamOrders(OrderNo);

                if (null != result) {

                    if (!result.isEmpty()) {
                        //如果不为空
                        Log.i("TeamActivity----->", "result------>" + result);
                        TeamBean_1 parseObject = JSON.parseObject(result, TeamBean_1.class);
                        if (null != parseObject) {

                            if (parseObject.Data == null) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        HttpUtils.exitProgressDialog(progressDialog);
                                        ErrDialog2(TeamActivity.this, "未查询到带团订单信息!", "");
                                    }
                                });

                                return;
                            }
                            //如果不为空
                            boolean isTrue = parseObject.isSuccess();
                            if (isTrue) {

                                //如果返回值不为空
                                teamBeanList_1 = parseObject.getData().getTeamOrders();
                                String isprint = teamBeanList_1.get(0).getISPRINT();
                                Log.i("TeamActivity----->", "isprint------->" + isprint);

                                if ("1".equals(isprint)) {
                                    //弹框提示
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            HttpUtils.exitProgressDialog(progressDialog);
                                            ErrDialog2(TeamActivity.this, "该订单已核销并打印!", "");
                                        }
                                    });
                                    return;
                                }

                                if (null != teamBeanList_1) {
                                    if (!teamBeanList_1.isEmpty()) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                HttpUtils.exitProgressDialog(progressDialog);
                                                TeamBean_1.DataBean.TeamOrdersBean teamOrdersBean = teamBeanList_1.get(0);
                                                orderNum_team.setText(teamOrdersBean.getORDERNO());//订单号
                                                double totalMoney = 0.00;
                                                for (int i = 0; i < teamBeanList_1.size(); i++) {
                                                    totalMoney += teamBeanList_1.get(i).getORDERAMT();
                                                }
                                                playTime_team.setText(teamOrdersBean.getPLAYDATE().substring(0, 10));
                                                String paytype = teamOrdersBean.getPAYTYPE();
                                                if ("1".equals(paytype)) {
                                                    tv_PayTyPE.setText("账户支付");
                                                } else if ("0".equals(paytype)) {
                                                    tv_PayTyPE.setText("到付");
                                                }

                                                tv_lxs_Team.setText(teamOrdersBean.getPARTNERNAME());
                                                String ispay = teamOrdersBean.getISPAY();
                                                if ("1".equals(ispay)) {
                                                    payType_team.setText("支付");
                                                } else if ("0".equals(ispay)) {
                                                    payType_team.setText("未支付");
                                                }

                                                String isPrint = teamOrdersBean.getISPRINT();
                                                if ("1".equals(isPrint)) {
                                                    tv_ISPrint.setText("已打印");
                                                } else if ("0".equals(isPrint)) {
                                                    tv_ISPrint.setText("未打印");
                                                }
                                                teamAdapter1.setTeamBean1(teamBeanList_1);
                                                lvInfoTeam.setAdapter(teamAdapter1);
                                            }
                                        });
                                    } else {
                                        return;
                                    }
                                } else {
                                    return;
                                }


                            } else {
                                //如果为空的话
                                TeamActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        HttpUtils.exitProgressDialog(progressDialog);
                                        ErrDialog2(TeamActivity.this, "未查询到带团订单信息!", "");
                                    }
                                });
                                return;
                            }
                        } else {
                            TeamActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    HttpUtils.exitProgressDialog(progressDialog);
                                    ErrDialog2(TeamActivity.this, "未查询到带团订单信息!", "");
                                }
                            });
                            return;
                        }
                    }

                } else {
                    TeamActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            HttpUtils.exitProgressDialog(progressDialog);
                            ErrDialog2(TeamActivity.this, "未查询到带团订单信息!", "");
                        }
                    });
                    return;
                }

            } else {
                //如果为空
                TeamActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        HttpUtils.exitProgressDialog(progressDialog);
                        ErrDialog2(TeamActivity.this, "未查询到带团订单信息!", "");
                    }
                });
                return;
            }
        } else {
            TeamActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    HttpUtils.exitProgressDialog(progressDialog);
                    ErrDialog2(TeamActivity.this, "输入的号码格式不正确，请重新输入", "");

                }
            });
            return;
        }

    }

    //核销完毕之后直接清空本界面
    public void deleteData() {
        //核销成功,清空界面
        orderNum_team.setText(null);
        playTime_team.setText(null);
        payType_team.setText(null);
        shouldMoney_team.setText(null);
        tv_ISPrint.setText(null);
        tv_PayTyPE.setText(null);
        tv_lxs_Team.setText(null);
        editIdTeam.setText("");
        teamBeanList_1 = new ArrayList<>();
        teamAdapter1.setTeamBean1(teamBeanList_1);
        teamAdapter1.notifyDataSetChanged();
    }

    public void hexiao() {
        //带团订单核销
        String thirdNo = null;
        String time = getTime();
        payTime = time;
        double totalMoney = 0.00;


        if (teamBeanList_1 == null) {
            ToastUtils.show(this, "获取带团订单失败!");
            Log.i(TAG, "2222222");
            return;
        }

        if (teamBeanList_1.isEmpty()) {
            ToastUtils.show(this, "获取带团订单失败!");
            Log.i(TAG, "11111111");
            return;
        }

        Log.i(TAG, "teamOrders.size()-------->" + teamBeanList_1.size());
//
        for (int j = 0; j < teamBeanList_1.size(); j++) {
            if (teamBeanList_1.get(j).getActualPerson() == null) {
                ToastUtils.show(this, "请输入实到人数!");
                return;
            }
            if (teamBeanList_1.get(j).getActualPerson().isEmpty()) {
                ToastUtils.show(this, "请输入实到人数!");
                return;
            }
            if (teamBeanList_1.get(j).getActualPerson() == "") {
                ToastUtils.show(this, "请输入实到人数!");
                return;
            }
        }

        for (int i = 0; i < teamBeanList_1.size(); i++) {

            Log.i(TAG, "门票数量----->" + teamBeanList_1.get(i).getPRODUCTNUM() + "--------------");
            Log.i(TAG, "实到人数----->" + teamBeanList_1.get(i).getActualPerson() + "------------");


            if (teamBeanList_1.get(i).getPRODUCTNUM() < Integer.parseInt(teamBeanList_1.get(i).getActualPerson())) {
                ToastUtils.show(this, "实到人数不能大于门票数量，请检查重新输入!");
                return;

            }

            TeamBean_1.DataBean.TeamOrdersBean teamOrdersBean = teamBeanList_1.get(i);
            double perMoney = teamOrdersBean.getPRODUCTPRICE() * Integer.parseInt(teamOrdersBean.getActualPerson());

            totalMoney = totalMoney + perMoney;
        }
        money = new DecimalFormat("0.00").format(totalMoney);
        boolean flags = false;
        for (int i = 0; i < teamBeanList_1.size(); i++) {
            if (teamBeanList_1.get(i).getActualPerson().equals("0")) {
                flags = false;
            } else {
                flags = true;
                break;
            }
        }
        if (flags == true) {
            HttpUtils.showProgressDialog(progressDialog);
            ThreadPoolUtils.runTaskInThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        checkOutForTeam(teamBeanList_1, payTime, null, money);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            ToastUtils.show(this, "请重新输入实到人数");
        }

//        orderAmt_team.setText(money);


    }

    /**
     * 带团订单核销接口
     *
     * @param teamOrders
     */
    private void checkOutForTeam(List<TeamBean_1.DataBean.TeamOrdersBean> teamOrders, String payTime, String thirdNo, String money) throws JSONException {
        if (null != teamOrders) {
            if (!teamOrders.isEmpty()) {
                //如果不为空,获取第一个，判断支付类型
                TeamBean_1.DataBean.TeamOrdersBean teamOrdersBean = teamOrders.get(0);

                String paytype = teamOrdersBean.getPAYTYPE();

                if ("0".equals(paytype)) {
                    ThreadPoolUtils.runTaskInUIThread(new Runnable() {
                        @Override
                        public void run() {
                            HttpUtils.exitProgressDialog(progressDialog);
                        }
                    });
                    //如果支付类型是到付，调到支付页面
                    Intent intent = new Intent(this, Team_PayActivity.class);
                    intent.putExtra("resultList", (Serializable) teamOrders);
                    intent.putExtra("payTime", payTime);
                    intent.putExtra("thirdNo", thirdNo);
                    intent.putExtra("money", money);
                    this.startActivityForResult(intent, 1002);

                } else if ("1".equals(paytype)) {
                    //如果是定存，直接核销
                    String result = GsWebServiceUtils.requestTeamOrderForTickets(teamOrders, payTime, thirdNo, money);
                    Log.i(TAG, "带团订单核销接口result-------->" + result);
                    ThreadPoolUtils.runTaskInUIThread(new Runnable() {
                        @Override
                        public void run() {
                            HttpUtils.exitProgressDialog(progressDialog);
                        }
                    });
                    if (null != result) {
                        //如果不为null
                        JSONObject jsonObject = new JSONObject(result);
                        boolean isTrue = jsonObject.getBoolean("Success");
                        if (isTrue) {
                            ThreadPoolUtils.runTaskInUIThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Log.i(TAG, "teamOrders----->" + teamOrders.size());
                                        TpsN900PrintUtils.PrintTeamTicket(TeamActivity.this, teamOrders, shouldMoney_team.getText().toString().trim());
                                        deleteData();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            });

                        } else {
                            //核销失败
                            ThreadPoolUtils.runTaskInUIThread(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtils.show(TeamActivity.this, "带团订单核销失败!");
                                }
                            });
                            Log.i(TAG, "1111111");
                            return;
                        }

                    } else {
                        //如果为null
                        ToastUtils.show(this, "带团订单核销失败!");
                        Log.i(TAG, "222222");
                        return;
                    }

                }

            } else {
                ToastUtils.show(this, "未查询到带团订单信息");
                return;
            }

        } else {
            //如果为空
            ToastUtils.show(this, "未查询到带团订单信息");
            return;
        }


    }

    public class IntentBroadcastReceiver extends BroadcastReceiver {
        public IntentBroadcastReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("updata")) {
                double price1 = 0.00;
                for (int i = 0; i < teamBeanList_1.size(); i++) {
                    if (null == teamBeanList_1.get(i).getActualPerson()) {
                        price1 += 0 * Double.valueOf(teamBeanList_1.get(i).getPRODUCTPRICE());
                    } else {
                        price1 += Integer.valueOf(teamBeanList_1.get(i).getActualPerson()) * Double.valueOf(teamBeanList_1.get(i).getPRODUCTPRICE());
                    }

                }
                shouldMoney_team.setText(price1 + "");
                Log.e("金额是---->", price1 + "------");
            }

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            TeamActivity.this.startActivity(new Intent(this, MainActivity.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void RefundPay(final String refundPay, final String Refund_PayID, final String Refund_OderID, final String Refund_Money) {
        if ("微信".equals(refundPay)) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    PayMentData = AliAndWePayMent.PayMentAndRefund("Refund", "", "R_WaChat", "", "" + "", Refund_PayID, Refund_OderID, Refund_Money + "");
                    Log.e("支付结果是--------", PayMentData);
                    if ("-1".equals(PayMentData) || TextUtils.isEmpty(PayMentData)) {
                        if (!TextUtils.isEmpty(ErrUtils.ParameterErr)) {
                            Log.e("StringPayMent", ErrUtils.ParameterErr);
                            HttpUtils.exitProgressDialog(progressDialog);
                        } else if (!TextUtils.isEmpty(ErrUtils.PaymentCodeErr)) {
                            Log.e("StringPayMent", ErrUtils.PaymentCodeErr);
                            HttpUtils.exitProgressDialog(progressDialog);
                        }
                    } else {
                        //支付宝支付与微信支付完之后清除
                        sendMessageInfo(handler, 0, "退款");

                    }
                }
            }).start();

        } else if ("支付宝".equals(refundPay)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    PayMentData = AliAndWePayMent.PayMentAndRefund("Refund", "", "R_Alipay", "", "" + "", Refund_PayID, Refund_OderID, Refund_Money + "");
                    Log.e("支付结果是--------", PayMentData);
                    if ("-1".equals(PayMentData) || TextUtils.isEmpty(PayMentData)) {
                        if (!TextUtils.isEmpty(ErrUtils.ParameterErr)) {
                            Log.e("StringPayMent", ErrUtils.ParameterErr);
                            HttpUtils.exitProgressDialog(progressDialog);
                        } else if (!TextUtils.isEmpty(ErrUtils.PaymentCodeErr)) {
                            Log.e("StringPayMent", ErrUtils.PaymentCodeErr);
                            HttpUtils.exitProgressDialog(progressDialog);
                        }
                    } else {
                        //支付宝支付与微信支付完之后清除
//                        handler.sendEmptyMessageDelayed(0, 10);
                        sendMessageInfo(handler, 0, "退款");
                    }

                }
            }).start();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(ibr);
    }
}
