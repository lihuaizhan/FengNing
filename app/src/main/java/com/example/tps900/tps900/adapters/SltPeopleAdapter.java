package com.example.tps900.tps900.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.tps900.tps900.Bean.SltPeopleBean;
import com.example.tps900.tps900.R;

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

public class SltPeopleAdapter extends BaseQuickAdapter<SltPeopleBean, BaseViewHolder> {

    Context context;
    private TicketItemAdapter ticketItemAdapter;
    private List<Boolean> isClicks;//控件是否被点击,默认为false，如果被点击，改变值，控件根据值改变自身颜色

    public SltPeopleAdapter(@LayoutRes int layoutResId, @Nullable List<SltPeopleBean> data, Context context, List<Boolean> isClicks) {
        super(layoutResId, data);
        this.context = context;
        this.isClicks = isClicks;
    }

    @Override
    protected void convert(BaseViewHolder helper, SltPeopleBean item) {
        helper.setText(R.id.item_slt_people, String.valueOf(item.getPeopleNum()));
        if (isClicks.get(helper.getPosition())) {
            helper.setBackgroundRes(R.id.item_slt_people, R.drawable.people_dn);
        } else {
            helper.setBackgroundRes(R.id.item_slt_people, R.drawable.people_up);

        }

    }

}
