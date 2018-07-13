package com.example.tps900.tps900.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.tps900.tps900.Bean.CategoryBean;
import com.example.tps900.tps900.Bean.DetailBean;
import com.example.tps900.tps900.InterFace.MessageEvent;
import com.example.tps900.tps900.R;
import com.example.tps900.tps900.Utlis.Constant;
import com.example.tps900.tps900.Utlis.LogUtil;
import com.example.tps900.tps900.adapters.MyAdapter;
import com.example.tps900.tps900.fragment.Fragment_pro_type_food;
import com.example.tps900.tps900.sql.PdaDaoUtils;
import com.example.tps900.tps900.view.NoScrollViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class FoodActivity extends BaseActivity {

    public static final String TAG = "FoodActivity";

    private String[] toolsList;
    private TextView[] toolsTextViews;
    private View[] views;
    private LayoutInflater inflater;
    private ScrollView scrollView;
    private int scrllViewWidth = 0, scrollViewMiddle = 0;
    private NoScrollViewPager shop_pager;
    private int currentItem = 0;
    private ShopAdapter shopAdapter;
    private ImageView shopping_cart;
    private FrameLayout cardLayout;
    private ArrayList arrayList;
    private LinearLayout img_btn;
    private TextView shoppingNum;
    private TextView defaultText;
    private ListView shopproductListView;
    private TextView settlement;
    private TextView shoppingPrise;
    private LinearLayout clear_shopCart;
    private MyAdapter myAdapter;
    private ArrayList searchList;
    private ArrayList commList;

    /**
     * 设置布局
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_food;
    }

    /**
     * 初始化控件
     */
    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        shopping_cart = (ImageView) findViewById(R.id.shopping_cart);
        cardLayout = (FrameLayout) findViewById(R.id.cardLayout);
        img_btn = (LinearLayout) findViewById(R.id.img_back);


        shoppingNum = (TextView) findViewById(R.id.shoppingNum);
        defaultText = (TextView) findViewById(R.id.defaultText);
        shopproductListView = (ListView) findViewById(R.id.shopproductListView);
        settlement = (TextView) findViewById(R.id.settlement);
        shoppingPrise = (TextView) findViewById(R.id.shoppingPrise);
        clear_shopCart = (LinearLayout) findViewById(R.id.clear_shopCart);
        scrollView = (ScrollView) findViewById(R.id.tools_scrlllview);
        shopAdapter = new ShopAdapter(getSupportFragmentManager());
        inflater = LayoutInflater.from(this);
        showToolsView();
        initPager();
    }

    /**
     * 操作数据
     */
    @Override
    public void initData() {

        shopping_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cardLayout.getVisibility() == View.GONE) {
                    cardLayout.setVisibility(View.VISIBLE);

                    if (Constant.BEANS.size() > 0) {
                        defaultText.setVisibility(View.GONE);
                        settlement.setVisibility(View.VISIBLE);
                        MyAdapter myAdapter = new MyAdapter(FoodActivity.this, Constant.BEANS, settlement, shoppingPrise, shoppingNum, defaultText);
                        shopproductListView.setAdapter(myAdapter);
                    } else {
                        defaultText.setVisibility(View.VISIBLE);
                        settlement.setVisibility(View.GONE);
                    }

                } else {
                    cardLayout.setVisibility(View.GONE);
                }
            }
        });

        img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.BEANS.clear();
                Constant.FoodMoney = "";
                Constant.TablePeople = "";
                openActivityAndCloseThis(MainActivity.class);
            }
        });


        settlement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String money = shoppingPrise.getText().toString().trim();
                Constant.FoodMoney = money;
                openActivity(SltTableActivity.class);
            }
        });

        clear_shopCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Constant.BEANS.clear();
                Constant.FoodMoney = "";
                Constant.TablePeople = "";
                shopAdapter.notifyDataSetChanged();
                shoppingPrise.setText("0.00");
                shoppingPrise.setTextColor(getResources().getColor(R.color.default_shopping));
                shoppingNum.setText(null);
                shoppingNum.setVisibility(View.GONE);
                settlement.setVisibility(View.GONE);
                shopproductListView.setAdapter(myAdapter);
                defaultText.setVisibility(View.VISIBLE);

            }
        });


    }


    public void updateViews(ArrayList<DetailBean.DtBean> beans) {
        if (null == beans) {
            shoppingNum.setVisibility(View.GONE);
            defaultText.setVisibility(View.VISIBLE);
            settlement.setVisibility(View.GONE);
        } else {
            if (beans.size() == 0) {
                shoppingNum.setVisibility(View.GONE);
                defaultText.setVisibility(View.VISIBLE);
                settlement.setVisibility(View.GONE);
            } else {
                shoppingNum.setVisibility(View.VISIBLE);
                defaultText.setVisibility(View.GONE);
                settlement.setVisibility(View.VISIBLE);

                myAdapter = new MyAdapter(FoodActivity.this, beans, settlement, shoppingPrise, shoppingNum, defaultText);
                shopproductListView.setAdapter(myAdapter);
                double totalMoney = 0.00;
                int totalCount = 0;
                for (int i = 0; i < beans.size(); i++) {
                    DetailBean.DtBean detailBean = beans.get(i);
                    LogUtil.i(TAG, detailBean.getPRICE() + "");
                    LogUtil.i(TAG, "Count----->数量" + detailBean.getGoodsCount() + "");
                    totalMoney += detailBean.getGoodsCount() * detailBean.getPRICE();
                    totalCount = totalCount + detailBean.getGoodsCount();
                }
                shoppingNum.setText(String.valueOf(totalCount));
                LogUtil.i(TAG, "beans的大小------>" + beans.size());
                LogUtil.i(TAG, "totalMoney----->" + totalMoney);
                shoppingPrise.setTextColor(getResources().getColor(R.color.white_text_color));
                shoppingPrise.setText(new DecimalFormat("0.00").format(totalMoney));
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1002:
                if (-1 == resultCode) {
                    //返回结果成功
                    boolean flag = data.getBooleanExtra("flag", false);
                    if (flag) {
                        //如果按的是返回键，则重置页面
                        Constant.BEANS.clear();
                        Constant.FoodMoney = "";
                        Constant.TablePeople = "";
                        shopAdapter.notifyDataSetChanged();
                        shoppingPrise.setText("0.00");
                        shoppingPrise.setTextColor(getResources().getColor(R.color.default_shopping));
                        shoppingNum.setVisibility(View.GONE);
                        shoppingNum.setText(null);
                        settlement.setVisibility(View.GONE);
                        shopproductListView.setAdapter(myAdapter);
                        defaultText.setVisibility(View.VISIBLE);
                    }
                }
                break;

            case 1003:
                if (-1 == resultCode) {
                    String flag = data.getStringExtra("flag");
                    if ("bothNo".equals(flag)) {

                        //设置小红圈商品数量
                        shoppingNum.setVisibility(View.VISIBLE);
                        String goodsCount = shoppingNum.getText().toString().trim();
                        if (goodsCount == "" || goodsCount.isEmpty()) {
                            shoppingNum.setText(1 + "");
                        } else {
                            int i = Integer.parseInt(goodsCount);
                            shoppingNum.setText(String.valueOf(i + 1));
                        }

                        //更改商品总价格
                        Bundle bundle = data.getBundleExtra("bundle");
                        DetailBean.DtBean bean = (DetailBean.DtBean) bundle.getSerializable("bean");
                        String money = shoppingPrise.getText().toString().trim();
                        double shouldMoney = Double.parseDouble(money);
                        shouldMoney = shouldMoney + bean.getPRICE();
                        shoppingPrise.setTextColor(getResources().getColor(R.color.white_text_color));
                        shoppingPrise.setText(new DecimalFormat("0.00").format(shouldMoney));

                        //更新显示列表
                        MyAdapter myAdapter = new MyAdapter(FoodActivity.this, Constant.BEANS, settlement, shoppingPrise, shoppingNum, defaultText);
                        shopproductListView.setAdapter(myAdapter);
                        //显示去结算按钮
                        settlement.setVisibility(View.VISIBLE);

                    } else if ("confirmOrCancel".equals(flag)) {
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {

            Constant.BEANS.clear();
            Constant.FoodMoney = "";
            Constant.TablePeople = "";
            openActivityAndCloseThis(MainActivity.class);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 动态生成显示items中的textview
     */
    private void showToolsView() {
        //查询分类列表
        PdaDaoUtils utils = new PdaDaoUtils();
        arrayList = utils.query_CommClass("2");
        System.out.println("大小---->" + arrayList.size());

        toolsList = new String[arrayList.size()];

        for (int i = 0; i < arrayList.size(); i++) {
            CategoryBean.DtBean bean = (CategoryBean.DtBean) arrayList.get(i);
            toolsList[i] = bean.getSCLASSNAME();
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
    }

    private View.OnClickListener toolsItemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            shop_pager.setCurrentItem(v.getId(), false);
        }
    };

    /**
     * initPager<br/>
     * 初始化ViewPager控件相关内容
     */
    private void initPager() {
        shop_pager = (NoScrollViewPager) findViewById(R.id.goods_pager);
        shop_pager.setAdapter(shopAdapter);
        shop_pager.setOnPageChangeListener(onPageChangeListener);
    }

    /**
     * OnPageChangeListener<br/>
     * 监听ViewPager选项卡变化事的事件
     */

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            LogUtil.i(TAG, "position---->:" + position);
            if (shop_pager.getCurrentItem() != position) {
                shop_pager.setCurrentItem(position, false);
            }

            if (currentItem != position) {

                changeTextColor(position);
                changeTextLocation(position);

            }
            currentItem = position;

            LogUtil.i(TAG, "currentItem--->" + currentItem);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    };


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
            Fragment fragment = new Fragment_pro_type_food();
            Bundle bundle = new Bundle();
            CategoryBean.DtBean bean = (CategoryBean.DtBean) arrayList.get(arg0);
            int nclassid = bean.getNCLASSID();
            String sclassname = bean.getSCLASSNAME();
            String sql = "select VCOMMYNAME,PRICE,NCOMMID,NPCOMMID,PNUMBER,VCOMMCODING,SIMPLIFY,ATTENTION from GzoneComm where classID = ' " + nclassid + " ' And TYPE ='2'";
            PdaDaoUtils utils = new PdaDaoUtils();
            commList = utils.query_GzoneComm(sql);
            LogUtil.i("TAG", "大小--->" + commList.size());
            bundle.putSerializable("result", commList);
            bundle.putString("topType", sclassname);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            return toolsList.length;
        }


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


    public void updateViews(DetailBean.DtBean bean) {


        shoppingNum.setVisibility(View.VISIBLE);
        String goodsCount = shoppingNum.getText().toString().trim();
        if (goodsCount == "" || goodsCount.isEmpty()) {
            shoppingNum.setText(1 + "");
        } else {
            int i = Integer.parseInt(goodsCount);
            shoppingNum.setText(String.valueOf(i + 1));
        }
        //更改商品总价格
        String money = shoppingPrise.getText().toString().trim();
        double shouldMoney = Double.parseDouble(money);
        shouldMoney = shouldMoney + bean.getPRICE();

        shoppingPrise.setText(new DecimalFormat("0.00").format(shouldMoney));

        //更新显示列表
        MyAdapter myAdapter = new MyAdapter(FoodActivity.this, Constant.BEANS, settlement, shoppingPrise, shoppingNum, defaultText);
        shopproductListView.setAdapter(myAdapter);

        settlement.setVisibility(View.VISIBLE);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        Intent data = messageEvent.getData();
        int requestCode = messageEvent.getRequestCode();
        int resultCode = messageEvent.getResultCode();
        switch (requestCode) {
            case 1002:
                if (-1 == resultCode) {
                    //返回结果成功
                    boolean flag = data.getBooleanExtra("flag", false);
                    if (flag) {
                        //如果按的是返回键，则重置页面
                        Constant.BEANS.clear();
                        Constant.FoodMoney = "";
                        Constant.TablePeople = "";
                        changeTextColor(0);
                        changeTextLocation(0);
                        shop_pager.setCurrentItem(0, false);
                        shopAdapter.notifyDataSetChanged();
                        shoppingPrise.setText("0.00");
                        shoppingPrise.setTextColor(getResources().getColor(R.color.default_shopping));
                        shoppingNum.setVisibility(View.GONE);
                        shoppingNum.setText(null);
                        settlement.setVisibility(View.GONE);
                        shopproductListView.setAdapter(myAdapter);
                        defaultText.setVisibility(View.VISIBLE);
                    }
                }
                break;

            case 1003:
                if (-1 == resultCode) {
                    String flag = data.getStringExtra("flag");
                    if ("bothNo".equals(flag)) {

                        //设置小红圈商品数量
                        shoppingNum.setVisibility(View.VISIBLE);
                        String goodsCount = shoppingNum.getText().toString().trim();
                        if (goodsCount == "" || goodsCount.isEmpty()) {
                            shoppingNum.setText(1 + "");
                        } else {
                            int i = Integer.parseInt(goodsCount);
                            shoppingNum.setText(String.valueOf(i + 1));
                        }

                        //更改商品总价格
                        Bundle bundle = data.getBundleExtra("bundle");
                        DetailBean.DtBean bean = (DetailBean.DtBean) bundle.getSerializable("bean");
                        String money = shoppingPrise.getText().toString().trim();
                        double shouldMoney = Double.parseDouble(money);
                        shouldMoney = shouldMoney + bean.getPRICE();
                        shoppingPrise.setTextColor(getResources().getColor(R.color.white_text_color));
                        shoppingPrise.setText(new DecimalFormat("0.00").format(shouldMoney));

                        //更新显示列表
                        MyAdapter myAdapter = new MyAdapter(FoodActivity.this, Constant.BEANS, settlement, shoppingPrise, shoppingNum, defaultText);
                        shopproductListView.setAdapter(myAdapter);
                        //显示去结算按钮
                        settlement.setVisibility(View.VISIBLE);

                    } else if ("confirmOrCancel".equals(flag)) {
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        changeTextColor(0);
        changeTextLocation(0);
        shop_pager.setCurrentItem(0, false);
        shopAdapter.notifyDataSetChanged();
        cardLayout.setVisibility(View.GONE);
    }
}
