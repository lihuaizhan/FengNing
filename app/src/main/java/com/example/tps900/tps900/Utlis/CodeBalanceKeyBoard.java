package com.example.tps900.tps900.Utlis;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
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

public class CodeBalanceKeyBoard extends RelativeLayout implements View.OnClickListener {

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
    private QRConnectLinstener linstener;

    public CodeBalanceKeyBoard(Context context, EditText editText) {
        super(context);
        this.editText = editText;
        init(context);

    }

    public CodeBalanceKeyBoard(Context context) {
        super(context);
    }


    public void register(View popupWindow_view, final PopupWindow popupWindow1) {
        CodeBalanceKeyBoard.popupWindow_view = popupWindow_view;
        CodeBalanceKeyBoard.popupWindow = popupWindow1;

    }


    public CodeBalanceKeyBoard(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public CodeBalanceKeyBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.keybord2,
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
        numberKeyconfirm = (Button) view.findViewById(R.id.numberKeyboard_queding);
        numberKeyboard_cancel = (Button) view.findViewById(R.id.numberKeyboard_quxiao);
        numberKeyboard_cance3 = (Button) view.findViewById(R.id.numberKeyboard_shuaka);
        numberKeyboard_dian = (Button) view.findViewById(R.id.numberKeyboard_dian);
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
        numberKeyconfirm.setOnClickListener(this);
        numberKeyboard_cancel.setOnClickListener(this);
        numberKeyboard_cance3.setOnClickListener(this);
        numberKeyboard_dian.setOnClickListener(this);
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
            editinsert("0");
        } else if (id == R.id.numberKeyboard_1) {
            editinsert("1");
        } else if (id == R.id.numberKeyboard_2) {
            editinsert("2");
        } else if (id == R.id.numberKeyboard_3) {
            editinsert("3");
        } else if (id == R.id.numberKeyboard_4) {
            editinsert("4");
        } else if (id == R.id.numberKeyboard_5) {
            editinsert("5");
        } else if (id == R.id.numberKeyboard_6) {
            editinsert("6");
        } else if (id == R.id.numberKeyboard_7) {
            editinsert("7");
        } else if (id == R.id.numberKeyboard_8) {
            editinsert("8");
        } else if (id == R.id.numberKeyboard_9) {
            editinsert("9");
        } else if (id == R.id.numberKeyboard_quxiao) {
            Intent intent = new Intent();
            intent.setAction("Intent");
            context.sendBroadcast(intent);
        } else if (id == R.id.numberKeyboard_queding) {
            Intent intent = new Intent();
            intent.setAction("WebService");
            context.sendBroadcast(intent);
        } else if (id == R.id.numberKeyboard_shuaka) {
            Intent intent = new Intent();
            intent.setAction("Code");
            context.sendBroadcast(intent);
        } else if (id == R.id.numberKeyboard_dian) {
            editinsert(".");
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
        inType = et.getInputType();

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
            et.setInputType(android.text.InputType.TYPE_NULL);
            et.setInputType(inType);

        }
    }


    public void editinsert(String text) {
        Editable editable = editText.getText();
        int start = editText.getSelectionStart();
        editable.insert(start, text);
    }

    public void setOnQRReceiveData(QRConnectLinstener linstener) {
        this.linstener = linstener;
    }

    public interface QRConnectLinstener {
        void onQRReceiveData(String data);
    }
}
