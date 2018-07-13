package com.example.tps900.tps900.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tps900.tps900.Bean.TicketFormsBean;
import com.example.tps900.tps900.R;
import com.example.tps900.tps900.Utlis.OtherUtils;

import java.text.ParseException;
import java.util.ArrayList;




/**
 * 项目名称：T_S_TPS900_AFK
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2017/6/15 16:46
 * 修改人：zxh
 * 修改时间：2017/6/15 16:46
 * 修改备注：
 */

public class TicketFormAdapter extends BaseAdapter {
    public Context c;
    public ArrayList<TicketFormsBean> classifysalelist;

    public TicketFormAdapter(Context c, ArrayList<TicketFormsBean> classifysalelist) {
        this.c = c;
        this.classifysalelist = classifysalelist;
    }

    public Context getC() {
        return c;
    }

    public void setC(Context c) {
        this.c = c;
    }

    public ArrayList<TicketFormsBean> getClassifysalelist() {
        return classifysalelist;
    }

    public void setClassifysalelist(ArrayList<TicketFormsBean> classifysalelist) {
        this.classifysalelist = classifysalelist;
    }

    @Override
    public int getCount() {
        return classifysalelist.size();
    }

    @Override
    public Object getItem(int position) {
        return classifysalelist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(c).inflate(R.layout.item_ticket_form, null);
            holder = new ViewHolder(convertView);
            holder.tvTicketNameForm = (TextView) convertView.findViewById(R.id.tv_ticketName_form);
            holder.tvTicketPriceForm = (TextView) convertView.findViewById(R.id.tv_ticketPrice_form);
            holder.tvTicketNumForm = (TextView) convertView.findViewById(R.id.tv_ticketNum_form);
            holder.tvTicketAllMoneyForm = (TextView) convertView.findViewById(R.id.tv_ticket_allMoney_form);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();

        TicketFormsBean bean = classifysalelist.get(position);
        holder.tvTicketNameForm.setText(bean.getTICKETNAME());
        holder.tvTicketPriceForm.setText(bean.getTICKETMONEY());
        holder.tvTicketNumForm.setText(bean.getINPARK());
        double price = Double.parseDouble(bean.getTICKETMONEY());
        int number = Integer.parseInt(bean.getINPARK());
        try {
            holder.tvTicketAllMoneyForm.setText(OtherUtils.doubleprice(String.valueOf(price * number)) + "");
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
