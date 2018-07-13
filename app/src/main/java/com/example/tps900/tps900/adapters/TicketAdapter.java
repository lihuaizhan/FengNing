package com.example.tps900.tps900.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tps900.tps900.Bean.Project_feeBean;
import com.example.tps900.tps900.R;

import java.util.ArrayList;




import static com.example.tps900.tps900.Utlis.Constant.context;

/**
 * Created by wo on 2017/5/26.
 */

public class TicketAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private ArrayList<Project_feeBean.TicketPrintsBean> list;


    public TicketAdapter(Context context, ArrayList<Project_feeBean.TicketPrintsBean> list) {
        mInflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Project_feeBean.TicketPrintsBean getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_ticket_form, null);
            holder = new ViewHolder(convertView);
            holder.tvTicketNameForm = (TextView) convertView.findViewById(R.id.tv_ticketName_form);
            holder.tvTicketPriceForm = (TextView) convertView.findViewById(R.id.tv_ticketPrice_form);
            holder.tvTicketNumForm = (TextView) convertView.findViewById(R.id.tv_ticketNum_form);
            holder.tvTicketAllMoneyForm = (TextView) convertView.findViewById(R.id.tv_ticket_allMoney_form);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();

        Project_feeBean.TicketPrintsBean bean = list.get(i);

        holder.tvTicketNameForm.setText(list.get(i).getSticketnamech());
        holder.tvTicketPriceForm.setText(list.get(i).getNprice());
        holder.tvTicketNumForm.setText(list.get(i).getNpeoplecount());
        double Price = Double.valueOf(list.get(i).getNprice()) * Double.valueOf(list.get(i).getNpeoplecount());
        holder.tvTicketAllMoneyForm.setText(Price + "元");
        Log.e("价格是" + i + "----->", Price + "元");
        return convertView;
    }


    class ViewHolder {

        TextView tvTicketNameForm;

        TextView tvTicketPriceForm;

        TextView tvTicketNumForm;

        TextView tvTicketAllMoneyForm;

        ViewHolder(View view) {

        }
    }
}
