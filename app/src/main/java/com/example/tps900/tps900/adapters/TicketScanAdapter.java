package com.example.tps900.tps900.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.tps900.tps900.Bean.TicketScanBean;
import com.example.tps900.tps900.R;
import com.example.tps900.tps900.Utlis.Dailog;

import java.util.List;

/**
 * 项目名称：PDA_XiLing
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2017/11/19 16:27
 * 修改人：zxh
 * 修改时间：2017/11/19 16:27
 * 修改备注：
 *
 * @author zxh
 */

public class TicketScanAdapter extends BaseQuickAdapter<TicketScanBean, BaseViewHolder> {

    private final Activity activity;
    Context context;
    private TicketItemAdapter ticketItemAdapter;
    private TextView textView;

    public TicketScanAdapter(@LayoutRes int layoutResId, @Nullable List<TicketScanBean> data, Context context, Activity activity) {
        super(layoutResId, data);
        this.context = context;
        this.activity = activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, TicketScanBean item) {
        textView = (TextView) helper.getView(R.id.item_ticketName);
        if (TextUtils.isEmpty(textView.getText().toString()) || !textView.getText().toString().equals(item.getTicketName())) {
            helper.setText(R.id.item_ticketName, item.getTicketName());
        }

        ticketItemAdapter = new TicketItemAdapter(R.layout.item_scan_item, item.getTicketdata(), context,activity);
        RecyclerView recyclerView = helper.getView(R.id.item_listview);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(ticketItemAdapter);
        ticketItemAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                TicketScanBean.TicketBean ticketBean = (TicketScanBean.TicketBean) adapter.getItem(position);
                Dailog.ErrDialogTicket(context, "确定要删除此条信息吗?", ticketBean);
            }
        });
    }

}
