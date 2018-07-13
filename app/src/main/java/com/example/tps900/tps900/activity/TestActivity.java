package com.example.tps900.tps900.activity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.example.tps900.tps900.Bean.DeviceSaleBean;
import com.example.tps900.tps900.Bean.FormulasBean;
import com.example.tps900.tps900.Bean.GetDeviceTicketBean;
import com.example.tps900.tps900.Bean.SalePayCodeBean;
import com.example.tps900.tps900.Bean.TestBean;
import com.example.tps900.tps900.R;
import com.example.tps900.tps900.Utlis.Constant;
import com.example.tps900.tps900.Utlis.ThreadPoolUtils;
import com.example.tps900.tps900.Utlis.ToastUtils;
import com.example.tps900.tps900.adapters.TestAdapter;
import com.example.tps900.tps900.contract.LoginContract;
import com.godfery.pay.HttpUtils;

import java.util.ArrayList;
import java.util.List;

import static com.example.tps900.tps900.Webservice.GsWebServiceUtils.GetTerminalFormula;
import static com.example.tps900.tps900.Webservice.GsWebServiceUtils.GetTerminalInfo;
import static com.example.tps900.tps900.Webservice.GsWebServiceUtils.GetTerminalPayMode;
import static com.example.tps900.tps900.Webservice.GsWebServiceUtils.GetTerminalTicket;


/**
 * 项目名称：PDAXianShang
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2018-02-26 13:52
 * 修改人：zxh
 * 修改时间：2018-02-26 13:52
 * 修改备注：
 */

public class TestActivity extends BaseActivity {


    EditText testEt;

    Button testBtn;

    Button testBtnClear;

    ListView testLt;
    ArrayList<TestBean> testBeanArrayList;
    private TestAdapter testAdapter;
    private LoginContract.Presenter presenter;

    /**
     * 设置布局
     *
     * @return
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }

    /**
     * 初始化控件
     */
    @Override
    public void initView() {
        testEt = (EditText) findViewById(R.id.test_et);
        testBtn = (Button) findViewById(R.id.test_btn);
        testBtnClear = (Button) findViewById(R.id.test_btn_clear);
        testLt = (ListView) findViewById(R.id.test_lt);

    }

    /**
     * 操作数据
     */
    @Override
    public void initData() {
        testBeanArrayList = new ArrayList<>();
        testAdapter = new TestAdapter(TestActivity.this, testBeanArrayList);
        testLt.setAdapter(testAdapter);
    }

    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.test_btn:
                if (TextUtils.isEmpty(testEt.getText().toString().trim())) {
                    ToastUtils.show(TestActivity.this, "请输入循环调用接口次数");
                } else {
                    testBeanArrayList.clear();
                    testAdapter.notifyDataSetChanged();
                    m_getLineTicket();
                    long num = Long.parseLong(testEt.getText().toString().trim());
                    for (int i = 1; i < num; i++) {
                        m_getLineTicket();
                    }
                }
                break;
            case R.id.test_btn_clear:
                testBeanArrayList.clear();
                testAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }


    /**
     * 线下门票售卖
     */
    public void m_getLineTicket() {
        try {
            HttpUtils.showProgressDialog(progressDialog);
            ThreadPoolUtils.runTaskInThread(new Runnable() {
                @Override
                public void run() {
                    //TODO 售票终端信息
                    final String getS_ticket = m_getTerminalInfo();
                    Log.e("售票信息", getS_ticket);
                    if (!"OK".equals(getS_ticket)) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                HttpUtils.exitProgressDialog(progressDialog);
                                TestBean testBean = new TestBean();
                                testBean.setCode("err");
                                testBean.setResult("GetTerminalInfo\n获取售票终端信息失败");
                                testBeanArrayList.add(testBean);
                                Log.e("TestActivity", "testBeanArrayList.size():" + testBeanArrayList.size());
                                testAdapter.notifyDataSetChanged();
                            }
                        });
                    } else {
                        //TODO 门票方案
                        final String Formula = m_Formulas();
                        Log.e("zxh门票方案", Formula);
                        if ("OK".equals(Formula)) {
                            //TODO 终端可售门票
                            final String getTerminalTicket = m_getTerminalTicket();
                            Log.e("zxh终端可售门票", getTerminalTicket);
                            if ("OK".equals(getTerminalTicket)) {
                                //TODO 支付方式
                                final String TerminalPayMode = m_TerminalPayMode();
                                HttpUtils.exitProgressDialog(progressDialog);
                                Log.e("zxh支付方式", TerminalPayMode);
                                if ("err".equals(TerminalPayMode)) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            HttpUtils.exitProgressDialog(progressDialog);
                                            TestBean testBean = new TestBean();
                                            testBean.setCode("err");
                                            testBean.setResult("GetTerminalPayMode\n获取支付方式失败");
                                            testBeanArrayList.add(testBean);
                                            Log.e("TestActivity", "testBeanArrayList.size():" + testBeanArrayList.size());
                                            testAdapter.notifyDataSetChanged();

                                        }
                                    });

                                }
                            } else if ("err".equals(getTerminalTicket)) {

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        HttpUtils.exitProgressDialog(progressDialog);
                                        TestBean testBean = new TestBean();
                                        testBean.setCode("err");
                                        testBean.setResult("GetTerminalTicket\n获取可售门票失败");
                                        testBeanArrayList.add(testBean);
                                        Log.e("TestActivity", "testBeanArrayList.size():" + testBeanArrayList.size());
                                        testAdapter.notifyDataSetChanged();

                                    }
                                });
                            }
                        } else if ("err".equals(Formula)) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    HttpUtils.exitProgressDialog(progressDialog);
                                    TestBean testBean = new TestBean();
                                    testBean.setCode("err");
                                    testBean.setResult("GetTerminalFormula\n获取门票方案失败");
                                    testBeanArrayList.add(testBean);
                                    Log.e("TestActivity", "testBeanArrayList.size():" + testBeanArrayList.size());
                                    testAdapter.notifyDataSetChanged();

                                }
                            });
                        }
                    }
                }
            });
        } catch (Exception e) {
        }
    }

    /**
     * 线下门票方案
     */
    public String m_Formulas() {
        String Formulas = "";
        try {
            String getTerminalFormula = GetTerminalFormula();
            if ("err".equals(getTerminalFormula)) {
                Formulas = "err";
            } else {
                FormulasBean formulasBean = JSON.parseObject(getTerminalFormula, FormulasBean.class);
                if (formulasBean.flag) {
                    Formulas = "OK";
                } else {
                    Formulas = formulasBean.erro;
                }

            }
        } catch (Exception e) {

        }
        return Formulas;
    }

    /**
     * 获取线下可售门票信息
     */
    public String m_getTerminalTicket() {
        String TerminalTicket = "";
        try {
            String getTerminalTicket = GetTerminalTicket();
            if ("err".equals(getTerminalTicket)) {
                TerminalTicket = "err";
            } else {
                GetDeviceTicketBean ticketsBean = JSON.parseObject(getTerminalTicket, GetDeviceTicketBean.class);
                if (ticketsBean.flag) {
                    Constant.getDeviceList = ticketsBean.getTickets();
                    TerminalTicket = "OK";
                } else {
                    TerminalTicket = ticketsBean.erro;
                }

            }
        } catch (Exception e) {

        }
        return TerminalTicket;
    }

    /**
     * 获取门票的支付方式
     */
    public String m_TerminalPayMode() {
        String TerminalPayMode = "";
        try {
            String getTerminalPayMode = GetTerminalPayMode();
            if ("err".equals(getTerminalPayMode)) {
                TerminalPayMode = "err";
            } else {
                SalePayCodeBean salePayCodeBean = JSON.parseObject(getTerminalPayMode, SalePayCodeBean.class);
                if (salePayCodeBean.flag) {
                    List<SalePayCodeBean.PayModesBean> payModes = salePayCodeBean.getPayModes();
                    for (int i = 0; i < payModes.size(); i++) {
                        switch (payModes.get(i).getNpaymenttype()) {
                            case 1:
                                Constant.CaSh = 1;
                                break;
                            case 5:
                                Constant.Alipay = 5;
                                break;
                            case 8:
                                Constant.WeChat = 8;
                                break;
                            case 3:
                                Constant.OneCard = 3;
                                break;
                            default:
                        }

                    }
                    TerminalPayMode = "OK";
                } else {
                    TerminalPayMode = salePayCodeBean.erro;
                }

            }

        } catch (Exception e) {

        }
        return TerminalPayMode;
    }

    /**
     * 售票终端信息
     */
    public String m_getTerminalInfo() {
        String getS_ticketinfo = "";
        try {
            //TODO 售票终端信息
            String getS_ticket = GetTerminalInfo();
            Log.e("售票信息", getS_ticket);
            if ("err".equals(getS_ticket)) {
                getS_ticketinfo = "获取数据异常请查网络\n或者检查一下配置";
            } else {
                DeviceSaleBean deviceSaleBean = JSON.parseObject(getS_ticket, DeviceSaleBean.class);
                if (deviceSaleBean.getTerminals() == null || deviceSaleBean.getTerminals().size() == 0) {
                    getS_ticketinfo = "获取设备信息数据异常请查网络\n或者检查一下配置\n或重新启动";
                } else {
                    for (int i = 0; i < deviceSaleBean.getTerminals().size(); i++) {
                        Constant.ter_id = String.valueOf(deviceSaleBean.getTerminals().get(i).getNterminalid());
                        Constant.emp_id = String.valueOf(deviceSaleBean.getTerminals().get(i).getNterminalid());
                    }
                    Constant.S_deviceID = String.valueOf(deviceSaleBean.getTerminals().get(0).getNterminalid());
                    getS_ticketinfo = "OK";
                }
            }
        } catch (Exception e) {

        }
        return getS_ticketinfo;
    }

}
