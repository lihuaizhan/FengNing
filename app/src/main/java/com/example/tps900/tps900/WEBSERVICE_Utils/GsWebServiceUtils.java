package com.example.tps900.tps900.WEBSERVICE_Utils;


import android.util.Log;

import com.example.tps900.tps900.Utlis.LogUtil;
import com.example.tps900.tps900.Utlis.OtherUtils;

import org.ksoap2.serialization.SoapObject;

import static com.example.tps900.tps900.Utlis.OtherUtils.string2Json;
import static com.example.tps900.tps900.WEBSERVICE_Utils.Login_Variate.MachineId;


/**
 * 环企WebServer方法
 */

public class GsWebServiceUtils {

    public static final String TAG = "GsWebServiceUtils";
    public static String url = "http://" + Constants.IP_ADDRESS + ":" + Constants.SOCKET + "/WebService.asmx";
    public static String nameSpace = "http://tempuri.org/";
    public static String soap_Action;
    public static SoapObject request;
    public static String result;
    public static SoapObject teamrequest;

    /**
     * 登陆方法
     *
     * @param username 登陆用户
     * @param password 登陆密码
     * @return
     */
    public static String Login(String username, String password) {
        String method = "Login";
        soap_Action = nameSpace + method;
        System.out.println("服务地址" + url.replaceAll(" ", "%20"));// 00000205-192.168.0.110
        String json = "{\"UserName\"" + ":\"" + username + "\",\"Password\":\"" + password + "\",\"MachineCode\":\"" + Constants.device_code + "\"}";
        String Enjson = Constants.Scenicspot_code + Constants.Scenicspot_Password + json;
        System.out.println("签名------------->" + Enjson);
        String signature = OtherUtils.EnCode(Enjson);
        request = new SoapObject(nameSpace, method);
        if ("-1".equals(signature)) {
            return "生成签名失败!";
        }
        request.addProperty("ParkCode", Constants.Scenicspot_code);//00000205
        request.addProperty("LoginData", json);
        request.addProperty("Sign", signature);
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
     * 获取门票类别
     *
     * @param MachineId   设备ID
     * @param ProductType 产品类型 可为空
     * @return
     */
    public static String GetTicketType(String MachineId, String ProductType) {
        String method = "GetTicketType";
        soap_Action = nameSpace + method;
        System.out.println("服务地址" + url.replaceAll(" ", "%20"));
        String json = "{\"MachineId\"" + ":\"" + MachineId + "\"" + ",\"ProductType\":" + ProductType + "}";//支付方式编码
        String Enjson = Constants.Scenicspot_code + Constants.Scenicspot_Password + json;
        System.out.println("签名------------->" + Enjson);

        String signature = OtherUtils.EnCode(Enjson);
        request = new SoapObject(nameSpace, method);
        if ("-1".equals(signature)) {
            return "生成签名失败!";
        }
        request.addProperty("parkCode", Constants.Scenicspot_code);//景区编码
        request.addProperty("Data", json);//查询参数
        request.addProperty("sign", signature);//签名
        Thread thread = new Thread() {
            @Override
            public void run() {
                result = null;
                result = WebServiceUtils.getRemoteInfo(request, url, soap_Action);
                System.out.println("门票类别------------>" + result);
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
     * 获取门票
     *
     * @param MachineId   设备ID
     * @param ProductType 产品类型
     * @param ProductId   产品ID
     * @return
     */
    public static String GetTickets(String MachineId, String ProductType, String ProductId) {
        String method = "GetTickets";
        soap_Action = nameSpace + method;
        System.out.println("服务地址" + url.replaceAll(" ", "%20"));
        String json = "{\"MachineId\"" + ":\"" + MachineId + "\"" + ",\"ProductType\":" + ProductType + ",\"ProductId\":" + ProductId + "}";//支付方式编码
        String Enjson = Constants.Scenicspot_code + Constants.Scenicspot_Password + json;
        System.out.println("签名------------->" + Enjson);
        String signature = OtherUtils.EnCode(Enjson);
        request = new SoapObject(nameSpace, method);
        if ("-1".equals(signature)) {
            return "生成签名失败!";
        }
        request.addProperty("parkCode", Constants.Scenicspot_code);//景区编码
        request.addProperty("QueryStr", json);//查询参数
        request.addProperty("sign", signature);//签名
        Thread thread = new Thread() {
            @Override
            public void run() {
                result = WebServiceUtils.getRemoteInfo(request, url, soap_Action);
                System.out.println("获取门票------------>" + result);
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
     * 获取散客订单可兑换门票信息
     *
     * @param Ecode       根据串码或者是订单号
     * @param MachineCode 设备编码
     * @return
     */
    public static String GetDisOrder(String Ecode, String MachineCode) {
        String method = "GetDisOrder";
        soap_Action = nameSpace + method;
        System.out.println("服务地址" + url.replaceAll(" ", "%20"));//130123199001244518  //192.168.0.110
        Log.e("Ecode", Ecode);
        String json = "{\"Ecode\"" + ":\"" + Ecode + "\",\"MachineCode\":\"" + MachineCode + "\"}";
        //"{UserName':'" + username + "','Password':'" + password + "','MachineCode':'" + Constants.device_code + "'}";
        String Enjson = Constants.Scenicspot_code + Constants.Scenicspot_Password + json;
        Log.e("Enjson", Enjson);
        System.out.println("签名------------->" + Enjson);
        String signature = OtherUtils.EnCode(Enjson);
        request = new SoapObject(nameSpace, method);
        if ("-1".equals(signature)) {
            return "生成签名失败!";
        }
        request.addProperty("parkCode", Constants.Scenicspot_code);//景区编码
        request.addProperty("queryStr", json);//查询参数
        request.addProperty("sign", signature);//签名
        Log.e("request", String.valueOf(request));
        Thread thread = new Thread() {
            @Override
            public void run() {
                result = null;
                result = WebServiceUtils.getRemoteInfo(request, url, soap_Action);
                System.out.println("散客订单查询result------------>" + result);
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
     * 散客换票
     *
     * @param Ecode       根据串码或者是订单号
     * @param MachineCode 设备编码
     * @param OrderNo     订单号
     * @param ProductId   产品ID
     * @param IsReprint   是否重复打印（0：否，1：是）
     * @return
     */
    public static String DisOrderChangeTicket(String Ecode, String MachineCode, String OrderNo, String ProductId, String IsReprint) {
        String method = "DisOrderChangeTicket";
        soap_Action = nameSpace + method;
        System.out.println("服务地址" + url);
        String json = "{\"Ecode\"" + ":\"" + Ecode + "\",\"MachineCode\":\"" + MachineCode + "\",\"OrderNo\":\"" + OrderNo + "\",\"ProductId\":\"" + ProductId + "\",\"IsReprint\":\"" + IsReprint + "\"}";
        String Enjson = Constants.Scenicspot_code + Constants.Scenicspot_Password + json;
        System.out.println("签名------------->" + Enjson);
        String signature = OtherUtils.EnCode(Enjson);
        request = new SoapObject(nameSpace, method);
        if ("-1".equals(signature)) {
            return "生成签名失败!";
        }
        request.addProperty("parkCode", Constants.Scenicspot_code);//景区编码
        request.addProperty("queryStr", json);//查询参数
        request.addProperty("sign", signature);//签名
        Thread thread = new Thread() {
            @Override
            public void run() {
                result = null;
                result = WebServiceUtils.getRemoteInfo(request, url, soap_Action);
                System.out.println("散客订单换票result------------>" + result);
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
     * @param orderNo 订单号
     * @return
     */
    public static String GetTeamOrders(String orderNo) {
        String method = "GetTeamOrders";
        soap_Action = nameSpace + method;
        System.out.println("服务地址" + url);
        String json = "{\"orderNo\"" + ":\"" + orderNo + "\"}";
        String Enjson = Constants.Scenicspot_code + Constants.Scenicspot_Password + json;
        System.out.println("签名------------->" + Enjson);
        String signature = OtherUtils.EnCode(Enjson);
        request = new SoapObject(nameSpace, method);
        if ("-1".equals(signature)) {
            return "生成签名失败!";
        }
        request.addProperty("parkCode", Constants.Scenicspot_code);//景区编码
        request.addProperty("Data", json);//查询参数
        request.addProperty("sign", signature);//签名
        Thread thread = new Thread() {
            @Override
            public void run() {
                result = null;
                result = WebServiceUtils.getRemoteInfo(request, url, soap_Action);
                System.out.println("result------------>" + result);
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
     * 带团订单换取门票
     *
     * @param OrderId                   订单ID
     * @param OrderNo                   订单号
     * @param IsReprint                 是否重复取票（0：否，1：是）
     * @param ProductId                 产品ID
     * @param InCount                   入园人数
     * @param PayTime                   交易时间
     * @param PayType                   支付方式编码
     * @param PayDirecation             交易方向（1：支付，2：退款）
     * @param PayAmt                    支付金额
     * @param ThirdNo                   第三方流水号
     * @param ORDERPAYINFO_MACHINEID    设备ID
     * @param ORDERPAYINFO_MACHINECODE  设备编号
     * @param ORDERPAYINFO_OPERATORID   操作人ID
     * @param ORDERPAYINFO_OPERATORNAME 操作人姓名
     * @return
     */
    public static String TeamOrderForTickets(String OrderId, String OrderNo,
                                             String IsReprint, String PayTime,
                                             String ProductId, String InCount,
                                             String PayType, String PayDirecation,
                                             String PayAmt, String ThirdNo,
                                             String ORDERPAYINFO_MACHINEID, String ORDERPAYINFO_MACHINECODE,
                                             String ORDERPAYINFO_OPERATORID, String ORDERPAYINFO_OPERATORNAME) {

        String method = "TeamOrderForTickets";
        soap_Action = nameSpace + method;
        System.out.println("服务地址" + url);
        String Items = "[{\"ProductId\"" + ":\"" + ProductId + "\"" + ",\"InCount\":\"" + InCount + "\"}]";//订单明细信息
        String OrderPayInfo = "{\"PayType\"" + ":\"" + PayType + "\"" + ",\"PayDirecation\":\"" + PayDirecation + "\"" +
                ",\"PayAmt\":\"" + PayAmt + "\"" + ",\"ThirdNo\":\"" + ThirdNo + "\" }";//支付方式编码
        String json = "{\"OrderId\"" + ":\"" + OrderId + "\",\"OrderNo\":\"" + OrderNo
                + "\",\"IsReprint\":\"" + IsReprint +
                "\",\"Items\":" + Items +
                ",\"OrderPayInfo\":" + OrderPayInfo +
                ",\"PayTim\":\"" + PayTime +
                "\",\"ORDERPAYINFO_MACHINEID\":\"" + ORDERPAYINFO_MACHINEID +
                "\",\"ORDERPAYINFO_MACHINECODE\":\"" + ORDERPAYINFO_MACHINECODE +
                "\",\"ORDERPAYINFO_OPERATORID\":\"" + ORDERPAYINFO_OPERATORID +
                "\",\"ORDERPAYINFO_OPERATORNAME\":\"" + ORDERPAYINFO_OPERATORNAME + "\"}";
        String Enjson = Constants.Scenicspot_code + Constants.Scenicspot_Password + json;
        System.out.println("签名------------->" + Enjson);
        String signature = OtherUtils.EnCode(Enjson);
        request = new SoapObject(nameSpace, method);
        if ("-1".equals(signature)) {
            return "生成签名失败!";
        }
        request.addProperty("parkCode", Constants.Scenicspot_code);//景区编码
        request.addProperty("dataStr", json);//查询参数
        request.addProperty("sign", signature);//签名
        Thread thread = new Thread() {
            @Override
            public void run() {
                result = null;
                result = WebServiceUtils.getRemoteInfo(request, url, soap_Action);
                System.out.println("result------------>" + result);
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




    static String results;

    /**
     * 网络请求方法分装  -  （退票） 其他也可使用
     *
     * @param method         接口名称
     * @param json           方式编码
     * @param scenicspotcode Constants.Scenicspot_code 景区编码
     * @return
     */
    public static String OnlineRequest(final String method, String json, String scenicspotcode) {
        Log.i(TAG, "服务地址" + url);
        Log.i(TAG, "签名------------->" + Constants.Scenicspot_code + Constants.Scenicspot_Password + json);
        String signature = OtherUtils.EnCode(Constants.Scenicspot_code + Constants.Scenicspot_Password + json);

        request = new SoapObject(nameSpace, method);
        if ("-1".equals(signature)) {
            return "生成签名失败!";
        }
        request.addProperty("parkCode", scenicspotcode);//景区编码
        request.addProperty("Data", json);//查询参数
        request.addProperty("sign", signature);//签名
        results = WebServiceUtils.getRemoteInfo(request, url, nameSpace + method);
        return results;
    }


    /**
     * TODO 网络请求通用方法
     *
     * @param method         接口名称 例："GetTickets"
     * @param json           发送的参数  例："{\"MachineId\"" + ":\"" + MachineId + "\"" + ",\"ProductType\":" + ProductType + "}"
     * @param signature      签名方式 默认null则使用默认编码方式，否则传入的签名方式
     * @param scenicspotcode 景区编码 例：Constants.Scenicspot_code 景区编码
     * @return 返回成功或者失败的提示
     */
    public static String OnlineRequest(final String method, String signature, String json, String scenicspotcode) {

        if (signature == null) {
            signature = OtherUtils.EnCode(Constants.Scenicspot_code + Constants.Scenicspot_Password + json);
        }

        if ("-1".equals(signature)) {
            return "生成签名失败!";
        }

        request = new SoapObject(nameSpace, method);
        request.addProperty("sign", signature);//签名
        request.addProperty("dataStr", json);//查询参数
        request.addProperty("parkCode", scenicspotcode);//景区编码

        result = WebServiceUtils.getRemoteInfo(request, url, nameSpace + method);

        return result;

    }





    /**
     * 订单提交
     *
     * @param CustName
     * @param CustPhone
     * @param CustCardNo
     * @param OperatorId
     * @param OrderId
     * @return
     */
    public static String CreateOrder(String Detailes,
                                     String CustName, String CustPhone,
                                     String CustCardNo, String OperatorId,
                                     String OrderId, String vipid, String payCode
    ) {

        String method = "CreateOrder";
        soap_Action = nameSpace + method;
        System.out.println("服务地址" + url.replaceAll(" ", "%20"));

        String Order = null;

        if("005".equals(payCode)){
            Order = "{\"CustName\"" + ":\"" + CustName + "\""
                    + ",\"CustPhone\":\"" + CustPhone + "\"" +
                    ",\"CustCardNo\":\"" + CustCardNo + "\"" +
                    ",\"OperatorId\":\"" + OperatorId + "\"" +
                    ",\"MachineId\":\"" + MachineId + "\"" +
                    ",\"ParkId\":\"" + Login_Variate.ParkId + "\"" +
                    ",\"OrderId\":\"" + OrderId + "\"" +
                    ",\"VipId\":\"" + vipid + "\"" +
                    "}";
        }else{
            Order = "{\"CustName\"" + ":\"" + CustName + "\""
                    + ",\"CustPhone\":\"" + CustPhone + "\"" +
                    ",\"CustCardNo\":\"" + CustCardNo + "\"" +
                    ",\"OperatorId\":\"" + OperatorId + "\"" +
                    ",\"MachineId\":\"" + MachineId + "\"" +
                    ",\"ParkId\":\"" + Login_Variate.ParkId + "\"" +
                    ",\"OrderId\":\"" + OrderId + "\"" +
                    "}";
        }



        String json = "{\"Order\":" + Order + "," + "\"Detailes\":" + Detailes + "}";
        Log.e("json-------------->", json);
        String Enjson = Constants.Scenicspot_code + Constants.Scenicspot_Password + json;
        System.out.println("签名------------->" + Enjson);
        String signature = OtherUtils.EnCode(Enjson);
        request = new SoapObject(nameSpace, method);
        if ("-1".equals(signature)) {
            return "生成签名失败!";
        }
        request.addProperty("parkCode", Constants.Scenicspot_code);//景区编码
        request.addProperty("Data", string2Json(json));//查询参数
        request.addProperty("sign", signature);//签名
        Thread thread = new Thread() {
            @Override
            public void run() {
                result = null;
                result = WebServiceUtils.getRemoteInfo(request, url, soap_Action);
                System.out.println("result------------>" + result);
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
     * 订单支付
     *
     * @param CustName
     * @param CustPhone
     * @param CustCardNo
     * @param OperatorId
     * @param OrderId
     * @return
     */
    public static String PayOrder(String Detailes,
                                  String CustName, String CustPhone,
                                  String CustCardNo, String OperatorId,
                                  String OrderId
    ) {
        String method = "PayOrder";
        soap_Action = nameSpace + method;
        System.out.println("服务地址" + url.replaceAll(" ", "%20"));
        String Order = "{\"CustName\"" + ":" + CustName
                + ",\"CustPhone\":" + CustPhone +
                ",\"CustCardNo\":" + CustCardNo +
                ",\"OperatorId\":" + OperatorId +
                ",\"MachineId\":\"" + MachineId + "\"" +
                ",\"ParkId\":\"" +Login_Variate. ParkId + "\"" +
                ",\"OrderId\":\"" + OrderId + "\"}";

        String json = "{\"Order\":" + Order + "," + "\"PayInfo\":" + Detailes + "}";
        String Enjson = Constants.Scenicspot_code + Constants.Scenicspot_Password + json;
        System.out.println("签名------------->" + Enjson);
        String signature = OtherUtils.EnCode(Enjson);
        request = new SoapObject(nameSpace, method);
        if ("-1".equals(signature)) {
            return "生成签名失败!";
        }
        request.addProperty("parkCode", Constants.Scenicspot_code);//景区编码
        request.addProperty("Data", json);//查询参数
        request.addProperty("sign", signature);//签名
        Thread thread = new Thread() {
            @Override
            public void run() {
                result = null;
                result = WebServiceUtils.getRemoteInfo(request, url, soap_Action);
                System.out.println("result------------>" + result);
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


//    public static String requestTeamOrderForTickets(List<TeamOrderBean.DataBean.TeamOrdersBean> teamOrders, String payTime, String thirdNO){
//        String method = "TeamOrderForTickets";
//        soap_Action = nameSpace + method;
//        LogUtil.i(TAG,"URL地址----->"+url);
//        String Items = null;
//        LogUtil.i(TAG,"teamOrders的大小--->"+teamOrders.size());
//
//        if(teamOrders.size() == 1){
//            TeamOrderBean.DataBean.TeamOrdersBean teamOrdersBean = teamOrders.get(0);
//
//            Items = "[{\"ProductId\"" + ":\"" + teamOrdersBean.getPRODUCTID() + "\"" + ",\"InCount\":\"" + teamOrdersBean.getActualPerson() + "\"}]";//订单明细信息
//
//        }else if(teamOrders.size() == 2){
//
//            Items = "[{\"ProductId\"" + ":\"" + teamOrders.get(0).getPRODUCTID() + "\"" + ",\"InCount\":\"" + teamOrders.get(0).getActualPerson()+"\" },{\"ProductId\"" + ":\"" + teamOrders.get(1).getPRODUCTID() + "\"" + ",\"InCount\":\"" + teamOrders.get(1).getActualPerson() + "\"}]";//订单明细信息
//
//        }else{
//
//            for(int i = 0;i < teamOrders.size();i++){
//                if(i == 0){
//                    Items = Items + "[{\"ProductId\"" + ":\"" + teamOrders.get(0).getPRODUCTID() + "\"" + ",\"InCount\":\"" + teamOrders.get(0).getActualPerson()+"\" },";
//                }else if(i == teamOrders.size()-1){
//                    Items = Items + "{\"ProductId\"" + ":\"" + teamOrders.get(teamOrders.size()-1).getPRODUCTID() + "\"" + ",\"InCount\":\"" + teamOrders.get(teamOrders.size()-1) + "\"}]";//订单明细信息
//                }else{
//                    Items = Items + "{\"ProductId\"" + ":\"" + teamOrders.get(i).getPRODUCTID() + "\"" + ",\"InCount\":\"" + teamOrders.get(i).getActualPerson() + "\"},";
//                }
//
//            }
//
//        }
//
//        TeamOrderBean.DataBean.TeamOrdersBean teamOrdersBean = teamOrders.get(0);
//
//        String OrderPayInfo = "{\"PayType\"" + ":\"" + teamOrdersBean.getPAYTYPE() + "\"" + ",\"PayDirecation\":\"" + 1 + "\"" +
//                ",\"PayAmt\":\"" + teamOrdersBean.getORDERAMT() + "\"" + ",\"ThirdNo\":\"" + thirdNO + "\" }";//支付方式编码
//        String json = "{\"OrderId\"" + ":\"" + teamOrdersBean.getORDERID() + "\",\"OrderNo\":\"" + teamOrdersBean.getORDERNO()
//                + "\",\"IsReprint\":\"" + teamOrdersBean.getISPRINT() +
//                "\",\"Items\":" + Items +
//                ",\"OrderPayInfo\":" + OrderPayInfo +
//                ",\"PayTim\":\"" + payTime +
//                "\",\"ORDERPAYINFO_MACHINEID\":\"" + null +
//                "\",\"ORDERPAYINFO_MACHINECODE\":\"" + null +
//                "\",\"ORDERPAYINFO_OPERATORID\":\"" + null +
//                "\",\"ORDERPAYINFO_OPERATORNAME\":\"" + null + "\"}";
//        String Enjson = Constants.Scenicspot_code + Constants.Scenicspot_Password + json;
//        System.out.println("签名------------->" + Enjson);
//        String signature = OtherUtils.EnCode(Enjson);
//        request = new SoapObject(nameSpace, method);
//        if (signature.equals("-1")) returncard "生成签名失败!";
//        request.addProperty("parkCode", Constants.Scenicspot_code);//景区编码
//        request.addProperty("dataStr", json);//查询参数
//        request.addProperty("sign", signature);//签名
//        Thread thread = new Thread() {
//            @Override
//            public void run() {
//                result = null;
//                result = WebServiceUtils.getRemoteInfo(request, url, soap_Action);
//                System.out.println("result------------>" + result);
//            }
//        };
//        thread.start();
//        try {
//            thread.join();
//        } catch (Exception e) {
//        }
//        if (result != null) {
//            returncard result;
//        }
//        returncard null;
//    }

    public static String GetOrderNos() {
        String method = "GetOrderNo";
        soap_Action = nameSpace + method;
        System.out.println("服务地址" + url.replaceAll(" ", "%20"));
        String json = "{\"MachineId\":\"" + MachineId + "\",\"OrderType\":\"" + 1 + "\"}";
        String Enjson = Constants.Scenicspot_code + Constants.Scenicspot_Password + json;
        System.out.println("签名------------->" + Enjson);
        String signature = OtherUtils.EnCode(Enjson);
        request = new SoapObject(nameSpace, method);
        if ("-1".equals(signature)) {
            return "生成签名失败!";
        }
        request.addProperty("parkCode", Constants.Scenicspot_code);//景区编码
        request.addProperty("Data", json);//查询参数
        request.addProperty("sign", signature);//签名
        Thread thread = new Thread() {
            @Override
            public void run() {
                result = WebServiceUtils.getRemoteInfo(request, url, soap_Action);
                System.out.println("result------------>" + result);
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

    public static String GetRefundOrderNos() {
        String method = "GetOrderNo";
        soap_Action = nameSpace + method;
        System.out.println("服务地址" + url.replaceAll(" ", "%20"));
        String json = "{\"MachineId\":\"" + MachineId + "\",\"OrderType\":\"" + 0 + "\"}";
        String Enjson = Constants.Scenicspot_code + Constants.Scenicspot_Password + json;
        System.out.println("签名------------->" + Enjson);
        String signature = OtherUtils.EnCode(Enjson);
        request = new SoapObject(nameSpace, method);
        if ("-1".equals(signature)) {
            return "生成签名失败!";
        }
        request.addProperty("parkCode", Constants.Scenicspot_code);//景区编码
        request.addProperty("Data", json);//查询参数
        request.addProperty("sign", signature);//签名
        Thread thread = new Thread() {
            @Override
            public void run() {
                result = WebServiceUtils.getRemoteInfo(request, url, soap_Action);
                System.out.println("result------------>" + result);
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
     * 获取会员卡信息
     *
     * @return
     */
    public static String GetVipInfo(String Key) {
        String method = "GetVipInfo";
        soap_Action = nameSpace + method;
        System.out.println("服务地址" + url.replaceAll(" ", "%20"));
        String json = "{\"ParkId\"" + ":\"" + Login_Variate.ParkId + "\"" + ",\"Key\":\"" + Key + "\"}";//支付方式编码
        String Enjson = Constants.Scenicspot_code + Constants.Scenicspot_Password + json;
        System.out.println("签名------------->" + Enjson);
        String signature = OtherUtils.EnCode(Enjson);
        request = new SoapObject(nameSpace, method);
        if ("-1".equals(signature)) {
            return "生成签名失败!";
        }
        request.addProperty("parkCode", Constants.Scenicspot_code);//景区编码
        request.addProperty("Data", json);//查询参数
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
     * 卡状态
     *
     * @return
     */
    public static String SetCardState(String Vipid, String Key) {
        String method = "SetCardState";
        soap_Action = nameSpace + method;
        System.out.println("服务地址" + url.replaceAll(" ", "%20"));
        String json = "{\"ParkId\"" + ":\"" + Login_Variate.ParkId + "\""
                + ",\"Vipid\":\"" + Vipid + "\""
                + ",\"OperatorId\":\"\""
                + ",\"CardType\":\"" + Key + "\"}";//支付方式编码

        String Enjson = Constants.Scenicspot_code + Constants.Scenicspot_Password + json;
        System.out.println("签名------------->" + Enjson);
        String signature = OtherUtils.EnCode(Enjson);
        try {
            request = new SoapObject(nameSpace, method);
            if ("-1".equals(signature)) {
                return "生成签名失败!";
            }
            request.addProperty("parkCode", Constants.Scenicspot_code);//景区编码
            request.addProperty("Data", json);//查询参数
            request.addProperty("sign", signature);//签名
            Thread thread = new Thread() {
                @Override
                public void run() {
                    result = WebServiceUtils.getRemoteInfo(request, url, soap_Action);
                }
            };
            thread.start();
            thread.join();
        } catch (Exception e) {
        }
        if (result != null) {
            return result;
        }
        return null;
    }

    /**
     * 获取卡内余额
     *
     * @return
     */
    public static String GetVipBalance(String vipid) {
        String method = "GetVipBalance";
        soap_Action = nameSpace + method;
        System.out.println("服务地址" + url.replaceAll(" ", "%20"));
        String json = "{\"ParkId\"" + ":\"" + Login_Variate.ParkId + "\"" + ",\"VipId\":\"" + vipid + "\"" + ",\"MchineId\":\"" + MachineId + "\"}";//支付方式编码
        String Enjson = Constants.Scenicspot_code + Constants.Scenicspot_Password + json;
        System.out.println("签名------------->" + Enjson);
        String signature = OtherUtils.EnCode(Enjson);
        request = new SoapObject(nameSpace, method);
        if ("-1".equals(signature)) {
            return "生成签名失败!";
        }
        request.addProperty("parkCode", Constants.Scenicspot_code);//景区编码
        request.addProperty("Data", json);//查询参数
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
     * 插入会员信息
     *
     * @return
     */
    public static String EditVipInfo(String Vipid, String CardLevelId, String CardLevelName, String VipName, String VipSex, String VipPhone, String VipAddress, String VipCertificatesNo, String VipBirthday) {
        String method = "EditVipInfo";
        soap_Action = nameSpace + method;
        System.out.println("服务地址" + url.replaceAll(" ", "%20"));
        String json = "{\"VipId\"" + ":\"" + Vipid + "\""
                + ",\"ParkId\"" + ":\"" + Login_Variate.ParkId + "\""
                + ",\"CardLevelId\":\"" + CardLevelId + "\""
                + ",\"CardLevelName\":\"" + CardLevelName + "\""
                + ",\"VipName\":\"" + VipName + "\"" +
                ",\"VipSex\":\"" + VipSex + "\"" +
                ",\"VipPhone\":\"" + VipPhone + "\"" +
                ",\"VipAddress\":\"" + VipAddress + "\"" +
                ",\"VipChannelId\":\"" + "" + "\"" +
                ",\"VipChannelName\":\"" + "" + "\"" +
                ",\"VipCertificatesNo\":\"" + VipCertificatesNo + "\"" +
                ",\"VipBirthday\":\"" + VipBirthday + "\"" +
                ",\"VipNation\":\"" + "" + "\"" +
                ",\"VipNationality\":\"" + "" + "\"" +
                ",\"VipQQ\":\"" + "" + "\"" +
                ",\"VipWeChat\":\"" + "" + "\"" +
                ",\"VipNickName\":\"\"}";

        String Enjson = Constants.Scenicspot_code + Constants.Scenicspot_Password + json;
        System.out.println("签名------------->" + Enjson);
        String signature = OtherUtils.EnCode(Enjson);
        request = new SoapObject(nameSpace, method);
        if ("-1".equals(signature)) {
            return "生成签名失败!";
        }
        request.addProperty("parkCode", Constants.Scenicspot_code);//景区编码
        request.addProperty("Data", json);//查询参数
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
     * 获取卡级信息
     *
     * @return
     */
    public static String GetLevelInfo() {
        String method = "GetLevelInfo";
        soap_Action = nameSpace + method;
        System.out.println("服务地址" + url.replaceAll(" ", "%20"));
        String json = "{\"ParkId\"" + ":\"" + Login_Variate.ParkId + "\"" + ",\"CardLevelId\":\"\"}";
        String Enjson = Constants.Scenicspot_code + Constants.Scenicspot_Password + json;
        System.out.println("签名------------->" + Enjson);
        String signature = OtherUtils.EnCode(Enjson);
        request = new SoapObject(nameSpace, method);
        if ("-1".equals(signature)) {
            return "生成签名失败!";
        }
        request.addProperty("parkCode", Constants.Scenicspot_code);//景区编码
        request.addProperty("Data", json);//查询参数
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
     * 获取卡级信息
     *
     * @return
     */
    public static String GetSingleLevelInfo(String levelId) {
        String method = "GetLevelInfo";
        soap_Action = nameSpace + method;
        System.out.println("服务地址" + url.replaceAll(" ", "%20"));
        String json = "{\"ParkId\"" + ":\"" + Login_Variate.ParkId + "\"" + ",\"CardLevelId\"" + ":\"" + levelId + "\"" + "}";
        String Enjson = Constants.Scenicspot_code + Constants.Scenicspot_Password + json;
        System.out.println("签名------------->" + Enjson);
        String signature = OtherUtils.EnCode(Enjson);
        request = new SoapObject(nameSpace, method);
        if ("-1".equals(signature)) {
            return "生成签名失败!";
        }
        request.addProperty("parkCode", Constants.Scenicspot_code);//景区编码
        request.addProperty("Data", json);//查询参数
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
     * 获取会员卡折扣信息
     *
     * @return
     */
    public static String GetVipRate() {
        String method = "GetVipRate";
        soap_Action = nameSpace + method;
        System.out.println("服务地址" + url.replaceAll(" ", "%20"));
        String json = "{\"ParkId\"" + ":\"" + Login_Variate.ParkId + "\"" + ",\"MachineId\":\"" + MachineId + "\"" + ",\"LevelId\":\"\"}";//支付方式编码
        String Enjson = Constants.Scenicspot_code + Constants.Scenicspot_Password + json;
        System.out.println("签名------------->" + Enjson);
        String signature = OtherUtils.EnCode(Enjson);
        request = new SoapObject(nameSpace, method);
        if ("-1".equals(signature)) {
            return "生成签名失败!";
        }
        request.addProperty("parkCode", Constants.Scenicspot_code);//景区编码
        request.addProperty("Data", json);//查询参数
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

    //{"ParkId":"00000205","CardLevelId":null,"CardNum":null,"CardId":"12345654368","PageIndex":1,"PageSize":1,"Total":0}

    //获取印刷号

    public static String getResultFromCardPageInfoService(String physicalNo) {

        String method = "GetCardPageInfo";
        soap_Action = nameSpace + method;

        String Items = "{\"ParkId\":\"" +Login_Variate.ParkId+ "\"" + ",\"CardLevelId\"" + ":" + null + ",\"CardNum\"" + ":" + null + ",\"CardId\":\"" + physicalNo + "\"" + ",\"PageIndex\" :\"" + 1 + "\"" + ",\"PageSize\":\"" + 1 + "\"" + ",\"Total\":\"" + 0 + "\"}";


        String signature_str = Constants.Scenicspot_code + Constants.Scenicspot_Password + Items;
        LogUtil.i(TAG, "getResultFromCardPageInfoService----->" + signature_str);

        String signature = OtherUtils.EnCode(signature_str);
        LogUtil.i(TAG, "加密后签名----->" + signature);
        request = new SoapObject(nameSpace, method);
        if ("-1".equals(signature)) {
            return "生成签名失败!";
        }
        request.addProperty("parkCode", Constants.Scenicspot_code);//景区编码
        request.addProperty("Data", Items);//查询参数
        request.addProperty("sign", signature);//签名

        Thread thread = new Thread() {
            @Override
            public void run() {
                result = WebServiceUtils.getRemoteInfo(request, url, soap_Action);
                System.out.println("卡入庫結果result------------>" + result);
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
     * 获取会员卡全部信息
     *
     * @return
     */
    public static String GetVipInfoTogether(String Key) {

        String method = "GetVipInfoTogether";
        soap_Action = nameSpace + method;
        System.out.println("服务地址" + url.replaceAll(" ", "%20"));
        String json = "{\"ParkId\"" + ":\"" + Login_Variate.ParkId + "\"" + ",\"Key\":\"" + Key + "\"" + ",\"MchineId\":\"" + Login_Variate.MachineId + "\"}";//支付方式编码
        String Enjson = Constants.Scenicspot_code + Constants.Scenicspot_Password + json;
        System.out.println("签名------------->" + Enjson);
        String signature = OtherUtils.EnCode(Enjson);
        try {
            request = new SoapObject(nameSpace, method);
            if ("-1".equals(signature)) {
                return "生成签名失败!";
            }
            request.addProperty("parkCode", Constants.Scenicspot_code);//景区编码
            request.addProperty("Data", json);//查询参数
            request.addProperty("sign", signature);//签名
            Thread thread = new Thread() {
                @Override
                public void run() {
                    result = WebServiceUtils.getRemoteInfo(request, url, soap_Action);
                    Log.e("卡信息-----",result);
                }
            };
            thread.start();
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (result != null) {
            return result;
        }
        return null;
    }



}

