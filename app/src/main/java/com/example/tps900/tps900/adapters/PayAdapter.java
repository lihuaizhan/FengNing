package com.example.tps900.tps900.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.tps900.tps900.Bean.PayBean;
import com.example.tps900.tps900.R;

import java.util.ArrayList;

/**
 * Created by wo on 2017/5/3.
 */

public class PayAdapter extends BaseAdapter {

    private ArrayList<PayBean.DtBean> list;
    private LayoutInflater mInflater;
    private int selectedIndex=-1;

    public PayAdapter(ArrayList<PayBean.DtBean> list, Context context) {
        this.list = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public PayBean.DtBean getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_pay, null);
            holder = new ViewHolder();

            holder.icon = (ImageView) convertView.findViewById(R.id.img_payIcon);
            holder.name = (TextView) convertView.findViewById(R.id.tv_payName);
            holder.isChecked = (RadioButton) convertView.findViewById(R.id.cb_paySelector);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        PayBean.DtBean payBean = list.get(position);
        if (8.0 == payBean.getNPAYMENTTYPEX()) {
            //微信
            holder.icon.setImageResource(R.drawable.wechat);
        } else if (1.0 == payBean.getNPAYMENTTYPEX()) {
            //人民币
            holder.icon.setImageResource(R.drawable.pay_food_cash);
        } else if (5.0 == payBean.getNPAYMENTTYPEX()) {
            //支付宝
            holder.icon.setImageResource(R.drawable.alipay);
        }
//        else if (3.0 == payBean.getNPAYMENTTYPEX()) {
//            //一卡通
//            holder.icon.setImageResource(R.drawable.pay_food_onecard);
//        }
        else {
            switch (payBean.getSMONEYNAMECNX()) {
                case "银联":
                    holder.icon.setImageResource(R.drawable.apily_union);
                    break;
                case "海盗储值卡":
                    holder.icon.setImageResource(R.drawable.apily_priate);
                    break;
                case "晚饭":
                    holder.icon.setImageResource(R.drawable.pay_food_cash);
                    break;
                    case "一卡通":
                        holder.icon.setImageResource(R.drawable.pay_food_onecard);
                    break;
                default:
                    break;
            }

        }
        holder.name.setText(payBean.getSMONEYNAMECNX());
        holder.isChecked.setChecked(false);
        if (selectedIndex == position) {
            holder.isChecked.setChecked(true);
            payBean.setChecked(true);
        } else {
            holder.isChecked.setChecked(false);
            payBean.setChecked(false);
        }
        return convertView;
    }

    public void setSelectedIndex(int position) {
        this.selectedIndex = position;
    }

    static class ViewHolder {

        public ImageView icon;
        public TextView name;
        public RadioButton isChecked;

    }


}
