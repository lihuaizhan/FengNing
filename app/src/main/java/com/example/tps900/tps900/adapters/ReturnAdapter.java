package com.example.tps900.tps900.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.example.tps900.tps900.R;
import com.example.tps900.tps900.Utlis.LogUtil;
import com.example.tps900.tps900.Bean.ReturnBean;
import com.godfery.Utils.ToastUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by wo on 2017/5/16.
 */

public class ReturnAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private ArrayList<ReturnBean> returnList;
    private Context context;
    private TextView shouldMoney;

    public static final String TAG = "ReturnAdapter";

    public ReturnAdapter(ArrayList<ReturnBean> returnList, Context context,TextView shouldMoney){
        mInflater = LayoutInflater.from(context);
        this.returnList = returnList;
        this.context = context;
        this.shouldMoney = shouldMoney;
    }

    @Override
    public int getCount() {
        return returnList.size();
    }

    @Override
    public ReturnBean getItem(int position) {
        return returnList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final ViewHolder holder;

        if(convertView == null){

            convertView = mInflater.inflate(R.layout.item_return,null);
            holder = new ViewHolder();
            holder.goodsname = (TextView) convertView.findViewById(R.id.name_ticket_cart);
            holder.goodsPrice = (TextView) convertView.findViewById(R.id.price_ticket_cart);
            holder.canReturnNo = (TextView) convertView.findViewById(R.id.canReturn_ticket_cart);
            holder.minus = (Button) convertView.findViewById(R.id.btn_minus_cart);
            holder.goodsCount = (TextView) convertView.findViewById(R.id.text_Count_cart);
            holder.plus = (Button) convertView.findViewById(R.id.btn_plus_cart);

            convertView.setTag(holder);

        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        final ReturnBean returnBean = returnList.get(i);

        holder.goodsname.setText(returnBean.getVCOMMYNAME());
        holder.goodsPrice.setText(String.valueOf(returnBean.getNPRICE()));
        holder.goodsCount.setText(String.valueOf(returnBean.getRCANNUMBER()));
        holder.canReturnNo.setText(String.valueOf(returnBean.getRETURNNUMBER()));


        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number = returnBean.getRCANNUMBER();
                if(number > 0){
                    number = number - 1;
                    returnBean.setRCANNUMBER(number);
                    LogUtil.i("商品数量",returnBean.getRCANNUMBER()+"");
                    holder.goodsCount.setText(String.valueOf(returnBean.getRCANNUMBER()));

                    if(number==0){
                        holder.minus.setClickable(false);
                        ToastUtils.show(context,"您已放弃该商品的退款!");

                    }else {
                        returnBean.setRCANNUMBER(number);
                        holder.goodsCount.setText(String.valueOf(returnBean.getRCANNUMBER()));
                    }

                    String trim1 = shouldMoney.getText().toString().trim();
                    double totalMoney = Double.parseDouble(trim1);
                    if(totalMoney >0){
                        totalMoney = totalMoney - returnBean.getNPRICE();
                        shouldMoney.setText(new DecimalFormat("0.00").format(totalMoney));

                    }

                }else{
                    holder.minus.setClickable(false);
                }
            }
        });

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.minus.setClickable(true);

                int number = returnBean.getRCANNUMBER();
                int returnNumber = returnBean.getRETURNNUMBER();

                if(number < returnNumber){

                    number = number + 1;
                    LogUtil.i(TAG,"数量----->"+number);
                    returnBean.setRCANNUMBER(number);
                    holder.goodsCount.setText(String.valueOf(returnBean.getRCANNUMBER()));

                    String trim1 = shouldMoney.getText().toString().trim();
                    double totalMoney = Double.parseDouble(trim1);
                    totalMoney = totalMoney + returnBean.getNPRICE();
                    shouldMoney.setText(new DecimalFormat("0.00").format(totalMoney));
                }else{
                    ToastUtils.show(context,"该商品可退数量为"+number);
                }
            }
        });

        return convertView;
    }

    static class ViewHolder{
        //商品名称
        public TextView goodsname;
        //商品价格
        public TextView goodsPrice;
        //数量减
        public Button minus;
        //商品数量
        public TextView goodsCount;
        //数量加
        public Button plus;
        //可退数量
        public TextView canReturnNo;


    }

}
