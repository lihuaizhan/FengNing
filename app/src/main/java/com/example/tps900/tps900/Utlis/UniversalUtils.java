package com.example.tps900.tps900.Utlis;

import android.content.Context;
import android.telephony.TelephonyManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by zxh on 2016/12/2.
 */

public class UniversalUtils {


    public static Date curDate;
    public static Random random;
    public static String rado;

    //获取随机数
    public static String codeH(Context context) {
        String code2 = "";
        //获取当前系统时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        curDate = new Date(System.currentTimeMillis());
        String data = formatter.format(curDate);// 获取当前时间
        //获取随机数
        random = new Random();// 随机数
        rado = random.nextInt(1000) + "";// s+随机数
        System.out.println("随机数----------->" + rado);
        //获取设备号id
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String device_id = tm.getDeviceId();
        String code = data + rado + device_id;
        int a;
        int b;
        int c;
        int d;
        int e;
        int f;
        int g;
//     for (int i=code.length() ;i>=8 ;i--){

        if (code.length() >30) {
            a = Integer.valueOf(code.substring(0, 8));//8位
            b = Integer.valueOf(code.substring(9, 17));//8位
            c = Integer.valueOf(code.substring(18, 26));//8位
            g = Integer.valueOf(code.substring(27, code.length() - 1));//位
            d = a + b + c + g;
            code2 = t1(String.valueOf(d));


            System.out.println("g--------0------------->" + d + "\ncode-----1->" + code2);
            System.out.println("前八位是" + data);
            if (code2.length() > 8) {
                d = Integer.valueOf(code2.substring(0, 8));
                code2 = t1(String.valueOf(d));
                System.out.println("code2-----------2----------->\n" + code2);
            } else {
                code2 = t1(String.valueOf(d));
                System.out.println("code2---------3----->\n" + code2);
            }
        } else {
            e = Integer.valueOf(code.substring(0, 8));
            f = Integer.valueOf(code.substring(9, code.length() - 8));
            g = e + f;
            code2 = t1(String.valueOf(g));
            System.out.println("g---------------------->" + g + "\ncode------->" + code2);
        }
        return code2;
    }

    // 将字母转换成数字_1
    public static String t1(String input) {
        String reg = "[a-zA-Z]";
        StringBuffer strBuf = new StringBuffer();
        input = input.toLowerCase();
        if (null != input && !"".equals(input)) {
            for (char c : input.toCharArray()) {
                if (String.valueOf(c).matches(reg)) {
                    strBuf.append(c - 96);
                } else {
                    strBuf.append(c);
                }
            }
            return strBuf.toString();
        } else {
            return input;
        }
    }
}
