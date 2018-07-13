package com.example.tps900.tps900.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tps900.tps900.Bean.TestBean;
import com.example.tps900.tps900.R;

import java.util.ArrayList;


/**
 * Created by wo on 2017/5/2.
 */

public class TestAdapter extends BaseAdapter {


    private LayoutInflater mInflater;
    private ArrayList<TestBean> beans;
    private Context context;

    public TestAdapter(Context context, ArrayList<TestBean> beans) {
        this.context = context;
        this.beans = beans;
        mInflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return beans.size();
    }

    @Override
    public TestBean getItem(int i) {
        return beans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = mInflater.inflate(R.layout.test_item, null);
            holder.result = (TextView) view.findViewById(R.id.test_item_tv);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final TestBean testBean = beans.get(i);
        if (testBean.getCode().equals("err")) {
            view.setBackgroundColor(Color.RED);
            holder.result.setText(testBean.getResult());
        }
        return view;
    }

    static class ViewHolder {
        public TextView result;
    }

}
