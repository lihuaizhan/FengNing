package com.example.tps900.tps900.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.tps900.tps900.Bean.TableInfoBean;
import com.example.tps900.tps900.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

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

public class TabletypeInfoAdapter extends BaseQuickAdapter<TableInfoBean.DtBean, BaseViewHolder> {

    Context context;
    private TicketItemAdapter ticketItemAdapter;
    private TextView textView;

    public TabletypeInfoAdapter(@LayoutRes int layoutResId, @Nullable ArrayList<TableInfoBean.DtBean> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, TableInfoBean.DtBean item) {
        helper.setText(R.id.tv_goodsName, item.getDESKNAME());
        helper.setText(R.id.tv_goodsCount, "1");
        helper.setText(R.id.tv_goodsPrice, "¥ " + new DecimalFormat("#0.00").format(item.getDPRICE()));
        helper.setBackgroundRes(R.id.table_info_lv, setBgDrawable(item));
    }

    private int setBgDrawable(TableInfoBean.DtBean item) {

        int nstatus = item.getISEnable();
        if (0 == nstatus) {
            return R.drawable.table_up;
        } else {
            return R.drawable.table_dn;
        }
//        else {
//            return R.drawable.room_dirty;
//        }
    }
}
