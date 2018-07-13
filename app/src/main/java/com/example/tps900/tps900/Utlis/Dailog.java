package com.example.tps900.tps900.Utlis;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.tps900.tps900.Bean.TicketScanBean;
import com.example.tps900.tps900.InterFace.MessageEvent;
import com.example.tps900.tps900.R;
import com.example.tps900.tps900.activity.SltTableActivity;
import com.example.tps900.tps900.sql.Sqls;
import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.FadeExit.FadeExit;
import com.flyco.animation.FlipEnter.FlipVerticalSwingEnter;
import com.flyco.dialog.listener.OnBtnLeftClickL;
import com.flyco.dialog.listener.OnBtnRightClickL;
import com.flyco.dialog.widget.NormalDialog;

import org.greenrobot.eventbus.EventBus;

import static com.example.tps900.tps900.Utlis.MessageConstanse.DELETE_TICKET_INFO;

/**
 * 项目名称：TPS900
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2017/4/18 14:08
 * 修改人：zxh
 * 修改时间：2017/4/18 14:08
 * 修改备注：
 */

public class Dailog {
    public static BaseAnimatorSet bas_in = new FlipVerticalSwingEnter();
    public static BaseAnimatorSet bas_out = new FadeExit();
    public static NormalDialog dialog;
    public static AlertDialog dialog1;

    public static void ErrDialog(final Context context, final String type, String msg) {
        LayoutInflater factory = LayoutInflater.from(context);
        final View textEntryView = factory.inflate(R.layout.handler, null);
        final TextView editTextNumEditText = (TextView) textEntryView.findViewById(R.id.Errtext);
        editTextNumEditText.setText(msg);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("温馨提示");
        builder.setCancelable(false);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setView(textEntryView);
        // 显示对话框
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if ("退出".equals(type)) {
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if ("退出".equals(type)) {
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            }
        });
        builder.show();
    }

    /**
     * @param context 上下文
     * @param msg     提示信息
     * @param type    type为空是普通弹框  1是进入设置网络页面
     */
    public static void ErrDialog2(final Context context, String msg, final String type) {
        LayoutInflater factory = LayoutInflater.from(context);
        final View textEntryView = factory.inflate(R.layout.handler, null);
        final TextView editTextNumEditText = (TextView) textEntryView
                .findViewById(R.id.Errtext);
        editTextNumEditText.setText(msg);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("温馨提示");
        builder.setCancelable(false);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setView(textEntryView);
        // 显示对话框
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if ("".equals(type)) {
                    DismissDialog();
                } else if ("1".equals(type)) {
                    DismissDialog();
                    Intent intent = new Intent();
                    intent.setAction(android.provider.Settings.ACTION_WIFI_SETTINGS);
                    context.startActivity(intent);
                } else if ("选桌台".equals(type)) {
                    DismissDialog();
                    Intent intent = new Intent(context, SltTableActivity.class);
                    context.startActivity(intent);
                } else if ("取消订单".equals(type)) {
                    DismissDialog();
                    Intent intent = new Intent();
                    intent.putExtra("flag", true);
                    EventBus.getDefault().post(new MessageEvent(1002, -1, intent));
                    Constant.BEANS_Table.clear();
                    Constant.BEANS.clear();
                    Constant.FoodMoney = "";
                    Constant.TablePeople = "";
                    //选桌页面消失
                    EventBus.getDefault().post(new MessageEvent_Food("选桌消失"));
                    //支付页面消失
                    EventBus.getDefault().post(new MessageEvent_Food("消失"));

                } else if (MessageConstanse.WRITE_OFF_CLEARING.equals(type)) {
                    //关闭核销页面
                    EventBus.getDefault().post(new MessageEvent_Food(MessageConstanse.WRITE_OFF_CLEARING));
                } else if (MessageConstanse.DELETE_ALL_TICKET_INFO.equals(type)) {
                    //清空核销页面数据
                    EventBus.getDefault().post(new MessageEvent_Food(MessageConstanse.DELETE_ALL_TICKET_INFO));
                } else if (MessageConstanse.CONFIRM_PAYMENT.equals(type)) {
                    //确认支付
                    EventBus.getDefault().post(new MessageEvent_Food(MessageConstanse.CONFIRM_PAYMENT));
                } else if (MessageConstanse.CONFIRM_PAYMENT.equals(type)) {
                    //取消支付
                    EventBus.getDefault().post(new MessageEvent_Food(MessageConstanse.CANCEL_PAYMENT));
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
            }
        });
        DismissDialog();
        dialog1 = builder.show();
    }

    public static void ErrDialogTicket(final Context context, String msg, final TicketScanBean.TicketBean ticketBean) {
        LayoutInflater factory = LayoutInflater.from(context);
        final View textEntryView = factory.inflate(R.layout.handler, null);
        final TextView editTextNumEditText = (TextView) textEntryView
                .findViewById(R.id.Errtext);
        editTextNumEditText.setText(msg);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("温馨提示");
        builder.setCancelable(false);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setView(textEntryView);
        // 显示对话框
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                DismissDialog();
                Sqls.deleteWriteOffInfo(ticketBean.getTicketCode());
                // 发送给核销页面更新界面
                EventBus.getDefault().post(new MessageEvent_Food(DELETE_TICKET_INFO));
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
            }
        });
        DismissDialog();
        dialog1 = builder.show();
    }

    /**
     * 是否核销
     *
     * @param context
     * @param msg
     */
    public static void ISPrintTicket(final Context context, String msg) {
        LayoutInflater factory = LayoutInflater.from(context);
        final View textEntryView = factory.inflate(R.layout.handler, null);
        final TextView editTextNumEditText = (TextView) textEntryView
                .findViewById(R.id.Errtext);
        editTextNumEditText.setText(msg);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("温馨提示");
        builder.setCancelable(false);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setView(textEntryView);
        // 显示对话框
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                Intent intent = new Intent();
                intent.setAction("WebService");
                context.sendBroadcast(intent);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                Intent intent = new Intent();
                intent.setAction("Intent");
                context.sendBroadcast(intent);

            }
        });
        DismissDialog();
        dialog1 = builder.show();

    }

    public static void DismissDialog() {
        if (dialog1 != null) {
            dialog1.dismiss();
            dialog1=null;
        }
    }

    public static void DismissDialog1() {
        if (dialog != null) {
            dialog.dismiss();
            dialog=null;
        }
    }

    public static void NormalDialogStyleOne(final Context context, String title, final String Code, final String Code2) {
        DismissDialog1();
        dialog = new NormalDialog(context);
        dialog.content(title)
                .showAnim(bas_in)
                .dimEnabled(false)
                .dismissAnim(bas_out)
                .setCanceledOnTouchOutside(true);
        dialog.show();
        dialog.setOnBtnLeftClickL(new OnBtnLeftClickL() {
            @Override
            public void onBtnLeftClick() {
                if ("qrcode".equals(Code)) {
                    Intent intent = new Intent();
                    intent.setAction("qrcode");
                    context.sendBroadcast(intent);
                } else if ("Intent".equals(Code)) {
                    Intent intent = new Intent();
                    intent.setAction("Intent");
                    context.sendBroadcast(intent);
                } else if ("GetDeviceTicket".equals(Code)) {
                    Intent intent = new Intent();
                    intent.setAction("GetDeviceTicket");
                    context.sendBroadcast(intent);
                } else if ("TVqrcode".equals(Code)) {
                    Intent intent = new Intent();
                    intent.setAction("TVqrcode");
                    context.sendBroadcast(intent);
                }
                dialog.dismiss();

            }
        });

        dialog.setOnBtnRightClickL(new OnBtnRightClickL() {
            @Override
            public void onBtnRightClick() {
                if ("Intent".equals(Code2)) {
                    Intent intent = new Intent();
                    intent.setAction("Intent");
                    context.sendBroadcast(intent);
                } else if ("listView".equals(Code2)) {
                    Intent intent = new Intent();
                    intent.setAction("listView");
                    context.sendBroadcast(intent);
                }
                dialog.dismiss();
            }
        });


        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if ("listView".equals(Code2)) {
                    Intent intent = new Intent();
                    intent.setAction("listView");
                    context.sendBroadcast(intent);
                }
            }

        });


    }
    /**
     * 退款前的提示
     * @param context
     * @param msg
     */
    public static void RefundErrDialog(final Context context, String msg,DialogInterface.OnClickListener listener) {
        LayoutInflater factory = LayoutInflater.from(context);
        final View textEntryView = factory.inflate(R.layout.handler, null);
        final TextView editTextNumEditText = (TextView) textEntryView.findViewById(R.id.Errtext);
        editTextNumEditText.setText(msg);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("温馨提示");
        builder.setCancelable(false);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setView(textEntryView);
        // 显示对话框
        builder.setPositiveButton("确定", listener);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

            }
        });
        builder.show();
    }
    public static void NetErrDialog(final Context context, String msg,DialogInterface.OnClickListener listener) {
        LayoutInflater factory = LayoutInflater.from(context);
        final View textEntryView = factory.inflate(R.layout.handler, null);
        final TextView editTextNumEditText = (TextView) textEntryView.findViewById(R.id.Errtext);
        editTextNumEditText.setText(msg);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("温馨提示");
        builder.setCancelable(false);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setView(textEntryView);
        // 显示对话框
        builder.setPositiveButton("确定", listener);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

            }
        });
        builder.show();
    }
}
