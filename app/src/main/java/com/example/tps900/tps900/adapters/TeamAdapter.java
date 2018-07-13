package com.example.tps900.tps900.adapters;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tps900.tps900.Bean.TeamBean_1;
import com.example.tps900.tps900.R;
import com.example.tps900.tps900.view.CounterView;
import com.example.tps900.tps900.view.IChangeCoutCallback;
import com.godfery.Utils.ToastUtils;

import java.util.List;

/**
 * Created by zxh on 2016/11/14.
 */

public class TeamAdapter extends BaseAdapter {
    private List<TeamBean_1.DataBean.TeamOrdersBean> teamBean1;
    private Activity context;
    public TextView ticketName;
    public TextView ticketPrice;
    public TextView shouldPerson;
    public EditText actualPerson;
    public TextView shouldMoney;
    public Button confirm;
    private CounterView cvCounter;

    public TeamAdapter(List<TeamBean_1.DataBean.TeamOrdersBean> teamBean1, Activity context) {
        this.teamBean1 = teamBean1;
        this.context = context;
    }

    public List<TeamBean_1.DataBean.TeamOrdersBean> getTeamBean1() {
        return teamBean1;
    }

    public void setTeamBean1(List<TeamBean_1.DataBean.TeamOrdersBean> teamBean1) {
        this.teamBean1 = teamBean1;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return teamBean1.size();
    }

    @Override
    public TeamBean_1.DataBean.TeamOrdersBean getItem(int position) {
        return teamBean1.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final TeamBean_1.DataBean.TeamOrdersBean bean = teamBean1.get(position);
        convertView = View.inflate(context.getApplicationContext(), R.layout.item_normal_team, null);
        //门票名称
        ticketName = (TextView) convertView
                .findViewById(R.id.text_TicketName);
        //门票价格
        ticketPrice = (TextView) convertView
                .findViewById(R.id.text_TicketPrice);
        //应到人数
        shouldPerson = (TextView) convertView
                .findViewById(R.id.text_shouldPerson);
        //实到人数
        actualPerson = (EditText) convertView.findViewById(R.id.edit_ActualPerson);
        //应付金额
        shouldMoney = (TextView) convertView
                .findViewById(R.id.text_shouldMoney);
        confirm = (Button) convertView.findViewById(R.id.btn_confirm_ActualPerson);//R.id.cv_counter
        cvCounter = (CounterView) convertView.findViewById(R.id.cv_counter);
        //把Bean与输入框进行绑定
        actualPerson.setTag(bean);
        //清除焦点
        actualPerson.clearFocus();
        cvCounter.setMaxValue(bean.PRODUCTNUM);
        final IChangeCoutCallback callback = new IChangeCoutCallback() {
            @Override
            public void change(int count) {
                bean.setActualPerson(String.valueOf(count));
                if (null != teamBean1 && !teamBean1.isEmpty()) {
                    if (count != 0) {
                        bean.setActualPerson(String.valueOf(count));
                    }
                } else {
                    ToastUtils.show(context, "未查询到带团订单信息!");
                    return;
                }
                Intent intent = new Intent();
                intent.setAction("updata");
                context.sendBroadcast(intent);
            }
        };
        if (TextUtils.isEmpty(bean.getActualPerson())) {
            bean.setActualPerson(String.valueOf(bean.PRODUCTNUM));
            Intent intent = new Intent();
            intent.setAction("updata");
            context.sendBroadcast(intent);
        }
        cvCounter.setCallback(callback);
        cvCounter.setCountValue(bean.PRODUCTNUM);
        ticketName.setText(bean.PRODUCTNAME);
        ticketPrice.setText(String.valueOf(bean.PRODUCTPRICE));
        shouldPerson.setText(String.valueOf(bean.PRODUCTNUM));
        return convertView;
    }


}


