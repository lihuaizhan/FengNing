package com.example.tps900.tps900.Utlis;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.example.tps900.tps900.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.example.tps900.tps900.Utlis.Constant.context;

public class GuiNumberKeyBoard_PF extends RelativeLayout implements View.OnClickListener {

    private View view;
    private Button numberKeyboard_0;
    private Button numberKeyboard_1;
    private Button numberKeyboard_2;
    private Button numberKeyboard_3;
    private Button numberKeyboard_4;
    private Button numberKeyboard_5;
    private Button numberKeyboard_6;
    private Button numberKeyboard_7;
    private Button numberKeyboard_8;
    private Button numberKeyboard_9;
    private Button numberKeyconfirm;
    private Button numberKeyboard_cancel;
    private Animation mHiddenAction;
    private Animation mShowAction;

    public static EditText editText;
    public static View popupWindow_view;
    public static PopupWindow popupWindow;
    private Button numberKeyboard_cance3;
    private Button numberKeyboard_dian;
    private int index;
    Editable edit;

    public GuiNumberKeyBoard_PF(Context context, EditText editText) {
        super(context);
        this.editText = editText;
        init(context);

    }

    public GuiNumberKeyBoard_PF(Context context) {
        super(context);
    }


    public void register(View popupWindow_view, final PopupWindow popupWindow1) {
        GuiNumberKeyBoard_PF.popupWindow_view = popupWindow_view;
        GuiNumberKeyBoard_PF.popupWindow = popupWindow1;

    }


    public GuiNumberKeyBoard_PF(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public GuiNumberKeyBoard_PF(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.keybord3,
                this, true);
        numberKeyboard_0 = (Button) view.findViewById(R.id.numberKeyboard_0);
        numberKeyboard_1 = (Button) view.findViewById(R.id.numberKeyboard_1);
        numberKeyboard_2 = (Button) view.findViewById(R.id.numberKeyboard_2);
        numberKeyboard_3 = (Button) view.findViewById(R.id.numberKeyboard_3);
        numberKeyboard_4 = (Button) view.findViewById(R.id.numberKeyboard_4);
        numberKeyboard_5 = (Button) view.findViewById(R.id.numberKeyboard_5);
        numberKeyboard_6 = (Button) view.findViewById(R.id.numberKeyboard_6);
        numberKeyboard_7 = (Button) view.findViewById(R.id.numberKeyboard_7);
        numberKeyboard_8 = (Button) view.findViewById(R.id.numberKeyboard_8);
        numberKeyboard_9 = (Button) view.findViewById(R.id.numberKeyboard_9);
        numberKeyboard_cancel = (Button) view.findViewById(R.id.numberKeyboard_quxiao);//取消
        numberKeyboard_0.setOnClickListener(this);
        numberKeyboard_1.setOnClickListener(this);
        numberKeyboard_2.setOnClickListener(this);
        numberKeyboard_3.setOnClickListener(this);
        numberKeyboard_4.setOnClickListener(this);
        numberKeyboard_5.setOnClickListener(this);
        numberKeyboard_6.setOnClickListener(this);
        numberKeyboard_7.setOnClickListener(this);
        numberKeyboard_8.setOnClickListener(this);
        numberKeyboard_9.setOnClickListener(this);
        numberKeyboard_cancel.setOnClickListener(this);
    }

    public void setEditText(final EditText editText) {
        GuiNumberKeyBoard_PF.editText = editText;
        GuiNumberKeyBoard_PF.editText.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                show();
                return false;
            }
        });
        GuiNumberKeyBoard_PF.editText.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hint();
                }


            }
        });
    }

    public void hint() {
        if (this.getVisibility() == View.VISIBLE) {
            this.startAnimation(mHiddenAction);
            this.setVisibility(View.GONE);
        }
    }

    public void show() {
        if (this.getVisibility() == View.GONE) {
            this.startAnimation(mShowAction);
            this.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.numberKeyboard_0) {
//            editText.setText(editText.getText().toString() + "0");
            editinsert("0");
        } else if (id == R.id.numberKeyboard_1) {
            //          editText.setText(editText.getText().toString() + "1");
            editinsert("1");
        } else if (id == R.id.numberKeyboard_2) {
            //           editText.setText(editText.getText().toString() + "2");
            editinsert("2");
        } else if (id == R.id.numberKeyboard_3) {
            //           editText.setText(editText.getText().toString() + "3");
            editinsert("3");
        } else if (id == R.id.numberKeyboard_4) {
            //          editText.setText(editText.getText().toString() + "4");
            editinsert("4");
        } else if (id == R.id.numberKeyboard_5) {
            //           editText.setText(editText.getText().toString() + "5");
            editinsert("5");
        } else if (id == R.id.numberKeyboard_6) {
            //          editText.setText(editText.getText().toString() + "6");
            editinsert("6");
        } else if (id == R.id.numberKeyboard_7) {
            //           editText.setText(editText.getText().toString() + "7");
            editinsert("7");
        } else if (id == R.id.numberKeyboard_8) {
            //          editText.setText(editText.getText().toString() + "8");
            editinsert("8");
        } else if (id == R.id.numberKeyboard_9) {
            //          editText.setText(editText.getText().toString() + "9");
            editinsert("9");
        } else if (id == R.id.numberKeyboard_quxiao) {
            Intent intent = new Intent();
            intent.setAction("Intent");
            context.sendBroadcast(intent);
        }
    }

    //回车键点击事件
    public void setReturnKey() {

        if (editText.hasFocus()) {

            editText.requestFocus();
        }

    }

    public static void getCursorIndex(EditText et, int inType, View v, int sdkInt) {
        //隐藏输入法，显示光标
        et = (EditText) v;
        inType = et.getInputType(); // back1 up the input type

        if (sdkInt >= 11) {
            Class<EditText> cls = EditText.class;
            try {
                Method setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                setShowSoftInputOnFocus.setAccessible(false);
                setShowSoftInputOnFocus.invoke(et, false);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            et.setInputType(android.text.InputType.TYPE_NULL); // disable soft input
            et.setInputType(inType);

        }
    }

    public void editinsert(String text) {
//        index = editText.getSelectionStart();//获取光标所在位置
//        Editable edit = editText.getEditableText();//获取EditText的文字
//        if (index < 0 || index >= edit.length()) {
//            edit.append(text);
//            editText.setSelection(index + text.length());
//        } else {
//            edit.insert(index, text);//光标所在位置插入文字
//            editText.setSelection(index + text.length());
//        }
        Editable editable = editText.getText();
        int start = editText.getSelectionStart();
        editable.insert(start, text);
    }
}
