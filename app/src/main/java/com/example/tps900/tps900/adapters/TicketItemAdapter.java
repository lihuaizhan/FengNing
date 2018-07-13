package com.example.tps900.tps900.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.tps900.tps900.Bean.TicketScanBean;
import com.example.tps900.tps900.R;
import com.example.tps900.tps900.view.CounterView_food;
import com.example.tps900.tps900.view.IChangeCoutCallback;

import java.util.List;


/**
 * Created by wo on 2017/5/2.
 */

public class TicketItemAdapter extends BaseQuickAdapter<TicketScanBean.TicketBean, BaseViewHolder> {


    private Activity activity;
    private LayoutInflater mInflater;
    private List<TicketScanBean.TicketBean> listbeans;
    private Context context;
    private CounterView_food cvCounter;
    private TextView textStatus;

    public TicketItemAdapter(@LayoutRes int layoutResId, @Nullable List<TicketScanBean.TicketBean> data, Context context, Activity activity) {
        super(layoutResId, data);
        this.context = context;
        this.listbeans = data;
        this.activity = activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, final TicketScanBean.TicketBean item) {
        cvCounter = (CounterView_food) helper.getView(R.id.item_sdpeople);
        cvCounter.setActivity(activity);
        textStatus = (TextView) helper.getView(R.id.item_status);
        helper.setText(R.id.item_num, String.valueOf(helper.getAdapterPosition() + 1));
        if (item.getTicketCode().length() > 12) {
            String code = item.getTicketCode().substring(item.getTicketCode().length() - 12, item.getTicketCode().length());
            helper.setText(R.id.item_ecode, code);
        } else {
            helper.setText(R.id.item_ecode, item.getTicketCode());
        }
        helper.setText(R.id.item_ydpeople, item.getShouldPeople());
//        helper.setText(R.id.item_sdpeople, item.getShouldPeople());
        //设置实到人数
        cvCounter.setMaxValue(Integer.parseInt(item.getShouldPeople()));
        final IChangeCoutCallback callback = new IChangeCoutCallback() {
            @Override
            public void change(int count) {
                item.setReallyPeople(String.valueOf(count));
//                if (!TextUtils.isEmpty(s.toString().trim())) {
//                    int count = Integer.parseInt(s.toString().trim());
//                    int people = Integer.parseInt(item.getShouldPeople());
//                    if (count != 0 && count <= people) {
//                        item.setReallyPeople(String.valueOf(count));
//                    } else if (count > people) {
//                        item.setReallyPeople(item.getShouldPeople());
//                        editText.setText(item.getShouldPeople());
////                        helper.setText(R.id.item_sdpeople, item.getReallyPeople());
//                        ToastUtils.show(context, "请输入正确人数");
//
//                    } else if (count == 0) {
//                        editText.setText("0");
//                        item.setReallyPeople("0");
//                    }
//                } else {
//                    editText.setText("");
//                    item.setReallyPeople("");
//                }
            }
        };
        cvCounter.setCallback(callback);
        cvCounter.setCountValue(Integer.parseInt(item.getShouldPeople()));
//        editText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (!TextUtils.isEmpty(s.toString().trim())) {
//                    int count = Integer.parseInt(s.toString().trim());
//                    int people = Integer.parseInt(item.getShouldPeople());
//                    if (count != 0 && count <= people) {
//                        item.setReallyPeople(String.valueOf(count));
//                    } else if (count > people) {
//                        item.setReallyPeople(item.getShouldPeople());
//                        editText.setText(item.getShouldPeople());
////                        helper.setText(R.id.item_sdpeople, item.getReallyPeople());
//                        ToastUtils.show(context, "请输入正确人数");
//
//                    }else if (count==0){
//                        editText.setText("0");
//                        item.setReallyPeople("0");
//                    }
//                } else {
//                    editText.setText("");
//                    item.setReallyPeople("");
//                }
//
//            }
//        });
        if (item.status == 0) {
            helper.setText(R.id.item_status, "未核销");
        }
    }

}
