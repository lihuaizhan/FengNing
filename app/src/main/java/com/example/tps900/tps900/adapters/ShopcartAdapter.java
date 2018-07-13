package com.example.tps900.tps900.adapters;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.tps900.tps900.Bean.GoodsInfo;
import com.example.tps900.tps900.Bean.StoreInfo;
import com.example.tps900.tps900.R;

import java.util.List;
import java.util.Map;




/**
 * 购物车数据适配器
 */
public class ShopcartAdapter extends BaseExpandableListAdapter {


    private List<StoreInfo> groups;
    private Map<String, List<GoodsInfo>> children;
    private Context context;
    private CheckInterface checkInterface;
    private ModifyCountInterface modifyCountInterface;
    public int flag = 0;
    private GroupEdtorListener mListener;

    public GroupEdtorListener getmListener() {
        return mListener;
    }

    public void setmListener(GroupEdtorListener mListener) {
        this.mListener = mListener;
    }

    int count = 0;

    /**
     * 构造函数
     *
     * @param groups   组元素列表
     * @param children 子元素列表
     * @param context
     */
    public ShopcartAdapter(List<StoreInfo> groups, Map<String, List<GoodsInfo>> children, Context context) {
        this.groups = groups;
        this.children = children;
        this.context = context;
    }

    public void setCheckInterface(CheckInterface checkInterface) {
        this.checkInterface = checkInterface;
    }

    public void setModifyCountInterface(ModifyCountInterface modifyCountInterface) {
        this.modifyCountInterface = modifyCountInterface;
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String groupId = groups.get(groupPosition).getId();
        return children.get(groupId).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        List<GoodsInfo> childs = children.get(groups.get(groupPosition).getId());
        return childs.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        final GroupViewHolder gholder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_shopcart_group, null);
            gholder = new GroupViewHolder(convertView);
            gholder.determineChekbox = (CheckBox) convertView.findViewById(R.id.determine_chekbox);
            gholder.tvSourceName = (TextView) convertView.findViewById(R.id.tv_source_name);
            gholder.tvStoreEdtor = (Button) convertView.findViewById(R.id.tv_store_edtor);
            gholder.adpSTime = (TextView) convertView.findViewById(R.id.adp_STime);
            gholder.adpSEime = (TextView) convertView.findViewById(R.id.adp_SEime);
            gholder.adpTicketName = (TextView) convertView.findViewById(R.id.adp_ticketName);
            gholder.adpTicketNum = (TextView) convertView.findViewById(R.id.adp_ticketNum);

            convertView.setTag(gholder);
        } else {
            gholder = (GroupViewHolder) convertView.getTag();
        }
        gholder.adpSTime.setText(context.getString(R.string.fm_Stime));
        gholder.adpSEime.setText(context.getString(R.string.fm_Etime));
        gholder.adpTicketName.setText(context.getString(R.string.fm_ticketName));
        gholder.adpTicketNum.setText(context.getString(R.string.fm_oderNUm));
        gholder.tvSourceName.setText(context.getString(R.string.fm_OrderCode));
        final StoreInfo group = (StoreInfo) getGroup(groupPosition);

        gholder.tvSourceName.setText(group.getName());
        gholder.determineChekbox.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v)

            {
                group.setChoosed(((CheckBox) v).isChecked());
                checkInterface.checkGroup(groupPosition, ((CheckBox) v).isChecked());// 暴露组选接口
            }
        });
        gholder.determineChekbox.setChecked(group.isChoosed());
        notifyDataSetChanged();
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, final boolean isLastChild, View convertView, final ViewGroup parent) {

        final ChildViewHolder cholder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_shopcart_product, null);

            cholder = new ChildViewHolder(convertView);
            cholder.checkBox = (CheckBox) convertView.findViewById(R.id.check_box);
            cholder.tvIntro = (TextView) convertView.findViewById(R.id.tv_intro);
            cholder.tvColorSize = (TextView) convertView.findViewById(R.id.tv_color_size);
            cholder.tvTicname = (TextView) convertView.findViewById(R.id.tv_ticname);
            cholder.tvBuyNum = (TextView) convertView.findViewById(R.id.tv_buy_num);
            convertView.setTag(cholder);
        } else {
            cholder = (ChildViewHolder) convertView.getTag();
        }
        final GoodsInfo goodsInfo = (GoodsInfo) getChild(groupPosition, childPosition);


        if (goodsInfo != null) {

            //获取时间
            cholder.tvIntro.setText(goodsInfo.getPlayStartDate().substring(0, 10));
            //种类
            cholder.tvTicname.setText(goodsInfo.getProductName() + "");
            //门票名称
            cholder.tvColorSize.setText(goodsInfo.getPlayEndDate().substring(0, 10));
            //门票数量
            cholder.tvBuyNum.setText("" + goodsInfo.getProductCount());
            cholder.checkBox.setChecked(goodsInfo.isChoosed());
            cholder.checkBox.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    goodsInfo.setChoosed(((CheckBox) v).isChecked());
                    cholder.checkBox.setChecked(((CheckBox) v).isChecked());
                    checkInterface.checkChild(groupPosition, childPosition, ((CheckBox) v).isChecked());// 暴露子选接口
                }
            });

        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;

    }


    /**
     * 复选框接口
     */
    public interface CheckInterface {
        /**
         * 组选框状态改变触发的事件
         *
         * @param groupPosition 组元素位置
         * @param isChecked     组元素选中与否
         */
        void checkGroup(int groupPosition, boolean isChecked);

        /**
         * 子选框状态改变时触发的事件
         *
         * @param groupPosition 组元素位置
         * @param childPosition 子元素位置
         * @param isChecked     子元素选中与否
         */
        void checkChild(int groupPosition, int childPosition, boolean isChecked);
    }

    /**
     * 改变数量的接口
     */
    public interface ModifyCountInterface {
        /**
         * 增加操作
         *
         * @param groupPosition 组元素位置
         * @param childPosition 子元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        void doIncrease(int groupPosition, int childPosition, View showCountView, boolean isChecked);

        /**
         * 删减操作
         *
         * @param groupPosition 组元素位置
         * @param childPosition 子元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked);


    }

    /**
     * 监听编辑状态
     */
    public interface GroupEdtorListener {
        void groupEdit(int groupPosition);
    }

    /**
     * 组元素绑定器
     */

    static class GroupViewHolder {

        CheckBox determineChekbox;

        TextView tvSourceName;

        Button tvStoreEdtor;

        TextView adpSTime;

        TextView adpSEime;

        TextView adpTicketName;

        TextView adpTicketNum;

        GroupViewHolder(View view) {

        }
    }

    /**
     * 子元素绑定器
     */
    static class ChildViewHolder {

        CheckBox checkBox;

        TextView tvIntro;

        TextView tvColorSize;

        TextView tvTicname;

        TextView tvBuyNum;

        ChildViewHolder(View view) {

        }
    }

}
