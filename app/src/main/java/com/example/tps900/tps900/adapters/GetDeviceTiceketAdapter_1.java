package com.example.tps900.tps900.adapters;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.tps900.tps900.Bean.GetDeviceTicketBean;
import com.example.tps900.tps900.R;

import java.util.List;


/**
 * 项目名称：TPS900
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2017/4/23 16:04
 * 修改人：zxh
 * 修改时间：2017/4/23 16:04
 * 修改备注：
 */

public class GetDeviceTiceketAdapter_1 extends BaseItemDraggableAdapter<GetDeviceTicketBean.TicketsBean, BaseViewHolder> {

    public GetDeviceTiceketAdapter_1(int layoutResId, List<GetDeviceTicketBean.TicketsBean> data) {
        super(layoutResId, data);
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(BaseViewHolder helper, GetDeviceTicketBean.TicketsBean item) {
        helper.setText(R.id.item_cost_name, item.getSticketnamech());
        helper.setText(R.id.item_cost_price, String.valueOf(item.getNgeneralprice()) + " 元");
        helper.setText(R.id.item_cost_personNum, item.getNpeoplecount() + " 人");
        helper.addOnClickListener(R.id.item_cost_lv);
    }
}
