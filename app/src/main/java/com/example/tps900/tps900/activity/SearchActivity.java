package com.example.tps900.tps900.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.alibaba.fastjson.JSON;
import com.example.tps900.tps900.Bean.DetailBean;
import com.example.tps900.tps900.Bean.GoodsInStockBean;
import com.example.tps900.tps900.R;
import com.example.tps900.tps900.Utlis.Constant;
import com.example.tps900.tps900.Utlis.Dailog;
import com.example.tps900.tps900.Utlis.LogUtil;
import com.example.tps900.tps900.Utlis.ThreadPoolUtils;
import com.example.tps900.tps900.Utlis.ToastUtils;
import com.example.tps900.tps900.Webservice.GsWebServiceUtils;
import com.example.tps900.tps900.adapters.SearchAdapter;
import com.example.tps900.tps900.sql.PdaDaoUtils;

import java.util.ArrayList;

import static com.example.tps900.tps900.Utlis.Constant.context;

public class SearchActivity extends Activity {

    public static final String TAG = "SearchActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search);
        Bundle bundle = getIntent().getExtras();
        final ArrayList<DetailBean.DtBean> searchList = (ArrayList<DetailBean.DtBean>) bundle.getSerializable("searchList");
        LogUtil.i(TAG, "searchList---->" + searchList.size());

        GridView search_grid = (GridView) findViewById(R.id.search_grid);
        Button btn_cancel_search = (Button) findViewById(R.id.btn_cancel_search);
        Button btn_confirm_search = (Button) findViewById(R.id.btn_confirm_search);
        SearchAdapter adapter = new SearchAdapter(SearchActivity.this, searchList);
        search_grid.setAdapter(adapter);

        search_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                DetailBean.DtBean detailBean = searchList.get(position);
                detailBean.setGoodsCount(1);
                boolean query_gzoneComm_stock = PdaDaoUtils.query_GzoneComm_Stock(
                        "select * from GzoneComm where VCOMMYNAME='" + detailBean.getVCOMMYNAME() + "'And PRICE='" + detailBean.getPRICE() + "'");
                if (query_gzoneComm_stock) {
                    getGoodsInStock(detailBean.getNCOMMID(), detailBean);
                } else {
                    addGoods(detailBean);
                }


            }
        });

        btn_cancel_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("flag", "confirmOrCancel");
                setResult(RESULT_OK, intent);
                SearchActivity.this.finish();
            }
        });


        btn_confirm_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("flag", "confirmOrCancel");
                setResult(RESULT_OK, intent);
                SearchActivity.this.finish();
            }
        });

    }

    /**
     * 添加商品
     *
     * @param detailBean
     */
    public void addGoods(DetailBean.DtBean detailBean) {
        if (!Constant.BEANS.isEmpty()) {

            boolean flag = false;
            for (int i = 0; i < Constant.BEANS.size(); i++) {
                DetailBean.DtBean detailBean_sc = Constant.BEANS.get(i);
                boolean isGood = (detailBean_sc.getNCOMMID().equals(detailBean.getNCOMMID()))
                        && (detailBean_sc.getVCOMMYNAME().equals(detailBean.getVCOMMYNAME()))
                        && (detailBean_sc.getPRICE() == detailBean.getPRICE());
                if (isGood) {
                    flag = true;
                }
            }

            if (!flag) {
                //集合里面没有该bean对象
                Constant.BEANS.add(detailBean);
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("bean", detailBean);
                intent.putExtra("bundle", bundle);
                intent.putExtra("flag", "bothNo");
                setResult(RESULT_OK, intent);
                SearchActivity.this.finish();

            } else {
                //集合里面有该bean对象
                ToastUtils.showCenter(SearchActivity.this, "购物车已添加该商品!");
            }
        } else {
            //集合为空
            Constant.BEANS.add(detailBean);
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("bean", detailBean);
            intent.putExtra("bundle", bundle);
            intent.putExtra("flag", "bothNo");
            setResult(RESULT_OK, intent);
            SearchActivity.this.finish();
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
                            if (Constant.GoodsInStock < 3||Constant.GoodsInStock==3) {
                                ThreadPoolUtils.runTaskInUIThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastUtils.showCenter(context, "库存不足,\n仅剩" + Constant.GoodsInStock + "件!");
                                    }
                                });
                                if (Constant.GoodsInStock > 0 &&goodsList < Constant.GoodsInStock) {

                                    ThreadPoolUtils.runTaskInUIThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ToastUtils.showCenter(context, "库存不足,\n仅剩" + Constant.GoodsInStock + "件!");
                                            addGoods(detailBean);
                                        }
                                    });

                                } else if (Constant.GoodsInStock<=0){
                                    ThreadPoolUtils.runTaskInUIThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ToastUtils.showCenter(context, "库存不足,\n仅剩" + Constant.GoodsInStock + "件!");
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
                            } else if (goodsList >=Constant.GoodsInStock) {
                                ThreadPoolUtils.runTaskInUIThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastUtils.showCenter(context, "已选商品库存不足");
                                    }
                                });
                            }
                        }
                    } else {
                        ThreadPoolUtils.runTaskInUIThread(new Runnable() {
                            @Override
                            public void run() {
                                Dailog.ErrDialog2(context, "获取商品库存数量失败 " , "");
                            }
                        });
                    }
                } else {
                    ThreadPoolUtils.runTaskInUIThread(new Runnable() {
                        @Override
                        public void run() {
                            Dailog.ErrDialog2(context, "获取商品库存数量失败\n请检查网络或配置", "");
                        }
                    });
                }
            }
        });

    }
}
