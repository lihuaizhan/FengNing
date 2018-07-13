package com.example.tps900.tps900.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.tps900.tps900.Bean.ApplicationListBean;
import com.example.tps900.tps900.R;
import com.example.tps900.tps900.Utlis.Constant;
import com.example.tps900.tps900.Utlis.OtherUtils;
import com.example.tps900.tps900.Utlis.SPUtils;
import com.example.tps900.tps900.Utlis.ToastUtils;
import com.example.tps900.tps900.Utlis.Updata_Bean;
import com.example.tps900.tps900.WEBSERVICE_Utils.Constants;
import com.example.tps900.tps900.activity.newfood.FoodReturnActivity;
import com.example.tps900.tps900.activity.newfood.New_FoodActivity;
import com.example.tps900.tps900.adapters.ApplicatioinListAdapter;
import com.example.tps900.tps900.contract.MainContract;
import com.example.tps900.tps900.presenter.MainPresenter;
import com.example.tps900.tps900.sql.PdaDaoUtils;
import com.example.tps900.tps900.updata.UpdateEditionActivity;
import com.godfery.Sqlite.DBUtils;
import com.godfery.pay.HttpUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.example.tps900.tps900.Utlis.Constant.DOWN_APK_URL;
import static com.example.tps900.tps900.Utlis.Constant.title;
import static com.example.tps900.tps900.Utlis.Constant.title_12;
import static com.example.tps900.tps900.Utlis.Constant.title_13;
import static com.example.tps900.tps900.Utlis.OtherUtils.getTime;


public class MainActivity extends BaseActivity implements MainContract.View {

    ProgressBar mainProgressBar;

    RelativeLayout relativeLayout;

    TextView data_tv, versionName;
    //包的信息
    PackageInfo packageInfo = null;
    RecyclerView mainReceyclerView;
    private static boolean isExit = false;
    int flag = 0;
    Handler mHandlerback = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };
    //主页显示图标bean对象
    private List<ApplicationListBean> applist_data;
    //主页显示图标的适配器
    private ApplicatioinListAdapter listAdapter;
    //主页的presenter
    private MainContract.Presenter presenter;
    String Type = "";
    public static final int MIN_CLICK_DELAY_TIME = 2000;
    private long lastClickTime = 0;

    /**
     * 设置布局
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    /**
     * 初始化控件
     */
    @Override
    public void initView() {
        presenter = new MainPresenter(this);
        if (Constant.isLogin) {
            Constant.dbUtils = new DBUtils(MainActivity.this, "galasys.db");
            PdaDaoUtils.OpenSql_CreatTable(MainActivity.this);
            OtherUtils.setConstants(MainActivity.this);
            if (Constant.isUpdata) {
                presenter.getP_UpdataAPP();
            }
        }
        versionName = (TextView) findViewById(R.id.mian_tv_versionName);
        mainProgressBar = (ProgressBar) findViewById(R.id.main_progressBar);
        relativeLayout = (RelativeLayout) findViewById(R.id.activity_main2);
        data_tv = (TextView) findViewById(R.id.button_cost_tv);
        mainReceyclerView = (RecyclerView) findViewById(R.id.main_receyclerView);
        Constant.SetOrMain = "Main";
        SPUtils.put(MainActivity.this, SPUtils.WIFI_DOWNLOAD_SWITCH, true); //将网络状态保存到数据库
        relativeLayout.setVisibility(View.GONE);
        Constant.Time = OtherUtils.time();
        applist_data = new ArrayList<>();
        listAdapter = new ApplicatioinListAdapter(MainActivity.this, R.layout.gridview_item, applist_data);
        //绑定Adapter
        listAdapter.bindToRecyclerView(mainReceyclerView);
        //显示布局风格
        mainReceyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mainReceyclerView.setAdapter(listAdapter);
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_CONFIGURATIONS);
            String packageName = packageInfo.versionName;
            //设置版本号
            versionName.setText("V" + packageName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 操作数据
     */
    @Override
    public void initData() {
        versionTitle();
        listAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PdaDaoUtils utils = new PdaDaoUtils();
                List list = utils.info_query();
                if (list.size() == 0) {
                    if (position == Constant.modleNum - 1) {
                        openActivityAndCloseThis(SettingActivity.class);
                        return;
                    } else {
                        ToastUtils.show(MainActivity.this, "请填写设置页面");
                        return;
                    }
                } else if (list.size() > 0) {
                    versionApp(position);
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!isExit) {
                isExit = true;
                ToastUtils.show(this, "再按一次退出程序");
                // 利用handler延迟发送更改状态信息
                mHandlerback.sendEmptyMessageDelayed(0, 2000);
            } else {
                flag = 0;
                System.exit(0);
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * Constant.versionNumber
     * 根据版本号显示不同的版本模块
     *
     * @param position
     */
    public void versionApp(int position) {
        switch (Constant.versionNumber) {
            case 0:
                if (position == 0) {//门票核销
                    openActivityAndCloseThis(TicketVerificationActivity.class);
                } else if (position == 1) {//次卡核销
                    openActivityAndCloseThis(CodeActivty.class);
                } else if (position == 2) {//年卡核销
                    openActivityAndCloseThis(YearCodeActivty.class);
                } else if (position == 3) {//商品售卖
                    Type = "商品售卖";
                    if (!Constants.IsGoods) {
                        //显示登录框
                        presenter.p_showDialog_Login();
                    } else {
                        openActivityAndCloseThis(RetailActivity.class);
                    }

                } else if (position == 4) {//餐饮售卖
                    Type = "餐饮售卖";
                    if (!Constants.IsGoods) {
                        //显示登录框
                        presenter.p_showDialog_Login();
                    } else {
                        openActivityAndCloseThis(FoodActivity.class);
                    }
                } else if (position == 5) {//门票售卖
                    openActivityAndCloseThis(GetSaleTicketActivity.class);
                } else if (position == 6) {//补打上笔

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (Constant.XS_PrintList != null) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        OtherUtils.Ticket_S_Print(MainActivity.this, Constant.XS_PrintList, Constant.Printpaymoney, Constant.PrintYMoney);
                                    }
                                }).start();
                            } else {
                                ToastUtils.show(MainActivity.this, "暂无数据");
                            }
                        }
                    });


                } else if (position == 7) {//查询报表
                    openActivityAndCloseThis(FormActivity.class);
                } else if (position == 8) {//商品退货
                    openActivityAndCloseThis(ReturnActivity.class);
                } else if (position == 9) {//消费支付
                    openActivityAndCloseThis(CunPayActivity.class);
                } else if (position == 10) {//散客购票
                    openActivityAndCloseThis(DisOrderChangeTicketActivity.class);
                } else if (position == 11) {//团队购票
                    openActivityAndCloseThis(TeamActivity.class);
                } else if (position == 12) {//线上售卖门票
                    openActivityAndCloseThis(RetailActivity_XS.class);
                } else if (position == 13) {//线上会员卡查询
                    openActivityAndCloseThis(CodeBalanceActivity.class);
                } else if (position == 14) {//线下一卡通查询
                    openActivityAndCloseThis(OneCardInfoActivity.class);
                } else if (position == 15) {//系统设置
                    openActivityAndCloseThis(SettingActivity.class);
                }
                break;
            case 1:
                if (position == 0) {//门票核销
                    if (Constant.mainLineWriteOff) {
                        openActivityAndCloseThis(TicketVerificationActivity.class);
                    } else {
                        presenter.p_getLineWriteOff();
                    }
                } else if (position == 1) {//门票售卖
                    if (Constant.mainLineTicket) {
                        openActivityAndCloseThis(GetSaleTicketActivity.class);
                    } else {
                        presenter.p_getLineTicket();
                    }
                } else if (position == 2) {//设置页面
                    openActivityAndCloseThis(SettingActivity.class);
                }
                break;
            case 2:
                if (position == 0) {//售卖
//                    openActivityAndCloseThis(GetSaleTicketActivity.class);
                    presenter.p_getLineTicket();
                } else if (position == 1) {//散客兑票
                    openActivityAndCloseThis(DisOrderChangeTicketActivity_1.class);
                } else if (position == 2) {//团队兑票
                    openActivityAndCloseThis(TeamActivity.class);
                } else if (position == 3) {//设置页面
                    openActivityAndCloseThis(SettingActivity.class);
                }
                break;
            case 3:
                if (position == 0) {//门票核销
                    if (Constant.mainLineWriteOff) {
                        openActivityAndCloseThis(TicketVerificationActivity.class);
                    } else {
                        presenter.p_getLineWriteOff();
                    }
                } else if (position == 1) {//散客兑票
                    openActivityAndCloseThis(DisOrderChangeTicketActivity_1.class);
                } else if (position == 2) {//团队兑票
                    openActivityAndCloseThis(TeamActivity.class);
                } else if (position == 3) {//设置页面
                    openActivityAndCloseThis(SettingActivity.class);
                }
                break;
            case 4:
                if (position == 0) {//门票核销
                    if (Constant.mainLineWriteOff) {
                        openActivityAndCloseThis(TicketVerificationActivity.class);
                    } else {
                        presenter.p_getLineWriteOff();
                    }
                } else if (position == 1) {//门票售卖
                    if (Constant.mainLineTicket) {
                        openActivityAndCloseThis(GetSaleTicketActivity.class);
                    } else {
                        presenter.p_getLineTicket();
                    }
                } else if (position == 2) {//散客兑票
                    openActivityAndCloseThis(DisOrderChangeTicketActivity_1.class);
                } else if (position == 3) {//团队兑票
                    openActivityAndCloseThis(TeamActivity.class);
                } else if (position == 4) {//设置
                    openActivityAndCloseThis(SettingActivity.class);
                }
                break;
            case 5:
                if (position == 0) {//消费支付
                    openActivityAndCloseThis(CunPayActivity.class);
                } else if (position == 1) {//设置
                    openActivityAndCloseThis(SettingActivity.class);
                }
                break;
            case 6:
                if (position == 0) {//商品售卖
                    openActivityAndCloseThis(RetailActivity.class);
                } else if (position == 1) {//商品退货
                    openActivityAndCloseThis(ReturnActivity.class);
                } else if (position == 2) {//商品报表
                    openActivityAndCloseThis(FormActivity.class);
                } else if (position == 3) {//设置
                    openActivityAndCloseThis(SettingActivity.class);
                }
                break;
            case 7:
                if (position == 0) {//门票核销
                    openActivityAndCloseThis(TicketVerificationActivity.class);
                } else if (position == 1) {//门票售卖
                    openActivityAndCloseThis(GetSaleTicketActivity.class);
                } else if (position == 2) {//商品售卖
                    openActivityAndCloseThis(RetailActivity.class);
                } else if (position == 3) {//商品退货
                    openActivityAndCloseThis(ReturnActivity.class);
                } else if (position == 4) {//商品报表
                    openActivityAndCloseThis(FormActivity.class);
                } else if (position == 5) {//设置
                    openActivityAndCloseThis(SettingActivity.class);
                }
                break;
            case 9:
                if (position == 0) {//门票核销
                    if (Constant.mainLineWriteOff) {
                        openActivityAndCloseThis(TicketVerificationActivity.class);
                    } else {
                        presenter.p_getLineWriteOff();
                    }

                } else if (position == 1) {//门票售卖
                    if (Constant.mainLineTicket) {
                        openActivityAndCloseThis(GetSaleTicketActivity.class);
                    } else {
                        presenter.p_getLineTicket();
                    }
                } else if (position == 2) {//消费支付
                    openActivityAndCloseThis(CunPayActivity.class);
                } else if (position == 3) {//设置
                    openActivityAndCloseThis(SettingActivity.class);
                }
                break;
            case 10:
                if (position == 0) {//门票核销
                    openActivityAndCloseThis(TicketVerificationActivity.class);
                } else if (position == 1) {//门票售卖
                    if (Constant.mainLineTicket) {
                        openActivityAndCloseThis(GetSaleTicketActivity.class);
                    } else {
                        presenter.p_getLineTicket();
                    }
                } else if (position == 2) {//散客兑票
                    openActivityAndCloseThis(DisOrderChangeTicketActivity_1.class);
                } else if (position == 3) {//团队兑票
                    openActivityAndCloseThis(TeamActivity.class);
                } else if (position == 4) {//消费支付
                    openActivityAndCloseThis(CunPayActivity.class);
                } else if (position == 5) {//设置
                    openActivityAndCloseThis(SettingActivity.class);
                }
                break;
            case 11://标准版
                if (position == 0) {//门票核销
                    openActivityAndCloseThis(TicketVerificationActivity.class);
                } else if (position == 1) {//门票售卖
                    openActivityAndCloseThis(GetSaleTicketActivity.class);
                } else if (position == 2) {//商品售卖
                    openActivityAndCloseThis(RetailActivity.class);
                } else if (position == 3) {//商品退货
                    openActivityAndCloseThis(ReturnActivity.class);
                } else if (position == 4) {//商品报表
                    openActivityAndCloseThis(FormActivity.class);
                } else if (position == 5) {//消费支付
                    openActivityAndCloseThis(CunPayActivity.class);
                } else if (position == 6) {//设置
                    openActivityAndCloseThis(SettingActivity.class);
                }
                break;
            case 12://增加版(可读身份证)
                if (position == 0) {//门票核销
                    openActivityAndCloseThis(TicketVerificationActivity.class);
                } else if (position == 1) {//门票售卖
                    openActivityAndCloseThis(GetSaleTicketActivity.class);
                } else if (position == 2) {//商品售卖
                    openActivityAndCloseThis(RetailActivity.class);
                } else if (position == 3) {//商品退货
                    openActivityAndCloseThis(ReturnActivity.class);
                } else if (position == 4) {//商品报表
                    openActivityAndCloseThis(FormActivity.class);
                } else if (position == 5) {//消费支付
                    openActivityAndCloseThis(CunPayActivity.class);
                } else if (position == 6) {//设置
                    openActivityAndCloseThis(SettingActivity.class);
                }
                break;
            case 13:
//                if (position == 0) {//门票核销
//                    openActivityAndCloseThis(TicketVerificationActivity.class);
//                }else
//                    if (position == 1) {//门票售卖
//                    openActivityAndCloseThis(GetSaleTicketActivity.class);
//                }
//                else
                if (position == 0) {//商品售卖
                    openActivityAndCloseThis(RetailActivity.class);
                } else if (position == 1) { //退货
                    openActivityAndCloseThis(ReturnActivity.class);
                } else if (position == 2) { //商品报表
                    openActivityAndCloseThis(FormActivity.class);
                } else if (position == 3) { //餐饮
                    openActivityAndCloseThis(New_FoodActivity.class);
                } else if (position == 4) { //餐饮退货
                    openActivityAndCloseThis(FoodReturnActivity.class);
                } else if (position == 5) { //餐饮报表
                    openActivityAndCloseThis(FoodFormActivity.class);
                } else if (position == 6) {//设置
                    openActivityAndCloseThis(SettingActivity.class);
                }
                break;
            default:
                break;
        }

    }

    /**
     * 根据版本号显示不同的模块名称和模块图标
     */
    public void versionTitle() {
        switch (Constant.versionNumber) {
            case 0:
                titleAndImg(Constant.title, Constant.imageId);
                Constant.modleNum = title.length;
                break;
            case 1:
                titleAndImg(Constant.title_1, Constant.imageId_1);
                Constant.modleNum = Constant.title_1.length;
                break;
            case 2:
                titleAndImg(Constant.title_2, Constant.imageId_2);
                Constant.modleNum = Constant.title_2.length;
                break;
            case 3:
                titleAndImg(Constant.title_3, Constant.imageId_3);
                Constant.modleNum = Constant.title_3.length;
                break;
            case 4:
                titleAndImg(Constant.title_4, Constant.imageId_4);
                Constant.modleNum = Constant.title_4.length;
                break;
            case 5:
                titleAndImg(Constant.title_5, Constant.imageId_5);
                Constant.modleNum = Constant.title_5.length;
                break;
            case 6:
                titleAndImg(Constant.title_6, Constant.imageId_6);
                Constant.modleNum = Constant.title_6.length;
                break;
            case 7:
                titleAndImg(Constant.title_7, Constant.imageId_7);
                Constant.modleNum = Constant.title_7.length;
                break;
            case 8:
                titleAndImg(Constant.title_8, Constant.imageId_8);
                Constant.modleNum = (Constant.title_8.length);
                break;
            case 9:
                titleAndImg(Constant.title_9, Constant.imageId_9);
                Constant.modleNum = Constant.title_9.length;
                break;
            case 10:
                titleAndImg(Constant.title_10, Constant.imageId_10);
                Constant.modleNum = Constant.title_10.length;
                break;
            case 11:
                titleAndImg(Constant.title_11, Constant.imageId_11);
                Constant.modleNum = Constant.title_11.length;
                break;
            case 12:
                titleAndImg(title_12, Constant.imageId_12);
                Constant.modleNum = Constant.title_12.length;
                break;
            case 13:
                titleAndImg(title_13, Constant.imageId_13);
                Constant.modleNum = Constant.title_13.length;
                break;
            default:
                break;
        }
    }

    /**
     * 显示模块的图标和文字
     *
     * @param title
     * @param imag
     */
    public void titleAndImg(String[] title, int[] imag) {
        for (int i = 0; i < title.length; i++) {
            ApplicationListBean applicationListBean = new ApplicationListBean();
            applicationListBean.setResId(imag[i]);
            applicationListBean.setTitleName(title[i]);
            applist_data.add(applicationListBean);
        }
    }


    /**
     * @param msg
     */
    @Override
    public void v_getGoodsInfoOK(String msg) {
        HttpUtils.exitProgressDialog(progressDialog);
        if (msg.equals("线下门票核销")) {
            Constant.mainLineWriteOff = true;
            openActivityAndCloseThis(TicketVerificationActivity.class);
        } else if (msg.equals("线下门票售卖")) {
            Constant.mainLineTicket = true;
            openActivityAndCloseThis(GetSaleTicketActivity.class);
        } else {
            if (Type.equals("商品售卖")) {
                openActivityAndCloseThis(RetailActivity.class);
            } else {
                openActivityAndCloseThis(FoodActivity.class);
            }

        }
    }

    /**
     * 获取APP更新方法成功回调
     * 通过调用Presenter来获取信息
     *
     * @param updataBeanList
     */
    @Override
    public void getV_UpdataApp_Ok(List<Updata_Bean.SuccessBean> updataBeanList) {
        Constant.isUpdata = false;
        try {
            //包的信息
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_CONFIGURATIONS);
            String packageName = packageInfo.versionName;
            for (int i = 0; i < updataBeanList.size(); i++) {
                DOWN_APK_URL = updataBeanList.get(i).getDownLoadURL();
                Constant.APK_NAME = updataBeanList.get(i).getVersionName();
                String android_v = updataBeanList.get(i).getNames();
                getServiceTime(updataBeanList.get(i).getEndData(), getTime());
                if (!TextUtils.isEmpty(android_v) && !packageName.equals(android_v)) {
                    if (!TextUtils.isEmpty(DOWN_APK_URL)) {
                        Bundle bundle = new Bundle();
                        bundle.putString("name", Constant.APK_NAME);
                        bundle.putString("url", OtherUtils.transition(DOWN_APK_URL));
                        openActivity(UpdateEditionActivity.class, bundle);
                    }
                }
            }
        } catch (Exception e) {

        }
    }

    /**
     * 获取app更新方法失败回调
     * 通过调用Presenter来获取信息
     *
     * @param err
     */
    @Override
    public void getV_UpdataApp_Err(String err) {
        Constant.isUpdata = false;
        relativeLayout.setAlpha((float) 0.3);
        relativeLayout.setVisibility(View.VISIBLE);
        data_tv.setText("查找服务失败，请检查服务链接");
    }


    //获取服务器时间 然后进行算出剩余天数
    public void getServiceTime(String EndData, String data) {
        try {
            SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");//格式化时间
            long result = (d.parse(EndData).getTime() - d.parse(data).getTime()) / 1000 / 60 / 60 / 24;
            if (result <= 4 && result >= 0) {
                relativeLayout.setAlpha((float) 0.3);
                data_tv.setText("注册有效期剩余" + result + "天");
                relativeLayout.setVisibility(View.VISIBLE);
            } else if (result < 0) {
                relativeLayout.setAlpha((float) 0.3);
                relativeLayout.setVisibility(View.VISIBLE);
                relativeLayout.setOnClickListener(null);  //只需如此设置，即可达到效果
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
