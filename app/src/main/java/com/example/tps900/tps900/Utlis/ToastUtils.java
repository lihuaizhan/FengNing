package com.example.tps900.tps900.Utlis;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Toast工具类
 * 
 */
public class ToastUtils {
	private static Toast toast = null;
	//短时间吐司
	public static void show(Context ctx, String text) {
		if (toast == null) {
			toast = Toast.makeText(ctx, text, Toast.LENGTH_SHORT);
		} else {
			toast.setText(text);
		}
		toast.show();
	}
	//长时间吐司
	public static void showLong(Context ctx, String text) {
		if (toast == null) {
			toast = Toast.makeText(ctx, text, Toast.LENGTH_SHORT);
		} else {
			toast.setText(text);
		}
		toast.show();
	}//测试
	public static void showLongText(Context ctx) {
		if (toast == null) {
			toast = Toast.makeText(ctx, "点击测试！", Toast.LENGTH_SHORT);
		} else {
			toast.setText("点击测试！");
		}
		toast.show();
	}//取消
	public static void showCancel(Context ctx) {
		if (toast == null) {
			toast = Toast.makeText(ctx, "已取消！", Toast.LENGTH_SHORT);
		} else {
			toast.setText("已取消！");
		}
		toast.show();
	}//成功
	public static void showSuccess(Context ctx) {
		if (toast == null) {
			toast = Toast.makeText(ctx, "successful！", Toast.LENGTH_SHORT);
		} else {
			toast.setText("successful！");
		}
		toast.show();
	}

	public static void showCenter(Context ctx, String msg) {
		if (toast == null) {
			toast = Toast.makeText(ctx, msg, Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
		} else {
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.setText(msg);
		}
		toast.show();
	}

}
