package com.example.tps900.tps900.Utlis;

import android.util.Xml;

import com.example.tps900.tps900.Bean.InfoSetting;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *信息设置xml解析
 * Created by Administrator on 2017/7/10 0010.
 */

public class InfoSettingParserImpl implements InfoSettingParser {

    @Override
    public List<InfoSetting> parse(InputStream is) throws Exception {
        List<InfoSetting> mList = null;
        InfoSetting infoSetting = null;

        // 由android.util.Xml创建一个XmlPullParser实例
        XmlPullParser xpp = Xml.newPullParser();
        // 设置输入流 并指明编码方式
        xpp.setInput(is,"UTF-8");
        // 产生第一个事件
        int eventType = xpp.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT){
            switch (eventType) {
                // 判断当前事件是否为文档开始事件
                case XmlPullParser.START_DOCUMENT:
                    mList = new ArrayList<InfoSetting>(); // 初始化books集合
                    break;
                // 判断当前事件是否为标签元素开始事件
                case XmlPullParser.START_TAG:
                        if ("InfoSetting".equals(xpp.getName())) {
                            infoSetting = new InfoSetting();
                        } else if ("AlipayUrl".equals(xpp.getName())) {  //支付宝支付
                            eventType = xpp.next();
                            infoSetting.setAlipayUrl(xpp.getText());
                        }else if ("appId".equals(xpp.getName())) {
                            eventType = xpp.next();
                            infoSetting.setAppId(xpp.getText());
                        } else if ("AlipayName".equals(xpp.getName())) {
                            eventType = xpp.next();
                            infoSetting.setAlipayName(xpp.getText());
                        }else if ("seller_id".equals(xpp.getName())) {
                            eventType = xpp.next();
                            infoSetting.setSeller_id(xpp.getText());
                        }else if ("WeChatUrl".equals(xpp.getName())) {  //微信支付
                            eventType = xpp.next();
                            infoSetting.setWeChatUrl(xpp.getText());
                        }else if ("APPID".equals(xpp.getName())) {
                            eventType = xpp.next();
                            infoSetting.setAPPID(xpp.getText());
                        }else if ("MCHID".equals(xpp.getName())) {
                            eventType = xpp.next();
                            infoSetting.setMCHID(xpp.getText());
                        }else if ("sub_mch_id".equals(xpp.getName())) {
                            eventType = xpp.next();
                            infoSetting.setSub_mch_id(xpp.getText());
                        }else if ("KEY".equals(xpp.getName())) {
                            eventType = xpp.next();
                            infoSetting.setKEY(xpp.getText());
                        }else if ("APPSECRET".equals(xpp.getName())) {
                            eventType = xpp.next();
                            infoSetting.setAPPSECRET(xpp.getText());
                        }else if ("SSLCERT_PASSWORD".equals(xpp.getName())) {
                            eventType = xpp.next();
                            infoSetting.setSSLCERT_PASSWORD(xpp.getText());
                        }else if ("SSLCERT_PATH".equals(xpp.getName())) {
                            eventType = xpp.next();
                            infoSetting.setSSLCERT_PATH(xpp.getText());
                        }else if ("Y_IP_ADDRESS".equals(xpp.getName())) { //线上核销接口
                            eventType = xpp.next();
                            infoSetting.setY_IP_ADDRESS(xpp.getText());
                        }else if ("Y_NUMBER".equals(xpp.getName())) { //大平台景区编码
                            eventType = xpp.next();
                            infoSetting.setY_NUMBER(xpp.getText());
                        }else if ("Y_SECRET".equals(xpp.getName())) { //大平台景区秘钥
                            eventType = xpp.next();
                            infoSetting.setY_SECRET(xpp.getText());
                        }else if ("IP_ADDRESS".equals(xpp.getName())) { //线下核销地址
                            eventType = xpp.next();
                            infoSetting.setIP_ADDRESS(xpp.getText());
                        }else if ("S_IP_ADDRESS".equals(xpp.getName())) { //线下售票地址
                            eventType = xpp.next();
                            infoSetting.setS_IP_ADDRESS(xpp.getText());
                        }else if ("ADDRESS".equals(xpp.getName())) { //线下售卖地址
                            eventType = xpp.next();
                            infoSetting.setADDRESS(xpp.getText());
                        }else if ("UPdate_IP_ADDRESS".equals(xpp.getName())) { //更新服务器地址
                            eventType = xpp.next();
                            infoSetting.setUPdate_IP_ADDRESS(xpp.getText());
                        }else if ("OneCardInterface".equals(xpp.getName())) { //一卡通接口地址
                            eventType = xpp.next();
                            infoSetting.setOneCardInterface(xpp.getText());
                        }
                    break;

                // 判断当前事件是否为标签元素结束事件
                case XmlPullParser.END_TAG:
                    if ("InfoSetting".equals(xpp.getName())) { // 判断结束标签元素是否是book
                        mList.add(infoSetting); // 将book添加到books集合
                        infoSetting = null;
                    }
                    break;
                default:
                    break;
            }
            // 进入下一个元素并触发相应事件
            eventType = xpp.next();
        }
        return mList;
    }

    @Override
    public String serialize(List<InfoSetting> beauties) throws Exception {
        return null;
    }
}
