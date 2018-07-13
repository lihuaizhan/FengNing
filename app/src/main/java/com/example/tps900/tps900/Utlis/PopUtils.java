package com.example.tps900.tps900.Utlis;

import android.app.Activity;
import android.content.Intent;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tps900.tps900.R;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by wo on 2017/2/20.
 */

public class PopUtils {

    public static final String TAG = "PopUtils";

    public static void showPopUp(final Activity context) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        //dialog布局
        View view = layoutInflater.inflate(R.layout.dialog_pop_net, null);
        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        Button btn_confirm = (Button) view.findViewById(R.id.btn_confirm);


        RelativeLayout ll_bottom = (RelativeLayout) view.findViewById(R.id.ll_bottom);

        View parent = layoutInflater.inflate(R.layout.activity_login, null);
        // 创建一个PopuWidow对象

        final PopupWindow popupWindow = new PopupWindow(view, 560, 425);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context.startActivityForResult(new Intent(Settings.ACTION_WIRELESS_SETTINGS), 0);

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });


        int[] location = new int[2];
        ll_bottom.getLocationOnScreen(location);
        popupWindow.showAtLocation(parent, Gravity.CENTER_HORIZONTAL,5,-120);
    }

    /**
     *
     * @param context
     * @param title
     * @param content
     */

    public static void showNoticePopUp(final Activity context,String title,String content) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        //dialog布局
        View view = layoutInflater.inflate(R.layout.dialog_pop_net, null);
        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        Button btn_confirm = (Button) view.findViewById(R.id.btn_confirm);

        TextView tv_title_pop = (TextView) view.findViewById(R.id.tv_title_pop);
        TextView tv_content_pop = (TextView) view.findViewById(R.id.tv_content_pop);

        tv_title_pop.setText(title);
        tv_content_pop.setText(content);

        RelativeLayout ll_bottom = (RelativeLayout) view.findViewById(R.id.ll_bottom);

        View parent = layoutInflater.inflate(R.layout.activity_login, null);
        // 创建一个PopuWidow对象

        final PopupWindow popupWindow = new PopupWindow(view, 560, 425);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });


        int[] location = new int[2];
        ll_bottom.getLocationOnScreen(location);
        popupWindow.showAtLocation(parent, Gravity.CENTER_HORIZONTAL,5,-120);
    }


}
