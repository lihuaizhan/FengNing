package com.example.tps900.tps900.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.tps900.tps900.Bean.TableInfoBean;
import com.example.tps900.tps900.Bean.TableTypeBean;
import com.example.tps900.tps900.R;
import com.example.tps900.tps900.Utlis.Dailog;
import com.example.tps900.tps900.Utlis.LogUtil;
import com.example.tps900.tps900.Utlis.MessageEvent_Food;
import com.example.tps900.tps900.Utlis.ThreadPoolUtils;
import com.example.tps900.tps900.fragment.Fragment_TableInfo;
import com.example.tps900.tps900.view.NoScrollViewPager;
import com.godfery.pay.HttpUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static com.example.tps900.tps900.Webservice.GsWebServiceUtils.GMB_GETTABLETYPE;
import static com.example.tps900.tps900.Webservice.GsWebServiceUtils.getTableInfo;


public class SltTableActivity extends BaseActivity {


    private ArrayList<TableTypeBean.LsBean> tableTypeList;
    private List<TableInfoBean.DtBean> tableInfoList;
    private String foodmoney;
    private String[] toolsList;
    private TextView[] toolsTextViews;
    private View[] views;
    private ArrayList arrayList;
    private LayoutInflater inflater;
    private NoScrollViewPager shop_pager;
    private ScrollView scrollView;
    private int scrllViewWidth = 0, scrollViewMiddle = 0;
    ArrayList commList;
    private ShopAdapter shopAdapter;
    private LinearLayout food_lv_back;
    private TextView slt_table_type;
    private Fragment_TableInfo fragment_tableInfo;

    /**
     * 设置布局
     *
     * @return
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_slt_table;
    }

    /**
     * 初始化控件
     */
    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        inflater = LayoutInflater.from(this);
        scrollView = (ScrollView) findViewById(R.id.tools_scrlllview);
        shop_pager = (NoScrollViewPager) findViewById(R.id.goods_pager);
        food_lv_back = (LinearLayout) findViewById(R.id.food_table_lv_back);//
        slt_table_type = (TextView) findViewById(R.id.slt_table_type);
        slt_table_type.setVisibility(View.GONE);
        shop_pager.setOnPageChangeListener(onPageChangeListener);
        tableTypeList = new ArrayList<>();
        tableInfoList = new ArrayList<>();
        arrayList = new ArrayList();
    }

    /**
     * 操作数据
     */
    @Override
    public void initData() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                HttpUtils.showProgressDialog(progressDialog);
            }
        });
        getTableType();
        food_lv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void showToolsView() {

        toolsList = new String[arrayList.size()];

        for (int i = 0; i < arrayList.size(); i++) {
            TableTypeBean.LsBean bean = (TableTypeBean.LsBean) arrayList.get(i);
            toolsList[i] = bean.getTypeName();
        }

        LinearLayout toolsLayout = (LinearLayout) findViewById(R.id.tools);
        toolsTextViews = new TextView[toolsList.length];

        views = new View[toolsList.length];

        for (int i = 0; i < toolsList.length; i++) {
            View view = inflater.inflate(R.layout.item_b_top_nav_layout, null);
            view.setId(i);
            view.setOnClickListener(toolsItemListener);
            TextView textView = (TextView) view.findViewById(R.id.text);
            textView.setText(toolsList[i]);
            toolsLayout.addView(view);
            toolsTextViews[i] = textView;
            views[i] = view;
        }
        changeTextColor(0);
        slt_table_type.setVisibility(View.VISIBLE);
        shopAdapter = new ShopAdapter(getSupportFragmentManager());
        shop_pager.setAdapter(shopAdapter);
    }

    /**
     * 改变textView的颜色
     *
     * @param id
     */
    private void changeTextColor(int id) {
        for (int i = 0; i < toolsTextViews.length; i++) {
            if (i != id) {
                toolsTextViews[i].setBackgroundResource(R.drawable.item_type_dn);
                toolsTextViews[i].setTextColor(0xff7f7f7f);
            }
        }
        toolsTextViews[id].setBackgroundResource(R.drawable.item_type);
        toolsTextViews[id].setTextColor(0xff323232);
    }

    private int currentItem;
    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            if (shop_pager.getCurrentItem() != position) {
                shop_pager.setCurrentItem(position, false);
            }

            if (currentItem != position) {

                changeTextColor(position);
                changeTextLocation(position);

            }
            currentItem = position;
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    };

    private View.OnClickListener toolsItemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            shop_pager.setCurrentItem(v.getId(), false);
        }
    };

    /**
     * 改变栏目位置
     *
     * @param clickPosition
     */
    private void changeTextLocation(int clickPosition) {

        int x = (views[clickPosition].getTop() - getScrollViewMiddle() + (getViewheight(views[clickPosition]) / 2));
        scrollView.smoothScrollTo(0, x);
    }

    /**
     * 返回scrollview的中间位置
     *
     * @return
     */
    private int getScrollViewMiddle() {
        if (scrollViewMiddle == 0) {
            scrollViewMiddle = getScrollViewheight() / 2;
        }
        return scrollViewMiddle;
    }

    /**
     * 返回ScrollView的宽度
     *
     * @return
     */
    private int getScrollViewheight() {
        if (scrllViewWidth == 0) {
            scrllViewWidth = scrollView.getBottom() - scrollView.getTop();
        }
        return scrllViewWidth;
    }

    /**
     * 返回view的宽度
     *
     * @param view
     * @return
     */
    private int getViewheight(View view) {
        return view.getBottom() - view.getTop();
    }

    /**
     * 获取桌台类型
     */
    public void getTableType() {
        ThreadPoolUtils.runTaskInThread(new Runnable() {
            @Override
            public void run() {
                String typeInfo = GMB_GETTABLETYPE();
                if (typeInfo.equals("err")) {
                    HttpUtils.exitProgressDialog(progressDialog);
                    Dailog.ErrDialog2(SltTableActivity.this, "请求", "");
                } else {
                    String Jsonstr = "{\"Flags\":true,\"ls\":[{\"TypeID\":\"1\",\"TypeName\":\"散客桌\"},{\"TypeID\":\"2\",\"TypeName\":\"包间桌\"}]}";

                    TableTypeBean tableTypeBean = JSON.parseObject(Jsonstr, TableTypeBean.class);
                    if (tableTypeBean.Flags && tableTypeBean.ls != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tableTypeList = tableTypeBean.ls;
                                arrayList = tableTypeList;
                                ThreadPoolUtils.runTaskInThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        GetTableInfo(Integer.parseInt(tableTypeBean.getLs().get(0).TypeID));
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                showToolsView();
                                            }
                                        });
                                    }
                                });
                            }
                        });


                    } else {
                        ThreadPoolUtils.runTaskInUIThread(new Runnable() {
                            @Override
                            public void run() {
                                HttpUtils.exitProgressDialog(progressDialog);
                                Dailog.ErrDialog2(SltTableActivity.this, "获取桌台分类失败", "");
                            }
                        });
                    }
                }
            }
        });
    }

    public void GetTableInfo(int Type) {
        String tableInfo = getTableInfo(0, "0");
        if (tableInfo.equals("err")) {
            ThreadPoolUtils.runTaskInUIThread(new Runnable() {
                @Override
                public void run() {
                    HttpUtils.exitProgressDialog(progressDialog);
                    Dailog.ErrDialog2(SltTableActivity.this, "请求失败请检查网络或配置信息!!!", "");
                }
            });
        } else {
            TableInfoBean tableInfoBean = JSON.parseObject(tableInfo, TableInfoBean.class);
            if (tableInfoBean.isSuccess && (tableInfoBean.dt != null && tableInfoBean.dt.size() > 0)) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList dalist = new ArrayList();
                        for (int i = 0; i < tableInfoBean.dt.size(); i++) {
                            TableInfoBean.DtBean dataBean = tableInfoBean.dt.get(i);
                            if (dataBean.DTABLETYPE == Type) {
                                dalist.add(dataBean);
                            }
                        }
                        tableInfoList = dalist;
                        commList = dalist;
                    }
                });
            } else {
                ThreadPoolUtils.runTaskInUIThread(new Runnable() {
                    @Override
                    public void run() {
                        HttpUtils.exitProgressDialog(progressDialog);
                        Dailog.ErrDialog2(SltTableActivity.this, "获取桌台信息失败" + "\n" + tableInfoBean.error, "");
                    }
                });
            }
        }

    }


    /**
     * ViewPager 加载选项卡
     *
     * @author Administrator
     */
    private class ShopAdapter extends FragmentPagerAdapter {
        public ShopAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            fragment_tableInfo = new Fragment_TableInfo();
            Fragment fragment = fragment_tableInfo;
            Bundle bundle = new Bundle();
            TableTypeBean.LsBean bean = (TableTypeBean.LsBean) arrayList.get(arg0);
            int nclassid = Integer.parseInt(bean.getTypeID());
            String sclassname = bean.getTypeName();
            GetTableInfo(nclassid);
            LogUtil.i("TAG", "大小--->" + commList.size());
            bundle.putSerializable("result", commList);
            bundle.putString("topType", sclassname);
            fragment.setArguments(bundle);
            HttpUtils.exitProgressDialog(progressDialog);
            return fragment;
        }

        @Override
        public int getCount() {
            return toolsList.length;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent_Food messageEvent) {
        String msg = messageEvent.getMsg();
        if (msg.equals("选桌消失")) {
            finish();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        changeTextColor(0);
        changeTextLocation(0);
        shop_pager.setCurrentItem(0, false);
        shopAdapter.notifyDataSetChanged();
    }
}
