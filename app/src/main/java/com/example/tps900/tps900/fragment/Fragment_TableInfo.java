package com.example.tps900.tps900.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.tps900.tps900.Bean.DetailBean;
import com.example.tps900.tps900.Bean.TableInfoBean;
import com.example.tps900.tps900.R;
import com.example.tps900.tps900.Utlis.Constant;
import com.example.tps900.tps900.activity.Pay_foodActivity;
import com.example.tps900.tps900.activity.SltPeopleActivity;
import com.example.tps900.tps900.activity.SltTableActivity;
import com.example.tps900.tps900.adapters.TabletypeInfoAdapter;
import com.example.tps900.tps900.view.GridSpacingItemDecoration;
import com.godfery.Utils.ToastUtils;

import java.util.ArrayList;


public class Fragment_TableInfo extends Fragment {
    private ImageView hint_img;
    private RecyclerView recyclerView;
    private ArrayList result;
    private ArrayList<DetailBean> BEANS_Table;
    private SltTableActivity activity;
    private TextView toptype;
    private TabletypeInfoAdapter tableInfoAdapter;
    ArrayList<TableInfoBean.DtBean> tableInfoList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        BEANS_Table = new ArrayList<>();

        View view = inflater.inflate(R.layout.fragment_tableinfo, null);
        tableInfoList = new ArrayList<>();
        toptype = (TextView) view.findViewById(R.id.toptype);
        recyclerView = (RecyclerView) view.findViewById(R.id.fm_tableinfo_receyclerView);
        //显示布局风格
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        //设置间距
        int spanCount = 2;
        int spacing = 35;
        boolean includeEdge = true;
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        result = (ArrayList) getArguments().getSerializable("result");
        String topType = getArguments().getString("topType");
        toptype.setText(topType);
        if (null != result) {
            tableInfoList = result;
            if (!result.isEmpty()) {
                tableInfoAdapter = new TabletypeInfoAdapter(R.layout.tableinfo_item, tableInfoList, getContext());
                recyclerView.setAdapter(tableInfoAdapter);
            }

        }

        tableInfoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                TableInfoBean.DtBean item = (TableInfoBean.DtBean) adapter.getItem(position);
                if (item.ISEnable == 1) {
                    ToastUtils.show(getActivity(), "桌台占用中");
                } else {
                    if (TextUtils.isEmpty(Constant.TablePeople)) {
                        Intent intent = new Intent(activity, SltPeopleActivity.class);
                        intent.putExtra("tableMoney", item.getDPRICE());
                        intent.putExtra("tableNo", item.getDDESKNUMBER());
                        intent.putExtra("tableName", item.getDESKNAME());
                        startActivity(intent);
//                        activity.finish();
                    } else {
                        Intent intent = new Intent(activity, Pay_foodActivity.class);
                        intent.putExtra("tableMoney", item.getDPRICE());
                        intent.putExtra("tableNo", item.getDDESKNUMBER());
                        intent.putExtra("tableName", item.getDESKNAME());
                        startActivity(intent);
//                        activity.finish();
                    }
                }
            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        activity = (SltTableActivity) context;


    }
}
