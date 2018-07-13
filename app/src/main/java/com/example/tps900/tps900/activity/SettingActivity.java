package com.example.tps900.tps900.activity;

import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.tps900.tps900.R;
import com.example.tps900.tps900.Utlis.Constant;
import com.example.tps900.tps900.Utlis.OtherUtils;
import com.example.tps900.tps900.Utlis.ToastUtils;
import com.example.tps900.tps900.sql.PdaDaoUtils;


/**
 * 项目名称：TPS900
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2017/4/17 13:03
 * 修改人：zxh
 * 修改时间：2017/4/17 13:03
 * 修改备注：
 */

public class SettingActivity extends BaseActivity {

    EditText ticketPrintNum;

    EditText ticketDeviceName;

    RadioButton settingConfirm;

    RadioButton settingCancel;
    EditText terminalNameScenicSpot;

    EditText settingSDeviceName;

    EditText yEditDeviceCode;

    EditText edit_OneCard_terminalName;
    private PdaDaoUtils utils;
    private EditText food_terminalName_scenicSpot;
    private CheckBox setting_radio_card;

    /**
     * 设置布局
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }


    /**
     * 初始化控件
     */
    @Override
    public void initView() {
        setting_radio_card = (CheckBox) findViewById(R.id.setting_radio_card);
        terminalNameScenicSpot = (EditText) findViewById(R.id.terminalName_scenicSpot);
        food_terminalName_scenicSpot = (EditText) findViewById(R.id.food_terminalName_scenicSpot);
        ticketPrintNum = (EditText) findViewById(R.id.ticket_printNum);
        ticketDeviceName = (EditText) findViewById(R.id.setting_deviceName);
        settingConfirm = (RadioButton) findViewById(R.id.setting_confirm);
        settingCancel = (RadioButton) findViewById(R.id.setting_cancel);
        settingSDeviceName = (EditText) findViewById(R.id.setting_S_deviceName);
        yEditDeviceCode = (EditText) findViewById(R.id.y_edit_deviceCode);
        edit_OneCard_terminalName = (EditText) findViewById(R.id.terminalName_OneCard);
        OtherUtils.setConstants(SettingActivity.this);
        yEditDeviceCode.setText(Constant.Y_deviceID);
        ticketPrintNum.setText(Constant.CHECK_OUT);
        ticketDeviceName.setText(Constant.devicename);
        settingSDeviceName.setText(Constant.S_devicename);
        terminalNameScenicSpot.setText(Constant.TERMINALNAME);
        food_terminalName_scenicSpot.setText(Constant.Food_TERMINALNAME);
        //一卡通终端名称
        edit_OneCard_terminalName.setText(Constant.ONECARD_TERMINALNAME + "");
        if (Constant.IsCtnCredirCard.equals("是")) {
            setting_radio_card.setChecked(true);
            setting_radio_card.setText("是");
        }else {
            setting_radio_card.setChecked(false);
            setting_radio_card.setText("否");
        }

        setting_radio_card.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(setting_radio_card.isChecked()){
                    setting_radio_card.setText("是");
                }else{
                    setting_radio_card.setText("否");
                }
            }
        });
    }

    /**
     * 操作数据
     */
    @Override
    public void initData() {
        utils = new PdaDaoUtils();
    }

    public void commit() {
        if (setting_radio_card.isChecked()) {
            Constant.IsCtnCredirCard = "是";
        } else {
            Constant.IsCtnCredirCard = "否";
        }
        String content_terminalName = terminalNameScenicSpot.getText().toString().trim();
        String foodterminalName = food_terminalName_scenicSpot.getText().toString().trim();
        int onecard_terminalName = Integer.valueOf(edit_OneCard_terminalName.getText().toString().trim());
        String CHECK_OUT = ticketPrintNum.getText().toString().trim();
        String devicename = ticketDeviceName.getText().toString().trim();
        String s_deviceName = settingSDeviceName.getText().toString().trim();
        String y_deviceID = yEditDeviceCode.getText().toString().trim();
        Constant.CHECK_OUT = CHECK_OUT;
        Constant.devicename = devicename;
        Constant.S_devicename = s_deviceName;
        Constant.TERMINALNAME = content_terminalName;
        Constant.Food_TERMINALNAME = foodterminalName;
        Constant.ONECARD_TERMINALNAME = onecard_terminalName;
        Constant.Y_deviceID = y_deviceID;
        int i_ret = utils.info_insert(SettingActivity.this);
        if (i_ret == 1) {
            ToastUtils.show(SettingActivity.this, "提交成功");
            if ("Set".equals(Constant.SetOrMain)) {
                openActivityAndCloseThis(LoginActivity.class);
            } else {
                openActivityAndCloseThis(MainActivity.class);
            }
        } else {
            ToastUtils.show(SettingActivity.this, "提交数据库失败取消返回上一页");
            if ("Set".equals(Constant.SetOrMain)) {
                openActivityAndCloseThis(LoginActivity.class);
            } else {
                openActivityAndCloseThis(MainActivity.class);
            }
        }

    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.setting_confirm) {
            commit();
        } else if (id == R.id.setting_cancel) {
            if ("Set".equals(Constant.SetOrMain)) {
                openActivityAndCloseThis(LoginActivity.class);
            } else {
                openActivityAndCloseThis(MainActivity.class);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ("Set".equals(Constant.SetOrMain)) {
                openActivityAndCloseThis(LoginActivity.class);
            } else {
                openActivityAndCloseThis(MainActivity.class);
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
