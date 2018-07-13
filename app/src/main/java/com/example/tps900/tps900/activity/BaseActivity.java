package com.example.tps900.tps900.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.tps900.tps900.Utlis.Constant;
import com.example.tps900.tps900.Utlis.OtherUtils;
import com.example.tps900.tps900.Utlis.ToastUtils;
import com.example.tps900.tps900.service.NetworkStateService;
import com.godfery.pay.HttpUtils;
import com.telpo.tps550.api.TelpoException;
import com.telpo.tps550.api.nfc.Nfc;
import com.telpo.tps550.api.util.StringUtil;

import static com.example.tps900.tps900.Utlis.Constant.context;
import static com.example.tps900.tps900.Utlis.Dailog.ErrDialog2;

/**
 * 项目名称：PDAXianShang
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2017/11/23 15:40
 * 修改人：zxh
 * 修改时间：2017/11/23 15:40
 * 修改备注：
 */

public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";
    //旋转进度条
    public static ProgressDialog progressDialog;
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
                    ToastUtils.show(context, "未检测到卡片超时 !");
                }
                break;
                case SHOW_NFC_DATA: {
                    byte[] uid_data = (byte[]) msg.obj;
                    if (uid_data[0] == 0x41) {
                        byte[] atqa = new byte[2];
                        byte[] sak = new byte[1];
                        byte[] uid = new byte[uid_data[5]];
                        String type = null;
                        System.arraycopy(uid_data, 2, atqa, 0, 2);
                        System.arraycopy(uid_data, 4, sak, 0, 1);
                        System.arraycopy(uid_data, 6, uid, 0, uid_data[5]);
                        HttpUtils.exitProgressDialog(progressDialog);
                        String codeNum = OtherUtils.rfidToHand(StringUtil.toHexString(uid));
                        if (codeNum.startsWith("0")) {
                            codeNum = codeNum.substring(1);
                        }
                        cardCodeInterface.onReadCardOk(codeNum);
                    } else {
                        ToastUtils.show(context, "unknow type card!!");
                    }
                }
                break;
                case 3:
                    ErrDialog2(BaseActivity.this, "当前无网络\n点击确定设置网络", "1");
                    break;
                default:
                    break;
            }
        }
    };
    CardCodeInterface cardCodeInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //防止底部标题栏被顶到上部
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        context = this;
        progressDialog = new ProgressDialog(this);
        setContentView(getLayoutId());
        initView();
        initData();
        registerService();
    }

    /**
     * 设置布局
     *
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 初始化控件
     */
    public abstract void initView();

    /**
     * 操作数据
     */
    public abstract void initData();

    /**
     * 无参跳转当前类不消失
     *
     * @param targetActivityClass 跳转的Activity
     */
    public void openActivity(Class<?> targetActivityClass) {
        Intent intent = new Intent(this, targetActivityClass);
        startActivity(intent);
    }

    /**
     * 有参跳转且当前类不消失
     *
     * @param targetActivityClass 跳转的Activity
     * @param bundle              传参
     */
    public void openActivity(Class<?> targetActivityClass, Bundle bundle) {
        Intent intent = new Intent(this, targetActivityClass);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 有参跳转且当前类不消失
     *
     * @param targetActivityClass 跳转的Activity
     * @param bundle              传参
     * @param requestCode         请求码
     */
    public void openActivity(Class<?> targetActivityClass, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, targetActivityClass);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    /**
     * 有参跳转且当前类不消失
     *
     * @param targetActivityClass 跳转的Activity
     * @param requestCode         请求码
     */
    public void openActivity(Class<?> targetActivityClass, int requestCode) {
        Intent intent = new Intent(this, targetActivityClass);
        startActivityForResult(intent, requestCode);
    }

    /**
     * 无参跳转且当前类消失
     *
     * @param targetActivityClass 跳转的Activity
     */
    public void openActivityAndCloseThis(Class<?> targetActivityClass) {
        openActivity(targetActivityClass);
        finish();
    }

    /**
     * 有参跳转且当前类消失
     *
     * @param targetActivityClass 跳转的Activity
     * @param bundle              传参
     */
    public void openActivityAndCloseThis(Class<?> targetActivityClass, Bundle bundle) {
        openActivity(targetActivityClass, bundle);
        finish();
    }

    /**
     * 设置卡号回调
     *
     * @param cardCodeInterface
     */

    public void setCardInterface(CardCodeInterface cardCodeInterface) {
        this.cardCodeInterface = cardCodeInterface;
    }

    //注册服务
    public void registerService() {
        Intent i = new Intent(Constant.context, NetworkStateService.class);
        startService(i);

    }

    /**
     * 停止服务
     */
    public void stopService() {
        Intent i = new Intent(Constant.context, NetworkStateService.class);
        this.stopService(i);
    }

    /**
     * NFC启动
     *
     * @param context
     */
    public void DeviceN900(Context context) {
        //打开nfc设备
        nfc = new Nfc(context);

        try {
            nfc.close();
            nfc.open();
        } catch (TelpoException e) {
            e.printStackTrace();
            HttpUtils.exitProgressDialog(progressDialog);
        }
        readThread = new ReadThread();
        readThread.start();
    }


    /**
     * addTextChangedListener
     * 监听输入框字体
     *
     * @param editText
     */
    public void addTextChangedListener(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editText.setSelection(s.length());
            }
        });//默认光标在字体最后

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService();
        if (nfc != null) {
            try {
                nfc.close();
            } catch (TelpoException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * NFC读卡线程
     */
    private class ReadThread extends Thread {
        byte[] nfcData = null;

        @Override
        public void run() {
            try {
                nfcData = nfc.activate(3 * 1000);
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

    public interface CardCodeInterface {
        /**
         * 读取卡号成功回调
         *
         * @param cardNo 返回卡号
         */
        void onReadCardOk(String cardNo);

    }


}