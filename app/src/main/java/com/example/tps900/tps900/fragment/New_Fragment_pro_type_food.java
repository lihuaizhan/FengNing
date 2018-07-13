package com.example.tps900.tps900.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.example.tps900.tps900.activity.RetailActivity;
import com.example.tps900.tps900.activity.newfood.New_FoodActivity;
import com.example.tps900.tps900.adapters.Pro_type_adapter;
import com.example.tps900.tps900.sql.PdaDaoUtils;

import java.util.ArrayList;


public class New_Fragment_pro_type_food extends Fragment {
    private ImageView hint_img;
    private GridView mGridView;
    private Pro_type_adapter adapter;
    private ArrayList result;
    private ArrayList<DetailBean> beans;
    private New_FoodActivity activity;
    private TextView toptype;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        beans = new ArrayList<>();

        View view = inflater.inflate(R.layout.fragment_pro_type, null);
        toptype = (TextView) view.findViewById(R.id.toptype);
        hint_img = (ImageView) view.findViewById(R.id.hint_img);
        mGridView = (GridView) view.findViewById(R.id.listView);
        result = (ArrayList) getArguments().getSerializable("result");
        String topType = getArguments().getString("topType");
        toptype.setText(topType);

        if (null != result) {
            if (!result.isEmpty()) {
                adapter = new Pro_type_adapter(getActivity(), result);
                mGridView.setAdapter(adapter);
            }

        }
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                DetailBean.DtBean detailBean = (DetailBean.DtBean) result.get(arg2);
                LogUtil.i("TAG", detailBean.getVCOMMYNAME());
                boolean query_gzoneComm_stock = PdaDaoUtils.query_GzoneComm_Stock(
                        "select * from GzoneComm where VCOMMYNAME='" + detailBean.getVCOMMYNAME() + "'And PRICE='" + detailBean.getPRICE() + "'");
                if (query_gzoneComm_stock) {
                    getGoodsInStock(detailBean.getNCOMMID(), detailBean);
                } else {
                    addGoods(detailBean);
                }

            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        activity = (New_FoodActivity) context;


    }


    /**
     * 获取商品库存数量
     */
    public void getGoodsInStock(String goodId, DetailBean.DtBean detailBean) {
        ThreadPoolUtils.runTaskInThread(new Runnable() {
            @Override
            public void run() {
                String goodsstock = GsWebServiceUtils.getGOODSSTOCK(String.valueOf(Constant.Food_ZONEID), goodId);
                if (!goodsstock.equals("err")) {
                    GoodsInStockBean goodsInStockBean = JSON.parseObject(goodsstock, GoodsInStockBean.class);
                    if (goodsInStockBean.isSuccess) {
                        if (goodsInStockBean.isStock) {
                            Constant.GoodsInStock = goodsInStockBean.StorckNum;
                            int goodsList = RetailActivity.isGoodsList(detailBean);
                            if (Constant.GoodsInStock < 3.0 || Constant.GoodsInStock == 3) {
                                ThreadPoolUtils.runTaskInUIThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastUtils.show(activity, "库存不足,\n仅剩" + Constant.GoodsInStock + "件!");
                                    }
                                });
                                if (Constant.GoodsInStock > 0 && goodsList < Constant.GoodsInStock) {

                                    ThreadPoolUtils.runTaskInUIThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ToastUtils.showCenter(activity, "库存不足,\n仅剩" + Constant.GoodsInStock + "件!");
                                        }
                                    });
                                    addGoods(detailBean);
                                } else if (Constant.GoodsInStock <= 0) {
                                    ThreadPoolUtils.runTaskInUIThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ToastUtils.showCenter(activity, "库存不足,\n仅剩" + Constant.GoodsInStock + "件!");
                                        }
                                    });
                                }
                            } else if (goodsList < Constant.GoodsInStock && Constant.GoodsInStock > 3.0) {
                                addGoods(detailBean);
                            } else if (goodsList >= Constant.GoodsInStock) {
                                ThreadPoolUtils.runTaskInUIThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastUtils.showCenter(activity, "已选商品库存不足");
                                    }
                                });
                            }
                        }
                    } else {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Dailog.ErrDialog2(activity, "获取商品库存数量失败", "");
                            }
                        });
                    }
                } else {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Dailog.ErrDialog2(activity, "获取商品库存数量失败\n请检查网络或配置", "");
                        }
                    });
                }
            }
        });

    }

    /**
     * 添加商品
     *
     * @param detailBean
     */
    public void addGoods(DetailBean.DtBean detailBean) {
        ThreadPoolUtils.runTaskInUIThread(new Runnable() {
            @Override
            public void run() {
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
                        //集合里没有该对象
                        Constant.BEANS.add(detailBean);
                        detailBean.setGoodsCount(1);
                        detailBean.setSale(true);
                        activity.updateViews(Constant.BEANS);
                    } else {
                        //集合里由该对象
                        ToastUtils.showCenter(getActivity(), "购物车已添加该商品!");
                    }
                } else {
                    //集合为空
                    Constant.BEANS.add(detailBean);
                    detailBean.setGoodsCount(1);
                    detailBean.setSale(true);
                    activity.updateViews(Constant.BEANS);

                }
            }
        });
    }

}
