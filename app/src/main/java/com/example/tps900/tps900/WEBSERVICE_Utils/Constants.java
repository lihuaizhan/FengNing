package com.example.tps900.tps900.WEBSERVICE_Utils;


import com.example.tps900.tps900.Bean.GetTicketType_bean;
import com.example.tps900.tps900.Bean.GetTicket_Bean;

import java.util.List;

/**
 * 项目名称：GalasysSystem
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2017/2/9 14:57
 * 修改人：zxh
 * 修改时间：2017/2/9 14:57
 * 修改备注：
 */
public class Constants {
    //TODO 以下是设置页面的数据
    //IP地址
    public static String IP_ADDRESS = "223.223.179.20";
    //景区密钥
    public static String Scenicspot_Password = "7cd87fbcff9898417bb1f8d43a63e1";//fc655f5c734686d520aa50af7ef77e,7cd87fbcff9898417bb1f8d43a63e1
    //景区编码
    public static String Scenicspot_code = "fbh0001qybm";//,ZGWQXH
    //端口号
    public static String SOCKET = "8087";
    //设备编码
    public static String device_code = "fbh0001qybm-192.168.113.101";//fbh0001qybm-192.168.113.101,ZGWQXH-192.168.113.101
    //用户名
    public static String username = "";
    //密码
    public static String password = "";
    //微信--->001
    public static String WeChat = "";
    //支付宝--->002
    public static String Alipay = "";
    //现金--->003
    public static String Cash = "";
    //银行卡--->004
    public static String BankCard = "";
    //一卡通--->005
    public static String OneCard = "";
    //账户--->006
    public static String Account = "";
    //卡券--->007
    public static String CardVoucher = "";
    //门票类别list
    public static List<GetTicketType_bean.DataBean> ticketTypeList;
    //门票list
    public static List<GetTicket_Bean.DataBean> ticketList;
    //请求异常信息
    public static String ConnectErr;
    //折扣率
    public static double goodsDepositRate = 1.0;
    //商品登录是否获取成功
    public static boolean IsGoods = false;
    //餐饮登录是否获取成功
    public static boolean IsFoods = false;
}
