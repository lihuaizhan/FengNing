package com.example.tps900.tps900.WEBSERVICE_Utils;

import android.content.ContentValues;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.tps900.tps900.Bean.CreateOrder_Bean;
import com.example.tps900.tps900.Bean.GetLevelInfo_Bean;
import com.example.tps900.tps900.Bean.GetOderID;
import com.example.tps900.tps900.Bean.GetTicketType_bean;
import com.example.tps900.tps900.Bean.GetTicket_Bean;
import com.example.tps900.tps900.Bean.GetVipInfoTogether_been;
import com.example.tps900.tps900.Bean.GetVipRate_Bean;
import com.example.tps900.tps900.Bean.Login_bean;
import com.example.tps900.tps900.Bean.PayOrder_Bean;
import com.godfery.Sqlite.DBUtils;

import java.util.ArrayList;
import java.util.List;

import static com.example.tps900.tps900.WEBSERVICE_Utils.GsWebServiceUtils.GetOrderNos;
import static com.example.tps900.tps900.WEBSERVICE_Utils.Login_Variate.MachineId;

/**
 * 项目名称：TPS613_WesternBrigade
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2017/3/2 14:15
 * 修改人：zxh
 * 修改时间：2017/3/2 14:15
 * 修改备注：
 */

public class JsonUtils {
    private static String TAG = "JsonUtils";

    /**
     * @author zxh
     * created at 2017/9/26 10:40
     * 方法名: 登录方法
     * 方法说明: 登录获取到景区的一系列信息包括支付方式
     */
    public static boolean Login_Json(String UserName, String Password) {
        try {
            Login_bean parseObject = JSON.parseObject(GsWebServiceUtils.Login(UserName, Password), Login_bean.class);
            if (!parseObject .Success) {
                Log.e("下载异常", parseObject.getMessage().toString());
                Constants.ConnectErr = parseObject.Message;
                return false;
            } else {
                Login_Variate.ParkId = parseObject.getData().getParkId();
            }
            Login_Variate.ParkName = parseObject.getData().getParkName();
            MachineId = parseObject.getData().getMachineId();
            Login_Variate.MachineName = parseObject.getData().getMachineName();
            Login_Variate.PrintTitle = parseObject.getData().getPrintTitle();
            Login_Variate.PrintEnd = parseObject.getData().getPrintEnd();
            Login_Variate.ShopCode = parseObject.getData().getShopCode();
            Login_Variate.ShopName = parseObject.getData().getShopName();
            Login_Variate.EmployeeCode = parseObject.getData().getEmployeeCode();
            Login_Variate.EmployeeName = parseObject.getData().getEmployeeName();
            Login_Variate.PayType = parseObject.getData().getPayType();
            for (int i = 0; i < Login_Variate.PayType.size(); i++) {
                Login_Variate.PayType.get(i).getTypeCode();//收款号码
                Login_Variate.PayType.get(i).getTypeName();//收款方式
                Log.e("编码-------------------->", Login_Variate.PayType.get(i).getTypeCode());

                switch (Login_Variate.PayType.get(i).getTypeCode()) {
                    case "001":
                        Constants.WeChat = "001";
                        break;
                    case "002":
                        Constants.Alipay = "002";
                        break;
                    case "003":
                        Constants.Cash = "003";
                        break;
                    case "004":
                        Constants.BankCard = "004";
                        break;
                    case "005":
                        Constants.OneCard = "005";
                        break;
                    case "006":
                        Constants.Account = "006";
                        break;
                    case "007":
                        Constants.CardVoucher = "007";
                        break;
                    default:
                        break;
                }
            }
            return true;
        } catch (Exception e) {
            Constants.ConnectErr = "请检查配置信息/网络连接";
            return false;
        }

    }

    /**
     * @author zxh
     * created at 2017/9/26 10:39
     * 方法名: 获取门票类别方法
     * 方法说明:将获取到的门票类别存到  Constants.ticketTypeList=data;
     */
    public static boolean GetTicketType_json(String orderNo, String ProductType ) {
        try {
            GetTicketType_bean parseObject = JSON.parseObject(GsWebServiceUtils.GetTicketType(orderNo, ProductType), GetTicketType_bean.class);
            if (parseObject.Success){
                List<GetTicketType_bean.DataBean> data = parseObject.getData();
                if (data == null || data.isEmpty() || data.size() == 0) {
                    Constants.ConnectErr ="门票类型数据为空";
                    return false;
                } else {
                    Constants.ticketTypeList = data;
                    for (int i = 0; i < data.size(); i++) {
                        GetTicketType_bean.DataBean dtatabean = new GetTicketType_bean.DataBean();
                        dtatabean.TypeName = data.get(i).getTypeName();
                        dtatabean.ProductType = data.get(i).getProductType();
                        dtatabean.TypeId = data.get(i).getTypeId();
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("TypeId", data.get(i).getTypeId());
                        contentValues.put("TypeName", data.get(i).getTypeName());
                        contentValues.put("ProductType", data.get(i).getProductType());
                        DBUtils.Insert("TicketType", contentValues);

                    }
                    return true;
                }
            }else{
                Constants.ConnectErr = parseObject.Message;
                return false;
            }


        } catch (Exception e) {
            Constants.ConnectErr = "请检查配置信息/网络连接";
            return false;
        }
    }

    /**
     * @author zxh
     * created at 2017/9/26 10:39
     * 方法名: 获取门票方法
     * 方法说明: 将获取的门票list存到 Constants.ticketList=data;
     */
    public static boolean GetTicket_json(String MachineId, String ProductType, String ProductId ) {//"00000129", "null","null"
        String getTickets = GsWebServiceUtils.GetTickets(MachineId, ProductType, ProductId);
        if (!"err".equals(getTickets)){
            GetTicket_Bean parseObject = JSON.parseObject(getTickets, GetTicket_Bean.class);
            if (parseObject.isSuccess()) {
                List<GetTicket_Bean.DataBean> data = parseObject.getData();
                if (data == null || data.isEmpty() || data.size() == 0) {
                    Constants.ConnectErr = "门票数据为空";
                    return false;
                } else {
                    Constants.ticketList = data;
                    for (int i = 0; i < data.size(); i++) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("ProductId", data.get(i).getProductId());
                        contentValues.put("ProductName", data.get(i).getProductName());
                        contentValues.put("ProductPrice", data.get(i).getProductPrice());
                        contentValues.put("ProductCode", (data.get(i).getProductCode()));
                        contentValues.put("ProductType", data.get(i).getProductType());
                        contentValues.put("PrintName", data.get(i).getPrintName());
                        contentValues.put("PrintPrice", data.get(i).getPrintPrice());
                        contentValues.put("Remark", data.get(i).getRemark());
                        contentValues.put("QuoteId", data.get(i).getQuoteId());
                        contentValues.put("TypeId", data.get(i).getTypeId());
                        int tickets = DBUtils.Insert("Tickets", contentValues);
                        Log.e("数据库状态","------>"+tickets);
                    }
                    return true;
                }
            } else {
                Constants.ConnectErr = parseObject.Message;
                return false;
            }
        }else{
            Constants.ConnectErr = "请求数据失败!!!请检查网络或者配置";
            return false;
        }

    }
    public static String CreateOrder(String Detailes,

                                     String CustName, String CustPhone,
                                     String CustCardNo, String OperatorId,
                                     String OrderId, String vipid, String payCode
    ) {//"00000129", "null","null"
        String Order = "";
        /**
         *
         */


        CreateOrder_Bean parseObject =JSON. parseObject(GsWebServiceUtils.
                CreateOrder(Detailes, CustName, CustPhone, CustCardNo, OperatorId,
                        OrderId, vipid, payCode
                ), CreateOrder_Bean.class);


        if (parseObject == null) {
            Order = "false";
            return Order;
        } else {
            if (parseObject.isSuccess() == true) {
                Order = parseObject.getData().getOrderId();
                Log.e("ORDER----OK", Order);
                return Order;
            } else {
                Order = "false";

                Log.e("ORDER----err", Order);
            }
        }
        return Order;
    }
    public static String JsonOrderID() {
        GetOderID getOderID = JSON.parseObject(GetOrderNos(), GetOderID.class);
        String orderId = "";
        if (getOderID != null && getOderID.isSuccess() == true) {
            orderId = getOderID.Data.getOrderId();
            return orderId;
        } else {
            return "err";
        }
    }

    //会员卡信息返回json
    public static List<GetVipInfoTogether_been.DataBean> GetMember(String str) {
        GetVipInfoTogether_been getVipInfo_bean = null;
        List<GetVipInfoTogether_been.DataBean> data = null;
        String getvipinfo = GsWebServiceUtils.GetVipInfoTogether(str);
        if (null != getvipinfo) {
            getVipInfo_bean = JSON.parseObject(getvipinfo, GetVipInfoTogether_been.class);
            if (getVipInfo_bean.isSuccess()) {
                data = getVipInfo_bean.getData();
                if (data != null &&!data.isEmpty()) {
                    return data;
                } else {
                    System.out.println("返回集合为空!");
                    return null;
                }
            } else {
                System.out.println("返回值为false");
            }
        } else {
            System.out.println("未连接服务器！");
            return null;
        }
        return data;
    }

    /**
     * 获取门票卡级解析的数据
     * 存入数据库
     *
     * @rturn
     */
    public static String GetLevelInfo_json() {
        String getLevelInfo_json = "";
        String getLevelInfo = GsWebServiceUtils.GetLevelInfo();
        if ("err".equals(getLevelInfo)) {
            getLevelInfo_json = "err";
        } else {
            GetLevelInfo_Bean parseObject = JSON.parseObject(getLevelInfo, GetLevelInfo_Bean.class);
            if (parseObject.isSuccess() ) {
                List<GetLevelInfo_Bean.DataBean> data = parseObject.getData();
                if (data == null || data.isEmpty() || data.size() == 0) {
                    getLevelInfo_json = "LevelInfoErr";
                } else {
                    String getVipRate_json = GetVipRate_json( data);
                    if ("OK".equals(getVipRate_json)) {
                        getLevelInfo_json = "OK";
                    } else {
                        getLevelInfo_json = "err";
                    }

                }
            } else {
                getLevelInfo_json = "err";
            }
        }

        return getLevelInfo_json;
    }
    public static String GetVipRate_json( List<GetLevelInfo_Bean.DataBean> levedata) {
        String getLevelInfo_json = "";
        String getVipRate = GsWebServiceUtils.GetVipRate();
        if ("err".equals(getVipRate)) {
            getLevelInfo_json = "err";
        } else {
            GetVipRate_Bean parseObject = JSON.parseObject(getVipRate, GetVipRate_Bean.class);
            if (parseObject.isSuccess() == true) {
                List<GetVipRate_Bean.DataBean> data = parseObject.getData();
                if (data == null || data.isEmpty() || data.size() == 0) {
                    getLevelInfo_json = "LevelInfoErr";
                } else {
                    for (int i = 0; i < levedata.size(); i++) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("LevelId", levedata.get(i).getLevelId());
                        contentValues.put("LevelName", levedata.get(i).getLevelName());
                        contentValues.put("IsStored", levedata.get(i).getIsStored());
                        contentValues.put("IsVipMark", (levedata.get(i).getIsVipMark()));
                        contentValues.put("IsAdjust", levedata.get(i).getIsAdJust());
                        contentValues.put("DepositAmount", levedata.get(i).getDepositAmount());
                        contentValues.put("DepositItid", levedata.get(i).getDepositItid());
                        contentValues.put("RentAmount", levedata.get(i).getRentAmount());
                        contentValues.put("RentItid", levedata.get(i).getRentItid());
                        contentValues.put("ReissueAmount", levedata.get(i).getReissueAmount());

                        for (int j = 0; j < data.size(); j++) {
                            if (levedata.get(i).getLevelId().equals(data.get(j).getLevelId())) {
                                contentValues.put("INTEGRALRATE", data.get(i).getINTEGRALRATE());
                                contentValues.put("DEPOSITRATE", data.get(i).getDEPOSITRATE());
                            }
                        }
                        int LevelInfo = DBUtils.Insert("LevelInfo", contentValues);
                        Log.e("数据库状态", "LevelInfo------>" + LevelInfo);
                    }

                    getLevelInfo_json = "OK";

                }
            } else {
                getLevelInfo_json = "err";
            }
        }

        return getLevelInfo_json;
    }
    public static List<PayOrder_Bean.DataBean> PayOrder_Json(String Detailes , String price1,String OderID ) {
        List<PayOrder_Bean.DataBean> PayOrder_Json_list = null;
        PayOrder_Bean parseObject =JSON. parseObject
                (GsWebServiceUtils.PayOrder(Detailes, "null", "null", "null", "null",OderID), PayOrder_Bean.class);
        if (parseObject == null || TextUtils.isEmpty(price1)) {
            return null;
        } else {
            if (parseObject.isSuccess() == true) {
                PayOrder_Json_list = new ArrayList<>();
                List<PayOrder_Bean.DataBean> data = parseObject.getData();
                for (int i = 0; i < data.size(); i++) {
                    PayOrder_Bean.DataBean dataBean = new PayOrder_Bean.DataBean();
                    dataBean.OrderId = data.get(i).getOrderId();
                    dataBean.OrderDlId = Login_Variate.MachineId;
                    dataBean.ProductId = data.get(i).getProductId();
                    dataBean.StartPlayDate = data.get(i).getStartPlayDate();
                    if (data.get(i).getEndPlayDate() != null) {
                        dataBean.EndPlayDate = data.get(i).getEndPlayDate();
                    } else {
                        dataBean.EndPlayDate = "";
                    }

                    if (data.get(i).getEcode() != null) {
                        dataBean.Ecode = data.get(i).getEcode();
                    } else {
                        dataBean.Ecode = "";
                    }
                    PayOrder_Json_list.add(dataBean);
                }
                return PayOrder_Json_list;
            }
            return PayOrder_Json_list;
        }

    }
}