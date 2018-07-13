package com.example.tps900.tps900.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.tps900.tps900.Bean.GetLevelInfo_Bean;
import com.example.tps900.tps900.Bean.GetVipInfoTogether_been;
import com.example.tps900.tps900.R;
import com.example.tps900.tps900.Utlis.CodeBalanceKeyBoard;
import com.example.tps900.tps900.Utlis.Dailog;
import com.example.tps900.tps900.Utlis.ThreadPoolUtils;
import com.example.tps900.tps900.Utlis.ToastUtils;
import com.example.tps900.tps900.WEBSERVICE_Utils.Constants;
import com.example.tps900.tps900.WEBSERVICE_Utils.GsWebServiceUtils;
import com.example.tps900.tps900.sql.PdaDaoUtils;
import com.godfery.pay.HttpUtils;

import java.util.ArrayList;

import static com.example.tps900.tps900.Utlis.Constant.context;

/**
 * @author zxh
 *         项目名称：TPS900
 *         类名称：
 *         类描述：
 *         创建人：zxh
 *         创建时间：2017年10月16日10:57:57
 *         修改人：zxh
 *         修改时间：
 *         修改备注：查询线上会员卡余额
 * @author zxh
 */
public class CodeBalanceActivity extends BaseActivity implements CodeBalanceKeyBoard.QRConnectLinstener, View.OnFocusChangeListener, View.OnTouchListener, BaseActivity.CardCodeInterface {
    //返回键

    RelativeLayout ticketVerificationBack;
    //输入框二维码

    EditText eCode;
    //删除

    ImageView imgCodeImgDelete;
    //查询

    Button bQuery;
    //显示卡号

    TextView cardNumber;
    //显示余额

    TextView cardYuE;
    //键盘
    CodeBalanceKeyBoard mCodeBalanceActivityKeyBoard;
    //卡名称

    TextView cardName;
    //会员卡ID

    TextView cardVIpID;
    //会员卡卡级

    TextView cardDiscount;
    private String strIntent;
    private String strWebService;
    private String strCode;
    private int sdkInt;
    private CodeBalanceBdr Pro;


    /**
     * 设置布局
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_code_blalance;
    }

    @Override
    public void initView() {
        context = CodeBalanceActivity.this;

        ticketVerificationBack = (RelativeLayout) findViewById(R.id.ticket_verification_back);
        eCode = (EditText) findViewById(R.id.E_code);
        imgCodeImgDelete = (ImageView) findViewById(R.id.Img_code_img_delete);
        bQuery = (Button) findViewById(R.id.B_Query);
        cardNumber = (TextView) findViewById(R.id.card_Number);
        cardYuE = (TextView) findViewById(R.id.card_YuE);
        cardName = (TextView) findViewById(R.id.card_name);
        cardVIpID = (TextView) findViewById(R.id.card_VIpID);
        cardDiscount = (TextView) findViewById(R.id.card_discount);
        sdkInt = Build.VERSION.SDK_INT;
        mCodeBalanceActivityKeyBoard = new CodeBalanceKeyBoard(CodeBalanceActivity.this, eCode);

        eCode.setOnFocusChangeListener(this);
        eCode.setOnTouchListener(this);
        setCardInterface(this);
        mCodeBalanceActivityKeyBoard.setOnQRReceiveData(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("Intent");
        intentFilter.addAction("WebService");
        intentFilter.addAction("Code");
        if (Pro == null) {
            Pro = new CodeBalanceBdr();
        }
        CodeBalanceActivity.this.registerReceiver(Pro, intentFilter);

    }

    /**
     * 操作数据
     */
    @Override
    public void initData() {
        addTextChangedListener(eCode);
    }

    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.ticket_verification_back) {
            openActivityAndCloseThis(MainActivity.class);
        } else if (id == R.id.Img_code_img_delete) {
            eCode.setText("");
        } else if (id == R.id.B_Query) {
            if (TextUtils.isEmpty(eCode.getText().toString().trim())) {
                ToastUtils.show(this, "请输入要查询的卡号");
            } else {
                HttpUtils.showProgressDialog(progressDialog);
                ThreadPoolUtils.runTaskInThread(new Runnable() {
                    @Override
                    public void run() {
                        getVipInfoPayMent(eCode.getText().toString().trim());
                    }
                });
            }
        } else if (id == R.id.E_code) {
            // mCodeBalanceActivityKeyBoard = new CodeBalanceKeyBoard(CodeBalanceActivity.this, eCode);
        }
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        try {
            EditText et = (EditText) view;
            if (!b) {
                et.setHint(et.getTag().toString());
            } else {
                String hint = et.getHint().toString();
                et.setTag(hint);
                et.setHint(null);
                int id = view.getId();
                if (id == R.id.E_code) {
                    mCodeBalanceActivityKeyBoard = new CodeBalanceKeyBoard(CodeBalanceActivity.this, eCode);
                }

            }
        } catch (Exception e) {
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //隐藏输入法，显示光标
        EditText et = (EditText) v;
        int inType = et.getInputType();
        CodeBalanceKeyBoard.getCursorIndex(et, inType, v, sdkInt);
        int id = v.getId();
        if (id == R.id.E_code) {
            eCode.onTouchEvent(event);
            eCode.setInputType(inType);
        }
        return true;
    }

    /**
     * @param codeNum 获取卡信息
     */
    public void getVipInfoPayMent(final String codeNum) {
        //获取折扣率信息
        String getVipInfoTogether = GsWebServiceUtils.GetVipInfoTogether(codeNum);
        final GetVipInfoTogether_been getVipInfoTogether_been = JSON.parseObject(getVipInfoTogether, GetVipInfoTogether_been.class);
        if (getVipInfoTogether_been.Success) {
            if (getVipInfoTogether_been.Data != null && getVipInfoTogether_been.Data.size() != 0) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        HttpUtils.exitProgressDialog(progressDialog);
                        //卡余额
                        double Blance = Double.valueOf(getVipInfoTogether_been.getData().get(0).getBalance());
                        //查询卡级折扣率
                        ArrayList<GetLevelInfo_Bean.DataBean> query_levelInfo = PdaDaoUtils.getQuery_LevelInfo("select * from LevelInfo where LevelId = '" + getVipInfoTogether_been.getData().get(0).getVipInfo().getCardLevelId() + "' ");
                        if (query_levelInfo != null && query_levelInfo.size() != 0) {
                            Constants.goodsDepositRate = query_levelInfo.get(0).getDEPOSITRATE();
                            Log.e("lala", "折扣率" + query_levelInfo.get(0).getDEPOSITRATE());
                            eCode.setText(codeNum);
                            cardNumber.setText(codeNum);
                            cardYuE.setText(Blance + "元");
                            double money = query_levelInfo.get(0).getDEPOSITRATE() * 100;
                            cardDiscount.setText((int) money + "%");
                            cardName.setText(getVipInfoTogether_been.getData().get(0).getVipInfo().getCardLevelName());
                            cardVIpID.setText(getVipInfoTogether_been.getData().get(0).getVipInfo().getVipId());
                        }
                    }
                });
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        HttpUtils.exitProgressDialog(progressDialog);
                        Dailog.ErrDialog2(CodeBalanceActivity.this,"会员卡信息为空!!!","");
                    }
                });
            }
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    HttpUtils.exitProgressDialog(progressDialog);
                    Dailog.ErrDialog2(CodeBalanceActivity.this,"获取会员卡信息失败!!!","");
                }
            });
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            openActivityAndCloseThis(MainActivity.class);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onReadCardOk(final String cardNo) {
        HttpUtils.showProgressDialog(progressDialog);
        ThreadPoolUtils.runTaskInThread(new Runnable() {
            @Override
            public void run() {
                getVipInfoPayMent(cardNo);
            }
        });
    }

    @Override
    public void onQRReceiveData(String data) {
        strIntent = "Intent";
        strWebService = "WebService";
        strCode = "Code";
        if (strIntent.equals(data)) {
            cardNumber.setText("");
            cardYuE.setText("");
            cardDiscount.setText("");
            cardName.setText("");
            cardVIpID.setText("");
            eCode.setText("");
            openActivityAndCloseThis(MainActivity.class);
        } else if (strWebService.equals(data)) {
            if (TextUtils.isEmpty(eCode.getText().toString().trim())) {
                ToastUtils.show(this, "请输入要查询的卡号");
            } else {
                HttpUtils.showProgressDialog(progressDialog);
                ThreadPoolUtils.runTaskInThread(new Runnable() {
                    @Override
                    public void run() {
                        getVipInfoPayMent(eCode.getText().toString().trim());
                    }
                });
            }
        } else if (strCode.equals(data)) {
            DeviceN900(CodeBalanceActivity.this);
        }
    }

    public class CodeBalanceBdr extends BroadcastReceiver {
        public CodeBalanceBdr() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case "Code":
                    HttpUtils.showProgressDialog(progressDialog);
                    DeviceN900(CodeBalanceActivity.this);
                    break;
                case "Intent":
                    cardNumber.setText("");
                    cardYuE.setText("");
                    cardDiscount.setText("");
                    cardName.setText("");
                    cardVIpID.setText("");
                    eCode.setText("");
                    openActivityAndCloseThis(MainActivity.class);
                    break;
                case "WebService":
                    if (TextUtils.isEmpty(eCode.getText().toString().trim())) {
                        ToastUtils.show(CodeBalanceActivity.this, "请输入要查询的卡号");
                    } else {
                        HttpUtils.showProgressDialog(progressDialog);
                        ThreadPoolUtils.runTaskInThread(new Runnable() {
                            @Override
                            public void run() {
                                getVipInfoPayMent(eCode.getText().toString().trim());
                            }
                        });
                    }
                    break;
                default:
                    break;
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Pro != null) {
            unregisterReceiver(Pro);
            Pro = null;
        }
    }
}
