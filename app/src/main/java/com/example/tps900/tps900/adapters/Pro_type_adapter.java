package com.example.tps900.tps900.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tps900.tps900.R;
import com.example.tps900.tps900.Bean.DetailBean;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class Pro_type_adapter extends BaseAdapter {
    // 定义Context
    private LayoutInflater mInflater;
    private ArrayList<DetailBean.DtBean> list;
    private Context context;
    private DetailBean.DtBean bean;

    public Pro_type_adapter(Context context, ArrayList<DetailBean.DtBean> list) {
        mInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (list != null && list.size() > 0) {
            return list.size();
        } else {
            return 0;
        }
    }

    @Override
    public DetailBean.DtBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final MyView view;
        if (convertView == null) {
            view = new MyView();
            convertView = mInflater.inflate(R.layout.list_pro_type_item, null);
            view.name = (TextView) convertView.findViewById(R.id.tv_goodsName);
            view.count = (TextView) convertView.findViewById(R.id.tv_goodsCount);
            view.price = (TextView) convertView.findViewById(R.id.tv_goodsPrice);

            convertView.setTag(view);
        } else {
            view = (MyView) convertView.getTag();
        }
        if (list != null && list.size() > 0) {
            bean = list.get(position);

            view.name.setText(bean.getVCOMMYNAME());
            view.count.setText("1");
            view.price.setText(new DecimalFormat("#0.00").format(bean.getPRICE()));
        }

        return convertView;
    }


    private class MyView {

        public TextView name;
        public TextView count;
        public TextView price;

    }

}
