package com.example.tps900.tps900.activity.newfood;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.tps900.tps900.R;
import com.example.tps900.tps900.Utlis.Constant;
import com.example.tps900.tps900.Utlis.SltTableKeyBoard;
import com.godfery.Utils.ToastUtils;

public class NewSltTableActivity extends Activity implements View.OnFocusChangeListener, View.OnTouchListener {


    EditText sltTableEt;

    Button selectTableBtn;
    private SltTableKeyBoard sltTableKeybord;
    private int sdkInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity_slt_table);
        initView();
    }

    /**
     * 初始化数据
     */
    private void initView() {
        sltTableEt = (EditText) findViewById(R.id.slt_table_et);
        selectTableBtn = (Button) findViewById(R.id.select_table_btn);
        sltTableEt.setOnFocusChangeListener(this);
        sltTableEt.setOnTouchListener(this);
        sdkInt = Build.VERSION.SDK_INT;
        sltTableKeybord = new SltTableKeyBoard(NewSltTableActivity.this);
        sltTableEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                sltTableEt.setSelection(s.length());
            }
        });//默认光标在字体最后
    }

    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.slt_table_et:
                sltTableKeybord = new SltTableKeyBoard(NewSltTableActivity.this, sltTableEt);
                break;
            case R.id.select_table_btn:
                String tableNo = sltTableEt.getText().toString().trim();
                if (TextUtils.isEmpty(tableNo)) {
                    ToastUtils.show(this, "请输入桌台号");
                } else {
                    if (TextUtils.isEmpty(Constant.TablePeople)) {
                        Intent intent = new Intent(this, New_SltPeopleActivity.class);
                        intent.putExtra("tableNo", tableNo);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(this, New_Pay_foodActivity.class);
                        intent.putExtra("tableNo", tableNo);
                        startActivity(intent);
                        finish();
                    }
                }
                break;
            default:
                break;
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
                //保存预设字
                et.setTag(hint);
                et.setHint(null);
                int id = view.getId();
                if (id == R.id.slt_table_et) {
                    sltTableKeybord = new SltTableKeyBoard(NewSltTableActivity.this, sltTableEt);
                }
            }
        } catch (Exception e) {
        }

    }//获取光标

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //隐藏输入法，显示光标
        EditText et = (EditText) v;
        int inType = et.getInputType();
        SltTableKeyBoard.getCursorIndex(et, inType, v, sdkInt);
        int id = v.getId();

        if (id == R.id.slt_table_et) {
            sltTableEt.onTouchEvent(event);
            sltTableEt.setInputType(inType);
        }
        return true;
    }//隐藏系统键盘

}
