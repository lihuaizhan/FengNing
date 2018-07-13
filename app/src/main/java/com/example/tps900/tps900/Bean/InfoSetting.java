package com.example.tps900.tps900.Bean;

/**
 * Created by Administrator on 2017/7/10 0010.
 */

public class InfoSetting {
    /**
     * 支付宝支付
     */
    private String AlipayUrl;

    public String getAlipayUrl() {
        return AlipayUrl;
    }

    public void setAlipayUrl(String alipayUrl) {
        AlipayUrl = alipayUrl;
    }

    public String getWeChatUrl() {
        return WeChatUrl;
    }

    public void setWeChatUrl(String weChatUrl) {
        WeChatUrl = weChatUrl;
    }

    private String appId;
    private String AlipayName;
    private String seller_id;
    /**
     * 微信支付
     */
    private String WeChatUrl;
    private String APPID;
    private String MCHID;
    private String sub_mch_id;
    private String KEY;
    private String APPSECRET;
    private String SSLCERT_PASSWORD;
    private String SSLCERT_PATH;
    /**
     * 线上核销接口
     */
    private String Y_IP_ADDRESS;
    /**
     * 大平台景区编码
     */
    private String Y_NUMBER;
    /**
     * 大平台景区秘钥
     */
    private String Y_SECRET;
    /**
     * 线下核销地址
     */
    private String IP_ADDRESS;
    /**
     * 线下售票地址
     */
    private String S_IP_ADDRESS;
    /**
     * 线下售卖地址
     */
    private String ADDRESS;
    /**
     * 更新服务器地址
     */
    private String UPdate_IP_ADDRESS;
    /**
     * 一卡通接口地址
     */
    private String OneCardInterface;

    @Override
    public String toString() {
        return "InfoSetting{" +
                "appId='" + appId + '\'' +
                ", AlipayName='" + AlipayName + '\'' +
                ", seller_id='" + seller_id + '\'' +
                ", APPID='" + APPID + '\'' +
                ", MCHID='" + MCHID + '\'' +
                ", sub_mch_id='" + sub_mch_id + '\'' +
                ", KEY='" + KEY + '\'' +
                ", APPSECRET='" + APPSECRET + '\'' +
                ", SSLCERT_PASSWORD='" + SSLCERT_PASSWORD + '\'' +
                ", SSLCERT_PATH='" + SSLCERT_PATH + '\'' +
                ", Y_IP_ADDRESS='" + Y_IP_ADDRESS + '\'' +
                ", Y_NUMBER='" + Y_NUMBER + '\'' +
                ", Y_SECRET='" + Y_SECRET + '\'' +
                ", IP_ADDRESS='" + IP_ADDRESS + '\'' +
                ", S_IP_ADDRESS='" + S_IP_ADDRESS + '\'' +
                ", ADDRESS='" + ADDRESS + '\'' +
                ", UPdate_IP_ADDRESS='" + UPdate_IP_ADDRESS + '\'' +
                ", OneCardInterface='" + OneCardInterface + '\'' +
                '}';
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAlipayName() {
        return AlipayName;
    }

    public void setAlipayName(String alipayName) {
        AlipayName = alipayName;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getAPPID() {
        return APPID;
    }

    public void setAPPID(String APPID) {
        this.APPID = APPID;
    }

    public String getMCHID() {
        return MCHID;
    }

    public void setMCHID(String MCHID) {
        this.MCHID = MCHID;
    }

    public String getSub_mch_id() {
        return sub_mch_id;
    }

    public void setSub_mch_id(String sub_mch_id) {
        this.sub_mch_id = sub_mch_id;
    }

    public String getKEY() {
        return KEY;
    }

    public void setKEY(String KEY) {
        this.KEY = KEY;
    }

    public String getAPPSECRET() {
        return APPSECRET;
    }

    public void setAPPSECRET(String APPSECRET) {
        this.APPSECRET = APPSECRET;
    }

    public String getSSLCERT_PASSWORD() {
        return SSLCERT_PASSWORD;
    }

    public void setSSLCERT_PASSWORD(String SSLCERT_PASSWORD) {
        this.SSLCERT_PASSWORD = SSLCERT_PASSWORD;
    }

    public String getSSLCERT_PATH() {
        return SSLCERT_PATH;
    }

    public void setSSLCERT_PATH(String SSLCERT_PATH) {
        this.SSLCERT_PATH = SSLCERT_PATH;
    }

    public String getY_IP_ADDRESS() {
        return Y_IP_ADDRESS;
    }

    public void setY_IP_ADDRESS(String y_IP_ADDRESS) {
        Y_IP_ADDRESS = y_IP_ADDRESS;
    }

    public String getY_NUMBER() {
        return Y_NUMBER;
    }

    public void setY_NUMBER(String y_NUMBER) {
        Y_NUMBER = y_NUMBER;
    }

    public String getY_SECRET() {
        return Y_SECRET;
    }

    public void setY_SECRET(String y_SECRET) {
        Y_SECRET = y_SECRET;
    }

    public String getIP_ADDRESS() {
        return IP_ADDRESS;
    }

    public void setIP_ADDRESS(String IP_ADDRESS) {
        this.IP_ADDRESS = IP_ADDRESS;
    }

    public String getS_IP_ADDRESS() {
        return S_IP_ADDRESS;
    }

    public void setS_IP_ADDRESS(String s_IP_ADDRESS) {
        S_IP_ADDRESS = s_IP_ADDRESS;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getUPdate_IP_ADDRESS() {
        return UPdate_IP_ADDRESS;
    }

    public void setUPdate_IP_ADDRESS(String UPdate_IP_ADDRESS) {
        this.UPdate_IP_ADDRESS = UPdate_IP_ADDRESS;
    }

    public String getOneCardInterface() {
        return OneCardInterface;
    }

    public void setOneCardInterface(String oneCardInterface) {
        OneCardInterface = oneCardInterface;
    }
}
