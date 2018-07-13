package com.example.tps900.tps900.model;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.tps900.tps900.Bean.CategoryBean;
import com.example.tps900.tps900.Bean.DetailBean;
import com.example.tps900.tps900.Bean.DeviceSaleBean;
import com.example.tps900.tps900.Bean.FormulasBean;
import com.example.tps900.tps900.Bean.GetDeviceTicketBean;
import com.example.tps900.tps900.Bean.LoginBean;
import com.example.tps900.tps900.Bean.Login_entity;
import com.example.tps900.tps900.Bean.SalePayCodeBean;
import com.example.tps900.tps900.Bean.ZoneInfoBean;
import com.example.tps900.tps900.Utlis.Constant;
import com.example.tps900.tps900.Utlis.Dailog;
import com.example.tps900.tps900.Utlis.FileUtil;
import com.example.tps900.tps900.Utlis.MD5Encoder;
import com.example.tps900.tps900.Utlis.PinyinUtils;
import com.example.tps900.tps900.Utlis.ReadXmlUtil;
import com.example.tps900.tps900.Utlis.ThreadPoolUtils;
import com.example.tps900.tps900.Utlis.Updata_Bean;
import com.example.tps900.tps900.WEBSERVICE_Utils.Constants;
import com.example.tps900.tps900.WEBSERVICE_Utils.JsonUtils;
import com.example.tps900.tps900.WEBSERVICE_Utils.Login_Variate;
import com.example.tps900.tps900.api.ApiMannger;
import com.example.tps900.tps900.contract.LoginContract;
import com.godfery.Sqlite.DBUtils;
import com.godfery.Utils.ToastUtils;
import com.godfery.pay.HttpUtils;

import java.io.File;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.tps900.tps900.Utlis.Dailog.ErrDialog2;
import static com.example.tps900.tps900.Utlis.OtherUtils.EnCode;
import static com.example.tps900.tps900.Utlis.OtherUtils.getUpdataTime;
import static com.example.tps900.tps900.Webservice.GsWebServiceUtils.GetDeviceInfo;
import static com.example.tps900.tps900.Webservice.GsWebServiceUtils.GetTerminalFormula;
import static com.example.tps900.tps900.Webservice.GsWebServiceUtils.GetTerminalInfo;
import static com.example.tps900.tps900.Webservice.GsWebServiceUtils.GetTerminalPayMode;
import static com.example.tps900.tps900.Webservice.GsWebServiceUtils.GetTerminalTicket;
import static com.example.tps900.tps900.Webservice.GsWebServiceUtils.getDetailedFoodsInfo;
import static com.example.tps900.tps900.Webservice.GsWebServiceUtils.getDetailedGoodsInfo;
import static com.example.tps900.tps900.Webservice.GsWebServiceUtils.getFoodsCategories;
import static com.example.tps900.tps900.Webservice.GsWebServiceUtils.getGoodsCategories;
import static com.example.tps900.tps900.Webservice.GsWebServiceUtils.getZoneInfo;
import static com.example.tps900.tps900.Webservice.GsWebServiceUtils.login;
import static com.example.tps900.tps900.activity.BaseActivity.progressDialog;

/**
 * 项目名称：PDAXianShang
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2017/11/24 9:24
 * 修改人：zxh
 * 修改时间：2017/11/24 9:24
 * 修改备注：
 *
 * @author zxh
 */

public class LoginModel implements LoginContract.Model {
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int state = msg.what;
            String info = msg.obj.toString();
            switch (state) {
                case -1:
                    HttpUtils.exitProgressDialog(progressDialog);
                    if (!TextUtils.isEmpty(info)) {
                        ToastUtils.show(Constant.context, info);
                        Dailog.ErrDialog(Constant.context, "", info);
                    }
                    break;
                case 1:
                    ErrDialog2(Constant.context, info, "1");
                    break;
                case 2:
                    Dailog.ErrDialog(Constant.context, "退出", info);
                    break;
                default:
                    break;
            }
        }
    };
    DataCallBack dataCallBack;

    public LoginModel(DataCallBack dataCallBack) {
        this.dataCallBack = dataCallBack;
    }

    /**
     * app更新方法
     */
    @Override
    public void getM_UpdataApp() {
        String signature = Constant.UPdate_APPName + "@" + EnCode(Constant.UPdate_APPName + getUpdataTime());
        Log.d("LoginModel", signature);
        ApiMannger.getApiService().getUpdataAPP(signature).enqueue(new Callback<Updata_Bean>() {
            @Override
            public void onResponse(Call<Updata_Bean> call, Response<Updata_Bean> response) {
                dataCallBack.onSuccess(response.body().success);
            }

            @Override
            public void onFailure(Call<Updata_Bean> call, Throwable t) {
                dataCallBack.onError(t.getMessage());
                Log.d("LoginModel", t.getMessage());
            }
        });
    }


    /**
     * 根据用户名密码登录
     * 根据登录的信息获取门票类别和门票详情
     *
     * @param userName
     * @param passWord
     */
    @Override
    public void getM_logininfoandticketinfo(final String userName, final String passWord) {
        if (userName.isEmpty()) {
            ToastUtils.show(Constant.context, "用户名不能为空!");
            return;
        } else if (passWord.isEmpty()) {
            ToastUtils.show(Constant.context, "密码不能为空!");
            return;
        }
        Constant.USERNAME = userName;
        //弹出进度条
        HttpUtils.showProgressDialog(progressDialog);
        GetGoodsInfo_X(userName, passWord);
    }


    /**
     * 读取Setting设置xml文件
     */
    @Override
    public void getM_readSettingXml() {
        ThreadPoolUtils.runTaskInThread(new Runnable() {
            @Override
            public void run() {
                String xmlPath = FileUtil.getDirPath("xml") + File.separator;
                String xmlName = "setting.xml";
                boolean isOK = ReadXmlUtil.readXml(xmlPath, xmlName);
                if (isOK) {
                    Log.e("xml", "xml文件配置信息如下:\n" + ReadXmlUtil.result);
                    ReadXmlUtil.result = "";
                } else {
                    sendMessageInfo(2, "本地配置文件读取失败!");
                }
            }
        });
    }

    /**
     * 线下获取商品信息
     *
     * @param password
     * @param userName
     */
    @Override
    public void GetGoodsInfo_X(final String userName, String password) {
        try {
            HttpUtils.showProgressDialog(progressDialog);
            if (userName.isEmpty()) {
                ToastUtils.show(Constant.context, "用户名不能为空!");
                return;
            } else if (password.isEmpty()) {
                ToastUtils.show(Constant.context, "密码不能为空!");
                return;
            }
            final String encode_password = MD5Encoder.encode(password);
            Log.e("LoginModel", encode_password);
            ThreadPoolUtils.runTaskInThread(new Runnable() {
                @Override
                public void run() {
                    Constant.USERNAME = userName;
                    DBUtils.execSQL("delete  from GzoneComm");
                    DBUtils.execSQL("delete  from CommClass");
                    //TODO 登录
                    String getLogin = GetLogin(userName, encode_password);
                    Log.e("登录", getLogin);
                    if (!"OK".equals(getLogin)) {
                        sendMessageInfo(-1, "获取登录信息失败\n" + getLogin);
                    } else {
                        String zoneInfo = GetZoneInfo(Constant.TERMINALNAME, "商品");
                        if (!"OK".equals(zoneInfo)) {
                            sendMessageInfo(-1, "获取商品营业点信息失败\n" + zoneInfo);
                        } else {
                            String food_zoneInfo = GetZoneInfo(Constant.Food_TERMINALNAME, "餐饮");
                            if (!"OK".equals(food_zoneInfo)) {
                                sendMessageInfo(-1, "获取餐饮营业点信息失败\n" + zoneInfo);
                            } else {
                                String getgoodsCategories = GetgoodsCategories();
                                if (!"OK".equals(getgoodsCategories)) {
                                    sendMessageInfo(-1, "获取商品信息失败\n" + getgoodsCategories);
                                } else {
                                    String getfoodsCategories = GetfoodsCategories();
                                    if (!"OK".equals(getfoodsCategories)) {
                                        sendMessageInfo(-1, "获取餐饮信息失败\n" + getfoodsCategories);
                                    } else {
                                        Constants.IsFoods = true;
                                        Constants.IsGoods = true;
                                        if (Constant.lineWriteOff && Constant.lineTicket) {
                                            //线下核销和线下售卖门票
                                            getGetTerminalInfo();
                                        } else if (Constant.lineWriteOff && !Constant.lineTicket) {
                                            //线下核销
                                            getLineWriteOff();
                                        } else if (!Constant.lineWriteOff && Constant.lineTicket) {
                                            //线下门票售卖
                                            getLineTicket();
                                        } else if (!Constant.lineWriteOff && !Constant.lineTicket) {
                                            //直接进入主界面
                                            dataCallBack.onloginOK();
                                        }

                                    }
                                }
                            }
                        }
                    }
                }
            });
        } catch (Exception e) {
        }
    }

    /**
     * 线上获取门票信息
     *
     * @param passWord
     * @param userName
     */

    public void GetGoodsInfo_Y(final String userName, final String passWord) {
        ThreadPoolUtils.runTaskInThread(new Runnable() {
            @Override
            public void run() {
                boolean loginJson = JsonUtils.Login_Json(userName, passWord);
                DBUtils.execSQL("delete  from TicketType");
                DBUtils.execSQL("delete  from Tickets");
                DBUtils.execSQL("delete  from LevelInfo");
                if (loginJson) {
                    boolean gettickettypeJson = JsonUtils.GetTicketType_json(Login_Variate.MachineId, null);
                    if (gettickettypeJson) {
                        boolean getticketJson = JsonUtils.GetTicket_json(Login_Variate.MachineId, null, null);
                        if (getticketJson) {
//                            String getLevelInfo = JsonUtils.GetLevelInfo_json();
//                            if ("err".equals(getLevelInfo)) {
//                                sendMessageInfo(-1, "数据加载失败,请求异常，请查看配置或者网络");
//                            } else if ("LevelInfoErr".equals(getLevelInfo)) {
//                                sendMessageInfo(-1, "卡集信息数据加载失败,请重新启动或查看配置参数");
//                            } else {
//                                dataCallBack.onloginOK();
//                            }
                            dataCallBack.onloginOK();
                        } else {
                            sendMessageInfo(-1, Constants.ConnectErr);
                        }

                    } else {
                        sendMessageInfo(-1, Constants.ConnectErr);
                    }
                } else {
                    sendMessageInfo(-1, Constants.ConnectErr);
                }
            }
        });
    }

    /**
     * 获取线下检票和售票信息
     */
    @Override
    public void getGetTerminalInfo() {
        try {

            String getS_ticket = getTerminalInfo();
            Log.e("售票信息", getS_ticket + Constant.versionNumber);
            if (!"OK".equals(getS_ticket)) {
                sendMessageInfo(-1, "获取售票终端信息失败\n" + getS_ticket);
                Log.e("LoginModel", "售票终端失败" + Constant.versionNumber);
            } else {
                //TODO 检票终端信息
                String getticketDevice = getticketDevice();
                Log.e("zxh检票终端信息", getticketDevice);
                if ("OK".equals(getticketDevice)) {
                    //TODO 门票方案
                    String Formula = Formulas();
                    Log.e("zxh门票方案", Formula);
                    if ("OK".equals(Formula)) {
                        //TODO 终端可售门票
                        String getTerminalTicket = getTerminalTicket();
                        Log.e("zxh终端可售门票", getTerminalTicket);
                        if ("OK".equals(getTerminalTicket)) {
                            //TODO 支付方式
                            String TerminalPayMode = TerminalPayMode();
                            Log.e("zxh支付方式", TerminalPayMode);
                            if ("OK".equals(TerminalPayMode)) {
//                                        //TODO 所有数据获取成功
                                dataCallBack.onloginOK();
                            } else if ("err".equals(TerminalPayMode)) {
                                sendMessageInfo(-1, "获取可售支付方式异常请查网络\n或者检查一下配置");
                            } else {
                                sendMessageInfo(-1, "获取可售支付方式失败:\n" + TerminalPayMode);
                            }
                        } else if ("err".equals(getTerminalTicket)) {
                            sendMessageInfo(-1, "获取可售门票信息数据异常请查网络\n或者检查一下配置");
                        } else {
                            sendMessageInfo(-1, "获取可售门票信息失败:\n" + getTerminalTicket);
                        }
                    } else if ("err".equals(Formula)) {
                        sendMessageInfo(-1, "请求失败:\n" + "获取门票方案数据异常请查网络\n或者检查一下配置");
                    } else {
                        sendMessageInfo(-1, "获取门票方案失败:\n" + Formula);
                    }
                } else if ("err".equals(getticketDevice)) {
                    sendMessageInfo(-1, "获取检票终端数据异常请查网络\n或者检查一下配置");
                }


            }
        } catch (Exception e) {
        }
    }

    /**
     * 线下核销
     */
    @Override
    public void getLineWriteOff() {
        try {
            ThreadPoolUtils.runTaskInThread(new Runnable() {
                @Override
                public void run() {
                    //TODO 检票终端信息
                    String getticketDevice = getticketDevice();
                    Log.e("zxh检票终端信息", getticketDevice);
                    if ("OK".equals(getticketDevice)) {
                        dataCallBack.onloginOK();
                    } else if ("err".equals(getticketDevice)) {
                        sendMessageInfo(-1, "获取检票终端数据异常请查网络\n或者检查一下配置");
                    }
                }
            });
        } catch (Exception e) {
        }
    }

    /**
     * 线下门票售卖
     */
    @Override
    public void getLineTicket() {
        try {
            ThreadPoolUtils.runTaskInThread(new Runnable() {
                @Override
                public void run() {
                    //TODO 售票终端信息
                    String getS_ticket = getTerminalInfo();
                    Log.e("售票信息", getS_ticket);
                    if (!"OK".equals(getS_ticket)) {
                        sendMessageInfo(-1, "获取售票终端信息失败\n" + getS_ticket);
                    } else {
                        //TODO 门票方案
                        String Formula = Formulas();
                        Log.e("zxh门票方案", Formula);
                        if ("OK".equals(Formula)) {
                            //TODO 终端可售门票
                            String getTerminalTicket = getTerminalTicket();
                            Log.e("zxh终端可售门票", getTerminalTicket);
                            if ("OK".equals(getTerminalTicket)) {
                                //TODO 支付方式
                                String TerminalPayMode = TerminalPayMode();
                                Log.e("zxh支付方式", TerminalPayMode);
                                if ("OK".equals(TerminalPayMode)) {
                                    //TODO 所有数据获取成功
                                    dataCallBack.onloginOK();
                                } else if ("err".equals(TerminalPayMode)) {
                                    sendMessageInfo(-1, "获取可售支付方式异常请查网络\n或者检查一下配置");
                                } else {
                                    sendMessageInfo(-1, "请求失败:\n" + TerminalPayMode);
                                }
                            } else if ("err".equals(getTerminalTicket)) {
                                sendMessageInfo(-1, "获取可售门票方案数据异常请查网络\n或者检查一下配置");
                            } else {
                                sendMessageInfo(-1, "请求失败:\n" + getTerminalTicket);
                            }
                        } else if ("err".equals(Formula)) {
                            sendMessageInfo(-1, "请求失败:\n" + "获取检票终端数据异常请查网络\n或者检查一下配置");
                        } else {
                            sendMessageInfo(-1, "请求失败:\n" + Formula);
                        }
                    }
                }
            });
        } catch (Exception e) {
        }
    }

    //门票方案
    public String Formulas() {
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

    //获取可售门票信息
    public String getTerminalTicket() {
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

    //获取可支付方式
    public String TerminalPayMode() {
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

    //售票终端信息
    public String getTerminalInfo() {
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

    //检票终端信息
    public String getticketDevice() {
        String getticketDevice = "";
        try {
            //TODO 检票终端信息
            String getticket = GetDeviceInfo();
            if ("err".equals(getticket)) {
                return getticketDevice = "err";
            } else {
                Login_entity le = JSON.parseObject(getticket, Login_entity.class);
                if (le.FLAG = true) {
                    List<Login_entity.DevicesBean> devices = le.getDevices();
                    for (int i = 0; i < devices.size(); i++) {
                        Constant.NDEVICEID = devices.get(i).getNDEVICEID();//设备ID
                        Constant.SDEVICENAME = devices.get(i).getSDEVICENAME();//设备名称
                        Constant.SDEVICEIP = devices.get(i).getSDEVICEIP();//设备IP
                        Constant.NPARKID = devices.get(i).getNPARKID();//景区ID
                        Constant.SPARKNAME = devices.get(i).getSPARKNAME();//景区名称
                    }
                    getticketDevice = "OK";
                } else {
                    return le.ERRO;
                }
            }
        } catch (Exception e) {

        }
        return getticketDevice;
    }

    //线下登录信息
    public String GetLogin(String userName, String password) {
        String geLogin = "";
        try {
            String getLogin = login(userName, password);
            Log.e("登录", getLogin);
            if ("err".equals(getLogin)) {
                geLogin = "err";
            } else {
                LoginBean loginBean = JSON.parseObject(getLogin, LoginBean.class);
                if (loginBean.isSuccess) {
                    if (loginBean.dt.size() > 0) {
                        for (int i = 0; i < loginBean.dt.size(); i++) {
                            Constant.EMPID = String.valueOf(loginBean.dt.get(i).EMP_IDX);
                            Constant.EMPNAME = loginBean.dt.get(i).EMP_NAMEX;
                        }
                        geLogin = "OK";
                    } else {

                        if (loginBean.error == null) {
                            geLogin = "获取登录信息失败";
                        } else {
                            geLogin = loginBean.error;
                        }

                    }
                } else {
                    if (loginBean.error == null) {
                        return "获取登录信息失败";
                    }
                    return loginBean.error;
                }
            }
        } catch (Exception e) {

        }
        return geLogin;
    }

    //获取营业点信息  传入终端id
    public String GetZoneInfo(String name, String type) {
        String gezoneInfo = "";
        try {
            //TODO 获取营业点信息
            String zoneInfo = getZoneInfo(name);
            Log.e("获取营业点信息", zoneInfo);
            if ("err".equals(zoneInfo)) {
                gezoneInfo = "err";
            } else {
                ZoneInfoBean zoninfobean = JSON.parseObject(zoneInfo, ZoneInfoBean.class);
                if (zoninfobean.isSuccess && zoninfobean.dt.size() > 0) {
                    if (type.equals("商品")) {
                        for (int i = 0; i < zoninfobean.dt.size(); i++) {
                            Constant.ZONEID = zoninfobean.dt.get(i).NGZONEID;
                            Constant.ZONENAME = zoninfobean.dt.get(i).SGZONENAME;
                            Constant.TERMINALID = zoninfobean.dt.get(i).NTERMINALID;
                        }
                    } else if (type.equals("餐饮")) {
                        for (int i = 0; i < zoninfobean.dt.size(); i++) {
                            Constant.Food_ZONEID = zoninfobean.dt.get(i).NGZONEID;
                            Constant.Food_ZONENAME = zoninfobean.dt.get(i).SGZONENAME;
                            Constant.Food_TERMINALID = zoninfobean.dt.get(i).NTERMINALID;
                        }
                    }
                    gezoneInfo = "OK";
                } else {
                    return zoninfobean.error;
                }
            }
        } catch (Exception e) {

        }
        return gezoneInfo;
    }

    //获取线下商品列表和商品详情
    public String GetgoodsCategories() {
        String goodInfo = "";
        try {
            //TODO 根据 营业点ID 获取 商品分类信息
            String goodsCategories = getGoodsCategories(Constant.ZONEID);
            Log.e("商品分类信息", goodsCategories);
            CategoryBean catebean = JSON.parseObject(goodsCategories, CategoryBean.class);
            if (catebean.isSuccess) {
                if (catebean.dt.size() > 0) {
                    for (int i = 0; i < catebean.dt.size(); i++) {
                        String sql = "insert into CommClass(NCLASSID,CODE,SCLASSNAME,TYPE) values(" +
                                "\"" + catebean.dt.get(i).NCLASSID + "\"," +
                                "\"" + catebean.dt.get(i).CODE + "\"," +
                                "\"" + catebean.dt.get(i).SCLASSNAME + "\"," +
                                "\"" + 1 + "\"" +
                                ")";
                        DBUtils.execSQL(sql);
                    }
                    //TODO 获取商品详情
                    for (int m = 0; m < catebean.dt.size(); m++) {
                        CategoryBean.DtBean dtBean = catebean.dt.get(m);
                        //TODO 访问商品详情
                        String detailedGoodsInfo = getDetailedGoodsInfo(Constant.ZONEID, dtBean.getNCLASSID());
                        Log.e("访问商品详情", detailedGoodsInfo);
                        DetailBean datailBean = JSON.parseObject(detailedGoodsInfo, DetailBean.class);
                        if (datailBean.isSuccess) {
                            if (datailBean.dt.size() > 0) {
                                for (int i = 0; i < datailBean.dt.size(); i++) {
                                    String pingYin = PinyinUtils.getPingYin(datailBean.dt.get(i).VCOMMYNAME);
                                    String firstSpell = PinyinUtils.getFirstSpell(datailBean.dt.get(i).VCOMMYNAME);
                                    String sql = "insert into GzoneComm(TID,NCOMMID,classID,VCOMMBARCODE,VCOMMCODING,VCOMMYNAME,PRICE,NPCOMMID,PNUMBER,SIMPLIFY,ATTENTION,TYPE,NCOMMISINVENTORY) values(" +
                                            "\"" + datailBean.dt.get(i).NCOMMID + datailBean.dt.get(i).NPCOMMID + "\"," +
                                            "\"" + datailBean.dt.get(i).NCOMMID + "\",\"" + dtBean.getNCLASSID() + "\"," +
                                            "\"" + datailBean.dt.get(i).VCOMMBARCODE + "\"," +
                                            "\"" + datailBean.dt.get(i).VCOMMCODING + "\"," +
                                            "\"" + datailBean.dt.get(i).VCOMMYNAME + "\"," +
                                            "\"" + datailBean.dt.get(i).PRICE + "\"," +
                                            "\"" + datailBean.dt.get(i).NPCOMMID + "\"," +
                                            "\"" + datailBean.dt.get(i).PNUMBER + "\"," +
                                            "\"" + firstSpell + "\"," +
                                            "\"" + pingYin + "\"," +
                                            "\"" + 1 + "\"," +
                                            "\"" + datailBean.dt.get(i).NCOMMISINVENTORY + "\"" +
                                            ")";
                                    DBUtils.execSQL(sql);
                                }

                            } else {
                                goodInfo = "获取商品详情数据为空\n" + datailBean.error;
                                break;
                            }
                        } else {
                            goodInfo = "获取商品详情失败\n" + datailBean.error;
                            break;
                        }
                    }

                    goodInfo = "OK";
                }
            } else {
                goodInfo = "获取商品分类信息失败" + catebean.error;
            }
        } catch (Exception e) {

        }
        return goodInfo;
    }

    //获取线下餐饮列表和餐饮详情
    public String GetfoodsCategories() {
        String goodInfo = "";
        try {
            //TODO 根据 营业点ID 获取 餐饮分类信息
            String goodsCategories = getFoodsCategories(Constant.Food_ZONEID);
            Log.e("商品分类信息", goodsCategories);
            CategoryBean catebean = JSON.parseObject(goodsCategories, CategoryBean.class);
            if (catebean.isSuccess) {
                if (catebean.dt.size() > 0) {
                    for (int i = 0; i < catebean.dt.size(); i++) {
                        String sql = "insert into CommClass(NCLASSID,CODE,SCLASSNAME,TYPE) values(" +
                                "\"" + catebean.dt.get(i).NCLASSID + "\"," +
                                "\"" + catebean.dt.get(i).CODE + "\"," +
                                "\"" + catebean.dt.get(i).SCLASSNAME + "\"," +
                                "\"" + 2 + "\"" +
                                ")";
                        DBUtils.execSQL(sql);
                    }
                    //TODO 访问餐饮详情
                    for (int m = 0; m < catebean.dt.size(); m++) {
                        CategoryBean.DtBean dtBean = catebean.dt.get(m);
                        //TODO 访问餐饮详情
                        String detailedGoodsInfo = getDetailedFoodsInfo(Constant.Food_ZONEID, dtBean.getNCLASSID());
                        Log.e("访问餐饮详情", detailedGoodsInfo);
                        DetailBean datailBean = JSON.parseObject(detailedGoodsInfo, DetailBean.class);
                        if (datailBean.isSuccess) {
                            if (datailBean.dt.size() > 0) {
                                for (int i = 0; i < datailBean.dt.size(); i++) {
                                    String pingYin = PinyinUtils.getPingYin(datailBean.dt.get(i).VCOMMYNAME);
                                    String firstSpell = PinyinUtils.getFirstSpell(datailBean.dt.get(i).VCOMMYNAME);
                                    String sql = "insert into GzoneComm(TID,NCOMMID,classID,VCOMMBARCODE,VCOMMCODING,VCOMMYNAME,PRICE,NPCOMMID,PNUMBER,SIMPLIFY,ATTENTION,TYPE,NCOMMISINVENTORY) values(" +
                                            "\"" + datailBean.dt.get(i).NCOMMID + datailBean.dt.get(i).NPCOMMID + "\"," +
                                            "\"" + datailBean.dt.get(i).NCOMMID + "\",\"" + dtBean.getNCLASSID() + "\"," +
                                            "\"" + datailBean.dt.get(i).VCOMMBARCODE + "\"," +
                                            "\"" + datailBean.dt.get(i).VCOMMCODING + "\"," +
                                            "\"" + datailBean.dt.get(i).VCOMMYNAME + "\"," +
                                            "\"" + datailBean.dt.get(i).PRICE + "\"," +
                                            "\"" + datailBean.dt.get(i).NPCOMMID + "\"," +
                                            "\"" + datailBean.dt.get(i).PNUMBER + "\"," +
                                            "\"" + firstSpell + "\"," +
                                            "\"" + pingYin + "\"," +
                                            "\"" + 2 + "\"," +
                                            "\"" + datailBean.dt.get(i).NCOMMISINVENTORY + "\"" +
                                            ")";
                                    DBUtils.execSQL(sql);
                                }

                            } else {
                                goodInfo = "获取餐饮详情数据为空\n" + datailBean.error;
                                break;
                            }
                        } else {
                            goodInfo = "获取餐饮详情失败\n" + datailBean.error;
                            break;
                        }
                    }

                    goodInfo = "OK";
                }
            } else {
                goodInfo = "获取餐饮分类信息失败" + catebean.error;
            }
        } catch (Exception e) {

        }
        return goodInfo;
    }

    /**
     * 给handler发送错误信息
     *
     * @param caseNum 发送的数字
     * @param msgInfo 发送的信息
     */
    public void sendMessageInfo(int caseNum, String msgInfo) {
        Message msg = Message.obtain();
        msg.what = caseNum;
        msg.obj = msgInfo;
        handler.sendMessage(msg);
    }

    /**
     * 接口数据回调类
     */
    public interface DataCallBack {
        /**
         * 接口成功回调数据
         *
         * @param updataBeanList 返回数据集合
         */
        void onSuccess(List<Updata_Bean.SuccessBean> updataBeanList);

        /**
         * 登录成功
         */
        void onloginOK();

        /**
         * 接口返回失败的回调
         *
         * @param Err
         */
        void onError(String Err);

    }

}
