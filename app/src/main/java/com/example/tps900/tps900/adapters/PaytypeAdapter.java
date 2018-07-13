package com.example.tps900.tps900.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tps900.tps900.Bean.SalaTicketFormsBean;
import com.example.tps900.tps900.R;
import com.example.tps900.tps900.Utlis.OtherUtils;

import java.text.ParseException;
import java.util.ArrayList;




/**
 * 项目名称：T_S_TPS900_AFK
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2017/6/26 16:53
 * 修改人：zxh
 * 修改时间：2017/6/26 16:53
 * 修改备注：
 */

public class PaytypeAdapter extends BaseAdapter{
    public Context c;
    public ArrayList<SalaTicketFormsBean> classifysalelist;

    public PaytypeAdapter(Context c, ArrayList<SalaTicketFormsBean> classifysalelist) {
        this.c = c;
        this.classifysalelist = classifysalelist;
    }

    public Context getC() {
        return c;
    }

    public void setC(Context c) {
        this.c = c;
    }

    public ArrayList<SalaTicketFormsBean> getClassifysalelist() {
        return classifysalelist;
    }

    public void setClassifysalelist(ArrayList<SalaTicketFormsBean> classifysalelist) {
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
            convertView = LayoutInflater.from(c).inflate(R.layout.item_saleticket_form, null);

            holder = new ViewHolder(convertView);
            holder.tvTicketNameForm= (TextView) convertView.findViewById(R.id.tv_ticketName_form);
            holder.tvTicketPriceForm= (TextView) convertView.findViewById(R.id.tv_ticketPrice_form);
            holder.tvTicketNumForm= (TextView) convertView.findViewById(R.id.tv_ticketNum_form);
            holder.tvTicketAllMoneyForm= (TextView) convertView.findViewById(R.id.tv_ticket_allMoney_form);
            holder.tvTicketPaytype= (TextView) convertView.findViewById(R.id.tv_ticket_paytype_form);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();

        SalaTicketFormsBean bean = classifysalelist.get(position);

        double price = Double.parseDouble(bean.getITEMMONEY());
        int number = Integer.parseInt(bean.getAMOUNT());
        holder.tvTicketNameForm.setText(bean.getPAYMENTSTYLE());
        try {
            holder.tvTicketPriceForm.setText(OtherUtils.doubleprice(String.valueOf(price)) + "");
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

        TextView tvTicketPaytype;

        ViewHolder(View view) {

        }
    }
}
