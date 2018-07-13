package com.example.tps900.tps900.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.tps900.tps900.Bean.GetTicket_Bean;
import com.example.tps900.tps900.R;
import com.example.tps900.tps900.Utlis.Constant;
import com.example.tps900.tps900.Utlis.LogUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;


/**
 * Created by wo on 2017/5/2.
 */

public class MyAdapter_XS extends BaseAdapter {

    public static final String TAG = "MyAdapter_XS";

    private LayoutInflater mInflater;
    private ArrayList<GetTicket_Bean.DataBean> BEANS_XS;
    private TextView settlement;
    private TextView shoppingPrise;
    private TextView shoppingNum;
    private Context context;
    private TextView defaultText;

    public MyAdapter_XS(Context context, ArrayList<GetTicket_Bean.DataBean> BEANS_XS, TextView settlement, TextView shoppingPrise, TextView shoppingNum, TextView defaultText){
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.BEANS_XS = BEANS_XS;
        this.settlement = settlement;
        this.shoppingPrise = shoppingPrise;
        this.shoppingNum = shoppingNum;
        this.defaultText = defaultText;
    }

    @Override
    public int getCount() {
        return BEANS_XS.size();
    }

    @Override
    public GetTicket_Bean.DataBean getItem(int i) {
        return BEANS_XS.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        GetTicket_Bean.DataBean detailBean01 = BEANS_XS.get(i);
        LogUtil.i(TAG,"detailBean01--->"+detailBean01.getTicketnumber());

        final ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            view = mInflater.inflate(R.layout.item_cart,null);
            holder.name = (TextView) view.findViewById(R.id.name_ticket_cart);
            holder.price = (TextView)view.findViewById(R.id.price_ticket_cart);
            holder.minus = (Button) view.findViewById(R.id.btn_minus_cart);
            holder.count = (TextView) view.findViewById(R.id.text_Count_cart);
            holder.plus = (Button) view.findViewById(R.id.btn_plus_cart);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        final GetTicket_Bean.DataBean detailBean = BEANS_XS.get(i);

        holder.name.setText(detailBean.getProductName());
        LogUtil.e("名字---------->",detailBean.getProductName()+"---------");
        LogUtil.e("价格---------->",String.valueOf(detailBean.getProductPrice())+"--------");
        LogUtil.e("数量---------->",String.valueOf(detailBean.getTicketnumber())+"-------");

        holder.price.setText(String.valueOf(detailBean.getProductPrice()));
        holder.count.setText(String.valueOf(detailBean.getTicketnumber()));

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number = detailBean.getTicketnumber();
                number = number + 1;
                LogUtil.i(TAG,"数量----->"+number);
                detailBean.setTicketnumber(number);
                holder.count.setText(String.valueOf(detailBean.getTicketnumber()));

                String trim = shoppingNum.getText().toString().trim();
                int totalNo = parseInt(trim);
                totalNo = totalNo + 1;
                shoppingNum.setText(String.valueOf(totalNo));

                String trim1 = shoppingPrise.getText().toString().trim();
                double totalMoney = Double.parseDouble(trim1);
                totalMoney = totalMoney + Double.valueOf(detailBean.getProductPrice()) ;
                shoppingPrise.setText(new DecimalFormat("0.00").format(totalMoney));


            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number = detailBean.getTicketnumber();
                if(number > 0){
                    number = number - 1;
                    detailBean.setTicketnumber(number);
                    holder.count.setText(String.valueOf(detailBean.getTicketnumber()));

                    if(number==0){
                        BEANS_XS.remove(detailBean);
                        notifyDataSetChanged();

                    }else {
                        detailBean.setTicketnumber(number);
                        holder.count.setText(String.valueOf(detailBean.getTicketnumber()));
                    }

                }

                if(Constant.BEANS_XS.size() == 0){
                    settlement.setVisibility(View.GONE);
                    shoppingPrise.setTextColor(context.getResources().getColor(R.color.default_shopping));
                    shoppingNum.setVisibility(View.GONE);
                    defaultText.setVisibility(View.VISIBLE);
                    shoppingPrise.setText("0.00");
                }

                String trim = shoppingNum.getText().toString().trim();
                int totalNo = parseInt(trim);
                if(totalNo > 0){
                    totalNo = totalNo - 1;
                    shoppingNum.setText(String.valueOf(totalNo));
                }


                String trim1 = shoppingPrise.getText().toString().trim();
                double totalMoney = Double.parseDouble(trim1);
                if(totalMoney >0){
                    totalMoney = totalMoney - Double.valueOf(detailBean.getProductPrice());
                    shoppingPrise.setText(new DecimalFormat("0.00").format(totalMoney));

                }

            }
        });

        return view;
    }

    static class ViewHolder{

        public TextView name;
        public TextView price;
        public Button minus;
        public TextView count;
        public Button plus;


    }

}
