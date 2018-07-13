package com.example.tps900.tps900.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.tps900.tps900.Bean.CategoryBean;
import com.example.tps900.tps900.Bean.DetailBean;
import com.example.tps900.tps900.Bean.GoodsInStockBean;
import com.example.tps900.tps900.R;
import com.example.tps900.tps900.Utlis.Constant;
import com.example.tps900.tps900.Utlis.Dailog;
import com.example.tps900.tps900.Utlis.LogUtil;
import com.example.tps900.tps900.Utlis.OtherUtils;
import com.example.tps900.tps900.Utlis.ShowInfo;
import com.example.tps900.tps900.Utlis.StrMatchUtils;
import com.example.tps900.tps900.Utlis.ThreadPoolUtils;
import com.example.tps900.tps900.Webservice.GsWebServiceUtils;
import com.example.tps900.tps900.adapters.MyAdapter;
import com.example.tps900.tps900.fragment.Fragment_pro_type;
import com.example.tps900.tps900.sql.PdaDaoUtils;
import com.example.tps900.tps900.view.NoScrollViewPager;
import com.godfery.Utils.ToastUtils;
import com.godfery.Zxing.CaptureActivity;
import com.godfery.keyboard.KeyboardUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.godfery.keyboard.CustomEditLintener.EditListener;

public class RetailActivity extends BaseActivity {

    public static final String TAG = "RetailActivity";

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
    private EditText et_searchGoods;
    private Button btn_scan;
    private TextView shoppingNum;
    private TextView defaultText;
    private ListView shopproductListView;
    private TextView settlement;
    private TextView shoppingPrise;
    private LinearLayout clear_shopCart;
    private MyAdapter myAdapter;
    private Button btn_search;
    private ArrayList searchList;
    private ArrayList commList;

    /**
     * 设置布局
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_retail;
    }

    /**
     * 初始化控件
     */
    @Override
    public void initView() {
        shopping_cart = (ImageView) findViewById(R.id.shopping_cart);
        cardLayout = (FrameLayout) findViewById(R.id.cardLayout);
        img_btn = (LinearLayout) findViewById(R.id.img_back);
        et_searchGoods = (EditText) findViewById(R.id.et_searchGoods);

        EditListener(et_searchGoods);
        et_searchGoods.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                new KeyboardUtil(getApplicationContext(), RetailActivity.this, et_searchGoods, R.id.schemas_key_keyboard_view).showKeyboard();
            }
        });

        et_searchGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new KeyboardUtil(getApplicationContext(), RetailActivity.this, et_searchGoods, R.id.schemas_key_keyboard_view).showKeyboard();
            }
        });

        btn_scan = (Button) findViewById(R.id.btn_scan);
        shoppingNum = (TextView) findViewById(R.id.shoppingNum);
        defaultText = (TextView) findViewById(R.id.defaultText);
        shopproductListView = (ListView) findViewById(R.id.shopproductListView);
        settlement = (TextView) findViewById(R.id.settlement);
        shoppingPrise = (TextView) findViewById(R.id.shoppingPrises);
        clear_shopCart = (LinearLayout) findViewById(R.id.clear_shopCart);
        btn_search = (Button) findViewById(R.id.btn_search);
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
                        MyAdapter myAdapter = new MyAdapter(RetailActivity.this, Constant.BEANS, settlement, shoppingPrise, shoppingNum, defaultText);
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
                openActivityAndCloseThis(MainActivity.class);
            }
        });

        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RetailActivity.this, CaptureActivity.class);
                RetailActivity.this.startActivityForResult(intent, 1001);
            }
        });

        settlement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (OtherUtils.isFastDoubleClick()) {
                    return;
                }

                String money = shoppingPrise.getText().toString().trim();
                Bundle bundle = new Bundle();
                bundle.putString("actualMoney", money);
                Intent intent = new Intent(RetailActivity.this,PayActivitys.class);
                intent.putExtras(bundle);
                startActivityForResult(intent,1002);
              //  openActivity(com.example.tps900.tps900.activity.PayActivitys.class, bundle, 1002);
            }
        });

        clear_shopCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.BEANS.clear();
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

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result = et_searchGoods.getText().toString().trim();
                et_searchGoods.setText(null);
                searchGoods(result);


            }
        });
    }

    private void searchGoods(String result) {
        //判空,获取字符串是否为空
        if (!TextUtils.isEmpty(result)) {
            //如果输入的是汉字
            if (StrMatchUtils.isChineseForStr(result)) {
                LogUtil.i(TAG, "result----->" + result);
                String query_Str = "SELECT * FROM GzoneComm WHERE VCOMMYNAME LIKE ? or VCOMMYNAME LIKE ?  And TYPE='1'";
                String[] selectionArgs = new String[]{"%" + result + "%", "/" + result + "/"};
                PdaDaoUtils utils = new PdaDaoUtils();

                searchList = utils.query_GzoneComm_EditText(query_Str, selectionArgs);
                if (searchList.size() == 1) {
                    et_searchGoods.setText(null);

                    DetailBean.DtBean bean = (DetailBean.DtBean) searchList.get(0);
                    bean.setGoodsCount(1);

                    isAddGoods(bean);

                } else if (searchList.size() > 1) {
                    LogUtil.i(TAG, "searchList----->" + searchList.size());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("searchList", searchList);
                    openActivity(SearchActivity.class, bundle, 1003);
                } else {
                    et_searchGoods.setText(null);
                    ShowInfo showInfo = new ShowInfo(RetailActivity.this);
                    showInfo.infoDialog(RetailActivity.this, "查询信息", "未查询到数据");
                }

            } else if (StrMatchUtils.isNumberForStr(result)) {
                String query_Str = "SELECT * FROM GzoneComm WHERE VCOMMYNAME LIKE ?  or NCOMMID LIKE ? or VCOMMCODING LIKE ? or VCOMMCODING = ?  And TYPE = '1'";
                String[] selectionArgs = new String[]{"%" + result + "%", "%" + result + "%", "%" + result + "%", "%" + result + "%"};
                PdaDaoUtils utils = new PdaDaoUtils();

                searchList = utils.query_GzoneComm_EditText(query_Str, selectionArgs);
                if (searchList.size() == 1) {
                    et_searchGoods.setText(null);
                    DetailBean.DtBean bean = (DetailBean.DtBean) searchList.get(0);
                    bean.setGoodsCount(1);

                    isAddGoods(bean);

                } else if (searchList.size() > 1) {
                    LogUtil.i(TAG, "searchList----->" + searchList.size());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("searchList", searchList);
                    openActivity(SearchActivity.class, bundle, 1003);
                } else {
                    et_searchGoods.setText(null);
                    ShowInfo showInfo = new ShowInfo(RetailActivity.this);
                    showInfo.infoDialog(RetailActivity.this, "查询信息", "未查询到您要的商品");
                }

            } else if (StrMatchUtils.isEnglishForStr(result)) {
                String query_Str = "SELECT * FROM GzoneComm WHERE VCOMMYNAME LIKE ? or VCOMMYNAME LIKE ? or SIMPLIFY LIKE ? or SIMPLIFY LIKE ? or ATTENTION LIKE ? or ATTENTION LIKE ? or VCOMMCODING LIKE ? And TYPE ='1'";
                String[] selectionArgs = new String[]{"%" + result + "%", "/" + result + "/", "%" + result + "%", "/" + result + "/", "%" + result + "%", "/" + result + "/", "%" + result + "%"};
                PdaDaoUtils utils = new PdaDaoUtils();

                searchList = utils.query_GzoneComm_EditText(query_Str, selectionArgs);
                if (searchList.size() == 1) {

                    et_searchGoods.setText(null);

                    DetailBean.DtBean bean = (DetailBean.DtBean) searchList.get(0);
                    bean.setGoodsCount(1);

                    isAddGoods(bean);
                } else if (searchList.size() > 1) {
                    LogUtil.i(TAG, "searchList----->" + searchList.size());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("searchList", searchList);
                    openActivity(SearchActivity.class, bundle, 1003);
                } else {
                    et_searchGoods.setText(null);
                    ShowInfo showInfo = new ShowInfo(RetailActivity.this);
                    showInfo.infoDialog(RetailActivity.this, "查询信息", "未查询到您要的商品");
                }

            }
        } else {
            ToastUtils.show(RetailActivity.this, "请输入商品条码");
            return;
        }
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

                myAdapter = new MyAdapter(RetailActivity.this, beans, settlement, shoppingPrise, shoppingNum, defaultText);
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
            case 1001:
                if (null != data) {
                    String result = data.getStringExtra("result");
                    et_searchGoods.setText(result);
                    searchGoods(result);
                } else {
                    ToastUtils.show(RetailActivity.this, "未获取到数据!");
                }

                break;

            case 1002:
                if (-1 == resultCode) {
                    //返回结果成功
                    boolean flag = data.getBooleanExtra("flag", false);
                    if (flag) {
                        //如果按的是返回键，则重置页面
                        Constant.BEANS.clear();
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

                        et_searchGoods.setText(null);
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
                        MyAdapter myAdapter = new MyAdapter(RetailActivity.this, Constant.BEANS, settlement, shoppingPrise, shoppingNum, defaultText);
                        shopproductListView.setAdapter(myAdapter);
                        //显示去结算按钮
                        settlement.setVisibility(View.VISIBLE);

                    } else if ("confirmOrCancel".equals(flag)) {
                        et_searchGoods.setText(null);
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
        arrayList = utils.query_CommClass("1");
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
            Fragment fragment = new Fragment_pro_type();
            Bundle bundle = new Bundle();
            CategoryBean.DtBean bean = (CategoryBean.DtBean) arrayList.get(arg0);
            int nclassid = bean.getNCLASSID();
            String sclassname = bean.getSCLASSNAME();
            String sql = "select VCOMMYNAME,PRICE,NCOMMID,NPCOMMID,PNUMBER,VCOMMCODING,SIMPLIFY,ATTENTION from GzoneComm where classID = '" + nclassid + "'" + "And TYPE = '1'";
            PdaDaoUtils utils = new PdaDaoUtils();
            commList = utils.query_GzoneComm(sql);
            LogUtil.i("TAG", "大小--->" + commList.size());
            bundle.putSerializable("result", commList);
            bundle.putString("topType", sclassname);
            fragment.setArguments(bundle);

            return fragment;
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
        MyAdapter myAdapter = new MyAdapter(RetailActivity.this, Constant.BEANS, settlement, shoppingPrise, shoppingNum, defaultText);
        shopproductListView.setAdapter(myAdapter);

        settlement.setVisibility(View.VISIBLE);

    }

    /**
     * 传入商品返回此商品的数量
     *
     * @param detailBean
     * @return
     */
    public static int isGoodsList(DetailBean.DtBean detailBean) {
        int goodsNum = 0;
        for (int i = 0; i < Constant.BEANS.size(); i++) {
            DetailBean.DtBean detailBeanSc = Constant.BEANS.get(i);
            boolean isGood = (detailBeanSc.getNCOMMID().equals(detailBean.getNCOMMID()))
                    && (detailBeanSc.getVCOMMYNAME().equals(detailBean.getVCOMMYNAME()))
                    && (detailBeanSc.getPRICE() == detailBean.getPRICE());
            if (isGood) {
                goodsNum = detailBeanSc.getGoodsCount();
            }
        }
        return goodsNum;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        changeTextColor(0);
        changeTextLocation(0);
        shop_pager.setCurrentItem(0, false);
        shopAdapter.notifyDataSetChanged();
        cardLayout.setVisibility(View.GONE);
        updateViews(Constant.BEANS);
    }

//    public void IsOnClick(String Type) {
//        if (Type.equals("Goods")) {
//            btn_search.setEnabled(false);
//        } else if (Type.equals("Submit")) {
//
//        }
//    }

    /**
     * 添加商品
     *
     * @param detailBean
     */
    public void addGoods(DetailBean.DtBean detailBean) {

        DetailBean.DtBean bean = (DetailBean.DtBean) searchList.get(0);
        bean.setGoodsCount(1);

        if (!Constant.BEANS.isEmpty()) {
            //集合非空
            boolean flag = false;
            for (int i = 0; i < Constant.BEANS.size(); i++) {
                DetailBean.DtBean detailBean_sc = Constant.BEANS.get(i);
                boolean isGood = (detailBean_sc.getNCOMMID().equals(bean.getNCOMMID()))
                        && (detailBean_sc.getVCOMMYNAME().equals(bean.getVCOMMYNAME()))
                        && (detailBean_sc.getPRICE() == bean.getPRICE());
                if (isGood) {
                    flag = true;
                }
            }

            if (!flag) {
                //集合里面没有该bean对象
                Constant.BEANS.add(bean);
                detailBean.setGoodsCount(1);
                detailBean.setSale(true);
                updateViews(bean);
            } else {
                //集合里面有该bean对象
                ToastUtils.show(RetailActivity.this, "购物车已添加该商品!");
            }
        } else {
            //集合为空
            Constant.BEANS.add(bean);
            detailBean.setGoodsCount(1);
            detailBean.setSale(true);
            updateViews(bean);
        }
    }

    /**
     * 获取商品库存数量
     */
    public void getGoodsInStock(String goodId, DetailBean.DtBean detailBean) {
        ThreadPoolUtils.runTaskInThread(new Runnable() {
            @Override
            public void run() {
                String goodsstock = GsWebServiceUtils.getGOODSSTOCK(String.valueOf(Constant.ZONEID), goodId);
                if (!goodsstock.equals("err")) {
                    GoodsInStockBean goodsInStockBean = JSON.parseObject(goodsstock, GoodsInStockBean.class);
                    if (goodsInStockBean.isSuccess) {
                        if (goodsInStockBean.isStock) {
                            Constant.GoodsInStock = goodsInStockBean.StorckNum;
                            int goodsList = RetailActivity.isGoodsList(detailBean);
                            if (Constant.GoodsInStock < 3 || Constant.GoodsInStock == 3) {
                                ThreadPoolUtils.runTaskInUIThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastUtils.show(RetailActivity.this, "库存不足,\n仅剩" + Constant.GoodsInStock + "件!");
                                    }
                                });
                                if (Constant.GoodsInStock > 0 && goodsList < Constant.GoodsInStock) {

                                    ThreadPoolUtils.runTaskInUIThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ToastUtils.show(RetailActivity.this, "库存不足,\n仅剩" + Constant.GoodsInStock + "件!");
                                            addGoods(detailBean);
                                        }
                                    });

                                } else if (Constant.GoodsInStock <= 0) {
                                    ThreadPoolUtils.runTaskInUIThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ToastUtils.show(RetailActivity.this, "库存不足,\n仅剩" + Constant.GoodsInStock + "件!");
                                        }
                                    });
                                }
                            } else if (goodsList < Constant.GoodsInStock && Constant.GoodsInStock > 3.0) {
                                ThreadPoolUtils.runTaskInUIThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        addGoods(detailBean);
                                    }
                                });
                            } else if (goodsList >= Constant.GoodsInStock) {
                                ThreadPoolUtils.runTaskInUIThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastUtils.show(RetailActivity.this, "已选商品库存不足");
                                    }
                                });
                            }
                        }
                    } else {
                        ThreadPoolUtils.runTaskInUIThread(new Runnable() {
                            @Override
                            public void run() {
                                Dailog.ErrDialog2(RetailActivity.this, "获取商品库存数量失败 ", "");
                            }
                        });
                    }
                } else {
                    ThreadPoolUtils.runTaskInUIThread(new Runnable() {
                        @Override
                        public void run() {
                            Dailog.ErrDialog2(RetailActivity.this, "获取商品库存数量失败\n请检查网络或配置", "");
                        }
                    });
                }
            }
        });

    }


    public void isAddGoods(DetailBean.DtBean detailBean) {
        boolean query_gzoneComm_stock = PdaDaoUtils.query_GzoneComm_Stock(
                "select * from GzoneComm where VCOMMYNAME='" + detailBean.getVCOMMYNAME() + "'And PRICE='" + detailBean.getPRICE() + "'");
        if (query_gzoneComm_stock) {
            getGoodsInStock(detailBean.getNCOMMID(), detailBean);
        } else {
            addGoods(detailBean);
        }
    }

    /**
     * @param event
     * @return 避免同时按两个按钮
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getPointerCount() == 2) {
            Log.e("两个按钮同时按","获得两点的坐标，此时禁止点击事件传递");
            //true,则拦截所有点击事件，按钮的点击事件不会被执行
            return true;
        }
        return false;
    }
}
