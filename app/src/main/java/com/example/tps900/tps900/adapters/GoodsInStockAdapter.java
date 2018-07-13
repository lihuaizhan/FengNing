package com.example.tps900.tps900.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tps900.tps900.Bean.SETTABLEINFOBean;
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

public class GoodsInStockAdapter extends BaseAdapter {
    private final LayoutInflater mInflater;
    List<SETTABLEINFOBean.GoodlsBean> list;
//    Context context;
//
//    public GoodsInStockAdapter(@LayoutRes int layoutResId, @Nullable List<SETTABLEINFOBean.GoodlsBean> data, Context context) {
//        super(layoutResId, data);
//        this.context = context;
//    }
//
//    @Override
//    protected void convert(BaseViewHolder helper, SETTABLEINFOBean.GoodlsBean item) {
//        if (!item.IsSale) {
//            helper.setText(R.id.goods_Name, item.GoodsName);
//            helper.setText(R.id.goods_instockNum, String.valueOf(item.StockNums));
//            helper.setText(R.id.goodsNum, String.valueOf(item.SaleNums));
//        }
//    }
//    private ArrayList<PayBean.DtBean> list;
//    private LayoutInflater mInflater;
//    private int selectedIndex=-1;

    public GoodsInStockAdapter(List<SETTABLEINFOBean.GoodlsBean> data, Context context) {
        this.list = data;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public SETTABLEINFOBean.GoodlsBean getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_instock_item, null);
            holder = new ViewHolder();
            holder.goods_Name = (TextView) convertView.findViewById(R.id.goods_Name);
            holder.goods_instockNum = (TextView) convertView.findViewById(R.id.goods_instockNum);
            holder.goodsNum = (TextView) convertView.findViewById(R.id.goodsNum);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        SETTABLEINFOBean.GoodlsBean item = list.get(position);
        if (!item.IsSale) {
            holder.goods_Name.setText( item.GoodsName);
            holder.goods_instockNum.setText(String.valueOf(item.StockNums));
            holder.goodsNum.setText( String.valueOf(item.SaleNums));
        }
        return convertView;
    }


    static class ViewHolder {

        public TextView goods_Name;
        public TextView goods_instockNum;
        public TextView goodsNum;

    }


}
