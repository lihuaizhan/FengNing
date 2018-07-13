package com.example.tps900.tps900.Utlis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.tps900.tps900.Webservice.GsWebServiceUtils.GMB_GETSERVERTIME;

/**
 * Created by zxh on 2016/12/17.
 */

public class TimeUtils {
    public static Date curDate;//系统日期

    /**
     * 获取当前系统时间
     *
     * @return
     */
    public static String time() {
        String strTime = "";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        curDate = new Date(System.currentTimeMillis());
        strTime = formatter.format(curDate);// 获取当前时间
        return strTime;
    }

    /**
     * 获取当前系统时间
     *
     * @return
     */
    public static String time_S() {
        String gmbGetservertime = GMB_GETSERVERTIME();
        if (!"err".equals(gmbGetservertime)) {
            return gmbGetservertime;
        } else {
            String strTime = "";
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            curDate = new Date(System.currentTimeMillis());
            strTime = formatter.format(curDate);// 获取当前时间
            return strTime;
        }
    }

    // 将字符串转为时间戳
    public static String gettime(String user_time) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d;
        try {
            d = sdf.parse(user_time);
            long l = d.getTime();
            String str = String.valueOf(l);
            re_time = str;
        } catch (ParseException e) {
        }
        return re_time;
    }

    public static String Json_Time(String time) {
        time = "\"\\/Date(" + gettime(time) + ")\\/\"";
        return time;
    }
}
