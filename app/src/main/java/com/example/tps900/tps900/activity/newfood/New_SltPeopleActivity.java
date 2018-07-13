package com.example.tps900.tps900.activity.newfood;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.tps900.tps900.Bean.SltPeopleBean;
import com.example.tps900.tps900.R;
import com.example.tps900.tps900.Utlis.Constant;
import com.example.tps900.tps900.adapters.SltPeopleAdapter;
import com.example.tps900.tps900.view.GridSpacingItemDecoration;
import com.godfery.Utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;


public class New_SltPeopleActivity extends Activity {


    RecyclerView sltpeopleReceyclerView;
    List<SltPeopleBean> data;

    Button selectPeopleBtn;
    private SltPeopleAdapter sltAdapter;
    private String tablemoney;
    private String foodmoney;
    private String tableNo;
    private String tableName;
    private SltPeopleBean item;
    private ArrayList isClicks;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slt_people);

        initView();
        initData();
    }

    /**
     * 初始化控件
     */
    public void initView() {
        selectPeopleBtn = (Button) findViewById(R.id.select_people_btn);
        Bundle bundle = getIntent().getExtras();
        tableNo = bundle.get("tableNo").toString();
        sltpeopleReceyclerView = (RecyclerView) findViewById(R.id.sltpeople_receyclerView);
    }

    /**
     * 操作数据
     */
    public void initData() {
        data = new ArrayList<>();
        for (int i = 1; i < 21; i++) {
            SltPeopleBean sltbean = new SltPeopleBean();
            sltbean.setPeopleNum(i);
            data.add(sltbean);
        }
        isClicks = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            isClicks.add(false);
        }
        sltAdapter = new SltPeopleAdapter(R.layout.slt_people_item, data, New_SltPeopleActivity.this, isClicks);
        //显示布局风格
        sltpeopleReceyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        //设置间距
        int spanCount = 4;
        int spacing = 35;
        boolean includeEdge = true;
        sltpeopleReceyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        sltpeopleReceyclerView.setAdapter(sltAdapter);
        sltAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                item = (SltPeopleBean) adapter.getItem(position);
                for (int i = 0; i < isClicks.size(); i++) {
                    isClicks.set(i, false);
                }
                isClicks.set(position, true);
                sltAdapter.notifyDataSetChanged();
            }
        });

        selectPeopleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null == item) {//0==item.getPeopleNum()
                    ToastUtils.show(New_SltPeopleActivity.this, "请选择人数");
                } else {
                    Constant.TablePeople = String.valueOf(item.getPeopleNum());
                    Intent intent = new Intent(New_SltPeopleActivity.this, New_Pay_foodActivity.class);
                    intent.putExtra("tableNo", tableNo);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }

}
