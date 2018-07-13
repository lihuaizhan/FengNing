package com.example.tps900.tps900.activity;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tps900.tps900.R;
import com.example.tps900.tps900.Utlis.DialogUtil;
import com.example.tps900.tps900.Webservice.GsWebServiceUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.tps900.tps900.Utlis.Dailog.ErrDialog;
import static com.example.tps900.tps900.Utlis.DialogUtil.showOneCardDialog;

/**
 * 项目名称：Goods
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2017/8/28 15:06
 * 修改人：zxh
 * 修改时间：2017/8/28 15:06
 * 修改备注：
 */

public class OneCardInfoActivity extends BaseActivity implements   BaseActivity.CardCodeInterface {
    private Button btn_back, btn_Sk;
    private TextView tv_card, tv_YuE;
    private String codeNum = "";
    private double balance = 0.00;

    /**
     * 设置布局
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_oncard;
    }

    @Override
    public void initView() {
        btn_back = (Button) findViewById(R.id.card_back);
        btn_Sk = (Button) findViewById(R.id.card_Sk);
        tv_card = (TextView) findViewById(R.id.card_Number);
        tv_YuE = (TextView) findViewById(R.id.card_YuE);

    }

    @Override
    public void initData() {
        setCardInterface(this);
    }

    public void onViewClicked(View view) {
        int id =view.getId();
        if (id == R.id.card_back) {
            tv_card.setText("");
            tv_YuE.setText("");
            openActivityAndCloseThis(MainActivity.class);
        } else if (id == R.id.card_Sk) {
            showOneCardDialog(OneCardInfoActivity.this);
            DeviceN900(OneCardInfoActivity.this);
        }
    }

    private void submitOneCardInfo(String cardNo) {

        OneCardInfoActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogUtil.showQueryDialog(OneCardInfoActivity.this);
            }
        });

        GsWebServiceUtils gs = new GsWebServiceUtils();
        String oneCardInfo = GsWebServiceUtils.getOneCardinfo(cardNo);
        Log.e("一卡通信息是--------", oneCardInfo + "");
        //如果一卡通信息不为空
        tv_card.setText(cardNo);
        if (oneCardInfo != null) {
            try {
                final JSONObject jsonObject = new JSONObject(oneCardInfo);
                boolean isSuccess = jsonObject.getBoolean("isSuccess");
                if (isSuccess) {
                    //如果获取一卡通信息成功
                    JSONArray dt = jsonObject.getJSONArray("dt");
                    JSONObject info = dt.getJSONObject(0);

                    String sprintno = info.getString("SPRINTNO");

                    balance = info.getDouble("BALANCE");
                    tv_YuE.setText(balance + "元");
                    OneCardInfoActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogUtil.cancelDownloadDialog();
                        }
                    });

                } else {
                    //如果获取一卡通信息失败
                    OneCardInfoActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                tv_YuE.setText("");
                                String err = jsonObject.getString("error");
                                ErrDialog(OneCardInfoActivity.this, "", err);
                                DialogUtil.cancelDownloadDialog();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            //如果一卡通信息为空
            OneCardInfoActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ErrDialog(OneCardInfoActivity.this, "", "获取一卡通信息失败!");
                    DialogUtil.cancelDownloadDialog();
                }
            });


        }
    }

    @Override
    public void onReadCardOk(String cardNo) {
        submitOneCardInfo(codeNum);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            openActivityAndCloseThis(MainActivity.class);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
