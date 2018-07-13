package com.example.tps900.tps900.WEBSERVICE_Utils;

import com.example.tps900.tps900.Bean.Login_bean;

import java.util.List;

/**
 * 项目名称：TPS613_WesternBrigade
 * 类名称：Login_Variate
 * 类描述：登录获取的静态变量
 * 创建人：zxh
 * 创建时间：2017/3/2 10:41
 * 修改人：zxh
 * 修改时间：2017/3/2 10:41
 * 修改备注：
 */

public class Login_Variate {

    /**
     * ParkId : 00000205
     * ParkName : 恒大海花岛乐园群
     * MachineId : 00000110
     * MachineName : 核销款台2
     * PrintTitle : 吉野家嘉华大厦店
     * PrintEnd : 这店有毒
     * ShopCode : 00000333
     * ShopName : 陆地公园
     * EmployeeCode : 00000289
     * EmployeeName : 梦幻世界管理员
     * PayType : [{"TypeCode":"005","TypeName":"一卡通"},{"TypeCode":"006","TypeName":"账户"},{"TypeCode":"001","TypeName":"微信"}]
     */
    public static String ParkId = "";
    public static String ParkName = "";
    public static String MachineId = "";
    public static String MachineName = "";
    public static String PrintTitle = "";
    public static String PrintEnd = "";
    public static String ShopCode = "";
    public static String ShopName = "";
    public static String EmployeeCode = "";
    public static String EmployeeName = "";
    /**
     * TypeCode : 005
     * TypeName : 一卡通
     */
    public static List<Login_bean.DataBean.PayTypeBean> PayType;

}
