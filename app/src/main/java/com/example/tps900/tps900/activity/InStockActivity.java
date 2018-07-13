package com.example.tps900.tps900.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;

import com.example.tps900.tps900.Bean.SETTABLEINFOBean;
import com.example.tps900.tps900.R;
import com.example.tps900.tps900.Utlis.MessageConstanse;
import com.example.tps900.tps900.Utlis.MessageEvent_Food;
import com.example.tps900.tps900.adapters.GoodsInStockAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


public class InStockActivity extends Activity implements View.OnClickListener {


    ListView instockReceyclerView;
    Button cancelBtn;
    Button determineBtn;
    private GoodsInStockAdapter goodsInStockAdapter;
    List<SETTABLEINFOBean.GoodlsBean> goodlsBeanList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //点击区域外不消失
        setFinishOnTouchOutside(false);
        setContentView(R.layout.activity_instock);
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
        WindowManager.LayoutParams p = getWindow().getAttributes();  //获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.6);   //高度设置为屏幕的1.0
        p.width = (int) (d.getWidth() * 0.9);    //宽度设置为屏幕的0.8
        getWindow().setAttributes(p);
        initView();
        initData();
    }

    /**
     * 初始化控件
     */
    public void initView() {
        goodlsBeanList = new ArrayList<>();
        instockReceyclerView = (ListView) findViewById(R.id.instock_receyclerView);
        cancelBtn = (Button) findViewById(R.id.cancel_btn);
        determineBtn = (Button) findViewById(R.id.determine_btn);
        cancelBtn.setOnClickListener(this);
        determineBtn.setOnClickListener(this);
    }

    /**
     * 操作数据
     */
    public void initData() {
        Bundle bundle = getIntent().getExtras();
        goodlsBeanList = (List<SETTABLEINFOBean.GoodlsBean>) bundle.getSerializable("goodsInStockList");
        goodsInStockAdapter = new GoodsInStockAdapter(  goodlsBeanList, InStockActivity.this);
        //显示布局风格
//        instockReceyclerView.setLayoutManager(new LinearLayoutManager(InStockActivity.this));
        instockReceyclerView.setAdapter(goodsInStockAdapter);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.cancel_btn) {
            EventBus.getDefault().post(new MessageEvent_Food(MessageConstanse.DropOutPayActivity));
            finish();
        } else if (id == R.id.determine_btn) {
            EventBus.getDefault().post(new MessageEvent_Food(MessageConstanse.SubmitGoodsInfo));
            finish();


        }

    }

}
