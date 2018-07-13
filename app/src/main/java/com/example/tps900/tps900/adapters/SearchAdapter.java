package com.example.tps900.tps900.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tps900.tps900.R;
import com.example.tps900.tps900.Bean.DetailBean;

import java.util.ArrayList;

/**
 * Created by wo on 2017/5/17.
 */

public class SearchAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<DetailBean.DtBean> searchList;
    private final LayoutInflater mInflater;

    public SearchAdapter(Context context, ArrayList<DetailBean.DtBean> searchList){
        this.context = context;
        this.searchList = searchList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return searchList.size();
    }

    @Override
    public DetailBean.DtBean getItem(int i) {
        return searchList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.list_pro_type_item_xs, null);
            holder = new ViewHolder();

            holder.goodsName = (TextView) convertView.findViewById(R.id.tv_goodsName);
            holder.goodsNo = (TextView) convertView.findViewById(R.id.tv_goodsCount);
            holder.goodsprice = (TextView) convertView.findViewById(R.id.tv_goodsPrice);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        DetailBean.DtBean detailBean = searchList.get(i);

        holder.goodsName.setText(detailBean.getVCOMMYNAME());
        holder.goodsNo.setText("1");
        holder.goodsprice.setText(String.valueOf(detailBean.getPRICE()));

        return convertView;
    }


    static class ViewHolder{

        public TextView goodsName;
        public TextView goodsNo;
        public TextView goodsprice;


    }


}
