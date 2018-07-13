package com.example.tps900.tps900.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tps900.tps900.Bean.TableInfoBean;
import com.example.tps900.tps900.R;

import java.util.ArrayList;


public class TableInfoadapter extends BaseAdapter {
    // 定义Context
    private LayoutInflater mInflater;
    private ArrayList<TableInfoBean.DtBean> list;
    private Context context;
    private TableInfoBean.DtBean bean;

    public TableInfoadapter(Context context, ArrayList<TableInfoBean.DtBean> list) {
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
    public TableInfoBean.DtBean getItem(int position) {
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
            convertView = mInflater.inflate(R.layout.list_pro_type_item_xs, null);
            view.name = (TextView) convertView.findViewById(R.id.tv_goodsName);
            view.count = (TextView) convertView.findViewById(R.id.tv_goodsCount);
            view.price = (TextView) convertView.findViewById(R.id.tv_goodsPrice);

            convertView.setTag(view);
        } else {
            view = (MyView) convertView.getTag();
        }
        if (list != null && list.size() > 0) {
            bean = list.get(position);

            view.name.setText(bean.getDESKNAME());
            view.count.setText(String.valueOf(bean.getDNUMBER()));
            view.price.setText(String.valueOf(bean.getDPRICE()) + "元");
        }

        return convertView;
    }


    private class MyView {

        public TextView name;
        public TextView count;
        public TextView price;

    }

}
