package com.example.tps900.tps900.Utlis;

import android.content.Context;

import com.example.tps900.tps900.Bean.InfoSetting;
import com.godfery.pay.PayConstanse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

/**
 * 配置xml读取
 * Created by Administrator on 2017/7/10 0010.
 */

public class ReadXmlUtil {
    //装载InfoSetting类型的链表，其内容由XML文件解析得到
    private static List<InfoSetting> infoSettingList;
    //xml读取结果
    public static String result = "";

    public static boolean readXml( String xmlBasePath, String xmlName){
        try {
            File f = new File(xmlBasePath+xmlName);
            if(!f.exists()) {
                copy(Constant.context,"infosetting.xml",xmlBasePath,xmlName);
            }
            InputStream sdcardis = new FileInputStream(f);
            //初始化自定义的实现类BeautyParserImpl
            InfoSettingParserImpl pbp = new InfoSettingParserImpl();
            //调用pbp的parse()方法，将输入流传进去解析，返回的链表结果赋给beautyList
            infoSettingList = pbp.parse(sdcardis);
            for (InfoSetting b : infoSettingList) {
                result += b.toString();
                //支付宝支付
                PayConstanse.AlipayUrl = b.getAlipayUrl();
                PayConstanse.appId = b.getAppId();
                PayConstanse.AlipayName = b.getAlipayName();
                PayConstanse.seller_id = b.getSeller_id();
                //微信支付
                PayConstanse.WeChatUrl = b.getWeChatUrl();
                PayConstanse.APPID = b.getAPPID();
                PayConstanse.MCHID = b.getMCHID();
                PayConstanse.sub_mch_id = b.getSub_mch_id();
                PayConstanse.KEY = b.getKEY();
                PayConstanse.APPSECRET = b.getAPPSECRET();
                PayConstanse.SSLCERT_PASSWORD = b.getSSLCERT_PASSWORD();
                PayConstanse.SSLCERT_PATH = b.getSSLCERT_PATH();
                //线上核销接口
                Constant.Y_IP_ADDRESS = b.getY_IP_ADDRESS();
                //大平台景区编码
                Constant.Y_NUMBER = b.getY_NUMBER();
                //大平台景区秘钥
                Constant.Y_SECRET = b.getY_SECRET();
                //线下核销地址
                Constant.IP_ADDRESS = b.getIP_ADDRESS();
                //线下售票地址
                Constant.S_IP_ADDRESS = b.getS_IP_ADDRESS();
                //线下售卖地址
                Constant.ADDRESS = b.getADDRESS();
                //更新服务器地址
                Constant.UPdate_IP_ADDRESS = b.getUPdate_IP_ADDRESS();
                //一卡通接口地址
                Constant.OneCardInterface = b.getOneCardInterface();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *
     * @param myContext
     * @param ASSETS_NAME 要复制的文件名
     * @param savePath 要保存的路径
     * @param saveName 复制后的文件名
     *  testCopy(Context context)是一个测试例子。
     */

    public static void copy(Context myContext, String ASSETS_NAME,
                            String savePath, String saveName) {
        String filename = savePath + "/" + saveName;

        File dir = new File(savePath);
        // 如果目录不中存在，创建这个目录
        if (!dir.exists()) {
            dir.mkdir();
        }
        try {
            if (!(new File(filename)).exists()) {
                InputStream is = myContext.getResources().getAssets()
                        .open(ASSETS_NAME);
                FileOutputStream fos = new FileOutputStream(filename);
                byte[] buffer = new byte[7168];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
