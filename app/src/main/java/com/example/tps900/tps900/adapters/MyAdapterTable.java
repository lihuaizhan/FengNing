package com.example.tps900.tps900.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.tps900.tps900.Bean.TableInfoBean;
import com.example.tps900.tps900.R;

import java.util.ArrayList;


/**
 * Created by wo on 2017/5/2.
 */

public class MyAdapterTable extends BaseAdapter {

    public static final String TAG = "MyAdapter_XS";

    private LayoutInflater mInflater;
    private ArrayList<TableInfoBean.DtBean> BEANS_Table;
    private TextView settlement;
    private TextView shoppingPrise;
    private TextView shoppingNum;
    private Context context;
    private TextView defaultText;

    public MyAdapterTable(Context context, ArrayList<TableInfoBean.DtBean> BEANS_Table, TextView settlement, TextView shoppingPrise, TextView shoppingNum, TextView defaultText) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.BEANS_Table = BEANS_Table;
        this.settlement = settlement;
        this.shoppingPrise = shoppingPrise;
        this.shoppingNum = shoppingNum;
        this.defaultText = defaultText;
    }

    @Override
    public int getCount() {
        return BEANS_Table.size();
    }

    @Override
    public TableInfoBean.DtBean getItem(int i) {
        return BEANS_Table.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TableInfoBean.DtBean detailBean01 = BEANS_Table.get(i);

        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = mInflater.inflate(R.layout.item_cart, null);
            holder.name = (TextView) view.findViewById(R.id.name_ticket_cart);
            holder.price = (TextView) view.findViewById(R.id.price_ticket_cart);
            holder.minus = (Button) view.findViewById(R.id.btn_minus_cart);
            holder.count = (TextView) view.findViewById(R.id.text_Count_cart);
            holder.plus = (Button) view.findViewById(R.id.btn_plus_cart);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final TableInfoBean.DtBean detailBean = BEANS_Table.get(i);

        holder.name.setText(detailBean.getDESKNAME());
        holder.price.setText(String.valueOf(detailBean.getDPRICE()));
        holder.count.setText(String.valueOf(detailBean.getTicketnumber()));
        holder.plus.setVisibility(View.GONE);
        holder.minus.setVisibility(View.GONE);
//        holder.plus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int number = detailBean.getTicketnumber();
//                number = number + 1;
//                LogUtil.i(TAG, "数量----->" + number);
//                detailBean.setTicketnumber(number);
//                holder.count.setText(String.valueOf(detailBean.getTicketnumber()));
//
//                String trim = shoppingNum.getText().toString().trim();
//                int totalNo = parseInt(trim);
//                totalNo = totalNo + 1;
//                shoppingNum.setText(String.valueOf(totalNo));
//
//                String trim1 = shoppingPrise.getText().toString().trim();
//                double totalMoney = Double.parseDouble(trim1);
//                totalMoney = totalMoney + Double.valueOf(detailBean.getDPRICE());
//                shoppingPrise.setText(new DecimalFormat("0.00").format(totalMoney));
//
//
//            }
//        });
//
//        holder.minus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int number = detailBean.getTicketnumber();
//                if (number > 0) {
//                    number = number - 1;
//                    detailBean.setTicketnumber(number);
//                    holder.count.setText(String.valueOf(detailBean.getTicketnumber()));
//
//                    if (number == 0) {
//                        BEANS_Table.remove(detailBean);
//                        notifyDataSetChanged();
//
//                    } else {
//                        detailBean.setTicketnumber(number);
//                        holder.count.setText(String.valueOf(detailBean.getTicketnumber()));
//                    }
//
//                }
//
//                if (BEANS_Table.size() == 0) {
//                    settlement.setVisibility(View.GONE);
//                    shoppingPrise.setTextColor(context.getResources().getColor(R.color.default_shopping));
//                    shoppingNum.setVisibility(View.GONE);
//                    defaultText.setVisibility(View.VISIBLE);
//                    shoppingPrise.setText("0.00");
//                }
//
//                String trim = shoppingNum.getText().toString().trim();
//                int totalNo = parseInt(trim);
//                if (totalNo > 0) {
//                    totalNo = totalNo - 1;
//                    shoppingNum.setText(String.valueOf(totalNo));
//                }
//
//
//                String trim1 = shoppingPrise.getText().toString().trim();
//                double totalMoney = Double.parseDouble(trim1);
//                if (totalMoney > 0) {
//                    totalMoney = totalMoney - Double.valueOf(detailBean.getDPRICE());
//                    shoppingPrise.setText(new DecimalFormat("0.00").format(totalMoney));
//
//                }
//
//            }
//        });

        return view;
    }

    static class ViewHolder {

        public TextView name;
        public TextView price;
        public Button minus;
        public TextView count;
        public Button plus;


    }

}
