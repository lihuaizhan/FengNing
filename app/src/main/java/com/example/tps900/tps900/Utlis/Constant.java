package com.example.tps900.tps900.Utlis;

import android.content.Context;
import android.content.Intent;

import com.example.tps900.tps900.Bean.DetailBean;
import com.example.tps900.tps900.Bean.GetDeviceTicketBean;
import com.example.tps900.tps900.Bean.GetTicket_Bean;
import com.example.tps900.tps900.Bean.Project_feeBean;
import com.example.tps900.tps900.Bean.TableInfoBean;
import com.example.tps900.tps900.Bean.TicketPrintBean;
import com.example.tps900.tps900.R;
import com.godfery.Sqlite.DBUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zxh on 2016/10/15.
 */

public class Constant {
    public static DBUtils dbUtils;
    public static String SetOrMain = "";
    //大平台核销地址
    public static String Y_IP_ADDRESS = "223.223.179.20:8084";
    //大平台景区编码
    public static String Y_NUMBER = "ZGWQXH";
    //大平台景区密钥
    public static String Y_SECRET = "fc655f5c734686d520aa50af7ef77e";
    //大平台款台ID
    public static String Y_deviceID = "192.168.131.101";
    //以下是设置页面的数据
    public static String IP_ADDRESS = "101.200.220.40:8003";
    public static String S_IP_ADDRESS = "101.200.220.40:8004";
    public static String S_deviceID = "";
    //配置参数
    //更新IP地址
    public static String UPdate_IP_ADDRESS = "gsapp.galasystrip.com:9000";
    public static String UPdate_APPName = "丰宁马镇";
    //扣费打印张数
    public static String CHECK_OUT = "1";
    //是否连续刷卡
    public static String IsCtnCredirCard = "";
    //检票终端
    public static String devicename = "PDA01";
    // 售票终端名称
    public static String S_devicename = "TVM01";
    //线下商品库存
    public static double GoodsInStock = 0.0;
    public static String DOWN_APK_URL = "";
    public static String APK_NAME = "";
    //获取时间----------计算时间差=4
    public static String Time = "";
    public static Context context;
    //TODO 销售终端信息
    //操作员 ID
    public static String emp_id = "";
    //销售终端ID
    public static String ter_id = "";
    public static String Code = "";
    //TODO 支付方式
    //一卡通
    public static int OneCard = 0;
    //人民币
    public static int CaSh = 0;
    //微信
    public static int WeChat = 0;
    //支付宝
    public static int Alipay = 0;
    //设备ID
    public static int NDEVICEID = 0;
    //设备名称
    public static String SDEVICENAME = "";
    //设备IP
    public static String SDEVICEIP = "";
    //景区ID
    public static int NPARKID = 0;
    //景区名称
    public static String SPARKNAME = "";

    public static ArrayList<GetDeviceTicketBean.TicketsBean> getDeviceList;
    //核销门票补打上笔
    public static List<TicketPrintBean> TicketPintList = new ArrayList<>();
    public static List<GetTicket_Bean.DataBean> XS_PrintList = null;
    public static double Printpaymoney = 0.0;
    public static double PrintYMoney = 0.0;
    //补打线上销售门票上笔
    public static Project_feeBean PrintBean;
    public static double Price = 0.00;
    //带团支付标记
    public static boolean FLAG = false;
    //第三方会员参数配置
    public static  String OpenId="BF9BEDAD739E11E7BEA2001018640E28";
    //
    public static String Secret="9ZJ2AK";
    //IP地址
    public static String ADDRESS = "101.200.220.40:8085";
    //商品终端ID
    public static int TERMINALID = 0;
    //餐饮终端ID
    public static int Food_TERMINALID = 0;
    //商品终端名称
    public static String TERMINALNAME = "TESTLucky";
    //餐饮终端名称
    public static String Food_TERMINALNAME = "TESTLucky";
    //一卡通终端名称
    public static int ONECARD_TERMINALNAME = 1000;
    //商品营业点ID
    public static int ZONEID;
    //餐饮营业点ID
    public static int Food_ZONEID;
    //商品营业点名称
    public static String ZONENAME;
    //餐饮营业点名称
    public static String Food_ZONENAME;
    //收银员ID
    public static String EMPID;
    //收银员姓名
    public static String EMPNAME;
    //线上
    public static ArrayList<GetTicket_Bean.DataBean> BEANS_XS = new ArrayList<>();
    public static ArrayList<TableInfoBean.DtBean> BEANS_Table = new ArrayList<>();
    //线下
    public static ArrayList<DetailBean.DtBean> BEANS = new ArrayList<>();
    //餐饮价格
    public static String FoodMoney;
    //桌台人数
    public static String TablePeople;

    //用户名
    public static String USERNAME;
    //一卡通接口地址
    public static String OneCardInterface = "101.200.220.40:8038";

    //***************************************根据以下判断请求接口***********************
    //判断是否有登录界面
    public static boolean isLogin = false;
    //线上门票
    public static boolean onLineTicket = false;
    //线下商品
    public static boolean lineGoods = false;
    //线下门票
    public static boolean lineTicket = false;
    //线下核销
    public static boolean lineWriteOff = false;

    //主界面线下门票 判断是否已经请求过接口
    public static boolean mainLineTicket = false;
    //主界面线下核销 判断是否已经请求过接口
    public static boolean mainLineWriteOff = false;
    //判断是否有身份证
    public static boolean isIdCard = true;

    /**
     * 版本号	功能分类	设备说明
     * 版本1	售票+门票核销	标准版
     * 版本2	售票+OTA兑票（散客、团队）	增加版（可读取身份证）
     * 版本3	OTA兑票（散客、团队）+门票核销	增加版（可读取身份证）
     * 版本4	售票+OTA兑票（散客、团队）+门票核销	增加版（可读取身份证）
     * 版本5	一卡通消费（扣费、扣次、一卡通余额查询）	标准版
     * 版本6	商品售卖+商品退货+商品报表	标准版
     * 版本7	售票+门票核销+商品售卖+商品退货+商品报表	标准版
     * 版本8	售票+OTA兑票（散客、团队）+门票核销+商品售卖+商品退货+商品报表	增加版（可读取身份证）
     * 版本9	售票+门票核销+一卡通消费（扣费、扣次、一卡通余额查询）	标准版
     * 版本10	售票+OTA兑票（散客、团队）+门票核销+一卡通消费（扣费、扣次、一卡通余额查询）	增加版（可读取身份证）
     * 版本11	售票+门票核销+商品售卖+商品退货+商品报表+一卡通消费（扣费、扣次、一卡通余额查询）	标准版
     * 版本12	售票+OTA兑票（散客、团队）+门票核销+商品售卖+商品退货+商品报表+一卡通消费（扣费、扣次、一卡通余额查询）	增加版（可读取身份证）
     */
    /**
     * 是否有登录如果true则没有登录页面
     * Constant.isLogin = false;
     * //是否需要线下商品售卖 false则无
     * Constant.onLineTicket = true;
     * //是否有身份证如果false则没有身份证模块
     * Constant.isIdCard = true;
     * //功能版本
     * Constant.versionNumber = 0;
     * //启动页面
     * context.startActivity(new Intent(context, com.example.tps900.tps900.activity.LoginActivity.class));
     *
     * @param context    上下文
     * @param versionNum 版本号
     */
    public static void VersionModle(Context context, int versionNum) {
        switch (versionNum) {
            case 0:
                //是否有登录如果true则没有登录页面
                Constant.isLogin = false;
                //是否需要线下商品售卖 false则无
                Constant.onLineTicket = true;
                //是否有身份证如果false则没有身份证模块
                Constant.isIdCard = true;
                //功能版本
                Constant.versionNumber = 0;
                //启动页面
                context.startActivity(new Intent(context, com.example.tps900.tps900.activity.LoginActivity.class));
                break;
            //售票+门票核销	标准版
            case 1:
                //是否有登录如果true则没有登录页面
                Constant.isLogin = true;
                //是否需要线下商品售卖 false则无
                Constant.onLineTicket = false;
                //是否有身份证如果false则没有身份证模块
                Constant.isIdCard = false;
                //功能版本
                Constant.versionNumber = 1;
                //启动页面
                context.startActivity(new Intent(context, com.example.tps900.tps900.activity.MainActivity.class));
                break;
            //	售票+OTA兑票（散客、团队）	增加版（可读取身份证）
            case 2:
                //是否有登录如果true则没有登录页面
                Constant.isLogin = true;
                //是否需要线下商品售卖 false则无
                Constant.onLineTicket = false;
                //是否有身份证如果false则没有身份证模块
                Constant.isIdCard = true;
                //功能版本
                Constant.versionNumber = 2;
                //启动页面
                context.startActivity(new Intent(context, com.example.tps900.tps900.activity.MainActivity.class));
                break;
            //OTA兑票（散客、团队）+门票核销	增加版（可读取身份证）
            case 3:
                //是否有登录如果true则没有登录页面
                Constant.isLogin = true;
                //是否需要线下商品售卖 false则无
                Constant.onLineTicket = false;
                //是否有身份证如果false则没有身份证模块
                Constant.isIdCard = true;
                //功能版本
                Constant.versionNumber = 3;
                //启动页面
                context.startActivity(new Intent(context, com.example.tps900.tps900.activity.MainActivity.class));
                break;
            //售票+OTA兑票（散客、团队）+门票核销	增加版（可读取身份证）
            case 4:
                //是否有登录如果true则没有登录页面
                Constant.isLogin = true;
                //是否需要线下商品售卖 false则无
                Constant.onLineTicket = false;
                //是否有身份证如果false则没有身份证模块
                Constant.isIdCard = true;
                //功能版本
                Constant.versionNumber = 4;
                //启动页面
                context.startActivity(new Intent(context, com.example.tps900.tps900.activity.MainActivity.class));
                break;
            //一卡通消费（扣费、扣次、一卡通余额查询）	标准版
            case 5:
                //是否有登录如果true则没有登录页面
                Constant.isLogin = true;
                //是否需要线下商品售卖 false则无
                Constant.onLineTicket = false;
                //是否有身份证如果false则没有身份证模块
                Constant.isIdCard = false;
                //功能版本
                Constant.versionNumber = 5;
                //启动页面
                context.startActivity(new Intent(context, com.example.tps900.tps900.activity.MainActivity.class));
                break;
            //商品售卖+商品退货+商品报表	标准版
            case 6:
                //是否有登录如果true则没有登录页面
                Constant.isLogin = false;
                //是否需要线下商品售卖 false则无
                Constant.onLineTicket = false;
                //是否有身份证如果false则没有身份证模块
                Constant.isIdCard = false;
                //功能版本
                Constant.versionNumber = 6;
                //启动页面
                context.startActivity(new Intent(context, com.example.tps900.tps900.activity.LoginActivity.class));
                break;
            //售票+门票核销+商品售卖+商品退货+商品报表	标准版
            case 7:
                //门票售卖
                Constant.lineTicket = true;
                //门票核销
                Constant.lineWriteOff = true;
                //是否有登录如果true则没有登录页面
                Constant.isLogin = false;
                //是否需要线下商品售卖  true
                Constant.onLineTicket = false;
                //是否有身份证如果false则没有身份证模块
                Constant.isIdCard = false;
                //功能版本
                Constant.versionNumber = 7;
                //启动页面
                context.startActivity(new Intent(context, com.example.tps900.tps900.activity.LoginActivity.class));
                break;
            //售票+OTA兑票（散客、团队）+门票核销+商品售卖+商品退货+商品报表	增加版（可读取身份证）
            case 8:
                //门票售卖
                Constant.lineTicket = true;
                //是否有登录如果true则没有登录页面
                Constant.isLogin = false;
                //是否需要线下商品售卖  true
                Constant.onLineTicket = false;
                //是否有身份证如果false则没有身份证模块
                Constant.isIdCard = true;
                //功能版本
                Constant.versionNumber = 8;
                //启动页面
                context.startActivity(new Intent(context, com.example.tps900.tps900.activity.LoginActivity.class));
                break;
            //售票+门票核销+一卡通消费（扣费、扣次、一卡通余额查询）	标准版
            case 9:
                //是否有登录如果true则没有登录页面
                Constant.isLogin = true;
                //是否需要线下商品售卖  true
                Constant.onLineTicket = false;
                //是否有身份证如果false则没有身份证模块
                Constant.isIdCard = false;
                //功能版本
                Constant.versionNumber = 9;
                //启动页面
                context.startActivity(new Intent(context, com.example.tps900.tps900.activity.MainActivity.class));
                break;
            //售票+OTA兑票（散客、团队）+门票核销+一卡通消费（扣费、扣次、一卡通余额查询）	增加版（可读取身份证）
            case 10:
                //是否有登录如果true则没有登录页面
                Constant.isLogin = true;
                //是否需要线下商品售卖  true
                Constant.onLineTicket = false;
                //是否有身份证如果false则没有身份证模块
                Constant.isIdCard = true;
                //功能版本
                Constant.versionNumber = 10;
                //启动页面
                context.startActivity(new Intent(context, com.example.tps900.tps900.activity.MainActivity.class));
                break;
            //售票+门票核销+商品售卖+商品退货+商品报表+一卡通消费（扣费、扣次、一卡通余额查询）	标准版
            case 11:
                //线下售卖
                Constant.lineTicket = true;
                //线下核销
                Constant.lineWriteOff = true;
                //是否有登录如果true则没有登录页面
                Constant.isLogin = false;
                //是否需要线下商品售卖  true
                Constant.onLineTicket = false;
                //是否有身份证如果false则没有身份证模块
                Constant.isIdCard = false;
                //功能版本
                Constant.versionNumber = 11;
                //启动页面
                context.startActivity(new Intent(context, com.example.tps900.tps900.activity.LoginActivity.class));
                break;
            //售票+OTA兑票（散客、团队）+门票核销+商品售卖+商品退货+商品报表+一卡通消费（扣费、扣次、一卡通余额查询）	增加版（可读取身份证）
            case 12:
                //线下售卖
                Constant.lineTicket = true;
                //线下核销
                Constant.lineWriteOff = true;
                //是否有登录如果true则没有登录页面
                Constant.isLogin = false;
                //是否需要线下商品售卖  true
                Constant.onLineTicket = false;
                //是否有身份证如果false则没有身份证模块
                Constant.isIdCard = true;
                //功能版本
                Constant.versionNumber = 12;
                //启动页面
                context.startActivity(new Intent(context, com.example.tps900.tps900.activity.LoginActivity.class));
                break;
            case 13:
                //线下售卖
                Constant.lineTicket = true;
                //线下核销
                Constant.lineWriteOff = true;
                //是否有登录如果true则没有登录页面
                Constant.isLogin = false;
                //是否需要线下商品售卖  true
                Constant.onLineTicket = false;
                //是否有身份证如果false则没有身份证模块
                Constant.isIdCard = true;
                //功能版本
                Constant.versionNumber = 13;
                //启动页面
                context.startActivity(new Intent(context, com.example.tps900.tps900.activity.LoginActivity.class));
                break;
            default:
                break;
        }
    }

    public static int versionNumber = 13;
    /**
     * 模块数量
     */
    public static int modleNum = 0;
    //列表selector
    public static int[] imageId = new int[]{
            R.drawable.btn_ticket_selector,
            R.drawable.btn_code_selector,
            R.drawable.btn_year_selector,
            R.drawable.btn_goods_selector,
            R.drawable.btn_food_selector,
            R.drawable.btn_project_selector,
            R.drawable.btn_print_selector,
            R.drawable.btn_tourform_selector,
            R.drawable.btn_goods_refund_selector,
            R.drawable.btn_payment_selector,
            R.drawable.btn_ticket_d_selector,
            R.drawable.btn_toor_selector,
            R.drawable.btn_project_selector,
            R.drawable.btn_vip_selector,
            R.drawable.btn_onecard_selector,
            R.drawable.btn_setting_selector
    };
    //图标书名
    public static String[] title = new String[]{
            "门票核销",
            "次卡核销",
            "年卡核销",
            "商品售卖",
            "餐饮售卖",
            "门票售卖",
            "补打上笔",
            "查询报表",
            "商品退货",
            "消费支付",
            "散客兑票",
            "团队兑票",
            "线上门票售卖",
            "线上会员卡查询",
            "线下一卡通查询",
            "系统设置"};
    public static int[] imageId_13 = new int[]{
//            R.drawable.btn_ticket_selector,
//            R.drawable.btn_project_selector,
            R.drawable.btn_goods_selector,
            R.drawable.btn_goods_refund_selector,
            R.drawable.btn_tourform_selector,
            R.drawable.btn_food_selector,
            R.drawable.btn_rtfood_selector,
            R.drawable.btn_food_reports_selector,
            R.drawable.btn_setting_selector
    };
    //图标书名
    public static String[] title_13 = new String[]{
//            "门票核销",
//            "门票售卖",
            "商品售卖",
            "商品退货",
            "商品报表",
            "餐饮售卖",
            "餐饮退货",
            "餐饮报表",
            "系统设置"};
    //*****************************************版本1	售票+门票核销	标准版
    public static int[] imageId_1 = new int[]{
            R.drawable.btn_ticket_selector,
            R.drawable.btn_project_selector,
            R.drawable.btn_setting_selector
    };
    public static String[] title_1 = new String[]{
            "门票核销",
            "门票售卖",
            "系统设置"};
    //*****************************************版本2	售票+OTA兑票（散客、团队）	增加版（可读取身份证）取身份证）
    public static int[] imageId_2 = new int[]{
            R.drawable.btn_project_selector,
            R.drawable.btn_ticket_d_selector,
            R.drawable.btn_toor_selector,
            R.drawable.btn_setting_selector
    };
    public static String[] title_2 = new String[]{
            "门票售卖",
            "散客兑票",
            "团队兑票",
            "系统设置"};
    //*****************************************版本3	OTA兑票（散客、团队）+门票核销	增加版（可读取身份证）
    public static int[] imageId_3 = new int[]{
            R.drawable.btn_ticket_selector,
            R.drawable.btn_ticket_d_selector,
            R.drawable.btn_toor_selector,
            R.drawable.btn_setting_selector
    };
    public static String[] title_3 = new String[]{
            "门票核销",
            "散客兑票",
            "团队兑票",
            "系统设置"};
    //*****************************************版本4	售票+OTA兑票（散客、团队）+门票核销	增加版（可读取身份证）
    public static int[] imageId_4 = new int[]{
            R.drawable.btn_ticket_selector,
            R.drawable.btn_project_selector,
            R.drawable.btn_ticket_d_selector,
            R.drawable.btn_toor_selector,
            R.drawable.btn_setting_selector
    };
    public static String[] title_4 = new String[]{
            "门票核销",
            "门票售卖",
            "散客兑票",
            "团队兑票",
            "系统设置"};
    //*****************************************版本5	一卡通消费（扣费、扣次、一卡通余额查询）	标准版
    public static int[] imageId_5 = new int[]{
            R.drawable.btn_payment_selector,
            R.drawable.btn_setting_selector
    };
    public static String[] title_5 = new String[]{
            "消费支付",
            "系统设置"};
    //*****************************************版本6	商品售卖+商品退货+商品报表	标准版
    public static int[] imageId_6 = new int[]{
            R.drawable.btn_goods_selector,
            R.drawable.btn_tourform_selector,
            R.drawable.btn_goods_refund_selector,
            R.drawable.btn_setting_selector
    };
    public static String[] title_6 = new String[]{
            "商品售卖",
            "查询报表",
            "商品退货",
            "系统设置"};
    //*****************************************版本7	售票+门票核销+商品售卖+商品退货+商品报表	标准版
    public static int[] imageId_7 = new int[]{
            R.drawable.btn_ticket_selector,
            R.drawable.btn_project_selector,
            R.drawable.btn_goods_selector,
            R.drawable.btn_tourform_selector,
            R.drawable.btn_goods_refund_selector,
            R.drawable.btn_setting_selector
    };
    public static String[] title_7 = new String[]{
            "门票核销",
            "门票售卖",
            "商品售卖",
            "查询报表",
            "商品退货",
            "系统设置"};
    //*****************************************版本8	售票+OTA兑票（散客、团队）+门票核销+商品售卖+商品退货+商品报表	增加版（可读取身份证）商品退货+商品报表	增加版（可读取身份证）
    public static int[] imageId_8 = new int[]{
            R.drawable.btn_ticket_selector,
            R.drawable.btn_project_selector,
            R.drawable.btn_goods_selector,
            R.drawable.btn_tourform_selector,
            R.drawable.btn_goods_refund_selector,
            R.drawable.btn_ticket_d_selector,
            R.drawable.btn_toor_selector,
            R.drawable.btn_setting_selector
    };
    public static String[] title_8 = new String[]{
            "门票核销",
            "门票售卖",
            "商品售卖",
            "查询报表",
            "商品退货",
            "散客兑票",
            "团队兑票",
            "系统设置"};
    //*************************************版本9	售票+门票核销+一卡通消费（扣费、扣次、一卡通余额查询）	标准版
    public static int[] imageId_9 = new int[]{
            R.drawable.btn_ticket_selector,
            R.drawable.btn_project_selector,
            R.drawable.btn_payment_selector,
            R.drawable.btn_setting_selector
    };
    public static String[] title_9 = new String[]{
            "门票核销",
            "门票售卖",
            "消费支付",
            "系统设置"};

    //*****************************************版本10	售票+OTA兑票（散客、团队）+门票核销+一卡通消费（扣费、扣次、一卡通余额查询）	增加版（可读取身份证）
    public static int[] imageId_10 = new int[]{
            R.drawable.btn_ticket_selector,
            R.drawable.btn_project_selector,
            R.drawable.btn_ticket_d_selector,
            R.drawable.btn_toor_selector,
            R.drawable.btn_payment_selector,
            R.drawable.btn_setting_selector
    };
    public static String[] title_10 = new String[]{
            "门票核销",
            "门票售卖",
            "散客兑票",
            "团队兑票",
            "消费支付",
            "系统设置"};

    //*****************************************版本11	售票+门票核销+商品售卖+商品退货+商品报表+一卡通消费（扣费、扣次、一卡通余额查询）标准版
    public static int[] imageId_11 = new int[]{
            R.drawable.btn_ticket_selector,
            R.drawable.btn_project_selector,
            R.drawable.btn_goods_selector,
            R.drawable.btn_tourform_selector,
            R.drawable.btn_goods_refund_selector,
            R.drawable.btn_payment_selector,
            R.drawable.btn_setting_selector
    };
    public static String[] title_11 = new String[]{
            "门票核销",
            "门票售卖",
            "商品售卖",
            "查询报表",
            "商品退货",
            "消费支付",
            "系统设置"};
    //*****************************************************版本12*售票+OTA兑票（散客、团队）+门票核销+商品售卖+商品退货+商品报表+一卡通消费（扣费、扣次、一卡通余额查询）	增加版（可读取身份证）
    public static int[] imageId_12 = new int[]{
            R.drawable.btn_ticket_selector,
            R.drawable.btn_goods_selector,
            R.drawable.btn_project_selector,
            R.drawable.btn_tourform_selector,
            R.drawable.btn_goods_refund_selector,
            R.drawable.btn_payment_selector,
            R.drawable.btn_ticket_d_selector,
            R.drawable.btn_toor_selector,
            R.drawable.btn_setting_selector
    };
    //图标书名
    public static String[] title_12 = new String[]{
            "门票核销",
            "商品售卖",
            "门票售卖",
            "查询报表",
            "商品退货",
            "消费支付",
            "散客兑票",
            "团队兑票",
            "系统设置"
    };
    /**
     * 是否已经更新过
     */
    public static boolean isUpdata = true;
}
