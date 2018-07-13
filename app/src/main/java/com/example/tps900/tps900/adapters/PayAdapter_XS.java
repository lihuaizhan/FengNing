package com.example.tps900.tps900.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.tps900.tps900.Bean.PayTypeBean;
import com.example.tps900.tps900.R;
import com.example.tps900.tps900.WEBSERVICE_Utils.Constants;

import java.util.List;

/**
 * Created by wo on 2017/5/3.
 */

public class PayAdapter_XS extends BaseAdapter {

    private List< PayTypeBean> list;
    private LayoutInflater mInflater;
    private int selectedIndex=-1;

    public PayAdapter_XS(List<PayTypeBean> list, Context context){
        this.list = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public  PayTypeBean getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.item_pay,null);
            holder = new ViewHolder();

            holder.icon = (ImageView) convertView.findViewById(R.id.img_payIcon);
            holder.name = (TextView) convertView.findViewById(R.id.tv_payName);
            holder.isChecked = (RadioButton) convertView.findViewById(R.id.cb_paySelector);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
       PayTypeBean payBean = list.get(position);
        switch ( list.get(position).getTypeCode()) {
            case "001":
                holder.icon.setImageResource(R.drawable.wechat);
                break;
            case "002":
                holder.icon.setImageResource(R.drawable.alipay);
                break;
            case "003":
                holder.icon.setImageResource(R.drawable.cash);
                break;
            case "004":
                Constants.BankCard = "004";
                holder.icon.setImageResource(R.drawable.cash);
                break;
            case "005":
                Constants.OneCard = "005";
                holder.icon.setImageResource(R.drawable.cash);
                break;
            case "006":
                Constants.Account = "006";
                holder.icon.setImageResource(R.drawable.cash);
                break;
            case "007":
                Constants.CardVoucher = "007";
                holder.icon.setImageResource(R.drawable.cash);
                break;
            default:
                break;
        }
        holder.name.setText(payBean.getTypeName());
        holder.isChecked.setChecked(payBean.isChecked());
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

    static class ViewHolder{

        public ImageView icon;
        public TextView name;
        public RadioButton isChecked;

    }


}
