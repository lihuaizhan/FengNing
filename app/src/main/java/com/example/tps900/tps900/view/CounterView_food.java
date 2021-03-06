package com.example.tps900.tps900.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.tps900.tps900.R;
import com.example.tps900.tps900.Utlis.ToastUtils;
import com.godfery.keyboard.KeyboardUtil;

import static com.godfery.keyboard.CustomEditLintener.EditListener;


/**
 * 购物车，计数
 */
public class CounterView_food extends LinearLayout implements View.OnClickListener, TextWatcher {
    /**
     * 最大的数量
     **/
    public static final int MAX_VALUE = 100;

    /**
     * 最小的数量
     **/
    public static final int MIN_VALUE = 0;
    private Activity activity;
    //数量
    private int countValue = 1;

    private ImageView ivAdd, ivMinu;

    private EditText etCount;

    private IChangeCoutCallback callback;

    private int maxValue = MAX_VALUE;


    public void setCallback(IChangeCoutCallback c) {
        this.callback = c;
    }

    private Context mContext;

    public CounterView_food(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

        initView(context, attrs);
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    /**
     * 功能描述：设置最大数量
     * 作者： hg_liuzl@qq.com
     * 时间：2016/12/3 18:33
     * 参数：
     */
    public void setMaxValue(int max) {
        this.maxValue = max;
    }


    private void initView(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.my_counter_size);

        int maxValue = a.getInt(R.styleable.my_counter_size_count_max, 100);

        setMaxValue(maxValue);

        LayoutInflater.from(mContext).inflate(R.layout.model_count_view_food, this);

        ivMinu = (ImageView) findViewById(R.id.iv_count_minus);
        ivMinu.setOnClickListener(this);

        ivAdd = (ImageView) findViewById(R.id.iv_count_add);
        ivAdd.setOnClickListener(this);

        etCount = (EditText) findViewById(R.id.et_count);
        EditListener(etCount);
        etCount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                etCount.setHint("");
                new KeyboardUtil(context, activity, etCount, R.id.schemas_key_keyboard_view).showKeyboard();

            }
        });
        etCount.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new KeyboardUtil(context, activity, etCount, R.id.schemas_key_keyboard_view).showKeyboard();

            }
        });
        etCount.addTextChangedListener(this);
        a.recycle();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_count_add) {
            addAction();
        } else if (id == R.id.iv_count_minus) {
            minuAction();
        }
    }

    public void setCountValue(int countValue) {
        this.countValue = countValue;
        changeWord(true);
        ivAdd.setEnabled(false);
    }

    /**
     * 添加操
     */
    private void addAction() {
        countValue++;
        btnChangeWord();
    }


    /**
     * 删除操作
     */
    private void minuAction() {
        countValue--;
        btnChangeWord();
    }

    /**
     * 功能描述：
     * 作者： hg_liuzl@qq.com
     * 时间：2016/12/12 10:29
     * 参数：boolean 是否需要重新赋值
     */
    private void changeWord(boolean needUpdate) {
        if (needUpdate) {
            etCount.removeTextChangedListener(this);
            //不为空的时候才需要赋值
            if (!TextUtils.isEmpty(etCount.getText().toString().trim())) {
                etCount.setText(String.valueOf(countValue));
            }
            etCount.addTextChangedListener(this);
        }
        etCount.setSelection(etCount.getText().toString().trim().length());
        callback.change(countValue);
    }

    private void btnChangeWord() {
        ivMinu.setEnabled(countValue > MIN_VALUE);
        ivAdd.setEnabled(countValue < maxValue);
        etCount.setText(String.valueOf(countValue));
        etCount.setSelection(etCount.getText().toString().trim().length());
        callback.change(countValue);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        boolean needUpdate = false;
        if (!TextUtils.isEmpty(s)) {
            countValue = Integer.valueOf(s.toString());
            if (countValue <= MIN_VALUE) {
                countValue = MIN_VALUE;
                ivMinu.setEnabled(false);
                ivAdd.setEnabled(true);
                needUpdate = true;
//                Toast.makeText(mContext, String.format("最少添加%s个数量", MIN_VALUE), Toast.LENGTH_SHORT).show();
//                ToastUtils.show(mContext, String.format("最少添加%s个数量", MIN_VALUE));
            } else if (countValue == maxValue) {
                countValue = maxValue;
                ivMinu.setEnabled(true);
                ivAdd.setEnabled(false);
                needUpdate = true;
            } else if (countValue > maxValue) {
                countValue = maxValue;
                ivMinu.setEnabled(true);
                ivAdd.setEnabled(false);
                needUpdate = true;
                ToastUtils.show(mContext, "超出核销应到人数!");
            } else {
                ivMinu.setEnabled(true);
                ivAdd.setEnabled(true);
            }
        } else {  //如果编辑框被清空了，直接填1
            countValue = 0;
            etCount.setText("0");
            ivMinu.setEnabled(false);
            ivAdd.setEnabled(true);
            needUpdate = true;
//            Toast.makeText(mContext, String.format("最少添加%s个数量", MIN_VALUE), Toast.LENGTH_SHORT).show();

        }
        changeWord(needUpdate);
    }
}
