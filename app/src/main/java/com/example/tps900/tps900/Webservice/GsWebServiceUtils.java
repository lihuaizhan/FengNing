package com.example.tps900.tps900.Webservice;


import android.content.ContentValues;
import android.util.Log;

import com.example.tps900.tps900.Bean.TeamBean_1;
import com.example.tps900.tps900.Utlis.Constant;
import com.godfery.WebService.WSUtils;
import com.godfery.WebService.WebServiceUtils;

import org.ksoap2.serialization.SoapObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.example.tps900.tps900.Utlis.OtherUtils.EnCode;
import static com.example.tps900.tps900.Utlis.OtherUtils.getNum;

/**
 * Created by zxh on 2017/1/13.
 * 环企WebServer方法
 */

public class GsWebServiceUtils {
    public static String result;
    public static String soap_Action;
    public static SoapObject request;
    public static String nameSpace = "http://tempuri.org/";

    public String time() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * **********检票接口*********
     * ******** http://101.200.220.40:8003/GSCheckTicket.asmx
     *
     * @author zxh
     * created at 2017/6/15 9:52
     * 方法名: GetTerminalInfo
     * 方法说明:获取检票终端信息
     */
    public static String GetDeviceInfo() {
        final String url = "http://" + Constant.IP_ADDRESS + "/GSCheckTicket.asmx";
        WSUtils.SetServer(url, "", "");
        ContentValues values = new ContentValues();
        values.put("name", Constant.devicename);
        result = WSUtils.WS("GetDeviceInfo", values);
        Log.e("获取检票终端信息--------", result);
        return result;
    }

    /**
     * @author zxh
     * created at 2017/6/15 9:51
     * 方法名: GetTicketCheck
     * 方法说明:获取门票验票信息 参数: inBarcode 入园码  nParkid 景区编码   nDeviceid 检票终端编码  type 类型 1.Ticket( 普通门票) 2.IC （IC  卡）
     */
    public static String GetTicketCheck(String inBarcode, String type) {
        final String url = "http://" + Constant.IP_ADDRESS + "/GSCheckTicket.asmx";
        WSUtils.SetServer(url, "", "");
        ContentValues values = new ContentValues();
        values.put("inBarcode", inBarcode.toString().trim());
        values.put("nParkid", Constant.NPARKID);
        values.put("nDeviceid", Constant.NDEVICEID);
        values.put("type", type);
        result = WSUtils.WS("GetTicketCheck", values);//GetTicketCheck
        Log.e("ticket_H门票信息--- ---", "---" + Constant.NPARKID + "----" + inBarcode + "---" + Constant.NDEVICEID + "====" + type);
        Log.e("获取门票验票信息--------", result);
        return result;
    }

    /**
     * @author zxh
     * created at 2017/6/15 9:51
     * 方法名: GetUpdateTicket
     * 方法说明: 门票入园核销
     */
    public static String GetUpdateTicket(String inBarcode, int inCount, String Type) {
        final String url = "http://" + Constant.IP_ADDRESS + "/GSCheckTicket.asmx";
        WSUtils.SetServer(url, "", "");
        ContentValues values = new ContentValues();
        values.put("inBarcode", inBarcode);
        values.put("nParkid", Constant.NPARKID);
        values.put("nDeviceid", Constant.NDEVICEID);
        values.put("InCount", inCount);
        values.put("Type", Type);
        result = WSUtils.WS("GetUpdateTicket", values);
        Log.e("门票入园核销--------", result);
        return result;
    }

    /**
     * @author zxh
     * created at 2017/6/15 9:42
     * 方法名:UpdateTicketReportForms
     * 方法说明: 获取今日核销的 总计
     */
    public static String UpdateTicketReportForms(String nStartdate, String nEnddate) {
        final String url = "http://" + Constant.IP_ADDRESS + "/GSCheckTicket.asmx";
        WSUtils.SetServer(url, "", "");
        ContentValues values = new ContentValues();
        values.put("nStartdate", nStartdate);
        values.put("nEnddate", nEnddate);
        values.put("nTerminalName", Constant.S_devicename);
        result = WSUtils.WS("UpdateTicketReportForms", values);
        return result;
    }

    /**
     * ************售票接口*************
     * **** http://101.200.220.40:8004/GSSalaTicket.asmx ****
     *
     * @author zxh
     * created at 2017/6/15 9:49
     * 方法名: GetTerminalInfo
     * 方法说明:获取售票终端信息 参数：传入 name(售票终端名称)
     */
    public static String GetTerminalInfo() {
        final String url = "http://" + Constant.S_IP_ADDRESS + "/GSSalaTicket.asmx";
        WSUtils.SetServer(url, "", "");
        ContentValues values = new ContentValues();
        values.put("name", Constant.S_devicename);
        result = WSUtils.WS("GetTerminalInfo", values);
        Log.e("获取售票终端信息--------", result);
        return result;
    }

    /**
     * @author zxh
     * created at 2017/6/15 9:48
     * 方法名: GetTerminalFormula
     * 方法说明:获取终端可售方案
     */
    public static String GetTerminalFormula() {
        final String url = "http://" + Constant.S_IP_ADDRESS + "/GSSalaTicket.asmx";
        WSUtils.SetServer(url, "", "");
        ContentValues values = new ContentValues();
        values.put("id", Constant.S_deviceID);
        result = WSUtils.WS("GetTerminalFormula", values);
        Log.e("获取终端可售方案--------", result);
        return result;
    }

    /**
     * @author zxh
     * created at 2017/6/15 9:48
     * 方法名: GetTerminalInfo
     * 方法说明:获取终端可售门票
     */
    public static String GetTerminalTicket() {
        final String url = "http://" + Constant.S_IP_ADDRESS + "/GSSalaTicket.asmx";
        WSUtils.SetServer(url, "", "");
        ContentValues values = new ContentValues();
        values.put("id", Constant.S_deviceID);
        result = WSUtils.WS("GetTerminalTicket", values);
        Log.e("获取终端可售门票--------", result);
        return result;
    }

    /**
     * @author zxh
     * created at 2017/6/15 9:48
     * 方法名: GetTerminalPayMode
     * 方法说明:获取终端支付方式
     */
    public static String GetTerminalPayMode() {
        final String url = "http://" + Constant.S_IP_ADDRESS + "/GSSalaTicket.asmx";
        WSUtils.SetServer(url, "", "");
        ContentValues values = new ContentValues();
        values.put("id", Constant.S_deviceID);
        result = WSUtils.WS("GetTerminalPayMode", values);
        Log.e("获取售票终端支付方式--------", result);
        return result;
    }

    /**
     * @author zxh
     * created at 2017/6/15 9:47
     * 方法名: GetSaleTicketInfo
     * 方法说明:获取检票终端销售门票信息
     */
    public static String GetSaleTicketInfo(String sum_money
            , String ticket_id, String ticket_price, String ticket_count, String pay_name
            , String pay_money, String emp_id, String ter_id, int nincometype, String pay_code) {
        final String url = "http://" + Constant.S_IP_ADDRESS + "/GSSalaTicket.asmx";
        String json = "{\"sum_money\":" + sum_money + ",\n" +
                "\"sub_money\": " + 0 + ",  \n" +
                "\"ticket_list\": [{\"ticket_id\": " + ticket_id + ", \"ticket_price\":\"" + ticket_price + "\", \"ticket_count\": " + ticket_count + "} ],  \n" +
                "\"ticket_pay_mode\": [{\"pay_name\": \"" + pay_name + "\", \"pay_money\": " + pay_money + "  }],\n" +
                "\"emp_id\":\"" + emp_id + "\",\n" +
                "\"ter_id\":" + ter_id + ", \n" +
                "\"remark\": \"测试 \", \n" +
                "\"nincometype\": " + nincometype + ", \n" +
                "\"pay_code\": \"" + pay_code + "\" \n" +
                "}";
        WSUtils.SetServer(url, "", "");
        ContentValues values = new ContentValues();
        values.put("jsonstr", json);
        result = WSUtils.WS("GetSaleTicketInfo", values);
        Log.e("json----------------》", json);
        Log.e("获取检票终端销售门票信息--------", result);
        return result;
    }


    /**
     * @author zxh
     * created at 2017/6/15 9:42
     * 方法名:SalaTicketReportForms
     * 方法说明: 获取今日售票的 总计
     */
    public static String SalaTicketReportForms(String nStartdate, String nEnddate) {
        final String url = "http://" + Constant.S_IP_ADDRESS + "/GSSalaTicket.asmx";
        WSUtils.SetServer(url, "", "");
        ContentValues values = new ContentValues();
        values.put("nStartdate", nStartdate);
        values.put("nEnddate", nEnddate);
        values.put("nTerminalName", Constant.S_devicename);
        result = WSUtils.WS("SalaTicketReportForms", values);
        Log.e("获取售票终端信息--------", result);
        return result;
    }

    //**********************************************商品接口****************************************
    /***
     *
     * 查询会员
     */

    public static String queryVIP(String cardID, String npwd) {
        final String url = "http://" + Constant.ADDRESS + "/WebService.asmx";
        System.out.println("服务地址" + url);
        WSUtils.SetServer(url, "", "");
        ContentValues values = new ContentValues();
        values.put("cardID", cardID);
        values.put("npwd", npwd);
        result = WSUtils.WS("DiscountData", values);
       // Log.e("获取终端支付方式--------", result);
        if (result != null) {
            return result;
        }
        return "err";
    }
    /***
     *
     * 会员支付积分
     */

    public static String VIPApily(String cm) {
        final String url = "http://" + Constant.ADDRESS + "/WebService.asmx";
        System.out.println("服务地址" + url);
        WSUtils.SetServer(url, "", "");
        ContentValues values = new ContentValues();
        values.put("cm", cm);
        result = WSUtils.WS("ConsumeData", values);
        // Log.e("获取终端支付方式--------", result);
        if (result != null) {
            return result;
        }
        return "err";
    }
    /***
     *
     * 会员撤单
     */

    public static String VIPPayErr(String rm) {
        final String url = "http://" + Constant.ADDRESS + "/WebService.asmx";
        System.out.println("服务地址" + url);
        WSUtils.SetServer(url, "", "");
        ContentValues values = new ContentValues();
        values.put("rm", rm);
        result = WSUtils.WS("RevokeData", values);
        // Log.e("获取终端支付方式--------", result);
        if (result != null) {
            return result;
        }
        return "err";
    }
    /**
     * @author zxh
     * created at 2017/6/15 9:54
     * 方法名: login
     * 方法说明:  登录获取商品
     */
    public static String login(String employeeName, String password) {
        final String url = "http://" + Constant.ADDRESS + "/WebService.asmx";
        System.out.println("服务地址" + url);
        WSUtils.SetServer(url, "", "");
        ContentValues values = new ContentValues();
        values.put("employeeName", employeeName);
        values.put("password", password);
        result = WSUtils.WS("GMB_GETEMPLOYEEINFO", values);
        Log.e("获取终端支付方式--------", result);
        if (result != null) {
            return result;
        }
        return "err";
    }

    /**
     * 获取营业点信息
     *
     * @param terminalId
     * @return
     */
    public static String getZoneInfo(String terminalId) {
        final String url = "http://" + Constant.ADDRESS + "/WebService.asmx";
        WSUtils.SetServer(url, "", "");
        ContentValues values = new ContentValues();
        values.put("terminalId", terminalId);
        result = WSUtils.WS("GMB_GETGZONEINFO", values);
        if (result != null) {
            return result;
        }

        return "err";
    }

    /**
     * 访问商品分类接口
     *
     * @param zoneId
     * @return
     */
    public static String getGoodsCategories(int zoneId) {
        final String url = "http://" + Constant.ADDRESS + "/WebService.asmx";
        WSUtils.SetServer(url, "", "");
        ContentValues values = new ContentValues();
        values.put("ngzoneId", zoneId);
        result = WSUtils.WS("GMB_GETGOODSCLASS", values);
        if (result != null) {
            return result;
        }

        return "err";
    }

    /**
     * 获取详细商品信息
     *
     * @param zoneId
     * @param classId
     * @return
     */
    public static String getDetailedGoodsInfo(int zoneId, int classId) {
        String method = "GMB_GETGOODSINFO";
        final String url = "http://" + Constant.ADDRESS + "/WebService.asmx";
        WSUtils.SetServer(url, "", "");
        ContentValues values = new ContentValues();
        values.put("ngzoneId", zoneId);
        values.put("goodsClassid", classId);
        result = WSUtils.WS(method, values);
        if (result != null) {
            return result;
        }


        return "err";
    }

    /**
     * 访问商品分类接口
     *
     * @param zoneId
     * @return
     */
    public static String getFoodsCategories(int zoneId) {
        final String url = "http://" + Constant.ADDRESS + "/WebService.asmx";
        WSUtils.SetServer(url, "", "");
        ContentValues values = new ContentValues();
        values.put("ngzoneId", zoneId);
        result = WSUtils.WS("GMB_GETRESTCLASS", values);
        if (result != null) {
            return result;
        }

        return "err";
    }

    /**
     * 获取详细商品信息
     *
     * @param zoneId
     * @param classId
     * @return
     */
    public static String getDetailedFoodsInfo(int zoneId, int classId) {
        String method = "GMB_GETRESTINFO";
        final String url = "http://" + Constant.ADDRESS + "/WebService.asmx";
        WSUtils.SetServer(url, "", "");
        ContentValues values = new ContentValues();
        values.put("ngzoneId", zoneId);
        values.put("goodsClassid", classId);
        result = WSUtils.WS(method, values);
        if (result != null) {
            return result;
        }


        return "err";
    }

    /**
     * 获取桌台详情
     *
     * @return
     */
    public static String getTableInfo(int Flag,String  Value) {
        String method = "GETTABLEINFOS";
        final String url = "http://" + Constant.ADDRESS + "/WebService.asmx";
        WSUtils.SetServer(url, "", "");
        ContentValues values = new ContentValues();
        values.put("Flag",Flag);
        values.put("Value", Value);
        result = WSUtils.WS(method, values);
        if (result != null) {
            return result;
        }
        return "err";
    }

    /**
     * 获取支付方式
     *
     * @param zoneId
     * @return
     */
    public static String getPayType(int zoneId) {
        String method = "GMB_GETPAYWAY";
        final String url = "http://" + Constant.ADDRESS + "/WebService.asmx";
        WSUtils.SetServer(url, "", "");
        ContentValues values = new ContentValues();
        values.put("ngzoneId", zoneId);
        result = WSUtils.WS(method, values);
        if (result != null) {
            return result;
        }

        return "err";
    }

    /**
     * 获取订单号
     *
     * @return
     */
    public static String getOrderNo() {
        String method = "GMB_GETBILLNO";
        final String url = "http://" + Constant.ADDRESS + "/WebService.asmx";
        WSUtils.SetServer(url, "", "");
        ContentValues values = new ContentValues();
        result = WSUtils.WS(method, values);
        if (result != null) {
            return result;
        }

        return "err";
    }


    /**
     * 商品提交订单信息
     *
     * @param billInfo
     * @return
     */
    public static String submitInfo(String billInfo) {
        String method = "GMB_SETSELLBILL";
        final String url = "http://" + Constant.ADDRESS + "/WebService.asmx";
        WSUtils.SetServer(url, "", "");
        ContentValues values = new ContentValues();
        values.put("billInfo", billInfo);
        result = WSUtils.WS(method, values);
        if (result != null) {
            return result;
        }
        return "err";
    }

    /**
     * 餐饮提交订单信息
     *
     * @param billInfo
     * @return
     */
    public static String submitFoodInfo(String billInfo) {
        String method = "SETTABLEINFO";
        final String url = "http://" + Constant.ADDRESS + "/WebService.asmx";
        WSUtils.SetServer(url, "", "");
        ContentValues values = new ContentValues();
        values.put("billInfo", billInfo);
        result = WSUtils.WS(method, values);
        if (result != null) {
            return result;
        }
        return "err";
    }
    /**
     * 餐饮挂单提交订单信息
     *
     * @param billInfo
     * @return
     */
    public static String submitFoodInfo_Table(String billInfo) {
        String method = "SETTABLEINFOTEMP";
        final String url = "http://" + Constant.ADDRESS + "/WebService.asmx";
        WSUtils.SetServer(url, "", "");
        ContentValues values = new ContentValues();
        values.put("billInfo", billInfo);
        result = WSUtils.WS(method, values);
        if (result != null) {
            return result;
        }
        return "err";
    }

    /**
     * 获取桌台类别
     *
     * @return
     */
    public static String GMB_GETTABLETYPE() {
        String method = "GMB_GETTABLETYPE";
        final String url = "http://" + Constant.ADDRESS + "/WebService.asmx";
        WSUtils.SetServer(url, "", "");
        ContentValues values = new ContentValues();
        result = WSUtils.WS(method, values);
        if (result != null) {
            return result;
        }
        return "err";
    }

    /**
     * 获取退货订单信息
     *
     * @param zoneId
     * @param billNo
     * @return
     */
    public static String getReturnInfo(String zoneId, String billNo) {

        String method = "GMB_GETRETURNBILL";
        final String url = "http://" + Constant.ADDRESS + "/WebService.asmx";
        WSUtils.SetServer(url, "", "");
        ContentValues values = new ContentValues();
        values.put("ngzoneId", zoneId);
        values.put("billNo", billNo);
        result = WSUtils.WS(method, values);
        if (result != null) {
            return result;
        }
        return "err";

    }

    /**
     * 提交退货订单信息
     *
     * @param billInfo
     * @return
     */
    public static String submitReturnInfo(String billInfo) {
        String method = "ThirdMethod";
        final String url = "http://" + Constant.ADDRESS + "/WebService.asmx";
        WSUtils.SetServer(url, "", "");
        ContentValues values = new ContentValues();
        values.put("billInfo", billInfo);
        result = WSUtils.WS(method, values);
        if (result != null) {
            return result;
        }
        return "err";

    }


    /**
     * 获取一卡通信息
     *
     * @param cardNo
     * @return
     */
    public static String getOneCardinfo(String cardNo) {
        String method = "GMB_GETONECARDINFO";
        final String url = "http://" + Constant.ADDRESS + "/WebService.asmx";
        WSUtils.SetServer(url, "", "");
        ContentValues values = new ContentValues();
        values.put("CARDPHYCNO", cardNo);
        result = WSUtils.WS(method, values);
        if (result != null) {
            return result;
        }
        return "err";
    }

    /**
     * 获取一卡通信息(专用于消费支付)
     *
     * @param cardNo
     * @return
     */
    public static String GetCardInfo(String cardNo) {
        String method = "GetCardInfo";
        final String url = "http://" + Constant.OneCardInterface + "/WebService.asmx";
        WSUtils.SetServer(url, "", "");
        ContentValues values = new ContentValues();
        values.put("nPhyID", cardNo);
        result = WSUtils.WS(method, values);
        if (result != null) {
            return result;
        }
        return "err";
    }

    /**
     * 一卡通消费（专用于消费支付）
     *
     * @param NPHYSICALNO 一卡通物理号(string)
     * @param NAMOUNT     消费金额(decimal)
     *                    NTERMINALID 消费款台ID(int)  Constant.TERMINALID
     *                    DTIME 消费时间(DateTime)
     *                    MEMO  备注(string)
     *                    OUTTRANDNO （业务系统交易单号）原交易单号(string)
     * @return
     */
    public static String GetCardConsumption(String NPHYSICALNO, double NAMOUNT) {
        String method = "GetCardConsumption";
        final String url = "http://" + Constant.OneCardInterface + "/WebService.asmx";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String date = formatter.format(curDate);
        String json = "{\"NPHYSICALNO\":\"" + NPHYSICALNO + "\",\n" +
                "\"NAMOUNT\":" + NAMOUNT + ",  \n" +
                "\"NTERMINALID\":" + Constant.ONECARD_TERMINALNAME + ",  \n" +
                "\"DTIME\":\"" + date + "\",  \n" +
                "\"MEMO\":\"" + "外部订单号 " + getNum() + "\",  \n" +
                "\"OUTTRANDNO\": \"" + getNum() + "\"\n" +
                "}";
        Log.e("一卡通", "一卡通消费传入json:" + json);
        WSUtils.SetServer(url, "", "");
        ContentValues values = new ContentValues();
        values.put("jsonstr", json);
        result = WSUtils.WS(method, values);
        if (result != null) {
            return result;
        }
        return "err";
    }

    /**
     * 获取收银日报信息
     *
     * @param json
     * @return
     */
    public static String getShouYinInfo(String json) {

        String method = "GMB_GETCASHIERREPORT";
        final String url = "http://" + Constant.ADDRESS + "/WebService.asmx";
        WSUtils.SetServer(url, "", "");
        ContentValues values = new ContentValues();
        values.put("rjson", json);
        result = WSUtils.WS(method, values);
        if (result != null) {
            return result;
        }

        return "err";
    }

    /**
     * 获取销售日报信息
     *
     * @param json
     * @return
     */
    public static String getSellInfo(String json) {
        String method = "GMB_GETSELLREPORT";
        final String url = "http://" + Constant.ADDRESS + "/WebService.asmx";
        WSUtils.SetServer(url, "", "");
        ContentValues values = new ContentValues();
        values.put("rjson", json);
        result = WSUtils.WS(method, values);
        if (result != null) {
            return result;
        }
        return "err";

    }

    /**
     * TODO---------------------------大平台门票核销和带团核销-------------------------------------
     * TODO http://223.223.179.20:8084/OrderRelated.asmx
     */
    /**
     * 核销接口
     *
     * @param ECode    串码
     * @param VerifNum 核销数量
     * @return
     */
    public static String ConsumeTickets(String ECode, String VerifNum) {
        String method = "ConsumeTickets";
        final String url = "http://" + Constant.Y_IP_ADDRESS + "/OrderRelated.asmx";
        soap_Action = nameSpace + method;
        System.out.println("服务地址" + url);
        String json = "{\"TaiwanCode\"" + ":\"" + Constant.Y_deviceID + "\"" + ",\"ECode\":\"" + ECode + "\"" + ",\"VerifNum\":\"" + VerifNum + "\"}";
        String Enjson = Constant.Y_NUMBER + Constant.Y_SECRET + json;
        System.out.println("签名------------->" + Enjson + "核销数量" + VerifNum);
        String signature = EnCode(Enjson);
        request = new SoapObject(nameSpace, method);
        if ("-1".equals(signature)) {
            return "生成签名失败!";
        }
        request.addProperty("parkCode", Constant.Y_NUMBER);//景区编码
        request.addProperty("dataStr", json);//查询参数
        request.addProperty("sign", signature);//签名
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    result = WebServiceUtils.getRemoteInfo(request, url, soap_Action);
                } catch (Exception e) {

                }
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
        }
        if (result != null) {
            return result;
        }
        return "err";
    }

    /**
     * 核销查询接口
     * 接口名称： GetOverifByEcodeBean
     *
     * @param Ecode   二维码
     * @param OrderNo 订单号
     * @return
     */
    public static String GetOverifByEcode(String Ecode, String OrderNo) {
        String method = "GetOverifByEcode";
        final String url = "http://" + Constant.Y_IP_ADDRESS + "/OrderRelated.asmx";
        soap_Action = nameSpace + method;
        System.out.println("服务地址" + url);
        String json = "{\"Ecode\"" + ":\"" + Ecode + "\"" + ",\"TaiwanNo\":\"" + Constant.Y_deviceID + "\"" + ",\"OrderNo\":\"" + OrderNo + "\"}";
        String Enjson = Constant.Y_NUMBER + Constant.Y_SECRET + json;
        System.out.println("签名------------->" + Enjson);
        String signature = EnCode(Enjson);
        request = new SoapObject(nameSpace, method);
        if ("-1".equals(signature)) {
            return "生成签名失败!";
        }
        request.addProperty("parkCode", Constant.Y_NUMBER);//景区编码
        request.addProperty("queryStr", json);//查询参数
        request.addProperty("sign", signature);//签名
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    result = WebServiceUtils.getRemoteInfo(request, url, soap_Action);
                    Log.e("线上查询信息", result);
                } catch (Exception e) {

                }
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
        }
        if (result != null) {
            return result;
        }
        return "err";
    }

    public static String GetDisOrder(String Ecode, String CardNo, String OrderNo) {
        String method = "GetDisOrder";
        final String url = "http://" + Constant.Y_IP_ADDRESS + "/OrderRelated.asmx";
        soap_Action = nameSpace + method;
        System.out.println("服务地址" + url);
        /**
         * Ecode
         CardNo
         TaiwanNo
         OrderNo
         ProductId

         */
        String json = "{\"Ecode\"" + ":\"" + Ecode + "\"" + ",\"CardNo\":\"" + CardNo + "\"" + ",\"TaiwanNo\":\"" + Constant.Y_deviceID + "\"" + ",\"OrderNo\":\"" + OrderNo + "\"" + ",\"ProductId\":\"" + "" + "\"}";
        String Enjson = Constant.Y_NUMBER + Constant.Y_SECRET + json;
        System.out.println("签名------------->" + Enjson);
        String signature = EnCode(Enjson);
        request = new SoapObject(nameSpace, method);
        if ("-1".equals(signature)) {
            return "生成签名失败!";
        }
        request.addProperty("parkCode", Constant.Y_NUMBER);//景区编码
        request.addProperty("queryStr", json);//查询参数
        request.addProperty("sign", signature);//签名
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    result = WebServiceUtils.getRemoteInfo(request, url, soap_Action);
                } catch (Exception e) {

                }
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
        }
        if (result != null) {
            return result;
        }
        return "err";
    }

    /**
     * 获取带团订单信息
     *
     * @param orderNo 订单号
     * @return
     */
    public static String GetTeamOrders(String orderNo) {
        String method = "GetTeamOrders";
        soap_Action = nameSpace + method;
        final String url = "http://" + Constant.Y_IP_ADDRESS + "/OrderRelated.asmx";
        System.out.println("服务地址" + url);
        String oderNoStr = "{\"orderNo\":\"" + orderNo + "\"}";
        String Enjson = Constant.Y_NUMBER + Constant.Y_SECRET + orderNo;
        System.out.println("签名------------->" + Enjson);
        String signature = EnCode(Enjson);
        request = new SoapObject(nameSpace, method);
        System.out.println("signature------------->" + signature);
        if ("-1".equals(signature)) {
            return "生成签名失败!";
        }
        request.addProperty("parkCode", Constant.Y_NUMBER);//景区编码
        request.addProperty("orderNo", orderNo);//查询参数
        request.addProperty("sign", signature);//签名
        Thread thread = new Thread() {
            @Override
            public void run() {
                result = WebServiceUtils.getRemoteInfo(request, url, soap_Action);
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
        }
        if (result != null) {
            return result;
        }
        return null;
    }


    /**
     * 请求带团订单核销接口
     *
     * @param teamOrders
     * @return
     */

    public static String requestTeamOrderForTickets(List<TeamBean_1.DataBean.TeamOrdersBean> teamOrders, String payTime, String thirdNO, String money) {
        String method = "TeamOrderForTickets";
        soap_Action = nameSpace + method;
        final String url = "http://" + Constant.Y_IP_ADDRESS + "/OrderRelated.asmx";
        Log.i("webservice", "URL地址----->" + url);
        String Items = "";
        Log.i("webservice", "teamOrders的大小--->" + teamOrders.size());
        StringBuffer sb = new StringBuffer();

        if (teamOrders.size() == 1) {
            TeamBean_1.DataBean.TeamOrdersBean teamOrdersBean = teamOrders.get(0);

            Items = "[{\"ProductId\"" + ":\"" + teamOrdersBean.getPRODUCTID() + "\"" + ",\"InCount\":\"" + teamOrdersBean.getActualPerson() + "\"}]";//订单明细信息

        } else if (teamOrders.size() == 2) {

            Items = "[{\"ProductId\"" + ":\"" + teamOrders.get(0).getPRODUCTID() + "\"" + ",\"InCount\":\"" + teamOrders.get(0).getActualPerson() + "\" },{\"ProductId\"" + ":\"" + teamOrders.get(1).getPRODUCTID() + "\"" + ",\"InCount\":\"" + teamOrders.get(1).getActualPerson() + "\"}]";//订单明细信息

        } else {

            for (int i = 0; i < teamOrders.size(); i++) {
                if (i == 0) {
                    Items = Items + "[{\"ProductId\"" + ":\"" + teamOrders.get(0).getPRODUCTID() + "\"" + ",\"InCount\":\"" + teamOrders.get(0).getActualPerson() + "\" },";
                } else if (i == teamOrders.size() - 1) {
                    Items = Items + "{\"ProductId\"" + ":\"" + teamOrders.get(teamOrders.size() - 1).getPRODUCTID() + "\"" + ",\"InCount\":\"" + teamOrders.get(teamOrders.size() - 1).getActualPerson() + "\"}]";//订单明细信息
                } else {
                    Items = Items + "{\"ProductId\"" + ":\"" + teamOrders.get(i).getPRODUCTID() + "\"" + ",\"InCount\":\"" + teamOrders.get(i).getActualPerson() + "\"},";
                }

            }

        }

        TeamBean_1.DataBean.TeamOrdersBean teamOrdersBean = teamOrders.get(0);

        String OrderPayInfo = "{\"PayType\"" + ":\"" + teamOrdersBean.getPAYTYPE() + "\"" + ",\"PayDirecation\":\"" + 1 + "\"" +
                ",\"PayAmt\":\"" + money + "\"" + ",\"ThirdNo\":\"" + thirdNO + "\" }";//支付方式编码
        String json = "{\"OrderId\"" + ":\"" + teamOrdersBean.getORDERID() + "\",\"OrderNo\":\"" + teamOrdersBean.getORDERNO()
                + "\",\"IsReprint\":\"" + teamOrdersBean.getISPRINT() +
                "\",\"Items\":" + Items +
                ",\"OrderPayInfo\":" + OrderPayInfo +
                ",\"PayTim\":\"" + payTime +
                "\",\"ORDERPAYINFO_MACHINEID\":\"" + null +
                "\",\"ORDERPAYINFO_MACHINECODE\":\"" + null +
                "\",\"ORDERPAYINFO_OPERATORID\":\"" + null +
                "\",\"ORDERPAYINFO_OPERATORNAME\":\"" + null + "\"}";
        String Enjson = Constant.Y_NUMBER + Constant.Y_SECRET + json;
        System.out.println("签名------------->" + Enjson);
        String signature = EnCode(Enjson);
        request = new SoapObject(nameSpace, method);
        if ("-1".equals(signature)) {
            return "生成签名失败!";
        }
        request.addProperty("parkCode", Constant.Y_NUMBER);//景区编码
        request.addProperty("dataStr", json);//查询参数
        request.addProperty("sign", signature);//签名
        Thread thread = new Thread() {
            @Override
            public void run() {
                result = WebServiceUtils.getRemoteInfo(request, url, soap_Action);
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
        }
        if (result != null) {
            return result;
        }
        return null;
    }

    /**
     * 获取带团订单信息
     *
     * @return
     */

    public static String GetDisOrderReport(String orderNO, String sdate, String edate, String stype, String PageIndex, String PageSize) {
        String method = "GetDisOrderReport";
        final String url = "http://" + Constant.Y_IP_ADDRESS + "/OrderRelated.asmx";
        soap_Action = nameSpace + method;
        System.out.println("服务地址" + url);//stype
        String json = "{\"orderNO\"" + ":\"" + orderNO + "\"" + ",\"sdate\":\"" + sdate + "\"" + ",\"edate\":\"" + edate + "\"" + ",\"stype\":\"" + stype + "\"" + ",\"PageIndex\":\"" + PageIndex + "\"" + ",\"PageSize\":\"" + PageSize + "\"}";//支付方式编码
        String Enjson = Constant.Y_NUMBER + Constant.Y_SECRET + json;
        String signature = EnCode(Enjson);
        request = new SoapObject(nameSpace, method);
        if ("-1".equals(signature)) {
            return "生成签名失败!";
        }
        request.addProperty("parkCode", Constant.Y_NUMBER);//景区编码
        request.addProperty("queryStr", json);//查询参数
        request.addProperty("sign", signature);//签名
        Thread thread = new Thread() {
            @Override
            public void run() {
                result = WebServiceUtils.getRemoteInfo(request, url, soap_Action);
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
        }
        if (result != null) {
            return result;
        }
        return "err";
    }

    /**
     * @author zxh
     * created at 2017/6/26 9:58
     * 方法名: 散客取票
     * 方法说明:
     * 1  Ecode	串码号  2 TaiwanNo	款台号   3 OrderNo	订单号 4 ProductId	产品ID   IsReprint	5 是否重复打印（0：否，1：是）
     */
    public static String DisOrderChangeTicket(String Ecode, String OrderNo, String ProductId, String IsReprint) {
        String method = "DisOrderChangeTicket";
        final String url = "http://" + Constant.Y_IP_ADDRESS + "/OrderRelated.asmx";
        soap_Action = nameSpace + method;
        System.out.println("服务地址" + url);
        String json = "{\"Ecode\"" + ":\"" + Ecode + "\"" + ",\"TaiwanNo\":\"" + Constant.Y_deviceID + "\"" + ",\"OrderNo\":\"" + OrderNo + "\"" + ",\"ProductId\":\"" + ProductId + "\"" + ",\"IsReprint\":\"" + IsReprint + "\" }";//支付方式编码
        Log.e("散客取票--json", json);
        String Enjson = Constant.Y_NUMBER + Constant.Y_SECRET + json;
        Log.e("散客取票--签名---1", Enjson);
        String signature = EnCode(Enjson);

        Log.e("散客取票--签名---2", signature);
        request = new SoapObject(nameSpace, method);
        if ("-1".equals(signature)) {
            return "生成签名失败!";
        }
        request.addProperty("parkCode", Constant.Y_NUMBER);//景区编码
        request.addProperty("queryStr", json);//查询参数
        request.addProperty("sign", signature);//签名
        Thread thread = new Thread() {
            @Override
            public void run() {
                result = WebServiceUtils.getRemoteInfo(request, url, soap_Action);
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
        }
        if (result != null) {
            return result;
        }
        return "err";
    }

    /**
     * @author zxh
     * created at 2017/10/23 9:21
     * 方法名: 获取服务器时间
     * 方法说明:
     */
    public static String GMB_GETSERVERTIME() {

        String method = "GMB_GETSERVERTIME";
        final String url = "http://" + Constant.ADDRESS + "/WebService.asmx";
        System.out.println("服务地址" + url);
        final SoapObject request = new SoapObject(nameSpace, method);
        Thread thread = new Thread() {
            @Override
            public void run() {
                result = WebServiceUtils.getRemoteInfo(request, url, soap_Action);
                Log.d("aaa", "服务器时间是---->" + result);
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
        }

        if (result != null) {
            return result;
        }

        return "err";

    }
    /**
     * 获取商品库存数量
     *
     * @param GZONEID 终端id
     * @param GOODSID     商品id
     * @return
     */
    public static String getGOODSSTOCK(String GZONEID, String GOODSID) {

        String method = "GETGOODSSTOCK";
        final String url = "http://" + Constant.ADDRESS + "/WebService.asmx";
        WSUtils.SetServer(url, "", "");
        ContentValues values = new ContentValues();
        values.put("GZONEID", GZONEID);
        values.put("GOODSID", GOODSID);
        result = WSUtils.WS(method, values);
        if (result != null) {
            return result;
        }
        return "err";

    }
    /**
     * 获取餐饮收银日报信息
     *
     * @param json
     * @return
     */
    public static String getFoodShouYinInfo(String json) {

        String method = "GMB_GETFOODCASHIERREPORT";
        final String url = "http://" + Constant.ADDRESS + "/WebService.asmx";
        WSUtils.SetServer(url, "", "");
        ContentValues values = new ContentValues();
        values.put("rjson", json);
        result = WSUtils.WS(method, values);
        if (result != null) {
            return result;
        }

        return "err";
    }

    /**
     * 提交餐饮退货订单信息
     *
     * @param billInfo
     * @return
     */
    public static String submitFoodReturnInfo(String billInfo) {
//        String method = "GMB_SETFOODRETURNBILL";
        final String url = "http://" + Constant.ADDRESS + "/WebService.asmx";
        WSUtils.SetServer(url, "", "");
        ContentValues values = new ContentValues();
        values.put("billInfo", billInfo);
        result = WSUtils.WS("FirstMethod", values);
        if (result != null) {
            return result;
        }
        return "err";

    }

    /**
     * 获取餐饮退货订单信息
     *
     * @param zoneId
     * @param billNo
     * @return
     */
    public static String getFoodReturnInfo(String zoneId, String billNo) {

        String method = "GMB_GETFOODRETURNBILL";
        final String url = "http://" + Constant.ADDRESS + "/WebService.asmx";
        WSUtils.SetServer(url, "", "");
        ContentValues values = new ContentValues();
        values.put("ngzoneId", zoneId);
        values.put("billNo", billNo);
        result = WSUtils.WS(method, values);
        if (result != null) {
            return result;
        }
        return "err";

    }
}
