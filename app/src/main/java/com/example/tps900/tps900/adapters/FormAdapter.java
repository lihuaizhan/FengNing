package com.example.tps900.tps900.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tps900.tps900.R;
import com.example.tps900.tps900.Utlis.OtherUtils;
import com.example.tps900.tps900.Bean.FormBean;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by wo on 2017/5/26.
 */

public class FormAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private ArrayList<FormBean> list;
    private double test;


    public FormAdapter(Context context, ArrayList<FormBean> list){
        mInflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public FormBean getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;

        if(convertView == null){
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_form,null);

            holder.payType = (TextView) convertView.findViewById(R.id.tv_payType_form);
            holder.payMoney = (TextView) convertView.findViewById(R.id.tv_payMoney_form);

            convertView.setTag(holder);

        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        FormBean bean = list.get(i);

        holder.payType.setText(bean.getSMONEYNAMECN());

        try {
              test = OtherUtils.doubleprice(String.valueOf(bean.getNAMOUNT()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(Double.parseDouble(bean.getNAMOUNT())>1.00){
            holder.payMoney.setText(test+"");
        }else{
            holder.payMoney.setText(test+"");
        }
        return convertView;
    }


    static class ViewHolder{
        //支付类型
        public TextView payType;
        //支付金额
        public TextView payMoney;
    }

}
