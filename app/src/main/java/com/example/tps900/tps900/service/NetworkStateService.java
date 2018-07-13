package com.example.tps900.tps900.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.example.tps900.tps900.Utlis.Constant;

import static com.example.tps900.tps900.Utlis.Dailog.DismissDialog;
import static com.example.tps900.tps900.Utlis.Dailog.ErrDialog2;
import static com.example.tps900.tps900.Utlis.OtherUtils.sendMessageInfo;

/**
 * 项目名称：PDAXianShang
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2018-04-16 11:38
 * 修改人：zxh
 * 修改时间：2018-04-16 11:38
 * 修改备注：
 */

public class NetworkStateService extends Service {
    private static final String tag = "tag";
    private ConnectivityManager connectivityManager;
    private NetworkInfo info;
    private final int InternetConnection = 1;
    private final int stopDialog = 2;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case InternetConnection:
                    ErrDialog2(Constant.context, "当前无网络\n点击确定设置网络", "1");
                    break;
                case stopDialog:
                    DismissDialog();
                    break;
                default:
                    break;
            }
        }
    };
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        private ConnectivityManager mConnectivityManager;
        private NetworkInfo netInfo;
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                netInfo = mConnectivityManager.getActiveNetworkInfo();
                if (netInfo != null && netInfo.isAvailable()) {
//                    sendMessageInfo(handler, stopDialog);
                    /////////////网络连接
                    if (netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                        /////WiFi网络
                    } else if (netInfo.getType() == ConnectivityManager.TYPE_ETHERNET) {
                        /////有线网络
                    } else if (netInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                        /////////3g网络
                    }
                } else {
                    ////////网络断开
                    sendMessageInfo(handler, InternetConnection);
                }
            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mReceiver, mFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

}
