package com.example.tps900.tps900.Utlis;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.tps900.tps900.R;

/**
 * Created by dahu on 2016-11-09.
 */

public class ShowInfo {

    private Activity activity;

    public ShowInfo(Activity activity) {
        this.activity = activity;

    }
    public AlertDialog.Builder builder;
    public void infoDialog(Context context,String Title ,final String errtext) {
        LayoutInflater factory = LayoutInflater.from(context);
        final View textEntryView = factory.inflate(R.layout.handler, null);
        final TextView editTextNumEditText = (TextView) textEntryView
                .findViewById(R.id.Errtext);
        editTextNumEditText.setText(errtext);
        builder = new AlertDialog.Builder(context);
        builder.setTitle(Title);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setView(textEntryView);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {


            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
            }
        });
        builder.show();// 显示对话框
    }


    public void showSuccessDialog( String Title , final String errtext) {
        LayoutInflater factory = LayoutInflater.from(activity);
        final View textEntryView = factory.inflate(R.layout.handler, null);
        final TextView editTextNumEditText = (TextView) textEntryView
                .findViewById(R.id.Errtext);
        editTextNumEditText.setText(errtext);
        builder = new AlertDialog.Builder(activity);
        builder.setTitle(Title);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setView(textEntryView);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                activity.finish();
            }
        });

        builder.show();// 显示对话框
    }


}

