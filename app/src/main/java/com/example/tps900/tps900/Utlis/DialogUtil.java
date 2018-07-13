package com.example.tps900.tps900.Utlis;

import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;

import com.example.tps900.tps900.R;

/**
 * Created by wo on 2017/4/25.
 */

public class DialogUtil {

    public static AlertDialog alertDialog;

    public static void showDownloadDialog(Context context){
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);
        Window window = alertDialog.getWindow();
        window.setBackgroundDrawableResource(R.color.black);
        window.setBackgroundDrawableResource(R.drawable.alertdialog_background);
        window.setGravity(Gravity.CENTER);
        window.setLayout(360,360);


        window.setContentView(R.layout.dialog_wait);

    }

    public static void showOneCardDialog(Context context){
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);
        Window window = alertDialog.getWindow();
        window.setBackgroundDrawableResource(R.color.black);
        window.setBackgroundDrawableResource(R.drawable.alertdialog_background);
        window.setGravity(Gravity.CENTER);
        window.setLayout(360,360);

        window.setContentView(R.layout.onecard_wait);

    }


    public static void showPayDialog(Context context){
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);
        Window window = alertDialog.getWindow();
        window.setBackgroundDrawableResource(R.color.black);
        window.setBackgroundDrawableResource(R.drawable.alertdialog_background);
        window.setGravity(Gravity.CENTER);
        window.setLayout(360,360);
        window.setContentView(R.layout.pay_wait);

    }
    //查询等待
    public static void showQueryDialog(Context context){
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);
        Window window = alertDialog.getWindow();
        window.setBackgroundDrawableResource(R.color.black);
        window.setBackgroundDrawableResource(R.drawable.alertdialog_background);
        window.setGravity(Gravity.CENTER);
        window.setLayout(360,360);
        window.setContentView(R.layout.query_wait);

    }

    public static void showReturnDialog(Context context){
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);
        Window window = alertDialog.getWindow();
        window.setBackgroundDrawableResource(R.color.black);
        window.setBackgroundDrawableResource(R.drawable.alertdialog_background);
        window.setGravity(Gravity.CENTER);
        window.setLayout(360,360);

        window.setContentView(R.layout.return_wait);

    }

    public static void cancelDownloadDialog(){
        if (alertDialog!=null){
            alertDialog.dismiss();
            alertDialog=null;
        }
    }



}
