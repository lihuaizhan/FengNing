package com.example.tps900.tps900.Utlis;

import android.view.View;

import java.util.Calendar;

/**
 * 项目名称：PDA550_New
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2017/2/7 13:25
 * 修改人：zxh
 * 修改时间：2017/2/7 13:25
 * 修改备注：
 */
public abstract class NoDoubleClickListener implements View.OnClickListener{

    public static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoDoubleClick(v);
        }
    }

    protected abstract void onNoDoubleClick(View v);
}
