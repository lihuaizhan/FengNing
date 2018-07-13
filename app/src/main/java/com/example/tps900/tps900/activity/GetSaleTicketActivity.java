package com.example.tps900.tps900.activity;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.tps900.tps900.R;
import com.example.tps900.tps900.Utlis.Constant;
import com.example.tps900.tps900.adapters.GetDeviceTiceketAdapter;
import com.example.tps900.tps900.adapters.GetDeviceTiceketAdapter_1;

/**
 * 项目名称：TPS900
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2017/4/23 16:54
 * 修改人：zxh
 * 修改时间：2017/4/23 16:54
 * 修改备注：
 */

public class GetSaleTicketActivity extends BaseActivity {

    ListView getslaeticketLv;

    ImageButton getsaBack;

    RelativeLayout gsetsaleticketLvBack;
    private GetDeviceTiceketAdapter getDeviceTiceketAdapter;
    private GetDeviceTiceketAdapter_1 getDeviceTiceketAdapter_1;

    /**
     * 设置布局
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_getsaleticket;
    }


    @Override
    public void initView() {
        getslaeticketLv = (ListView) findViewById(R.id.getslaeticket_lv);
//        getslaeticketLv.setLayoutManager(new LinearLayoutManager(GetSaleTicketActivity.this));
        getsaBack = (ImageButton) findViewById(R.id.getsa_back);
        gsetsaleticketLvBack = (RelativeLayout) findViewById(R.id.gsetsaleticket_lv_back);
        if (Constant.getDeviceList != null) {
            getDeviceTiceketAdapter = new GetDeviceTiceketAdapter(Constant.getDeviceList, GetSaleTicketActivity.this);
            getDeviceTiceketAdapter.setData(Constant.getDeviceList);
            getslaeticketLv.setAdapter(getDeviceTiceketAdapter);
            getDeviceTiceketAdapter.notifyDataSetChanged();
//            getDeviceTiceketAdapter_1 = new GetDeviceTiceketAdapter_1(R.layout.item_cost, Constant.getDeviceList);
//            getDeviceTiceketAdapter_1.setNewData(Constant.getDeviceList);
//
//            ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(getDeviceTiceketAdapter_1);
//            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
//            itemTouchHelper.attachToRecyclerView(getslaeticketLv);
//            /**
//             * 拖拽的监听事件
//             */
//            OnItemDragListener onItemDragListener = new OnItemDragListener() {
//                @Override
//                public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
//                }
//
//                @Override
//                public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
//                }
//
//                @Override
//                public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
//                }
//            };
//
//            // 开启拖拽
//            getDeviceTiceketAdapter_1.enableDragItem(itemTouchHelper, R.id.item_cost_lv, true);
//            getDeviceTiceketAdapter_1.setOnItemDragListener(onItemDragListener);
//            getslaeticketLv.setAdapter(getDeviceTiceketAdapter_1);
        }

    }

    /**
     * 操作数据
     */
    @Override
    public void initData() {
//        getDeviceTiceketAdapter_1.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                switch (view.getId()) {
//                    case R.id.item_cost_lv:
//                        Intent intent = new Intent(GetSaleTicketActivity.this, ProjectFeeActivity.class);
//                        intent.putExtra("ticketname", Constant.getDeviceList.get(position).getSticketnamech());
//                        intent.putExtra("ticketprice", Constant.getDeviceList.get(position).getNgeneralprice() + "");
//                        intent.putExtra("nticketid", String.valueOf(Constant.getDeviceList.get(position).getNticketid()));
//                        GetSaleTicketActivity.this.startActivity(intent);
//                        break;
//                    default:
//                        break;
//                }
//
//            }
//        });
        getslaeticketLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(GetSaleTicketActivity.this, ProjectFeeActivity.class);
                intent.putExtra("ticketname", Constant.getDeviceList.get(position).getSticketnamech());
                intent.putExtra("ticketprice", Constant.getDeviceList.get(position).getNgeneralprice() + "");
                intent.putExtra("nticketid", String.valueOf(Constant.getDeviceList.get(position).getNticketid()));
                GetSaleTicketActivity.this.startActivity(intent);
            }
        });
    }


    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.gsetsaleticket_lv_back) {
            openActivityAndCloseThis(MainActivity.class);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        openActivityAndCloseThis(MainActivity.class);
        return super.onKeyDown(keyCode, event);
    }
}
