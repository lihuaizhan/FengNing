package com.example.tps900.tps900.Utlis;

import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

/**
 * Created by zxh on 2016/10/16.
 * 调用系统音效
 */

public class Voice {


    public static void success(Context context) {
        //成功调用的是系统通知声音
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(context, notification);
        r.play();
    }


    public static void failed(Context context) {
        //失败调用的是系统手机铃声
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TITLE_COLUMN_INDEX);
        Ringtone r = RingtoneManager.getRingtone(context, notification);
        r.play();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        r.stop();
    }
}
