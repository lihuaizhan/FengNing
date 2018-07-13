package com.example.tps900.tps900.Utlis.PDAN900CardUtils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.tps900.tps900.Utlis.Constant;
import com.example.tps900.tps900.Utlis.ToastUtils;
import com.godfery.pay.HttpUtils;
import com.telpo.tps550.api.TelpoException;
import com.telpo.tps550.api.nfc.Nfc;

import static com.example.tps900.tps900.activity.BaseActivity.progressDialog;

/**
 * 项目名称：PDAXianShang
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2017/12/4 14:43
 * 修改人：zxh
 * 修改时间：2017/12/4 14:43
 * 修改备注：
 */

public class ReadCardUtils {
    private final int CHECK_NFC_TIMEOUT = 1;
    private final int SHOW_NFC_DATA = 2;
    private Nfc nfc;
    private ReadThread readThread;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CHECK_NFC_TIMEOUT: {
                    HttpUtils.exitProgressDialog(progressDialog);
                    ToastUtils.show(Constant.context, "未检测到卡片超时 !");
                }
                break;
                case SHOW_NFC_DATA: {
                    byte[] uid_data = (byte[]) msg.obj;
                    if (uid_data[0] == 0x41) {
                        byte[] uid = new byte[uid_data[5]];

                    } else {
                        ToastUtils.show(Constant.context, "unknow type card!!");
                    }
                }
                break;


                default:
                    break;
            }
        }
    };

    public void DeviceN900() {
        //打开nfc设备
        nfc = new Nfc(Constant.context);
        try {
            nfc.close();
            nfc.open();
        } catch (TelpoException e) {
            e.printStackTrace();
        }
        readThread = new ReadThread();
        readThread.start();
    }

    private class ReadThread extends Thread {
        byte[] nfcData = null;

        @Override
        public void run() {
            try {
                nfcData = nfc.activate(3 * 1000); // 10s
                if (null != nfcData) {
                    handler.sendMessage(handler.obtainMessage(SHOW_NFC_DATA, nfcData));
                } else {
                    Log.d("GetNFC----------->", "Check MagneticCard timeout...");
                    handler.sendMessage(handler.obtainMessage(CHECK_NFC_TIMEOUT, null));
                }
            } catch (TelpoException e) {
                e.printStackTrace();
            }
        }
    }

}
