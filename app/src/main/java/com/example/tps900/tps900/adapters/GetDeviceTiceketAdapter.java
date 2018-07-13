package com.example.tps900.tps900.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tps900.tps900.Bean.GetDeviceTicketBean;
import com.example.tps900.tps900.R;

import java.util.ArrayList;


/**
 * 项目名称：TPS900
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2017/4/23 16:04
 * 修改人：zxh
 * 修改时间：2017/4/23 16:04
 * 修改备注：
 */

public class GetDeviceTiceketAdapter extends BaseAdapter {
    public ArrayList<GetDeviceTicketBean.TicketsBean> data;
    public Context context;

    public GetDeviceTiceketAdapter(ArrayList<GetDeviceTicketBean.TicketsBean> data, Context context) {
        this.data = data;
        this.context = context;
    }

    public ArrayList<GetDeviceTicketBean.TicketsBean> getData() {
        return data;
    }

    public void setData(ArrayList<GetDeviceTicketBean.TicketsBean> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if(convertView == null){
            vh = new ViewHolder();
            convertView = LayoutInflater.from(context ).inflate(R.layout.item_cost, null);
            vh.itemCostName = (TextView) convertView.findViewById(R.id.item_cost_name);
            vh.itemCostPrice = (TextView) convertView.findViewById(R.id.item_cost_price);
            vh.itemCostPersonNum = (TextView) convertView.findViewById(R.id.item_cost_personNum);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        vh.itemCostName.setText(data.get(position).getSticketnamech());
        vh.itemCostPrice.setText(String.valueOf(data.get(position).getNgeneralprice()) + " 元");
        vh.itemCostPersonNum.setText(data.get(position).getNpeoplecount() + " 人");
        return convertView;
    }

    static class ViewHolder {

        TextView itemCostName;

        TextView itemCostPrice;

        TextView itemCostPersonNum;

    }
}
