package com.example.tps900.tps900.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.tps900.tps900.Bean.FormBean;
import com.example.tps900.tps900.Bean.Project_feeBean;
import com.example.tps900.tps900.Bean.SalaTicketFormsBean;
import com.example.tps900.tps900.Bean.TicketFormsBean;
import com.example.tps900.tps900.R;
import com.example.tps900.tps900.Utlis.Constant;
import com.example.tps900.tps900.Utlis.DialogUtil;
import com.example.tps900.tps900.Utlis.LogUtil;
import com.example.tps900.tps900.Utlis.OtherUtils;
import com.example.tps900.tps900.Utlis.TimeUtils;
import com.example.tps900.tps900.Utlis.TpsN900PrintUtils;
import com.example.tps900.tps900.Webservice.GsWebServiceUtils;
import com.example.tps900.tps900.adapters.ExamplePagerAdapter;
import com.example.tps900.tps900.adapters.FormAdapter;
import com.example.tps900.tps900.adapters.PaytypeAdapter;
import com.example.tps900.tps900.adapters.SaletAdapter;
import com.example.tps900.tps900.adapters.ScaleTransitionPagerTitleView;
import com.example.tps900.tps900.adapters.TicketFormAdapter;
import com.example.tps900.tps900.view.GalasysListView;
import com.example.tps900.tps900.view.magicindicator.MagicIndicator;
import com.example.tps900.tps900.view.magicindicator.ViewPagerHelper;
import com.example.tps900.tps900.view.magicindicator.buildins.commonnavigator.CommonNavigator;
import com.example.tps900.tps900.view.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import com.example.tps900.tps900.view.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import com.example.tps900.tps900.view.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import com.example.tps900.tps900.view.magicindicator.buildins.commonnavigator.indicators.BezierPagerIndicator;
import com.example.tps900.tps900.view.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.tps900.tps900.Utlis.DialogUtil.cancelDownloadDialog;
import static com.example.tps900.tps900.Utlis.OtherUtils.sendMessageInfo;


public class FoodFormActivity extends BaseActivity {

    public static final String TAG = "GoodFormActivity";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            int what = msg.what;
            switch (what) {
                case 1001:
                    String result = (String) msg.obj;
                    try {
                        parseJson(result);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;
                case 1002:
                    String result1 = (String) msg.obj;
                    if ("err".equals(result1)) {
                    } else {
                        try {
                            parseJSONWithJSONObject1(result1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                    break;
                case 1003:
                    String result2 = (String) msg.obj;
                    if ("err".equals(result2)) {

                    } else {
                        try {
                            parseJSONWithJSONObject(result2);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    break;

                default:
                    break;

            }


        }
    };
    private TextView tv_counter_form;
    private TextView tv_sellerID_form;
    private TextView tv_sellerName_form;
    private TextView tv_sellDate_form;
    private TextView tv_printDate_form;
    private GalasysListView lv_payType_consumer_form;
    private TextView tv_allMoney_consumer_form;
    private GalasysListView lv_payType_return_form;
    private TextView tv_allMoney_return_form;
    private GalasysListView lv_payTypeAndCountAndMoney_current;
    private TextView tv_allMoney_current_form;
    private ScrollView sv_middle;
    private LinearLayout ll_fail;
    private ArrayList<FormBean> payList;
    private ArrayList<FormBean> returnList;
    private ArrayList<FormBean> currentList;
    private ArrayList<Project_feeBean.TicketPrintsBean> Y_ticketList;
    private ArrayList<Project_feeBean.TicketPrintsBean> X_ticketList;
    private ArrayList<Project_feeBean.TicketPrintsBean> S_ticketList;
    private double totalPayMoney;
    private double totalReturnMoney;
    private double totalMoney;
    private String empId;
    private String empname;
    private String newStartDate;
    private String printDate;
    private GalasysListView lv_Y_ticket_consumer_form;
    private GalasysListView lv_X_ticket_consumer_form;
    private GalasysListView lv_S_ticket_consumer_form;
    private TextView Y_ticket_allNum, Y_ticket_allPrice, X_ticket_allNum, X_ticket_allPrice, ticket_allNum, ticket_allPrice;
    private ArrayList<View> listView;
    private static final String[] CHANNELS = new String[]{"线上", "线下", "售卖"};
    private List<String> mDataList = Arrays.asList(CHANNELS);
    private ExamplePagerAdapter mExamplePagerAdapter;
    private ViewPager mViewPager;
    private LinearLayout ticket_lv;
    private TextView tv_counter_form1;
    private TextView tv_sellerID_form1;
    private TextView tv_sellerName_form1;
    private ArrayList<SalaTicketFormsBean> salelist;
    private ArrayList<TicketFormsBean> ticketlist;
    private ArrayList<TicketFormsBean> classifyticketlist;
    private ListView sale_listview;
    private boolean ishas;
    private int hasIndex;
    private ArrayList<SalaTicketFormsBean> salelist1;
    private ArrayList<SalaTicketFormsBean> PaytypeList;

    /**
     * 设置布局
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_food_form;
    }

    /**
     * 初始化控件
     */
    @Override
    public void initView() {
        DialogUtil.showDownloadDialog(FoodFormActivity.this);
    }

    /**
     * 操作数据
     */
    @Override
    public void initData() {
        findView();
    }


    private void findView() {
        ticketlist = new ArrayList<TicketFormsBean>();
        salelist = new ArrayList<SalaTicketFormsBean>();
        //返回按钮
        Button btn_back_form = (Button) findViewById(R.id.btn_back_form);
        //打印按钮
        Button btn_print_form = (Button) findViewById(R.id.btn_print_form);
        //收银柜台
        tv_counter_form = (TextView) findViewById(R.id.tv_counter_form);
        //收银员ID
        tv_sellerID_form = (TextView) findViewById(R.id.tv_sellerID_form);
        //收银员
        tv_sellerName_form = (TextView) findViewById(R.id.tv_sellerName_form);
        //收银柜台
        tv_counter_form1 = (TextView) findViewById(R.id.tv_counter_form1);
        //收银员ID
        tv_sellerID_form1 = (TextView) findViewById(R.id.tv_sellerID_form1);
        //收银员
        tv_sellerName_form1 = (TextView) findViewById(R.id.tv_sellerName_form1);
        //销售日期
        tv_sellDate_form = (TextView) findViewById(R.id.tv_sellDate_form);
        //打印日期
        tv_printDate_form = (TextView) findViewById(R.id.tv_printDate_form);
        //消费购买信息列表
        lv_payType_consumer_form = (GalasysListView) findViewById(R.id.lv_payType_consumer_form);
        //购买总金额
        tv_allMoney_consumer_form = (TextView) findViewById(R.id.tv_allMoney_consumer_form);
        //退货信息列表
        lv_payType_return_form = (GalasysListView) findViewById(R.id.lv_payType_return_form);
        //退货总金额
        tv_allMoney_return_form = (TextView) findViewById(R.id.tv_allMoney_return_form);
        //当日合计信息列表
        lv_payTypeAndCountAndMoney_current = (GalasysListView) findViewById(R.id.lv_payTypeAndCountAndMoney_current);
        //当日合计总金额
        tv_allMoney_current_form = (TextView) findViewById(R.id.tv_allMoney_current_form);
        LayoutInflater inflater = LayoutInflater.from(this);
        listView = new ArrayList<View>();
        View view1 = inflater.inflate(R.layout.view_y_ticket, null);
        View view2 = inflater.inflate(R.layout.view_t_ticket, null);
        View view3 = inflater.inflate(R.layout.view_s_ticket, null);
        sale_listview = (GalasysListView) view3.findViewById(R.id.sale_listview);
        //线上核销数量统计
        lv_Y_ticket_consumer_form = (GalasysListView) view1.findViewById(R.id.lv_Y_ticket_consumer_form);
        //线下核销数量统计
        lv_X_ticket_consumer_form = (GalasysListView) view2.findViewById(R.id.lv_X_ticket_consumer_form);
        //线下售卖数量统计
        lv_S_ticket_consumer_form = (GalasysListView) view3.findViewById(R.id.lv_S_ticket_consumer_form);

        //线上核销总数
        Y_ticket_allNum = (TextView) view1.findViewById(R.id.Y_ticket_allNum);
        //线上核销金额总计
        Y_ticket_allPrice = (TextView) view1.findViewById(R.id.Y_ticket_allPrice);
        //线下核销总数
        X_ticket_allNum = (TextView) view2.findViewById(R.id.X_ticket_allNum);
        //线下核销金额总计
        X_ticket_allPrice = (TextView) view2.findViewById(R.id.X_ticket_allPrice);
        //线下门票售卖总数
        ticket_allNum = (TextView) view3.findViewById(R.id.ticket_allNum);
        //线下门票售卖金额总计
        ticket_allPrice = (TextView) view3.findViewById(R.id.ticket_allPrice);

        sv_middle = (ScrollView) findViewById(R.id.sv_middle);
        ll_fail = (LinearLayout) findViewById(R.id.ll_fail);//ticket_lv
        ticket_lv = (LinearLayout) findViewById(R.id.ticket_lv);
        ticket_lv.setVisibility(View.GONE);
        listView.add(view1);
        listView.add(view2);
        listView.add(view3);
        mExamplePagerAdapter = new ExamplePagerAdapter(listView, this);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(mExamplePagerAdapter);
        initMagicIndicator();
        btn_back_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityAndCloseThis(MainActivity.class);
            }
        });


        btn_print_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try {

                    for (int i = 0; i < currentList.size(); i++) {
                        LogUtil.i(TAG, currentList.get(i).getSMONEYNAMECN());
                        LogUtil.i(TAG, currentList.get(i).getNAMOUNT());
                    }

                    TpsN900PrintUtils.TpsN900FormPrint
                            (FoodFormActivity.this,"餐饮报表信息", X_ticket_allNum.getText().toString().trim(),
                                    X_ticket_allPrice.getText().toString().trim(), PaytypeList,
                                    ticket_allNum.getText().toString().trim(), ticket_allPrice.getText().toString().trim(),
                                    payList, new DecimalFormat("#0.00").format(totalPayMoney), returnList, new DecimalFormat("#0.00").format(totalReturnMoney), currentList, new DecimalFormat("#0.00").format(totalMoney), Constant.Food_ZONENAME, empId, empname, newStartDate, printDate);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                GsWebServiceUtils gs = new GsWebServiceUtils();
                String time = TimeUtils.time_S();
                String substring = time.substring(0, 11);
                String startTime = substring + "00:00:00";
                String endTime = substring + "23:59:59";
                String json = "{\"nterminalid\"" + ":" + Constant.Food_TERMINALID + ",\"starttime\"" + ":\"" + startTime + "\"" + ",\"endtime\"" + ":\"" + endTime + "\"" + ",\"NEMPID\"" + ":" + Constant.EMPID + "}";//订单明细信息
                LogUtil.i(TAG, "收银日报：json----->" + json);
                String shouYinInfo = GsWebServiceUtils.getFoodShouYinInfo(json);
                LogUtil.i(TAG, "shouYinInfo---->" + shouYinInfo);
                if (null != shouYinInfo && !"err".equals(shouYinInfo)) {
                    try {
                        JSONObject jsonObject = new JSONObject(shouYinInfo);
                        boolean isSuccess = jsonObject.getBoolean("isSuccess");
                        if (isSuccess) {
                            //获取收银报表信息成功
                            DialogUtil.cancelDownloadDialog();
                            sendMessageInfo(handler, 1001, shouYinInfo);

                        } else {
                            //获取收银报表信息失败，返回值为FALSE
                            FoodFormActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    cancelDownloadDialog();
                                    LogUtil.i(TAG, "收银日报返回值为FALSE");
                                    sv_middle.setVisibility(View.INVISIBLE);
                                    ll_fail.setVisibility(View.VISIBLE);
                                }
                            });

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    FoodFormActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            cancelDownloadDialog();
                            LogUtil.i(TAG, "收银日报返回为空");
                            sv_middle.setVisibility(View.INVISIBLE);
                            ll_fail.setVisibility(View.VISIBLE);
                        }
                    });

                }
            }
        });

        thread.start();


    }

    //解析json
    private void parseJson(String result) throws JSONException {

        JSONObject json = new JSONObject(result);
        JSONObject pdi = json.getJSONObject("pdi");
        String terminalId = pdi.getString("nterminalid");
        String startDate = pdi.getString("starttime");
        String endDate = pdi.getString("endtime");
        empId = pdi.getString("nempid");
        empname = pdi.getString("empname");
        String terminalname = pdi.getString("terminalname");
        newStartDate = startDate.substring(0, 10);
        printDate = TimeUtils.time_S().substring(0, 16);

        tv_counter_form.setText(Constant.Food_ZONENAME);
        tv_sellerID_form.setText(Constant.EMPID);
        tv_sellerName_form.setText(Constant.EMPNAME);
        tv_sellDate_form.setText(newStartDate);
        tv_printDate_form.setText(printDate);

        payList = new ArrayList<>();
        returnList = new ArrayList<>();
        currentList = new ArrayList<>();


        JSONArray returnTypes = pdi.getJSONArray("returnTypes");
        for (int i = 0; i < returnTypes.length(); i++) {
            JSONObject jsonObject = returnTypes.getJSONObject(i);
            String payType = jsonObject.getString("payType");
            if ("付款".equals(payType)) {

                boolean isSuccess = jsonObject.getBoolean("isSuccess");

                if (isSuccess) {
                    JSONArray payments = jsonObject.getJSONArray("payments");
                    for (int j = 0; j < payments.length(); j++) {
                        FormBean bean = new FormBean();
                        JSONObject pay = payments.getJSONObject(j);

                        bean.setSMONEYNAMECN(pay.getString("SMONEYNAMECN"));
                        bean.setNAMOUNT(pay.getString("NAMOUNT"));
                        payList.add(bean);
                    }
                } else {
                    continue;
                }

            } else if ("退款".equals(payType)) {
                boolean isSuccess = jsonObject.getBoolean("isSuccess");
                if (isSuccess) {
                    JSONArray payments = jsonObject.getJSONArray("payments");
                    LogUtil.i(TAG, payments + "");
                    for (int j = 0; j < payments.length(); j++) {
                        FormBean bean = new FormBean();
                        JSONObject pay = payments.getJSONObject(j);

                        bean.setSMONEYNAMECN(pay.getString("SMONEYNAMECN"));
                        bean.setNAMOUNT(pay.getString("NAMOUNT"));

                        returnList.add(bean);
                    }
                } else {
                    continue;
                }

            }
        }

        FormAdapter payAdapter = new FormAdapter(FoodFormActivity.this, payList);
        lv_payType_consumer_form.setAdapter(payAdapter);

        FormAdapter returnAdapter = new FormAdapter(FoodFormActivity.this, returnList);
        lv_payType_return_form.setAdapter(returnAdapter);

        totalPayMoney = 0.00;
        for (int i = 0; i < payList.size(); i++) {

            FormBean bean = payList.get(i);
            totalPayMoney += Double.parseDouble(bean.getNAMOUNT());
            FormBean formBean = new FormBean();
            formBean.setSMONEYNAMECN(bean.getSMONEYNAMECN());
            formBean.setNAMOUNT(bean.getNAMOUNT());
            currentList.add(formBean);
        }

        totalReturnMoney = 0.00;
        for (int i = 0; i < returnList.size(); i++) {
            FormBean bean = returnList.get(i);
            totalReturnMoney += Double.parseDouble(bean.getNAMOUNT());

        }

        for (int i = 0; i < currentList.size(); i++) {
            FormBean formBean = currentList.get(i);
            for (int j = 0; j < returnList.size(); j++) {
                FormBean formBean1 = returnList.get(j);
                if (formBean.getSMONEYNAMECN().equals(formBean1.getSMONEYNAMECN())) {
                    formBean.setNAMOUNT(String.valueOf(Double.parseDouble(formBean.getNAMOUNT()) - Double.parseDouble(formBean1.getNAMOUNT())));

                }
            }
        }

        if (totalPayMoney > 1.00) {
            tv_allMoney_consumer_form.setText(new DecimalFormat("#0.00").format(totalPayMoney));
        } else {
            tv_allMoney_consumer_form.setText(String.valueOf(totalPayMoney));
        }

        if (totalReturnMoney > 1.00) {
            tv_allMoney_return_form.setText(new DecimalFormat("#0.00").format(totalReturnMoney));
        } else {
            tv_allMoney_return_form.setText(String.valueOf(totalReturnMoney));
        }

        totalMoney = totalPayMoney - totalReturnMoney;

        if (totalMoney > 1.00) {
            tv_allMoney_current_form.setText(new DecimalFormat("#0.00").format(totalMoney));
        } else {
            tv_allMoney_current_form.setText(new DecimalFormat("#0.00").format(totalMoney));
        }

        FormAdapter currentAdapter = new FormAdapter(FoodFormActivity.this, currentList);
        lv_payTypeAndCountAndMoney_current.setAdapter(currentAdapter);

    }

    private void initMagicIndicator() {
        MagicIndicator magicIndicator = (MagicIndicator) findViewById(R.id.magic_indicator6);
        magicIndicator.setBackgroundColor(Color.WHITE);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setTextSize(24);
                simplePagerTitleView.setGravity(Gravity.CENTER);
                simplePagerTitleView.setNormalColor(Color.GRAY);
                simplePagerTitleView.setSelectedColor(Color.BLACK);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                        Log.e("哈哈哈", "xixiixixixiixixixi");
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                BezierPagerIndicator indicator = new BezierPagerIndicator(context);
                indicator.setColors(Color.parseColor("#ff4a42"), Color.parseColor("#fcde64"), Color.parseColor("#73e8f4"), Color.parseColor("#76b0ff"), Color.parseColor("#c683fe"));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }


    private void parseJSONWithJSONObject(String jsonData) {
        try {
            //将json字符串jsonData装入JSON数组，即JSONArray
            //jsonData可以是从文件中读取，也可以从服务器端获得

            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                //循环遍历，依次取出JSONObject对象
                //用getInt和getString方法取出对应键值
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String paytype = jsonObject.getString("PAYMENTSTYLE");
                String paymoney = jsonObject.getString("ITEMMONEY");
                String ticketname = jsonObject.getString("ITEMNAME");
                String ticketNumber = jsonObject.getString("AMOUNT");
                SalaTicketFormsBean stfb = new SalaTicketFormsBean();
                stfb.setITEMMONEY(paymoney);
                stfb.setPAYMENTSTYLE(paytype);
                stfb.setITEMNAME(ticketname);
                stfb.setAMOUNT(ticketNumber);
                salelist.add(stfb);
                Log.d("MainActivity", "付款名称: " + stfb.getPAYMENTSTYLE());
                Log.d("MainActivity", "stu_sex: " + paymoney);
                Log.d("salelist", "salelist: " + salelist.size());

            }
            SaleClassify();
            parseJSONWithJSONObject2(jsonData);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void parseJSONWithJSONObject2(String jsonData) {
        try {
            //将json字符串jsonData装入JSON数组，即JSONArray
            //jsonData可以是从文件中读取，也可以从服务器端获得
            salelist1 = new ArrayList<SalaTicketFormsBean>();
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                //循环遍历，依次取出JSONObject对象
                //用getInt和getString方法取出对应键值
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String paytype = jsonObject.getString("PAYMENTSTYLE");
                String paymoney = jsonObject.getString("ITEMMONEY");
                String ticketname = jsonObject.getString("ITEMNAME");
                String ticketNumber = jsonObject.getString("AMOUNT");
                SalaTicketFormsBean stfb = new SalaTicketFormsBean();
                stfb.setITEMMONEY(paymoney);
                stfb.setPAYMENTSTYLE(paytype);
                stfb.setITEMNAME(ticketname);
                stfb.setAMOUNT(ticketNumber);
                salelist1.add(stfb);
                Log.d("MainActivity", "stu_name: " + stfb.getPAYMENTSTYLE());
                Log.d("MainActivity", "stu_sex: " + paymoney);
                Log.d("salelist", "salelist: " + salelist1.size());

            }
            PaytypeList = new ArrayList<SalaTicketFormsBean>();
            for (SalaTicketFormsBean bill1 : salelist1) {
                boolean state = false;
                for (SalaTicketFormsBean bills1 : PaytypeList) {
                    Log.e("金额是------", bill1.getAMOUNT() + "--------");
                    if (bills1.getPAYMENTSTYLE().equals(bill1.getPAYMENTSTYLE())) {
                        double ticketPrice = Integer.valueOf(bills1.getAMOUNT()) * Double.valueOf(bills1.getITEMMONEY());
                        ticketPrice += Integer.valueOf(bill1.getAMOUNT()) * Double.valueOf(bill1.getITEMMONEY());
                        bills1.setITEMMONEY(String.valueOf(ticketPrice));
                        bills1.setPAYMENTSTYLE(bills1.getPAYMENTSTYLE());
                        state = true;
                    }
                }
                if (!state) {
                    PaytypeList.add(bill1);
                }
            }
            PaytypeAdapter S_ticketAdapter2 = new PaytypeAdapter(FoodFormActivity.this, PaytypeList);
            S_ticketAdapter2.setClassifysalelist(PaytypeList);
            sale_listview.setAdapter(S_ticketAdapter2);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void SaleClassify() {
        int cuntTicket = 0;
        double countPrice = 0.00;
        ArrayList<SalaTicketFormsBean> Classifysalelist1 = new ArrayList<SalaTicketFormsBean>();
        for (SalaTicketFormsBean bill : salelist) {
            boolean state = false;
            for (SalaTicketFormsBean bills : Classifysalelist1) {
                if (bills.getPAYMENTSTYLE().equals(bill.getPAYMENTSTYLE()) && bills.getITEMNAME().equals(bill.getITEMNAME())) {
                    int ticketNumber1 = Integer.valueOf(bills.getAMOUNT());
                    ticketNumber1 += Integer.valueOf(bill.getAMOUNT());
                    bills.setAMOUNT(String.valueOf(ticketNumber1));
                    bills.setPAYMENTSTYLE(bills.getPAYMENTSTYLE());
                    state = true;
                }
            }
            if (!state) {
                Classifysalelist1.add(bill);
            }
        }

        for (int i = 0; i < Classifysalelist1.size(); i++) {
            cuntTicket += Integer.valueOf(Classifysalelist1.get(i).getAMOUNT());
            countPrice += Integer.valueOf(Classifysalelist1.get(i).getAMOUNT()) * Double.valueOf(Classifysalelist1.get(i).getITEMMONEY());
        }
        Log.e("Classifysalelist1", Classifysalelist1.size() + "长度");
        ticket_allNum.setText(cuntTicket + "次");
        try {
            ticket_allPrice.setText(OtherUtils.doubleprice(String.valueOf(countPrice)) + "元");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SaletAdapter S_ticketAdapter = new SaletAdapter(FoodFormActivity.this, Classifysalelist1, "");
        lv_S_ticket_consumer_form.setAdapter(S_ticketAdapter);
        Log.e("合成类数组是----------------》", Classifysalelist1.size() + "");

    }

    /**
     * @author zxh
     * created at 2017/6/19 14:10
     * 方法名:
     * 方法说明: 门票核销解析数据
     */
    private void parseJSONWithJSONObject1(String jsonData) {
        try {
            //将json字符串jsonData装入JSON数组，即JSONArray
            //jsonData可以是从文件中读取，也可以从服务器端获得

            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                //循环遍历，依次取出JSONObject对象
                //用getInt和getString方法取出对应键值
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String ticketname = jsonObject.getString("TICKETNAME");
                String ticketprice = jsonObject.getString("TICKETMONEY");
                String ticketnumber = jsonObject.getString("INPARK");
                if ("0".equals(ticketnumber)) {

                } else {
                    TicketFormsBean stfb = new TicketFormsBean();
                    stfb.setTICKETNAME(ticketname);
                    stfb.setTICKETMONEY(ticketprice);
                    stfb.setINPARK(ticketnumber);
                    ticketlist.add(stfb);
                }

                Log.d("MainActivity", "ticketname: " + ticketname);
                Log.d("MainActivity", "ticketprice: " + ticketprice);
                Log.d("salelist", "ticketlist: " + ticketlist.size());

            }
            TicketClassify1();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //门票数据分类
    public void TicketClassify1() {
        int ticketNumber = 0;
        double ticketPrice = 0.00;
        classifyticketlist = new ArrayList<TicketFormsBean>();
        for (TicketFormsBean bill : ticketlist) {
            ticketNumber += Integer.valueOf(bill.getINPARK());
            ticketPrice += Integer.valueOf(bill.getINPARK()) * Double.valueOf(bill.getTICKETMONEY());
            boolean state = false;
            for (TicketFormsBean bills : classifyticketlist) {
                if (bills.getTICKETNAME().equals(bill.getTICKETNAME())) {
                    int ticketNumber1 = Integer.valueOf(bills.getINPARK());
                    ticketNumber1 += Integer.valueOf(bill.getINPARK());
                    bills.setINPARK(String.valueOf(ticketNumber1));
                    bills.setTICKETNAME(bills.getTICKETNAME());
                    state = true;
                }
            }
            if (!state) {
                classifyticketlist.add(bill);
            }
        }

        try {
            X_ticket_allPrice.setText(OtherUtils.doubleprice(ticketPrice + "") + "元");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        X_ticket_allNum.setText(ticketNumber + "次");
        TicketFormAdapter S_ticketAdapter = new TicketFormAdapter(FoodFormActivity.this, classifyticketlist);
        S_ticketAdapter.setClassifysalelist(classifyticketlist);
        lv_X_ticket_consumer_form.setAdapter(S_ticketAdapter);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        openActivityAndCloseThis(MainActivity.class);
        return super.onKeyDown(keyCode, event);
    }
}
