package com.example.tps900.tps900.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.tps900.tps900.Bean.ApplicationListBean;
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
 * @author zxh
 */

public class ApplicatioinListAdapter extends BaseQuickAdapter<ApplicationListBean,BaseViewHolder> {
    Context context;
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId The layout resource id of each item.
     * @param data        A new list is created out of this one to avoid mutable list
     */
    public ApplicatioinListAdapter(Context context, @LayoutRes int layoutResId, @Nullable List<ApplicationListBean> data) {
        super(layoutResId, data);
        this.context=context;
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(BaseViewHolder helper, ApplicationListBean item) {
        helper.setText(R.id.textView,item.getTitleName());
        helper.setBackgroundRes(R.id.image,item.getResId());
    }
}
